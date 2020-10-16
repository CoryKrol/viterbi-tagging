package me.coryt.viterbi;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class HMMTagger {
	
	public List<List<Pair<String, String>>> load_corpus(String path) {
		return null;
	}
	
	public void initialize_probabilities(List<List<Pair<String, String>>> sentences) {
		// implementing after all the pieces are done
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
			entry.setValue(entry.getValue() / count);
		}
		
		return tagMap;
	}
	
	public Map<String, Double> calculateStateTransitionProbabilities(List<List<Pair<String, String>>> sentences) {
		
		Map<String, Double> tagMap = new HashMap<>();
		
		int totalPossibleTransitions = 0;
		for (List<Pair<String, String>> sentence : sentences) {
			for (int i = 0; i < sentence.size() - 1; i++) {
				String tagChange = sentence.get(i).getValue() + " " + sentence.get(i + 1).getValue();
				if (tagMap.containsKey(tagChange)) {
					tagMap.put(tagChange, tagMap.get(tagChange) + 1.0);
				} else {
					tagMap.put(tagChange, 1.0);
				}
				totalPossibleTransitions++;
			}
			
			
			for (Map.Entry<String, Double> entry : tagMap.entrySet()) {
				entry.setValue(entry.getValue() / (double) totalPossibleTransitions);
			}
		}
		
		return tagMap;
	}
}
