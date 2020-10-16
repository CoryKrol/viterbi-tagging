package me.coryt.viterbi.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {
	public final String[] TEST_SENTENCES = {
			"Merger/NOUN proposed/VERB ",
			"The/DETERMINER Fulton/NOUN County/NOUN Grand/ADJECTIVE Jury/NOUN said/VERB Friday/NOUN an/DETERMINER investigation/NOUN of/PREPOSITION Atlanta's/NOUN recent/ADJECTIVE primary/NOUN election/NOUN produced/VERB ``/PUNCT no/DETERMINER ",
			"``/PUNCT This/DETERMINER is/VERB one/NUMBER of/PREPOSITION the/DETERMINER major/ADJECTIVE items/NOUN in/PREPOSITION the/DETERMINER Fulton/NOUN County/NOUN general/ADJECTIVE assistance/NOUN program/NOUN ''/PUNCT ,/PUNCT the/DETERMINER jury/NOUN said/VERB ,/PUNCT but/CONJUNCTION the/DETERMINER State/NOUN Welfare/NOUN Department/NOUN ``/PUNCT has/VERB seen/VERB fit/ADJECTIVE to/X distribute/VERB these/DETERMINER funds/NOUN through/PREPOSITION the/DETERMINER welfare/NOUN departments/NOUN of/PREPOSITION all/X the/DETERMINER counties/NOUN in/PREPOSITION the/DETERMINER state/NOUN with/PREPOSITION the/DETERMINER exception/NOUN of/PREPOSITION Fulton/NOUN County/NOUN ,/PUNCT which/DETERMINER receives/VERB none/PRONOUN of/PREPOSITION this/DETERMINER money/NOUN ./PUNCT"
	};
	
	public final String START_OF_SENTENCE_WORD = "<s>";
	public final String END_OF_SENTENCE_WORD = "</s>";
	
	
	// Regexes
	public final String NEWLINE_REGEX = "\\n+";
	public final String WHITESPACE_REGEX = "\\s+";
	//([^a-zA-z\s]|\[|])+
	// _____________
	public final String NON_WORD_CHARACTERS_REGEX = "([^a-zA-z\\s]|\\[|]|\\_)+";
	
	public final String EMPTY_STRING = "";
	
	public final String CLASSPATH_STRING = "classpath:";
	
	public final String TEST_CORPUS = "Merger/NOUN proposed/VERB \n" +
			"The/DETERMINER Fulton/NOUN County/NOUN Grand/ADJECTIVE Jury/NOUN said/VERB Friday/NOUN an/DETERMINER investigation/NOUN of/PREPOSITION Atlanta's/NOUN recent/ADJECTIVE primary/NOUN election/NOUN produced/VERB ``/PUNCT no/DETERMINER \n" +
			"``/PUNCT This/DETERMINER is/VERB one/NUMBER of/PREPOSITION the/DETERMINER major/ADJECTIVE items/NOUN in/PREPOSITION the/DETERMINER Fulton/NOUN County/NOUN general/ADJECTIVE assistance/NOUN program/NOUN ''/PUNCT ,/PUNCT the/DETERMINER jury/NOUN said/VERB ,/PUNCT but/CONJUNCTION the/DETERMINER State/NOUN Welfare/NOUN Department/NOUN ``/PUNCT has/VERB seen/VERB fit/ADJECTIVE to/X distribute/VERB these/DETERMINER funds/NOUN through/PREPOSITION the/DETERMINER welfare/NOUN departments/NOUN of/PREPOSITION all/X the/DETERMINER counties/NOUN in/PREPOSITION the/DETERMINER state/NOUN with/PREPOSITION the/DETERMINER exception/NOUN of/PREPOSITION Fulton/NOUN County/NOUN ,/PUNCT which/DETERMINER receives/VERB none/PRONOUN of/PREPOSITION this/DETERMINER money/NOUN ./PUNCT";
	
	public final String TEST_CORPUS2 = "Merger/NOUN proposed/VERB \n" +
			"The/DETERMINER Fulton/NOUN County/NOUN Grand/ADJECTIVE Jury/NOUN said/VERB Friday/NOUN an/DETERMINER investigation/NOUN of/PREPOSITION Atlanta's/NOUN recent/ADJECTIVE primary/NOUN election/NOUN produced/VERB ``/PUNCT no/DETERMINER \n" +
			"``/PUNCT This/DETERMINER is/VERB one/NUMBER of/PREPOSITION the/DETERMINER major/ADJECTIVE items/NOUN in/PREPOSITION the/DETERMINER Fulton/NOUN County/NOUN general/ADJECTIVE assistance/NOUN program/NOUN ''/PUNCT ,/PUNCT the/DETERMINER jury/NOUN said/VERB ,/PUNCT but/CONJUNCTION the/DETERMINER State/NOUN Welfare/NOUN Department/NOUN ``/PUNCT has/VERB seen/VERB fit/ADJECTIVE to/X distribute/VERB these/DETERMINER funds/NOUN through/PREPOSITION the/DETERMINER welfare/NOUN departments/NOUN of/PREPOSITION all/X the/DETERMINER counties/NOUN in/PREPOSITION the/DETERMINER state/NOUN with/PREPOSITION the/DETERMINER exception/NOUN of/PREPOSITION Fulton/NOUN County/NOUN ,/PUNCT which/DETERMINER receives/VERB none/PRONOUN of/PREPOSITION this/DETERMINER money/NOUN ./PUNCT \n" +
			"Mrs./NOUN J./NOUN Edward/NOUN Hackstaff/NOUN and/CONJUNCTION Mrs./NOUN Paul/NOUN Luette/NOUN are/VERB planning/VERB a/DETERMINER luncheon/NOUN next/DETERMINER week/NOUN in/PREPOSITION honor/NOUN of/PREPOSITION Mrs./NOUN J./NOUN Clinton/NOUN Bowman/NOUN ,/PUNCT who/PRONOUN celebrates/VERB her/PRONOUN birthday/NOUN on/PREPOSITION Tuesday/NOUN ./PUNCT ";
	
	
	public final String TEST_CORPUS3 = "Merger/NOUN proposed/VERB \n" +
			"The/DETERMINER Fulton/NOUN County/NOUN Grand/ADJECTIVE Jury/NOUN said/VERB Friday/NOUN an/DETERMINER";
	
	public final String TEST_CORPUS4 = "Merger/NOUN proposed/VERB friday/NOUN \n" +
			"The/DETERMINER Fulton/NOUN County/NOUN Grand/ADJECTIVE Jury/NOUN said/VERB Friday/NOUN an/DETERMINER";
	
	
	public boolean LAPLACE_SMOOTHING = true;
}
