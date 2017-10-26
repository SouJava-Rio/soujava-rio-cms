package br.org.soujava.api.json;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * Json Error
 * 
 * @author Fernando Arruda - <progfer@gmail.com>
 *
 */
@Builder
@Data
public class JsonError implements Serializable{

	private String code;
	
	private String message;

}
