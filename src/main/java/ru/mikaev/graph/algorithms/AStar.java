package ru.mikaev.graph.algorithms;

import ru.mikaev.graph.UndirectedGraph;
import ru.mikaev.graph.commons.DoubleLengthComparator;
import ru.mikaev.graph.commons.EdgeAndCost;
import ru.mikaev.graph.commons.Vertex;
import ru.mikaev.graph.commons.VertexAndCost;

import java.util.*;
import java.util.function.BiFunction;

public class AStar {
    private UndirectedGraph graph;
    private Set<Vertex> visited;
    private PriorityQueue<VertexAndCost> queue;
    private Vertex vertexEnd;
    private BiFunction<Vertex, Vertex, Double> euristic;
    private Map<Vertex, Vertex> cameFrom;


    public void computeAndPrint(UndirectedGraph graph, Vertex from, Vertex to, BiFunction<Vertex, Vertex, Double> euristic){
        aStar(graph, from, to, euristic);
        printPath();
    }

    //returns path
    private void aStar(UndirectedGraph graph, Vertex from, Vertex to, BiFunction<Vertex, Vertex, Double> euristic){
        this.graph = graph;
        this.queue = new PriorityQueue<>(new DoubleLengthComparator());
        this.vertexEnd = to;
        this.visited = new HashSet<>();
        this.euristic = euristic;
        this.cameFrom = new HashMap<>();

        aStar(from);
    }

    private void aStar(Vertex vertexBegin){
        queue.add(new VertexAndCost(vertexBegin, 0.0));
        Map<Vertex, Double> costSoFar = new HashMap<>();

        double priority;
        cameFrom.put(vertexBegin, vertexBegin);
        costSoFar.put(vertexBegin, 0.0);
        int counter = 0;

        while (!queue.isEmpty()){
            VertexAndCost current = queue.poll();
            Vertex currentVertex = current.getVertex();
            Set<EdgeAndCost> edges = graph.getEdgesFromVertex(currentVertex);

            double newCost;
            counter++;

            if (currentVertex.equals(vertexEnd)){
                break;
            }

            for (EdgeAndCost edge : edges){
                Vertex child = edge.getTo();

                if (costSoFar.containsKey(currentVertex)) {
                    newCost = costSoFar.get(currentVertex) + edge.getDistance();
                } else {
                    newCost = edge.getDistance();
                }
                if (!costSoFar.containsKey(child) || newCost < costSoFar.get(child)){
                    costSoFar.put(child, newCost);
                    priority = newCost + euristic.apply(vertexEnd, child);
                    queue.add(new VertexAndCost(child, priority));
                    cameFrom.put(child, currentVertex);
                }
            }
        }
    }

    private List<Vertex> computePath(){
        Vertex currentVertex = vertexEnd;
        List<Vertex> path = new ArrayList<>();

        while (true){
            path.add(currentVertex);

            if (currentVertex.equals(cameFrom.get(currentVertex))){
                break;
            }

            currentVertex = cameFrom.get(currentVertex);
        }

        Collections.reverse(path);
        return path;
    }

    private void printPath(){
        System.out.println("AStar:");
        List<Vertex> vertices = computePath();

        for(Vertex vertex : vertices){
            System.out.println(vertex.getName() + " -> ");
        }
    }

}
