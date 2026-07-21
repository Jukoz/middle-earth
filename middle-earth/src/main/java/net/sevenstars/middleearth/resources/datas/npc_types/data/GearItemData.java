package net.sevenstars.middleearth.resources.datas.npc_types.data;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
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
    public GearItemData(Identifier itemIdentifier) {
        this.item = getItemFromId(itemIdentifier);
    }

    public static GearItemData create() {
        return new GearItemData();
    }
    public static GearItemData create(Item item) {
        return new GearItemData(item);
    }
    public static GearItemData create(Identifier itemIdentifier) {
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
            MiddleEarth.LOGGER.logError("NpcGearItemData:: [%s - %s] Cannot set the hood state to %s, it was forced to %s!".formatted(this.item.getName(), hood.getName(), isDown, this.isDown));
        }
        return this;
    }

    private static Item getItemFromId(Identifier itemId){
        return Registries.ITEM.get(itemId);
    }

    private static Identifier getIdentifierFromItem(Item item){
        return Registries.ITEM.getId(item);
    }

    private int getRandomColor(List<Integer> listToFetch) {
        if(listToFetch != null){
            int max = listToFetch.size() - 1;
            return listToFetch.get(Random.create().nextBetween(0, max));
        }
        return Color.PINK.getRGB();
    }

    public Integer getColor(){
        return this.color;
    }
    public Identifier getItemIdentifier() {
        return getIdentifierFromItem(this.item);
    }

    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(this.item);

        if(this.color != null){
            List<TagKey<Item>> tags = itemStack.streamTags().toList();
            if(tags.contains(ItemTags.DYEABLE))
                itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(this.color));
            else if(itemStack.isIn(ItemTags.DYEABLE))
                itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(this.color));
        } else if(this.colors != null){
            List<TagKey<Item>> tags = itemStack.streamTags().toList();
            if(tags.contains(ItemTags.DYEABLE))
                itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(getRandomColor(colors)));
            else if(itemStack.isIn(ItemTags.DYEABLE))
                itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(getRandomColor(colors)));
        }
        if(this.noCape != null && this.noCape && itemStack.getComponents().contains(DataComponentTypesME.BACK_ATTACHMENT_DATA)){
            itemStack.remove(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        } else if (cape != null)
            if(capeColor != null)
                itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachmentWithColor(cape, capeColor));
            else if(capeColors != null)
                itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachmentWithColor(cape, getRandomColor(capeColors)));
            else
                itemStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(cape));

        if(this.noHood != null && this.noHood && itemStack.getComponents().contains(DataComponentTypesME.HELMET_ATTACHMENT_DATA)){
            itemStack.remove(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        } else if(hood != null){
            boolean hoodState = false;
            if(this.hood.getConstantState() != null){
                this.isDown = this.hood.getConstantState() == HelmetAttachmentsStatesME.DOWN;
                hoodState = this.isDown;
                MiddleEarth.LOGGER.logError("NpcGearItemData:: [%s - %s] Cannot set the hood state to %s, it was forced to %s!".formatted(this.item.getName(), hood.getName(), isDown, this.isDown));
            } else if(isDown == null){
                hoodState = Math.random() >= 0.5;
            } else {
                hoodState = this.isDown;
            }

            if(hoodColor != null)
                itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(hoodState, hood, hoodColor));
            else if(hoodColors != null)
                itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(hoodState, hood, getRandomColor(hoodColors)));
            else
                itemStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(hoodState, hood, DyedColorComponent.DEFAULT_COLOR));
        }
        return itemStack;
    }

    public GearItemData(NbtCompound nbt){
        this(Identifier.of(nbt.getString("id").get()));

        if(nbt.get("color") != null){
            color = nbt.getInt("color").get();
        }
        if(nbt.get("colors") != null && nbt.getIntArray("colors").isPresent()){
            int[] list = nbt.getIntArray("colors").get();
            List<Integer> newColors = new ArrayList<>();
            for (int j : list) {
                newColors.add(j);
            }
            colors = newColors;
        }
        if(nbt.get("no_cape") != null){
            noCape = nbt.getBoolean("no_cape").get();
        }
        if(nbt.get("cape") != null){
            cape = BackAttachmentsME.valueOf(nbt.getString("cape").get().toUpperCase());

            if(nbt.getInt("cape_color").isPresent()){
                capeColor = nbt.getInt("cape_color").get();
            }
            else if(nbt.get("cape_colors") != null && nbt.get("cape_colors").asNbtList().isPresent()){
                var capeElements = nbt.get("cape_colors").asNbtList().get();
                List<Integer> newList = new ArrayList<>();
                for (var element : capeElements){
                    newList.add(element.asInt().get());
                }
                capeColors = newList;
            }
        }

        if(nbt.get("no_hood") != null){
            noHood = nbt.getBoolean("no_hood").get();
        }
        if(nbt.get("hood") != null){
            hood = HelmetAttachmentsME.valueOf(nbt.getString("hood").get().toUpperCase());
            if(nbt.get("hood_is_down") != null)
                isDown = nbt.getBoolean("hood_is_down").get();

            if(nbt.get("hood_color") != null){
                hoodColor = nbt.getInt("hood_color").get();
            }
            else if(nbt.get("hood_colors") != null && nbt.get("hood_colors").asNbtList().isPresent()){
                var hoodElements = nbt.get("hood_colors").asNbtList().get();
                List<Integer> newList = new ArrayList<>();
                for (var element : hoodElements){
                    newList.add(element.asInt().get());
                }
                hoodColors = newList;
            }
        }
    }

    public NbtElement getNbt(NbtCompound nbt) {
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
        if(nbt.getKeys().size() == 1 && nbt.getString("id").isPresent()){
            return NbtString.of(nbt.getString("id").get());
        }
        return nbt;
    }
}
