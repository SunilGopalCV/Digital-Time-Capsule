package com.timecapsule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.timecapsule.model.User;
import com.timecapsule.util.DBUtil;

public class UserDAO {
	public void saveUser(User user) {
		String sql = "INSERT INTO users (email, password) VALUES (?, ?) ON DUPLICATE KEY UPDATE password = ?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getPassword());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT email, password FROM users";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				users.add(new User(rs.getString("email"), rs.getString("password")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

}
