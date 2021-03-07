package FeatureProperties;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

public class RuinedPortalProperties {
    public final long structureSeed;
    public final CPos chunkPosition;

    public RuinedPortalProperties(long structureSeed, CPos chunkPosition) {
        this.structureSeed = structureSeed;
        this.chunkPosition = chunkPosition;
    }

    // TODO: fix for desert, jungle, swamp, ocean, nether
    // does not work for desert, jungle, swamp, ocean, nether
    public Integer getType(ChunkRand chunkRand, Biome biome) {
        chunkRand.setCarverSeed(structureSeed, chunkPosition.getX(), chunkPosition.getZ(), MCVersion.v1_16_1);
        chunkRand.advance(2);
        if (chunkRand.nextFloat() >= 0.05F) {
            return chunkRand.nextInt(10);
        } else {
            return chunkRand.nextInt(3);
        }
    }

    // TODO: add biome groups
    public StructureConfigurations.RuinedPortalVerticalPlacement getHeight(ChunkRand chunkRand, Biome biome) {
        chunkRand.setCarverSeed(structureSeed, chunkPosition.getX(), chunkPosition.getZ(), MCVersion.v1_16_1);
        if (biome.getId() == 2) {
            return StructureConfigurations.RuinedPortalVerticalPlacement.PARTLY_BURIED;
        } else if (biome.getId() == 21) {
            return StructureConfigurations.RuinedPortalVerticalPlacement.ON_LAND_SURFACE;
        } else if (biome.getId() == 6) {
            return StructureConfigurations.RuinedPortalVerticalPlacement.ON_OCEAN_FLOOR;
        } else if (biome.getId() == 3) {
            boolean vp = chunkRand.nextFloat() < 0.5F;
            return vp ? StructureConfigurations.RuinedPortalVerticalPlacement.IN_MOUNTAIN : StructureConfigurations.RuinedPortalVerticalPlacement.ON_LAND_SURFACE;
        } else if (biome.isOcean(biome.getId())) {
            return StructureConfigurations.RuinedPortalVerticalPlacement.ON_OCEAN_FLOOR;
        } else if (biome.getId() == 8) {
            return StructureConfigurations.RuinedPortalVerticalPlacement.IN_NETHER;
        } else {
            boolean vp = chunkRand.nextFloat() < 0.5F;
            return vp ? StructureConfigurations.RuinedPortalVerticalPlacement.UNDERGROUND : StructureConfigurations.RuinedPortalVerticalPlacement.ON_LAND_SURFACE;
        }
    }
}
