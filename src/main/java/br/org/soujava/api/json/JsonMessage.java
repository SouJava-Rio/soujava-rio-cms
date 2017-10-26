package br.org.soujava.api.json;

import lombok.Builder;
import lombok.Data;

/**
 * JSON Message 
 * 
 * @author Fernando Arruda - <progfer@gmail.com>
 *
 * @param <T>
 */
@Data
@Builder
public class JsonMessage<T> {
	
	private T data;
	
}
