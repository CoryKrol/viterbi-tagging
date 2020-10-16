package me.coryt.viterbi;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.coryt.viterbi.util.ApplicationConstants;
import me.coryt.viterbi.util.TextProcessingUtil;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class HMMTagger {
	private Map<String, Double> initialStateProbabilities;
	private Map<String, Double> stateTransitionProbabilities;
	private Map<String, Double> emissionProbabilities;
	private int vocabSize;
	private Map<String, Integer> posCounts;
	private List<String> posTagKeys;
	
	private List<List<Double>> viterbi;
	private List<List<Integer>> backpointer;
	
	public List<List<Pair<String, String>>> load_corpus(String path) {
		return TextProcessingUtil.tokenizeCorpus(TextProcessingUtil.readFromFile(path));
	}
	
	public void initialize_probabilities(List<List<Pair<String, String>>> sentences) {
		vocabSize = countTokens(sentences).size();
		posCounts = posCounter(sentences);
		posTagKeys = new ArrayList<>(posCounts.keySet());
		
		initialStateProbabilities = calculateInitialTagProbabilities(sentences);
		
		
		Map<String, Integer> stateTransitionCounts = countStateTransitions(sentences);
		stateTransitionProbabilities = calculateStateTransitionProb(stateTransitionCounts, posCounts);
		
		Map<String, Integer> wordCounts = countWordGivenTag(sentences);
		emissionProbabilities = calculateEmissionProb(wordCounts, posCounts, vocabSize);
	}
	
	public String viterbi_decode(String sentence) {
		List<String> sentenceTokens = tokenizeInputSentence(sentence.toLowerCase());
		initializeViterbi(sentenceTokens);
		
		int inputLength = sentenceTokens.size();
		
		for (int i = 1; i < inputLength; i++) {
			for (int state = 0; state < posTagKeys.size(); state++) {
				int index = getStateArgMax(viterbi, i - 1, state, sentenceTokens.get(i));
				double maxValue = calculateViterbiValue(viterbi, i - 1, index, state, sentenceTokens.get(i));
				backpointer.get(state).set(i, index);
				viterbi.get(state).set(i, maxValue);
			}
		}
		
		int maxIndex = -1;
		double maxValue = -1.0;
		for (int i = 0; i < viterbi.size(); i++) {
			double lastWordStateProb = viterbi.get(i).get(inputLength - 1);
			if (lastWordStateProb > maxValue) {
				maxValue = lastWordStateProb;
				maxIndex = i;
			}
		}
		
		String maxPath = getMaxPath(maxIndex, inputLength - 1);
		log.info("Predicted Parts of Speech: " + maxPath);
		log.info("Best Path Probability: " + maxValue);
		
		
		return maxPath;
	}
	
	private String getMaxPath(int state, int timeStep) {
		int prevState = backpointer.get(state).get(timeStep);
		return getPath(prevState, timeStep - 1) +
				posTagKeys.get(state) + "]";
	}
	
	private String getPath(int state, int timeStep) {
		int prevState = backpointer.get(state).get(timeStep);
		if (timeStep != 0) {
			return getPath(prevState, timeStep - 1) + posTagKeys.get(state) + ", ";
		} else {
			return "[" + posTagKeys.get(state) + ", ";
		}
	}
	
	private void initializeViterbi(List<String> sentenceTokens) {
		viterbi = new ArrayList<>();
		backpointer = new ArrayList<>();
		
		String firstWord = sentenceTokens.get(0);
		for (int state = 0; state < posTagKeys.size(); state++) {
			String wordPosCombo = firstWord + " " + posTagKeys.get(state);
			viterbi.add(new ArrayList<>());
			viterbi.get(state).add(
					getInitialTagProbability(posTagKeys.get(state)) * getEmissionProbability(wordPosCombo)
			);
			
			backpointer.add(new ArrayList<>());
			backpointer.get(state).add(0);
			
			for (int i = 1; i < sentenceTokens.size(); i++) {
				viterbi.get(state).add(0.0);
				backpointer.get(state).add(0);
			}
		}
	}
	
	private double calculateViterbiValue(List<List<Double>> viterbi, int prevTimeStep, int prevState, int currentState, String token) {
		
		String stateTransition = posTagKeys.get(prevState) + " " + posTagKeys.get(currentState);
		String tokenTag = token + " " + posTagKeys.get(currentState);
		return viterbi.get(prevState).get(prevTimeStep)
				* getStateTransitionProbability(stateTransition)
				* getEmissionProbability(tokenTag);
	}
	
	private int getStateArgMax(List<List<Double>> viterbi, int prevTimeStep, int currentState, String token) {
		int index = -1;
		double value = -1.0;
		for (int i = 0; i < viterbi.size(); i++) {
			double viterbiValue = calculateViterbiValue(viterbi, prevTimeStep, i, currentState, token);
			
			if (viterbiValue > value) {
				value = viterbiValue;
				index = i;
			}
		}
		return index;
	}
	
	public double getInitialTagProbability(String tag) {
		if (initialStateProbabilities.containsKey(tag)) {
			return initialStateProbabilities.get(tag);
		} else {
			return 0.0;
		}
	}
	
	public double getStateTransitionProbability(String tokenTag) {
		if (stateTransitionProbabilities.containsKey(tokenTag)) {
			return stateTransitionProbabilities.get(tokenTag);
		} else {
			int numPos = posCounts.size();
			List<String> split = Arrays.asList(tokenTag.split("\\s"));
			return 1.0 / (double) (posCounts.get(split.get(0)) + numPos);
		}
	}
	
	public double getEmissionProbability(String wordPosCombo) {
		if (emissionProbabilities.containsKey(wordPosCombo)) {
			return emissionProbabilities.get(wordPosCombo);
		} else {
			List<String> tokenTag = Arrays.asList(wordPosCombo.split("\\s"));
			return 1.0 / (double) (posCounts.get(tokenTag.get(1)) + vocabSize);
		}
	}
	
	public Map<String, Double> calculateInitialTagProbabilities(List<List<Pair<String, String>>> sentences) {
		Map<String, Double> tagMap = new HashMap<>();
		for (List<Pair<String, String>> sentence : sentences) {
			// Key is Token and Value is TAG
			String posTag = sentence.get(0).getValue();
			if (tagMap.containsKey(posTag)) {
				tagMap.put(posTag, tagMap.get(posTag) + 1.0);
			} else {
				tagMap.put(posTag, 1.0);
			}
		}
		
		double count = 0;
		for (Map.Entry<String, Double> entry : tagMap.entrySet()) {
			count += entry.getValue();
		}
		
		for (Map.Entry<String, Double> entry : tagMap.entrySet()) {
			entry.setValue(entry.getValue() / sentences.size());
		}
		
		return tagMap;
	}
	
	public Map<String, Integer> countStateTransitions(List<List<Pair<String, String>>> sentences) {
		
		Map<String, Integer> tagMap = new HashMap<>();
		
		for (List<Pair<String, String>> sentence : sentences) {
			for (int i = 0; i < sentence.size() - 1; i++) {
				String tagChange = sentence.get(i).getValue() + " " + sentence.get(i + 1).getValue();
				if (tagMap.containsKey(tagChange)) {
					tagMap.put(tagChange, tagMap.get(tagChange) + 1);
				} else {
					tagMap.put(tagChange, 1);
				}
			}
		}
		
		return tagMap;
	}
	
	public Map<String, Double> calculateStateTransitionProb(Map<String, Integer> transitionCounts, Map<String, Integer> posCounts) {
		Map<String, Double> tagMap = new HashMap<>();
		int numOfPos = posCounts.size();
		for (Map.Entry<String, Integer> entry : transitionCounts.entrySet()) {
			List<String> split = Arrays.asList(entry.getKey().split("\\s"));
			tagMap.put(entry.getKey(), (double) (entry.getValue() + 1) / (double) (posCounts.get(split.get(0)) + numOfPos));
		}
		
		return tagMap;
	}
	
	public Map<String, Integer> posCounter(List<List<Pair<String, String>>> sentences) {
		Map<String, Integer> tagMap = new HashMap<>();
		
		for (List<Pair<String, String>> sentence : sentences) {
			for (int i = 0; i < sentence.size(); i++) {
				String tagChange = sentence.get(i).getValue();
				if (tagMap.containsKey(tagChange)) {
					tagMap.put(tagChange, tagMap.get(tagChange) + 1);
				} else {
					tagMap.put(tagChange, 1);
				}
			}
		}
		
		return tagMap;
	}
	
	public Map<String, Integer> countWordGivenTag(List<List<Pair<String, String>>> sentences) {
		Map<String, Integer> tagMap = new HashMap<>();
		
		for (List<Pair<String, String>> sentence : sentences) {
			for (int i = 0; i < sentence.size(); i++) {
				String tagChange = sentence.get(i).getKey() + " " + sentence.get(i).getValue();
				if (tagMap.containsKey(tagChange)) {
					tagMap.put(tagChange, tagMap.get(tagChange) + 1);
				} else {
					tagMap.put(tagChange, 1);
				}
			}
		}
		
		return tagMap;
	}
	
	public Map<String, Double> calculateEmissionProb(Map<String, Integer> wordTagCounts, Map<String, Integer> postCounts, int vocabSize) {
		Map<String, Double> tagMap = new HashMap<>();
		
		for (Map.Entry<String, Integer> entry : wordTagCounts.entrySet()) {
			List<String> split = Arrays.asList(entry.getKey().split("\\s"));
			tagMap.put(entry.getKey(), (double) (entry.getValue() + 1) / (double) (postCounts.get(split.get(1)) + vocabSize));
		}
		
		
		return tagMap;
	}
	
	public List<String> tokenizeInputSentence(String input) {
		return Arrays.asList(input.split(ApplicationConstants.WHITESPACE_REGEX));
	}
	
	public Map<String, Integer> countTokens(List<List<Pair<String, String>>> sentences) {
		Map<String, Integer> tokenMap = new HashMap<>();
		
		for (List<Pair<String, String>> sentence : sentences) {
			for (Pair<String, String> tokenTag : sentence) {
				String token = tokenTag.getKey();
				if (tokenMap.containsKey(token)) {
					tokenMap.put(token, tokenMap.get(token) + 1);
				} else {
					tokenMap.put(token, 1);
				}
			}
		}
		
		return tokenMap;
	}
}
