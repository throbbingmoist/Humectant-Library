package net.moist.humectant.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.moist.humectant.Humectant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.moist.humectant.Humectant.GSON;

public abstract class ModelGenerator {

	public ModelGenerator(Consumer<BlockStateGenerator> blockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput, Block block) {
		this.generateBlock(modelOutput, block);
		this.generateState(blockStateOutput, block);
	}

	public void generateBlock(BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput, Block block) {
		HashMap<String, JsonObject> models = this.generateModelJSON(block);
		JsonObject item = new JsonObject();
		item.addProperty("parent",block.arch$registryName().getNamespace()+":block/"+block.arch$registryName().getPath());

		modelOutput.accept(ResourceLocation.fromNamespaceAndPath(block.arch$registryName().getNamespace(), "item/"+block.arch$registryName().getPath()), () -> item);

		for (String key : models.keySet()) {
			models.get(key).add("display",this.getDisplayTransforms());
			modelOutput.accept(ResourceLocation.fromNamespaceAndPath(block.arch$registryName().getNamespace(),"block/"+key), () -> models.get(key));
		}
	}
	public void generateState(Consumer<BlockStateGenerator> blockStateOutput, Block block) {
		blockStateOutput.accept(new BlockStateGenerator() {
			@Override public Block getBlock() {return block;}
			@Override public JsonElement get() {return generateBlockStateJSON(block);}
		});
	}

	public JsonObject generateBlockStateJSON(Block block) {
		return this.generateBlockStateJSON(block.arch$registryName().getNamespace(), block.arch$registryName().getPath());
	};
	public HashMap<String, JsonObject> generateModelJSON(Block block) {
		return this.generateModelJSON(new HashMap<>(), block.arch$registryName().getNamespace(), block.arch$registryName().getPath());
	};


	public abstract JsonObject generateBlockStateJSON(String namespace, String catwalk_identifier);
	public JsonObject getDisplayTransforms() {
		JsonObject display = new JsonObject();
		JsonElement gui = getDisplay(
			new double[]{30, 225, 0},
			new double[]{0, 0, 0},
			new double[]{0.625, 0.625, 0.625});
		JsonElement ground = getDisplay(
			new double[]{0, 0, 0},
			new double[]{0, 3, 0},
			new double[]{0.25, 0.25, 0.25});
		JsonElement fixed = getDisplay(
			new double[]{0, 0, 0},
			new double[]{0, 0, 0},
			new double[]{0.5, 0.5, 0.5});
		JsonElement thirdPerson = getDisplay(
			new double[]{75, 45, 0},
			new double[]{0, 2.5, 0},
			new double[]{0.375, 0.375, 0.375});
		JsonElement firstPerson = getDisplay(
			new double[]{0, 45, 0},
			new double[]{0, 0, 0},
			new double[]{0.4, 0.4, 0.4});

		display.add("gui", gui);
		display.add("ground", ground);
		display.add("fixed", fixed);
		display.add("thirdperson_righthand", thirdPerson);
		display.add("thirdperson_lefthand", thirdPerson);
		display.add("firstperson_righthand", firstPerson);
		display.add("firstperson_lefthand", firstPerson);

		return display;
	}

	public JsonObject getDisplay(double[] rot,double[] trans,double[] scale) {
		JsonObject displayElement = new JsonObject();
		displayElement.add("rotation",GSON.toJsonTree(rot));
		displayElement.add("translation",GSON.toJsonTree(trans));
		displayElement.add("scale",GSON.toJsonTree(scale));
		return displayElement;
	}
	public abstract HashMap<String, JsonObject> generateModelJSON(HashMap<String, JsonObject> models, String namespace, String catwalk_identifier);
}
