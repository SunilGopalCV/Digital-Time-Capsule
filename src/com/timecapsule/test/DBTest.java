package com.timecapsule.test;

import com.timecapsule.util.DBUtil;
import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null) {
                System.out.println("Connection test passed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
