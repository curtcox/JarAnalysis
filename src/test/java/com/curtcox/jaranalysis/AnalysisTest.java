package com.curtcox.jaranalysis;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class AnalysisTest {

    @Test
    public void can_create() {
        assertNotNull(new Analysis());
    }

    @Test
    public void classes_are_empty_when_no_dependencies_have_been_scanned() {
        assertTrue(new Analysis().classes.isEmpty());
    }

    @Test
    public void description_from_one_dependency() {
        Analysis analysis = new Analysis();

        analysis.scan(Arrays.asList(ClassDependency.from("a -> b (j)")));

        Class c = Class.forName("a");
        ClassDescription description = analysis.classes.get(c);
        assertEquals(c,description.c);
        assertEquals("j",description.jar());
    }

}
