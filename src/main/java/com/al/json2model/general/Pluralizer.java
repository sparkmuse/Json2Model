package com.al.json2model.general;

import org.apache.commons.lang3.StringUtils;

/**
 * Class to convert from singular to plural forms in English.
 * Due to the different variations and complexity of some of the word
 * rules the algorithms used here might fail in some instances.
 * 
 * @author alfredo
 *
 */

public class Pluralizer {
	
	/**
	 * The word was not found in the IRREGULAR_PLURALS array.
	 */
	private static final int INDEX_NOT_FOUND = 0;
	
	/**
	 * The index for the singular's column in the IRREGULAR_PLURALS array.
	 */
	private static final int INDEX_SINGULAR = 0;
	
	/**
	 * The index for the plural's column in the IRREGULAR_PLURALS array.
	 */
	private static final int INDEX_PLURAL = 1;

	/**
	 * List of irregular plurals in English.
	 */
	private static final String[][] IRREGULAR_PLURALS = { { "addendum", "addenda" }, { "addendum", "addenda" },
			{ "alga", "algae" }, { "alumna", "alumnae" }, { "alumnus", "alumni" }, { "analysis", "analyses" },
			{ "antenna", "antennas" }, { "antenna", "antennae" }, { "apparatus", "apparatuses" },
			{ "appendix", "appendices" }, { "appendix", "appendixes" }, { "axis", "axes" }, { "bacillus", "bacilli" },
			{ "bacterium", "bacteria" }, { "basis", "bases" }, { "beau", "beaux" }, { "bison", "bison" },
			{ "buffalo", "buffalos" }, { "buffalo", "buffaloes" }, { "bureau", "bureaus" }, { "bus", "busses" },
			{ "bus", "buses" }, { "cactus", "cactusescacti" }, { "calf", "calves" }, { "child", "children" },
			{ "corps", "corps" }, { "corpus", "corpora" }, { "corpus", "corpuses" }, { "crisis", "crises" },
			{ "criterion", "criteria" }, { "curriculum", "curricula" }, { "datum", "data" }, { "deer", "deer" },
			{ "die", "dice" }, { "dwarf", "dwarfs" }, { "dwarf", "dwarves" }, { "diagnosis", "diagnoses" },
			{ "echo", "echoes" }, { "elf", "elves" }, { "ellipsis", "ellipses" }, { "embargo", "embargoes" },
			{ "emphasis", "emphases" }, { "erratum", "errata" }, { "fireman", "firemen" }, { "fish", "fishfishes" },
			{ "focus", "focuses" }, { "foot", "feet" }, { "formula", "formulas" }, { "fungus", "fungi" },
			{ "fungus", "funguses" }, { "genus", "genera" }, { "goose", "geese" }, { "half", "halves" },
			{ "hero", "heroes" }, { "hippopotamus", "hippopotami" }, { "hippopotamus", "hippopotamuses" },
			{ "hoof", "hoofs" }, { "hoof", "hooves" }, { "hypothesis", "hypotheses" }, { "index", "indices" },
			{ "index", "indexes" }, { "knife", "knives" }, { "leaf", "leaves" }, { "life", "lives" },
			{ "loaf", "loaves" }, { "louse", "lice" }, { "man", "men" }, { "matrix", "matrices" }, { "means", "means" },
			{ "medium", "media" }, { "memorandum", "memoranda" }, { "millennium", "millenniums" },
			{ "millennium", "milennia" }, { "moose", "moose" }, { "mosquito", "mosquitoes" }, { "mouse", "mice" },
			{ "nebula", "nebulae" }, { "nebula", "nebulas" }, { "neurosis", "neuroses" }, { "nucleus", "nuclei" },
			{ "oasis", "oases" }, { "octopus", "octopi" }, { "octopus", "octopuses" }, { "ovum", "ova" },
			{ "ox", "oxen" }, { "paralysis", "paralyses" }, { "parenthesis", "parentheses" }, { "person", "people" },
			{ "phenomenon", "phenomena" }, { "potato", "potatoes" }, { "radius", "radii" }, { "radius", "radiuses" },
			{ "scarf", "scarfs" }, { "scarf", "scarves" }, { "self", "selves" }, { "series", "series" },
			{ "sheep", "sheep" }, { "shelf", "shelves" }, { "scissors", "scissors" }, { "species", "species" },
			{ "stimulus", "stimuli" }, { "stratum", "strata" }, { "syllabus", "syllabi" }, { "syllabus", "syllabuses" },
			{ "symposium", "symposia" }, { "symposium", "symposiums" }, { "synthesis", "syntheses" },
			{ "synopsis", "synopses" }, { "tableau", "tableaux" }, { "that", "those" }, { "thesis", "theses" },
			{ "thief", "thieves" }, { "this", "these" }, { "tomato", "tomatoes" }, { "tooth", "teeth" },
			{ "torpedo", "torpedoes" }, { "vertebra", "vertebrae" }, { "veto", "vetoes" }, { "vita", "vitae" },
			{ "watch", "watches" }, { "wife", "wives" }, { "wolf", "wolves" }, { "woman", "women" },
			{ "zero", "zeros" } };

	private static String word;
	private static String original;
	private static int indexIrregular = INDEX_NOT_FOUND;
	private static boolean upper = false;
	private static boolean capital = false;

	/**
	 * Procedure to get the plural of a word. We are using the following rules:
	 * 
	 * - If the word is found in the IRREGULAR_PLURALS array then use it.
	 * - If the word ends in 'y' then, drop it and append 'ies'.
	 * - If the word ends in 's', 'x', 'z', 'ch', 'sh' then, append 'es'
	 * - For all other cases append 's'
	 * 
	 * @param noun The word to pluralize.
	 * @return A pluralized version of the word.
	 */
	public static String getPlural(String noun) {

		String result = null;
		word = noun.toLowerCase();
		original = noun;
		setLetterCases();
		getIrregularPluralIndex();

		if (indexIrregular != INDEX_NOT_FOUND) {

			String singular = IRREGULAR_PLURALS[indexIrregular][INDEX_SINGULAR];
			String plural = IRREGULAR_PLURALS[indexIrregular][INDEX_PLURAL];

			result = replaceIrregular(singular, plural);

		} else if (word.endsWith("y")) {

			String ending = upper ? "ies".toUpperCase() : "ies";
			result = original.substring(0, original.length() - 1) + ending;

		} else if (word.endsWith("s") || word.endsWith("x") || word.endsWith("z") || word.endsWith("ch")
				|| word.endsWith("sh")) {

			String ending = upper ? "es".toUpperCase() : "es";
			result = original + ending;

		} else {
			String ending = upper ? "s".toUpperCase() : "s";
			result = original + ending;
		}

		return result;
	}


	/**
	 * Procedure to get the singular of a word. We are using the following rules:
	 * 
	 * - If the word is found in the IRREGULAR_PLURALS array then use it.
	 * - If the word ends in 'ies' then, drop it and append 'y'.
	 * - If the word ends in 'ses', 'xes', 'zes', 'ches', 'shes' then drop 'es'.
	 * - For all other cases drop 's'
	 * 
	 * @param noun The word to convert to singular.
	 * @return A singular version of the word.
	 */
	public static String getSingular(String noun) {

		String result = noun;
		word = noun.toLowerCase();
		original = noun;
		setLetterCases();
		getIrregularSingularIndex();

		if (indexIrregular != INDEX_NOT_FOUND) {

			String singular = IRREGULAR_PLURALS[indexIrregular][INDEX_SINGULAR];
			String plural = IRREGULAR_PLURALS[indexIrregular][INDEX_PLURAL];

			result = replaceIrregular(plural, singular);

		} else if (word.endsWith("ies")) {

			String ending = upper ? "y".toUpperCase() : "y";
			result = original.substring(0, original.length() - 3) + ending;

		} else if (word.endsWith("ses") || word.endsWith("xes") || word.endsWith("zes") || word.endsWith("ches")
				|| word.endsWith("shes")) {

			result = original.substring(0, original.length() - 2);

		} else if (word.endsWith("s")){
			result = original.substring(0, original.length() - 1);
		}

		return result;
	}
	
	
	/**
	 * Helper method to replace one word from the other. This method allows
	 * to expand the 'Irregular' array to include compound words of those
	 * irregulars.
	 * 
	 * Ex: AmazingChild -> AmazingChildren
	 * 
	 * @param singular The word in singular
	 * @param plural The equivalent word in plural.
	 * @return The final word with the strings swapped.
	 */
	private static String replaceIrregular(String singular, String plural) {

		String result;

		if (upper) {

			singular = singular.toUpperCase();
			plural = plural.toUpperCase();

		} else if (capital) {

			singular = StringUtils.capitalize(singular);
			plural = StringUtils.capitalize(plural);

		}

		result = original.replaceAll(singular, plural);
		return result;
	}

	/**
	 * Helper method to do the casing for the final words in the class.
	 * We want to preserve the original casing rules from the original
	 * as much as possible.
	 * 
	 * If all letters in the word are UPPER case then we want to use UPPER case.
	 * If the first letter of the word is Upper we Capitalize it.
	 * Otherwise, we keep lower case.
	 */
	private static void setLetterCases() {

		upper = StringUtils.isAllUpperCase(original);
		capital = StringUtils.capitalize(original).equals(original);
	}

	/**
	 * Get the irregular plural of a word.
	 */
	private static void getIrregularPluralIndex() {
		indexForWord(INDEX_SINGULAR);
	}

	/**
	 * Get the irregular singular of a word.
	 */
	private static void getIrregularSingularIndex() {
		indexForWord(INDEX_PLURAL);
	}

	/**
	 * Helper method to find an irregular word in the IRREGULAR_PLURALS array.
	 * 
	 * @param indexToCheck if we want to check plural's or singular's dimension.
	 */
	private static void indexForWord(int indexToCheck) {

		indexIrregular = INDEX_NOT_FOUND;
		for (int i = 0; i < IRREGULAR_PLURALS.length; i++) {
			if (word.endsWith(IRREGULAR_PLURALS[i][indexToCheck])) {
				indexIrregular = i;
				break;
			}
		}
	}
}
