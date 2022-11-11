/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import Models.NSX;
import Models.SanPham;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface INSXServices {

    List<NSX> getAll();

    Integer add(NSX nsx);

    Integer update(String ten, String ma);

    Integer delete(String id);

    List<NSX> findByName(String tenNSX);

    NSX getObjByMa(String ma);

    NSX getObjById(String id);
}
