package com.gmail.willramanand.RamSkills.utils;

import com.google.common.base.Preconditions;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public enum VBlockFace {

    // CORE DIRECTIONS
    NORTH (new Vector(0, 0, -1)),
    EAST(new Vector(1, 0, 0)),
    SOUTH(new Vector(0, 0, 1)),
    WEST(new Vector(-1, 0, 0)),
    UP(new Vector(0, 1, 0)),
    DOWN(new Vector(0, -1, 0)),

    // CORNER DIRECTIONS
    NORTH_EAST(new Vector(1, 0, -1)),
    NORTH_WEST(new Vector(-1, 0, -1)),
    SOUTH_EAST(new Vector(1, 0, 1)),
    SOUTH_WEST(new Vector(-1, 0, 1)),

    // EDGE DIRECTIONS
    NORTH_UP(new Vector(0, 1, -1)),
    EAST_UP(new Vector(1, 1, 0)),
    SOUTH_UP(new Vector(0, 1, 1)),
    WEST_UP(new Vector(-1, 1, 0)),
    NORTH_DOWN(new Vector(0, -1, -1)),
    EAST_DOWN(new Vector(1, -1, 0)),
    SOUTH_DOWN(new Vector(0, -1, 1)),
    WEST_DOWN(new Vector(-1, -1, 0)),

    // CORNER EDGE DIRECTIONS
    NORTH_EAST_UP(new Vector(1, 1, -1)),
    NORTH_WEST_UP(new Vector(-1, 1, -1)),
    SOUTH_EAST_UP(new Vector(1, 1, 1)),
    SOUTH_WEST_UP(new Vector(-1, 1, 1)),
    NORTH_EAST_DOWN(new Vector(1, -1, -1)),
    NORTH_WEST_DOWN(new Vector(-1, -1, -1)),
    SOUTH_EAST_DOWN(new Vector(1, -1, 1)),
    SOUTH_WEST_DOWN(new Vector(-1, -1, 1));

    private Vector direction;

    VBlockFace(Vector direction) {
        this.direction = direction;
    }

    public Vector getDirection() {
        return direction;
    }

    public Block getConnectedBlock(Block block) {
        Preconditions.checkNotNull(block, "Cannot get the relative block of a null block");
        return block.getLocation().add(direction).getBlock();
    }

}