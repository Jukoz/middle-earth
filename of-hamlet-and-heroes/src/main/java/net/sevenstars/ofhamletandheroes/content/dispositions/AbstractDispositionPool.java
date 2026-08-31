package net.sevenstars.ofhamletandheroes.content.dispositions;

import net.sevenstars.ofhamletandheroes.OfHamletAndHeroes;
import net.sevenstars.ofhamletandheroes.dtos.disposition.Disposition;

public abstract class AbstractDispositionPool {
    public static Disposition create(String idPath) {
        return new Disposition(OfHamletAndHeroes.id(idPath));
    }
}
