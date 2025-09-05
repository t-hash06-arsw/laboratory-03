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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BlueprintServicesTest {

    @Autowired
    private BlueprintServices services;

    @Test
    public void addAndQueryBlueprints() throws Exception {
        Blueprint a1 = new Blueprint("eva", "one", Arrays.asList(new Point(0, 0), new Point(1, 1)));
        Blueprint a2 = new Blueprint("eva", "two", Arrays.asList(new Point(2, 2)));
        services.addNewBlueprint(a1);
        services.addNewBlueprint(a2);

        assertEquals(2, services.getBlueprintsByAuthor("eva").size());
        assertEquals("one", services.getBlueprint("eva", "one").getName());
        assertEquals(2, services.getBlueprint("eva", "one").getPoints().size());
    }
}
