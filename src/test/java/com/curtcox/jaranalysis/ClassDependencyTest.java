package com.curtcox.jaranalysis;

import org.junit.*;

import static org.junit.Assert.*;

public class ClassDependencyTest {

    @Test
    public void can_create() {
        assertNotNull(ClassDependency.from("x->y ()",""));
    }

    @Test
    public void unquoted_with_empty_jar_specified() {
        ClassDependency dependency = ClassDependency.from("x->y ()","");
        assertEquals(Class.forName("x",Jar.forName("")),dependency.dependent);
        assertEquals(Class.forName("y",Jar.forName("")),dependency.dependency);
    }

    @Test
    public void unquoted_with_jar_specified() {
        ClassDependency dependency = ClassDependency.from("e->f (j)","k");
        assertEquals(Class.forName("e",Jar.forName("k")),dependency.dependent);
        assertEquals(Class.forName("f",Jar.forName("j")),dependency.dependency);
    }

}
