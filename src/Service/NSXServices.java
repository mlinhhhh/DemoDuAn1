/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Models.NSX;
import Models.SanPham;
import Repositories.INSXRespo;
import Repositories.NSXRespo;
import java.util.List;

/**
 *
 * @author ACER
 */
public class NSXServices implements INSXServices {
    

    INSXRespo repo;

    public NSXServices() {
        repo = new NSXRespo();
    }

    @Override
    public List<NSX> getAll() {
        return repo.getAll();
    }

    @Override
    public Integer add(NSX ctsp) {
        return repo.add(ctsp);
    }

    @Override
    public Integer delete(String id) {
        return repo.delete(id);
    }

    @Override
    public Integer update(String ten, String ma) {
        return repo.update(ten,ma);
    }

    @Override
    public List<NSX> findByName(String tenSp) {
        return repo.findByName(tenSp);
    }
    
    @Override
    public NSX getObjByMa(String ma) {
        return repo.getObjByMa(ma);
    }
     @Override
    public NSX getObjById(String id) {
       return repo.getObjById(id);
    }

}
