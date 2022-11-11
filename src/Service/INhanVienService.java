package Service;
import Models.NhanVien;
import java.util.List;

public interface INhanVienService {
     List<NhanVien> getAll();

    Integer add(NhanVien nv);

    Integer update(NhanVien nv,String ma);

    Integer delete(String id);

    List<NhanVien> findByName(String tennv);

    NhanVien getObjByMa(String ma);

    NhanVien getObjById(String id);
}
