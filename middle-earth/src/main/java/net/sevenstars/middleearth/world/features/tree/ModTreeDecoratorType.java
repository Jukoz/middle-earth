package net.sevenstars.middleearth.world.features.tree;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.world.features.tree.backport.AttachedToLogsTreeDecorator;
import net.sevenstars.middleearth.world.features.tree.backport.PlaceOnGroundTreeDecorator;
import net.sevenstars.middleearth.world.features.tree.decorators.ConnectedLeavesTreeDecorator;
import net.sevenstars.middleearth.world.features.tree.decorators.PaleMossTreeDecorator;

public class ModTreeDecoratorType {
    public static final TreeDecoratorType<AttachedToLogsTreeDecorator> ATTACHED_TO_LOGS = register(
            "attached_to_logs", AttachedToLogsTreeDecorator.CODEC);
    public static final TreeDecoratorType<PlaceOnGroundTreeDecorator> PLACE_ON_GROUND = register(
            "place_on_ground", PlaceOnGroundTreeDecorator.CODEC);
    public static final TreeDecoratorType<PaleMossTreeDecorator> PALE_MOSS = register(
            "pale_moss", PaleMossTreeDecorator.CODEC);
    public static final TreeDecoratorType<ConnectedLeavesTreeDecorator> CONNECTED_LEAVES = register(
            "connected_leaves", ConnectedLeavesTreeDecorator.CODEC);

    private static <P extends TreeDecorator> TreeDecoratorType<P> register(String id, MapCodec<P> codec) {
        return RegistrationBridge.register(
                BuiltInRegistries.TREE_DECORATOR_TYPE,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id),
                new TreeDecoratorType<>(codec)
        );
    }

    public static void initialize() {
    }
}
