package edu.eci.arsw.blueprints.test;

import edu.eci.arsw.blueprints.config.AppConfig;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BlueprintFiltersTest {

    @Autowired
    private BlueprintServices services;

    @Test
    public void redundancyFilterRemovesConsecutiveDuplicates() throws Exception {
        List<Point> pts = Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(1, 1), new Point(1, 1),
                new Point(2, 2));
        Blueprint bp = new Blueprint("zoe", "dup", pts);
        services.addNewBlueprint(bp);

        Blueprint filtered = services.getBlueprint("zoe", "dup");
        // Expected: [ (0,0), (1,1), (2,2) ] => size 3
        assertEquals(3, filtered.getPoints().size());
        assertEquals(new Point(0, 0), filtered.getPoints().get(0));
        assertEquals(new Point(1, 1), filtered.getPoints().get(1));
        assertEquals(new Point(2, 2), filtered.getPoints().get(2));
    }
}
