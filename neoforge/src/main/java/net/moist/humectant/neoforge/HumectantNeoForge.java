package net.moist.humectant.neoforge;

import net.moist.humectant.Humectant;
import net.neoforged.fml.common.Mod;

@Mod(Humectant.MOD_ID)
public final class HumectantNeoForge {
	public HumectantNeoForge() {
		// Run our common setup.
		Humectant.init();
	}
}
