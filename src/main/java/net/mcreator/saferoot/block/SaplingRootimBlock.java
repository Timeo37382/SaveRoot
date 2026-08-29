package net.mcreator.saferoot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.saferoot.init.SaferootModBlocks;

public class SaplingRootimBlock extends Block implements BonemealableBlock {

	private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

	private static final int[][] TRUNK_OFFSET = {{2, 2}, {2, 2}, {3, 4}, {2, 2}, {1, 3}};
	private static final int[] BIG_TRUNK_OFFSET = {3, 3};
	private static final int GROWTH_LIGHT = 9;
	private static final int GROWTH_CHANCE = 7;
	private static final double BONEMEAL_CHANCE = 0.45;

	public SaplingRootimBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.GRASS).instabreak().noCollission().randomTicks()
				.pushReaction(PushReaction.DESTROY));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState soil = world.getBlockState(pos.below());
		return soil.is(BlockTags.DIRT) || soil.is(SaferootModBlocks.HERBEENROOTIUM.get()) || soil.getBlock() instanceof FarmBlock;
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction side, BlockState neighbour, LevelAccessor world, BlockPos pos, BlockPos neighbourPos) {
		return state.canSurvive(world, pos) ? super.updateShape(state, side, neighbour, world, pos, neighbourPos) : Blocks.AIR.defaultBlockState();
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
		return true;
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return type == PathComputationType.AIR;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 100;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (!world.isAreaLoaded(pos, 1))
			return;
		if (world.getMaxLocalRawBrightness(pos.above()) >= GROWTH_LIGHT && random.nextInt(GROWTH_CHANCE) == 0)
			this.grow(world, pos, random);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return random.nextDouble() < BONEMEAL_CHANCE;
	}

	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		this.grow(world, pos, random);
	}

	public void grow(ServerLevel world, BlockPos pos, RandomSource random) {
		BlockPos corner = findSquare(world, pos);
		if (corner != null) {
			BlockPos[] square = {corner, corner.east(), corner.south(), corner.east().south()};
			for (BlockPos part : square)
				world.setBlock(part, Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
			if (!placeTree(world, "arbre_6", corner.offset(-BIG_TRUNK_OFFSET[0], 0, -BIG_TRUNK_OFFSET[1]), random)) {
				for (BlockPos part : square)
					world.setBlock(part, this.defaultBlockState(), Block.UPDATE_ALL);
			}
			return;
		}

		int index = random.nextInt(TRUNK_OFFSET.length);
		world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
		if (!placeTree(world, "arbre_" + (index + 1), pos.offset(-TRUNK_OFFSET[index][0], 0, -TRUNK_OFFSET[index][1]), random))
			world.setBlock(pos, this.defaultBlockState(), Block.UPDATE_ALL);
	}

	private BlockPos findSquare(LevelReader world, BlockPos pos) {
		BlockPos[] corners = {pos, pos.west(), pos.north(), pos.west().north()};
		for (BlockPos corner : corners) {
			if (isSapling(world, corner) && isSapling(world, corner.east()) && isSapling(world, corner.south()) && isSapling(world, corner.east().south()))
				return corner;
		}
		return null;
	}

	private boolean isSapling(LevelReader world, BlockPos pos) {
		return world.getBlockState(pos).is(this);
	}

	private static boolean placeTree(ServerLevel world, String name, BlockPos origin, RandomSource random) {
		StructureTemplate template = world.getStructureManager().get(ResourceLocation.fromNamespaceAndPath("saferoot", name)).orElse(null);
		if (template == null)
			return false;
		StructurePlaceSettings settings = new StructurePlaceSettings().setIgnoreEntities(true).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
		return template.placeInWorld(world, origin, origin, settings, random, Block.UPDATE_ALL);
	}
}
