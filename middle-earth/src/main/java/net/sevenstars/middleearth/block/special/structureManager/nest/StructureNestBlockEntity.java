package net.sevenstars.middleearth.block.special.structureManager.nest;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StructureNestBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_nest";

    protected Identifier nestIdentifier;

    public StructureNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_NEST, pos, state);
        this.nestIdentifier = Identifier.of(MiddleEarth.MOD_ID, "mannimarcus_mother");
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return nestIdentifier;
    }

    public Text getDisplayName() {
        return Text.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StructureNestScreenHandler(syncId, playerInventory, nestIdentifier);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Optional<Identifier> selected = view.read("nest_identifer", Identifier.CODEC);
        selected.ifPresent(identifier -> this.nestIdentifier = identifier);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put("nest_identifer", Identifier.CODEC, this.nestIdentifier);
    }
}
