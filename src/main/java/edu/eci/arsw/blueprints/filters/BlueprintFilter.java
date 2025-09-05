package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 * Strategy interface for applying a filtering policy to a blueprint.
 * Implementations MUST NOT mutate the input instance. Return a new instance
 * preserving author and name, with the filtered list of points.
 */
public interface BlueprintFilter {

    /**
     * Apply the filter to the given blueprint without mutating it.
     *
     * @param bp the input blueprint (non-null)
     * @return a new Blueprint instance with filtered points
     */
    Blueprint apply(Blueprint bp);
}
