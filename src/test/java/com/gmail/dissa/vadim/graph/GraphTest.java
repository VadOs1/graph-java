package com.gmail.dissa.vadim.graph;

import com.gmail.dissa.vadim.graph.model.AppPackage;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GraphTest {
    private static final AppPackage PACKAGE_1 = new AppPackage("package 1");
    private static final AppPackage PACKAGE_2 = new AppPackage("package 2");
    private static final AppPackage PACKAGE_3 = new AppPackage("package 3");
    private static final AppPackage PACKAGE_4 = new AppPackage("package 4");
    private static final AppPackage PACKAGE_5 = new AppPackage("package 5");

    @Test
    public void testShouldAddPackageWithoutDependenciesToGraph(){
        // GIVEN
        Graph<AppPackage> graph = new Graph<>();

        // WHEN
        graph.addPackage(PACKAGE_1, null);

        // THEN
        Map<AppPackage, Set<AppPackage>> adjacencyVertices = graph.getGraph();
        assertNotNull(adjacencyVertices);
        assertEquals(1, adjacencyVertices.size());
        assertEquals(0, adjacencyVertices. get(PACKAGE_1).size());
    }
}
