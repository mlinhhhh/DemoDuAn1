package Repositories;

import Service.SanPhamViewModelService;
import ViewModels.SanPhamViewModel;
import java.util.List;

public interface iSanPhamViewModelRepository {
      List<SanPhamViewModel> getAll();
       List<SanPhamViewModel> findByName(String tenSp);
}
