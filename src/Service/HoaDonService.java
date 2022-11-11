package Service;

import Models.HoaDon;
import Repositories.HoaDonRepo;
import java.util.List;
import Repositories.IHoaDonRepo;

public class HoaDonService implements IHoaDonService{
    IHoaDonRepo repo;

    public HoaDonService() {
        repo = new HoaDonRepo() ;
    }

    public List<HoaDon> getAll() {
        return repo.getAll();
    }

    @Override
    public Integer add() {
        return repo.add();
    }

    @Override
    public Integer update(HoaDon hd, String id) {
        return repo.update(hd, id);
    }

    @Override
    public Integer delete(String id) {
        return repo.delete(id);
    }

    @Override
    public List<HoaDon> findByMaHD(String maHD) {
        return repo.findByMaHD(maHD);
    }
    
    @Override
    public HoaDon getObjByMa(String ma) {
        return repo.getObjByMa(ma);
    }

    @Override
    public HoaDon getObjById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}
