package com.timecapsule.model;

public abstract class Message {
	private String content;
	private Mood mood;
	private String tag;
	
	public Message(String content, Mood mood, String tag) {
		super();
		this.content = content;
		this.mood = mood;
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public Mood getMood() {
		return mood;
	}

	public String getTag() {
		return tag;
	}

	public abstract void display();
	
}
