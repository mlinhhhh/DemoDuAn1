package Repositories;

import Models.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utilities.JDBC_Helper;

public class NhanVienRepo implements INhanVienRepo{
     @Override
    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN";
        ResultSet rs = JDBC_Helper.executeQuery(sql);
        try {
            while (rs.next()) {
                list.add(new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Integer add(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (Id, Ma, Ten, TenDem, Ho, GioiTinh, NgaySinh, DiaChi, Sdt, MatKhau, IdCH, IdCV, IdGuiBC, TrangThai) VALUES (NEWID(),?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Integer i = JDBC_Helper.executeUpdate(sql, nv.getMa(), nv.getTen(), nv.getTenDem(), nv.getHo(), nv.getGioiTinh(), nv.getNgaySinh(), nv.getDiaChi(), nv.getSdt(),nv.getMatKhau(), nv.getIdCH(), nv.getIdCV(), nv.getIdGuiBC(), nv.getTrangThai());
        return i;
    }

    @Override
    public Integer update(NhanVien nv, String ma) {
        String sql = "UPDATE NhanVien SET  Ten = ?, TenDem = ?, Ho = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, Sdt = ?, MatKhau = ?, IdCH = ?, IdCV = ?, IdGuiBC = ?, TrangThai = ? WHERE ma = ?";
        Integer i = JDBC_Helper.executeUpdate(sql, nv.getTen(), nv.getTenDem(), nv.getHo(), nv.getGioiTinh(), nv.getNgaySinh(), nv.getDiaChi(),nv.getSdt(), nv.getMatKhau(), nv.getIdCH(), nv.getIdCV(), nv.getIdGuiBC(), nv.getTrangThai(), ma);
        return i;
    }

    @Override
    public Integer delete(String ma) {
        String sql = "DELETE FROM NHANVIEN WHERE ma = ?";
        Integer i = JDBC_Helper.executeUpdate(sql, ma);
        return i;
    }

    @Override
    public List<NhanVien> findByName(String tenNV) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN WHERE TEN LIKE ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, "%" + tenNV + "%");
        try {
            while (rs.next()) {
                list.add(new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public NhanVien getObjByMa(String ma) {
        NhanVien nv = null;
        String sql = "SELECT * FROM NHANVIEN WHERE MA = ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, ma);
        try {
            while (rs.next()) {
                nv = new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nv;
    }
    public NhanVien getObjById(String id) {
        NhanVien  nv = null;
        String sql = "select * from NhanVien where Id =?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                nv= new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nv;
    }

   
}
