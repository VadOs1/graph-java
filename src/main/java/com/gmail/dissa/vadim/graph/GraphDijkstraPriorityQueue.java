package com.gmail.dissa.vadim.graph;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GraphDijkstraPriorityQueue<T> {
    private Map<T, Map<T, Double>> adjacencyVerticesWithCost;

    public GraphDijkstraPriorityQueue() {
        this.adjacencyVerticesWithCost = new ConcurrentHashMap<>();
    }

    public void addVertex(T t) {
        adjacencyVerticesWithCost.putIfAbsent(t, new ConcurrentHashMap<>());
    }

    public void removeVertex(T t) {
        adjacencyVerticesWithCost.keySet().remove(t);
    }

    public void createEdge(T from, T to, double cost) {
        adjacencyVerticesWithCost.get(from).put(to, cost);
    }

    public void createVerticesAndEdge(T from, T to, double cost) {
        addVertex(from);
        addVertex(to);
        createEdge(from, to, cost);
    }

    public Map<T, Double> getEdges(T t) {
        return adjacencyVerticesWithCost.get(t);
    }

    public int getVertexCount() {
        return adjacencyVerticesWithCost.size();
    }

    public double findShortestPathCost(T t1, T t2) {
        if (t1 == null || t2 == null) {
            return Double.POSITIVE_INFINITY;
        }

        // add initial costs map
        Map<T, Double> costs = new HashMap<>();
        adjacencyVerticesWithCost.keySet().forEach(v -> costs.put(v, Double.POSITIVE_INFINITY));

        // add initial parents map
        Map<T, T> parents = new HashMap<>();
        adjacencyVerticesWithCost.keySet().forEach(v -> parents.put(v, null));

        // add set to store visited elements
        Set<T> visited = new HashSet<>();

        // send element to the queue
        PriorityQueue<Q> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(q -> q.priority));
        priorityQueue.add(new Q(t1, 0));
        costs.put(t1, 0.0);


        while (!priorityQueue.isEmpty()) {
            Q q = priorityQueue.poll();
            T t = q.t;
            if (!visited.contains(t)) {
                visited.add(t);
                updateCostsAndParents(t, costs, parents, priorityQueue);
            }
        }

        return costs.get(t2);
    }

    private void updateCostsAndParents(T t, Map<T, Double> costs, Map<T, T> parents, PriorityQueue<Q> priorityQueue) {
        var currentCost = costs.get(t);
        Map<T, Double> edges = getEdges(t);
        for (Map.Entry<T, Double> entry : edges.entrySet()) {
            var costToCheck = currentCost + entry.getValue();
            if (costs.get(entry.getKey()) > costToCheck) {
                costs.put(entry.getKey(), costToCheck);
                parents.put(entry.getKey(), t);
            }
            priorityQueue.add(new Q(entry.getKey(), costs.get(entry.getKey())));
        }
    }

    private class Q {
        public T t;
        public double priority;

        public Q(T t, double priority) {
            this.t = t;
            this.priority = priority;
        }
    }
}
