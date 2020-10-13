package me.coryt.viterbi.util;

import javafx.util.Pair;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class TextProcessingUtil {
	
	public List<List<Pair<String, String>>> tokenizeCorpus(String corpus) {
		return generatePosTuples(splitSentences(splitCorpus(corpus)));
	}
	
	/**
	 * Replace all non-letter/non-whitespace characters and remove any spurious whitespace around them
	 *
	 * @param corpus input text corpus string
	 * @return processed corpus string
	 */
	public String removeNonWordCharacters(String corpus) {
		return corpus
				.replaceAll(
						ApplicationConstants.NON_WORD_CHARACTERS_REGEX,
						ApplicationConstants.EMPTY_STRING)
				.trim();
	}
	
	/**
	 * Split corpus on new lines
	 *
	 * @param corpus string of text
	 * @return list of sentences
	 */
	public List<String> splitCorpus(String corpus) {
		List<String> result = Arrays.stream(corpus.split(ApplicationConstants.NEWLINE_REGEX)).collect(Collectors.toList());
		return result.stream().filter(Predicate.not(String::isEmpty)).collect(Collectors.toList());
	}
	
	/**
	 * Split a list of sentences into a list of Tokens/PoSTag
	 *
	 * @param corpus list of sentences
	 * @return list of sentences represented as tokens
	 */
	public List<List<String>> splitSentences(List<String> corpus) {
		List<List<String>> sentenceList = new ArrayList<>();
		corpus.forEach(str -> {
			List<String> tmpList = new ArrayList<>(Arrays.asList(str.split(ApplicationConstants.WHITESPACE_REGEX)));
			tmpList.removeAll(Arrays.asList("", null));
			sentenceList.add(tmpList);
		});
		return sentenceList;
	}
	
	public List<List<Pair<String, String>>> generatePosTuples(List<List<String>> tokenizedCorpus) {
		List<List<Pair<String, String>>> returnList = new ArrayList<>();
		for (List<String> sentence : tokenizedCorpus) {
			List<Pair<String, String>> sentenceTuples = new ArrayList<>();
			for (String tokenTag : sentence) {
				List<String> tupleList = Arrays.asList(tokenTag.split("/"));
				sentenceTuples.add(new Pair<>(tupleList.get(0), tupleList.get(1)));
			}
			returnList.add(sentenceTuples);
		}
		return returnList;
	}
	
	public String readFromFile(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		
		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return contentBuilder.toString();
	}
}
