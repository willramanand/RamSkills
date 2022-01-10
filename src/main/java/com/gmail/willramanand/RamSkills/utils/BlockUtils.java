package com.gmail.willramanand.RamSkills.utils;

import com.gmail.willramanand.RamSkills.RamSkills;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class BlockUtils {

    @SuppressWarnings("deprecation")
    public static boolean isFullyGrown(Block block) {
        if (block.getState().getData() instanceof Crops) {
            Crops crops = (Crops) block.getState().getData();
            return crops.getState().equals(CropState.RIPE);
        } else if (block.getState().getData() instanceof NetherWarts) {
            NetherWarts wart = (NetherWarts) block.getState().getData();
            return wart.getState().equals(NetherWartsState.RIPE);
        } else if (block.getState().getData() instanceof CocoaPlant) {
            CocoaPlant cocoaPlant = (CocoaPlant) block.getState().getData();
            return cocoaPlant.getSize().equals(CocoaPlant.CocoaPlantSize.LARGE);
        }
        return false;
    }

    public static int getGrowthStage(Block block) {
        if (block.getBlockData() instanceof Ageable) {
            Ageable crop = (Ageable) block.getBlockData();
            return crop.getAge();
        }
        return 0;
    }

    public static boolean isCarrot(Material material) {
        return material == Material.CARROT || material.name().equals("CARROTS");
    }

    public static boolean isPotato(Material material) {
        return material == Material.POTATO || material.name().equals("POTATOES");
    }

    public static boolean isBeetroot(Material material) {
        return material == Material.BEETROOT || material.name().equals("BEETROOTS") || material.name().equals("BEETROOT_BLOCK");
    }

    public static boolean isWheat(Material material) {
        return material == Material.WHEAT || material.name().equals("CROPS");
    }

    public static boolean isNetherWart(Material material) {
        return material.name().equals("NETHER_WART") || material.name().equals("NETHER_WARTS") || material.name().equals("NETHER_STALK");
    }

    public static boolean isReplenishable(Material material) {
        return isWheat(material) || isCarrot(material) || isPotato(material) || isBeetroot(material) || isNetherWart(material);
    }

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
