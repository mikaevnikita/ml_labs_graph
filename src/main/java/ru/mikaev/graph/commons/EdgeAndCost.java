package ru.mikaev.graph.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdgeAndCost {
    private Vertex from;
    private Vertex to;
    private double distance;
}
