package net.moist.humectant.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class EntityData {
	public static CompoundTag getTag(Entity entity) {
		return ((IEntityData) entity).humectant$getPersistentData();
	}
	public static void setTag(Entity entity, CompoundTag tag) {
		((IEntityData) entity).humectant$setPersistentData(tag);
	}
}
