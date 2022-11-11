package Repositories;

import java.util.List;
import Models.DongSP;
import Models.MauSac;
import Models.SanPham;
import Utilities.JDBC_Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DONGSPRespo implements IDONGSP{

    @Override
    public List<DongSP> getAll() {
        List<DongSP> list = new ArrayList<>();
        String sql = "SELECT * FROM DONGSP";
        ResultSet rs = JDBC_Helper.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(new DongSP(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Integer add(DongSP dongSP) {
        String sql = "INSERT INTO DONGSP VALUES (NEWID(),?,?)";
        Integer i = JDBC_Helper.executeUpdate(sql, dongSP.getMa(), dongSP.getTen());
        return i;
    }

    @Override
    public Integer update( String ten,String ma) {
        String sql = "UPDATE DONGSP SET TEN = ? WHERE Ma=?";
        Integer i = JDBC_Helper.executeUpdate(sql, ten,ma);
        return i;
    }

    @Override
    public Integer delete(String id) {
        String sql = "DELETE FROM CHITIETSP WHERE IDDONGSP = ? DELETE FROM DONGSP WHERE ID = ?";
        Integer i = JDBC_Helper.executeUpdate(sql, id, id);
        return i;
    }

    @Override
    public List<DongSP> findByName(String tenDongSp) {
        List<DongSP> list = new ArrayList<>();
        String sql = "SELECT * FROM DONGSP WHERE TEN LIKE ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, "%" + tenDongSp + "%");
        try {
            while (rs.next()) {
                list.add(new DongSP(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public DongSP getObjByMa(String ma) {
        DongSP dsp = null;
        String sql = "SELECT * FROM DONGSP WHERE MA = ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, ma);
        try {
            if (rs.next()) {
                dsp = new DongSP(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dsp;
    }
     public DongSP getObjById(String id) {
        DongSP dsp = null;
        String sql = "select * from DongSP where id =?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                dsp = new DongSP(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dsp;
    }


}
