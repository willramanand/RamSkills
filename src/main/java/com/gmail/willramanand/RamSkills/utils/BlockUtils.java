package com.gmail.willramanand.RamSkills.utils;

import com.gmail.willramanand.RamSkills.RamSkills;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class BlockUtils {

    public static void setPlayerPlace(Block block) {
        block.setMetadata("playerPlaced", new FixedMetadataValue(RamSkills.getInstance(), true));
    }

    public static boolean isPlayerPlaced(Block block) {
        List<MetadataValue> placedData = block.getMetadata("playerPlaced");
        for (MetadataValue value : placedData) {
            if (value != null) {
                return value.asBoolean();
            }
        }
        return false;
    }
}
