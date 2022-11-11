package Service;

import Models.MauSac;
import Models.SanPham;
import java.util.List;
import Repositories.IMauSacRespo;
import Repositories.MauSacRespo;

public class MauSacService implements IMauSacService {

    IMauSacRespo repo;

    public MauSacService() {
        repo = new MauSacRespo();
    }

    @Override
    public List<MauSac> getAll() {
        return repo.getAll();
    }

    @Override
    public Integer add(MauSac ms) {
        return repo.add(ms);
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
    public List<MauSac> findByName(String tenMs) {
        return repo.findByName(tenMs);
    }

    @Override
    public MauSac getObjByMa(String ma) {
        return repo.getObjByMa(ma);
    }

    @Override
    public MauSac getObjById(String id) {
        return repo.getObjById(id);
    }

}
