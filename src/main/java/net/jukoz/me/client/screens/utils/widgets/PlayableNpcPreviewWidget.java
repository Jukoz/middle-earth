package net.jukoz.me.client.screens.utils.widgets;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollEntity;
import net.jukoz.me.entity.crab.CrabEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goose.GooseEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.snail.SnailEntity;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.Race;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.resources.datas.faction.utils.FactionNpcPreviewData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PlayableNpcPreviewWidget {
    private static final int MINIMAL_MARGIN = 4;
    private CrabEntity defaultEntity;
    private LongbeardDwarfEntity dwarfEntity;
    private GondorHumanEntity humanEntity;
    private MordorOrcEntity orcEntity;
    private GaladhrimElfEntity elfEntity;
    private ShireHobbitEntity hobbitEntity;
    private LivingEntity entity;
    private static final Quaternionf ENTITY_ROTATION;
    private static final Vector3f VECTOR;

    public PlayableNpcPreviewWidget(World world){
        humanEntity = new GondorHumanEntity(ModEntities.GONDORIAN_SOLDIER, world);
        dwarfEntity = new LongbeardDwarfEntity(ModEntities.LONGBEARD_SOLDIER, world);
        elfEntity = new GaladhrimElfEntity(ModEntities.LORIEN_SOLDIER, world);
        orcEntity = new MordorOrcEntity(ModEntities.MORDOR_ORC_SOLDIER, world);
        hobbitEntity = new ShireHobbitEntity(ModEntities.HOBBIT_CIVILIAN, world);

        defaultEntity = new CrabEntity(ModEntities.CRAB, world);
    }

    public void updateEntity(FactionNpcPreviewData data, Race race) {
        updateEntityRace(race);
        updateEquipment(data);
    }

    private void updateEquipment(FactionNpcPreviewData data){
        if(data == null) {
            this.entity = null;
            return;
        }
        if(this.entity == null) return;

        this.entity.bodyYaw = 210f;
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.prevHeadYaw = this.entity.getBodyYaw();

        this.entity.equipStack(EquipmentSlot.HEAD, data.get(EquipmentSlot.HEAD));
        this.entity.equipStack(EquipmentSlot.CHEST, data.get(EquipmentSlot.CHEST));
        this.entity.equipStack(EquipmentSlot.LEGS, data.get(EquipmentSlot.LEGS));
        this.entity.equipStack(EquipmentSlot.FEET, data.get(EquipmentSlot.FEET));
        this.entity.equipStack(EquipmentSlot.MAINHAND, data.get(EquipmentSlot.MAINHAND));
        this.entity.equipStack(EquipmentSlot.OFFHAND, data.get(EquipmentSlot.OFFHAND));
    }

    private void updateEntityRace(Race race) {
        this.entity =
                switch ( race )
                {
                    case Race.Human -> humanEntity;
                    case Race.Dwarf -> dwarfEntity;
                    case Race.Orc -> orcEntity;
                    case Race.Elf -> elfEntity;
                    case Race.Hobbit -> hobbitEntity;
                    default -> defaultEntity;
                };
    }

    public void drawCenteredAnchoredBottom(DrawContext context, int centerX, int endY) {
        float size = 40f;
        float x = centerX;
        float y = endY;

        DiffuseLighting.disableGuiDepthLighting();
        DiffuseLighting.disableForLevel();
        if(this.entity == null) return;
        InventoryScreen.drawEntity(context, x, y, size, VECTOR, ENTITY_ROTATION, (Quaternionf)null, this.entity);
    }

    static {
        VECTOR = new Vector3f();
        // Vanilla values from SmithingScreen
        ENTITY_ROTATION = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, 3.1415927F);
    }

    public void displayDefaultEntity() {
        this.entity = defaultEntity;
        this.entity.bodyYaw = 210f;
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.prevHeadYaw = this.entity.getBodyYaw();
    }
}
