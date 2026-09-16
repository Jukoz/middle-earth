package net.sevenstars.ofhillsanddells;

import net.sevenstars.api.AbstractModInitializer;

public class OfHillsAndDells extends AbstractModInitializer {
	public OfHillsAndDells() {
		initialize("ofhillsanddells", true);
	}

	@Override
	public void onInitialize() {
		registerAll();
	}

	private void registerAll() {
	}
}
