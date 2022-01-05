package com.gmail.willramanand.RamSkills.commands;

public enum Commands {
    MAIN("skills"),
    BOSSBAR("bossbar"),
    VERSION("version"),
    HELP("help"),

    ;


    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
