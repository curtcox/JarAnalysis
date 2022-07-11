package com.curtcox.jaranalysis;

import java.util.*;

final class Partitioning {

    private List<Set<Class>> partitions = new ArrayList<>();

    void ensureInSamePartition(Class a, Class b) {
        Set<Class> aIn = findPartitionContaining(a);
        Set<Class> bIn = findPartitionContaining(b);
        if (aIn==null && bIn==null) {
            addBothToNewPartition(a,b);
        }
        if (aIn!=null && bIn!=null) {
            combinePartitions(aIn,bIn);
        }
        if (aIn!=null && bIn==null) {
            aIn.add(b);
        }
        if (aIn==null && bIn!=null) {
            bIn.add(a);
        }
    }

    private void combinePartitions(Set<Class> a, Set<Class> b) {
        a.addAll(b);
        partitions.remove(b);
    }

    private void addBothToNewPartition(Class a, Class b) {
        Set<Class> partition = new TreeSet<>();
        partition.add(a);
        partition.add(b);
        partitions.add(partition);
    }

    private Set<Class> findPartitionContaining(Class c) {
        for (Set<Class> partition : partitions) {
            if (partition.contains(c)) {
                return partition;
            }
        }
        return null;
    }

    public int partitionNumberFor(Class c) {
        for (int i = 0; i<partitions.size(); i++) {
            Set<Class> partition = partitions.get(i);
            if (partition.contains(c)) {
                return rankFor(partition);
            }
        }
        return -1;
    }

    private int rankFor(Set<Class> partition) {
        int rank = 1;
        for (Set<Class> other : partitions) {
            if (partition != other && secondIsRankedHigher(partition, other)) {
                rank = rank + 1;
            }
        }
        return rank;
    }

    private boolean secondIsRankedHigher(Set<Class> first, Set<Class> second) {
        return secondIsBigger(first,second) || (sameSize(first,second) && secondSortsFirst(first,second));
    }

    private boolean sameSize(Set<Class> first, Set<Class> second) {
        return first.size() == second.size();
    }

    private boolean secondIsBigger(Set<Class> first, Set<Class> second) {
        return first.size()<second.size();
    }

    private boolean secondSortsFirst(Set<Class> first, Set<Class> second) {
        return first.iterator().next().compareTo(second.iterator().next()) > 0;
    }

}
