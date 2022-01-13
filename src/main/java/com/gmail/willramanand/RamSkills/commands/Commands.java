package com.gmail.willramanand.RamSkills.commands;

public enum Commands {
    MAIN("skills"),
    VERSION("version"),
    HELP("help"),
    XP("xp"),
    STATS("stats"),
    LIST("list")

    ;


    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
