package net.mcreator.saferoot.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import net.mcreator.saferoot.init.SaferootModItems;

@EventBusSubscriber
public class EffetArmureProcedure {

	public static void execute() {
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		Player player = event.getEntity();
		if (player.level().isClientSide()) return;

		boolean helmet = player.getItemBySlot(EquipmentSlot.HEAD).is(SaferootModItems.ROOTIUM_ARMOR_HELMET.get());
		boolean chest = player.getItemBySlot(EquipmentSlot.CHEST).is(SaferootModItems.ROOTIUM_ARMOR_CHESTPLATE.get());
		boolean legs = player.getItemBySlot(EquipmentSlot.LEGS).is(SaferootModItems.ROOTIUM_ARMOR_LEGGINGS.get());
		boolean boots = player.getItemBySlot(EquipmentSlot.FEET).is(SaferootModItems.ROOTIUM_ARMOR_BOOTS.get());

		if (helmet) player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false));
		if (chest) player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, false, false));
		if (legs) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
		if (boots) player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 1, false, false));

		if (helmet && chest && legs && boots) {
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, false, false));
		}
	}
}