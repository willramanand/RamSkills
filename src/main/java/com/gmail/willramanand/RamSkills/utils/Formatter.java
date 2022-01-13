package com.gmail.willramanand.RamSkills.utils;

// Copyright (c) 2020 Archy.

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Formatter {

    public static String decimalFormat(double input, int maxPlaces) {
        // Creates DecimalFormat pattern based on maxPlaces
        StringBuilder pattern = new StringBuilder("#");
        if (maxPlaces > 0) {
            pattern.append(".");
        }
        pattern.append(StringUtils.repeat("#", maxPlaces));
        NumberFormat numberFormat = new DecimalFormat(pattern.toString());

        return numberFormat.format(input);
    }

    public static String nameFormat(String name) {
        String formattedName = name;
        String upperFirst = formattedName.substring(0, 1).toUpperCase();
        formattedName = upperFirst + formattedName.substring(1).toLowerCase();

        return formattedName;
    }
}
