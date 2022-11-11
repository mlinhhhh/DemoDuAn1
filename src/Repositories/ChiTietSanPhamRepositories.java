      package Repositories;

import Models.SanPham;
import java.util.List;
import Models.ChiTietSP;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.MauSac;
import Models.DongSP;
import Models.NSX;
import Utilities.JDBC_Helper;

public class ChiTietSanPhamRepositories implements IChiTietSanPhamRepositories {

    @Override
    public int CreateSpct(ChiTietSP spct) {

        int rowaffected = 0;
        String query = "insert into ChiTietSP (IdSP,IdNsx,IdMauSac,IdDongSP,NamBH,MoTa,SoLuongTon,GiaNhap,GiaBan)\n"
                + "values (?,?,?,?,?,?,?,?,?)";
        rowaffected = JDBC_Helper.executeUpdate(query, spct.getSanPham().getId(), spct.getNsx().getId(), spct.getMauSac().getId(),
                spct.getDongSP().getId(), spct.getNamBH(), spct.getMoTa(), spct.getSoLuongTon(), spct.getGiaNhap(), spct.getGiaBan());
        return rowaffected;
    }

    @Override
    public List<ChiTietSP> ReadSPct() {
        try {
            List<ChiTietSP> listSPCT = new ArrayList<>();
            String query = "SELECT CT.Id,SP.Id, MS.Id  , DSP.Id  , N.Id  ,CT.NamBH , CT.MoTa,  CT.SoLuongTon ,CT.GiaNhap , CT.GiaBan\n"
                    + "				FROM ChiTietSP CT \n"
                    + "				INNER JOIN SanPham SP ON CT.IdSP = SP.Id \n"
                    + "				INNER JOIN DongSP DSP ON CT.IdDongSP = DSP.Id \n"
                    + "				INNER JOIN MauSac MS ON CT.IdMauSac = MS.Id \n"
                    + "				INNER JOIN NSX N ON CT.IdNsx = N.Id ";
            ResultSet rs = JDBC_Helper.executeQuery(query);
            while (rs.next()) {
                ChiTietSP spct = new ChiTietSP();
                spct.setId(rs.getString(1));
                spct.setSanPham(new SanPhamRepositories().getObjById(rs.getString(2)));
                spct.setMauSac(new MauSacRespo().getObjById(rs.getString(3)));
                spct.setNsx(new NSXRespo().getObjById(rs.getString(5)));
                spct.setDongSP(new DONGSPRespo().getObjById(rs.getString(4)));
                spct.setNamBH(rs.getInt(6));
                spct.setMoTa(rs.getString(7));
                spct.setSoLuongTon(rs.getInt(8));
                spct.setGiaNhap(rs.getDouble(9));
                spct.setGiaBan(rs.getDouble(10));
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamRepositories.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int UpdateSPct(ChiTietSP chiTietSP, String id) {
        int rowaffected = 0;
        String query = "UPDATE ChiTietSP SET IdSp=?,IdNsx=?,IdMauSac=?,IdDongSp=?,NamBH=?,Mota=?,"
                + "SoLuongTon=?,GiaNhap=?,GiaBan=? WHERE Id=?";
        rowaffected = JDBC_Helper.executeUpdate(query, chiTietSP.getSanPham().getId(),
                chiTietSP.getNsx().getId(), chiTietSP.getMauSac().getId(),
                chiTietSP.getDongSP().getId(), chiTietSP.getNamBH(),
                chiTietSP.getMoTa(), chiTietSP.getSoLuongTon(), 
                chiTietSP.getGiaNhap(), chiTietSP.getGiaBan(),id);
        return rowaffected;
    }

    @Override
    public int DeleteSpct(String id) {
        int rowaffected = 0;
        String query = "DELETE FROM ChiTietSP WHERE Id=?";
        rowaffected = JDBC_Helper.executeUpdate(query, id);
        return rowaffected;
    }

    @Override
    public List<ChiTietSP> findByName(String tenSp) {
        List<ChiTietSP> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM CHITIETSP A JOIN SANPHAM B ON A.IDSP = B.ID JOIN NSX C ON A.IDNSX = C.ID JOIN MAUSAC D ON A.IDMAUSAC = D.ID JOIN DONGSP E ON A.IDDONGSP = E.ID WHERE B.TEN = ?";
            ResultSet rs = JDBC_Helper.executeQuery(sql, "%" + tenSp + "%");
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setId(rs.getString(1));
                sp.setTen(rs.getString(2));
                NSX nsx = new NSX();
                nsx.setId(rs.getString(3));
                MauSac ms = new MauSac();
                ms.setId(rs.getString(4));
                DongSP dsp = new DongSP();
                dsp.setId(rs.getString(5));
                ChiTietSP spct = new ChiTietSP();
                spct.setNamBH(rs.getInt(6));
                spct.setMoTa(rs.getString(7));
                spct.setSoLuongTon(rs.getInt(8));
                spct.setGiaNhap(rs.getDouble(9));
                spct.setGiaBan(rs.getDouble(10));
                list.add(spct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ChiTietSP getObjByMa(String maSp) {
        ChiTietSP ctsp = null;
        try {
            String sql = "SELECT * FROM CHITIETSP A JOIN SANPHAM B ON A.IDSP = B.ID JOIN NSX C ON A.IDNSX = C.ID "
                    + "JOIN MAUSAC D ON A.IDMAUSAC = D.ID JOIN DONGSP E ON A.IDDONGSP = E.ID WHERE B.MA = ?";
            ResultSet rs = JDBC_Helper.executeQuery(sql, maSp);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setId(rs.getString(1));
                sp.setTen(rs.getString(2));
                NSX nsx = new NSX();
                nsx.setId(rs.getString(3));
                MauSac ms = new MauSac();
                ms.setId(rs.getString(4));
                DongSP dsp = new DongSP();
                dsp.setId(rs.getString(5));
                ChiTietSP spct = new ChiTietSP();
                spct.setNamBH(rs.getInt(6));
                spct.setMoTa(rs.getString(7));
                spct.setSoLuongTon(rs.getInt(8));
                spct.setGiaNhap(rs.getDouble(9));
                spct.setGiaBan(rs.getDouble(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctsp;
    }
     public ChiTietSP getObjById(String id) {
        ChiTietSP ctsp = null;
        String sql = "SELECT * FROM CHITIETSP A JOIN SANPHAM B ON A.IDSP = B.ID JOIN NSX C ON A.IDNSX = C.ID "
                    + "JOIN MAUSAC D ON A.IDMAUSAC = D.ID JOIN DONGSP E ON A.IDDONGSP = E.ID WHERE B.Id = ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, id);
        try {
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setId(rs.getString(1));
                sp.setTen(rs.getString(2));
                NSX nsx = new NSX();
                nsx.setId(rs.getString(3));
                MauSac ms = new MauSac();
                ms.setId(rs.getString(4));
                DongSP dsp = new DongSP();
                dsp.setId(rs.getString(5));
                ChiTietSP spct = new ChiTietSP();
                spct.setNamBH(rs.getInt(6));
                spct.setMoTa(rs.getString(7));
                spct.setSoLuongTon(rs.getInt(8));
                spct.setGiaNhap(rs.getDouble(9));
                spct.setGiaBan(rs.getDouble(10));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ctsp;
    }

}
