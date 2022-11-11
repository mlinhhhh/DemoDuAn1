package Service;

import Models.ChiTietSP;
import Models.MauSac;
import Models.SanPham;
import Repositories.ISanPhamRepositories;
import Repositories.SanPhamRepositories;
import java.util.List;

public class SanPhamService implements ISanPhamService{
     ISanPhamRepositories repo;

    public SanPhamService() {
        repo = new SanPhamRepositories();
    }

    @Override
    public List<SanPham> getAll() {
        return repo.getAll();
    }

    @Override
    public Integer add(SanPham ctsp) {
        return repo.add(ctsp);
    }

    @Override
    public Integer delete(String id) {
        return repo.delete(id);
    }

    @Override
    public Integer update(String ten,String ma) {
        return repo.update(ten,ma);
    }

    @Override
    public List<SanPham> findByName(String tenSp) {
        return repo.findByName(tenSp);
    }
    
    @Override
    public SanPham getObjByMa(String ma) {
        return repo.getObjByMa(ma);
    }
     @Override
    public SanPham getObjById(String id) {
       return repo.getObjById(id);
    }
   

}
