package com.al.json2model.general;

import org.apache.commons.lang3.StringUtils;

public class Pluralize {

	private static final int indexNotFound = 0;
	private static final int indexSingular = 0;
	private static final int indexPlural = 1;

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
	private static int indexIrregular = indexNotFound;
	private static boolean upper = false;
	private static boolean capital = false;

	public static String getPlural(String noun) {

		String result = null;
		word = noun.toLowerCase();
		original = noun;
		setLetterCases();
		getIrregularPluralIndex();

		if (indexIrregular != indexNotFound) {

			String singular = IRREGULAR_PLURALS[indexIrregular][indexSingular];
			String plural = IRREGULAR_PLURALS[indexIrregular][indexPlural];

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
	 * @param singular
	 * @param plural
	 * @return
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

		// Use substitute in case we have compound words of irregulars.
		result = original.replaceAll(singular, plural);
		return result;
	}

	public static String getSingular(String noun) {

		String result = null;
		word = noun.toLowerCase();
		original = noun;
		setLetterCases();
		getIrregularSingularIndex();

		if (indexIrregular != indexNotFound) {

			String singular = IRREGULAR_PLURALS[indexIrregular][indexSingular];
			String plural = IRREGULAR_PLURALS[indexIrregular][indexPlural];

			result = replaceIrregular(plural, singular);

		} else if (word.endsWith("ies")) {

			String ending = upper ? "y".toUpperCase() : "y";
			result = original.substring(0, original.length() - 3) + ending;

		} else if (word.endsWith("ses") || word.endsWith("xes") || word.endsWith("zes") || word.endsWith("ches")
				|| word.endsWith("shes")) {

			result = original.substring(0, original.length() - 2);

		} else {
			result = original.substring(0, original.length() - 1);
		}

		return result;
	}

	private static void setLetterCases() {

		upper = StringUtils.isAllUpperCase(original);
		capital = StringUtils.capitalize(original).equals(original);
	}

	private static void getIrregularPluralIndex() {
		indexForWord(indexSingular);
	}

	private static void getIrregularSingularIndex() {
		indexForWord(indexPlural);
	}

	private static void indexForWord(int indexToCheck) {

		indexIrregular = indexNotFound;
		for (int i = 0; i < IRREGULAR_PLURALS.length; i++) {
			if (word.endsWith(IRREGULAR_PLURALS[i][indexToCheck])) {
				indexIrregular = i;
				break;
			}
		}
	}
}
