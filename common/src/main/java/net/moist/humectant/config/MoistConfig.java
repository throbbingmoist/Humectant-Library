package net.moist.humectant.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import dev.architectury.platform.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class MoistConfig {
	private transient final Gson GSON = new GsonBuilder()
		.setPrettyPrinting()
		.registerTypeAdapter(this.getClass(), (InstanceCreator<?>) type -> this)
		.create();
	private static final Logger LOG = LoggerFactory.getLogger("humectant-config");
	public transient final File ConfigFile;
	public transient final String ConfigFileName;

	protected String getConfigName() {return this.ConfigFileName;};

	public MoistConfig(String name) {
		ConfigFileName = name;
		ConfigFile = Platform.getConfigFolder().resolve(name + ".json").toFile();
	}

	public void initialize() {
		if (ConfigFile.exists()) {
			try (FileReader reader = new FileReader(ConfigFile)) {
				Object loaded = GSON.fromJson(reader, this.getClass());
				for (var field : this.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					field.set(this, field.get(loaded));
				}
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage());
			}
		}
		save();
	}

	public void save() {
		try (FileWriter writer = new FileWriter(ConfigFile)) {
			GSON.toJson(this, writer);
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage());
		}
	}

}
