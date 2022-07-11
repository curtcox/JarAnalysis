package com.curtcox.jaranalysis;

import java.util.*;

final class ClassNameType {

    final String name;
    final Match match;

    ClassNameType(String name) {
        this(Match.EndsWith,name);
    }

    ClassNameType(Match match,String name) {
        this.match = match;
        this.name = name;
    }

    private static List<ClassNameType> all = new ArrayList<>();

    static List<ClassNameType> values() { return all; }
    static ClassNameType from(Class c) {
        for (ClassNameType t : values()) {
            if (t.match.matches(c.shortName(),t.name)) {
                return t;
            }
        }
        return null;
    }


}
