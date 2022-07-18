package com.curtcox.jaranalysis;

import org.junit.*;

import static org.junit.Assert.*;

public class ClassTest {

    @Test
    public void forName_with_empty_jar() {
        Class c = Class.forName("c","");
        assertEquals("c",c.fullName);
        assertEquals("",c.jar);
    }

    @Test
    public void forName_with_jar() {
        Class d = Class.forName("d","dj");
        assertEquals("d",d.fullName);
        assertEquals("dj",d.jar);
    }

}
