package net.moist.humectant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Humectant {
	public static final String MOD_ID = "humectant";
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static void init() {
		// Write common init code here.
	}
}
