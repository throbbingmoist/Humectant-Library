package net.moist.humectant.util;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

// hDirection short for humectant directions.
public enum hDirection {
	NORTHWEST("northwest", new String[]{"north", "west"}),
	NORTH("north", "north"),
	NORTHEAST("northeast", new String[]{"north", "east"}),
	EAST("east", "east"),
	SOUTHEAST("southeast", new String[]{"south", "east"}),
	SOUTH("south", "south"),
	SOUTHWEST("southwest", new String[]{"south", "west"}),
	WEST("west", "west");

	public static final hDirection[] CatwalkDirections = new hDirection[]{NORTHWEST, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST};

	public final String String;
	public final String[] States;
	public final BooleanProperty Property;
	hDirection(String name, String state) {
		this.String = name;
		this.States = new String[]{state};
		Property = BooleanProperty.create(name);
	}
	hDirection(String name, String[] state) {
		this.String = name;
		this.States = state;
		Property = BooleanProperty.create(name);
	}
}
