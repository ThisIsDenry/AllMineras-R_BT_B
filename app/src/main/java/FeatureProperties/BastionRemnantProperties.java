package FeatureProperties;

import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

public class BastionRemnantProperties {
    public final long structureSeed;
    public final CPos chunkPosition;

    public BastionRemnantProperties(long structureSeed, CPos chunkPosition) {
        this.structureSeed = structureSeed;
        this.chunkPosition = chunkPosition;
    }

    public Integer getType(ChunkRand chunkRand) {
        chunkRand.setCarverSeed(structureSeed, chunkPosition.getX(), chunkPosition.getZ(), MCVersion.v1_16_1);
        return chunkRand.nextInt(StructureConfigurations.BastionRemnantType.values().length);
    }
}
