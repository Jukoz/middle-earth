package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public LivingEntity createRandom(ServerWorld serverWorld, LivingEntity owner) {
        List<MountPassengerData> weightedPassengers = new ArrayList<>();
        for (MountPassengerData weightedPassenger : passengers) {
            for(int i = 0; i < weightedPassenger.getWeight(1); i++){
                weightedPassengers.add(weightedPassenger);
            }
        }

        if(weightedPassengers.isEmpty())
            return null;
        Random random = new Random();

        MountPassengerData data =  weightedPassengers.get(random.nextInt(weightedPassengers.size()));
        if(data.isDiscarded(random))
            return null;
        return data.createEntity(serverWorld, owner);
    }
}
