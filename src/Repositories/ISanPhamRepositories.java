/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositories;

import Models.SanPham;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface ISanPhamRepositories {

    List<SanPham> getAll();

    Integer add(SanPham sp);

    Integer update(String ten,String ma);

    Integer delete(String id);

    List<SanPham> findByName(String tenSp);

    SanPham getObjByMa(String ma);

    SanPham getObjById(String id);
}
