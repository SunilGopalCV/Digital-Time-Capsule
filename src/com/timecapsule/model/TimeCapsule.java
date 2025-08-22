package com.timecapsule.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TimeCapsule {
	private String title;
	private LocalDateTime unlockDate;
	private List<Message> messages;
	private Set<String> tags;

	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public TimeCapsule(String title, LocalDateTime unlockDate) {
		super();
		this.title = title;
		this.unlockDate = unlockDate;
		this.messages = new ArrayList<>();
		this.tags = new HashSet<>();
	}

	public void addMessage(Message message) {
		messages.add(message);
		tags.add(message.getTag());
	}

	public boolean isUnlocked() {
		return !LocalDateTime.now().isBefore(unlockDate);
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getUnlockDate() {
		return unlockDate;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public Set<String> getTags() {
		return tags;
	}

}
