package net.sevenstars.middleearth.resources.datas.npc_types.data;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsStatesME;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GearItemData {
    private final static String NO_HOOD_KEY = "no_hood";
    private final static String NO_CAPE_KEY = "no_cape";
    private Item item;
    private Integer color = null;
    private List<Integer> colors = null;
    private BackAttachmentsME cape = null;
    private Integer capeColor = null;
    private List<Integer> capeColors = null;
    private HelmetAttachmentsME hood = null;
    private Integer hoodColor = null;
    private List<Integer> hoodColors = null;
    private Boolean isDown = null;
    private Boolean noCape = null;
    private Boolean noHood = null;

    public GearItemData() {
        this.item = Items.AIR;
    }
    public GearItemData(Item item) {
        this.item = item;
    }
    public GearItemData(ResourceLocation itemIdentifier) {
        this.item = getItemFromId(itemIdentifier);
    }

    public static GearItemData create() {
        return new GearItemData();
    }
    public static GearItemData create(Item item) {
        return new GearItemData(item);
    }
    public static GearItemData create(ResourceLocation itemIdentifier) {
        return new GearItemData(itemIdentifier);
    }

    public GearItemData withColor(int color) {
        this.color = color;
        return this;
    }

    public GearItemData withColors(List<Integer> colors) {
        this.colors = colors;
        return this;
    }

    public GearItemData withoutCape() {
        this.noCape = true;
        return this;
    }
    public GearItemData withCape(BackAttachmentsME cape, int color) {
        capeColor = color;
        return withCape(cape);
    }
    public GearItemData withCape(BackAttachmentsME cape, List<Integer> colors) {
        this.capeColors = colors;
        return withCape(cape);
    }

    public GearItemData withCape(BackAttachmentsME cape) {
        if(cape == null){
            this.noCape = true;
        }
        this.cape = cape;
        return this;
    }

    public GearItemData withoutHood() {
        this.noHood = true;
        return this;
    }
    public GearItemData withHood(HelmetAttachmentsME hood, int color) {
        hoodColor = color;
        return withHood(hood);
    }
    public GearItemData withHood(HelmetAttachmentsME hood, List<Integer> colors) {
        hoodColors = colors;
        return withHood(hood);
    }
    public GearItemData withHood(HelmetAttachmentsME hood) {
        if(hood == null){
            this.noHood = true;
            this.hood = null;
            this.isDown = null;
            return this;
        }
        this.hood = hood;
        if(this.hood.getConstantState() != null)
            this.isDown = this.hood.getConstantState() == HelmetAttachmentsStatesME.DOWN;
        else
            this.isDown = null;
        return this;
    }

    public GearItemData withHood(HelmetAttachmentsME hood, boolean isDown) {
        withHood(hood);
        if(this.hood.getConstantState() == null) {
            this.isDown = isDown;
        }
        if(this.isDown != isDown){
            MiddleEarth.LOGGER.logError("NpcGearItemData:: [%s - %s] Cannot set the hood state to %s, it was forced to %s!".formatted(this.item.getDescription(), hood.getName(), isDown, this.isDown));
        }
        return this;
    }

    private static Item getItemFromId(ResourceLocation itemId){
        return BuiltInRegistries.ITEM.get(itemId);
    }

    private static ResourceLocation getIdentifierFromItem(Item item){
        return BuiltInRegistries.ITEM.getKey(item);
    }

    private int getRandomColor(List<Integer> listToFetch) {
        if(listToFetch != null && !listToFetch.isEmpty()){
            return listToFetch.get(ThreadLocalRandom.current().nextInt(listToFetch.size()));
        }
        return Color.PINK.getRGB();
    }

    public Integer getColor(){
        return this.color;
    }
    public ResourceLocation getItemIdentifier() {
        return getIdentifierFromItem(this.item);
    }

    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(this.item);

        if(this.color != null){
            if(itemStack.is(ItemTags.DYEABLE))
                itemStack.set(DataComponents.DYED_COLOR, new DyedItemColor(this.color, true));
        } else if(this.colors != null){
            if(itemStack.is(ItemTags.DYEABLE))
                itemStack.set(DataComponents.DYED_COLOR, new DyedItemColor(getRandomColor(colors), true));
        }
        if(this.noCape != null && this.noCape && itemStack.getComponents().has(DataComponentTypesME.BACK_ATTACHMENT_DATA)){
            itemStack.remove(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        } else if (cape != null)
            if(capeColor != null)
                itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachmentWithColor(cape, capeColor));
            else if(capeColors != null)
                itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachmentWithColor(cape, getRandomColor(capeColors)));
            else
                itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(cape));

        if(this.noHood != null && this.noHood && itemStack.getComponents().has(DataComponentTypesME.HELMET_ATTACHMENT_DATA)){
            itemStack.remove(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        } else if(hood != null){
            boolean hoodState = false;
            if(this.hood.getConstantState() != null){
                hoodState = this.hood.getConstantState() == HelmetAttachmentsStatesME.DOWN;
            } else if(isDown == null){
                hoodState = ThreadLocalRandom.current().nextBoolean();
            } else {
                hoodState = this.isDown;
            }

            if(hoodColor != null)
                itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(hoodState, hood, hoodColor));
            else if(hoodColors != null)
                itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(hoodState, hood, getRandomColor(hoodColors)));
            else
                itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(hoodState, hood, DyedItemColor.LEATHER_COLOR));
        }
        return itemStack;
    }

    public GearItemData(CompoundTag nbt){
        this(ResourceLocation.parse(nbt.getString("id")));

        if(nbt.get("color") != null){
            color = nbt.getInt("color");
        }
        if(nbt.contains("colors", Tag.TAG_INT_ARRAY)){
            int[] list = nbt.getIntArray("colors");
            List<Integer> newColors = new ArrayList<>();
            for (int j : list) {
                newColors.add(j);
            }
            colors = newColors;
        }
        if(nbt.get("no_cape") != null){
            noCape = nbt.getBoolean("no_cape");
        }
        if(nbt.get("cape") != null){
            cape = BackAttachmentsME.valueOf(nbt.getString("cape").toUpperCase());

            if(nbt.contains("cape_color", Tag.TAG_ANY_NUMERIC)){
                capeColor = nbt.getInt("cape_color");
            }
            else if(nbt.contains("cape_colors", Tag.TAG_INT_ARRAY)){
                int[] capeElements = nbt.getIntArray("cape_colors");
                List<Integer> newList = new ArrayList<>();
                for (int element : capeElements){
                    newList.add(element);
                }
                capeColors = newList;
            }
        }

        if(nbt.get("no_hood") != null){
            noHood = nbt.getBoolean("no_hood");
        }
        if(nbt.get("hood") != null){
            hood = HelmetAttachmentsME.valueOf(nbt.getString("hood").toUpperCase());
            if(nbt.get("hood_is_down") != null)
                isDown = nbt.getBoolean("hood_is_down");

            if(nbt.get("hood_color") != null){
                hoodColor = nbt.getInt("hood_color");
            }
            else if(nbt.contains("hood_colors", Tag.TAG_INT_ARRAY)){
                int[] hoodElements = nbt.getIntArray("hood_colors");
                List<Integer> newList = new ArrayList<>();
                for (int element : hoodElements){
                    newList.add(element);
                }
                hoodColors = newList;
            }
        }
    }

    public Tag getNbt(CompoundTag nbt) {
        nbt.putString("id", getItemIdentifier().toString());

        if(color != null)
            nbt.putInt("color", color);

        if(colors != null){
            int[] colors = this.colors.stream().mapToInt(Integer::intValue).toArray();

            if(colors != null)
                nbt.putIntArray("colors", colors);
        }

        if(noCape != null)
            nbt.putBoolean("no_cape", noCape);

        if(cape != null){
            nbt.putString("cape", cape.getName().toLowerCase());
            if(capeColor != null)
                nbt.putInt("cape_color", capeColor);

            if(capeColors != null){
                int[] capeColors = this.capeColors.stream().mapToInt(Integer::intValue).toArray();
                if(capeColors != null)
                    nbt.putIntArray("cape_colors", capeColors);
            }
        }

        if(noHood != null)
            nbt.putBoolean("no_hood", noHood);

        if(hood != null){
            nbt.putString("hood", hood.getName().toLowerCase());
            if(isDown != null)
                nbt.putBoolean("hood_is_down", isDown);
        }

        if(hoodColor != null)
            nbt.putInt("hood_color", hoodColor);

        if(hoodColors != null){
            int[] hoodColors = this.hoodColors.stream().mapToInt(Integer::intValue).toArray();
            if(hoodColors != null)
                nbt.putIntArray("hood_colors", hoodColors);
        }
        if(nbt.getAllKeys().size() == 1 && nbt.contains("id", Tag.TAG_STRING)){
            return StringTag.valueOf(nbt.getString("id"));
        }
        return nbt;
    }
}
