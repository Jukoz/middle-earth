package net.sevenstars.ofhallsandheralds.persistentdatas.origin;

import com.mojang.serialization.Codec;
import net.sevenstars.api.persistentdata.AbstractPersistenceManager;

import java.nio.file.Path;

public class OriginPersistenceManager extends AbstractPersistenceManager<OriginPersistentData> {
    public OriginPersistenceManager(Path directory) {
        super(directory);
    }

    @Override
    protected Codec<OriginPersistentData> ObtenirCodec() {
        return OriginPersistentData.CODEC;
    }

    @Override
    protected OriginPersistentData createDefault() {
        return new OriginPersistentData();
    }
}