package lemon_juice.item.custom;

import lemon_juice.item.custom.util.UtilShape;
import lemon_juice.item.tier.LembraryTiers;
import lemon_juice.tag.TagRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TunnelerItem extends DiggerItem {
    final int radius; //Radius 1 is 3x3 (1 Up, 1 Down, 1 Left, 1 Right, and Corners); 2 is 5x5; 3 is 7x7, etc.
    final Tier tier;

    public TunnelerItem(Tier tier, Properties properties, int radius) {
        super(1.0F, 1.0F, tier, TagRegistry.Blocks.WITH_TUNNELER, properties);
        this.radius = radius;
        this.tier = tier;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if(tier == Tiers.GOLD) return 128;
        else if(tier == Tiers.WOOD) return 236;
        else if(tier == Tiers.STONE) return 524;
        else if(tier == Tiers.IRON) return 1_000;
        else if(tier == Tiers.DIAMOND) return 6_244;
        else if(tier == Tiers.NETHERITE) return 8_024;
        else if(tier == LembraryTiers.NECRONITE_TIER) return -1;
        else return 128; //This case should never be reached
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(tier == LembraryTiers.NECRONITE_TIER) components.add(Component.translatable("tooltip.lembrary.unbreakable").withStyle(ChatFormatting.RED));

        if(radius == 1) components.add(Component.translatable("tooltip.lembrary.tunneler_r1"));
        else if(radius == 2) components.add(Component.translatable("tooltip.lembrary.tunneler_r2"));
        else if(radius == 3) components.add(Component.translatable("tooltip.lembrary.tunneler_r3"));
        else components.add(Component.translatable("tooltip.lembrary.tunneler_r4"));

        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        Level world = player.level();
        HitResult ray = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);

        //Get Y-Offset
        int yOff = 0;
        if (radius == 2 && player.isCrouching()) {
            yOff = 1;
        }

        if (ray != null && ray.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) ray;
            Direction sideHit = blockHitResult.getDirection();
            List<BlockPos> shape;

            if (sideHit == Direction.UP || sideHit == Direction.DOWN) { //If UP/DOWN
                shape = UtilShape.squareHorizontalHollow(pos, radius);
                for (int i = 1; i <= radius; i++) {
                    shape.addAll(UtilShape.squareHorizontalHollow(pos, radius - i));
                }
            } else if (sideHit == Direction.EAST || sideHit == Direction.WEST) { //If EAST/WEST
                int y = 1 + radius + yOff;
                int z = radius;
                shape = UtilShape.squareVerticalZ(pos, y, z);
            } else { //Must be NORTH/SOUTH
                int x = radius;
                int y = 1 + radius - yOff;
                shape = UtilShape.squareVerticalX(pos, x, y);
            }

            for (BlockPos posCurrent : shape) {
                BlockState currentBlockState = world.getBlockState(posCurrent);
                if (currentBlockState.isAir()) {
                    continue;
                }

                if (currentBlockState.getDestroySpeed(world, posCurrent) >= 0
                        && player.mayUseItemAt(posCurrent, sideHit, stack)
                        && ForgeEventFactory.doPlayerHarvestCheck(player, currentBlockState, true)
                        && this.getDestroySpeed(stack, currentBlockState) > 1
                        && (currentBlockState.canHarvestBlock(world, pos, player)
                        || currentBlockState.is(this.getTier().getTag()))) {
                    stack.mineBlock(world, currentBlockState, posCurrent, player);
                    Block blockCurrent = currentBlockState.getBlock();
                    if (world.isClientSide) {
                        world.levelEvent(2001, posCurrent, Block.getId(currentBlockState));
                        if (blockCurrent.onDestroyedByPlayer(currentBlockState, world, posCurrent, player, true, currentBlockState.getFluidState())) {
                            blockCurrent.destroy(world, posCurrent, currentBlockState);
                        }
                    } else if (player instanceof ServerPlayer) {
                        ServerPlayer mp = (ServerPlayer) player;
                        int xpGivenOnDrop = ForgeHooks.onBlockBreakEvent(world, ((ServerPlayer) player).gameMode.getGameModeForPlayer(), (ServerPlayer) player, posCurrent);
                        if (blockCurrent.onDestroyedByPlayer(currentBlockState, world, posCurrent, player, true, currentBlockState.getFluidState()) && world instanceof ServerLevel) {
                            BlockEntity tile = world.getBlockEntity(posCurrent);
                            blockCurrent.destroy(world, posCurrent, currentBlockState);
                            blockCurrent.playerDestroy(world, player, posCurrent, currentBlockState, tile, stack);
                            blockCurrent.popExperience((ServerLevel) world, posCurrent, xpGivenOnDrop);
                        }
                        mp.connection.send(new ClientboundBlockUpdatePacket(world, posCurrent));
                    }
                }
            }
        }

        return super.onBlockStartBreak(stack, pos, player);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if(tier == Tiers.WOOD) return Math.max(Items.WOODEN_PICKAXE.getDestroySpeed(stack, state), Items.WOODEN_SHOVEL.getDestroySpeed(stack, state));
        else if(tier == Tiers.STONE) return Math.max(Items.STONE_PICKAXE.getDestroySpeed(stack, state), Items.STONE_SHOVEL.getDestroySpeed(stack, state));
        else if(tier == Tiers.IRON) return Math.max(Items.IRON_PICKAXE.getDestroySpeed(stack, state), Items.IRON_SHOVEL.getDestroySpeed(stack, state));
        else if(tier == Tiers.GOLD) return Math.max(Items.GOLDEN_PICKAXE.getDestroySpeed(stack, state), Items.GOLDEN_SHOVEL.getDestroySpeed(stack, state));
        else if(tier == Tiers.DIAMOND) return Math.max(Items.DIAMOND_PICKAXE.getDestroySpeed(stack, state), Items.DIAMOND_SHOVEL.getDestroySpeed(stack, state));
        else if(tier == Tiers.NETHERITE) return Math.max(Items.NETHERITE_PICKAXE.getDestroySpeed(stack, state), Items.NETHERITE_SHOVEL.getDestroySpeed(stack, state));

        else if(tier == LembraryTiers.NECRONITE_TIER) return getMaxDestroySpeedOfTier(LembraryTiers.NECRONITE_TIER, stack, state);
        else if(tier == LembraryTiers.BLUTSTEIN_TIER) return getMaxDestroySpeedOfTier(LembraryTiers.BLUTSTEIN_TIER, stack, state);
        else if(tier == LembraryTiers.UMBRALITE_TIER) return getMaxDestroySpeedOfTier(LembraryTiers.UMBRALITE_TIER, stack, state);
        else return getMaxDestroySpeedOfTier(LembraryTiers.ZWEIITE_TIER, stack, state);
    }

    /**
     * Gets the maximum destroy speed of the pickaxe and shovel of the same tier
     *
     * @param tier The <code>Tier</code> of the <code>Item</code> of the <code>ItemStack</code>
     * @param stack The <code>Item</code> itself
     * @param state The <code>BlockState</code> of the <code>Block</code> being broken
     */
    private float getMaxDestroySpeedOfTier(Tier tier, ItemStack stack, BlockState state){
        return Math.max(new PickaxeItem(tier, 1, -2.8f, new Properties()).getDestroySpeed(stack, state), new ShovelItem(tier, 1.5f, -3.0f, new Properties()).getDestroySpeed(stack, state));
    }
}
