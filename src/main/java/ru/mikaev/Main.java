package ru.mikaev;

import ru.mikaev.graph.algorithms.AStar;
import ru.mikaev.graph.algorithms.Dfs;
import ru.mikaev.graph.algorithms.Ucs;
import ru.mikaev.graph.commons.Vertex;
import ru.mikaev.graph.UndirectedGraph;
import ru.mikaev.graph.algorithms.Bfs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        UndirectedGraph graph = new UndirectedGraph();
        graph.buildFromFiles(Paths.get(ClassLoader.getSystemResource("l1_links.txt").toURI()),
                Paths.get(ClassLoader.getSystemResource("l1_points.txt").toURI()));

        Vertex vertexFrom = graph.getVertex("A").get();
        Vertex vertexTo = graph.getVertex("Z").get();

        Dfs dfs = new Dfs();
        dfs.computeAndPrint(graph, vertexFrom, vertexTo);
        System.out.println("---");

        Bfs bfs = new Bfs();
        bfs.computeAndPrint(graph, vertexFrom, vertexTo);
        System.out.println("---");

        Ucs ucs = new Ucs();
        ucs.computeAndPrint(
                graph,
                vertexFrom,
                vertexTo);
        System.out.println("---");

        AStar aStar = new AStar();
        aStar.computeAndPrint(
                graph,
                vertexFrom,
                vertexTo,
                (from, to) -> (sqrt( pow(vertexFrom.getPoint().getX() - vertexTo.getPoint().getX(), 2) +
                        pow(vertexFrom.getPoint().getY() - vertexTo.getPoint().getY(), 2) ))
                );

    }
}
