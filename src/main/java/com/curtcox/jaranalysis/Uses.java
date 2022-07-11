package com.curtcox.jaranalysis;

import java.util.*;

final class Uses {
    final String text;

    private static List<ClassNameType> all = new ArrayList<>();

    static List<ClassNameType> values() { return all; }

    Uses(String text) {
        this.text = text;
    }
}
