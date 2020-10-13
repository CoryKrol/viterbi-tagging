package me.coryt.viterbi;

import javafx.util.Pair;
import me.coryt.viterbi.util.ApplicationConstants;
import me.coryt.viterbi.util.TextProcessingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class HMMTaggerTest {
	HMMTagger hMMTagger = new HMMTagger();

//	@Test
//	void testLoad_corpus() {
//		List<List<Pair<String, String>>> result = hMMTagger.load_corpus("path");
//		Assertions.assertEquals(Arrays.<List<Pair<String, String>>>asList(Arrays.<Pair<String, String>>asList(null)), result);
//	}
//
//	@Test
//	void testInitialize_probabilities() {
//		hMMTagger.initialize_probabilities(Arrays.<List<Pair<String, String>>>asList(Arrays.<Pair<String, String>>asList(null)));
//	}
	
	@DisplayName("Should process 3 sentences with each part of speech occurring at the beginning once")
	@Test
	void testCalculateInitialTagProbabilities_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS);
		
		Map<String, Double> initialTagProbabilities = hMMTagger.calculateInitialTagProbabilities(sentences);
		
		Assertions.assertNotNull(initialTagProbabilities);
		Assertions.assertEquals(sentences.size(), initialTagProbabilities.size());
		Assertions.assertEquals(1.0 / 3.0, initialTagProbabilities.get("NOUN"));
	}
	
	@DisplayName("Should process 4 sentences with each part of speech occurring at the beginning once and noun beginning twice")
	@Test
	void testCalculateInitialTagProbabilities_Case2() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS2);
		
		Map<String, Double> initialTagProbabilities = hMMTagger.calculateInitialTagProbabilities(sentences);
		
		Assertions.assertNotNull(initialTagProbabilities);
		Assertions.assertEquals(0.50, initialTagProbabilities.get("NOUN"));
	}
}