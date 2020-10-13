package me.coryt.viterbi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PosTuple {
	private String token;
	// TODO: Move to enum
	// NOUN
	// VERB
	// DETERMINER
	// PRONOUN
	// X
	// PREPOSITION
	// ADJECTIVE
	// PUNCT
	// CONJUNCTION
	// ADVERB
	// INTERJECTION
	private String posTag;
}
