package net.sevenstars.middleearth.item.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sevenstars.middleearth.gui.map.MapScreenController;

public class MiddleEarthMapItem extends Item {
    public MiddleEarthMapItem(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        MapScreenController controller = new MapScreenController(world, user);
        if(controller.open())
            return super.use(world, user, hand);
        return ActionResult.FAIL;
    }
}
