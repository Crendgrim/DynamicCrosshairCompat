package mod.crend.dynamiccrosshair.compat.guardvillagers;

import dev.mrsterner.guardvillagers.GuardVillagers;
import dev.mrsterner.guardvillagers.common.entity.GuardEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.village.VillagerProfession;

public class ApiImplGuardVillagers implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return GuardVillagers.MODID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof GuardEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();

		if (entity instanceof GuardEntity guard) {
			boolean configValues = !GuardVillagers.config.general.giveGuardStuffHOTV
					|| !GuardVillagers.config.general.setGuardPatrolHotv
					|| context.player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE) && GuardVillagers.config.general.giveGuardStuffHOTV
					|| context.player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE) && GuardVillagers.config.general.setGuardPatrolHotv
					|| context.player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE) && GuardVillagers.config.general.giveGuardStuffHOTV
					&& GuardVillagers.config.general.setGuardPatrolHotv;
			boolean inventoryRequirements = !context.player.shouldCancelInteraction() && guard.isOnGround();
			if (configValues && inventoryRequirements && guard.getTarget() != context.player && guard.canMoveVoluntarily()) {
				return Crosshair.INTERACTABLE;
			}
		}

		ItemStack itemStack = context.getItemStack();
		if ((itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof CrossbowItem) && context.player.isSneaking()) {
			if (entity instanceof VillagerEntity villagerEntity) {
				if (!villagerEntity.isBaby()
						&& (villagerEntity.getVillagerData().getProfession() == VillagerProfession.NONE || villagerEntity.getVillagerData().getProfession() == VillagerProfession.NITWIT)
						&& (!GuardVillagers.config.general.ConvertVillagerIfHaveHOTV || context.player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)
						&& GuardVillagers.config.general.ConvertVillagerIfHaveHOTV)) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}
}
