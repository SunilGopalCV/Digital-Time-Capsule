package com.timecapsule.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.timecapsule.model.TimeCapsule;
import com.timecapsule.model.User;

public class CapsuleServiceImp implements CapsuleService {
	private Map<User, List<TimeCapsule>> capsuleMap = new HashMap<>();
	
	public Map<User, List<TimeCapsule>> getCapsuleMap() {
	    return capsuleMap;
	}


	@Override
	public void createCapsule(User user, String title, LocalDateTime unlockDate) {
		TimeCapsule capsule = new TimeCapsule(title, unlockDate);
		capsuleMap.computeIfAbsent(user, k -> new ArrayList<>()).add(capsule);

	}

	@Override
	public void unlockCapsule(TimeCapsule capsule) {
		if (capsule.isUnlocked()) {
			System.out.println("Capsule Unlocked: " + capsule.getTitle());
		} else {
			System.out.println("Capsule is still locked. Unlocks on: " + capsule.getUnlockDate());
		}
	}

	@Override
	public void deleteCapsule(User user, String title) {
		List<TimeCapsule> capsules = capsuleMap.get(user);
		if (capsules != null) {
			capsules.removeIf(capsule -> capsule.getTitle().equalsIgnoreCase(title));
		}

	}

	@Override
	public List<TimeCapsule> getCapsuleByUser(User user) {
		return capsuleMap.getOrDefault(user, new ArrayList<>());
	}

	@Override
	public List<TimeCapsule> getUnlockedCapsules(User user) {
		List<TimeCapsule> unlocked = new ArrayList<>();
		List<TimeCapsule> capsules = capsuleMap.getOrDefault(user, new ArrayList<>());
		for (TimeCapsule capsule : capsules) {
			if (capsule.isUnlocked()) {
				unlocked.add(capsule);
			}
		}
		return unlocked;
	}

}
