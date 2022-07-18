package com.curtcox.jaranalysis;

import org.junit.*;

import static org.junit.Assert.*;

public class ClassTest {

    @Test
    public void forName_with_empty_jar() {
        Class c = Class.forName("cn","");
        assertEquals("cn",c.fullName);
    }

    @Test
    public void forName_with_jar() {
        Class d = Class.forName("dn","dj");
        assertEquals("dn",d.fullName);
        assertEquals("dj",d.jar);
    }

}