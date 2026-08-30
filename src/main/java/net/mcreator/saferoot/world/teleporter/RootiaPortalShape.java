package net.mcreator.saferoot.world.teleporter;

import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.saferoot.init.SaferootModBlocks;

public class RootiaPortalShape {

	private static final int MIN_WIDTH = 2;
	public static final int MAX_WIDTH = 21;
	private static final int MIN_HEIGHT = 3;
	public static final int MAX_HEIGHT = 21;

	private final LevelAccessor level;
	private final Direction.Axis axis;
	private final Direction rightDir;
	private int numPortalBlocks;
	@Nullable
	private BlockPos bottomLeft;
	private int height;
	private int width;

	public RootiaPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
		this.level = level;
		this.axis = axis;
		this.rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		this.bottomLeft = this.calculateBottomLeft(pos);
		if (this.bottomLeft == null) {
			this.bottomLeft = pos;
			this.width = 1;
			this.height = 1;
		} else {
			this.width = this.calculateWidth();
			if (this.width > 0)
				this.height = this.calculateHeight();
		}
	}

	private static boolean isFrameBlock(BlockState state) {
		return state.is(SaferootModBlocks.ROOTIUM_BLOCK.get()) || state.is(Blocks.CRIMSON_PLANKS);
	}

	private static boolean isEmpty(BlockState state) {
		return state.isAir() || state.is(BlockTags.FIRE) || state.is(SaferootModBlocks.ROOTIA_PORTAL.get());
	}

	@Nullable
	private BlockPos calculateBottomLeft(BlockPos pos) {
		int floor = Math.max(this.level.getMinBuildHeight(), pos.getY() - MAX_HEIGHT);
		while (pos.getY() > floor && isEmpty(this.level.getBlockState(pos.below()))) {
			pos = pos.below();
		}
		Direction direction = this.rightDir.getOpposite();
		int dist = this.getDistanceUntilEdgeAboveFrame(pos, direction) - 1;
		return dist < 0 ? null : pos.relative(direction, dist);
	}

	private int calculateWidth() {
		int w = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
		return w >= MIN_WIDTH && w <= MAX_WIDTH ? w : 0;
	}

	private int getDistanceUntilEdgeAboveFrame(BlockPos pos, Direction direction) {
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
		for (int i = 0; i <= MAX_WIDTH; i++) {
			cursor.set(pos).move(direction, i);
			BlockState state = this.level.getBlockState(cursor);
			if (!isEmpty(state)) {
				if (isFrameBlock(state))
					return i;
				break;
			}
			BlockState below = this.level.getBlockState(cursor.move(Direction.DOWN));
			if (!isFrameBlock(below))
				break;
		}
		return 0;
	}

	private int calculateHeight() {
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
		int h = this.getDistanceUntilTop(cursor);
		return h >= MIN_HEIGHT && h <= MAX_HEIGHT && this.hasTopFrame(cursor, h) ? h : 0;
	}

	private boolean hasTopFrame(BlockPos.MutableBlockPos cursor, int height) {
		for (int i = 0; i < this.width; i++) {
			BlockPos.MutableBlockPos top = cursor.set(this.bottomLeft).move(Direction.UP, height).move(this.rightDir, i);
			if (!isFrameBlock(this.level.getBlockState(top)))
				return false;
		}
		return true;
	}

	private int getDistanceUntilTop(BlockPos.MutableBlockPos cursor) {
		for (int i = 0; i < MAX_HEIGHT; i++) {
			cursor.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
			if (!isFrameBlock(this.level.getBlockState(cursor)))
				return i;
			cursor.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
			if (!isFrameBlock(this.level.getBlockState(cursor)))
				return i;
			for (int j = 0; j < this.width; j++) {
				cursor.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
				BlockState state = this.level.getBlockState(cursor);
				if (!isEmpty(state))
					return i;
				if (state.is(SaferootModBlocks.ROOTIA_PORTAL.get()))
					this.numPortalBlocks++;
			}
		}
		return MAX_HEIGHT;
	}

	public boolean isValid() {
		return this.bottomLeft != null && this.width >= MIN_WIDTH && this.width <= MAX_WIDTH && this.height >= MIN_HEIGHT && this.height <= MAX_HEIGHT;
	}

	public boolean isComplete() {
		return this.isValid() && this.numPortalBlocks == this.width * this.height;
	}

	public void createPortalBlocks() {
		BlockState portal = SaferootModBlocks.ROOTIA_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
		BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1))
				.forEach(pos -> this.level.setBlock(pos, portal, 18));
	}

	public static Optional<RootiaPortalShape> findEmptyPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
		return findPortalShape(level, pos, shape -> shape.isValid() && shape.numPortalBlocks == 0, axis);
	}

	public static Optional<RootiaPortalShape> findPortalShape(LevelAccessor level, BlockPos pos, Predicate<RootiaPortalShape> predicate, Direction.Axis axis) {
		Optional<RootiaPortalShape> found = Optional.of(new RootiaPortalShape(level, pos, axis)).filter(predicate);
		if (found.isPresent())
			return found;
		Direction.Axis other = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
		return Optional.of(new RootiaPortalShape(level, pos, other)).filter(predicate);
	}

	public static Vec3 getRelativePosition(BlockUtil.FoundRectangle rectangle, Direction.Axis axis, Vec3 pos, EntityDimensions dimensions) {
		double freeWidth = (double) rectangle.axis1Size - (double) dimensions.width();
		double freeHeight = (double) rectangle.axis2Size - (double) dimensions.height();
		BlockPos corner = rectangle.minCorner;
		double x;
		if (freeWidth > 0.0) {
			float center = (float) corner.get(axis) + dimensions.width() / 2.0F;
			x = Mth.clamp(Mth.inverseLerp(pos.get(axis) - (double) center, 0.0, freeWidth), 0.0, 1.0);
		} else {
			x = 0.5;
		}
		double y;
		if (freeHeight > 0.0) {
			y = Mth.clamp(Mth.inverseLerp(pos.get(Direction.Axis.Y) - (double) corner.get(Direction.Axis.Y), 0.0, freeHeight), 0.0, 1.0);
		} else {
			y = 0.0;
		}
		Direction.Axis other = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
		double z = pos.get(other) - ((double) corner.get(other) + 0.5);
		return new Vec3(x, y, z);
	}

	public static Vec3 findCollisionFreePosition(Vec3 pos, ServerLevel level, Entity entity, EntityDimensions dimensions) {
		if (dimensions.width() > 4.0F || dimensions.height() > 4.0F)
			return pos;
		double halfHeight = (double) dimensions.height() / 2.0;
		Vec3 center = pos.add(0.0, halfHeight, 0.0);
		VoxelShape shape = Shapes.create(AABB.ofSize(center, (double) dimensions.width(), 0.0, (double) dimensions.width()).expandTowards(0.0, 1.0, 0.0).inflate(1.0E-6));
		Optional<Vec3> free = level.findFreePosition(entity, shape, center, (double) dimensions.width(), (double) dimensions.height(), (double) dimensions.width());
		return free.map(found -> found.subtract(0.0, halfHeight, 0.0)).orElse(pos);
	}
}
