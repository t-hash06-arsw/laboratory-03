# Blueprints demo: DI, filters toggling, and persistence

This module demonstrates Spring DI with a service, an in-memory persistence, and a pluggable filter strategy. You can toggle filters by changing only annotations.

## Build and test

```bash
mvn -q -DskipTests=false test
```

## Run the console demo

The demo entry point is `edu.eci.arsw.blueprints.ui.BlueprintsMain` which bootstraps a Spring context with `AppConfig` and uses `BlueprintServices`.

Run from Maven (exec plugin not required if you use your IDE). From terminal you can run the class via your IDE or add the exec plugin if desired.

## Toggle filters via annotations

Two filters exist as Spring components:
- `RedundancyFilter` (bean name `redundancyFilter`) — removes consecutive duplicates.
- `SubsamplingFilter` (bean name `subsamplingFilter`) — keeps points at even indices.

By default, `RedundancyFilter` is active because it is annotated with `@Primary` in `RedundancyFilter.java`.

To switch to subsampling without touching any code besides annotations, make `SubsamplingFilter` the primary bean:
1. Edit `src/main/java/edu/eci/arsw/blueprints/filters/impl/RedundancyFilter.java` and remove `@Primary`.
2. Edit `src/main/java/edu/eci/arsw/blueprints/filters/impl/SubsamplingFilter.java` and add `@Primary` above the class.

Alternatively, you can change the injection in `BlueprintServices` to use a `@Qualifier` of either `"redundancyFilter"` or `"subsamplingFilter"`.

## Switch persistence (optional)

An alternate stub persistence `AnotherBlueprintsPersistence` is provided and registered as `@Repository("anotherPersistence")`. To use it, qualify the dependency in `BlueprintServices` with `@Qualifier("anotherPersistence")` (for demo only; methods are unsupported).

## Expected results

- With redundancy filter active, blueprints returned by services have consecutive duplicates removed.
- With subsampling active, returned blueprints include roughly half the points (indices 0,2,4,...).
```