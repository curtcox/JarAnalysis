package com.curtcox.jaranalysis;

import org.junit.*;

import static org.junit.Assert.*;

public class ClassDependencyTest {

    @Test
    public void can_create() {
        assertNotNull(ClassDependency.from("x->y ()"));
    }

    @Test
    public void unquoted_with_empty_jar_specified() {
        ClassDependency dependency = ClassDependency.from("x->y ()");
        assertEquals(Class.forName("x"),dependency.dependent);
        assertEquals(Class.forName("y"),dependency.dependency);
    }

    @Test
    public void unquoted_with_jar_specified() {
        ClassDependency dependency = ClassDependency.from("c->d (j)");
        assertEquals(Class.forName("c"),dependency.dependent);
        assertEquals(Class.forName("d"),dependency.dependency);
        assertEquals("j",dependency.jar);
    }

}
