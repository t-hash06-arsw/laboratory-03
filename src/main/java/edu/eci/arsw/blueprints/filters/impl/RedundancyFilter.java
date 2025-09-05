package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Removes consecutive duplicated points from a blueprint.
 */
@Component("redundancyFilter")
@Primary
public class RedundancyFilter implements BlueprintFilter {

    @Override
    public Blueprint apply(Blueprint bp) {
        if (bp == null)
            return null;
        List<Point> pts = bp.getPoints();
        List<Point> filtered = new ArrayList<>();
        Point prev = null;
        for (Point p : pts) {
            if (prev == null || !p.equals(prev)) {
                filtered.add(p);
            }
            prev = p;
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), filtered);
    }
}
