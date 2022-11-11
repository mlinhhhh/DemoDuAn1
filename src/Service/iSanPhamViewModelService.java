package Service;

import ViewModels.SanPhamViewModel;
import java.util.List;

public interface iSanPhamViewModelService {
     List<SanPhamViewModel> getAll();
     List<SanPhamViewModel> findByName(String tenSp);
}
