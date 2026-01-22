package net.deano.expanded_steam_whistles.whistle;


import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import net.createmod.catnip.lang.Lang;
import net.deano.expanded_steam_whistles.init.AllBlockEntityTypes;
import net.deano.expanded_steam_whistles.init.AllBlocks;
import net.deano.expanded_steam_whistles.init.AllShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.simibubi.create.content.decoration.steamWhistle.WhistleBlock.WALL;


public class ExpandedSteamWhistleBlock extends Block implements IBE<ExpandedSteamWhistleBlockEntity>, IWrenchable {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<ExpandedWhistleSize> SIZE = EnumProperty.create("size", ExpandedWhistleSize.class);

    public static enum ExpandedWhistleSize implements StringRepresentable {

        TINY, SMALL, MEDIUM, LARGE, HUGE;

        @Override
        public String getSerializedName() {
            return Lang.asId(name());
        }

    }

    public ExpandedSteamWhistleBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState()
                .setValue(POWERED, false)
                .setValue(WALL, false)
                .setValue(SIZE, ExpandedWhistleSize.MEDIUM));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return FluidTankBlock.isTank(pLevel.getBlockState(pPos.relative(getAttachedDirection(pState))));
    }

    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.cycle(SIZE);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FACING, POWERED, SIZE, WALL));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        Direction face = pContext.getClickedFace();
        boolean wall = true;
        if (face.getAxis() == Axis.Y) {
            face = pContext.getHorizontalDirection()
                    .getOpposite();
            wall = false;
        }

        BlockState state = super.getStateForPlacement(pContext).setValue(FACING, face.getOpposite())
                .setValue(POWERED, level.hasNeighborSignal(clickedPos))
                .setValue(WALL, wall);
        if (!canSurvive(state, level, clickedPos))
            return null;
        return state;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hit) {
        if (player == null)
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (AllBlocks.EXPANDED_STEAM_WHISTLE.isIn(stack)) {
            incrementSize(level, pos);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    public static void incrementSize(LevelAccessor pLevel, BlockPos pPos) {
        BlockState base = pLevel.getBlockState(pPos);
        if (!base.hasProperty(SIZE))
            return;
        ExpandedWhistleSize size = base.getValue(SIZE);
        SoundType soundtype = base.getSoundType();
        BlockPos currentPos = pPos.above();

        for (int i = 1; i <= 6; i++) {
            BlockState blockState = pLevel.getBlockState(currentPos);
            float pVolume = (soundtype.getVolume() + 1.0F) / 2.0F;
            SoundEvent growSound = SoundEvents.NOTE_BLOCK_XYLOPHONE.value();
            SoundEvent hitSound = soundtype.getHitSound();

            if (AllBlocks.EXPANDED_STEAM_WHISTLE_EXTENSION.has(blockState)) {
                if (blockState.getValue(ExpandedSteamWhistleExtensionBlock.SHAPE) == ExpandedSteamWhistleExtensionBlock.ExpandedWhistleExtenderShape.SINGLE) {
                    pLevel.setBlock(currentPos,
                            blockState.setValue(ExpandedSteamWhistleExtensionBlock.SHAPE, ExpandedSteamWhistleExtensionBlock.ExpandedWhistleExtenderShape.DOUBLE), 3);
                    if (soundtype != null) {
                        float pPitch = (float) Math.pow(2, -(i * 2) / 12.0);
                        pLevel.playSound(null, currentPos, growSound, SoundSource.BLOCKS, pVolume / 4f, pPitch);
                        pLevel.playSound(null, currentPos, hitSound, SoundSource.BLOCKS, pVolume, pPitch);
                    }
                    return;
                }
                currentPos = currentPos.above();
                continue;
            }

            if (!blockState.canBeReplaced())
                return;

            pLevel.setBlock(currentPos, AllBlocks.EXPANDED_STEAM_WHISTLE_EXTENSION.getDefaultState()
                    .setValue(SIZE, size), 3);
            if (soundtype != null) {
                float pPitch = (float) Math.pow(2, -(i * 2 - 1) / 12.0);
                pLevel.playSound(null, currentPos, growSound, SoundSource.BLOCKS, pVolume / 4f, pPitch);
                pLevel.playSound(null, currentPos, hitSound, SoundSource.BLOCKS, pVolume, pPitch);
            }
            return;
        }
    }

    public static void queuePitchUpdate(LevelAccessor level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock()instanceof ExpandedSteamWhistleBlock whistle && !level.getBlockTicks()
                .hasScheduledTick(pos, whistle))
            level.scheduleTick(pos, whistle, 1);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        withBlockEntityDo(pLevel, pPos, ExpandedSteamWhistleBlockEntity::updatePitch);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        FluidTankBlock.updateBoilerState(pState, pLevel, pPos.relative(getAttachedDirection(pState)));
        if (pOldState.getBlock() != this || pOldState.getValue(SIZE) != pState.getValue(SIZE))
            queuePitchUpdate(pLevel, pPos);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        IBE.onRemove(pState, pLevel, pPos, pNewState);
        FluidTankBlock.updateBoilerState(pState, pLevel, pPos.relative(getAttachedDirection(pState)));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block,
                                BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide)
            return;

        boolean previouslyPowered = state.getValue(POWERED);
        if (previouslyPowered != level.hasNeighborSignal(pos))
            level.setBlock(pos, state.cycle(POWERED), Block.UPDATE_CLIENTS);
    }


    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel,
                                  BlockPos pCurrentPos, BlockPos pFacingPos) {
        return getAttachedDirection(pState) == pFacing && !pState.canSurvive(pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : pState;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        ExpandedWhistleSize size = pState.getValue(SIZE);
        if (!pState.getValue(WALL))
            return size == ExpandedWhistleSize.TINY ? AllShapes.WHISTLE_TINY_FLOOR :
                     size == ExpandedWhistleSize.SMALL ? AllShapes.WHISTLE_SMALL_FLOOR :
                     size == ExpandedWhistleSize.MEDIUM ? AllShapes.WHISTLE_MEDIUM_FLOOR :
                     size == ExpandedWhistleSize.LARGE ? AllShapes.WHISTLE_LARGE_FLOOR :
                    AllShapes.WHISTLE_HUGE_FLOOR;

        Direction direction = pState.getValue(FACING);
        return (size == ExpandedWhistleSize.TINY ? AllShapes.WHISTLE_TINY_WALL :
                size == ExpandedWhistleSize.SMALL ? AllShapes.WHISTLE_SMALL_WALL :
                size == ExpandedWhistleSize.MEDIUM ? AllShapes.WHISTLE_MEDIUM_WALL :
                size == ExpandedWhistleSize.LARGE ? AllShapes.WHISTLE_LARGE_WALL :
                AllShapes.WHISTLE_HUGE_WALL).get(direction);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }


    public static Direction getAttachedDirection(BlockState state) {
        return state.getValue(WALL) ? state.getValue(FACING) : Direction.DOWN;
    }

    @Override
    public Class<ExpandedSteamWhistleBlockEntity> getBlockEntityClass() {
        return ExpandedSteamWhistleBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ExpandedSteamWhistleBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.EXPANDED_STEAM_WHISTLE.get();
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pMirror == Mirror.NONE ? pState : pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

}