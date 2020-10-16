package me.coryt.viterbi;

import javafx.util.Pair;
import me.coryt.viterbi.util.ApplicationConstants;
import me.coryt.viterbi.util.ResourceReader;
import me.coryt.viterbi.util.TextProcessingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class HMMTaggerTest {
	HMMTagger hMMTagger = new HMMTagger();
	
	@DisplayName("Should process 3 sentences with each part of speech occurring at the beginning once")
	@Test
	void testCalculateInitialTagProbabilities_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS);
		
		Map<String, Double> result = hMMTagger.calculateInitialTagProbabilities(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(sentences.size(), result.size());
		Assertions.assertEquals(1.0 / 3.0, result.get("NOUN"));
	}
	
	@DisplayName("Should process 4 sentences with each part of speech occurring at the beginning once and noun beginning twice")
	@Test
	void testCalculateInitialTagProbabilities_Case2() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS2);
		
		Map<String, Double> result = hMMTagger.calculateInitialTagProbabilities(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(0.50, result.get("NOUN"));
	}
	
	@DisplayName("Should parse corpus with 8 transitions, and only Noun -> Noun is repeated")
	@Test
	void testCalculateStateTransitionProbabilities_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS3);
		
		Map<String, Double> result = hMMTagger.calculateStateTransitionProbabilities(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(0.25, result.get("NOUN VERB"));
		Assertions.assertEquals(0.125, result.get("NOUN NOUN"));
	}
	
	@DisplayName("Should parse corpus")
	@Test
	void testCalculateStateTransitionProbabilities_Case2() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS2);
		
		Map<String, Double> result = hMMTagger.calculateStateTransitionProbabilities(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(0.00986103179135732, result.get("NOUN VERB"));
		Assertions.assertEquals(0.07846183133447554, result.get("NOUN NOUN"));
	}
	
	@DisplayName("Should parse corpus from training data")
	@Test
	void testCalculateStateTransitionProbabilities_Case3() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ResourceReader.readFileToString("train/ca01"));
		
		Map<String, Double> result = hMMTagger.calculateStateTransitionProbabilities(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.size() < 122);
		Assertions.assertEquals(2.1846325163685504E-7, result.get("NOUN VERB"));
		Assertions.assertEquals(2.0541906841494624E-10, result.get("NOUN NOUN"));
	}
}