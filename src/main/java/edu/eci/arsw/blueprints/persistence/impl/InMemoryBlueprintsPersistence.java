package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
@Primary
public class InMemoryBlueprintsPersistence implements BlueprintPersistence {

    private final ConcurrentMap<String, Blueprint> blueprints = new ConcurrentHashMap<>();

    private static String key(String author, String name) {
        return Objects.requireNonNull(author) + ":" + Objects.requireNonNull(name);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (bp == null) {
            throw new BlueprintPersistenceException("Blueprint cannot be null");
        }
        String k = key(bp.getAuthor(), bp.getName());
        Blueprint prev = blueprints.putIfAbsent(k, bp);
        if (prev != null) {
            throw new BlueprintPersistenceException(
                    "The given blueprint already exists: " + bp.getAuthor() + "/" + bp.getName());
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(key(author, name));
        if (bp == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + "/" + name);
        }
        return bp;
    }

    @Override
    public Set<Blueprint> getBlueprints() {
        return new HashSet<>(blueprints.values());
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> result = new HashSet<>();
        for (Blueprint bp : blueprints.values()) {
            if (bp.getAuthor() != null && bp.getAuthor().equals(author)) {
                result.add(bp);
            }
        }
        if (result.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints for author: " + author);
        }
        return result;
    }
}
