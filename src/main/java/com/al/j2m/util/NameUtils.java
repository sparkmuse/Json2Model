/**
 * 
 */
package com.al.j2m.util;

import org.apache.commons.lang3.StringUtils;
import com.al.json2model.general.Pluralizer;

/**
 * Utility to handle the names.
 * 
 * @author Alfredo Lopez
 *
 */
public class NameUtils {

	/**
	 * Default hidden constructor so this class is never instantiated.
	 */
	NameUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Method to get the common elements in a word. It starts from the end and
	 * if nothing matches then it moves from the beginning.
	 * 
	 * There is a minimunMatchLength of 4 to return a match.
	 * 
	 * @param word1
	 * @param word2
	 * @return
	 */
	public static String getCommonBetween(String word1, String word2) {

		final int minimunMatchLength = 4;
		String longWord = word1.length() >= word2.length() ? word1 : word2;
		String shortWord = word1.length() < word2.length() ? word1 : word2;

		int maxLength = longWord.length() - 1;
		char[] longChar = longWord.toCharArray();
		char[] shortChar = shortWord.toCharArray();
		String prospect = StringUtils.EMPTY;

		for (int i = longChar.length - 1, j = shortChar.length - 1; (i <= maxLength && i > 0); i--, j--) {
			if (longChar[i] != shortChar[j]) {
				prospect = longWord.substring(i + 1);
				if (prospect.length() >= minimunMatchLength) {
					return prospect;
				} else {
					break;
				}
			}
		}

		for (int i = 0, j = 0; i < maxLength; i++, j++) {
			if (longChar[i] != shortChar[j]) {
				prospect = longWord.substring(0, i);
				if (prospect.length() >= minimunMatchLength) {
					return prospect;
				} else {
					break;
				}
			}
		}

		return StringUtils.EMPTY;
	}

	/**
	 * Returns the name out of a name string based on the casing. Most
	 * programmers us a capital letter to start a meaningful name. We skip the
	 * first letter.
	 * 
	 * If the variable does not contain a capital letter then we will return the
	 * same name.
	 * 
	 * Best used with getCommonBetween().
	 * 
	 * Ex:
	 * 
	 * <pre>
	 * String s = "firstName";
	 * String s1 = "lastName";
	 *
	 * NameUtils.getCommonBetween(s, s1); // returns "stName"
	 * NameUtils.inferName(result); // returns = "Name"
	 * </pre>
	 * 
	 * @param name The name to check
	 * @return The inferred name if possible, otherwise name
	 */
	public static String inferName(String name) {

		char[] chars = name.toCharArray();
		int i = 1;
		while (i < chars.length && !Character.isUpperCase(chars[i])) {
			i++;
		}

		if (i == name.length()) {
			return name;
		}

		return name.substring(i);
	}

	/**
	 * Procedure to get the plural of a word
	 * 
	 * @param noun The word to pluralize.
	 * @return A pluralized version of the word.
	 * @see Pluralizer
	 */
	public static String getPlural(String noun) {
		return Pluralizer.getPlural(noun);
	}

	/**
	 * Procedure to get the singular of a word.
	 *
	 * @param noun The word to convert to singular.
	 * @return A singular version of the word.
	 * @see Pluralizer
	 */
	public static String getSingular(String noun) {
		return Pluralizer.getSingular(noun);
	}

	/**
	 * Procedure to get the capitalized of a word.
	 *
	 * @param word The word to capitalize.
	 * @return The capitalized word.
	 *
	 * @see StringUtils
	 */
	public static String getCapitalized(String word) {
		return StringUtils.capitalize(word);
	}

}
