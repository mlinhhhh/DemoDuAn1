package Repositories;

import Models.HoaDonChiTiet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utilities.JDBC_Helper;
import Models.HoaDonChiTiet;
import Models.HoaDon;
import Models.ChiTietSP;

public class HoaDonCTRepoViewModel implements IHoaDonCTRepoViewModel {

    @Override
    public int saveHoaDon(HoaDonChiTiet hoaDonChiTiet) {
        int rowaffected = 0;
        String query = "insert into HoaDonChiTiet(IdHoaDon,IdChiTietSP,SoLuong,DonGia) values (?,?,?,?)";
        rowaffected = JDBC_Helper.executeUpdate(query,
                hoaDonChiTiet.getIdHoaDon(),
                hoaDonChiTiet.getIdSanPham(),
                hoaDonChiTiet.getSoLuong(),
                hoaDonChiTiet.getDonGia());
        return rowaffected;
    }

  

}
