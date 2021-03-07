package FeatureProperties;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

public class ShipwreckProperties {
    public final long structureSeed;
    public final CPos chunkPosition;

    public ShipwreckProperties(long structureSeed, CPos chunkPosition) {
        this.structureSeed = structureSeed;
        this.chunkPosition = chunkPosition;
    }

    public Integer getType(ChunkRand chunkRand, Biome biome) {
        chunkRand.setCarverSeed(structureSeed, chunkPosition.getX(), chunkPosition.getZ(), MCVersion.v1_16_1);
        chunkRand.advance(1);
        int id = biome.getId();
        if (biome.isOcean(id)) return chunkRand.nextInt(StructureConfigurations.OceanShipwreckType.values().length);
        else if (id == 16 || id == 26) return chunkRand.nextInt(StructureConfigurations.BeachShipwreckType.values().length);
        return null;
    }
}
