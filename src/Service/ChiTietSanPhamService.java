package Service;

import Models.ChiTietSP;
import Models.DongSP;
import Repositories.ChiTietSanPhamRepositories;
import java.util.List;
import Repositories.IChiTietSanPhamRepositories;

public class ChiTietSanPhamService implements IChiTietSanPhamReservice {
    IChiTietSanPhamRepositories repo;

    public ChiTietSanPhamService() {
        repo = new ChiTietSanPhamRepositories();
    }

    @Override
    public List<ChiTietSP> getAll() {
        return repo.ReadSPct();
    }

    @Override
    public Integer add(ChiTietSP ctsp) {
        return repo.CreateSpct(ctsp);
    }

    @Override
    public Integer delete(String id) {
        return repo.DeleteSpct(id);
    }

    @Override
    public Integer update(ChiTietSP ctsp, String id) {
        return repo.UpdateSPct(ctsp,id);
    }

    @Override
    public List<ChiTietSP> findByName(String tenSp) {
        return repo.findByName(tenSp);
    }

    @Override
    public ChiTietSP getObjByMa(String maSp) {
        return repo.getObjByMa(maSp);
    }
      @Override
    public ChiTietSP getObjById(String id) {
       return repo.getObjById(id);
    }


  

   

}
