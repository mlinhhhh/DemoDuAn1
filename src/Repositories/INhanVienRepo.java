package Repositories;

import Models.NhanVien;
import java.util.List;

public interface INhanVienRepo {
      List<NhanVien> getAll();

    Integer add(NhanVien sp);

    Integer update(NhanVien nv, String ma);

    Integer delete(String id);

    List<NhanVien> findByName(String tenSp);

    NhanVien getObjByMa(String ma);

    NhanVien getObjById(String id);
}
