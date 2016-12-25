package com.scramble;

import java.util.UUID;

/**
 * Universal POJO for the project. Will likely replace the individual classes
 * ScrambleInput and ScrambleOutput, but currently those two earn their keep
 * behind the scenes. Plus the instructions ask for two kinds of POJOs so...
 * 
 * ScrambleObj is ideally suited for marshalling considering it's just a
 * collection of getters, setters, and the variables associated with them. 
 * @author AWS_admin
 *
 */
public class ScrambleObj {

	private String text;
	private UUID id;
	private String key;
	
	/*
	 * It's a POJO, and nothing's being set in the constructor here. All private vars will
	 * start off as null, though.
	 */
	public ScrambleObj(){
	}
	
	/*
	 * Getter for the text var.
	 */
	public String getText(){
		return this.text;
	}
	
	/*
	 * Setter for the text var.
	 */
	public void setText(String t){
		this.text = t;
	}
	
	/*
	 * Getter for the ID var.
	 */
	public UUID getIdent(){
		return this.id;
	}
	
	/*
	 * Setter for the ID var.
	 */
	public void setIdent(UUID id){
		this.id = id;
	}
	
	/*
	 * Getter for the key var.
	 */
	public String getKey(){
		return this.key;
	}
	
	/*
	 * Setter for the key var.
	 */
	public void setKey(String k){
		this.key = k;
	}
}
