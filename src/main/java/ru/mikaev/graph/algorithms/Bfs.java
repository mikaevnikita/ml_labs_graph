package ru.mikaev.graph.algorithms;

import ru.mikaev.graph.commons.EdgeAndCost;
import ru.mikaev.graph.commons.Vertex;
import ru.mikaev.graph.UndirectedGraph;

import java.util.*;

public class Bfs {
    private UndirectedGraph graph;
    private Map<Vertex, Vertex> visited;//to-from
    private Deque<Vertex> queue;
    private Vertex vertexEnd;

    public void computeAndPrint(UndirectedGraph graph, Vertex from, Vertex to){
        bfs(graph, from, to);
        printPath();
    }

    //returns path
    private void bfs(UndirectedGraph graph, Vertex from, Vertex to){
        this.graph = graph;
        this.visited = new HashMap<>();
        this.queue = new ArrayDeque<>();
        this.vertexEnd = to;

        bfs(from);
    }

    private void bfs(Vertex vertexFrom){
        queue.add(vertexFrom);

        visited.put(vertexFrom, vertexFrom);

        while (!queue.isEmpty()){
            if (visited.containsKey(vertexEnd)){
                break;
            }

            Vertex vertex = queue.pollFirst();
            Set<EdgeAndCost> edges = graph.getEdgesFromVertex(vertex);

            for (EdgeAndCost edge : edges){
                Vertex to = edge.getTo();
                if (!visited.containsKey(to)){
                    visited.put(to, vertex);
                    queue.add(to);
                }
            }
        }
    }

    private List<Vertex> computePath(){
        Vertex currentVertex = vertexEnd;
        List<Vertex> path = new ArrayList<>();

        while (true){
            path.add(currentVertex);

            if (currentVertex.equals(visited.get(currentVertex))){
                break;
            }

            currentVertex = visited.get(currentVertex);
        }

        Collections.reverse(path);
        return path;
    }

    private void printPath(){
        System.out.println("Bfs:");
        List<Vertex> vertices = computePath();

        for(Vertex vertex : vertices){
            System.out.println(vertex.getName() + " -> ");
        }
    }
}
