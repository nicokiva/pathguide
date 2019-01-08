package com.nicok.pathguide.graph;

import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GraphUnitTest {
    private NodeDefinition createNode(String id, String description) {
        NodeDefinition node = new NodeDefinition();
        node.id = id;
        node.description = description;

        return node;
    }

    @Test
    public void graph_is_loaded() {
        Node bano = new Node(createNode("000", "Bano"));
        Node aula204 = new Node(createNode("001", "Aula 204"));
        Node aula205 = new Node(createNode("002", "Aula 205"));
        Node aula206 = new Node(createNode("003", "Aula 206"));
        Node beacon01 = new Node(createNode("004", "Beacon 01"));

        Node[] nodes = {
            bano,
            aula204,
            aula205,
            aula206,
            beacon01
        };

        Edge[] edges = {
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
        g.printResult();
    }
}