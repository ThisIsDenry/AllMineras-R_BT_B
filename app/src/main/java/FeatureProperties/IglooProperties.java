package FeatureProperties;

import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

public class IglooProperties {
    public final long structureSeed;
    public final CPos chunkPosition;
    public boolean hasBasement;

    public IglooProperties(long structureSeed, CPos chunkPosition) {
        this.structureSeed = structureSeed;
        this.chunkPosition = chunkPosition;
    }

    public boolean getBasement(ChunkRand chunkRand, MCVersion version) {
        chunkRand.setCarverSeed(structureSeed, chunkPosition.getX(), chunkPosition.getZ(), version);
        // rotation call
        chunkRand.advance(1);
        return hasBasement = (chunkRand.nextDouble() < 0.5);
    }
}
