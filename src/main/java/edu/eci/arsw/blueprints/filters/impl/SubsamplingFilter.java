package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps points at even indices (0,2,4,...) to downsample the blueprint.
 */
@Component("subsamplingFilter")
public class SubsamplingFilter implements BlueprintFilter {

    @Override
    public Blueprint apply(Blueprint bp) {
        if (bp == null)
            return null;
        List<Point> pts = bp.getPoints();
        List<Point> filtered = new ArrayList<>();
        for (int i = 0; i < pts.size(); i++) {
            if (i % 2 == 0) {
                filtered.add(pts.get(i));
            }
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), filtered);
    }
}
