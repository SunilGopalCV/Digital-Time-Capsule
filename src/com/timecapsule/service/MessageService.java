package com.timecapsule.service;

import java.util.List;

import com.timecapsule.model.Message;
import com.timecapsule.model.Mood;
import com.timecapsule.model.TimeCapsule;

public interface MessageService {
	
	void addMessage(TimeCapsule capsule, Message message);
	List<Message> getMessageByTag(TimeCapsule capsule, String tag);
	List<Message> getMessageByMood(TimeCapsule capsule, Mood mood);
	void displayMessages(List<Message> messages);
	
}
