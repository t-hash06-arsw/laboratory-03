package edu.eci.arsw.springdemo;

public class SpanishSpellChecker implements SpellChecker {

	@Override
	public String checkSpell(String text) {
		return "revisando (" + text + ") con el verificador de sintaxis del espanol";
	}
}
