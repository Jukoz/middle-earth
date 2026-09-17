package net.sevenstars.ofhallsandheralds.persistentdatas.reputation;

import com.mojang.serialization.Codec;
import net.sevenstars.api.persistentdata.AbstractPersistenceManager;

import java.nio.file.Path;

public class ReputationPersistenceManager extends AbstractPersistenceManager<reputationPersistentData> {
    public ReputationPersistenceManager(Path directory) {
        super(directory);
    }

    @Override
    protected Codec<reputationPersistentData> ObtenirCodec() {
        return reputationPersistentData.CODEC;
    }

    @Override
    protected reputationPersistentData createDefault() {
        return new reputationPersistentData();
    }
}