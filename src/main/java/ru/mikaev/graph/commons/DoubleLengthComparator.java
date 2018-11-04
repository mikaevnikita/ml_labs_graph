package ru.mikaev.graph.commons;

import java.util.Comparator;

public class DoubleLengthComparator implements Comparator<VertexAndCost> {
    public int compare(VertexAndCost x, VertexAndCost y){
        if ( x.getDistance() > y.getDistance() ){
            return 1;
        } else if ( x.getDistance() < y.getDistance() ){
            return -1;
        } else {
            return 0;
        }
    }
}
