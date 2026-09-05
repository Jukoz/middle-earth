package net.sevenstars.ofhallsandheralds.persistentdatas.reputation;

import com.mojang.serialization.Codec;
import net.sevenstars.api.persistentdata.AbstractPersistenceManager;

import java.nio.file.Path;

public class ReputationPersistenceManager extends AbstractPersistenceManager<ReputationPersistentData> {
    public ReputationPersistenceManager(Path directory) {
        super(directory);
    }

    @Override
    protected Codec<ReputationPersistentData> ObtenirCodec() {
        return ReputationPersistentData.CODEC;
    }

    @Override
    protected ReputationPersistentData createDefault() {
        return new ReputationPersistentData();
    }
}