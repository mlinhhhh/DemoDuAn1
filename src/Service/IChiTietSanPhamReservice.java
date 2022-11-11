package Service;

import Models.ChiTietSP;
import Models.NSX;
import java.util.List;

public interface IChiTietSanPhamReservice {

    List<ChiTietSP> getAll();

    Integer add(ChiTietSP ctsp);

    Integer update(ChiTietSP ctsp, String id);

    Integer delete(String id);

    List<ChiTietSP> findByName(String tenSp);

    ChiTietSP getObjByMa(String maSp);

    ChiTietSP getObjById(String id);

}
