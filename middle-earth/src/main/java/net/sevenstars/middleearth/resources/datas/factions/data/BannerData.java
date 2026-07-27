package net.sevenstars.middleearth.resources.datas.factions.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;

public class BannerData {
    public static class BannerPatternWithColor {
        public ResourceLocation id;
        public DyeColor color;
        public ResourceKey<BannerPattern> patternRegistryKey;
        public BannerPattern pattern;

        public BannerPatternWithColor(ResourceLocation id, DyeColor dyeColor){
            this.id = id;
            this.color = dyeColor;
            this.patternRegistryKey = null;
            this.pattern = null;
        }

        public BannerPatternWithColor(ResourceKey<BannerPattern> patternRegistryKey, DyeColor dyeColor) {
            this.patternRegistryKey = patternRegistryKey;
            this.id = patternRegistryKey.location();
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

    public BannerData(Optional<CompoundTag> optionalBannerDataNbt) {
        if(optionalBannerDataNbt.isEmpty()){
            bannerPatternWithColors = null;
            return;
        }
        CompoundTag compound = optionalBannerDataNbt.get();

        baseBannerColor = DyeColor.byName(compound.getString("base_color"), DEFAULT_DYE);

        ListTag patterns = compound.getList("patterns", Tag.TAG_COMPOUND);
        this.bannerPatternWithColors = new ArrayList<>();

        for(Tag element: patterns){
            try{
                CompoundTag elementCompound = (CompoundTag) element;
                ResourceLocation id = ResourceLocation.parse(elementCompound.getString("id"));
                String dyeColor = elementCompound.contains("dye_color", Tag.TAG_STRING)
                        ? elementCompound.getString("dye_color")
                        : DEFAULT_DYE.getSerializedName();
                DyeColor color = DyeColor.byName(dyeColor, DEFAULT_DYE);

                BannerPatternWithColor bannerPatternWithColor = new BannerPatternWithColor(id, color);
                bannerPatternWithColors.add(bannerPatternWithColor);
            } catch (Exception ignored){

            }
        }
    }

    public List<BannerPatternWithColor> getBannerPatternsWithColors(Level world){
        List<BannerPatternWithColor> patterns = new ArrayList<>();
        for(int i = 0; i < bannerPatternWithColors.size(); i++){
            BannerPattern pattern = world.registryAccess().registryOrThrow(Registries.BANNER_PATTERN).get(bannerPatternWithColors.get(i).id);
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

    public Optional<CompoundTag> getNbt() {
        if(baseBannerColor == null || bannerPatternWithColors == null || bannerPatternWithColors.isEmpty())
            return Optional.empty();

        CompoundTag nbt = new CompoundTag();
        nbt.putString("base_color",  getBaseDye().name().toLowerCase());
        ListTag list = new ListTag();
        for(BannerPatternWithColor pattern : bannerPatternWithColors){
            CompoundTag compound = new CompoundTag();
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
    public BannerPatternLayers getBannerPatternComponents(HolderGetter<BannerPattern> bannerPatternLookup) {
        BannerPatternLayers.Builder bannerPatternsComponentBuilder = new BannerPatternLayers.Builder();

        bannerPatternsComponentBuilder.add(new BannerPatternLayers.Layer(bannerPatternLookup.getOrThrow(BannerPatterns.BASE), baseBannerColor));
        for(BannerPatternWithColor bannerPatternWithColor :  bannerPatternWithColors){
            bannerPatternsComponentBuilder.add(bannerPatternLookup.getOrThrow(bannerPatternWithColor.patternRegistryKey), bannerPatternWithColor.color);
        }

        return bannerPatternsComponentBuilder.build();
    }

    public ItemStack getBannerItem(HolderLookup.Provider wrapperLookup, Component text) {
        BannerPatternLayers.Builder builder = new BannerPatternLayers.Builder();
        HolderLookup.RegistryLookup<BannerPattern> registry = wrapperLookup.lookupOrThrow(Registries.BANNER_PATTERN);
        for (BannerPatternWithColor bannerPatternWithColor : bannerPatternWithColors) {
            ResourceKey<BannerPattern> key = ResourceKey.create(
                    Registries.BANNER_PATTERN,
                    bannerPatternWithColor.id
            );

            Holder<BannerPattern> pattern = registry.getOrThrow(key);

            builder.add(new BannerPatternLayers.Layer(
                    pattern,
                    bannerPatternWithColor.color
            ));
        }

        return formatBanner(new ItemStack(Items.WHITE_BANNER), builder.build(), text);
    }

    public static ItemStack formatBanner(ItemStack itemStack, BannerPatternLayers bannerPatternsComponent, Component translationKey) {
        itemStack.set(DataComponents.BANNER_PATTERNS, bannerPatternsComponent);
        // itemStack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE); // TODO : Not existing
        itemStack.set(DataComponents.ITEM_NAME, translationKey);
        return itemStack;
    }
}
