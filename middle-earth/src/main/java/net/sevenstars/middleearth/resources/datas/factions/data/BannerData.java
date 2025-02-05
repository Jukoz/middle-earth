package net.sevenstars.middleearth.resources.datas.factions.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BannerData {
    public static class BannerPatternWithColor {
        public Identifier id;
        public DyeColor color;
        public RegistryKey<BannerPattern> patternRegistryKey;
        public BannerPattern pattern;

        public BannerPatternWithColor(Identifier id, DyeColor dyeColor){
            this.id = id;
            this.color = dyeColor;
            this.patternRegistryKey = null;
            this.pattern = null;
        }

        public BannerPatternWithColor(RegistryKey<BannerPattern> patternRegistryKey, DyeColor dyeColor) {
            this.patternRegistryKey = patternRegistryKey;
            this.id = patternRegistryKey.getValue();
            this.color = dyeColor;
            this.pattern = null;
        }
        public void setPattern(BannerPattern pattern){
            this.pattern = pattern;
        }
        @Override
        public String toString() {
            return id + " in " + color.name();
        }
    }
    private DyeColor baseBannerColor;
    private final List<BannerPatternWithColor> bannerPatternWithColors;
    public static final DyeColor DEFAULT_DYE = DyeColor.PINK;

    public BannerData(DyeColor baseBannerColor, List<BannerPatternWithColor> bannerPatternWithColors){
        this.baseBannerColor = baseBannerColor;

        if (bannerPatternWithColors != null)
            this.bannerPatternWithColors = bannerPatternWithColors;
        else
            this.bannerPatternWithColors = new ArrayList<>();

    }

    public BannerData(Optional<NbtCompound> optionalBannerDataNbt) {
        if(optionalBannerDataNbt.isEmpty()){
            bannerPatternWithColors = null;
            return;
        }
        NbtCompound compound = optionalBannerDataNbt.get();

        baseBannerColor = DyeColor.byName(compound.getString("base_color"), DEFAULT_DYE);

        NbtList patterns = compound.getList("patterns", NbtType.COMPOUND);
        this.bannerPatternWithColors = new ArrayList<>();

        JsonParser jsonParser = new JsonParser();

        for(NbtElement element: patterns){
            JsonObject json = (JsonObject) jsonParser.parse(element.asString());
            Identifier id = Identifier.of(json.get("id").getAsString());
            DyeColor color = DyeColor.byName(json.get("dye_color").getAsString(), DEFAULT_DYE);

            BannerPatternWithColor bannerPatternWithColor = new BannerPatternWithColor(id, color);
            bannerPatternWithColors.add(bannerPatternWithColor);
        }
    }

    public List<BannerPatternWithColor> getBannerPatternsWithColors(World world){
        List<BannerPatternWithColor> patterns = new ArrayList<>();
        for(int i = 0; i < bannerPatternWithColors.size(); i++){
            BannerPattern pattern = world.getRegistryManager().getOptional(RegistryKeys.BANNER_PATTERN).get().get(bannerPatternWithColors.get(i).id);
            if(pattern == null){
                continue;
            }
            bannerPatternWithColors.get(i).setPattern(pattern);
            patterns.add(bannerPatternWithColors.get(i));
        }
        return patterns;
    }

    public DyeColor getBaseDye() {
        return baseBannerColor;
    }

    public Optional<NbtCompound> getNbt() {
        if(baseBannerColor == null || bannerPatternWithColors == null || bannerPatternWithColors.isEmpty())
            return Optional.empty();

        NbtCompound nbt = new NbtCompound();
        nbt.putString("base_color",  getBaseDye().name().toLowerCase());
        NbtList list = new NbtList();
        for(BannerPatternWithColor pattern : bannerPatternWithColors){
            NbtCompound compound = new NbtCompound();
            compound.putString("id", pattern.id.toString());
            compound.putString("dye_color",  pattern.color.name().toLowerCase());
            list.add(compound);
        }
        nbt.put("patterns", list);

        return Optional.of(nbt);
    }

    /**
     * Not datadriven, only for hardcoded factions
     * @param bannerPatternLookup
     * @return
     */
    public BannerPatternsComponent getBannerPatternComponents(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        BannerPatternsComponent.Builder bannerPatternsComponentBuilder = new BannerPatternsComponent.Builder();

        bannerPatternsComponentBuilder.add(new BannerPatternsComponent.Layer(bannerPatternLookup.getOrThrow(BannerPatterns.BASE), baseBannerColor));
        for(BannerPatternWithColor bannerPatternWithColor :  bannerPatternWithColors){
            bannerPatternsComponentBuilder.add(bannerPatternLookup.getOrThrow(bannerPatternWithColor.patternRegistryKey), bannerPatternWithColor.color);
        }

        return bannerPatternsComponentBuilder.build();
    }

    public ItemStack getBannerItem(World world, Text text) {
        BannerPatternsComponent.Builder builder = new BannerPatternsComponent.Builder();

        var registry = world.getRegistryManager().get(RegistryKeys.BANNER_PATTERN);
        for(BannerPatternWithColor bannerPatternWithColor :  bannerPatternWithColors){
            RegistryEntry<BannerPattern> bannerPattern = registry.getEntry(bannerPatternWithColor.id).get();
            BannerPatternsComponent.Layer layer = new BannerPatternsComponent.Layer(bannerPattern, bannerPatternWithColor.color);
            builder.add(layer);
        }

        return formatBanner(new ItemStack(Items.WHITE_BANNER), builder.build(), text);
    }

    public static ItemStack formatBanner(ItemStack itemStack, BannerPatternsComponent bannerPatternsComponent, Text translationKey) {
        itemStack.set(DataComponentTypes.BANNER_PATTERNS, bannerPatternsComponent);
        itemStack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        itemStack.set(DataComponentTypes.ITEM_NAME, translationKey);
        return itemStack;
    }
}
