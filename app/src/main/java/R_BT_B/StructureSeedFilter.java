package R_BT_B;

import FeatureProperties.BastionRemnantProperties;
import FeatureProperties.RavineProperties;
import kaptainwutax.biomeutils.source.NetherBiomeSource;
import kaptainwutax.featureutils.loot.LootContext;
import kaptainwutax.featureutils.loot.MCLootTables;
import kaptainwutax.featureutils.loot.item.Item;
import kaptainwutax.featureutils.loot.item.ItemStack;
import kaptainwutax.featureutils.structure.BastionRemnant;
import kaptainwutax.featureutils.structure.BuriedTreasure;
import kaptainwutax.seedutils.mc.ChunkRand;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.pos.CPos;
import kaptainwutax.seedutils.util.math.DistanceMetric;
import kaptainwutax.seedutils.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class StructureSeedFilter {
    public static final MCVersion VERSION = MCVersion.v1_16_2;
    public static ArrayList<Long> filteredSeeds = new ArrayList<>();

    public static List<ItemStack> getTreasureLoot(long structureSeed, int chunkX, int chunkZ, ChunkRand rand, MCVersion version) {
        rand.setDecoratorSeed(structureSeed, chunkX << 4, chunkZ << 4,
                version.isNewerOrEqualTo(MCVersion.v1_16) ? 1 : 2,
                version.isNewerOrEqualTo(MCVersion.v1_16) ? 3 : 2, version);
        return MCLootTables.BURIED_TREASURE_CHEST.generate(new LootContext(rand.nextLong()));
    }

    public static void filterStructureSeeds(int threadCount, int offset, long startSeed, long endSeed) {
        ChunkRand chunkRand = new ChunkRand();
        BuriedTreasure buriedTreasure = new BuriedTreasure(VERSION);
        BastionRemnant bastionRemnant = new BastionRemnant(VERSION);

        for (long structureSeed = startSeed + offset; structureSeed < endSeed; structureSeed += threadCount) {
            CPos treasure = new CPos(0, 0);
            RavineProperties rp = new RavineProperties(0, new CPos(0, 0));;
            boolean rCheck = false;
            boolean tCheck = false;

            for (int chunkX = -1; chunkX < 1; chunkX++) {
                for (int chunkZ = -1; chunkZ < 1; chunkZ++) {
                    treasure = buriedTreasure.getInRegion(structureSeed, chunkX, chunkZ, chunkRand);

                    if (treasure != null) {
                        boolean diamond = false;
                        boolean emerald = false;
                        boolean gold = false;
                        boolean iron = false;
                        for (ItemStack item : getTreasureLoot(structureSeed, chunkX, chunkZ, chunkRand, VERSION)) {
                            if (item.getItem().equals(Item.IRON_INGOT)) iron = true;
                            else if (item.getItem().equals(Item.GOLD_INGOT)) gold = true;
                            else if (item.getItem().equals(Item.DIAMOND)) diamond = true;
                            else if (item.getItem().equals(Item.EMERALD)) emerald = true;
                        }
                        if (diamond && emerald && gold && iron) {
                            tCheck = true;
                            break;
                        }
                    }
                }
                if (tCheck) break;
            }

            if (!tCheck) continue;

            for (int chunkX = -3; chunkX < 0; chunkX++) {
                for (int chunkZ = -3; chunkZ < 0; chunkZ++) {
                    rp = new RavineProperties(structureSeed, new CPos(chunkX, chunkZ));
                    if (rp.generate(chunkRand)) {
                        if (rp.maxLength > 100 && rp.width > 5.5 && rp.blockPosition.getY() > 25 && rp.blockPosition.getY() <= 30 && rp.pitch < -0.100F && rp.yaw < Math.PI / 2)
                        {
                            rCheck = true;
                            break;
                        }
                    }
                }
                if (rCheck) break;
            }

            if (!rCheck) continue;

            CPos bastion1 = bastionRemnant.getInRegion(structureSeed, 0, 0, chunkRand);
            if (bastion1 == null) continue;
            if (bastion1.distanceTo(new Vec3i(0, 0, 0), DistanceMetric.EUCLIDEAN_SQ) > 0.10) continue;
            BastionRemnantProperties bp = new BastionRemnantProperties(structureSeed, bastion1);
            if (bp.getType(chunkRand) != 2) continue;
            NetherBiomeSource source = new NetherBiomeSource(VERSION, structureSeed);
            if (!bastionRemnant.canSpawn(bastion1.getX(), bastion1.getZ(), source)) continue;
            filteredSeeds.add(structureSeed);
            System.out.println(structureSeed);
        }
    }
}
