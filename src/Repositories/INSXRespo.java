package Repositories;
import Models.NSX;
import Models.SanPham;
import java.util.List;
public interface INSXRespo {
    
    List<NSX> getAll();

    Integer add(NSX nsx);

    Integer update(String ten,String ma);

    Integer delete(String id);

    List<NSX> findByName(String tenNSX);
    
    NSX getObjByMa(String ma);
     NSX getObjById(String id);
}
