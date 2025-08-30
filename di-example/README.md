# Escuela Colombiana de Ingeniería
# Arquitecturas de Software - ARSW
### Taller – Principio de Inversión de dependencias, Contenedores Livianos e Inyección de dependencias.

Parte I. Ejercicio básico.

Para ilustrar el uso del framework Spring, y el ambiente de desarrollo para el uso del mismo a través de Maven (y NetBeans), se hará la configuración de una aplicación de análisis de textos, que hace uso de un verificador gramatical que requiere de un corrector ortográfico. A dicho verificador gramatical se le inyectará, en tiempo de ejecución, el corrector ortográfico que se requiera (por ahora, hay dos disponibles: inglés y español).

1. Abra el los fuentes del proyecto en NetBeans.

2. Revise el archivo de configuración de Spring ya incluido en el proyecto (src/main/resources). El mismo indica que Spring buscará automáticamente los 'Beans' disponibles en el paquete indicado.

3. Haciendo uso de la [configuración de Spring basada en anotaciones](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-spring-beans-and-dependency-injection.html) marque con las anotaciones @Autowired y @Service las dependencias que deben inyectarse, y los 'beans' candidatos a ser inyectadas -respectivamente-:

	* GrammarChecker será un bean, que tiene como dependencia algo de tipo 'SpellChecker'.
	* EnglishSpellChecker y SpanishSpellChecker son los dos posibles candidatos a ser inyectados. Se debe seleccionar uno, u otro, mas NO ambos (habría conflicto de resolución de dependencias). Por ahora haga que se use EnglishSpellChecker.
 
5.	Haga un programa de prueba, donde se cree una instancia de GrammarChecker mediante Spring, y se haga uso de la misma:

	```java
	public static void main(String[] args) {
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		GrammarChecker gc=ac.getBean(GrammarChecker.class);
		System.out.println(gc.check("la la la "));
	}
	```
	
6.	Modifique la configuración con anotaciones para que el Bean ‘GrammarChecker‘ ahora haga uso del  la clase SpanishSpellChecker (para que a GrammarChecker se le inyecte EnglishSpellChecker en lugar de  SpanishSpellChecker). Verifique el nuevo resultado.

## Qué se hizo

- Se habilitó el escaneo de componentes en `applicationContext.xml` para el paquete `edu.eci.arsw`.
- Se marcó `GrammarChecker` como `@Service` y su dependencia `SpellChecker` se inyecta con `@Autowired` (setter injection).
- Se implementaron dos `SpellChecker`:
	- `EnglishSpellChecker` anotado con `@Service` como candidato por defecto.
	- `SpanishSpellChecker` disponible (sin anotación) para evitar conflicto de beans simultáneos.
- La clase `Main` crea el contexto con `ClassPathXmlApplicationContext` y obtiene el bean `GrammarChecker` para procesar un texto de ejemplo.

Para cambiar a español, se puede: anotar `SpanishSpellChecker` con `@Service` y quitar la anotación de `EnglishSpellChecker`, o usar `@Primary`/`@Qualifier` en la inyección.

## Cómo ejecutar con Maven Exec

Desde la carpeta del proyecto (o ubicándose en `di-example/`), ejecutar:

```bash
# Desde la raíz del workspace
mvn -f di-example/pom.xml exec:java

# O dentro de di-example/
mvn exec:java
```

Salida esperada (con el verificador en inglés activo):

```
Spell checking output:Checked with english checker:la la la Plagiarism checking output: Not available yet
```