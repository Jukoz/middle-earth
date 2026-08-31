package net.sevenstars.ofheraldsandhamlets.content.dispositions;

import net.sevenstars.ofheraldsandhamlets.OfHeraldsAndHamlets;
import net.sevenstars.ofheraldsandhamlets.dtos.disposition.Disposition;

public abstract class AbstractDispositionPool {
    public static Disposition create(String idPath) {
        return new Disposition(OfHeraldsAndHamlets.id(idPath));
    }
}
