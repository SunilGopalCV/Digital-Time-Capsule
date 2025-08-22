package com.timecapsule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.timecapsule.model.ImageMessage;
import com.timecapsule.model.Message;
import com.timecapsule.model.Mood;
import com.timecapsule.model.TextMessage;
import com.timecapsule.model.TimeCapsule;
import com.timecapsule.model.User;
import com.timecapsule.util.DBUtil;

public class MessageDAO {
	public void saveMessage(User user, TimeCapsule capsule, Message message) {
		String sql = "INSERT INTO messages (capsule_id, type, content, mood, tag, image_path, created_at) "
				+ "VALUES ((SELECT c.id FROM capsules c JOIN users u ON c.user_id = u.id WHERE c.title = ? AND u.email = ?), ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, capsule.getTitle());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, message instanceof TextMessage ? "TEXT" : "IMAGE");
			stmt.setString(4, message.getContent());
			stmt.setString(5, message.getMood().name());
			stmt.setString(6, message.getTag());
			stmt.setString(7, message instanceof ImageMessage ? ((ImageMessage) message).getImagePath() : null);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Message> getMessagesByCapsule(User user, TimeCapsule capsule) {
		List<Message> messages = new ArrayList<>();
		String sql = "SELECT type, content, mood, tag, image_path FROM messages "
				+ "WHERE capsule_id = (SELECT c.id FROM capsules c JOIN users u ON c.user_id = u.id WHERE c.title = ? AND u.email = ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, capsule.getTitle());
			stmt.setString(2, user.getEmail());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String type = rs.getString("type");
				String content = rs.getString("content");
				Mood mood = Mood.valueOf(rs.getString("mood"));
				String tag = rs.getString("tag");

				if ("TEXT".equalsIgnoreCase(type)) {
					messages.add(new TextMessage(content, mood, tag));
				} else if ("IMAGE".equalsIgnoreCase(type)) {
					String imagePath = rs.getString("image_path");
					messages.add(new ImageMessage(content, mood, tag, imagePath));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

}
