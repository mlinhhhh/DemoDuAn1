/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author hangnt
 */
public class SanPhamViewModel {
    
    
    private String tenSanPham;
    
    private int soLuong;
    
    private int nambh;
    
    private double giaNhap;
    
    private double giaBan;
    
    private String mota;
    

    public SanPhamViewModel() {
    }

    public SanPhamViewModel(String tenSanPham, int soLuong, int nambh, double giaNhap, double giaBan, String mota) {
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.nambh = nambh;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.mota = mota;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getNambh() {
        return nambh;
    }

    public void setNambh(int nambh) {
        this.nambh = nambh;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Override
    public String toString() {
        return "SanPhamViewModel{" + "tenSanPham=" + tenSanPham + ", soLuong=" + soLuong + ", nambh=" + nambh + ", giaNhap=" + giaNhap + ", giaBan=" + giaBan + ", mota=" + mota + '}';
    }

   

   

    
    
    
}
