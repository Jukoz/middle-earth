package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class MountPassengerSlotData {
    public static class Fields {
        public static final String PASSENGERS = "passengers";
    }

    public static final Codec<MountPassengerSlotData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MountPassengerData.CODEC.listOf().fieldOf(MountPassengerSlotData.Fields.PASSENGERS).forGetter(MountPassengerSlotData::getPassengers)
    ).apply(instance, MountPassengerSlotData::new));

    private final List<MountPassengerData> passengers;

    private MountPassengerSlotData(List<MountPassengerData> mountPassengerData) {
        this.passengers = mountPassengerData;
    }

    public MountPassengerSlotData(
            MountPassengerData... passengerDatas
    ) {
        this.passengers = List.of(passengerDatas);
    }

    private List<MountPassengerData> getPassengers() {
        return passengers;
    }

    public LivingEntity createRandom(ServerLevel serverWorld, LivingEntity owner) {
        int totalWeight = 0;
        for (MountPassengerData passenger : passengers) {
            totalWeight += Math.max(0, passenger.getWeight(1));
        }
        if(totalWeight <= 0)
            return null;

        Random random = new Random();
        int selectedWeight = random.nextInt(totalWeight);
        MountPassengerData data = null;
        for (MountPassengerData passenger : passengers) {
            int weight = Math.max(0, passenger.getWeight(1));
            if (selectedWeight < weight) {
                data = passenger;
                break;
            }
            selectedWeight -= weight;
        }
        if (data == null) {
            return null;
        }
        if(data.isDiscarded(random))
            return null;
        return data.createEntity(serverWorld, owner);
    }
}
