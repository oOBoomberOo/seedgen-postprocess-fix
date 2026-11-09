package me.boomber.seedgenfix.fabric;

import me.boomber.seedgenfix.SeedGenPostProcessFix;
import net.fabricmc.api.ModInitializer;

public final class FabricEntrypoint implements ModInitializer {
	@Override
	public void onInitialize() {
		SeedGenPostProcessFix.init();
	}
}
