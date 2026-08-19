package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcTypeLookup;
import org.jetbrains.annotations.Nullable;

public class NpcEntityBuilder {
    NpcEntity entity;
    Level world;

    public NpcEntityBuilder(Level world, @Nullable BlockPos pos){
        this.world = world;
        this.entity = new NpcEntity(EntitiesME.NPC, this.world);
        if(pos != null)
            this.entity.setPos(pos.getCenter());
    }

    public NpcEntityBuilder withNpcType(ResourceLocation identifier) {
        this.entity.prepareNpcIdentifier(identifier);
        return this;
    }

    public NpcEntity build(){
        this.entity.tryToInitializeData();
        return this.entity;
    }

    public NpcEntity forceBuild() {
        this.entity.tryToInitializeData();
        return this.entity;
    }

}
