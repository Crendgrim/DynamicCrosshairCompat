package mod.crend.dynamiccrosshair.compat.guardvillagers;

import dev.sterner.guardvillagers.GuardVillagers;
import dev.sterner.guardvillagers.GuardVillagersConfig;
import dev.sterner.guardvillagers.common.entity.GuardEntity;
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

		if (entity instanceof GuardEntity guard && !context.player.shouldCancelInteraction() && guard.getTarget() != context.player && guard.canMoveVoluntarily()) {
			if (context.player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)
					|| guard.getPlayerEntityReputation(context.player) >= GuardVillagersConfig.reputationRequirement
					|| guard.getOwnerId() != null && guard.getOwnerId().equals(context.player.getUuid())
			) {
				return Crosshair.INTERACTABLE;
			}
		}

		ItemStack itemStack = context.getItemStack();
		if ((itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof CrossbowItem) && context.player.isSneaking()) {
			if (entity instanceof VillagerEntity villagerEntity) {
				if (!villagerEntity.isBaby()
						&& (villagerEntity.getVillagerData().getProfession() == VillagerProfession.NONE || villagerEntity.getVillagerData().getProfession() == VillagerProfession.NITWIT)
						&& (!GuardVillagersConfig.convertVillagerIfHaveHotv || context.player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE))
				) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}
}
