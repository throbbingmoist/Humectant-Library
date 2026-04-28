package net.moist.humectant.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.moist.humectant.api.IEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin implements IEntityData {
	private CompoundTag persistentData;

	@Override
	public CompoundTag humectant$getPersistentData() {
		if (this.persistentData == null) {
			this.persistentData = new CompoundTag();
		}
		return this.persistentData;
	}

	@Override
	public void humectant$setPersistentData(CompoundTag nbt) {this.persistentData = nbt;}

	@Inject(method = "saveWithoutId", at = @At("HEAD"))
	protected void injectWriteMethod(CompoundTag compoundTag, CallbackInfoReturnable<CompoundTag> cir) {
		if (this.persistentData != null) {
			compoundTag.put("humectant_data", this.persistentData);
		}
	}

	// Inject into NBT Load
	@Inject(method = "load", at = @At("HEAD"))
	protected void injectReadMethod(CompoundTag compoundTag, CallbackInfo ci) {
		if (compoundTag.contains("humectant_data", 10)) {
			this.persistentData = compoundTag.getCompound("humectant_data");
		}
	}

	@Inject(method = "restoreFrom", at = @At("TAIL"))
	private void injectCloneMethod(Entity entity, CallbackInfo ci) {
		IEntityData oldData = (IEntityData) entity;
		IEntityData newData = this;

		newData.humectant$setPersistentData(oldData.humectant$getPersistentData());
	}
}
