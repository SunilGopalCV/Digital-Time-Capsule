package com.timecapsule.service;

import java.time.LocalDateTime;
import java.util.List;

import com.timecapsule.model.TimeCapsule;
import com.timecapsule.model.User;

public interface CapsuleService {
	void createCapsule(User user, String title, LocalDateTime unlockDate);
	void unlockCapsule(TimeCapsule capsule);
	void deleteCapsule(User user, String title);
	
	List<TimeCapsule> getCapsuleByUser(User user);
	List<TimeCapsule> getUnlockedCapsules(User user);
}
