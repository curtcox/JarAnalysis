package com.curtcox.jaranalysis;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartitioningTest {

    Class a = named("a");
    Class b = named("b");
    Class c = named("c");
    Class d = named("d");
    Class e = named("e");
    Class f = named("f");
    Class g = named("g");
    Class h = named("h");
    Class i = named("i");
    Class j = named("j");
    Class k = named("k");

    static Class named(String name) {
        return Class.forName(name,"");
    }
    @Test
    public void can_create() {
        assertNotNull(new Partitioning());
    }

    @Test
    public void one_partition_with_a_b_c() {
        Partitioning partitioning = new Partitioning();

        partitioning.ensureInSamePartition(a,b);
        partitioning.ensureInSamePartition(b,c);

        assertEquals(1,partitioning.partitionNumberFor(a));
        assertEquals(1,partitioning.partitionNumberFor(b));
        assertEquals(1,partitioning.partitionNumberFor(c));
    }

    @Test
    public void abc_ranks_higher_than_de() {
        Partitioning partitioning = new Partitioning();

        partitioning.ensureInSamePartition(a,b);
        partitioning.ensureInSamePartition(b,c);
        partitioning.ensureInSamePartition(d,e);

        assertEquals(1,partitioning.partitionNumberFor(a));
        assertEquals(2,partitioning.partitionNumberFor(d));
    }

    @Test
    public void abcd_ranks_higher_than_efg_ranks_higher_than_hi() {
        Partitioning partitioning = new Partitioning();

        partitioning.ensureInSamePartition(a,b);
        partitioning.ensureInSamePartition(b,c);
        partitioning.ensureInSamePartition(c,d);

        partitioning.ensureInSamePartition(e,f);
        partitioning.ensureInSamePartition(f,g);

        partitioning.ensureInSamePartition(h,i);

        assertEquals(1,partitioning.partitionNumberFor(a));
        assertEquals(2,partitioning.partitionNumberFor(e));
        assertEquals(3,partitioning.partitionNumberFor(h));
    }

    @Test
    public void abcd_ranks_higher_than_efg_ranks_higher_than_hi_and_jk() {
        Partitioning partitioning = new Partitioning();

        partitioning.ensureInSamePartition(a,b);
        partitioning.ensureInSamePartition(b,c);
        partitioning.ensureInSamePartition(c,d);

        partitioning.ensureInSamePartition(e,f);
        partitioning.ensureInSamePartition(f,g);

        partitioning.ensureInSamePartition(h,i);

        partitioning.ensureInSamePartition(j,k);

        assertEquals(1,partitioning.partitionNumberFor(a));
        assertEquals(2,partitioning.partitionNumberFor(e));
        assertEquals(3,partitioning.partitionNumberFor(h));
        assertEquals(4,partitioning.partitionNumberFor(j));
    }

    @Test
    public void abcd_and_hijk_ranks_higher_than_efg() {
        Partitioning partitioning = new Partitioning();

        partitioning.ensureInSamePartition(a,b);
        partitioning.ensureInSamePartition(b,c);
        partitioning.ensureInSamePartition(c,d);

        partitioning.ensureInSamePartition(e,f);
        partitioning.ensureInSamePartition(f,g);

        partitioning.ensureInSamePartition(h,i);
        partitioning.ensureInSamePartition(i,j);
        partitioning.ensureInSamePartition(j,k);

        assertEquals(1,partitioning.partitionNumberFor(a));
        assertEquals(2,partitioning.partitionNumberFor(h));
        assertEquals(3,partitioning.partitionNumberFor(e));
    }

}
