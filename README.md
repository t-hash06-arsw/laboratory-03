# Laboratorio 03 — Blueprints: Inyección de Dependencias, Filtros conmutables y Persistencia en memoria

Este proyecto implementa una capa lógica para gestionar planos arquitectónicos usando Spring (DI por anotaciones), un esquema de persistencia en memoria y una estrategia de filtrado conmutable. Todo se valida con pruebas unitarias y un programa de demostración por consola.

El diagrama de alto nivel está en `blueprint.puml` (raíz del repo).

## ✅ ¿Qué se hizo (resumen del enunciado)?

- Se configuró la aplicación para funcionar con Inversión de Control/Inyección de Dependencias (IoC/DI) usando Spring.
- Se agregaron dependencias y configuración de Spring por anotaciones (`@Configuration`, `@ComponentScan`).
- Se inyectó la implementación de persistencia (por defecto, `InMemoryBlueprintsPersistence`) dentro del bean `BlueprintServices`.
- Se implementaron las operaciones de servicio `getBlueprint(author,name)` y `getBlueprintsByAuthor(author)` delegando en persistencia.
- Se construyó un programa de demo que crea `BlueprintServices` a través de Spring y permite registrar/consultar planos.
- Se agregaron filtros conmutables (Estrategia) aplicados por el servicio antes de retornar los planos:
	- (A) Redundancias: elimina puntos consecutivos repetidos.
	- (B) Submuestreo: conserva puntos en posiciones pares (0,2,4,…).
- Se agregaron pruebas unitarias para persistencia, servicios y filtros; se demuestra que con solo cambiar anotaciones se alterna el filtro activo.

## 🧩 Cómo se hizo (DI, persistencia y filtros)

### Configuración de Spring (DI por anotaciones)
- `edu.eci.arsw.blueprints.config.AppConfig` usa `@Configuration` + `@ComponentScan("edu.eci.arsw.blueprints")` para detectar automáticamente `@Service`, `@Repository` y `@Component`.
- `BlueprintServices` es un `@Service` al que se le inyecta:
	- `BlueprintPersistence` (por defecto la implementación `InMemoryBlueprintsPersistence`, `@Repository`).
	- Un `BlueprintFilter` (estrategia de filtrado) seleccionado por anotaciones.

### Persistencia en memoria
- `BlueprintPersistence` define el contrato de repositorio y excepciones checked.
- `InMemoryBlueprintsPersistence` (`@Repository`) almacena planos en un mapa en memoria y provee operaciones de guardado/consulta.
- `AnotherBlueprintsPersistence` es un stub alternativo anotado como `@Repository("anotherPersistence")` para demostrar conmutabilidad vía `@Qualifier`.

### Filtros conmutables (Patrón Estrategia)
- `BlueprintFilter` define `apply(Blueprint)` y devuelve un plano filtrado (sin mutar el original).
- Implementaciones:
	- `RedundancyFilter` (`@Component("redundancyFilter")`, anotado con `@Primary` por defecto): elimina duplicados consecutivos.
	- `SubsamplingFilter` (`@Component("subsamplingFilter")`): conserva índices pares 0,2,4,…
- Conmutación: basta alternar la anotación `@Primary` entre ambos, o inyectar con `@Qualifier("redundancyFilter"|"subsamplingFilter")` en `BlueprintServices`.

### Servicios
- `BlueprintServices` consulta la persistencia e inmediatamente aplica el filtro inyectado antes de retornar los datos.
- Métodos implementados: `getBlueprint(author,name)` y `getBlueprintsByAuthor(author)` (además de registro/operaciones auxiliares usadas en pruebas y demo).

## 🧪 Construcción y pruebas

Ejecutar la suite completa de pruebas (JUnit 4):

```bash
mvn -q -DskipTests=false test
```

Pruebas incluidas (ver `src/test/java/edu/eci/arsw/blueprints/test/`):
- `InMemoryPersistenceTest`: guarda y consulta; maneja duplicados.
- `BlueprintServicesTest`: delegación a persistencia y aplicación de filtro.
- `BlueprintFiltersTest` y `BlueprintSubsamplingFilterTest`: validan cada estrategia de filtrado y el conmutador por anotaciones.

Resultados esperados: todas en verde (ver reportes Surefire en `target/surefire-reports/`).

## ▶️ Demo por consola

El punto de entrada es `edu.eci.arsw.blueprints.ui.BlueprintsMain`. Crea el contexto Spring con `AppConfig` e interactúa con `BlueprintServices` (registrar/consultar y observar filtrado activo).

- Ejecutable desde tu IDE directamente.
- Si deseas ejecutarlo desde terminal vía Maven, puedes agregar el plugin `exec-maven-plugin` o ejecutar desde el IDE. El proyecto ya compila y prueba desde Maven.

## 🔁 Conmutar filtros por anotaciones

Dos beans de filtro existen:
- `redundancyFilter` (por defecto `@Primary` en `RedundancyFilter.java`).
- `subsamplingFilter` (`SubsamplingFilter.java`).

Para alternar al submuestreo sin cambiar código (solo anotaciones):
1) Edita `src/main/java/edu/eci/arsw/blueprints/filters/impl/RedundancyFilter.java` y elimina `@Primary`.
2) Edita `src/main/java/edu/eci/arsw/blueprints/filters/impl/SubsamplingFilter.java` y añade `@Primary` sobre la clase.

Efecto esperado:
- Con redundancias activas: se eliminan duplicados consecutivos en los planos retornados.
- Con submuestreo: se retornan ~la mitad de los puntos (índices 0,2,4,…).

Alternativa: usar `@Qualifier("redundancyFilter")` o `@Qualifier("subsamplingFilter")` en la inyección del servicio.

## 🗂️ Estructura de carpetas principal

```
src/
	main/
		java/edu/eci/arsw/blueprints/
			config/           # AppConfig (ComponentScan)
			filters/          # Interfaz de filtro
				impl/           # RedundancyFilter / SubsamplingFilter
			model/            # Point / Blueprint
			persistence/      # Contratos y excepciones
				impl/           # InMemoryBlueprintsPersistence / AnotherBlueprintsPersistence
			services/         # BlueprintServices (aplica el filtro)
			ui/               # BlueprintsMain (demo)
		resources/
			application.properties
	test/
		java/edu/eci/arsw/blueprints/test/
			*.java            # Pruebas unitarias (persistencia, servicios, filtros)
```

Otros archivos relevantes:
- `blueprint.puml`: diagrama de relaciones de servicios y persistencia.
- `pom.xml`: dependencias de Spring (Context/Test) y JUnit 4.

## 📋 Lista de chequeo para calificación (mapping al enunciado)

- [x] Dependencias de Spring agregadas en `pom.xml` (Context y Test) y JUnit 4.
- [x] Configuración de Spring por anotaciones (`AppConfig` con `@ComponentScan`).
- [x] Inyección de `BlueprintPersistence` en el bean `BlueprintServices` al crearse.
- [x] Operaciones `getBlueprint()` y `getBlueprintsByAuthor()` implementadas (delegan en persistencia) con pruebas (`InMemoryPersistenceTest`).
- [x] Programa de demo por consola que instancia `BlueprintServices` mediante Spring (`BlueprintsMain`).
- [x] Abstracción `BlueprintFilter` e implementaciones conmutables:
	- [x] (A) Redundancias: elimina puntos consecutivos repetidos.
	- [x] (B) Submuestreo: elimina 1 de cada 2 puntos (conserva índices pares).
- [x] Pruebas para ambos filtros y verificación de alternancia por anotaciones (`@Primary`/`@Qualifier`).

## 🛠️ Cómo correr rápidamente

Compilar y ejecutar pruebas:

```bash
mvn -q -DskipTests=false test
```

Ejecutar la demo (desde IDE) usando la clase `edu.eci.arsw.blueprints.ui.BlueprintsMain`.

## 📝 Notas y troubleshooting

- Si usas el stub `AnotherBlueprintsPersistence`, recuerda que es solo demostrativo y lanza `UnsupportedOperationException` en operaciones no implementadas; inyecta con `@Qualifier("anotherPersistence")` si deseas probarlo.
- Los filtros no mutan el `Blueprint` original; retornan una nueva instancia filtrada para evitar efectos colaterales.
- Para validar la alternancia del filtro, ejecuta nuevamente las pruebas después de mover `@Primary` entre `RedundancyFilter` y `SubsamplingFilter`.

---

Autoría: Tomas Felipe Panqueva Manrrique — Laboratorio 03 Blueprints
