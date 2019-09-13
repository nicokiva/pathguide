package com.nicok.pathguide.graph;

import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.Graph;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GraphUnitTest {
    private NodeDefinition bano = new NodeDefinition("000", "Bano");
    private NodeDefinition aula204 = new NodeDefinition("001", "Aula 204");
    private NodeDefinition aula205 = new NodeDefinition("002", "Aula 205");
    private NodeDefinition aula206 = new NodeDefinition("003", "Aula 206");
    private NodeDefinition beacon01 = new NodeDefinition("004", "Beacon 01");

    private EdgeDefinition banoToBeacon01 = createEdge("Baño to Beacon 01!", 1);
    private EdgeDefinition beacon01ToAula204 = createEdge("Beacon 01 to Aula 204!", 2);
    private EdgeDefinition beacon01ToAula205 = createEdge("Beacon 01 to Aula 205!", 3);
    private EdgeDefinition beacon01ToAula206 = createEdge("Beacon 01 to Aula 206!", 4);
    private EdgeDefinition beacon01ToBano = createEdge("Beacon 01 to Baño!", 1);
    private EdgeDefinition aula204ToBeacon01 = createEdge("Aula 204 to Beacon 01!", 2);
    private EdgeDefinition aula204ToAula205 = createEdge("Aula 204 to Aula 205!", 5);
    private EdgeDefinition aula204ToAula206 = createEdge("Aula 204 to Aula 206!", 6);
    private EdgeDefinition aula205ToBeacon01 = createEdge("Aula 205 to Beacon 01!", 3);
    private EdgeDefinition aula205ToAula204 = createEdge("Aula 205 to Aula 204!", 5);
    private EdgeDefinition aula205ToAula206 = createEdge("Aula 205 to Aula 206!", 7);
    private EdgeDefinition aula206ToBeacon01 = createEdge("Aula 206 to Beacon 01!", 4);
    private EdgeDefinition aula206ToAula204 = createEdge("Aula 206 to Aula 204!", 6);
    private EdgeDefinition aula206ToAula205 = createEdge("Aula 206 to Aula 205!", 7);

    private EdgeDefinition banoToAula206 = createEdge("Baño to Aula 206!", 8);

    private EdgeDefinition createEdge(String instructions, Integer distance) {
        EdgeDefinition edge = new EdgeDefinition();
        edge.instructions = instructions;
        edge.distance = distance;

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
        graph.updateCurrentLocation(bano);
        EdgeDefinition edge = graph.getInstructionsTo(bano);

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

        graph.updateCurrentLocation(bano);
        EdgeDefinition edge = graph.getInstructionsTo(bano);

        assertEquals(edge, banoToBeacon01);

        graph.updateCurrentLocation(beacon01);
        edge = graph.getInstructionsTo(beacon01);
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
        graph.updateCurrentLocation(bano);
        EdgeDefinition edge = graph.getInstructionsTo(bano);

        assertEquals(edge, banoToBeacon01);

        graph.updateCurrentLocation(beacon01);
        edge = graph.getInstructionsTo(beacon01);
        assertEquals(edge, beacon01ToAula204);

        graph.updateCurrentLocation(aula204);
        edge = graph.getInstructionsTo(aula204);
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
        graph.updateCurrentLocation(bano);
        EdgeDefinition edge = graph.getInstructionsTo(bano);

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
        graph.updateCurrentLocation(bano);
        EdgeDefinition edge = graph.getInstructionsTo(bano);
        assertEquals(edge, banoToBeacon01);

        graph.updateCurrentLocation(beacon01);
        edge = graph.getInstructionsTo(beacon01);
        assertEquals(edge, beacon01ToAula206);


        steps = graph.calculateShortestPath(aula206, aula204);
        assertEquals(steps, 2);

        graph.updateCurrentLocation(aula206);
        edge = graph.getInstructionsTo(aula206);
        assertEquals(edge, aula206ToBeacon01);

        graph.updateCurrentLocation(beacon01);
        edge = graph.getInstructionsTo(beacon01);
        assertEquals(edge, beacon01ToAula204);
    }

    @Test
    public void should_bano_be_connected_to_aula_206_in_two_steps() {
        bano.addDestination(beacon01, banoToBeacon01);
        beacon01.addDestination(bano, beacon01ToBano);

        beacon01.addDestination(aula204, beacon01ToAula204);
        beacon01.addDestination(aula205, beacon01ToAula205);
        beacon01.addDestination(aula206, beacon01ToAula206);

        aula204.addDestination(aula205, aula204ToAula205);
        aula204.addDestination(aula206, aula204ToAula206);
        aula204.addDestination(beacon01, aula204ToBeacon01);

        aula205.addDestination(aula204, aula205ToAula204);
        aula205.addDestination(aula206, aula205ToAula206);
        aula205.addDestination(beacon01, aula205ToBeacon01);

        aula206.addDestination(aula204, aula206ToAula204);
        aula206.addDestination(aula205, aula206ToAula205);
        aula206.addDestination(beacon01, aula206ToBeacon01);
        List<NodeDefinition> nodes = new ArrayList<>();

        nodes.add(bano);
        nodes.add(beacon01);
        nodes.add(aula204);
        nodes.add(aula205);
        nodes.add(aula206);

        Graph graph = new Graph(nodes);

        long steps = graph.calculateShortestPath(aula206, bano);
        assertEquals(steps, 2);
        graph.updateCurrentLocation(aula206);
        EdgeDefinition edge = graph.getInstructionsTo(aula206);
        assertEquals(edge, aula206ToBeacon01);

        graph.updateCurrentLocation(beacon01);
        edge = graph.getInstructionsTo(beacon01);
        assertEquals(edge, beacon01ToBano);
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
        graph.updateCurrentLocation(bano);
        EdgeDefinition edge = graph.getInstructionsTo(bano);
        assertEquals(edge, banoToBeacon01);

        // Return from beacon to bathroom
        steps = graph.calculateShortestPath(beacon01, bano);
        assertEquals(steps, 1);
        graph.updateCurrentLocation(beacon01);
        edge = graph.getInstructionsTo(beacon01);
        assertEquals(edge, beacon01ToBano);

        // Again from bathroom to beacon
        steps = graph.calculateShortestPath(bano, beacon01);
        assertEquals(steps, 1);
        graph.updateCurrentLocation(bano);
        edge = graph.getInstructionsTo(bano);
        assertEquals(edge, banoToBeacon01);
    }

}