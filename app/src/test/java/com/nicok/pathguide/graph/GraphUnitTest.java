package com.nicok.pathguide.graph;

import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphUnitTest {
    private Node bano = new Node(createNode("000", "Bano"));
    private Node aula204 = new Node(createNode("001", "Aula 204"));
    private Node aula205 = new Node(createNode("002", "Aula 205"));
    private Node aula206 = new Node(createNode("003", "Aula 206"));
    private Node beacon01 = new Node(createNode("004", "Beacon 01"));

    private Node[] nodes = {
        bano,
        aula204,
        aula205,
        aula206,
        beacon01
    };

    private Edge[] edges;

    private NodeDefinition createNode(String id, String description) {
        NodeDefinition node = new NodeDefinition();
        node.id = id;
        node.description = description;

        return node;
    }

    @Before
    public void reset() {
        edges = null;
    }

    @Test
    public void should_bano_be_properly_connected() {
        this.edges = new Edge[]{
            new Edge(bano, beacon01, new EdgeDefinition()),

            new Edge(aula204, beacon01, new EdgeDefinition()),
            new Edge(aula204, aula205, new EdgeDefinition()),
            new Edge(aula204, aula206, new EdgeDefinition()),

            new Edge(aula205, beacon01, new EdgeDefinition()),
            new Edge(aula205, aula204, new EdgeDefinition()),
            new Edge(aula205, aula206, new EdgeDefinition()),

            new Edge(aula206, beacon01, new EdgeDefinition()),
            new Edge(aula206, aula204, new EdgeDefinition()),
            new Edge(aula206, aula205, new EdgeDefinition()),

            new Edge(beacon01, bano, new EdgeDefinition()),
            new Edge(beacon01, aula204, new EdgeDefinition()),
            new Edge(beacon01, aula205, new EdgeDefinition()),
            new Edge(beacon01, aula206, new EdgeDefinition()),
        };

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
        this.edges = new Edge[]{
            new Edge(bano, beacon01, new EdgeDefinition()),
            new Edge(beacon01, aula204, new EdgeDefinition()),
            new Edge(aula204, aula205, new EdgeDefinition()),
            new Edge(aula205, aula206, new EdgeDefinition()),
        };

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
        this.edges = new Edge[]{
                new Edge(bano, beacon01, new EdgeDefinition()),
                new Edge(beacon01, bano, new EdgeDefinition())
        };

        Graph g = new Graph(nodes, edges);
        g.calculateDistanceFrom(bano);
        assertEquals(1, (long)g.getDistanceTo(beacon01));

        g.resetDistance();

        g.calculateDistanceFrom(beacon01);
        assertEquals(1, (long)g.getDistanceTo(bano));
    }

    @Test
    public void should_set_smaller_distance() {
        this.edges = new Edge[]{
            new Edge(bano, beacon01, new EdgeDefinition()),
            new Edge(beacon01, aula204, new EdgeDefinition()),
            new Edge(aula204, aula205, new EdgeDefinition()),
            new Edge(aula205, aula206, new EdgeDefinition()),

            new Edge(bano, aula206, new EdgeDefinition()),
        };

        Graph g = new Graph(nodes, edges);
        g.calculateDistanceFrom(bano);
        assertEquals(1, (long)g.getDistanceTo(aula206));
    }
}