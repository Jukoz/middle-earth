package net.sevenstars.api.utils;

import net.minecraft.inventory.Inventory;
import net.sevenstars.api.entity.AbstractMountEntity;

public interface IServerPlayerEntity {
    void openMountInventory(AbstractMountEntity mount, Inventory inventory);
}
