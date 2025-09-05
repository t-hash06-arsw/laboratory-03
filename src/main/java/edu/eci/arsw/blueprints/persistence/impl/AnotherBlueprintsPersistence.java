package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;

/**
 * Alternative persistence stub to demonstrate DI switching.
 * Not wired by default; use @Qualifier("anotherPersistence") if needed.
 */
@Repository("anotherPersistence")
public class AnotherBlueprintsPersistence implements BlueprintPersistence {

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        throw new UnsupportedOperationException("Not implemented in stub");
    }

    @Override
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        throw new UnsupportedOperationException("Not implemented in stub");
    }

    @Override
    public Set<Blueprint> getBlueprints() {
        return Collections.emptySet();
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return Collections.emptySet();
    }
}
