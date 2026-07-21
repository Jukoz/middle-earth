package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MountData {
    public static class Fields {
        public static final String ENTITY_TYPE = "entity_type";
        public static final String ARMOR = "armor_id";
        public static final String PASSENGER_SLOTS = "passenger_slots";
    }

    public static final Codec<MountData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf(MountData.Fields.ENTITY_TYPE).forGetter(MountData::getEntityType),
            ItemStack.CODEC.optionalFieldOf(Fields.ARMOR).forGetter(MountData::getOptionalArmor),
            MountPassengerSlotData.CODEC.listOf().fieldOf(Fields.PASSENGER_SLOTS).forGetter(MountData::getPassengerSlots)
    ).apply(instance, MountData::new));


    private Identifier entityType;
    private ItemStack armor;
    private List<MountPassengerSlotData> passengerSlots;

    private MountData(
            Identifier entityType,
            Optional<ItemStack> armor,
            List<MountPassengerSlotData> passengerSlots
    ) {
        this.entityType = entityType;
        this.armor = armor.orElse(null);
        this.passengerSlots = passengerSlots;
    }

    public MountData(EntityType<?> entity) {
        this.entityType = Registries.ENTITY_TYPE.getId(entity);
        this.armor = null;
    }

    public MountData withArmor(ItemStack armorItem){
        this.armor = armorItem;
        return this;
    }

    public MountData withArmor(Item armorItem){
        this.armor = armorItem.getDefaultStack();
        return this;
    }

    public MountData withColor(DyedColorComponent color){
        if(this.armor == null)
            return this;
        this.armor.set(DataComponentTypes.DYED_COLOR, color);
        return this;
    }

    private Identifier getEntityType() {
        return entityType;
    }
    private Optional<ItemStack> getOptionalArmor() {
        return Optional.ofNullable(armor);
    }


    private List<MountPassengerSlotData> getPassengerSlots() {
        if(passengerSlots == null)
            return new ArrayList<>();
        return passengerSlots;
    }

    public MountData withPassengerSlots(MountPassengerSlotData... passengerSlots) {
        this.passengerSlots = Arrays.asList(passengerSlots);
        return this;
    }

    public void createEntity(ServerWorld world, LivingEntity owner) {
        if(this.entityType == null || owner.hasVehicle())
            return;
        EntityType<?> type = Registries.ENTITY_TYPE.get(this.entityType);
        var notLiving = type.create(world, SpawnReason.JOCKEY);
        if(notLiving == null)
            return;
        if(notLiving instanceof LivingEntity entity){
            entity.setPosition(owner.getPos());
            entity.equipStack(EquipmentSlot.SADDLE, Items.SADDLE.asItem().getDefaultStack());
            if(armor != null)
                entity.equipStack(EquipmentSlot.BODY, this.armor);

            if(entity instanceof AbstractHorseEntity horse){
                horse.initialize(
                        world,
                        world.getLocalDifficulty(owner.getBlockPos()),
                        SpawnReason.EVENT,
                        null
                );
                horse.setTame(true);
                horse.setOwner(owner);
                if(horse instanceof AbstractBeastEntity beast){
                    beast.tameBeast(owner);
                }

            }
            world.spawnEntity(entity);
            owner.startRiding(entity, true);
            // Set other passengers
            passengerSlots.forEach(slot -> {
               LivingEntity passengerEntity = slot.createRandom(world, owner);
               if(passengerEntity != null)
                   passengerEntity.startRiding(entity, true);
            });

        }
    }
}
