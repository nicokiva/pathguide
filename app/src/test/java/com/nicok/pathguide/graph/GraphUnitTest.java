package com.nicok.pathguide.graph;

import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.Graph;
import com.nicok.pathguide.business_definitions.NodeDefinition;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class GraphUnitTest {
    private NodeDefinition bano = new NodeDefinition("000", "Bano");
    private NodeDefinition aula204 = new NodeDefinition("001", "Aula 204");
    private NodeDefinition aula205 = new NodeDefinition("002", "Aula 205");
    private NodeDefinition aula206 = new NodeDefinition("003", "Aula 206");
    private NodeDefinition beacon01 = new NodeDefinition("004", "Beacon 01");

    private EdgeDefinition banoToBeacon01 = createEdge("Baño to Beacon 01!");
    private EdgeDefinition beacon01ToAula204 = createEdge("Beacon 01 to Aula 204!");
    private EdgeDefinition beacon01ToAula206 = createEdge("Beacon 01 to Aula 206!");
    private EdgeDefinition beacon01ToBano = createEdge("Beacon 01 to Baño!");
    private EdgeDefinition aula204ToAula205 = createEdge("Aula 204 to Aula 205!");
    private EdgeDefinition banoToAula205 = createEdge("Baño to Aula 205!");
    private EdgeDefinition banoToAula206 = createEdge("Baño to Aula 206!");
    private EdgeDefinition aula204ToAula206 = createEdge("Aula 204 to Aula 206!");
    private EdgeDefinition aula206ToBeacon01 = createEdge("Aula 206 to Beacon 01!");


    private EdgeDefinition createEdge(String instructions) {
        EdgeDefinition edge = new EdgeDefinition();
        edge.instructions = instructions;

        return edge;
    }

    @Test
    public void should_bano_be_properly_connected_to_beacon01_in_one_step() {
        bano.addDestination(beacon01, banoToBeacon01);
        List<NodeDefinition> nodes = new ArrayList<>();

        nodes.add(bano);
        nodes.add(beacon01);

        Graph graph = new Graph(nodes);
        long steps = graph.calculateShortestPath(bano, beacon01);
        EdgeDefinition edge = graph.updateNodeAndGetInstructions(bano);

        assertEquals(steps, 1);
        assertEquals(edge, banoToBeacon01);
    }


    @Test
    public void should_bano_be_properly_connected_to_aula204_in_two_steps() {
        bano.addDestination(beacon01, banoToBeacon01);
        beacon01.addDestination(aula204, beacon01ToAula204);
        List<NodeDefinition> nodes = new ArrayList<>();

        nodes.add(bano);
        nodes.add(beacon01);
        nodes.add(aula204);

        Graph graph = new Graph(nodes);
        long steps = graph.calculateShortestPath(bano, aula204);
        assertEquals(steps, 2);
        EdgeDefinition edge = graph.updateNodeAndGetInstructions(bano);

        assertEquals(edge, banoToBeacon01);

        edge = graph.updateNodeAndGetInstructions(beacon01);
        assertEquals(edge, beacon01ToAula204);
    }

    @Test
    public void should_bano_be_properly_connected_to_aula206_in_three_steps() {
        bano.addDestination(beacon01, banoToBeacon01);
        beacon01.addDestination(aula204, beacon01ToAula204);
        aula204.addDestination(aula206, aula204ToAula206);
        List<NodeDefinition> nodes = new ArrayList<>();

        nodes.add(bano);
        nodes.add(beacon01);
        nodes.add(aula204);
        nodes.add(aula206);

        Graph graph = new Graph(nodes);
        long steps = graph.calculateShortestPath(bano, aula206);
        assertEquals(steps, 3);
        EdgeDefinition edge = graph.updateNodeAndGetInstructions(bano);

        assertEquals(edge, banoToBeacon01);

        edge = graph.updateNodeAndGetInstructions(beacon01);
        assertEquals(edge, beacon01ToAula204);

        edge = graph.updateNodeAndGetInstructions(aula204);
        assertEquals(edge, aula204ToAula206);
    }


    @Test
    public void should_bano_be_properly_connected_to_aula206_faster_in_one_step() {
        bano.addDestination(beacon01, banoToBeacon01);
        beacon01.addDestination(aula204, beacon01ToAula204);
        aula204.addDestination(aula206, aula204ToAula206);
        bano.addDestination(aula206, banoToAula206);
        List<NodeDefinition> nodes = new ArrayList<>();

        nodes.add(bano);
        nodes.add(beacon01);
        nodes.add(aula204);
        nodes.add(aula206);

        Graph graph = new Graph(nodes);
        long steps = graph.calculateShortestPath(bano, aula206);
        assertEquals(steps, 1);
        EdgeDefinition edge = graph.updateNodeAndGetInstructions(bano);

        assertEquals(edge, banoToAula206);
    }

    @Test
    public void should_bano_be_connected_to_aula_206_in_two_steps_and_aula_206_to_aula_204_in_two_steps() {
        bano.addDestination(beacon01, banoToBeacon01);
        beacon01.addDestination(aula204, beacon01ToAula204);
        beacon01.addDestination(aula206, beacon01ToAula206);
        aula204.addDestination(aula206, aula204ToAula206);
        aula206.addDestination(beacon01, aula206ToBeacon01);
        List<NodeDefinition> nodes = new ArrayList<>();

        nodes.add(bano);
        nodes.add(beacon01);
        nodes.add(aula204);
        nodes.add(aula206);

        Graph graph = new Graph(nodes);

        long steps = graph.calculateShortestPath(bano, aula206);
        assertEquals(steps, 2);
        EdgeDefinition edge = graph.updateNodeAndGetInstructions(bano);
        assertEquals(edge, banoToBeacon01);

        edge = graph.updateNodeAndGetInstructions(beacon01);
        assertEquals(edge, beacon01ToAula206);


        steps = graph.calculateShortestPath(aula206, aula204);
        assertEquals(steps, 2);
        edge = graph.updateNodeAndGetInstructions(aula206);
        assertEquals(edge, aula206ToBeacon01);

        edge = graph.updateNodeAndGetInstructions(beacon01);
        assertEquals(edge, beacon01ToAula204);
    }

    @Test
    public void should_bano_and_beacon_be_properly_connected_in_two_ways() {
        bano.addDestination(beacon01, banoToBeacon01);
        beacon01.addDestination(bano, beacon01ToBano);
        List<NodeDefinition> nodes = new ArrayList<>();

        nodes.add(bano);
        nodes.add(beacon01);

        Graph graph = new Graph(nodes);

        // Go from bathroom to beacon
        long steps = graph.calculateShortestPath(bano, beacon01);
        assertEquals(steps, 1);
        EdgeDefinition edge = graph.updateNodeAndGetInstructions(bano);
        assertEquals(edge, banoToBeacon01);

        // Return from beacon to bathroom
        steps = graph.calculateShortestPath(beacon01, bano);
        assertEquals(steps, 1);
        edge = graph.updateNodeAndGetInstructions(beacon01);
        assertEquals(edge, beacon01ToBano);

        // Again from bathroom to beacon
        steps = graph.calculateShortestPath(bano, beacon01);
        assertEquals(steps, 1);
        edge = graph.updateNodeAndGetInstructions(bano);
        assertEquals(edge, banoToBeacon01);
    }

}