package net.sevenstars.ofhallsandheralds.content.dispositions;

import net.minecraft.util.Identifier;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;

public abstract class AbstractDispositionPool {
    public static Disposition create(Identifier id) {
        return new Disposition(id);
    }
}
