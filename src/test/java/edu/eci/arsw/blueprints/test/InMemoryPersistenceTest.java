package edu.eci.arsw.blueprints.test;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintsPersistence;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InMemoryPersistenceTest {

    private InMemoryBlueprintsPersistence persistence;

    @Before
    public void setup() {
        persistence = new InMemoryBlueprintsPersistence();
    }

    @Test
    public void saveNewAndRetrieveByKey() throws Exception {
        List<Point> pts = Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(2, 2));
        Blueprint bp = new Blueprint("alice", "house", pts);
        persistence.saveBlueprint(bp);

        Blueprint loaded = persistence.getBlueprint("alice", "house");
        assertEquals("alice", loaded.getAuthor());
        assertEquals("house", loaded.getName());
        assertEquals(pts.size(), loaded.getPoints().size());
        assertEquals(pts, loaded.getPoints());
    }

    @Test(expected = BlueprintPersistenceException.class)
    public void savingDuplicateThrows() throws Exception {
        Blueprint bp1 = new Blueprint("bob", "bridge");
        bp1.addPoint(new Point(10, 10));
        persistence.saveBlueprint(bp1);

        // Duplicate key (same author and name)
        Blueprint bp2 = new Blueprint("bob", "bridge");
        bp2.addPoint(new Point(20, 20));
        persistence.saveBlueprint(bp2);
    }

    @Test
    public void getByAuthorReturnsAtLeastOne() throws Exception {
        Blueprint bp1 = new Blueprint("carol", "villa");
        bp1.addPoint(new Point(1, 2));
        Blueprint bp2 = new Blueprint("carol", "flat");
        bp2.addPoint(new Point(3, 4));
        Blueprint bp3 = new Blueprint("dave", "shed");
        bp3.addPoint(new Point(5, 6));

        persistence.saveBlueprint(bp1);
        persistence.saveBlueprint(bp2);
        persistence.saveBlueprint(bp3);

        assertEquals(2, persistence.getBlueprintsByAuthor("carol").size());
    }

    @Test(expected = BlueprintNotFoundException.class)
    public void getByAuthorThrowsWhenEmpty() throws Exception {
        persistence.getBlueprintsByAuthor("nobody");
    }
}
