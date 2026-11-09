package me.boomber.seedgenfix.neoforge;

import me.boomber.seedgenfix.SeedGenPostProcessFix;
import net.neoforged.fml.common.Mod;

@Mod("seedgen_postprocess_fix")
public final class NeoForgeEntrypoint {
	public NeoForgeEntrypoint() {
		SeedGenPostProcessFix.init();
	}
}
