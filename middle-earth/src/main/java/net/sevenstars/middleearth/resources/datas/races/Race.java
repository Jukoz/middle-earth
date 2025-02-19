package net.sevenstars.middleearth.resources.datas.races;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.TestNpcEntity;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.races.data.AttributeData;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Race {
    public static final Codec<Race> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(Race::getIdValue),
            Codec.STRING.fieldOf("type").forGetter(Race::getRaceTypeValue),
            NbtCompound.CODEC.fieldOf("attributes").forGetter(Race::getAttributeDatas),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_join").forGetter(Race::getJoinCommands),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_leave").forGetter(Race::getLeaveCommands),
            Codec.list(Codec.STRING).fieldOf("skin_patterns").forGetter(Race::getSkinPatterns),
            Codec.list(Codec.STRING).fieldOf("skin_materials").forGetter(Race::getSkinMaterials),
            Codec.list(Codec.STRING).fieldOf("eye_patterns").forGetter(Race::getEyePatterns),
            Codec.list(Codec.STRING).fieldOf("eye_materials").forGetter(Race::getEyeMaterials),
            Codec.BOOL.fieldOf("emissive_eyes").forGetter(Race::haveEmissiveEyes),
            Codec.list(Codec.STRING).fieldOf("hair_patterns").forGetter(Race::getHairPatterns),
            Codec.list(Codec.STRING).fieldOf("hair_materials").forGetter(Race::getHairMaterials)
    ).apply(instance, Race::new));

    private final Identifier id;
    private final RaceType raceType;
    private final String translatableKey;
    private final AttributeData attributeData;
    private List<String> joinCommands;
    private List<String> leaveCommands;
    private List<String> skinPatterns;
    private List<String> skinMaterials;
    private List<String> eyePatterns;
    private List<String> eyeMaterials;
    private boolean haveEmissiveEyes;
    private List<String> hairPatterns;
    private List<String> hairMaterials;

    public Race(String id, String raceTypeValue, NbtCompound attributes, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands, List<String> skinPatterns, List<String> skinMaterials, List<String> eyePatterns, List<String> eyeMaterials, Boolean haveEmissiveEyes, List<String> hairPatterns, List<String> hairMaterials){
        // Create id
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.translatableKey = "race.".concat(this.id.toTranslationKey());
        // Create model
        this.raceType = RaceType.valueOf(raceTypeValue.toUpperCase());
        // Attribute Datas
        this.attributeData = new AttributeData(attributes);
        // Join commands
        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(nbtCompound -> this.joinCommands.addAll(nbtCompound));
        // Leave commands
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(nbtCompound -> this.leaveCommands.addAll(nbtCompound));

        // Skin Textures
        this.skinPatterns = skinPatterns;
        this.skinMaterials = skinMaterials;
        // Eye Textures
        this.eyePatterns = eyePatterns;
        this.eyeMaterials = eyeMaterials;
        this.haveEmissiveEyes = haveEmissiveEyes;
        // Hair Textures
        this.hairPatterns = hairPatterns;
        this.hairMaterials = hairMaterials;
    }

    public Race(Identifier id, RaceType raceType, AttributeData attributeData, List<String> joinCommands, List<String> leaveCommands, List<String> skinPatterns, List<String> skinMaterials, List<String> eyePatterns, List<String> eyeMaterials, boolean haveEmissiveEyes, List<String> hairPatterns, List<String> hairMaterials) {
        this.id = id;
        this.raceType = raceType;
        this.translatableKey = "race.".concat(this.id.toTranslationKey());
        this.attributeData = attributeData;
        this.joinCommands = joinCommands;
        this.leaveCommands = leaveCommands;
        this.skinPatterns = skinPatterns;
        this.skinMaterials = skinMaterials;
        this.eyePatterns = eyePatterns;
        this.eyeMaterials = eyeMaterials;
        this.haveEmissiveEyes = haveEmissiveEyes;
        this.hairPatterns = hairPatterns;
        this.hairMaterials = hairMaterials;
    }

    public Identifier getId() {
        return id;
    }
    private String getIdValue() {
        return this.id.toString();
    }
    private String getRaceTypeValue() {
        return raceType.toString().toUpperCase();
    }
    private NbtCompound getAttributeDatas() {
        if(attributeData == null)
            return null;
        return attributeData.getNbt();
    }
    public Optional<List<String>> getJoinCommands() {
        if(this.joinCommands == null)
            return Optional.empty();
        return Optional.of(this.joinCommands);
    }
    public Optional<List<String>> getLeaveCommands() {
        if(this.leaveCommands == null)
            return Optional.empty();
        return Optional.of(this.leaveCommands);
    }
    public List<String> getSkinPatterns() {
        return this.skinPatterns;
    }
    public List<String> getSkinMaterials() {
        return this.skinMaterials;
    }

    public List<String> getEyePatterns() {
        return this.eyePatterns;
    }
    public List<String> getEyeMaterials() {
        return this.eyeMaterials;
    }
    public Boolean haveEmissiveEyes() {
        return haveEmissiveEyes;
    }
    public List<String> getHairPatterns() {
        return this.hairPatterns;
    }
    public List<String> getHairMaterials() {
        return this.hairMaterials;
    }

    public Text getFullName() {
        return Text.translatable(translatableKey);
    }

    public LivingEntity getModel(World world) {
        TestNpcEntity entity;
        switch (raceType){
            case RaceType.HUMAN:
                entity = null;
                break;
            case RaceType.DWARF:
                entity = null;
                break;
            case RaceType.HOBBIT:
                entity = null;
                break;
            case RaceType.ELF:
                entity = null;
                break;
            case RaceType.ORC:
                entity = null;
                break;
            case RaceType.URUK:
                entity = null;
                break;
            default:
                entity = null;
                break;
        }
        if(entity != null)
            entity.setAiDisabled(true);
        return entity;
    }

    public void applyAttributes(PlayerEntity playerEntity){
        attributeData.ApplyAll(playerEntity);
    }

    public void reverseAttributes(PlayerEntity playerEntity){
        AttributeData.reset(playerEntity);
    }

    public String getTranslatableKey() {
        return translatableKey;
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public void drawTooltip(LivingEntity entity, DrawContext context, TextRenderer renderer, int x, int y){
        List<Text> texts = new ArrayList<>();
        texts.add(getFullName());
        texts.add(Text.translatable("race_tooltip.me.attribute_header").formatted(Formatting.UNDERLINE));
        Map<Identifier, Double> datas = attributeData.getDatas();
        for(Identifier id : datas.keySet()){
            double value = datas.get(id);
            double difference = datas.get(id) - attributeData.getCurrentValue(entity, id);
            // Round them
            value = Math.round(value * 1000) / 1000.0;
            difference = Math.round(difference * 1000) / 1000.0;

            String differenceChar = (difference > 0) ? "+" : "";
            Formatting white = Formatting.WHITE;
            Formatting differenceColor = (difference < 0) ? Formatting.RED : (difference > 0) ? Formatting.GREEN : white;
            if(attributeData.isBuffReversed(id)){
                differenceColor = (difference < 0) ? Formatting.GREEN : (difference > 0) ? Formatting.RED : white;
            }
            MutableText rawValue = Text.literal(String.valueOf(value)).formatted(white);
            MutableText valueText = rawValue.append(Text.literal(" (").formatted(white).append(Text.literal(differenceChar + difference).formatted(differenceColor).append(Text.literal(") ").formatted(white))));
            texts.add(valueText.append(Text.translatable("attribute.name."+id.getPath()).formatted(Formatting.WHITE)));
        }
        context.drawTooltip(renderer, texts, x, y);
    }
}
