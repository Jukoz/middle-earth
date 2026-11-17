package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.CommandRegistryME;
import net.sevenstars.middleearth.commands.suggestions.MapBiomeSuggestionProvider;
import net.sevenstars.middleearth.world.dimension.DimensionRegistryME;
import net.sevenstars.middleearth.world.map.MapBiomeLocator;

public class CommandLocateBiome {
    private static final String LOCATE = "locate";
    private static final String BIOME = "biome";

    private static final DynamicCommandExceptionType NOT_FOUND = new DynamicCommandExceptionType(
            id -> Text.translatable("commands.locate.biome.not_found", id));
    private static final DynamicCommandExceptionType NOT_ON_MAP = new DynamicCommandExceptionType(
            id -> Text.translatable("command.%s.locate_biome.not_on_map".formatted(MiddleEarth.MOD_ID), id));
    private static final SimpleCommandExceptionType NOT_IN_MIDDLE_EARTH = new SimpleCommandExceptionType(
            Text.translatable("command.%s.locate_biome.not_in_middle_earth".formatted(MiddleEarth.MOD_ID)));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal(CommandRegistryME.BASE_COMMAND)
                .requires(CommandManager.requirePermissionLevel(CommandManager.GAMEMASTERS_CHECK))
                .then(CommandManager.literal(LOCATE)
                .then(CommandManager.literal(BIOME)
                .then(CommandManager.argument(BIOME, IdentifierArgumentType.identifier())
                .suggests(new MapBiomeSuggestionProvider())
                .executes(CommandLocateBiome::run)))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        Identifier id = IdentifierArgumentType.getIdentifier(context, BIOME);

        if (!DimensionRegistryME.isInMiddleEarth(source.getWorld())) throw NOT_IN_MIDDLE_EARTH.create();

        RegistryKey<Biome> biome = RegistryKey.of(RegistryKeys.BIOME, id);
        if (!MapBiomeLocator.getFindableBiomes().contains(biome)) throw NOT_ON_MAP.create(id.toString());

        BlockPos origin = BlockPos.ofFloored(source.getPosition());
        ServerWorld world = source.getWorld();
        BiomeSource biomeSource = world.getChunkManager().getChunkGenerator().getBiomeSource();
        MultiNoiseUtil.MultiNoiseSampler sampler = world.getChunkManager().getNoiseConfig().getMultiNoiseSampler();
        // near the top of the world the source gives the surface biome, not a cave one
        int surfaceY = BiomeCoords.fromBlock(world.getTopYInclusive() - 16);
        BlockPos found = MapBiomeLocator.locate(biome, origin.getX(), origin.getZ(),
                        (x, z) -> biomeSource.getBiome(BiomeCoords.fromBlock(x), surfaceY, BiomeCoords.fromBlock(z), sampler).getKey().orElse(null))
                .orElseThrow(() -> NOT_FOUND.create(id.toString()));

        int distance = MathHelper.floor(Math.hypot(found.getX() - origin.getX(), found.getZ() - origin.getZ()));

        Text coordinates = Texts.bracketed(Text.translatable("chat.coordinates", found.getX(), "~", found.getZ()))
                .styled(style -> style.withColor(Formatting.GREEN)
                        .withClickEvent(new ClickEvent.SuggestCommand("/tp @s " + found.getX() + " ~ " + found.getZ()))
                        .withHoverEvent(new HoverEvent.ShowText(Text.translatable("chat.coordinates.tooltip"))));
        source.sendFeedback(() -> Text.translatable("commands.locate.biome.success", id.toString(), coordinates, distance), false);
        return distance;
    }
}
