package br.org.soujava.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * Slide of the Website SouJava
 * 
 * @author Fernando Arruda - progfer@gmail.com
 *
 */
@Data
@Builder
public class Slide implements Serializable{

	private String id;
	private String title;
	private String description;
	private boolean disabled;
	private int order;
	
}
