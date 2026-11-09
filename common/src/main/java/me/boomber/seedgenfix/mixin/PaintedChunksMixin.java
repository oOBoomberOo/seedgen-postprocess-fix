package me.boomber.seedgenfix.mixin;

import me.boomber.seedgenfix.SeedGenPostProcessFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * SeedGen gates two unrelated things behind its single "trees" option:
 * the tree canopy (drawn by TreeCover/addTrees) and the painting of
 * every ground-painting placed feature (PaintedChunks). Painting runs
 * each placed feature through a reflective in-memory WorldGenLevel, so
 * a modded worldgen pack such as WWOO makes LOD generation crawl.
 *
 * Returning null here skips only the painted ground features (clay
 * disks, vegetation patches, modded ground features). Tree canopies are
 * untouched because they come from a different path.
 *
 * Disable with -Dseedgenfix.skipFeaturePainting=false to get SeedGen's
 * original behaviour back.
 */
@Mixin(targets = "mobius.seedrenderer.dh.PaintedChunks$World", remap = false)
public final class PaintedChunksMixin {

	@Inject(method = "at", at = @At("HEAD"), cancellable = true, remap = false)
	private void seedgenfix$skipGroundFeaturePainting(CallbackInfoReturnable<Object> cir) {
		if (SeedGenPostProcessFix.skipFeaturePainting()) {
			cir.setReturnValue(null);
		}
	}
}
