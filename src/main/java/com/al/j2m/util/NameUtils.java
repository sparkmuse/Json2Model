/**
 * 
 */
package com.al.j2m.util;

import org.apache.commons.lang3.StringUtils;

import com.al.json2model.general.Pluralizer;

/**
 * @author alfredo
 *
 */
public class NameUtils {

	public static String getCommonBetween(String w1, String w2) {

		char[] chars = w1.toCharArray();

		String test = "";
		int i = 0;

		while (test.length() < 4 && i < chars.length) {
			test = w1.substring(i, i + 1);

			// Check if we have it, then keep on trying.
			if (w2.contains(test)) {

				int j = i + 1;
				while (w2.contains(test) && j < chars.length) {
					test = w1.substring(i, j + 1);
					j++;
				}
			}
			i++;
		}

		return test;
	}

	/**
	 * Returns the name out of a name string based on the casing. Most
	 * programmers us a capital letter to start a meaningful name.
	 * 
	 * Best used with getCommonBetween().
	 * 
	 * Ex:
	 * 
	 * <pre>
	 * 	String s = "firstName";
	 * 	String s1 = "lastName";
	 *
	 * 	NameUtils.getCommonBetween(s, s1); // returns "stName"
	 * 	NameUtils.inferName(result); // returns = "Name"
	 * </pre>
	 * 
	 * @param name The name to check
	 * @return The inferred name if possible, otherwise name
	 */
	public static String inferName(String name) {
		
		// Try to find a capital letter.
		char[] chars = name.toCharArray();

		int i = 0;
		while (i < chars.length && !Character.isUpperCase(chars[i])) {
			i++;
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
	 *@param word The word to capitalize.
	 *@return The capitalized word.
	 *
	 * @see StringUtils
	 */
	public static String getCapitalized (String word) {
		return StringUtils.capitalize(word);
	}

}
