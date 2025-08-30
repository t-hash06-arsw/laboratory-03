package edu.eci.arsw.blueprints.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Domain model representing an architectural blueprint.
 */
public class Blueprint {
    private final String author;
    private final String name;
    private List<Point> points = new ArrayList<>();

    public Blueprint(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public Blueprint(String author, String name, List<Point> points) {
        this.author = author;
        this.name = name;
        if (points != null) {
            this.points = new ArrayList<>(points);
        }
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public void addPoint(Point p) {
        if (p != null) {
            points.add(p);
        }
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public void setPoints(List<Point> points) {
        this.points = new ArrayList<>(points);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Blueprint that = (Blueprint) o;
        return Objects.equals(author, that.author) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, name);
    }
}
