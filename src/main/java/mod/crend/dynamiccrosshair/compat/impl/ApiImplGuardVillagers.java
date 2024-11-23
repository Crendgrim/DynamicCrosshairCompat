package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
//? if guard-villagers-fabric {
//? if =1.20.1 {
import dev.sterner.guardvillagers.GuardVillagersConfig;
import dev.sterner.guardvillagers.common.entity.GuardEntity;
//?} else {
/*import dev.mrsterner.guardvillagers.GuardVillagersConfig;
import dev.mrsterner.guardvillagers.common.entity.GuardEntity;
*///?}
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.village.VillagerProfession;
//?}

public class ApiImplGuardVillagers implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "guardvillagers";
	}

	//? if guard-villagers-fabric {

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();

		if (entity instanceof GuardEntity guard && !context.getPlayer().shouldCancelInteraction() && guard.getTarget() != context.getPlayer() && guard.canMoveVoluntarily()) {
			if (context.getPlayer().hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)
					//? if =1.20.1
					|| guard.getPlayerEntityReputation(context.getPlayer()) >= GuardVillagersConfig.reputationRequirement
					|| guard.getOwnerId() != null && guard.getOwnerId().equals(context.getPlayer().getUuid())
			) {
				return new Crosshair(InteractionType.INTERACT_WITH_ENTITY);
			}
		}

		ItemStack itemStack = context.getItemStack();
		if ((itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof CrossbowItem) && context.getPlayer().isSneaking()) {
			if (entity instanceof VillagerEntity villagerEntity) {
				if (!villagerEntity.isBaby()
						&& (villagerEntity.getVillagerData().getProfession() == VillagerProfession.NONE || villagerEntity.getVillagerData().getProfession() == VillagerProfession.NITWIT)
						&& (!GuardVillagersConfig./*? if =1.20.1 {*/convertVillagerIfHaveHotv/*?} else {*//*ConvertVillagerIfHaveHOTV*//*?}*/ || context.getPlayer().hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE))
				) {
					return new Crosshair(InteractionType.PLACE_ITEM_ON_ENTITY);
				}
			}
		}

		return null;
	}
	//?}
}
