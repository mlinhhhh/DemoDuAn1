package Service;

import Models.MauSac;
import Models.SanPham;
import java.util.List;

public interface IMauSacService {

    List<MauSac> getAll();

    Integer add(MauSac ms);

    Integer update(String ten,String ma);

    Integer delete(String id);

    List<MauSac> findByName(String tenMs);

    MauSac getObjByMa(String ma);

    MauSac getObjById(String id);
}
