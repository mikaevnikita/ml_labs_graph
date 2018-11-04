package ru.mikaev.graph.algorithms;

import ru.mikaev.graph.commons.EdgeAndCost;
import ru.mikaev.graph.commons.Vertex;
import ru.mikaev.graph.UndirectedGraph;

import java.util.*;

public class Dfs {
    private UndirectedGraph graph;
    private Set<Vertex> path;
    private Set<Vertex> visited;

    public void computeAndPrint(UndirectedGraph graph, Vertex from, Vertex to){
        dfs(graph, from, to);
        printPath();
    }

    //returns path
    private Set<Vertex> dfs(UndirectedGraph graph, Vertex from, Vertex to){
        this.graph = graph;
        this.visited = new HashSet<>();
        this.path = new LinkedHashSet<>();

        dfs(from, to);
        return path;
    }

    private void dfs(Vertex currentVertex, Vertex end){
        if (currentVertex.equals(end)){
            path.add(end);
            return;
        } else if (visited.contains(currentVertex)){
            return;
        }

        path.add(currentVertex);
        Set<EdgeAndCost> edges = graph.getEdgesFromVertex(currentVertex);

        for(EdgeAndCost edge : edges){
            dfs(edge.getTo(), end);

            if(path.contains(end)){
                return;
            }
        }

        visited.add(currentVertex);
        path.remove(currentVertex);//очищаем если зашли не туда
    }

    private void printPath(){
        System.out.println("Dfs:");

        for(Vertex vertex : path){
            System.out.println(vertex.getName() + " -> ");
        }
    }
}
