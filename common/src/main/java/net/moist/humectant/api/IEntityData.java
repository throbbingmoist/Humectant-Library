package net.moist.humectant.api;

import net.minecraft.nbt.CompoundTag;

public interface IEntityData {
	CompoundTag humectant$getPersistentData();
	void humectant$setPersistentData(CompoundTag nbt);
}
