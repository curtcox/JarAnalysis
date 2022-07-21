package com.curtcox.jaranalysis;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

final class CSV {
    static String from(Object... values) { return valuesMapper(values, Object::toString); }

    static String valuesSet(Object[] values, Set<?> set) { return valuesMapper(values, (v) -> set.contains(v) ? v.toString() : ""); }

    static String valuesSetCheck(Object[] values, Set<?> set) { return valuesMapper(values, (v) -> set.contains(v) ? "X" : ""); }

    static String valuesMapper(Object[] values, Function<Object,String> mapper) {
        return Arrays.stream(values).map(mapper).collect(Collectors.joining(","));
    }

}
