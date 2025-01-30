package net.sevenstars.middleearth.resources.datas.races.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.util.NbtType;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import net.minecraft.entity.LivingEntity;
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
    HashMap<Identifier, Double> datas;
    private static final List<Identifier> buffReverseIdentifiers = List.of(
            IdentifierUtil.getIdentifierFromString(EntityAttributes.SCALE.getIdAsString()),
            IdentifierUtil.getIdentifierFromString(EntityAttributes.FALL_DAMAGE_MULTIPLIER.getIdAsString()),
            IdentifierUtil.getIdentifierFromString(EntityAttributes.BURNING_TIME.getIdAsString())
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
    }

    public double getCurrentValue(LivingEntity entity, Identifier id){
        final DynamicRegistryManager registryManager = entity.getWorld().getRegistryManager();
        if(registryManager.getOptional(RegistryKeys.ATTRIBUTE).isEmpty()){
            return 1.0;
        }
        EntityAttribute attribute = registryManager.getOptional(RegistryKeys.ATTRIBUTE).get().get(id);

        Optional<RegistryEntry.Reference<EntityAttribute>> attributeEntry = Registries.ATTRIBUTE.getEntry(id);
        if(attribute != null && attributeEntry != null && attributeEntry.isPresent()){
            return entity.getAttributeBaseValue(attributeEntry.get());
        }
        return -999.99;
    }

    public Map<Identifier, Double> getDatas(){
        return datas;
    }

    public boolean isBuffReversed(Identifier id){
        return buffReverseIdentifiers.contains(id);
    }

    private static final HashMap<Identifier, Double> defaultAttributes = new HashMap<>(){{
        put(Identifier.of("armor"), 0.0);
        put(Identifier.of("armor_toughness"), 0.0);
        put(Identifier.of("attack_damage"), 0.9);
        put(Identifier.of("attack_knockback"), 0.0);
        put(Identifier.of("attack_speed"), 4.0);
        put(Identifier.of("burning_time"), 1.0);
        put(Identifier.of("explosion_knockback_resistance"), 0.0);
        put(Identifier.of("fall_damage_multiplier"), 1.0);
        put(Identifier.of("gravity"), 0.08);
        put(Identifier.of("jump_strength"), 0.41999998688697815);
        put(Identifier.of("knockback_resistance"), 0.0);
        put(Identifier.of("luck"), 0.0);
        put(Identifier.of("max_absorption"), 0.0);
        put(Identifier.of("max_health"), 20.0);
        put(Identifier.of("movement_efficiency"), 0.0);
        put(Identifier.of("movement_speed"), 0.10000000149011612);
        put(Identifier.of("oxygen_bonus"), 0.0);
        put(Identifier.of("oxygen_bonus"), 0.0);
        put(Identifier.of("safe_fall_distance"), 3.0);
        put(Identifier.of("scale"), 1.0);
        put(Identifier.of("step_height"), 0.6);
        put(Identifier.of("water_movement_efficiency"), 0.0);

        put(Identifier.of("block_break_speed"), 1.0);
        put(Identifier.of("block_interaction_range"), 	4.5);
        put(Identifier.of("entity_interaction_range"), 3.0);
        put(Identifier.of("mining_efficiency"), 0.0);
        put(Identifier.of("sneaking_speed"), 0.3);
        put(Identifier.of("submerged_mining_speed"), 0.2);
        put(Identifier.of("sweeping_damage_ratio"), 0.0);
    }};

    public static boolean reset(PlayerEntity player){
        return apply(player, defaultAttributes);
    }

    public static boolean apply(PlayerEntity player, HashMap<Identifier, Double> attributeList){
        for(Identifier id : defaultAttributes.keySet()){
            Optional<RegistryEntry.Reference<EntityAttribute>> attributeEntry =  Registries.ATTRIBUTE.getEntry(id);
            if(attributeEntry != null && attributeEntry.isPresent()){
                EntityAttributeInstance instance = player.getAttributes().getCustomInstance(attributeEntry.get());
                if(instance != null){
                    instance.setBaseValue(defaultAttributes.get(id));
                }
            }
        }
        return  true;
    }
}
