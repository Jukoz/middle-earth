package net.sevenstars.middleearth.block.utils;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;

public interface ExtendedMenuProviderME extends MenuProvider {
    void writeOpeningData(RegistryFriendlyByteBuf buffer);

    static void open(Player player, ExtendedMenuProviderME provider) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(provider, provider::writeOpeningData);
        }
    }
}
