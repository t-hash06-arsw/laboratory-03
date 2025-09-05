package edu.eci.arsw.blueprints.test;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintServices;
import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.filters.impl.SubsamplingFilter;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintsPersistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BlueprintSubsamplingFilterTest.TestConfig.class)
public class BlueprintSubsamplingFilterTest {

    @Autowired
    private BlueprintServices services;

    @Test
    public void subsamplingFilterKeepsEvenIndices() throws Exception {
        Blueprint bp = new Blueprint("yuki", "down", Arrays.asList(
                new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4), new Point(5, 5)));
        services.addNewBlueprint(bp);

        Blueprint filtered = services.getBlueprint("yuki", "down");
        assertEquals(3, filtered.getPoints().size());
        assertEquals(new Point(0, 0), filtered.getPoints().get(0));
        assertEquals(new Point(2, 2), filtered.getPoints().get(1));
        assertEquals(new Point(4, 4), filtered.getPoints().get(2));
    }

    @Configuration
    static class TestConfig {
        @Bean
        public BlueprintPersistence blueprintPersistence() {
            return new InMemoryBlueprintsPersistence();
        }

        @Bean
        @org.springframework.context.annotation.Primary
        public BlueprintFilter blueprintFilter() {
            return new SubsamplingFilter();
        }

        @Bean
        public BlueprintServices blueprintServices() {
            return new BlueprintServices();
        }
    }
}
