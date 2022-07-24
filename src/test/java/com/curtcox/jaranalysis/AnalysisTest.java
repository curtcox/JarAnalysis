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
    public void class_depends_on_one_implementing_class() {
        Analysis analysis = new Analysis();

        analysis.scan(Arrays.asList(ClassDependency.from("c -> d (dj)","cj")));

        Class c = Class.forName("c",Jar.forName("cj"));
        ClassDescription description = analysis.classes.get(c);
        assertEquals(c,description.c);
        assertEquals("cj",description.c.jar.toString());
    }

    @Test
    public void scan_class_with_2_dependencies() {
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

    @Test
    public void simple_dependency_chain_produces_correct_dependnecies() {
        scan("j",
                "chain_1 -> chain_2 (j)",
                "chain_2 -> chain_3 (j)",
                "chain_3 -> chain_4 (j)"
        );

        Class c1 = Class.find("chain_1");
        assertClasses(c1.directDependents);
        assertClasses(c1.directDependencies,"chain_2");

        Class c2 = Class.find("chain_2");
        assertClasses(c2.directDependents,"chain_1");
        assertClasses(c2.directDependencies,"chain_3");

        Class c3 = Class.find("chain_3");
        assertClasses(c3.directDependents,"chain_2");
        assertClasses(c3.directDependencies,"chain_4");

        Class c4 = Class.find("chain_4");
        assertClasses(c4.directDependents,"chain_3");
        assertClasses(c4.directDependencies);
    }

    @Test
    public void dependency_set() {
        Analysis analysis = scan("j",
                "chain_1 -> chain_2 (j)",
                "chain_2 -> chain_3 (j)",
                "chain_3 -> chain_4 (j)"
        );

        Set<ClassDependency> set = analysis.dependencyTree(Class.find("chain_1"));

        assertEquals(3,set.size());
        assertTrue(set.contains(dependency("chain_1","chain_2")));
        assertTrue(set.contains(dependency("chain_2","chain_3")));
        assertTrue(set.contains(dependency("chain_3","chain_4")));
    }

    private ClassDependency dependency(String a, String b) {
        return ClassDependency.direct(Class.find(a),Class.find(b));
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
