package com.curtcox.jaranalysis;

enum Match {
    EndsWith   { @Override public boolean matches(String c, String text) { return c.endsWith(text); } },
    EndsWithIgnoringCase   { @Override public boolean matches(String c, String text) { return c.toLowerCase().endsWith(text.toLowerCase()); } },
    StartsWith { @Override public boolean matches(String c, String text) { return c.startsWith(text); } },
    Contains   { @Override public boolean matches(String c, String text) { return c.contains(text); } },
    ;

    public abstract boolean matches(String c, String text);

}
