package net.moist.humectant.block;

import com.mojang.serialization.MapCodec;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.moist.humectant.datagen.OldCatwalkModelGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OldCatwalkBlock extends Block {
	private static final MapCodec<OldCatwalkBlock> CODEC;
	private static final BooleanProperty NORTH;
	private static final BooleanProperty EAST;
	private static final BooleanProperty SOUTH;
	private static final BooleanProperty WEST;

	public OldCatwalkBlock(Properties properties) {
		super(properties);
		super.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, true).setValue(EAST, true).setValue(SOUTH, true).setValue(WEST, true));
		RenderTypeRegistry.register(RenderType.translucent(), this);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NORTH).add(EAST).add(SOUTH).add(WEST);
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		Level level = blockPlaceContext.getLevel();
		BlockPos pos = blockPlaceContext.getClickedPos();
		return this.defaultBlockState().setValue(NORTH, !level.getBlockState(pos.relative(Direction.NORTH)).is(this)).setValue(EAST, !level.getBlockState(pos.relative(Direction.EAST)).is(this)).setValue(SOUTH, !level.getBlockState(pos.relative(Direction.SOUTH)).is(this)).setValue(WEST, !level.getBlockState(pos.relative(Direction.WEST)).is(this));
	}

	@Override
	protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		return blockState.setValue(NORTH, !levelAccessor.getBlockState(blockPos.relative(Direction.NORTH)).is(this)).setValue(EAST, !levelAccessor.getBlockState(blockPos.relative(Direction.EAST)).is(this)).setValue(SOUTH, !levelAccessor.getBlockState(blockPos.relative(Direction.SOUTH)).is(this)).setValue(WEST, !levelAccessor.getBlockState(blockPos.relative(Direction.WEST)).is(this));
	}

	protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {return Shapes.create(0,0.875,0,1,1,1);}
	protected @NotNull VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {return Shapes.empty();}
	protected float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {return 1.0F;}
	protected boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {return true;}

	static {
		CODEC = simpleCodec(OldCatwalkBlock::new);
		NORTH = OldCatwalkModelGenerator.NORTH.Property;
		EAST = OldCatwalkModelGenerator.EAST.Property;
		SOUTH = OldCatwalkModelGenerator.SOUTH.Property;
		WEST = OldCatwalkModelGenerator.WEST.Property;
	}
}
