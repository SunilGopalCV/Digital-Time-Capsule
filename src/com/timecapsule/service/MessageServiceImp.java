package com.timecapsule.service;

import java.util.ArrayList;
import java.util.List;

import com.timecapsule.model.Message;
import com.timecapsule.model.Mood;
import com.timecapsule.model.TimeCapsule;

public class MessageServiceImp implements MessageService{

	@Override
	public void addMessage(TimeCapsule capsule, Message message) {
		capsule.addMessage(message);		
	}

	@Override
	public List<Message> getMessageByTag(TimeCapsule capsule, String tag) {
		List<Message> result = new ArrayList<>();
		for(Message message: capsule.getMessages()) {
			if(message.getTag().equalsIgnoreCase(tag)) {
				result.add(message);
			}
		}
		return result;
	}

	@Override
	public List<Message> getMessageByMood(TimeCapsule capsule, Mood mood) {
		List<Message> result = new ArrayList<>();
		for(Message message: capsule.getMessages()) {
			if(message.getMood() == mood) {
				result.add(message);
			}
		}
		return result;
	}

	@Override
	public void displayMessages(List<Message> messages) {
		for(Message message: messages) {
			message.display();
		}
	}
	
}
