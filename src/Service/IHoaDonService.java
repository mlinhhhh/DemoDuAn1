package Service;

import Models.HoaDon;
import java.util.List;

public interface IHoaDonService {
     List<HoaDon> getAll();

    Integer add();

    Integer update(HoaDon hd, String ma);

    Integer delete(String ma);

    List<HoaDon> findByMaHD(String maHD);
    
    HoaDon getObjByMa(String ma);
     HoaDon getObjById(String id);
}
