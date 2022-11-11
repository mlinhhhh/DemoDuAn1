package Repositories;

import Models.ChiTietSP;
import Models.DongSP;
import Models.SanPham;
import java.util.List;

public interface IChiTietSanPhamRepositories {
    public int CreateSpct(ChiTietSP spct);
   public List<ChiTietSP> ReadSPct();
   public int UpdateSPct(ChiTietSP chiTietSP,String id);
   public int DeleteSpct(String id);
    List<ChiTietSP> findByName(String tenSp);
    ChiTietSP getObjByMa(String maSp);
     ChiTietSP getObjById(String id);
}
