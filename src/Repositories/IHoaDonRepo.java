package Repositories;

import Models.HoaDon;
import java.util.List;

public interface IHoaDonRepo {
      List<HoaDon> getAll();

    Integer add();

    Integer update(HoaDon hd, String idHoaDon);

    Integer delete(String idHoaDon);

    List<HoaDon> findByMaHD(String maHD);
    
    HoaDon getObjByMa(String ma);
}
