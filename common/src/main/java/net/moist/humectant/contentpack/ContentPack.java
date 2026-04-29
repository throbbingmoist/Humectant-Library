package net.moist.humectant.contentpack;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.moist.humectant.Humectant;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@Deprecated
public class ContentPack {
	private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Humectant.MOD_ID, Registries.CREATIVE_MODE_TAB);
	public @Nullable
	final DeferredSupplier<CreativeModeTab> TAB;
	private final String NAMESPACE;
	public DeferredRegister<Item> ITEMS;
	public DeferredRegister<Block> BLOCKS;
	public ContentPack(String namespace, String modid, ItemLike itemLike) {
		this.NAMESPACE = namespace;
		this.ITEMS = DeferredRegister.create(modid, Registries.ITEM);
		this.BLOCKS = DeferredRegister.create(modid, Registries.BLOCK);
		this.TAB = TABS.register(
			NAMESPACE + "_tab",
			() -> CreativeTabRegistry.create(
				Component.translatable("category."+modid+"." + namespace + "_tab"),
				() -> new ItemStack(itemLike)
			)
		);
	}

	public static void registerTabs() {
		TABS.register();
	}

	public String namespace() {return this.NAMESPACE;}

	public DeferredSupplier<Block> registerBlock(String id, Supplier<? extends Block> supplier) {
		return BLOCKS.register(ResourceLocation.fromNamespaceAndPath(this.NAMESPACE, id), supplier);
	}

	public DeferredSupplier<Block> registerBlockWithItem(String id, Supplier<? extends Block> supplier) {
		ResourceLocation location = ResourceLocation.fromNamespaceAndPath(this.NAMESPACE, id);
		DeferredSupplier<Block> block = BLOCKS.register(location, supplier);
		ITEMS.register(location, () -> new BlockItem(block.get(), new Item.Properties().arch$tab(TAB).stacksTo(64)));
		return block;
	}

	public DeferredSupplier<Item> registerItem(String id, Supplier<? extends Item> supplier) {
		return ITEMS.register(ResourceLocation.fromNamespaceAndPath(this.NAMESPACE, id), supplier);
	}

	public void registerBlocks() {
		this.BLOCKS.register();
	}

	public void registerItems() {
		this.ITEMS.register();
	}
}