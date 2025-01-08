package net.jukoz.me.resources.datas.races.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.util.NbtType;
import net.jukoz.me.utils.IdentifierUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttributeData {
    Map<Identifier, Double> datas;
    private static final List<Identifier> buffReverseIdentifiers = List.of(
            IdentifierUtil.getIdentifierFromString(EntityAttributes.GENERIC_SCALE.getIdAsString()),
            IdentifierUtil.getIdentifierFromString(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER.getIdAsString()),
            IdentifierUtil.getIdentifierFromString(EntityAttributes.GENERIC_BURNING_TIME.getIdAsString())
    );

    public AttributeData(NbtCompound compound) {
        if(compound == null) return;

        datas = new HashMap<>();
        NbtList attributes = compound.getList("datas", NbtType.COMPOUND);
        JsonParser jsonParser = new JsonParser();

        for(NbtElement element: attributes){
            JsonObject json = (JsonObject) jsonParser.parse(element.asString());
            String doubleRegex = "[^0-9.]";

            Identifier id = IdentifierUtil.getIdentifierFromString(json.get("id").getAsString());
            double value = Double.parseDouble(json.get("value").getAsString().replaceAll(doubleRegex, ""));

            datas.put(id, value);
        }
    }
    public AttributeData(HashMap<RegistryEntry<EntityAttribute>, Double> attributes) {
        datas = new HashMap<>();
        for(RegistryEntry<EntityAttribute> registryEntry : attributes.keySet()){
            Identifier id = IdentifierUtil.getIdentifierFromString(registryEntry.getIdAsString());
            datas.put(id, attributes.get(registryEntry));
        }
    }

    public NbtCompound getNbt() {
        NbtCompound nbt = new NbtCompound();
        NbtList list = new NbtList();

        for(Identifier id : datas.keySet()){
            NbtCompound compound = new NbtCompound();
            compound.putString("id", id.toString());
            compound.putDouble("value",  datas.get(id));
            list.add(compound);
        }
        nbt.put("datas", list);
        return nbt;
    }

    public void ApplyAll(LivingEntity entity){
        for(Identifier id : datas.keySet()){
            Optional<RegistryEntry.Reference<EntityAttribute>> attributeEntry =  Registries.ATTRIBUTE.getEntry(id);
            if(attributeEntry != null && attributeEntry.isPresent()){
                EntityAttributeInstance instance = entity.getAttributes().getCustomInstance(attributeEntry.get());
                if(instance != null){
                    instance.setBaseValue(datas.get(id));
                }
            }
        }
        entity.heal(entity.getMaxHealth());
    }

    public void ReverseAll(LivingEntity entity, DefaultAttributeContainer container){
        final DynamicRegistryManager registryManager = entity.getWorld().getRegistryManager();

        for(Identifier id : datas.keySet()){
            EntityAttribute attribute = registryManager.get(RegistryKeys.ATTRIBUTE).get(id);

            Optional<RegistryEntry.Reference<EntityAttribute>> attributeEntry =  Registries.ATTRIBUTE.getEntry(id);
            if(attribute != null && attributeEntry != null && attributeEntry.isPresent()){
                EntityAttributeInstance instance = entity.getAttributes().getCustomInstance(attributeEntry.get());
                if(instance != null){
                    if(container.has(attributeEntry.get()))
                        instance.setBaseValue(container.getValue(attributeEntry.get()));
                    else
                        instance.setBaseValue(attribute.getDefaultValue());
                }
            }
        }
        entity.heal(entity.getMaxHealth());
    }

    public double getCurrentValue(LivingEntity entity, Identifier id){
        final DynamicRegistryManager registryManager = entity.getWorld().getRegistryManager();
        EntityAttribute attribute = registryManager.get(RegistryKeys.ATTRIBUTE).get(id);

        Optional<RegistryEntry.Reference<EntityAttribute>> attributeEntry = Registries.ATTRIBUTE.getEntry(id);
        if(attribute != null && attributeEntry != null && attributeEntry.isPresent()){
            return entity.getAttributeValue(attributeEntry.get());
        }
        return -999.99;
    }

    public Map<Identifier, Double> getDatas(){
        return datas;
    }

    public boolean isBuffReversed(Identifier id){
        return buffReverseIdentifiers.contains(id);
    }

    public void reset(PlayerEntity player) {

    }
}
