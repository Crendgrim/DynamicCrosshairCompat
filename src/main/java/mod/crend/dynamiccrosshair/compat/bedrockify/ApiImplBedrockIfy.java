package mod.crend.dynamiccrosshair.compat.bedrockify;

import me.juancarloscp52.bedrockify.Bedrockify;
import me.juancarloscp52.bedrockify.client.BedrockifyClient;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

public class ApiImplBedrockIfy implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "bedrockify";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		return (!context.isTargeting()
				&& context.getItem() instanceof BlockItem
				&& BedrockifyClient.getInstance().settings.isReacharoundEnabled()
				&& (MinecraftClient.getInstance().isInSingleplayer() || BedrockifyClient.getInstance().settings.isReacharoundMultiplayerEnabled())
				&& (context.player.isSneaking() || !BedrockifyClient.getInstance().settings.isReacharoundSneakingEnabled())
		);
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		if (entity instanceof TntMinecartEntity tntMinecart && !tntMinecart.isPrimed() && Bedrockify.getInstance().settings.fireAspectLight) {
			ItemStack itemStack = context.getItemStack();
			if ((itemStack.hasEnchantments() || itemStack.getItem() instanceof EnchantedBookItem)
					&& EnchantmentHelper.get(itemStack).containsKey(Enchantments.FIRE_ASPECT)) {
				return Crosshair.USABLE;
			}
		}
		return null;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (Bedrockify.getInstance().settings.fireAspectLight) {
			ItemStack itemStack = context.getItemStack();
			if ((itemStack.hasEnchantments() || itemStack.getItem() instanceof EnchantedBookItem)
					&& EnchantmentHelper.get(itemStack).containsKey(Enchantments.FIRE_ASPECT)) {
				BlockState blockState = context.getBlockState();
				Block block = context.getBlock();
				if (block instanceof TntBlock) {
					return Crosshair.USABLE;
				}
				if (block instanceof CampfireBlock
						&& !CampfireBlock.isLitCampfire(blockState)
						&& CampfireBlock.canBeLit(blockState)) {
					return Crosshair.USABLE;
				}
				if (block instanceof CandleBlock
						&& !CandleBlock.isLitCandle(blockState)
						&& CandleBlock.canBeLit(blockState)) {
					return Crosshair.USABLE;
				}
				if (block instanceof CandleCakeBlock
						&& isHittingCandleOnCake(context.getBlockHitResult())
						&& !CandleCakeBlock.isLitCandle(blockState)
						&& CandleCakeBlock.canBeLit(blockState)) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeHoldingBlock()
				&& !context.isTargeting()
				&& context.getItem() instanceof BlockItem
				&& BedrockifyClient.getInstance().settings.isReacharoundEnabled()
				&& (MinecraftClient.getInstance().isInSingleplayer() || BedrockifyClient.getInstance().settings.isReacharoundMultiplayerEnabled())
				&& (context.player.isSneaking() || !BedrockifyClient.getInstance().settings.isReacharoundSneakingEnabled())
				&& context.player.getPitch() > (float)BedrockifyClient.getInstance().settings.getReacharoundPitchAngle()) {
			BlockPos belowPlayerPos = context.player.getBlockPos().down();
			BlockState belowPlayerState = context.world.getBlockState(belowPlayerPos);
			if (!belowPlayerState.isAir() && !(belowPlayerState.getBlock() instanceof FluidBlock) || isNonFullBlock(context)) {
				if (checkRelativeBlockPosition(context)) {
					BlockState offsetState = context.world.getBlockState(belowPlayerPos.offset(context.player.getHorizontalFacing()));
					if (offsetState.getBlock() instanceof FluidBlock || offsetState.getBlock() instanceof AirBlock) {
						return Crosshair.HOLDING_BLOCK;
					}
				}
			}
		}
		return null;
	}

	// Replicated, partially simplified private helper functions from Bedrockify
	private boolean isHittingCandleOnCake(BlockHitResult hitResult) {
		return hitResult.getPos().y - (double)hitResult.getBlockPos().getY() > 0.5D;
	}
	private boolean isNonFullBlock(CrosshairContext context) {
		Block playerPosBlock = context.world.getBlockState(context.player.getBlockPos()).getBlock();
		return playerPosBlock instanceof SlabBlock
				|| playerPosBlock instanceof StairsBlock
				|| playerPosBlock instanceof ChainBlock
				|| playerPosBlock instanceof EndRodBlock
				|| playerPosBlock instanceof BedBlock
				|| playerPosBlock instanceof SkullBlock
				|| playerPosBlock instanceof StonecutterBlock
				|| playerPosBlock instanceof AbstractChestBlock;
	}

	private boolean checkRelativeBlockPosition(CrosshairContext context) {
		return this.checkRelativeBlockPosition(context.player.getPos().getX() - (double)context.player.getBlockPos().getX(), context.player.getHorizontalFacing().getUnitVector().getX())
				|| this.checkRelativeBlockPosition(context.player.getPos().getZ() - (double)context.player.getBlockPos().getZ(), context.player.getHorizontalFacing().getUnitVector().getZ());
	}

	private boolean checkRelativeBlockPosition(double pos, float direction) {
		double distance = BedrockifyClient.getInstance().settings.getReacharoundBlockDistance();
		if (direction > 0.0F) {
			return 1.0 - pos < distance;
		} else if (direction < 0.0F) {
			return pos < distance;
		} else {
			return false;
		}
	}

}
