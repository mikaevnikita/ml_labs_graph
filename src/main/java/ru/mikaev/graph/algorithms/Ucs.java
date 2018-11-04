package ru.mikaev.graph.algorithms;

import ru.mikaev.graph.commons.DoubleLengthComparator;
import ru.mikaev.graph.commons.EdgeAndCost;
import ru.mikaev.graph.commons.Vertex;
import ru.mikaev.graph.UndirectedGraph;
import ru.mikaev.graph.commons.VertexAndCost;

import java.util.*;

public class Ucs {
    private UndirectedGraph graph;
    private Map<Vertex, Vertex> cameFrom;
    private Set<Vertex> visited;
    private PriorityQueue<VertexAndCost> queue;
    private Vertex vertexEnd;

    public void computeAndPrint(UndirectedGraph graph, Vertex from, Vertex to){
        ucs(graph, from, to);
        printPath();
    }

    //returns path
    private void ucs(UndirectedGraph graph, Vertex from, Vertex to){
        this.graph = graph;
        this.cameFrom = new HashMap<>();
        this.queue = new PriorityQueue<>(new DoubleLengthComparator());
        this.vertexEnd = to;
        this.visited = new HashSet<>();

        ucs(from);
    }

    private void ucs(Vertex vertexFrom){
        queue.add(new VertexAndCost(vertexFrom, 0.0));

        double newCost;
        Map<Vertex, Double> costSoFar = new HashMap<>();

        cameFrom.put(vertexFrom, vertexFrom);

        while(!queue.isEmpty()){
            if (visited.contains(vertexEnd)){
                break;
            }

            VertexAndCost vertexAndCost = queue.poll();
            Vertex currentVertex = vertexAndCost.getVertex();
            Set<EdgeAndCost> edgesFromVertex = graph.getEdgesFromVertex(currentVertex);

            if(!visited.contains(currentVertex)){
                for(EdgeAndCost edge : edgesFromVertex){
                    Vertex child = edge.getTo();

                    if(costSoFar.containsKey(currentVertex)){
                        newCost = costSoFar.get(currentVertex) + edge.getDistance();
                    }
                    else{
                        newCost = edge.getDistance();
                    }

                    if (!costSoFar.containsKey(child) || newCost < costSoFar.get(child)){
                        costSoFar.put(child, newCost);
                        cameFrom.put(child, currentVertex);
                    }

                    queue.add(new VertexAndCost(child, newCost));
                }
                visited.add(currentVertex);
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

    private double computeLength(List<Vertex> vertices){
        double length = 0;
        for (int i = 0; i < vertices.size() - 1; i++) {
            Vertex currentVertex = vertices.get(i);
            Vertex nextVertex = vertices.get(i+1);

            length += graph.getDistance(currentVertex, nextVertex);
        }

        return length;
    }

    private void printPath(){
        System.out.println("Ucs:");
        List<Vertex> vertices = computePath();

        double pathLength = computeLength(vertices);

        for(Vertex vertex : vertices){
            System.out.println(vertex.getName() + " -> ");
        }

        System.out.println("Length: " + pathLength);
    }
}

