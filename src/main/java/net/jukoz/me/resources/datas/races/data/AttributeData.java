package net.jukoz.me.resources.datas.races.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.util.NbtType;
import net.jukoz.me.utils.IdentifierUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AttributeData {
    Map<Identifier, Double> datas;
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

    public void ReverseAll(LivingEntity entity){
        final DynamicRegistryManager registryManager = entity.getWorld().getRegistryManager();

        for(Identifier id : datas.keySet()){
            EntityAttribute attribute = registryManager.get(RegistryKeys.ATTRIBUTE).get(id);

            Optional<RegistryEntry.Reference<EntityAttribute>> attributeEntry =  Registries.ATTRIBUTE.getEntry(id);
            if(attributeEntry != null && attributeEntry.isPresent()){
                EntityAttributeInstance instance = entity.getAttributes().getCustomInstance(attributeEntry.get());
                if(instance != null){
                    instance.setBaseValue(attribute.getDefaultValue());
                }
            }
        }
    }
}
