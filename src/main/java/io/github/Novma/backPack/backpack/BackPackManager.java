package io.github.Novma.backPack.backpack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackPackManager {
    private Map<UUID, BackPack> backPacks;

    public BackPackManager() {
        backPacks = new HashMap<UUID, BackPack>();
    }

    public void add(UUID uuid, BackPack backPack) {
        backPacks.put(uuid, backPack);
    }

    public BackPack get(UUID uuid) {
        return backPacks.get(uuid);
    }
}
