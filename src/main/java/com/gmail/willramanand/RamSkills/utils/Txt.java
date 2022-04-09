package com.gmail.willramanand.RamSkills.utils;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class Txt {

    private static final Map<String, String> alternateChars = new HashMap<>();
    private static final char COLOR_CHAR = ChatColor.COLOR_CHAR;

    public static String parse(String message) {
        return replaceAlternateChars(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static String[] parse(String... messages) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = replaceAlternateChars(ChatColor.translateAlternateColorCodes('&', messages[i]));
        }
        return messages;
    }

    public static TextComponent parse(TextComponent component) {
        String componentString = component.content();
        componentString = replaceAlternateChars(ChatColor.translateAlternateColorCodes('&', componentString));
        return component.content(componentString);
    }

    public static String header(String headerTitle) {
        return parse("{gold}{str}                  {r}{aqua}{bold} " + headerTitle + " {r}{gold}{str}                  ");
    }

    public static String replace(String message, String... replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            message = message.replace(replacements[i], replacements[i + 1]);
        }
        return message;
    }


    private static String replaceAlternateChars(String message) {
        if (alternateChars.isEmpty()) {
            setupAlternateCodes();
        }

        for (String s : alternateChars.keySet()) {
            if (message.contains(s)) {
                message = message.replace(s, COLOR_CHAR + alternateChars.get(s));
            }
        }

        return message;
    }


    public static void setupAlternateCodes() {
        alternateChars.put("{darkred}", "4");
        alternateChars.put("{dred}", "4");
        alternateChars.put("{red}", "c");
        alternateChars.put("{w}", "c");
        alternateChars.put("{gold}", "6");
        alternateChars.put("{yellow}", "e");
        alternateChars.put("{ye}", "e");
        alternateChars.put("{s}", "e");
        alternateChars.put("{darkgreen}", "2");
        alternateChars.put("{dgr}", "2");
        alternateChars.put("{green}", "a");
        alternateChars.put("{gr}", "a");
        alternateChars.put("{aqua}", "b");
        alternateChars.put("{aq}", "b");
        alternateChars.put("{darkaqua}", "3");
        alternateChars.put("{daq}", "3");
        alternateChars.put("{darkblue}", "1");
        alternateChars.put("{dbl}", "1");
        alternateChars.put("{blue}", "9");
        alternateChars.put("{bl}", "9");
        alternateChars.put("{purple}", "d");
        alternateChars.put("{pur}", "d");
        alternateChars.put("{h}", "d");
        alternateChars.put("{darkpurple}", "5");
        alternateChars.put("{dpur}", "5");
        alternateChars.put("{white}", "f");
        alternateChars.put("{whi}", "f");
        alternateChars.put("{gray}", "7");
        alternateChars.put("{darkgray}", "8");
        alternateChars.put("{dgray}", "8");
        alternateChars.put("{black}", "0");
        alternateChars.put("{bla}", "0");

        alternateChars.put("{obf}", "k");
        alternateChars.put("{bold}", "l");
        alternateChars.put("{str}", "m");
        alternateChars.put("{ul}", "n");
        alternateChars.put("{it}", "o");
        alternateChars.put("{r}", "r");
    }
}
