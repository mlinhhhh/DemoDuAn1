package Service;

import Models.SanPham;
import java.util.List;

public interface ISanPhamService {

    List<SanPham> getAll();

    Integer add(SanPham sp);

    Integer update(String ten,String ma);

    Integer delete(String id);

    List<SanPham> findByName(String tenSp);

    SanPham getObjByMa(String ma);

    SanPham getObjById(String id);
}
