package com.timecapsule.model;

public class TextMessage extends Message{

	public TextMessage(String content, Mood mood, String tag) {
		super(content, mood, tag);
	}

	@Override
	public void display() {
		System.out.println("Text Message: "+getContent());
	}

}
