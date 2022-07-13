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

        analysis.scan(Arrays.asList(ClassDependency.from("c -> d (j)")));

        Class c = Class.forName("c");
        ClassDescription description = analysis.classes.get(Class.forName("c"));
        assertEquals(c,description.c);

        Class d = Class.forName("d");
        assertEquals("j",d.jar);
    }

}
