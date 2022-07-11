package com.curtcox.jaranalysis;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartitioningTest {

    Class a = Class.forName("a");
    Class b = Class.forName("b");
    Class c = Class.forName("c");
    Class d = Class.forName("d");
    Class e = Class.forName("e");
    Class f = Class.forName("f");
    Class g = Class.forName("g");
    Class h = Class.forName("h");
    Class i = Class.forName("i");
    Class j = Class.forName("j");
    Class k = Class.forName("k");

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
