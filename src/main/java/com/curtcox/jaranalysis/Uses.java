package com.curtcox.jaranalysis;

import java.util.*;

public final class Uses {

    final String name;
    final String text;

    private static List<Uses> all = new ArrayList<>();

    static Uses[] values() { return all.toArray(new Uses[0]); }

    public static void add(String...uses) {
        for (String s : uses) {
            add(s);
        }
    }

    static void add(String uses) {
        String[] parts = uses.split("/");
        all.add(new Uses(parts[0],parts[1]));
    }

    Uses(String name,String text) {
        this.name = name;
        this.text = text;
    }

    @Override
    public String toString() {
        return name;
    }
}
