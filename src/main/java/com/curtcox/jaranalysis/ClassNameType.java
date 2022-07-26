package com.curtcox.jaranalysis;

import java.util.*;

public final class ClassNameType {

    final String name;
    final Match match;

    ClassNameType(Match match,String name) {
        this.match = match;
        this.name = name;
    }

    private static List<ClassNameType> all = new ArrayList<>();

    public static void add(String uses) {
        String[] parts = uses.split("/");
        Match match = Match.valueOf(parts[0]);
        all.add(new ClassNameType(match,parts[1]));
    }


    static ClassNameType[] values() { return all.toArray(new ClassNameType[0]); }
    static ClassNameType from(Class c) {
        for (ClassNameType t : values()) {
            if (t.match.matches(c.shortName(),t.name)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
