package br.unesp.repositorio.tools.metadatapatterner.tools;

import java.text.Normalizer;

public abstract class TextUtils {
	
	public static String removePuncts(String text){
		return text.replaceAll("[\\p{Punct}]", "");
	}
	
	public static String removeAccents(String text){
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	
	public static String removeExtraSpaces(String text){
		return text.trim().replaceAll("\\s{2,}", " ");
	}
	
}
