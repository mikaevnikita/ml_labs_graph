package ru.mikaev.graph.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VertexAndCost {
    private Vertex vertex;
    private double distance;
}
