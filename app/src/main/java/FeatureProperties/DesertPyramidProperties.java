package FeatureProperties;

import kaptainwutax.featureutils.loot.LootContext;
import kaptainwutax.featureutils.loot.MCLootTables;
import kaptainwutax.featureutils.loot.item.ItemStack;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;

import java.util.List;

public class DesertPyramidProperties {
    public final long structureSeed;
    public final CPos chunkPosition;

    public DesertPyramidProperties(long structureSeed, CPos chunkPosition) {
        this.structureSeed = structureSeed;
        this.chunkPosition = chunkPosition;
    }

    public List<ItemStack> getPyramidLoot(ChunkRand rand, MCVersion version) {
        rand.setDecoratorSeed(structureSeed, chunkPosition.getX() << 4, chunkPosition.getZ() << 4,
                version.isNewerOrEqualTo(MCVersion.v1_16) ? 3 : 2,
                version.isNewerOrEqualTo(MCVersion.v1_16) ? 4 : 3, version);
        var loot = MCLootTables.DESERT_PYRAMID_CHEST
                .generate(new LootContext(rand.nextLong()));
        for(int i = 0; i < 3; i++){
            loot.addAll(MCLootTables.DESERT_PYRAMID_CHEST
                    .generate(new LootContext(rand.nextLong())));
        }
        return loot;
    }
}
