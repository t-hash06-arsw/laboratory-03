package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BlueprintServices {

    @Autowired
    private BlueprintPersistence persistence;

    @Autowired
    private BlueprintFilter filter;

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        persistence.saveBlueprint(bp);
    }

    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> original = persistence.getBlueprints();
        Set<Blueprint> out = new HashSet<>();
        for (Blueprint bp : original) {
            out.add(filter.apply(bp));
        }
        return out;
    }

    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> originals = persistence.getBlueprintsByAuthor(author);
        Set<Blueprint> out = new HashSet<>();
        for (Blueprint bp : originals) {
            out.add(filter.apply(bp));
        }
        return out;
    }

    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint bp = persistence.getBlueprint(author, name);
        return filter.apply(bp);
    }
}
