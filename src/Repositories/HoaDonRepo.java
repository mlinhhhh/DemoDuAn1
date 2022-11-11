package Repositories;

import Models.HoaDon;
import Models.KhachHang;
import Models.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utilities.JDBC_Helper;

public class HoaDonRepo implements IHoaDonRepo {
    @Override
    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HOADON";
        ResultSet rs = JDBC_Helper.executeQuery(sql);
        try {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMa(rs.getString(4));
                hd.setNgayTao(rs.getString(5));
                hd.setTinhTrang(rs.getInt(9));
                list.add(hd);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer add() {
        String sql = "exec hoadon_tusinhma\n" +
        "select * from HoaDon";
        Integer i = JDBC_Helper.executeUpdate(sql);
        return i;
    }

    @Override
    public Integer update(HoaDon hd, String ma) {
        String sql = "UPDATE HoaDon SET IdKH = ?, IdNV = ?, NgayTao = ?, NgayThanhToan = ?, NgayShip = ?, NgayNhan = ?, TinhTrang = ?, TenNguoiNhan = ?, DiaChi = ?, Sdt = ? WHERE ma = ?";
        Integer i = JDBC_Helper.executeUpdate(sql, hd.getKhachHang().getId(), hd.getNhanVien().getId(), hd.getNgayTao(), hd.getNgayThanhToan(), hd.getNgayShip(), hd.getNgayNhan(), hd.getTinhTrang(), hd.getTenNguoiNhan(), hd.getDiaChi(), hd.getSdt(), ma);
        return i;
    }

    @Override
    public Integer delete(String ma) {
        String sql = "DELETE FROM HOADON WHERE ma = ?";
        Integer i = JDBC_Helper.executeUpdate(sql, ma);
        return i;
    }

    @Override
    public List<HoaDon> findByMaHD(String maHD) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HOADON A JOIN NHANVIEN B ON A.IDNV = B.ID JOIN KHACHHANG C ON A.IDKH = C.ID WHERE A.MA LIKE ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, "%" + maHD + "%");
        try {
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getDate(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getInt(26));
                KhachHang kh = new KhachHang(rs.getString(27), rs.getString(28), rs.getString(29), rs.getString(30), rs.getString(31), rs.getDate(32), rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36), rs.getString(37));
                //list.add(new HoaDon(rs.getString(1), kh, nv, rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getDate(7), rs.getDate(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public HoaDon getObjByMa(String ma) {
        HoaDon hd = null;
        String sql = "SELECT * FROM HOADON A JOIN NHANVIEN B ON A.IDNV = B.ID JOIN KHACHHANG C ON A.IDKH = C.ID WHERE A.MA = ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql,ma);
        try {
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getDate(19), rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getInt(26));
                KhachHang kh = new KhachHang(rs.getString(27), rs.getString(28), rs.getString(29), rs.getString(30), rs.getString(31), rs.getDate(32), rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36), rs.getString(37));
               // hd = new HoaDon(rs.getString(1), kh, nv, rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getDate(7), rs.getDate(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return hd;
    }
     
}
