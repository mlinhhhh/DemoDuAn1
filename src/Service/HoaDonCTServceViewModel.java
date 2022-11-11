package Service;

import Models.HoaDonChiTiet;
import java.util.List;
import Models.HoaDonChiTiet;
import Repositories.HoaDonCTRepoViewModel;
import Repositories.IHoaDonCTRepoViewModel;

public class HoaDonCTServceViewModel implements IHoaDonCTServiceViewModel{
     IHoaDonCTRepoViewModel repo;

    public HoaDonCTServceViewModel() {
        repo = new HoaDonCTRepoViewModel();
    }

    @Override
    public int saveHoaDon(HoaDonChiTiet hoaDonChiTiet) {
        return repo.saveHoaDon(hoaDonChiTiet);
    }

}
