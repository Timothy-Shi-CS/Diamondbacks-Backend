package com.example.Diamondbacks;

import java.util.ArrayList;
import java.util.Collection;

public class Geometry {
    private float area;
    private float perimeter;
    private Collection<ArrayList<Float>> boundary;

    public float calArea(){
        return 0;
    }
    public float calPerimeter(){
        return 0;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "area=" + area +
                ", perimeter=" + perimeter +
                ", boundary=" + boundary +
                '}';
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(float perimeter) {
        this.perimeter = perimeter;
    }

    public Collection<ArrayList<Float>> getBoundary() {
        return boundary;
    }

    public void setBoundary(Collection<ArrayList<Float>> boundary) {
        this.boundary = boundary;
    }
}
