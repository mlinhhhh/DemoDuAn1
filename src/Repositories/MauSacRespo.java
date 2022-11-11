package Repositories;

import Models.DongSP;
import Models.MauSac;
import Utilities.JDBC_Helper;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MauSacRespo implements IMauSacRespo{
    @Override
    public List<MauSac> getAll() {
        List<MauSac> list = new ArrayList<>();
        String sql = "SELECT * FROM MAUSAC";
        ResultSet rs = JDBC_Helper.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(new MauSac(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Integer add(MauSac ms) {
        String sql = "INSERT INTO MAUSAC VALUES (NEWID(),?,?)";
        Integer i = JDBC_Helper.executeUpdate(sql, ms.getMa(), ms.getTen());
        return i;
    }

    @Override
    public Integer update(String ten,String ma) {
        String sql = "UPDATE MAUSAC SET Ten = ? WHERE Ma = ?";
        Integer i = JDBC_Helper.executeUpdate(sql,ten,ma);
        return i;
    }

    @Override
    public Integer delete(String id) {
        String sql = "DELETE FROM CHITIETSP WHERE IDMAUSAC = ? DELETE FROM MAUSAC WHERE ID = ?";
        Integer i = JDBC_Helper.executeUpdate(sql, id);
        return i;
    }

    @Override
    public List<MauSac> findByName(String tenMs) {
        List<MauSac> list = new ArrayList<>();
        String sql = "SELECT * FROM MAUSAC WHERE TEN LIKE ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, "%" + tenMs + "%");
        try {
            while (rs.next()) {
                list.add(new MauSac(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public MauSac getObjByMa(String ma) {
        MauSac ms = null;
        String sql = "SELECT * FROM MAUSAC WHERE MA = ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, ma);
        try {
            while (rs.next()) {
                ms = new MauSac(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ms;
    }
     public MauSac getObjById(String id) {
        MauSac ms = null;
        String sql = "select * from MauSac where Id =?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                ms = new MauSac(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ms;
    }

}
