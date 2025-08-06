package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.resources.datas.npcs.NpcDataLookup;

public class NpcEntityBuilder {
    NpcEntity entity;
    World world;

    public NpcEntityBuilder(World world, BlockPos pos){
        this.world = world;
        this.entity = new NpcEntity(ModEntities.NPC, this.world);
        this.entity.setPosition(pos.toCenterPos());
    }

    public NpcEntityBuilder withNpcData(Identifier identifier){
        this.entity.setNpcData(NpcDataLookup.getNpcData(this.world, identifier));
        return this;
    }

    public NpcEntity build(){
        this.entity.apply();
        return this.entity;
    }
}
