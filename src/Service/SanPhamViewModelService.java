package Service;

import ViewModels.SanPhamViewModel;
import java.util.List;
import Repositories.SanPhamViewModelRepository;
import Repositories.iSanPhamViewModelRepository;

public class SanPhamViewModelService implements iSanPhamViewModelService{
      iSanPhamViewModelRepository repo;

    public SanPhamViewModelService() {
        repo = new SanPhamViewModelRepository();
    }

    @Override
    public List<SanPhamViewModel> getAll() {
        return repo.getAll();
    }

    @Override
    public List<SanPhamViewModel> findByName(String tenSp) {
        return  repo.findByName(tenSp);
    }
}
