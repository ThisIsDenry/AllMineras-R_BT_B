package FeatureProperties;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DragonGatewayProperties {
    public final long structureSeed;

    public DragonGatewayProperties(long structureSeed) {
        this.structureSeed = structureSeed;
    }

    public ArrayList<Integer> getGatewayOrder() {
        ArrayList<Integer> gateways = new ArrayList<Integer>();
        gateways.addAll(ContiguousSet.<Integer>create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
        Collections.shuffle(gateways, new Random(structureSeed));
        return gateways;
    }
}
