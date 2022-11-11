package Repositories;


import Models.NSX;
import Utilities.JDBC_Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NSXRespo implements INSXRespo{

    @Override
    public List<NSX> getAll() {
        List<NSX> list = new ArrayList<>();
        String sql = "SELECT * FROM NSX";
        ResultSet rs = JDBC_Helper.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(new NSX(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Integer add(NSX nsx) {
        String sql = "INSERT INTO NSX VALUES (NEWID(),?,?)";
        Integer i = JDBC_Helper.executeUpdate(sql, nsx.getMa(), nsx.getTen());
        return i;
    }

    @Override
    public Integer update(String ten,String ma) {
        String sql = "UPDATE NSX SET Ten = ? WHERE Ma = ?";
        Integer i = JDBC_Helper.executeUpdate(sql,ten,ma);
        return i;
    }

    @Override
    public Integer delete(String id) {
        String sql = "DELETE FROM CHITIETSP WHERE IDNSX = ? DELETE FROM NSX WHERE ID = ?";
        Integer i = JDBC_Helper.executeUpdate(sql, id,id);
        return i;
    }

    @Override
    public List<NSX> findByName(String tenNSX) {
        List<NSX> list = new ArrayList<>();
        String sql = "SELECT * FROM NSX WHERE TEN LIKE ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, "%" + tenNSX + "%");
        try {
            while (rs.next()) {
                list.add(new NSX(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public NSX getObjByMa(String ma) {
        NSX nsx = null;
        String sql = "SELECT * FROM NSX WHERE MA = ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, ma);
        try {
            while (rs.next()) {
                nsx = new NSX(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsx;
    }
     public NSX getObjById(String id) {
        NSX nsx = null;
        String sql = "select * from NSX where id =?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                nsx = new NSX(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nsx;
    }
}
