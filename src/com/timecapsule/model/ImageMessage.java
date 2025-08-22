package com.timecapsule.model;

public class ImageMessage extends Message{
	
	private String imagePath;
	

	public String getImagePath() {
		return imagePath;
	}

	public ImageMessage(String content, Mood mood, String tag, String imagePath) {
		super(content, mood, tag);
		this.imagePath = imagePath;
	}

	@Override
	public void display() {
		System.out.println("Image: "+ getContent() + "Image Path ["+ imagePath +"]");
	}
	
}
