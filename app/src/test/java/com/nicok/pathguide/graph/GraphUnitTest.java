package com.nicok.pathguide.graph;

import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.Graph;
import com.nicok.pathguide.business_definitions.NodeDefinition;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GraphUnitTest {
    private NodeDefinition bano = createNode("000", "Bano");
    private NodeDefinition aula204 = createNode("001", "Aula 204");
    private NodeDefinition aula205 = createNode("002", "Aula 205");
    private NodeDefinition aula206 = createNode("003", "Aula 206");
    private NodeDefinition beacon01 = createNode("004", "Beacon 01");

    private List<NodeDefinition> nodes = new ArrayList<>();

    public GraphUnitTest(){
        nodes.add(bano);
        nodes.add(aula204);
        nodes.add(aula205);
        nodes.add(aula206);
        nodes.add(beacon01);
    }

    private List<EdgeDefinition> edges;

    private NodeDefinition createNode(String id, String description) {
        NodeDefinition node = new NodeDefinition();
        node.id = id;
        node.description = description;

        return node;
    }

    private EdgeDefinition createEdge(NodeDefinition fromNode, NodeDefinition toNode) {
        EdgeDefinition edge = new EdgeDefinition();
        edge.fromNode = fromNode;
        edge.toNode = toNode;

        return edge;
    }

    @Before
    public void reset() {
        edges = new ArrayList<>();
    }

    @Test
    public void should_bano_be_properly_connected() {
        this.edges.add(createEdge(bano, beacon01));

        this.edges.add(createEdge(aula204, beacon01));
        this.edges.add(createEdge(aula204, aula205));
        this.edges.add(createEdge(aula204, aula206));

        this.edges.add(createEdge(aula205, beacon01));
        this.edges.add(createEdge(aula205, aula204));
        this.edges.add(createEdge(aula205, aula206));

        this.edges.add(createEdge(aula206, beacon01));
        this.edges.add(createEdge(aula206, aula204));
        this.edges.add(createEdge(aula206, aula205));

        this.edges.add(createEdge(beacon01, bano));
        this.edges.add(createEdge(beacon01, aula204));
        this.edges.add(createEdge(beacon01, aula205));
        this.edges.add(createEdge(beacon01, aula206));

        Graph g = new Graph(nodes, edges);
        g.calculateDistanceFrom(bano);
        assertEquals(1, (long)g.getDistanceTo(beacon01));
        assertEquals(2, (long)g.getDistanceTo(aula204));
        assertEquals(2, (long)g.getDistanceTo(aula205));
        assertEquals(2, (long)g.getDistanceTo(aula206));
        assertEquals(0, (long)g.getDistanceTo(bano));
    }

    @Test
    public void should_bano_be_properly_connected_big_graph() {
        this.edges.add(createEdge(bano, beacon01));
        this.edges.add(createEdge(beacon01, aula204));
        this.edges.add(createEdge(aula204, aula205));
        this.edges.add(createEdge(aula205, aula206));

        Graph g = new Graph(nodes, edges);
        g.calculateDistanceFrom(bano);
        assertEquals(0, (long)g.getDistanceTo(bano));
        assertEquals(1, (long)g.getDistanceTo(beacon01));
        assertEquals(2, (long)g.getDistanceTo(aula204));
        assertEquals(3, (long)g.getDistanceTo(aula205));
        assertEquals(4, (long)g.getDistanceTo(aula206));
    }

    @Test
    public void bidirectional_connection() {
        this.edges.add(createEdge(bano, beacon01));
        this.edges.add(createEdge(beacon01, bano));

        Graph g = new Graph(nodes, edges);
        g.calculateDistanceFrom(bano);
        assertEquals(1, (long)g.getDistanceTo(beacon01));

        g.resetDistance();

        g.calculateDistanceFrom(beacon01);
        assertEquals(1, (long)g.getDistanceTo(bano));
    }

    @Test
    public void should_set_smaller_distance() {
        this.edges.add(createEdge(bano, beacon01));
        this.edges.add(createEdge(beacon01, aula204));
        this.edges.add(createEdge(aula204, aula205));
        this.edges.add(createEdge(aula205, aula206));
        this.edges.add(createEdge(bano, aula206));

        Graph g = new Graph(nodes, edges);
        g.calculateDistanceFrom(bano);
        assertEquals(1, (long)g.getDistanceTo(aula206));
    }

//    @Test
//    public void as() {
//        this.edges.add(createEdge(bano, beacon01));
//        this.edges.add(createEdge(beacon01, aula204));
//        this.edges.add(createEdge(aula204, aula205));
//        this.edges.add(createEdge(aula205, aula206));
//        this.edges.add(createEdge(bano, aula206));
//
//        Graph g = new Graph(nodes, edges);
//        //g.generatePath(bano, beacon01);
//        assertEquals(1, (long)g.getDistanceTo(aula206));
//    }
}