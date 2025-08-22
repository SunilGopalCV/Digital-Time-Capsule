package com.timecapsule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.timecapsule.model.TimeCapsule;
import com.timecapsule.model.User;
import com.timecapsule.util.DBUtil;

public class TimeCapsuleDAO {
	public void saveCapsule(User user, TimeCapsule capsule) {
		String sql = "INSERT INTO capsules (user_id, title, unlock_datetime) "
				+ "VALUES ((SELECT id FROM users WHERE email = ?), ?, ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getEmail());
			stmt.setString(2, capsule.getTitle());
			stmt.setTimestamp(3, Timestamp.valueOf(capsule.getUnlockDate()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<TimeCapsule> getCapsulesByUser(User user) {
		List<TimeCapsule> capsules = new ArrayList<>();
		String sql = "SELECT title, unlock_datetime FROM capsules WHERE user_id = (SELECT id FROM users WHERE email = ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getEmail());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				capsules.add(
						new TimeCapsule(rs.getString("title"), rs.getTimestamp("unlock_datetime").toLocalDateTime()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return capsules;
	}
}
