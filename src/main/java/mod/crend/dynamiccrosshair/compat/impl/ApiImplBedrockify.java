package mod.crend.dynamiccrosshair.compat.impl;

//? if bedrockify {
import me.juancarloscp52.bedrockify.Bedrockify;
import me.juancarloscp52.bedrockify.client.BedrockifyClient;
//?}
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.TntBlock;
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

public class ApiImplBedrockify implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "bedrockify";
	}

	//? if bedrockify {
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
				&& (context.getPlayer().isSneaking() || !BedrockifyClient.getInstance().settings.isReacharoundSneakingEnabled())
		);
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		if (entity instanceof TntMinecartEntity tntMinecart && !tntMinecart.isPrimed() && Bedrockify.getInstance().settings.fireAspectLight) {
			ItemStack itemStack = context.getItemStack();
			if ((itemStack.hasEnchantments() || itemStack.getItem() instanceof EnchantedBookItem)
					&& EnchantmentHelper.get(itemStack).containsKey(Enchantments.FIRE_ASPECT)) {
				return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
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
					return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
				}
				if (block instanceof CampfireBlock
						&& !CampfireBlock.isLitCampfire(blockState)
						&& CampfireBlock.canBeLit(blockState)) {
					return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
				}
				if (block instanceof CandleBlock
						&& !CandleBlock.isLitCandle(blockState)
						&& CandleBlock.canBeLit(blockState)) {
					return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
				}
				if (block instanceof CandleCakeBlock
						&& isHittingCandleOnCake(context.getBlockHitResult())
						&& !CandleCakeBlock.isLitCandle(blockState)
						&& CandleCakeBlock.canBeLit(blockState)) {
					return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
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
				&& (context.getPlayer().isSneaking() || !BedrockifyClient.getInstance().settings.isReacharoundSneakingEnabled())
				&& context.getPlayer().getPitch() > (float)BedrockifyClient.getInstance().settings.getReacharoundPitchAngle()) {
			BlockPos belowPlayerPos = context.getPlayer().getBlockPos().down();
			BlockState belowPlayerState = context.getWorld().getBlockState(belowPlayerPos);
			if (!belowPlayerState.isAir() && !(belowPlayerState.getBlock() instanceof FluidBlock) || isNonFullBlock(context)) {
				if (checkRelativeBlockPosition(context)) {
					BlockState offsetState = context.getWorld().getBlockState(belowPlayerPos.offset(context.getPlayer().getHorizontalFacing()));
					if (offsetState.getBlock() instanceof FluidBlock || offsetState.getBlock() instanceof AirBlock) {
						return new Crosshair(InteractionType.PLACE_BLOCK);
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
		Block playerPosBlock = context.getWorld().getBlockState(context.getPlayer().getBlockPos()).getBlock();
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
		return this.checkRelativeBlockPosition(context.getPlayer().getPos().getX() - (double)context.getPlayer().getBlockPos().getX(), context.getPlayer().getHorizontalFacing().getUnitVector().x())
				|| this.checkRelativeBlockPosition(context.getPlayer().getPos().getZ() - (double)context.getPlayer().getBlockPos().getZ(), context.getPlayer().getHorizontalFacing().getUnitVector().z());
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
	//?}
}
