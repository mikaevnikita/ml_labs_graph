package ru.mikaev.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mikaev.graph.commons.EdgeAndCost;
import ru.mikaev.graph.commons.Point2d;
import ru.mikaev.graph.commons.Vertex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UndirectedGraph {
    Set<Vertex> vertices;
    Set<EdgeAndCost> edges;

    public void buildFromFiles(Path links, Path points) throws IOException {
        vertices = new HashSet<>();
        edges = new HashSet<>();

        Files.
                lines(points)
                .map(line -> line.split(","))
                .map(data -> new Vertex(data[0], new Point2d(Integer.parseInt(data[1]), Integer.parseInt(data[2]))))
                .forEach(this::addVertex);

        Files.
                lines(links)
                .map(line -> line.split(","))
                .map(data -> new EdgeAndCost(getVertex(data[0]).get(), getVertex(data[1]).get(),
                        calculateDistance( getVertex(data[0]).get(), getVertex(data[1]).get() )))
                .forEach(this::addEdge);
    }

    public boolean addVertex(Vertex vertex){
        return vertices.add(vertex);
    }

    public boolean addEdge(EdgeAndCost edgeAndCost){
        return edges.add(edgeAndCost);
    }

    public Optional<Vertex> getVertex(String vertexName){
        return vertices.stream().filter(vertex -> vertex.getName().equals(vertexName)).findAny();
    }

    public boolean hasVertex(String vertexName) {
        return vertices.stream().anyMatch(vertex -> vertex.getName().equals(vertexName));
    }

    public boolean hasVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }


    private double calculateDistance(Vertex vertexFrom, Vertex vertexTo){
        return sqrt( pow(vertexFrom.getPoint().getX() - vertexTo.getPoint().getX(), 2) +
                pow(vertexFrom.getPoint().getY() - vertexTo.getPoint().getY(), 2) );
    }

    public boolean hasEdge(String vertexFrom, String vertexTo) {
        for(EdgeAndCost edge : edges){
            if(edge.getFrom().equals(getVertex(vertexFrom)) && edge.getTo().equals(getVertex(vertexTo)))
                return true;
        }
        return false;
    }

    public boolean hasEdge(Vertex vertexFrom, Vertex vertexTo) {
        for(EdgeAndCost edge : edges){
            if(edge.getFrom().equals(vertexFrom) && edge.getTo().equals(vertexTo))
                return true;
        }
        return false;
    }

    public Set<EdgeAndCost> getEdgesFromVertex(Vertex from){
        return edges.stream().filter(edge -> edge.getFrom().equals(from)).collect(Collectors.toSet());
    }

    public Set<EdgeAndCost> getEdgesToVertex(Vertex from){
        return edges.stream().filter(edge -> edge.getTo().equals(from)).collect(Collectors.toSet());
    }

    public double getDistance(Vertex from, Vertex to) throws NoSuchElementException{
        for(EdgeAndCost edge : edges){
            if(edge.getFrom().equals(from) && edge.getTo().equals(to))
                return edge.getDistance();
        }
        throw new NoSuchElementException("there is no such edge");
    }

/*
    public void printAllVertices(){
        System.out.println("Vertices: ");
        for(Vertex vertex : vertices){
            System.out.println("Name: " + vertex.getName() + " Coordinate: " + vertex.getPoint());
        }
    }

    public void printAllLinks(){
        System.out.println("Edges: ");
        for(EdgeAndCost edgeAndCost : edges){
            System.out.println("Edge From: " + edgeAndCost.getFrom() + " To: " + edgeAndCost.getTo() + " Distance: " + edgeAndCost.getDistance());
        }
    }*/


}
