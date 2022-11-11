/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.SanPham;
import Utilities.JDBC_Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class SanPhamRepositories implements ISanPhamRepositories {

    @Override
    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SANPHAM";
        ResultSet rs = JDBC_Helper.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(new SanPham(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Integer add(SanPham sp) {
        String sql = "INSERT INTO SANPHAM VALUES (NEWID(),?,?)";
        Integer i = JDBC_Helper.executeUpdate(sql, sp.getMa(), sp.getTen());
        return i;
    }

    @Override
    public Integer update(String ten,String ma) {
        String sql = "UPDATE SANPHAM SET TEN = ? WHERE Ma = ?";
        Integer i = JDBC_Helper.executeUpdate(sql,ten,ma);
        return i;
    }

    @Override
    public Integer delete(String id) {
        String sql = " delete from ChiTietSP where IdSP = ?";
         String sql1 = "delete from SanPham where Ma = ?";
        Integer i = JDBC_Helper.executeUpdate(sql,id);
         i = JDBC_Helper.executeUpdate(sql1,id);
        return i;
    }

    @Override
    public List<SanPham> findByName(String tenSp) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SANPHAM WHERE TEN LIKE ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, "%" + tenSp + "%");
        try {
            while (rs.next()) {
                list.add(new SanPham(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public SanPham getObjByMa(String ma) {
        SanPham sp = null;
        String sql = "SELECT * FROM SANPHAM WHERE MA = ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, ma);
        try {
            while (rs.next()) {
                sp = new SanPham(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sp;
    }

    public SanPham getObjById(String id) {
        SanPham sp = null;
        String sql = "select * from SanPham where id =?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                sp = new SanPham(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sp;
    }
}
