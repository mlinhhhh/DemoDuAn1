/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.ChiTietSP;
import Models.SanPham;
import Service.SanPhamViewModelService;
import Utilities.JDBC_Connect;
import Utilities.JDBC_Helper;
import ViewModels.SanPhamViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguyenvv
 */
public class SanPhamViewModelRepository implements iSanPhamViewModelRepository{

    private JDBC_Connect dBConnection;

    public List<SanPhamViewModel> getAll() {
        List<SanPhamViewModel> list = new ArrayList<>();
        String sql = "select SanPham.Ten, ChiTietSP.SoLuongTon,ChiTietSP.NamBH, ChiTietSP.GiaNhap,ChiTietSP.GiaBan, ChiTietSP.MoTa "
                + "from SanPham join ChiTietSP on SanPham.Id = ChiTietSP.IdSP";
        try (Connection con = dBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamViewModel sanPhamViewModel = new SanPhamViewModel();
                sanPhamViewModel.setTenSanPham(rs.getString(1));
                sanPhamViewModel.setSoLuong(rs.getInt(2));
                sanPhamViewModel.setNambh(rs.getInt(3));
                sanPhamViewModel.setGiaNhap(rs.getDouble(4));
                sanPhamViewModel.setGiaBan(rs.getDouble(5));
                sanPhamViewModel.setMota(rs.getString(6));

                list.add(sanPhamViewModel);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return list;
    }
    @Override
    public List<SanPhamViewModel> findByName(String tenSp) {
        List<SanPhamViewModel> list = new ArrayList<>();
        String sql = "select SanPham.Ten, ChiTietSP.SoLuongTon, ChiTietSP.GiaNhap,ChiTietSP.GiaBan, ChiTietSP.MoTa "
                + "from SanPham join ChiTietSP on SanPham.Id = ChiTietSP.IdSP where Ten = ?";
        ResultSet rs = JDBC_Helper.executeQuery(sql, "%" + tenSp + "%");
        try {
            while (rs.next()) {
             SanPhamViewModel sanPhamViewModel = new SanPhamViewModel();
                sanPhamViewModel.setTenSanPham(rs.getString(1));
                sanPhamViewModel.setSoLuong(rs.getInt(2));
                sanPhamViewModel.setGiaNhap(rs.getDouble(3));
                sanPhamViewModel.setGiaBan(rs.getDouble(4));
                sanPhamViewModel.setMota(rs.getString(5));

                list.add(sanPhamViewModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

   
  
}
