package com.curtcox.jaranalysis;

import org.junit.*;

import static org.junit.Assert.*;

public class ClassTest {

    Class c = Class.forName("cn",Jar.forName(""));
    Class d = Class.forName("dn",Jar.forName("dj"));
    Class cImpl = Class.forName("cnImpl",Jar.forName(""));

    @Test
    public void forName_with_empty_jar() {
        assertEquals("cn",c.fullName);
    }

    @Test
    public void forName_with_jar() {
        assertEquals("dn",d.fullName);
        assertEquals("dj",d.jar.toString());
    }

    @Test
    public void shortName() {
        assertEquals("cn",c.shortName());
        assertEquals("dn",d.shortName());
        assertEquals("cnImpl",cImpl.shortName());
    }

    @Test
    public void class_shows_not_implementing_class_based_on_name() {
        assertFalse(c.implementsDependency(d));
        assertFalse(d.implementsDependency(c));
        assertFalse(c.implementsDependency(c));
        assertFalse(d.implementsDependency(d));
        assertFalse(cImpl.implementsDependency(d));
    }

    @Test
    public void class_shows_implements_class_based_on_name() {
        assertTrue(cImpl.implementsDependency(c));
    }

}
