package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBC_Helper {

    public static ResultSet executeQuery(String sql, Object... args) {
        ResultSet rs = null;
        try {
            Connection con = JDBC_Connect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static Integer executeUpdate(String sql, Object... args) {
        Integer row = 0;
        try {
            Connection con = JDBC_Connect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            row = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }
}
