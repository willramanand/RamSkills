package com.gmail.willramanand.RamSkills.commands;

public enum Commands {
    MAIN("rskills"),
    VERSION("version"),
    HELP("help"),
    XP("xp")

    ;


    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
