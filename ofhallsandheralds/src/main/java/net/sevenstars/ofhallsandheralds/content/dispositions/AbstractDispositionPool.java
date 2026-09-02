package net.sevenstars.ofhallsandheralds.content.dispositions;

import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;

public abstract class AbstractDispositionPool {
    public static Disposition create(String idPath) {
        return new Disposition(OfHallsAndHeralds.id(idPath));
    }
}
