package net.mcreator.saferoot.mixin;

import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.core.Holder;

import net.mcreator.saferoot.init.SaferootModBiomes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin implements SaferootModBiomes.SaferootModNoiseGeneratorSettings {
	@Unique
	private Holder<DimensionType> saferoot_dimensionTypeReference;

	@WrapMethod(method = "surfaceRule")
	public SurfaceRules.RuleSource surfaceRule(Operation<SurfaceRules.RuleSource> original) {
		SurfaceRules.RuleSource retval = original.call();
		if (this.saferoot_dimensionTypeReference != null) {
			retval = SaferootModBiomes.adaptSurfaceRule(retval, this.saferoot_dimensionTypeReference);
		}
		return retval;
	}

	@Override
	public void setsaferootDimensionTypeReference(Holder<DimensionType> dimensionType) {
		this.saferoot_dimensionTypeReference = dimensionType;
	}
}