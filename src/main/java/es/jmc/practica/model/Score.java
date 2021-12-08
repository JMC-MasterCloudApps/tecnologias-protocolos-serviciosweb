package es.jmc.practica.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum Score {

	NONE(-1),
	ZERO(0),
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5);
	
	private final int value;
	
	public static Score findByValue(int value) {
		
		for (Score score : Score.values()) {
			if (score.getValue() == value) {
				return score;
			}
		}
		
		log.warn("Invalid score value provided.");
		return NONE;
	}
}
