package net.mcreator.saferoot.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.saferoot.init.SaferootModItems;

import java.util.List;

@EventBusSubscriber
public class PeespecialeeProcedure {

	public static void execute() {
	}

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (!(event.getSource().getEntity() instanceof Player player)) return;
		ItemStack itemstack = player.getMainHandItem();
		if (!itemstack.is(SaferootModItems.ROOTIUM_SWORD.get())) return;

		LivingEntity target = event.getEntity();
		Level world = target.level();

		// --- 1. RÉGÉNÉRATION CUMULABLE ---
		int level = 0;
		if (player.hasEffect(MobEffects.REGENERATION)) {
			level = Math.min(player.getEffect(MobEffects.REGENERATION).getAmplifier() + 1, 4);
		}
		player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, level, false, true));

		// --- 2. CERCLE DE PARTICULES DE RAYON 5 BLOCS ---
		if (world instanceof ServerLevel serverWorld) {
			double centerX = target.getX();
			double centerY = target.getY() + 0.2;
			double centerZ = target.getZ();
			double radius = 5.0;

			for (int i = 0; i < 36; i++) {
				double angle = Math.toRadians(i * 10);
				double px = centerX + radius * Math.cos(angle);
				double pz = centerZ + radius * Math.sin(angle);
				serverWorld.sendParticles(ParticleTypes.HAPPY_VILLAGER, px, centerY, pz, 1, 0, 0.05, 0, 0.02);
				serverWorld.sendParticles(ParticleTypes.CRIT, px, centerY, pz, 1, 0, 0.1, 0, 0.05);
			}
		}

		// --- 3. ONDE DE CHOC (KNOCKBACK) ---
		AABB area = new AABB(target.getX() - 5, target.getY() - 5, target.getZ() - 5, target.getX() + 5, target.getY() + 5, target.getZ() + 5);
		List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, area);

		for (LivingEntity e : entities) {
			if (e == player || e == target) continue;
			Vec3 dir = e.position().subtract(target.position()).normalize();
			e.setDeltaMovement(new Vec3(dir.x * 0.8, 0.25, dir.z * 0.8));
			e.hasImpulse = true;
		}
	}
}