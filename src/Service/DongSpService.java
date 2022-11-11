package Service;

import Models.DongSP;
import Models.SanPham;
import Repositories.DONGSPRespo;
import java.util.List;
import Repositories.IDONGSP;

public class DongSpService implements IDongSpService{
    IDONGSP repo;

    public DongSpService() {
        repo = new DONGSPRespo();
    }

    @Override
    public List<DongSP> getAll() {
        return repo.getAll();
    }

    @Override
    public Integer add(DongSP dongSP) {
        return repo.add(dongSP);
    }

    @Override
    public Integer update(String ten,String ma) {
        return repo.update(ten,ma);
    }

    @Override
    public Integer delete(String id) {
        return repo.delete(id);
    }

    @Override
    public List<DongSP> findByName(String tenDongSp) {
        return repo.findByName(tenDongSp);
    }

    @Override
    public DongSP getObjByMa(String ma) {
        return repo.getObjByMa(ma);
    }
     @Override
    public DongSP getObjById(String id) {
       return repo.getObjById(id);
    }
}
