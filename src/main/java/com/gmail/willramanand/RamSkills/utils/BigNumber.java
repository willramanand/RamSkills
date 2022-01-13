package com.gmail.willramanand.RamSkills.utils;

// Copyright (c) 2020 Archy.

public class BigNumber {

    public static String withSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c",
                count / Math.pow(1000, exp),
                "KMBTQU".charAt(exp-1));
    }
}