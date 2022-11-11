package Repositories;

import Models.DongSP;
import Models.SanPham;
import java.util.List;

public interface IDONGSP {
 
    List<DongSP> getAll();

    Integer add(DongSP dongSP);

    Integer update(String ten,String ma);

    Integer delete(String id);

    List<DongSP> findByName(String tenDongSp);
    
    DongSP getObjByMa(String ma);
     DongSP getObjById(String id);
}
