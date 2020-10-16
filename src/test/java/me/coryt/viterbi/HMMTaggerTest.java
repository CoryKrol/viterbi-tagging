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
		Assertions.assertTrue(result.size() < 12);
		Assertions.assertEquals(sentences.size(), result.size());
		Assertions.assertEquals(1.0 / 3.0, result.get("NOUN"));
	}
	
	@DisplayName("Should process 4 sentences with each part of speech occurring at the beginning once and noun beginning twice")
	@Test
	void testCalculateInitialTagProbabilities_Case2() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS2);
		
		Map<String, Double> result = hMMTagger.calculateInitialTagProbabilities(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.size() < 12);
		Assertions.assertEquals(0.50, result.get("NOUN"));
	}
	
	@DisplayName("Should process 4 sentences with each part of speech occurring at the beginning once and noun beginning twice")
	@Test
	void testCalculateInitialTagProbabilities_Case3() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ResourceReader.readFileToString("train/ca01")
				);
		
		Map<String, Double> result = hMMTagger.calculateInitialTagProbabilities(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.size() < 12);
		Assertions.assertEquals(0.2755102040816326, result.get("NOUN"));
	}
	
	@DisplayName("Should parse corpus with 8 transitions, and only Noun -> Noun is repeated")
	@Test
	void testCountStateTransitions_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS3);
		
		Map<String, Integer> result = hMMTagger.countStateTransitions(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.get("NOUN VERB"));
		Assertions.assertEquals(1, result.get("NOUN NOUN"));
	}
	
	@DisplayName("Should parse corpus")
	@Test
	void testCountStateTransitions_Case2() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS2);
		
		Map<String, Integer> result = hMMTagger.countStateTransitions(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(5, result.get("NOUN VERB"));
		Assertions.assertEquals(16, result.get("NOUN NOUN"));
	}
	
	@DisplayName("Should parse corpus from training data")
	@Test
	void testCountStateTransitions_Case3() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ResourceReader.readFileToString("train/ca01"));
		
		Map<String, Integer> result = hMMTagger.countStateTransitions(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.size() < 122);
		Assertions.assertEquals(123, result.get("NOUN VERB"));
		Assertions.assertEquals(207, result.get("NOUN NOUN"));
	}
	
	@Test
	void testCalculateStateTransitionProb_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(ApplicationConstants.TEST_CORPUS3);
		
		Map<String, Integer> transitionCounts = hMMTagger.countStateTransitions(sentences);
		Map<String, Integer> tagCounts = hMMTagger.posCounter(sentences);
		
		Map<String, Double> result = hMMTagger.calculateStateTransitionProb(
				transitionCounts,
				tagCounts
		);
		
		
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.size() < 122);
		Assertions.assertEquals(0.3333333333333333, result.get("NOUN VERB"));
		Assertions.assertEquals(0.2222222222222222, result.get("NOUN NOUN"));
	}
	
	@DisplayName("Should count the number of occurrences of each POS")
	@Test
	void testPosCounter_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(ApplicationConstants.TEST_CORPUS3);
		
		Map<String, Integer> result = hMMTagger.posCounter(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.size() < 12);
		Assertions.assertEquals(5, result.get("NOUN"));
		Assertions.assertEquals(2, result.get("VERB"));
	}
	
	@DisplayName("Should count the number of occurrences of each POS")
	@Test
	void testPosCounter_Case2() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(ApplicationConstants.TEST_CORPUS2);
		
		Map<String, Integer> result = hMMTagger.posCounter(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.size() < 12);
		Assertions.assertEquals(43, result.get("NOUN"));
		Assertions.assertEquals(12, result.get("VERB"));
	}
	
	@DisplayName("Should count the number of occurrences of each POS")
	@Test
	void testPosCounter_Case3() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ResourceReader.readFileToString("train/ca01"));
		
		Map<String, Integer> result = hMMTagger.posCounter(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.size() < 12);
		Assertions.assertEquals(704, result.get("NOUN"));
		Assertions.assertEquals(382, result.get("VERB"));
	}
	
	@DisplayName("Should count the number of occurrences of a word with a given tag")
	@Test
	void testCountWordGivenTag_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ApplicationConstants.TEST_CORPUS4);
		
		Map<String, Integer> result = hMMTagger.countWordGivenTag(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.get("friday NOUN"));
		Assertions.assertEquals(1, result.get("proposed VERB"));
	}
	
	@DisplayName("Should count the number of occurrences of a word with a given tag")
	@Test
	void testCountWordGivenTag_Case2() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ApplicationConstants.TEST_CORPUS2);
		
		Map<String, Integer> result = hMMTagger.countWordGivenTag(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.get("friday NOUN"));
		Assertions.assertEquals(1, result.get("proposed VERB"));
	}
	
	@DisplayName("Should count the number of occurrences of a word with a given tag")
	@Test
	void testCountWordGivenTag_Case3() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ResourceReader.readFileToString("train/ca01"));
		
		Map<String, Integer> result = hMMTagger.countWordGivenTag(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(4, result.get("friday NOUN"));
		Assertions.assertEquals(2, result.get("proposed VERB"));
	}
	
	@DisplayName("Should count the number of occurrences of a word with a given tag")
	@Test
	void testCalculateEmissionProb_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ApplicationConstants.TEST_CORPUS4);
		
		Map<String, Integer> wordCounts = hMMTagger.countWordGivenTag(sentences);
		Map<String, Integer> tagCounts = hMMTagger.posCounter(sentences);
		
		int vocabSize = hMMTagger.countTokens(sentences).size();
		
		Map<String, Double> result = hMMTagger.calculateEmissionProb(wordCounts, tagCounts, vocabSize);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(0.1875, result.get("friday NOUN"));
		Assertions.assertEquals(0.16666666666666666, result.get("proposed VERB"));
	}
	
	@Test
	void testTokenizeInputSentence() {
		List<String> result = hMMTagger.tokenizeInputSentence("tokenize input sentence please");
		Assertions.assertEquals(4, result.size());
	}
	
	@Test
	void testCountTokens() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ApplicationConstants.TEST_CORPUS3);
		
		Map<String, Integer> result = hMMTagger.countTokens(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.get("friday"));
	}
	
	@Test
	void testTagCount() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ApplicationConstants.TEST_CORPUS3);
		
		Map<String, Integer> result = hMMTagger.countTokens(sentences);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.get("friday"));
	}
	
	@Test
	void testViterbiDecode_Case1() {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ResourceReader.readFileToString("train/ca01"));//ApplicationConstants.TEST_CORPUS3);
		
		hMMTagger.initialize_probabilities(sentences);
		
		String result = hMMTagger.viterbi_decode("merger proposed friday");
		
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	void testViterbiDecode_Case2() throws Exception {
		List<List<Pair<String, String>>> sentences = TextProcessingUtil
				.tokenizeCorpus(
						ResourceReader.readFilesInDirToString("train/"));//ApplicationConstants.TEST_CORPUS3);
		
		hMMTagger.initialize_probabilities(sentences);
		
		String result = hMMTagger.viterbi_decode("merger proposed friday");
		
		
		Assertions.assertNotNull(result);
	}
}