package Service;
import Repositories.NhanVienRepo;
import Repositories.INhanVienRepo;
import Models.NhanVien;
import java.util.List;

public class NhanVienService implements INhanVienService{
     INhanVienRepo repo;

    public NhanVienService() {
        repo = new NhanVienRepo();
    }

    @Override
    public List<NhanVien> getAll() {
        return repo.getAll();
    }

    @Override
    public Integer add(NhanVien nv) {
        return repo.add(nv);
    }

    @Override
    public Integer delete(String id) {
        return repo.delete(id);
    }

    @Override
    public Integer update(NhanVien nv, String ma) {
        return repo.update( nv,ma);
    }

    @Override
    public List<NhanVien> findByName(String tennv) {
        return repo.findByName(tennv);
    }
    
    @Override
    public NhanVien getObjByMa(String ma) {
        return repo.getObjByMa(ma);
    }
     @Override
    public NhanVien getObjById(String id) {
       return repo.getObjById(id);
    }
   
}
