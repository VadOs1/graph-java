package com.gmail.dissa.vadim.graph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation:
 * - call addPackage
 * - call removePackage
 */
public class Graph<T> {
    private final Map<T, Set<T>> adjacencyVertices;

    public Graph() {
        adjacencyVertices = new ConcurrentHashMap<>();
    }

    public synchronized boolean addPackage(T t, Set<T> set) {
        if (t == null) {
            throw new IllegalArgumentException("Package not provided");
        }
        if (set == null) {
            set = new HashSet<>();
        }
        if (set.contains(t)) {
            throw new IllegalArgumentException("Package can not have dependency on itself");
        }

        addVertex(t);
        for (T t1 : set) {
            addVertex(t1);
            adjacencyVertices.get(t).add(t1);
        }
        return true;
    }

    public synchronized boolean removePackage(T t) {
        if (adjacencyVertices.get(t) == null) {
            throw new IllegalArgumentException("Vertex doesn't exist");
        }

        if (isReferenced(t)) {
            return false;
        } else {
            Set<T> edges = adjacencyVertices.get(t);
            adjacencyVertices.keySet().remove(t);
            for (T edge : edges) {
                removePackage(edge);
            }
            return true;
        }
    }

    private void addVertex(T t) {
        adjacencyVertices.putIfAbsent(t, ConcurrentHashMap.newKeySet());
    }

    private boolean isReferenced(T t) {
        for (Map.Entry<T, Set<T>> entry : adjacencyVertices.entrySet()) {
            if (entry.getValue().contains(t)) {
                return true;
            }
        }
        return false;
    }
}
