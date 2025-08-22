//package com.timecapsule.app;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
//import com.timecapsule.model.ImageMessage;
//import com.timecapsule.model.Message;
//import com.timecapsule.model.Mood;
//import com.timecapsule.model.TextMessage;
//import com.timecapsule.model.TimeCapsule;
//import com.timecapsule.model.User;
//import com.timecapsule.service.CapsuleServiceImp;
//import com.timecapsule.service.MessageServiceImp;
//
//public class UserInterface {
//
//	private final Scanner scanner = new Scanner(System.in);
//	private final CapsuleServiceImp capsuleService = new CapsuleServiceImp();
//	private final MessageServiceImp messageService = new MessageServiceImp();
//
//	private final Map<String, User> userMap = new HashMap<>();
//	private User currentUser;
//
//	public void start() {
//		System.out.println("Welcome to Digital Time Capsule");
//		boolean running = true;
//		while (running) {
//			System.out.println("\n ***** Main menu ******");
//			System.out.print("1. Create User\n" + "2. Create Time Capsule\n" + "3. Add message to capsule\n"
//					+ "4. View Capsules\n" + "5. Unlock Capsules\n" + "6. Search Messages\n" + "7. Exit\n"
//					+ "Please Enter your choice:");
//
//			String choice = scanner.next();
//
//			switch (choice) {
//			case "1" -> running = createUser();
//			case "2" -> createCapsule();
//			case "3" -> addMessageToCapsule();
//			case "4" -> viewCapsules();
//			case "6" -> searchMessages();
//			case "5" -> unlockCapsules();
//			case "7" -> running = false;
//			default -> System.out.println("Invalid choice please try again");
//			}
//		}
//		System.out.println("Thank you for using digital time capsule");
//	}
//
//	private boolean createUser() {
//		scanner.nextLine();
//
//		System.out.print("Enter email: ");
//		String email = scanner.nextLine().trim().toLowerCase();
//
//		if (email.isEmpty()) {
//			System.out.println("Email cannot be empty.");
//			return true;
//		}
//
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//
//		if (password.isEmpty()) {
//			System.out.println("Password cannot be empty.");
//			return true;
//		}
//
//		User existing = userMap.get(email);
//
//		if (existing == null) {
//			currentUser = new User(email, password);
//			userMap.put(email, currentUser);
//			System.out.println("Account created & logged in as: " + currentUser.getEmail());
//		} else {
//			if (existing.getPassword().equals(password)) {
//				currentUser = existing;
//				System.out.println("Logged in as: " + currentUser.getEmail());
//			} else {
//				System.out.println("Incorrect password. Login denied.");
//				return false;
//				
//			}
//		}
//		return true;
//	}
//
//	private void createCapsule() {
//		scanner.nextLine();
//		if (!checkUser())
//			return;
//
//		System.out.print("Enter capsule title: ");
//		String title = scanner.nextLine().trim();
//
//		System.out.print("Enter unlock date and time (yyyy-MM-dd HH:mm): ");
//		String dateTimeStr = scanner.nextLine().trim();
//		try {
//			LocalDateTime unlockDate = LocalDateTime.parse(dateTimeStr,
//					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//			capsuleService.createCapsule(currentUser, title, unlockDate);
//			System.out.println("Capsule created.");
//		} catch (Exception e) {
//			System.out.println("Invalid date format.");
//		}
//	}
//
//	private void addMessageToCapsule() {
//		scanner.nextLine();
//		if (!checkUser())
//			return;
//
//		List<TimeCapsule> capsules = capsuleService.getCapsuleByUser(currentUser);
//		if (capsules.isEmpty()) {
//			System.out.println("No capsules found.");
//			return;
//		}
//
//		TimeCapsule capsule = selectCapsule(capsules);
//		if (capsule == null)
//			return;
//
//		System.out.print("Enter message type (text/image/video): ");
//		String type = scanner.nextLine().trim().toLowerCase();
//
//		System.out.print("Enter content: ");
//		String content = scanner.nextLine().trim();
//
//		System.out.print("Enter mood (HAPPY, SAD, EXCITED, REFLECTIVE, MOTIVATED): ");
//		Mood mood = Mood.valueOf(scanner.nextLine().trim().toUpperCase());
//
//		System.out.print("Enter tag: ");
//		String tag = scanner.nextLine().trim();
//
//		Message message = switch (type) {
//		case "text" -> new TextMessage(content, mood, tag);
//		case "image" -> {
//			System.out.print("Enter image path: ");
//			String imagePath = scanner.nextLine().trim();
//			yield new ImageMessage(content, mood, tag, imagePath);
//		}
//		default -> {
//			System.out.println("Invalid message type.");
//			yield null;
//		}
//		};
//
//		if (message != null) {
//			messageService.addMessage(capsule, message);
//			System.out.println("Message added.");
//		}
//	}
//
//	private void viewCapsules() {
//		if (!checkUser())
//			return;
//
//		List<TimeCapsule> capsules = capsuleService.getCapsuleByUser(currentUser);
//		if (capsules.isEmpty()) {
//			System.out.println("No capsules found.");
//			return;
//		}
//
//		System.out.println("Your Capsules:");
//		for (TimeCapsule capsule : capsules) {
//			System.out.println("- " + capsule.getTitle() + " (Unlocks: " + capsule.getUnlockDate() + ")");
//		}
//	}
//
//	private void unlockCapsules() {
//		if (!checkUser())
//			return;
//
//		List<TimeCapsule> unlocked = capsuleService.getUnlockedCapsules(currentUser);
//		if (unlocked.isEmpty()) {
//			System.out.println("No capsules are ready to unlock.");
//			System.out.println("Current time: " + LocalDateTime.now());
//			return;
//		}
//
//		for (TimeCapsule capsule : unlocked) {
//			capsuleService.unlockCapsule(capsule);
//		}
//	}
//
//	private void searchMessages() {
//		scanner.nextLine();
//		if (!checkUser())
//			return;
//
//		List<TimeCapsule> capsules = capsuleService.getCapsuleByUser(currentUser);
//		if (capsules.isEmpty()) {
//			System.out.println("No capsules found.");
//			return;
//		}
//
//		TimeCapsule capsule = selectCapsule(capsules);
//		if (capsule == null)
//			return;
//
//		if (!capsule.isUnlocked()) {
//			System.out.println("🔒 This capsule is still locked. Unlocks on: " + capsule.getUnlockDate());
//			return;
//		}
//
//		System.out.print("Search by (tag/mood): ");
//		String choice = scanner.nextLine().trim().toLowerCase();
//
//		List<Message> result = new ArrayList<>();
//		switch (choice) {
//		case "tag" -> {
//			System.out.print("Enter tag: ");
//			String tag = scanner.nextLine().trim();
//			result = messageService.getMessageByTag(capsule, tag);
//		}
//		case "mood" -> {
//			System.out.print("Enter mood (HAPPY, SAD, EXCITED, REFLECTIVE, MOTIVATED): ");
//			Mood mood = Mood.valueOf(scanner.nextLine().trim().toUpperCase());
//			result = messageService.getMessageByMood(capsule, mood);
//		}
//		default -> System.out.println("Invalid choice.");
//		}
//
//		if (!result.isEmpty()) {
//			messageService.displayMessages(result);
//		} else {
//			System.out.println("No messages found.");
//		}
//	}
//
//	private TimeCapsule selectCapsule(List<TimeCapsule> capsules) {
//		System.out.println("Select a capsule:");
//		for (int i = 0; i < capsules.size(); i++) {
//			System.out.println((i + 1) + ". " + capsules.get(i).getTitle());
//		}
//		System.out.print("Enter choice: ");
//		try {
//			int index = Integer.parseInt(scanner.nextLine()) - 1;
//			if (index >= 0 && index < capsules.size()) {
//				return capsules.get(index);
//			}
//		} catch (NumberFormatException ignored) {
//		}
//		System.out.println("Invalid selection.");
//		return null;
//	}
//
//	private boolean checkUser() {
//		if (currentUser == null) {
//			System.out.println("Please create or log in as a user first.");
//			return false;
//		}
//		return true;
//	}
//}

// With mysql update

package com.timecapsule.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.timecapsule.dao.MessageDAO;
import com.timecapsule.dao.TimeCapsuleDAO;
import com.timecapsule.dao.UserDAO;
import com.timecapsule.model.ImageMessage;
import com.timecapsule.model.Message;
import com.timecapsule.model.Mood;
import com.timecapsule.model.TextMessage;
import com.timecapsule.model.TimeCapsule;
import com.timecapsule.model.User;
import com.timecapsule.service.CapsuleServiceImp;
import com.timecapsule.service.MessageServiceImp;

public class UserInterface {

	private final Scanner scanner = new Scanner(System.in);
	private final CapsuleServiceImp capsuleService = new CapsuleServiceImp();
	private final MessageServiceImp messageService = new MessageServiceImp();

	private final UserDAO userDAO = new UserDAO();
	private final TimeCapsuleDAO capsuleDAO = new TimeCapsuleDAO();
	private final MessageDAO messageDAO = new MessageDAO();

	private final Map<String, User> userMap = new HashMap<>();
	private User currentUser;

	public void start() {
		System.out.println("Welcome to Digital Time Capsule");

		loadDataFromDatabase();

		boolean running = true;
		while (running) {
			System.out.println("\n ***** Main menu ******");
			System.out.print("1. Create/Login User\n" + "2. Create Time Capsule\n" + "3. Add Message to Capsule\n"
					+ "4. View Capsules\n" + "5. Unlock Capsules\n" + "6. Search Messages\n" + "7. Exit\n"
					+ "Please Enter your choice: ");

			String choice = scanner.next();

			switch (choice) {
			case "1" -> running = createUser();
			case "2" -> createCapsule();
			case "3" -> addMessageToCapsule();
			case "4" -> viewCapsules();
			case "5" -> unlockCapsules();
			case "6" -> searchMessages();
			case "7" -> running = false;
			default -> System.out.println("Invalid choice, please try again.");
			}
		}

		System.out.println("Thank you for using Digital Time Capsule");
	}

	private void loadDataFromDatabase() {
		List<User> users = userDAO.getAllUsers();
		for (User user : users) {
			userMap.put(user.getEmail(), user);
			List<TimeCapsule> capsules = capsuleDAO.getCapsulesByUser(user);
			capsuleService.getCapsuleMap().put(user, capsules);

			for (TimeCapsule capsule : capsules) {
				List<Message> messages = messageDAO.getMessagesByCapsule(user, capsule);
				capsule.setMessages(messages);
			}
		}
	}

	private boolean createUser() {
		scanner.nextLine();

		System.out.print("Enter email: ");
		String email = scanner.nextLine().trim().toLowerCase();

		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		User existing = userMap.get(email);

		if (existing == null) {
			currentUser = new User(email, password);
			userMap.put(email, currentUser);
			userDAO.saveUser(currentUser);
			System.out.println("Account created & logged in as: " + currentUser.getEmail());
		} else {
			if (existing.getPassword().equals(password)) {
				currentUser = existing;
				System.out.println("Logged in as: " + currentUser.getEmail());
			} else {
				System.out.println("Incorrect password. Login denied.");
				return false;
			}
		}
		return true;
	}

	private void createCapsule() {
		scanner.nextLine();
		if (!checkUser())
			return;

		System.out.print("Enter capsule title: ");
		String title = scanner.nextLine().trim();

		System.out.print("Enter unlock date and time (yyyy-MM-dd HH:mm): ");
		String dateTimeStr = scanner.nextLine().trim();
		try {
			LocalDateTime unlockDate = LocalDateTime.parse(dateTimeStr,
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			capsuleService.createCapsule(currentUser, title, unlockDate);
			TimeCapsule capsule = new TimeCapsule(title, unlockDate);
			capsuleDAO.saveCapsule(currentUser, capsule);
			System.out.println("Capsule created.");
		} catch (Exception e) {
			System.out.println("Invalid date format.");
		}
	}

	private void addMessageToCapsule() {
		scanner.nextLine();
		if (!checkUser())
			return;

		List<TimeCapsule> capsules = capsuleService.getCapsuleByUser(currentUser);
		if (capsules.isEmpty()) {
			System.out.println("No capsules found.");
			return;
		}

		TimeCapsule capsule = selectCapsule(capsules);
		if (capsule == null)
			return;

		System.out.print("Enter message type (text/image): ");
		String type = scanner.nextLine().trim().toLowerCase();

		System.out.print("Enter content: ");
		String content = scanner.nextLine().trim();

		System.out.print("Enter mood (HAPPY, SAD, EXCITED, REFLECTIVE, MOTIVATED): ");
		Mood mood = Mood.valueOf(scanner.nextLine().trim().toUpperCase());

		System.out.print("Enter tag: ");
		String tag = scanner.nextLine().trim();

		Message message = switch (type) {
		case "text" -> new TextMessage(content, mood, tag);
		case "image" -> {
			System.out.print("Enter image path: ");
			String imagePath = scanner.nextLine().trim();
			yield new ImageMessage(content, mood, tag, imagePath);
		}
		default -> {
			System.out.println("Invalid message type.");
			yield null;
		}
		};

		if (message != null) {
			messageService.addMessage(capsule, message);
			messageDAO.saveMessage(currentUser, capsule, message);
			System.out.println("Message added.");
		}
	}

	private void viewCapsules() {
		if (!checkUser())
			return;

		List<TimeCapsule> capsules = capsuleService.getCapsuleByUser(currentUser);
		if (capsules.isEmpty()) {
			System.out.println("No capsules found.");
			return;
		}

		System.out.println("Your Capsules:");
		for (TimeCapsule capsule : capsules) {
			System.out.println("- " + capsule.getTitle() + " (Unlocks: " + capsule.getUnlockDate() + ")");
		}
	}

	private void unlockCapsules() {
		if (!checkUser())
			return;

		List<TimeCapsule> unlocked = capsuleService.getUnlockedCapsules(currentUser);
		if (unlocked.isEmpty()) {
			System.out.println("No capsules are ready to unlock.");
			System.out.println("Current time: " + LocalDateTime.now());
			return;
		}

		for (TimeCapsule capsule : unlocked) {
			capsuleService.unlockCapsule(capsule);
		}
	}

	private void searchMessages() {
		scanner.nextLine();
		if (!checkUser())
			return;

		List<TimeCapsule> capsules = capsuleService.getCapsuleByUser(currentUser);
		if (capsules.isEmpty()) {
			System.out.println("No capsules found.");
			return;
		}

		TimeCapsule capsule = selectCapsule(capsules);
		if (capsule == null)
			return;

		if (!capsule.isUnlocked()) {
			System.out.println("🔒 This capsule is still locked. Unlocks on: " + capsule.getUnlockDate());
			return;
		}

		System.out.print("Search by (tag/mood): ");
		String choice = scanner.nextLine().trim().toLowerCase();

		List<Message> result = new ArrayList<>();
		switch (choice) {
		case "tag" -> {
			System.out.print("Enter tag: ");
			String tag = scanner.nextLine().trim();
			result = messageService.getMessageByTag(capsule, tag);
		}
		case "mood" -> {
			System.out.print("Enter mood (HAPPY, SAD, EXCITED, REFLECTIVE, MOTIVATED): ");
			Mood mood = Mood.valueOf(scanner.nextLine().trim().toUpperCase());
			result = messageService.getMessageByMood(capsule, mood);
		}
		default -> System.out.println("Invalid choice.");
		}

		if (!result.isEmpty()) {
			messageService.displayMessages(result);
		} else {
			System.out.println("No messages found.");
		}
	}

	private TimeCapsule selectCapsule(List<TimeCapsule> capsules) {
		System.out.println("Select a capsule:");
		for (int i = 0; i < capsules.size(); i++) {
			System.out.println((i + 1) + ". " + capsules.get(i).getTitle());
		}
		System.out.print("Enter choice: ");
		try {
			int index = Integer.parseInt(scanner.nextLine()) - 1;
			if (index >= 0 && index < capsules.size()) {
				return capsules.get(index);
			}
		} catch (NumberFormatException ignored) {
		}
		System.out.println("Invalid selection.");
		return null;
	}

	private boolean checkUser() {
		if (currentUser == null) {
			System.out.println("Please create or log in as a user first.");
			return false;
		}
		return true;
	}
}
