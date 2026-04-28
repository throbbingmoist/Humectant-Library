package net.moist.humectant.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.moist.humectant.util.hDirection;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.moist.humectant.Humectant.GSON;

public class CatwalkModelGenerator extends ModelGenerator {
	public static hDirection NORTHWEST;
	public static hDirection NORTH;
	public static hDirection NORTHEAST;
	public static hDirection EAST;
	public static hDirection SOUTHEAST;
	public static hDirection SOUTH;
	public static hDirection SOUTHWEST;
	public static hDirection WEST;


	public CatwalkModelGenerator(Consumer<BlockStateGenerator> blockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput, Block block) {
		super(blockStateOutput, modelOutput, block);
	}

	@Override
	public JsonObject generateBlockStateJSON(String namespace, String catwalk_identifier) {
		JsonObject state = new JsonObject();
		JsonArray multipart = new JsonArray();

		// center
		JsonObject element = new JsonObject(); JsonObject apply = new JsonObject(); JsonObject when = new JsonObject();
		apply.addProperty("model",namespace+":block/"+catwalk_identifier);

		element.add("apply",apply);multipart.add(element);
		// frame
		for (hDirection dir : hDirection.CatwalkDirections) {
			element = new JsonObject(); apply = new JsonObject(); when = new JsonObject();
			apply.addProperty("model",namespace+":block/"+catwalk_identifier+"_"+dir.String);
			JsonArray or = new JsonArray();
			when.add("OR", or);
			for (String dirflag : dir.States) {
				JsonObject el = new JsonObject();
				el.addProperty(dirflag, "true");
				or.add(el);
			}

			element.add("when",when);element.add("apply",apply);multipart.add(element);
		}

		state.add("multipart",multipart); return state;
	}

	public JsonObject getDisplayTransforms() {
		JsonObject display = new JsonObject();
		JsonElement gui = getDisplay(
			new double[]{30, 225, 0},
			new double[]{0, -2.5, 0},
			new double[]{0.625, 0.625, 0.625});
		JsonElement ground = getDisplay(
			new double[]{0, 0, 0},
			new double[]{0, 3, 0},
			new double[]{0.25, 0.25, 0.25});
		JsonElement fixed = getDisplay(
			new double[]{-90, 0, 0},
			new double[]{0, 0, 6.75},
			new double[]{0, 0, 0});
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

	@Override
	public HashMap<String, JsonObject> generateModelJSON(HashMap<String, JsonObject> models, String namespace, String catwalk_identifier) {
		JsonObject textures_floor = new JsonObject();
		textures_floor.addProperty("floor", namespace+":block/"+catwalk_identifier+"_floor");
		textures_floor.addProperty("particle", namespace+":block/"+catwalk_identifier+"_floor");
		JsonObject textures_frame = new JsonObject();
		textures_frame.addProperty("frame", namespace+":block/"+catwalk_identifier+"_frame");

		models.put(catwalk_identifier,generateCenter(textures_floor));
		models.put(catwalk_identifier+"_"+NORTH.String,generateNorth(textures_frame));
		models.put(catwalk_identifier+"_"+NORTHEAST.String,generateNorthEast(textures_frame));
		models.put(catwalk_identifier+"_"+EAST.String,generateEast(textures_frame));
		models.put(catwalk_identifier+"_"+SOUTHEAST.String,generateSouthEast(textures_frame));
		models.put(catwalk_identifier+"_"+SOUTH.String,generateSouth(textures_frame));
		models.put(catwalk_identifier+"_"+SOUTHWEST.String,generateSouthWest(textures_frame));
		models.put(catwalk_identifier+"_"+WEST.String,generateWest(textures_frame));
		models.put(catwalk_identifier+"_"+NORTHWEST.String,generateNorthWest(textures_frame));
		return models;
	}

	static {
		NORTHWEST = hDirection.NORTHWEST;
		NORTH = hDirection.NORTH;
		NORTHEAST = hDirection.NORTHEAST;
		EAST = hDirection.EAST;
		SOUTHEAST = hDirection.SOUTHEAST;
		SOUTH = hDirection.SOUTH;
		SOUTHWEST = hDirection.SOUTHWEST;
		WEST = hDirection.WEST;
	}


	public static JsonObject generateCenter(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{0, 14, 0}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{16, 16, 16}).getAsJsonArray());

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{0, 0, 16, 16}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#floor");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{16, 0, 0, 16}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#floor");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
	public static JsonObject generateNorthWest(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{-0.1, 13.95, -0.1}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{2, 16.05, 2}).getAsJsonArray());

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{3, 0, 1, 2}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("north",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{0, 3, 2, 1}).getAsJsonArray());
		face.addProperty("rotation", 90); face.addProperty("texture", "#frame");
		faces.add("west",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{0, 0, 2, 2}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{1, 1, 3, 3}).getAsJsonArray());
		face.addProperty("rotation", 270); face.addProperty("texture", "#frame");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
	public static JsonObject generateNorth(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{2, 13.95, -0.1}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{14, 16.05, 2}).getAsJsonArray());

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{14, 0, 2, 2}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("north",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{2, 2, 14, 0}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("south",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{2, 0, 14, 2}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{2, 0, 14, 2}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
	public static JsonObject generateNorthEast(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{14, 13.95, -0.1}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{16.1, 16.05, 2}).getAsJsonArray());

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{15, 0, 13, 2}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("north",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{14, 3, 16, 1}).getAsJsonArray());
		face.addProperty("rotation", 270); face.addProperty("texture", "#frame");
		faces.add("east",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{14, 0, 16, 2}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{13, 3, 15, 1}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
	public static JsonObject generateEast(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{14, 13.95, 2}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{16.1, 16.05, 14}).getAsJsonArray());

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{16, 2, 14, 14}).getAsJsonArray());
		face.addProperty("rotation", 90); face.addProperty("texture", "#frame");
		faces.add("east",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{16, 2, 14, 14}).getAsJsonArray());
		face.addProperty("rotation", 270); face.addProperty("texture", "#frame");
		faces.add("west",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{14, 2, 16, 14}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{14, 2, 16, 14}).getAsJsonArray());
		face.addProperty("rotation", 180); face.addProperty("texture", "#frame");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
	public static JsonObject generateSouthEast(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{14, 13.95, 14}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{16.1, 16.05, 16.1}).getAsJsonArray());

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{14, 15, 16, 13}).getAsJsonArray());
		face.addProperty("rotation", 270); face.addProperty("texture", "#frame");
		faces.add("east",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{15, 14, 13, 16}).getAsJsonArray());
		face.addProperty("rotation", 180); face.addProperty("texture", "#frame");
		faces.add("south",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{14, 14, 16, 16}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{13, 13, 15, 15}).getAsJsonArray());
		face.addProperty("rotation", 270); face.addProperty("texture", "#frame");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
	public static JsonObject generateSouth(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{2, 13.95, 14}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{14, 16.05, 16.1}).getAsJsonArray());

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{14, 14, 2, 16}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("north",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{2, 16, 14, 14}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("south",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{2, 14, 14, 16}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{2, 14, 14, 16}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
	public static JsonObject generateSouthWest(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{-0.1, 13.95, 14}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{2, 16.05, 16.1}).getAsJsonArray());

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{3, 14, 1, 16}).getAsJsonArray());
		face.addProperty("rotation", 180); face.addProperty("texture", "#frame");
		faces.add("south",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{0, 15, 2, 13}).getAsJsonArray());
		face.addProperty("rotation", 90); face.addProperty("texture", "#frame");
		faces.add("west",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{0, 14, 2, 16}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{1, 15, 3, 13}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
	public static JsonObject generateWest(JsonObject textures) {
		JsonObject model = new JsonObject(); JsonArray elements = new JsonArray(); JsonObject element = new JsonObject(); JsonObject faces = new JsonObject(); JsonObject face;
		element.add("from", GSON.toJsonTree(new double[]{-0.1, 13.95, 2}).getAsJsonArray());
		element.add("to", GSON.toJsonTree(new double[]{2, 16.05, 14}).getAsJsonArray());


		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{2, 2, 0, 14}).getAsJsonArray());
		face.addProperty("rotation", 90); face.addProperty("texture", "#frame");
		faces.add("east",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{0, 14, 2, 2}).getAsJsonArray());
		face.addProperty("rotation", 90); face.addProperty("texture", "#frame");
		faces.add("west",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{0, 2, 2, 14}).getAsJsonArray());
		face.addProperty("rotation", 0); face.addProperty("texture", "#frame");
		faces.add("up",face);

		face = new JsonObject();face.add("uv", GSON.toJsonTree(new int[]{0, 2, 2, 14}).getAsJsonArray());
		face.addProperty("rotation", 270); face.addProperty("texture", "#frame");
		faces.add("down",face);

		element.add("faces", faces); elements.add(element); model.add("textures",textures); model.add("elements",elements);
		return model;
	}
}
