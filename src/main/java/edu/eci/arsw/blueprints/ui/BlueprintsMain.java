package edu.eci.arsw.blueprints.ui;

import edu.eci.arsw.blueprints.config.AppConfig;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintServices;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class BlueprintsMain {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        try {
            BlueprintServices svs = ctx.getBean(BlueprintServices.class);

            Blueprint bp = new Blueprint("demo", "sample", Arrays.asList(
                    new Point(0, 0), new Point(1, 1), new Point(2, 2)));
            svs.addNewBlueprint(bp);

            System.out.println("All blueprints: " + svs.getAllBlueprints().size());
            System.out.println("By author 'demo': " + svs.getBlueprintsByAuthor("demo").size());
            System.out.println("Loaded points: " + svs.getBlueprint("demo", "sample").getPoints());
        } finally {
            ctx.close();
        }
    }
}
