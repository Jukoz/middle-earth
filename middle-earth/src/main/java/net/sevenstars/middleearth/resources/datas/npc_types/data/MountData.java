package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MountData {
    public static class Fields {
        public static final String ENTITY_TYPE = "entity_type";
        public static final String NPC_TYPE = "npc_type";
        public static final String ARMOR = "armor_id";
        public static final String PASSENGER_SLOTS = "passenger_slots";
    }

    public static final Codec<MountData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf(Fields.ENTITY_TYPE).forGetter(MountData::getEntityType),
            Identifier.CODEC.optionalFieldOf(Fields.NPC_TYPE).forGetter(MountData::getOptionalNpcType),
            ItemStack.CODEC.optionalFieldOf(Fields.ARMOR).forGetter(MountData::getOptionalArmor),
            MountPassengerSlotData.CODEC.listOf().fieldOf(Fields.PASSENGER_SLOTS).forGetter(MountData::getPassengerSlots)
    ).apply(instance, MountData::new));


    private Identifier entityType;
    private Identifier npcType;
    private ItemStack armor;
    private List<MountPassengerSlotData> passengerSlots;

    private MountData(
            Identifier entityType,
            Optional<Identifier> npcType,
            Optional<ItemStack> armor,
            List<MountPassengerSlotData> passengerSlots
    ) {
        this.entityType = entityType;
        this.npcType = npcType.orElse(null);
        this.armor = armor.orElse(null);
        this.passengerSlots = passengerSlots;
    }

    public MountData(EntityType<?> entity) {
        this.entityType = Registries.ENTITY_TYPE.getId(entity);
        this.armor = null;
    }

    public MountData(RegistryKey<NpcType> npcType){
        this.entityType = Registries.ENTITY_TYPE.getId(EntitiesME.NPC);
        this.npcType = npcType.getValue();
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
    private Optional<Identifier> getOptionalNpcType() {
        return Optional.ofNullable(npcType);
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
        if(this.entityType == null || owner.hasVehicle() || owner.hasPassengers())
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

            if (entity instanceof MobEntity mob) {
                mob.initialize(
                        world,
                        world.getLocalDifficulty(owner.getBlockPos()),
                        SpawnReason.EVENT,
                        null
                );
            }
            if(entity instanceof AbstractHorseEntity horse){
                horse.setTame(true);
                horse.setOwner(owner);
                if(horse instanceof AbstractBeastEntity beast){
                    beast.tameBeast(owner);
                }

            }

            owner.startRiding(entity, true);
            world.spawnEntity(entity);

            if(entity instanceof NpcEntity npc && npcType != null){
                npc.prepareNpcIdentifier(npcType);
                npc.prepare();
            }

            // Set other passengers
            passengerSlots.forEach(slot -> {
               LivingEntity passengerEntity = slot.createRandom(world, owner);
               if(passengerEntity != null)
                   passengerEntity.startRiding(entity, true);
            });

        }
    }
}
