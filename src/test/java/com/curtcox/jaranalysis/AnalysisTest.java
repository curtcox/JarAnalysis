package com.curtcox.jaranalysis;

import org.junit.*;

import java.util.*;
import java.util.stream.*;

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

        analysis.scan(Arrays.asList(ClassDependency.from("c -> d (dj)","cj")));

        Class c = Class.forName("c",Jar.forName("cj"));
        ClassDescription description = analysis.classes.get(c);
        assertEquals(c,description.c);
        assertEquals("cj",description.c.jar.toString());
    }

    @Test
    public void scan_() {
        scan("j",
                "with_deps -> wd_d1 (j)",
                "with_deps -> wd_d2 (j)"
        );

        Class c = Class.find("with_deps");
        assertClasses(c.directDependents);
        assertClasses(c.directDependencies,"wd_d1","wd_d2");

        Class d1 = Class.find("wd_d1");
        assertClasses(d1.directDependents,"with_deps");
        assertClasses(d1.directDependencies);

        Class d2 = Class.find("wd_d2");
        assertClasses(d2.directDependents,"with_deps");
        assertClasses(d2.directDependencies);
    }

    private void assertClasses(Set<Class> set,String...classes) {
        assertEquals(classes.length,set.size());
        for (String c : classes) {
            assertTrue(set.contains(Class.find(c)));
        }
    }

    static Analysis scan(String jar, String... lines) {
        Analysis analysis = new Analysis();

        analysis.scan(Arrays.stream(lines).map(x -> ClassDependency.from(x,jar)).collect(Collectors.toList()));

        return analysis;
    }
}
