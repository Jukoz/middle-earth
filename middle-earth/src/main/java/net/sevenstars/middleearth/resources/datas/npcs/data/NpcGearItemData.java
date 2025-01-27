package net.sevenstars.middleearth.resources.datas.npcs.data;

import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CapeDataComponent;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HoodDataComponent;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.sevenstars.middleearth.item.utils.armor.hoods.ModHoodStates;
import net.sevenstars.middleearth.item.utils.armor.hoods.ModHoods;
import net.sevenstars.middleearth.recipe.ModTags;
import net.sevenstars.middleearth.utils.LoggerUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NpcGearItemData {
    private final static String NO_HOOD_KEY = "no_hood";
    private final static String NO_CAPE_KEY = "no_cape";
    private Item item;
    private Integer color = null;
    private List<Integer> colors = null;
    private Integer weight = null;
    private ModCapes cape = null;
    private Integer capeColor = null;
    private List<Integer> capeColors = null;
    private ModHoods hood = null;
    private Integer hoodColor = null;
    private List<Integer> hoodColors = null;
    private Boolean isDown = null;
    private Boolean noCape = null;
    private Boolean noHood = null;

    public NpcGearItemData() {
        this.item = Items.AIR;
    }
    public NpcGearItemData(Item item) {
        this.item = item;
    }
    public NpcGearItemData(Identifier itemIdentifier) {
        this.item = getItemFromId(itemIdentifier);
    }

    public static NpcGearItemData create() {
        return new NpcGearItemData();
    }
    public static NpcGearItemData create(Item item) {
        return new NpcGearItemData(item);
    }
    public static NpcGearItemData create(Identifier itemIdentifier) {
        return new NpcGearItemData(itemIdentifier);
    }
    public NpcGearItemData withWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public NpcGearItemData withColor(int color) {
        this.color = color;
        return this;
    }

    public NpcGearItemData withColors(List<Integer> colors) {
        this.colors = colors;
        return this;
    }

    public NpcGearItemData withoutCape() {
        this.noCape = true;
        return this;
    }
    public NpcGearItemData withCape(ModCapes cape, int color) {
        capeColor = color;
        return withCape(cape);
    }
    public NpcGearItemData withCape(ModCapes cape, List<Integer> colors) {
        this.capeColors = colors;
        return withCape(cape);
    }

    public NpcGearItemData withCape(ModCapes cape) {
        if(cape == null){
            this.noCape = true;
        }
        this.cape = cape;
        return this;
    }

    public NpcGearItemData withoutHood() {
        this.noHood = true;
        return this;
    }
    public NpcGearItemData withHood(ModHoods hood, int color) {
        hoodColor = color;
        return withHood(hood);
    }
    public NpcGearItemData withHood(ModHoods hood, List<Integer> colors) {
        hoodColors = colors;
        return withHood(hood);
    }
    public NpcGearItemData withHood(ModHoods hood) {
        if(hood == null){
            this.noHood = true;
        }
        this.hood = hood;
        if(this.hood.getConstantState() != null)
            this.isDown = this.hood.getConstantState() == ModHoodStates.DOWN;
        else
            this.isDown = null;
        return this;
    }

    public NpcGearItemData withHood(ModHoods hood, boolean isDown) {
        withHood(hood);
        if(this.hood.getConstantState() == null) {
            this.isDown = isDown;
        }
        if(this.isDown != isDown){
            LoggerUtil.logError("NpcGearItemData:: [%s - %s] Cannot set the hood state to %s, it was forced to %s!".formatted(this.item.getName(), hood.getName(), isDown, this.isDown));
        }
        return this;
    }

    private static Item getItemFromId(Identifier itemId){
        return Registries.ITEM.get(itemId);
    }

    private static Identifier getIdentifierFromItem(Item item){
        return Registries.ITEM.getId(item);
    }

    public ItemStack getItem(){
        ItemStack itemStack = new ItemStack(this.item);

        if(this.color != null){
            List<TagKey<Item>> tags = itemStack.streamTags().toList();
            if(tags.contains(ItemTags.DYEABLE))
                itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(this.color, true));
            else if(itemStack.isIn(ModTags.DYEABLE))
                itemStack.set(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(this.color));
        } else if(this.colors != null){
            List<TagKey<Item>> tags = itemStack.streamTags().toList();
            if(tags.contains(ItemTags.DYEABLE))
                itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(getRandomColor(colors), true));
            else if(itemStack.isIn(ModTags.DYEABLE))
                itemStack.set(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(getRandomColor(colors)));
        }
        if(this.noCape != null && this.noCape && itemStack.getComponents().contains(ModDataComponentTypes.CAPE_DATA)){
            itemStack.remove(ModDataComponentTypes.CAPE_DATA);
        } else if (cape != null)
            if(capeColor != null)
                itemStack.set(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCapeWithColor(cape, capeColor));
            else if(capeColors != null){
                itemStack.set(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCapeWithColor(cape, getRandomColor(capeColors)));
            }
            else
                itemStack.set(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(cape));
        if(this.noHood != null && this.noHood && itemStack.getComponents().contains(ModDataComponentTypes.HOOD_DATA)){
            itemStack.remove(ModDataComponentTypes.HOOD_DATA);
        } else if(hood != null){
            boolean hoodState = false;
            if(this.hood.getConstantState() != null){
                this.isDown = this.hood.getConstantState() == ModHoodStates.DOWN;
                hoodState = this.isDown;
                LoggerUtil.logError("NpcGearItemData:: [%s - %s] Cannot set the hood state to %s, it was forced to %s!".formatted(this.item.getName(), hood.getName(), isDown, this.isDown));
            } else if(isDown == null){
                hoodState = Math.random() >= 0.5;
            } else {
                hoodState = this.isDown;
            }
            int newHoodColor = CustomDyeableDataComponent.DEFAULT_COLOR;
            if(hoodColor != null)
                newHoodColor = hoodColor;
            if(hoodColors != null)
                newHoodColor = getRandomColor(hoodColors);
            itemStack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(hoodState, hood, newHoodColor));
        }
        return itemStack;
    }

    private int getRandomColor(List<Integer> listToFetch) {
        if(listToFetch != null){
            int max = listToFetch.size() - 1;
            return listToFetch.get(Random.create().nextBetween(0, max));
        }
        return Color.PINK.getRGB();
    }

    public Integer getWeight(){
        Integer weight = this.weight;
        if(weight == null)
            weight = 1;
        return weight;
    }
    public Integer getColor(){
        return this.color;
    }
    public Identifier getItemIdentifier() {
        return getIdentifierFromItem(this.item);
    }

    public static NbtCompound createNbt(NpcGearItemData gearItemData){
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", gearItemData.getItemIdentifier().toString());

        Integer weight = gearItemData.weight;
        if(weight != null)
            nbt.putInt("weight", weight);

        Integer color = gearItemData.color;
        if(color != null)
            nbt.putInt("color", color);

        List<Integer> colors = gearItemData.colors;
        if(colors != null)
            nbt.putIntArray("colors", colors);

        Boolean noCape = gearItemData.noCape;
        if(noCape != null)
            nbt.putBoolean("no_cape", noCape);

        if(gearItemData.cape != null){
            nbt.putString("cape", gearItemData.cape.getName().toLowerCase());
        }

        Integer capeColor = gearItemData.capeColor;
        if(capeColor != null)
            nbt.putInt("cape_color", capeColor);

        List<Integer> capeColors = gearItemData.capeColors;
        if(capeColors != null)
            nbt.putIntArray("cape_colors", capeColors);

        Boolean noHood = gearItemData.noHood;
        if(noHood != null)
            nbt.putBoolean("no_hood", noHood);

        if(gearItemData.hood != null){
            nbt.putString("hood", gearItemData.hood.getName().toLowerCase());
            if(gearItemData.isDown != null)
                nbt.putBoolean("hood_is_down", gearItemData.isDown);
        }

        Integer hoodColor = gearItemData.hoodColor;
        if(hoodColor != null)
            nbt.putInt("hood_color", hoodColor);

        List<Integer> hoodColors = gearItemData.hoodColors;
        if(hoodColors != null)
            nbt.putIntArray("hood_colors", hoodColors);

        return nbt;
    }

    public static NpcGearItemData readNbt(NbtCompound nbt){
        Identifier id = Identifier.of(nbt.getString("id"));
        NpcGearItemData npcGearItemData = NpcGearItemData.create(id);
        if(nbt.get("weight") != null){
            npcGearItemData.weight = nbt.getInt("weight");
        }
        if(nbt.get("color") != null){
            npcGearItemData.color = nbt.getInt("color");
        }
        if(nbt.get("colors") != null){
            int[] list = nbt.getIntArray("colors");
            List<Integer> newColors = new ArrayList<>();
            for(int i = 0; i < list.length; i++){
                newColors.add(list[i]);
            }
            npcGearItemData.colors = newColors;
        }
        if(nbt.get("no_cape") != null){
            npcGearItemData.noCape = nbt.getBoolean("no_cape");
        }
        if(nbt.get("cape") != null){
            npcGearItemData.cape = ModCapes.valueOf(nbt.getString("cape").toUpperCase());
        }
        if(nbt.get("cape_color") != null){
            npcGearItemData.capeColor = nbt.getInt("cape_color");
        }
        if(nbt.get("cape_colors") != null){
            int[] list = nbt.getIntArray("cape_colors");
            List<Integer> newColors = new ArrayList<>();
            for(int i = 0; i < list.length; i++){
                newColors.add(list[i]);
            }
            npcGearItemData.capeColors = newColors;
        }

        if(nbt.get("no_hood") != null){
            npcGearItemData.noHood = nbt.getBoolean("no_hood");
        }
        if(nbt.get("hood") != null){
            npcGearItemData.hood = ModHoods.valueOf(nbt.getString("hood").toUpperCase());
            if(nbt.get("hood_is_down") != null)
                npcGearItemData.isDown = nbt.getBoolean("hood_is_down");
        }
        if(nbt.get("hood_color") != null){
            npcGearItemData.hoodColor = nbt.getInt("hood_color");
        }
        if(nbt.get("hood_colors") != null){
            int[] list = nbt.getIntArray("hood_colors");
            List<Integer> newColors = new ArrayList<>();
            for(int i = 0; i < list.length; i++){
                newColors.add(list[i]);
            }
            npcGearItemData.hoodColors = newColors;
        }
        return npcGearItemData;
    }
}
