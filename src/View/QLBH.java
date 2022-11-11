package View;

import Models.ChiTietSP;
import Models.DongSP;
import Models.HoaDon;
import Models.MauSac;
import Models.NSX;
import Models.SanPham;
import Service.ChiTietSanPhamService;
import Service.DongSpService;
import Service.HoaDonService;
import Service.IChiTietSanPhamReservice;
import Service.IDongSpService;
import Service.IMauSacService;
import Service.INSXServices;
import Service.ISanPhamService;
import Service.MauSacService;
import Service.NSXServices;
import Service.SanPhamService;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.JobAttributes;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Service.IHoaDonService;
import Service.SanPhamViewModelService;
import ViewModels.SanPhamViewModel;
import ViewModels.ChiTietHoaDonViewModel;
import Service.iSanPhamViewModelService;
import Service.IHoaDonCTServiceViewModel;
import Service.HoaDonCTServceViewModel;
import ViewModels.ChiTietHoaDonViewModel;

public class QLBH extends javax.swing.JFrame {

    private int flag_qlsp_sp = 0;
    private DefaultTableModel defaultTableModel;
    private CardLayout cardLayout;
    SanPham spsv = new SanPham();
    MauSac mssv = new MauSac();
    DongSP dspsv = new DongSP();
    NSX nsxsv = new NSX();

    ChiTietSP ctspsv = new ChiTietSP();
    List<SanPham> lsp = new ArrayList<>();
    List<MauSac> lms = new ArrayList<>();
    List<NSX> lnsx = new ArrayList<>();
    List<DongSP> ldsp = new ArrayList<>();
    List<ChiTietSP> lctsp = new ArrayList<>();
    List<HoaDon> lhd = new ArrayList<>();
    List<SanPhamViewModel> spvw = new ArrayList<>();
    List<ChiTietHoaDonViewModel> cthdvm = new ArrayList<>();

    private DefaultComboBoxModel<SanPham> comboSanPham;
    private DefaultComboBoxModel<NSX> comboNSX;
    private DefaultComboBoxModel<MauSac> comboMauSac;
    private DefaultComboBoxModel<DongSP> comboDongSP;

    private IChiTietSanPhamReservice iChiTietSPService;
    private IDongSpService iDongSPService;
    private IMauSacService iMauSacService;
    private INSXServices iNsxService;
    private ISanPhamService iSanPhamService;
    private IHoaDonService iHoaDonService;
    private iSanPhamViewModelService ispviewmodelService;
    private IHoaDonCTServiceViewModel iHoaDonCTServiceViewModel;

    public QLBH() {
        initComponents();
        iChiTietSPService = new ChiTietSanPhamService();
        iDongSPService = new DongSpService();
        iMauSacService = new MauSacService();
        iNsxService = new NSXServices();
        iSanPhamService = new SanPhamService();
        iHoaDonService = new HoaDonService();
        ispviewmodelService = new SanPhamViewModelService();
        iHoaDonCTServiceViewModel = new HoaDonCTServceViewModel();

        comboSanPham = new DefaultComboBoxModel<>();
        comboNSX = new DefaultComboBoxModel<>();
        comboMauSac = new DefaultComboBoxModel<>();
        comboDongSP = new DefaultComboBoxModel<>();
        setLocationRelativeTo(null);
        setTitle("Lê Thị Mai Linh");
        cardLayout = (CardLayout) pn_main.getLayout();
        cardLayout.show(pn_main, "hello");
        loadData();

    }

    private void getSelectionInterval(int index) {
        if (tb_qlsp_ttsp.getRowCount() > 0) {
            tb_qlsp_ttsp.setRowSelectionInterval(index, index);
            showDetailQLSP();

        }
        if (tb_qlsp_chitietsp.getRowCount() > 0) {
            tb_qlsp_chitietsp.setRowSelectionInterval(index, index);
            showDetailctsp();
        }
    }

    private void showDetailQLSP() {
        int row = tb_qlsp_ttsp.getSelectedRow();
        txt_ttsp_ma.setText((String) tb_qlsp_ttsp.getValueAt(row, 0));
        txt_ttsp_ten.setText((String) tb_qlsp_ttsp.getValueAt(row, 1));

    }

    private void showDetailctsp() {
        int row = tb_qlsp_chitietsp.getSelectedRow();
        tb_qlsp__qlhd.getSelectedRow();
        ChiTietSP ctsp;
        if (checkFindCTSP == 0) {
            ctsp = iChiTietSPService.getAll().get(row);
        } else {
            SanPham sp = (SanPham) comboSanPham.getSelectedItem();
            ctsp = iChiTietSPService.findByName(sp.getTen()).get(row);
        }
        comboSanPham.setSelectedItem(ctsp.getSanPham());
        comboDongSP.setSelectedItem(ctsp.getDongSP());
        comboNSX.setSelectedItem(ctsp.getNsx());
        comboMauSac.setSelectedItem(ctsp.getMauSac());
        txt_ctsp_nambh.setText(ctsp.getNamBH() + "");
        txt_SLton.setText(ctsp.getSoLuongTon() + "");
        txt_ctsp_giaban.setText(ctsp.getGiaBan() + "");
        txt_ctsp_gianhap.setText(ctsp.getGiaNhap() + "");
        txt_ctsp_mota.setText(ctsp.getMoTa());

    }

    // QUẢN LÝ SẢN PHẨM
    // loadData 4 table SanPham,MauSac,DongSP,NSX
    private void load_QLSP() {
        if (flag_qlsp_sp == 0) {
            load_QLSP_SP(iSanPhamService.getAll());
        } else if (flag_qlsp_sp == 1) {
            load_QLSP_DongSP(iDongSPService.getAll());
        } else if (flag_qlsp_sp == 2) {
            load_QLSP_MS(iMauSacService.getAll());
        } else if (flag_qlsp_sp == 3) {
            load_QLSP_NSX(iNsxService.getAll());
        }
        getSelectionInterval(0);
    }

    private void load_QLSP_SP(List<SanPham> list) {
        defaultTableModel = (DefaultTableModel) tb_qlsp_ttsp.getModel();
        defaultTableModel.setRowCount(0);
        for (SanPham x : list) {
            defaultTableModel.addRow(new Object[]{
                x.getMa(), x.getTen()
            });
        }
    }

    private void load_QLSP_MS(List<MauSac> list) {
        defaultTableModel = (DefaultTableModel) tb_ms.getModel();
        defaultTableModel.setRowCount(0);
        for (MauSac x : list) {
            defaultTableModel.addRow(new Object[]{
                x.getMa(), x.getTen()
            });
        }
    }

    private void load_QLSP_DongSP(List<DongSP> list) {
        defaultTableModel = (DefaultTableModel) tb_dsp.getModel();
        defaultTableModel.setRowCount(0);
        for (DongSP x : list) {
            defaultTableModel.addRow(new Object[]{
                x.getMa(), x.getTen()
            });
        }
    }

    private void load_QLSP_NSX(List<NSX> list) {
        defaultTableModel = (DefaultTableModel) tb_nsx.getModel();
        defaultTableModel.setRowCount(0);
        for (NSX x : list) {
            defaultTableModel.addRow(new Object[]{
                x.getMa(), x.getTen()
            });
        }
    }

    // loadData table ChiTietSP
    private void load_QLSP_CTSP(List<ChiTietSP> list) {
        defaultTableModel = (DefaultTableModel) tb_qlsp_chitietsp.getModel();
        defaultTableModel.setRowCount(0);
        for (ChiTietSP x : iChiTietSPService.getAll()) {
            defaultTableModel.addRow(new Object[]{
                x.getSanPham().getMa(), x.getSanPham().getTen(), x.getMauSac().getTen(), x.getDongSP().getTen(),
                x.getNsx().getTen(),
                x.getNamBH(), x.getMoTa(),
                x.getSoLuongTon(), x.getGiaNhap(), x.getGiaBan()
            });
        }
    }

    private void LoadTable(List<SanPhamViewModel> list) {
        defaultTableModel = (DefaultTableModel) tb_qlsp__qlhd.getModel();
        defaultTableModel.setRowCount(0);
        int stt = 1;
        for (SanPhamViewModel sanPham : ispviewmodelService.getAll()) {
            defaultTableModel.addRow(new Object[]{
                stt++, sanPham.getTenSanPham(), sanPham.getSoLuong(), sanPham.getNambh(), sanPham.getGiaNhap(),
                sanPham.getGiaBan(), sanPham.getMota()
            });
        }
    }

    private void load_HD() {
        defaultTableModel = (DefaultTableModel) tb_hoadon.getModel();
        defaultTableModel.setRowCount(0);
        int stthd = 1;
        for (HoaDon x : iHoaDonService.getAll()) {
            defaultTableModel.addRow(new Object[]{
                stthd++, x.getMa(), x.getNgayTao(), x.getStatus()});
        }
    }

    // addComboBox SanPham to QLSP
    private void addCbSanPham() {
        lsp = iSanPhamService.getAll();
        comboSanPham = (DefaultComboBoxModel) (new DefaultComboBoxModel<>(lsp.toArray()));
        cb_ctsp_sp.setModel((DefaultComboBoxModel) comboSanPham);
    }

    // addComboBox NSX to QLSP
    private void addCbNSX() {
        lnsx = iNsxService.getAll();
        comboNSX = (DefaultComboBoxModel) (new DefaultComboBoxModel<>(lnsx.toArray()));
        cb_ctsp_nsx.setModel((DefaultComboBoxModel) comboNSX);
    }

    // addComboBox MauSac to QLSP
    private void addCbMauSac() {
        lms = iMauSacService.getAll();
        comboMauSac = (DefaultComboBoxModel) (new DefaultComboBoxModel<>(lms.toArray()));
        cb_ctsp_mausac.setModel((DefaultComboBoxModel) comboMauSac);
    }

    // addComboBox DongSP to QLSP
    private void addCbDongSP() {
        ldsp = iDongSPService.getAll();
        comboDongSP = (DefaultComboBoxModel) (new DefaultComboBoxModel<>(ldsp.toArray()));
        cb_ctsp_dongsp.setModel((DefaultComboBoxModel) comboDongSP);
    }

    private void loadData() {
        load_QLSP();
        load_QLSP_CTSP(iChiTietSPService.getAll());
        //load_QLSP_CTSP1();
        load_QLSP_DongSP(iDongSPService.getAll());
        load_QLSP_MS(iMauSacService.getAll());
        load_QLSP_NSX(iNsxService.getAll());
        LoadTable(ispviewmodelService.getAll());
        load_HD();
        addCbSanPham();
        addCbNSX();
        addCbMauSac();
        addCbDongSP();
    }
/////////////////////////////////////////////////////

    private boolean checkEmptySP() {
        if (txt_ttsp_ma.getText().isBlank() || txt_ttsp_ten.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "can not empty");
            return false;
        }
        return true;
    }

    private boolean checkMatchersp(String ma) {
        List<SanPham> list = new ArrayList<>();
        list = iSanPhamService.getAll();
        for (SanPham sp : list) {
            if (ma.equals(sp.getMa())) {
                JOptionPane.showMessageDialog(this, "exitsted");
                return false;
            }
        }
        return true;
    }
    // CRUD SanPham,DongSP,MauSac,NSX

    private void addSP() {
        if (checkEmptySP()) {
            SanPham sp = new SanPham();
            if (checkMatchersp(txt_ttsp_ma.getText())) {
                sp.setMa(txt_ttsp_ma.getText());
                sp.setTen(txt_ttsp_ten.getText());
                iSanPhamService.add(sp);
                JOptionPane.showMessageDialog(this, "Save Success !");
                addCbSanPham();
                lsp = iSanPhamService.getAll();
                load_QLSP_SP(lsp);
            }

        }
    }

    private void updateSP() {
        int row = tb_qlsp_ttsp.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            if (checkEmptySP()) {
                String maSp = txt_ttsp_ma.getText();
                String tenSp = txt_ttsp_ten.getText();

                if (iSanPhamService.update(tenSp, maSp) > 0) {
                    JOptionPane.showMessageDialog(this, "success");
                    addCbSanPham();
                    load_QLSP_SP(iSanPhamService.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Fail");
                }
            }
        }

    }

    private void deleteSP() {

//       int row = tb_qlsp_ttsp.getSelectedRow();
//        if (row == -1) {
//            JOptionPane.showMessageDialog(this, "Click on table,please");
//        } else {
//            int choice = JOptionPane.showConfirmDialog(this, "Chắc chắn xoá?", "Thông báo", JOptionPane.YES_NO_OPTION);
//            if (choice == JOptionPane.YES_OPTION) {
//                  String id =tb_qlsp_ttsp.getValueAt(row, 0).toString();
//                int id1 =  Integer.parseInt(id);
//               int rowAffected =iSanPhamService.delete(id);
//                if (rowAffected > 0) {
//                    JOptionPane.showMessageDialog(this, "delete success");
//                    cleanForm();
//                    addCbSanPham();
//                    load_QLSP_SP(lsp);
//                } else {
//                    JOptionPane.showMessageDialog(this, "delete Fail");
//
//                }
//            }
//
//        }
    }

    /////// ////////////////////////////////////////NSX//////////////////////
    private boolean checkEmptynsx() {
        if (txt_nsx_ma.getText().isBlank() || txt_nsx_ten.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "can not empty");
            return false;
        }
        return true;
    }

    private boolean checkMatchernsx(String ma) {
        List<NSX> list = new ArrayList<>();
        list = iNsxService.getAll();
        for (NSX nsx : list) {
            if (ma.equals(nsx.getMa())) {
                JOptionPane.showMessageDialog(this, "exitsted");
                return false;
            }
        }
        return true;
    }

    private void addNSX() {
        if (checkEmptynsx()) {
            NSX nsx = new NSX();
            if (checkMatchersp(txt_nsx_ma.getText())) {
                nsx.setMa(txt_nsx_ma.getText());
                nsx.setTen(txt_nsx_ten.getText());
                iNsxService.add(nsx);
                JOptionPane.showMessageDialog(this, "Save Success !");
                lnsx = iNsxService.getAll();
                load_QLSP_NSX(lnsx);
            }

        }
    }

    private void updateNSX() {
        int row = tb_nsx.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            if (checkEmptynsx()) {
                String mansx = txt_nsx_ma.getText();
                String tennsx = txt_nsx_ten.getText();
                if (iNsxService.update(tennsx, mansx) > 0) {
                    JOptionPane.showMessageDialog(this, "success");
                    addCbNSX();
                    load_QLSP_NSX(iNsxService.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Fail");
                }
            }
        }
    }

    private void deleteNSX() {
        int row = tb_nsx.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Chắc chắn xoá?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                int rowAffected = iNsxService.delete(tb_nsx.getValueAt(row, 0).toString());
                if (rowAffected > 0) {
                    JOptionPane.showMessageDialog(this, "delete success");
                    cleanForm();
                } else {
                    JOptionPane.showMessageDialog(this, "delete Fail");

                }
            }

        }

    }

////////////////////////////////dongsp/////////////////////
    private boolean checkEmptydongsp() {
        if (txt_dsp_ma.getText().isBlank() || txt_dsp_ten.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "can not empty");
            return false;
        }
        return true;
    }

    private boolean checkMatcherdongsp(String ma) {
        List<DongSP> list = new ArrayList<>();
        list = iDongSPService.getAll();
        for (DongSP dsp : list) {
            if (ma.equals(dsp.getMa())) {
                JOptionPane.showMessageDialog(this, "exitsted");
                return false;
            }
        }
        return true;
    }

    private void addDongSP() {
        if (checkEmptydongsp()) {
            DongSP DSP = new DongSP();
            if (checkMatchersp(txt_dsp_ma.getText())) {
                DSP.setMa(txt_dsp_ma.getText());
                DSP.setTen(txt_dsp_ten.getText());
                iDongSPService.add(DSP);
                JOptionPane.showMessageDialog(this, "Save Success !");
                ldsp = iDongSPService.getAll();
                load_QLSP_DongSP(ldsp);
            }

        }
    }

    private void updateDongSP() {
        int row = tb_dsp.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            if (checkEmptydongsp()) {
                String madsp = txt_dsp_ma.getText();
                String tendsp = txt_dsp_ten.getText();
                if (iDongSPService.update(tendsp, madsp) > 0) {
                    JOptionPane.showMessageDialog(this, "success");
                    addCbDongSP();
                    load_QLSP_DongSP(iDongSPService.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Fail");
                }
            }
        }
    }

    private void deleteDongSP() {
        int row = tb_dsp.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Chắc chắn xoá?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                int rowAffected = iDongSPService.delete(tb_dsp.getValueAt(row, 0).toString());
                if (rowAffected > 0) {
                    JOptionPane.showMessageDialog(this, "delete success");
                    cleanForm();
                } else {
                    JOptionPane.showMessageDialog(this, "delete Fail");

                }
            }

        }
    }

///////////////////////////////mausac////////////////
    private boolean checkEmptyms() {
        if (txt_ms_ma.getText().isBlank() || txt_ms_ten.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "can not empty");
            return false;
        }
        return true;
    }

    private boolean checkMatcherms(String ma) {
        List<MauSac> list = new ArrayList<>();
        list = iMauSacService.getAll();
        for (MauSac ms : list) {
            if (ma.equals(ms.getMa())) {
                JOptionPane.showMessageDialog(this, "exitsted");
                return false;
            }
        }
        return true;
    }

    private void addMauSac() {
        if (checkEmptyms()) {
            MauSac ms = new MauSac();
            if (checkMatchersp(txt_ms_ma.getText())) {
                ms.setMa(txt_ms_ma.getText());
                ms.setTen(txt_ms_ten.getText());
                iMauSacService.add(ms);
                JOptionPane.showMessageDialog(this, "Save Success !");
                lms = iMauSacService.getAll();
                load_QLSP_MS(lms);
            }

        }
    }

    private void updateMauSac() {
        int row = tb_ms.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            if (checkEmptyms()) {
                String mams = txt_ms_ma.getText();
                String tenms = txt_ms_ten.getText();
                if (iMauSacService.update(tenms, mams) > 0) {
                    JOptionPane.showMessageDialog(this, "success");
                    addCbMauSac();
                    load_QLSP_MS(iMauSacService.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Fail");
                }
            }
        }
    }

    private void deleteMauSac() {
        int row = tb_ms.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Chắc chắn xoá?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                int rowAffected = iMauSacService.delete(tb_ms.getValueAt(row, 0).toString());
                if (rowAffected > 0) {
                    JOptionPane.showMessageDialog(this, "delete success");
                    cleanForm();
                } else {
                    JOptionPane.showMessageDialog(this, "delete Fail");

                }
            }

        }
    }

    ///////////////////////////////////////////////////////////
    private void addTableGioHang(List<ChiTietHoaDonViewModel> list) {
        defaultTableModel = (DefaultTableModel) tb_giohang.getModel();
        defaultTableModel.setRowCount(0);
        for (ChiTietHoaDonViewModel chiTietHoaDonViewModel : list) {
            defaultTableModel.addRow(new Object[]{
                chiTietHoaDonViewModel.getTenSp(), chiTietHoaDonViewModel.getSoLuong(), chiTietHoaDonViewModel.getDonGia(),
                chiTietHoaDonViewModel.getSoLuong() * chiTietHoaDonViewModel.getDonGia()
            });
        }
    }

    private void addTable(List<SanPhamViewModel> list) {
        defaultTableModel = (DefaultTableModel) tb_qlsp__qlhd.getModel();
        defaultTableModel.setRowCount(0);
        for (SanPhamViewModel sanPhamViewModel : list) {
            defaultTableModel.addRow(new Object[]{
                sanPhamViewModel.getTenSanPham(), sanPhamViewModel.getSoLuong(), sanPhamViewModel.getGiaNhap(),
                sanPhamViewModel.getGiaBan(), sanPhamViewModel.getMota()
            });
        }
    }

    private void addTableHoaDon(List<HoaDon> list) {
        defaultTableModel = (DefaultTableModel) tb_hoadon.getModel();
        defaultTableModel.setRowCount(0);
        for (HoaDon hoaDon : list) {
            defaultTableModel.addRow(new Object[]{
                hoaDon.getId(), hoaDon.getNgayTao(), hoaDon.getStatus()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pn_navigation = new javax.swing.JPanel();
        qlhd = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        sp = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pn_nv = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pn_khachhang = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        pn_main = new javax.swing.JPanel();
        pn_hello = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        pn_qlsp = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        qlsp_sp1 = new javax.swing.JPanel();
        lbl_title_gener1 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tb_ms = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txt_ms_ma = new javax.swing.JTextField();
        txt_ms_ten = new javax.swing.JTextField();
        btn_ms_xoa = new javax.swing.JButton();
        btn_ms_capnhat = new javax.swing.JButton();
        btn_ms_them = new javax.swing.JButton();
        txt_tk_ms = new javax.swing.JTextField();
        qlsp_sp2 = new javax.swing.JPanel();
        lbl_title_gener2 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tb_nsx = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txt_nsx_ma = new javax.swing.JTextField();
        txt_tk_nsx = new javax.swing.JTextField();
        btn_nsx_xoa = new javax.swing.JButton();
        btn_nsx_capnhat = new javax.swing.JButton();
        btn_nsx_them = new javax.swing.JButton();
        txt_nsx_ten = new javax.swing.JTextField();
        qlsp_sp3 = new javax.swing.JPanel();
        lbl_title_gener3 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tb_dsp = new javax.swing.JTable();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txt_dsp_ma = new javax.swing.JTextField();
        txt_dsp_ten = new javax.swing.JTextField();
        btn_dsp_xoa = new javax.swing.JButton();
        btn_dsp_capnhat = new javax.swing.JButton();
        btn_dsp_them = new javax.swing.JButton();
        txt_tk_dsp = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        qlsp_sp = new javax.swing.JPanel();
        lbl_title_gener = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tb_qlsp_ttsp = new javax.swing.JTable();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txt_ttsp_ma = new javax.swing.JTextField();
        txt_ttsp_ten = new javax.swing.JTextField();
        btn_ttsp_xoa = new javax.swing.JButton();
        btn_ttsp_capnhat = new javax.swing.JButton();
        btn_ttsp_them = new javax.swing.JButton();
        txt_tk_sp = new javax.swing.JTextField();
        qlsp_ctsp = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tb_qlsp_chitietsp = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        cb_ctsp_sp = new javax.swing.JComboBox<>();
        cb_ctsp_nsx = new javax.swing.JComboBox<>();
        cb_ctsp_mausac = new javax.swing.JComboBox<>();
        cb_ctsp_dongsp = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txt_ctsp_nambh = new javax.swing.JTextField();
        txt_ctsp_gianhap = new javax.swing.JTextField();
        txt_ctsp_giaban = new javax.swing.JTextField();
        btn_ctsp_them = new javax.swing.JButton();
        btn_ctsp_capnhat = new javax.swing.JButton();
        btn_ctsp_xoa = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        txt_ctsp_mota = new javax.swing.JTextField();
        txt_SLton = new javax.swing.JTextField();
        txt_tk_ctsp = new javax.swing.JTextField();
        lblhienthi = new javax.swing.JLabel();
        lblhienthi1 = new javax.swing.JLabel();
        lblhienthi2 = new javax.swing.JLabel();
        lblhienthi3 = new javax.swing.JLabel();
        pn_hoadon = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_hoadon = new javax.swing.JTable();
        rd_datt = new javax.swing.JRadioButton();
        rd_dahuy = new javax.swing.JRadioButton();
        rd_tatca = new javax.swing.JRadioButton();
        rd_chott = new javax.swing.JRadioButton();
        btn_taohd = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txt_sanpham_qlhd = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tb_qlsp__qlhd = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_mahd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_ngaytao = new javax.swing.JTextField();
        txt_tennv = new javax.swing.JTextField();
        txt_tongtien = new javax.swing.JTextField();
        txt_tienkhachdua = new javax.swing.JTextField();
        txt_tienthua = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_thanhtoan = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_giohang = new javax.swing.JTable();
        pn_nhanvien = new javax.swing.JPanel();
        qlnv_nv = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_qlnv_nhanvien = new javax.swing.JTable();
        txt_nv_ma = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_nv_ten = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_nv_diachi = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txt_nv_sodt = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        cb_nv_cuahang = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        cb_nv_chucvu = new javax.swing.JComboBox<>();
        btn_nv_them = new javax.swing.JButton();
        btn_nv_timkiem = new javax.swing.JButton();
        btn_nv_capnhat = new javax.swing.JButton();
        btn_nv_xoa = new javax.swing.JButton();
        rd_nv_nam = new javax.swing.JRadioButton();
        rd_nv_nu = new javax.swing.JRadioButton();
        jLabel57 = new javax.swing.JLabel();
        txt_nv_matkhau = new javax.swing.JTextField();
        qlnv_cv = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_qlnv_chucvu = new javax.swing.JTable();
        txt_cv_ten = new javax.swing.JTextField();
        txt_cv_ma = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        btn_cv_xoa = new javax.swing.JButton();
        btn_cv_timkiem = new javax.swing.JButton();
        btn_cv_them = new javax.swing.JButton();
        btn_cv_capnhat = new javax.swing.JButton();
        pn_kh = new javax.swing.JPanel();
        qlch_kh = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_qlch_khachhang = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cb_kh_tp = new javax.swing.JComboBox<>();
        txt_kh_quocgia = new javax.swing.JTextField();
        txt_kh_diachi = new javax.swing.JTextField();
        txt_kh_ma = new javax.swing.JTextField();
        txt_kh_ten = new javax.swing.JTextField();
        txt_kh_tendem = new javax.swing.JTextField();
        txt_kh_ho = new javax.swing.JTextField();
        txt_kh_ngaysinh = new javax.swing.JTextField();
        txt_kh_sdt = new javax.swing.JTextField();
        btn_kh_them = new javax.swing.JButton();
        btn_kh_timkiem = new javax.swing.JButton();
        btn_kh_capnhat = new javax.swing.JButton();
        btn_kh_xoa = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        txt_kh_matkhau = new javax.swing.JTextField();
        qlch_ch = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_qlch_cuahang = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        txt_ch_ma = new javax.swing.JTextField();
        txt_ch_ten = new javax.swing.JTextField();
        txt_ch_diachi = new javax.swing.JTextField();
        cb_ch_thanhpho = new javax.swing.JComboBox<>();
        txt_ch_quocgia = new javax.swing.JTextField();
        btn_ch_xoa = new javax.swing.JButton();
        btn_ch_them = new javax.swing.JButton();
        btn_ch_timkiem = new javax.swing.JButton();
        btn_ch_capnhat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pn_navigation.setBackground(new java.awt.Color(153, 51, 0));

        qlhd.setBackground(new java.awt.Color(255, 255, 255));
        qlhd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qlhdMouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản lý bán hàng");

        javax.swing.GroupLayout qlhdLayout = new javax.swing.GroupLayout(qlhd);
        qlhd.setLayout(qlhdLayout);
        qlhdLayout.setHorizontalGroup(
            qlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlhdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        qlhdLayout.setVerticalGroup(
            qlhdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        sp.setBackground(new java.awt.Color(255, 255, 255));
        sp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spMouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Quản lý sản phẩm");

        javax.swing.GroupLayout spLayout = new javax.swing.GroupLayout(sp);
        sp.setLayout(spLayout);
        spLayout.setHorizontalGroup(
            spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(spLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );
        spLayout.setVerticalGroup(
            spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        logout.setBackground(new java.awt.Color(255, 255, 255));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 102, 102));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Đăng xuất");

        javax.swing.GroupLayout logoutLayout = new javax.swing.GroupLayout(logout);
        logout.setLayout(logoutLayout);
        logoutLayout.setHorizontalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(153, 51, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Welcome ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(82, 82, 82))
        );

        pn_nv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pn_nvMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản lí nhân viên");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pn_nvLayout = new javax.swing.GroupLayout(pn_nv);
        pn_nv.setLayout(pn_nvLayout);
        pn_nvLayout.setHorizontalGroup(
            pn_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_nvLayout.setVerticalGroup(
            pn_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        pn_khachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pn_khachhangMouseClicked(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(102, 102, 102));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Quản lí khách hàng");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pn_khachhangLayout = new javax.swing.GroupLayout(pn_khachhang);
        pn_khachhang.setLayout(pn_khachhangLayout);
        pn_khachhangLayout.setHorizontalGroup(
            pn_khachhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_khachhangLayout.setVerticalGroup(
            pn_khachhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pn_navigationLayout = new javax.swing.GroupLayout(pn_navigation);
        pn_navigation.setLayout(pn_navigationLayout);
        pn_navigationLayout.setHorizontalGroup(
            pn_navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_navigationLayout.createSequentialGroup()
                .addGroup(pn_navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_navigationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pn_navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(qlhd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_nv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_khachhang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_navigationLayout.setVerticalGroup(
            pn_navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_navigationLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(qlhd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pn_nv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pn_khachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pn_main.setLayout(new java.awt.CardLayout());

        jLabel48.setBackground(new java.awt.Color(102, 102, 102));
        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("WELCOME!");

        javax.swing.GroupLayout pn_helloLayout = new javax.swing.GroupLayout(pn_hello);
        pn_hello.setLayout(pn_helloLayout);
        pn_helloLayout.setHorizontalGroup(
            pn_helloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_helloLayout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_helloLayout.setVerticalGroup(
            pn_helloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_helloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                .addContainerGap())
        );

        pn_main.add(pn_hello, "hello");

        pn_qlsp.setLayout(new java.awt.GridLayout(1, 0));

        qlsp_sp1.setBorder(javax.swing.BorderFactory.createTitledBorder("Màu Sắc"));

        lbl_title_gener1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_title_gener1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title_gener1.setText("Màu Sắc");

        tb_ms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Màu", "Tên Màu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_ms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_msMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tb_ms);

        jLabel49.setText("Mã");

        jLabel50.setText("Tên");

        txt_ms_ma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_ms_ten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btn_ms_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_ms_xoa.setText("Xóa");
        btn_ms_xoa.setBorderPainted(false);
        btn_ms_xoa.setFocusPainted(false);
        btn_ms_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ms_xoaActionPerformed(evt);
            }
        });

        btn_ms_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_ms_capnhat.setText("Cập nhật");
        btn_ms_capnhat.setBorderPainted(false);
        btn_ms_capnhat.setFocusPainted(false);
        btn_ms_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ms_capnhatActionPerformed(evt);
            }
        });

        btn_ms_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_ms_them.setText("Thêm");
        btn_ms_them.setBorderPainted(false);
        btn_ms_them.setFocusPainted(false);
        btn_ms_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ms_themActionPerformed(evt);
            }
        });

        txt_tk_ms.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txt_tk_ms.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_tk_msKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout qlsp_sp1Layout = new javax.swing.GroupLayout(qlsp_sp1);
        qlsp_sp1.setLayout(qlsp_sp1Layout);
        qlsp_sp1Layout.setHorizontalGroup(
            qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_sp1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(qlsp_sp1Layout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(lbl_title_gener1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(qlsp_sp1Layout.createSequentialGroup()
                        .addGroup(qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_tk_ms)
                            .addGroup(qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(qlsp_sp1Layout.createSequentialGroup()
                                    .addGroup(qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel50))
                                    .addGap(18, 18, 18)
                                    .addGroup(qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_ms_ma)
                                        .addComponent(txt_ms_ten)))
                                .addGroup(qlsp_sp1Layout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(btn_ms_them, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btn_ms_capnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btn_ms_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 18, Short.MAX_VALUE))))
        );
        qlsp_sp1Layout.setVerticalGroup(
            qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_sp1Layout.createSequentialGroup()
                .addComponent(lbl_title_gener1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txt_ms_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txt_ms_ten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(qlsp_sp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ms_xoa)
                    .addComponent(btn_ms_them)
                    .addComponent(btn_ms_capnhat))
                .addGap(48, 48, 48)
                .addComponent(txt_tk_ms, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        qlsp_sp2.setBorder(javax.swing.BorderFactory.createTitledBorder("Nhà Sản Xuất"));

        lbl_title_gener2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_title_gener2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title_gener2.setText("Nhà Sản Xuất");

        tb_nsx.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NSX", "Tên NSX"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_nsx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_nsxMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tb_nsx);

        jLabel53.setText("Mã");

        jLabel54.setText("Tên");

        txt_nsx_ma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_tk_nsx.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txt_tk_nsx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_tk_nsxKeyReleased(evt);
            }
        });

        btn_nsx_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_nsx_xoa.setText("Xóa");
        btn_nsx_xoa.setBorderPainted(false);
        btn_nsx_xoa.setFocusPainted(false);
        btn_nsx_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nsx_xoaActionPerformed(evt);
            }
        });

        btn_nsx_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_nsx_capnhat.setText("Cập nhật");
        btn_nsx_capnhat.setBorderPainted(false);
        btn_nsx_capnhat.setFocusPainted(false);
        btn_nsx_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nsx_capnhatActionPerformed(evt);
            }
        });

        btn_nsx_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_nsx_them.setText("Thêm");
        btn_nsx_them.setBorderPainted(false);
        btn_nsx_them.setFocusPainted(false);
        btn_nsx_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nsx_themActionPerformed(evt);
            }
        });

        txt_nsx_ten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout qlsp_sp2Layout = new javax.swing.GroupLayout(qlsp_sp2);
        qlsp_sp2.setLayout(qlsp_sp2Layout);
        qlsp_sp2Layout.setHorizontalGroup(
            qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_sp2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_title_gener2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlsp_sp2Layout.createSequentialGroup()
                        .addGroup(qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qlsp_sp2Layout.createSequentialGroup()
                                .addGroup(qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_nsx_ma)
                                    .addComponent(txt_nsx_ten)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qlsp_sp2Layout.createSequentialGroup()
                                .addComponent(btn_nsx_them, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addComponent(btn_nsx_capnhat)
                                .addGap(18, 18, 18)
                                .addComponent(btn_nsx_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlsp_sp2Layout.createSequentialGroup()
                        .addGroup(qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_tk_nsx, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        qlsp_sp2Layout.setVerticalGroup(
            qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_sp2Layout.createSequentialGroup()
                .addComponent(lbl_title_gener2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(txt_nsx_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(qlsp_sp2Layout.createSequentialGroup()
                        .addComponent(txt_nsx_ten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(qlsp_sp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_nsx_them)
                            .addComponent(btn_nsx_capnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_nsx_xoa)))
                    .addComponent(jLabel54))
                .addGap(48, 48, 48)
                .addComponent(txt_tk_nsx, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        qlsp_sp3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dòng sản phẩm"));

        lbl_title_gener3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_title_gener3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title_gener3.setText("Dòng Sản Phẩm");

        tb_dsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Dòng SP", "Tên Dòng SP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_dsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_dspMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tb_dsp);

        jLabel55.setText("Mã");

        jLabel56.setText("Tên");

        txt_dsp_ma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_dsp_ten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btn_dsp_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_dsp_xoa.setText("Xóa");
        btn_dsp_xoa.setBorderPainted(false);
        btn_dsp_xoa.setFocusPainted(false);
        btn_dsp_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dsp_xoaActionPerformed(evt);
            }
        });

        btn_dsp_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_dsp_capnhat.setText("Cập nhật");
        btn_dsp_capnhat.setBorderPainted(false);
        btn_dsp_capnhat.setFocusPainted(false);
        btn_dsp_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dsp_capnhatActionPerformed(evt);
            }
        });

        btn_dsp_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_dsp_them.setText("Thêm");
        btn_dsp_them.setBorderPainted(false);
        btn_dsp_them.setFocusPainted(false);
        btn_dsp_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dsp_themActionPerformed(evt);
            }
        });

        txt_tk_dsp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txt_tk_dsp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_tk_dspKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout qlsp_sp3Layout = new javax.swing.GroupLayout(qlsp_sp3);
        qlsp_sp3.setLayout(qlsp_sp3Layout);
        qlsp_sp3Layout.setHorizontalGroup(
            qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_sp3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_title_gener3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlsp_sp3Layout.createSequentialGroup()
                        .addGap(0, 73, Short.MAX_VALUE)
                        .addGroup(qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(qlsp_sp3Layout.createSequentialGroup()
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(qlsp_sp3Layout.createSequentialGroup()
                                .addComponent(btn_dsp_them, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_dsp_capnhat)
                                .addGap(30, 30, 30)
                                .addComponent(btn_dsp_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))))
                    .addGroup(qlsp_sp3Layout.createSequentialGroup()
                        .addGroup(qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tk_dsp)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlsp_sp3Layout.createSequentialGroup()
                                .addGroup(qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel56))
                                .addGap(18, 18, 18)
                                .addGroup(qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_dsp_ma)
                                    .addComponent(txt_dsp_ten))))
                        .addContainerGap())))
        );
        qlsp_sp3Layout.setVerticalGroup(
            qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_sp3Layout.createSequentialGroup()
                .addComponent(lbl_title_gener3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txt_dsp_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(txt_dsp_ten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(qlsp_sp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_dsp_them)
                    .addComponent(btn_dsp_capnhat)
                    .addComponent(btn_dsp_xoa))
                .addGap(46, 46, 46)
                .addComponent(txt_tk_dsp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(qlsp_sp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(qlsp_sp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qlsp_sp3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(qlsp_sp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(qlsp_sp2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qlsp_sp3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông tin sản phẩm", jPanel8);

        qlsp_sp.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin"));

        lbl_title_gener.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_title_gener.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title_gener.setText("SẢN PHẨM");

        tb_qlsp_ttsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_qlsp_ttsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlsp_ttspMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tb_qlsp_ttsp);

        jLabel46.setText("Mã");

        jLabel47.setText("Tên");

        txt_ttsp_ma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_ttsp_ten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btn_ttsp_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_ttsp_xoa.setText("Xóa");
        btn_ttsp_xoa.setBorderPainted(false);
        btn_ttsp_xoa.setFocusPainted(false);
        btn_ttsp_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttsp_xoaActionPerformed(evt);
            }
        });

        btn_ttsp_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_ttsp_capnhat.setText("Cập nhật");
        btn_ttsp_capnhat.setBorderPainted(false);
        btn_ttsp_capnhat.setFocusPainted(false);
        btn_ttsp_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttsp_capnhatActionPerformed(evt);
            }
        });

        btn_ttsp_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_ttsp_them.setText("Thêm");
        btn_ttsp_them.setBorderPainted(false);
        btn_ttsp_them.setFocusPainted(false);
        btn_ttsp_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ttsp_themActionPerformed(evt);
            }
        });

        txt_tk_sp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txt_tk_sp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tk_spActionPerformed(evt);
            }
        });
        txt_tk_sp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_tk_spKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout qlsp_spLayout = new javax.swing.GroupLayout(qlsp_sp);
        qlsp_sp.setLayout(qlsp_spLayout);
        qlsp_spLayout.setHorizontalGroup(
            qlsp_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_spLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlsp_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_title_gener, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(qlsp_spLayout.createSequentialGroup()
                        .addGroup(qlsp_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(qlsp_spLayout.createSequentialGroup()
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_ttsp_ma))
                            .addGroup(qlsp_spLayout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addGap(38, 38, 38)
                                .addComponent(txt_ttsp_ten)))
                        .addGap(43, 43, 43))
                    .addGroup(qlsp_spLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(qlsp_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(qlsp_spLayout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txt_tk_sp)))
                    .addGroup(qlsp_spLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btn_ttsp_them, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_ttsp_capnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btn_ttsp_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addContainerGap())
        );
        qlsp_spLayout.setVerticalGroup(
            qlsp_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_spLayout.createSequentialGroup()
                .addComponent(lbl_title_gener)
                .addGroup(qlsp_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txt_ttsp_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txt_ttsp_ten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(qlsp_spLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ttsp_them)
                    .addComponent(btn_ttsp_capnhat)
                    .addComponent(btn_ttsp_xoa))
                .addGap(31, 31, 31)
                .addComponent(txt_tk_sp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        qlsp_ctsp.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi tiết sản phẩm"));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("CHI TIẾT SẢN PHẨM");

        tb_qlsp_chitietsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên", "Màu", "Dòng SP", "Nhà SX", "Năm BH", "Mô tả", "SLTồn", "Gía Nhập", "Gía Bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_qlsp_chitietsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlsp_chitietspMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tb_qlsp_chitietsp);

        jLabel38.setText("Sản phẩm");

        jLabel39.setText("Nhà sản xuất");

        jLabel40.setText("Màu sắc");

        jLabel41.setText("Dòng SP");

        cb_ctsp_sp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cb_ctsp_sp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_ctsp_spItemStateChanged(evt);
            }
        });
        cb_ctsp_sp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ctsp_spActionPerformed(evt);
            }
        });

        cb_ctsp_nsx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_ctsp_nsxItemStateChanged(evt);
            }
        });

        cb_ctsp_mausac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_ctsp_mausacItemStateChanged(evt);
            }
        });

        cb_ctsp_dongsp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_ctsp_dongspItemStateChanged(evt);
            }
        });
        cb_ctsp_dongsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ctsp_dongspActionPerformed(evt);
            }
        });

        jLabel42.setText("Năm BH");

        jLabel43.setText("Số lượng tồn");

        jLabel44.setText("Giá nhập");

        jLabel45.setText("Giá bán");

        txt_ctsp_nambh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_ctsp_gianhap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_ctsp_giaban.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btn_ctsp_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_ctsp_them.setText("Thêm");
        btn_ctsp_them.setBorderPainted(false);
        btn_ctsp_them.setFocusPainted(false);
        btn_ctsp_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ctsp_themActionPerformed(evt);
            }
        });

        btn_ctsp_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_ctsp_capnhat.setText("Cập nhật");
        btn_ctsp_capnhat.setBorderPainted(false);
        btn_ctsp_capnhat.setFocusPainted(false);
        btn_ctsp_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ctsp_capnhatActionPerformed(evt);
            }
        });

        btn_ctsp_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_ctsp_xoa.setText("Xóa");
        btn_ctsp_xoa.setBorderPainted(false);
        btn_ctsp_xoa.setFocusPainted(false);
        btn_ctsp_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ctsp_xoaActionPerformed(evt);
            }
        });

        jLabel51.setText("Mô tả");

        txt_ctsp_mota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_tk_ctsp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txt_tk_ctsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tk_ctspActionPerformed(evt);
            }
        });
        txt_tk_ctsp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_tk_ctspKeyReleased(evt);
            }
        });

        lblhienthi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblhienthi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblhienthi.setText("fgjfj");

        lblhienthi1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblhienthi1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblhienthi1.setText("hrghfr");

        lblhienthi2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblhienthi2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblhienthi2.setText("fhdfhgeh");

        lblhienthi3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblhienthi3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblhienthi3.setText("shrtwthwrh");

        javax.swing.GroupLayout qlsp_ctspLayout = new javax.swing.GroupLayout(qlsp_ctsp);
        qlsp_ctsp.setLayout(qlsp_ctspLayout);
        qlsp_ctspLayout.setHorizontalGroup(
            qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_ctspLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(txt_tk_ctsp))
                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                        .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(33, 33, 33)
                                .addComponent(cb_ctsp_sp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlsp_ctspLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cb_ctsp_mausac, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_ctsp_nsx, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                                .addComponent(btn_ctsp_capnhat))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qlsp_ctspLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                        .addComponent(lblhienthi3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_ctsp_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                        .addComponent(lblhienthi, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_ctsp_them, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                        .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblhienthi2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblhienthi1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                        .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel42))
                                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(txt_ctsp_nambh))
                                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cb_ctsp_dongsp, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_SLton))
                            .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_ctsp_giaban, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                    .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(qlsp_ctspLayout.createSequentialGroup()
                                            .addGap(34, 34, 34)
                                            .addComponent(txt_ctsp_gianhap, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlsp_ctspLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_ctsp_mota, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        qlsp_ctspLayout.setVerticalGroup(
            qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlsp_ctspLayout.createSequentialGroup()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(cb_ctsp_sp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ctsp_them)
                    .addComponent(lblhienthi, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_ctsp_capnhat))
                    .addGroup(qlsp_ctspLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_ctsp_nsx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)
                            .addComponent(lblhienthi1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_ctsp_mausac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)
                            .addComponent(lblhienthi2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(cb_ctsp_dongsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ctsp_xoa)
                    .addComponent(lblhienthi3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txt_ctsp_nambh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(txt_SLton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txt_ctsp_gianhap, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txt_ctsp_giaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(qlsp_ctspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txt_ctsp_mota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_tk_ctsp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(qlsp_sp, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(qlsp_ctsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(qlsp_sp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(qlsp_ctsp, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel7);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pn_qlsp.add(jPanel6);

        pn_main.add(pn_qlsp, "qlsp");

        tb_hoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HĐ", "Ngày tạo", "Tình trạng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_hoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_hoadonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_hoadon);

        buttonGroup1.add(rd_datt);
        rd_datt.setText("Đã thanh toán");
        rd_datt.setFocusPainted(false);
        rd_datt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_dattActionPerformed(evt);
            }
        });

        buttonGroup1.add(rd_dahuy);
        rd_dahuy.setText("Đã hủy");
        rd_dahuy.setFocusPainted(false);
        rd_dahuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_dahuyActionPerformed(evt);
            }
        });

        buttonGroup1.add(rd_tatca);
        rd_tatca.setText("Tất cả");
        rd_tatca.setFocusPainted(false);
        rd_tatca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_tatcaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rd_chott);
        rd_chott.setText("Chờ thanh toán");
        rd_chott.setFocusPainted(false);
        rd_chott.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rd_chottActionPerformed(evt);
            }
        });

        btn_taohd.setBackground(new java.awt.Color(153, 153, 153));
        btn_taohd.setForeground(new java.awt.Color(255, 255, 255));
        btn_taohd.setText("Tạo hóa đơn");
        btn_taohd.setBorder(null);
        btn_taohd.setFocusPainted(false);
        btn_taohd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taohdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_taohd, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 68, Short.MAX_VALUE)
                        .addComponent(rd_chott)
                        .addGap(18, 18, 18)
                        .addComponent(rd_tatca)
                        .addGap(18, 18, 18)
                        .addComponent(rd_dahuy)
                        .addGap(18, 18, 18)
                        .addComponent(rd_datt))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rd_datt)
                    .addComponent(rd_dahuy)
                    .addComponent(rd_tatca)
                    .addComponent(rd_chott))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_taohd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 91, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản phẩm"));

        txt_sanpham_qlhd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_sanpham_qlhd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_sanpham_qlhdKeyReleased(evt);
            }
        });

        tb_qlsp__qlhd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên SP", "SLTồn", "Năm BH", "Gía Nhập", "Gía Bán", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_qlsp__qlhd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlsp__qlhdMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tb_qlsp__qlhd);
        if (tb_qlsp__qlhd.getColumnModel().getColumnCount() > 0) {
            tb_qlsp__qlhd.getColumnModel().getColumn(0).setResizable(false);
            tb_qlsp__qlhd.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_sanpham_qlhd, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane12))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_sanpham_qlhd, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn"));

        jLabel5.setText("Mã HD");

        txt_mahd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel6.setText("Ngày tạo");

        txt_ngaytao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txt_tennv.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txt_tongtien.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txt_tienkhachdua.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txt_tienthua.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel7.setText("Tên NV");

        jLabel8.setText("Tổng tiền");

        jLabel9.setText("Tiền khách đưa");

        jLabel10.setText("Tiền thừa");

        btn_thanhtoan.setBackground(new java.awt.Color(153, 153, 153));
        btn_thanhtoan.setForeground(new java.awt.Color(255, 255, 255));
        btn_thanhtoan.setText("THANH TOÁN");
        btn_thanhtoan.setBorder(null);
        btn_thanhtoan.setFocusPainted(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_mahd, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(txt_ngaytao)
                    .addComponent(txt_tennv)
                    .addComponent(txt_tongtien)
                    .addComponent(txt_tienkhachdua)
                    .addComponent(txt_tienthua))
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_mahd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_ngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tennv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tienkhachdua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(41, 41, 41)
                .addComponent(btn_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));

        tb_giohang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane2.setViewportView(tb_giohang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_hoadonLayout = new javax.swing.GroupLayout(pn_hoadon);
        pn_hoadon.setLayout(pn_hoadonLayout);
        pn_hoadonLayout.setHorizontalGroup(
            pn_hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_hoadonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_hoadonLayout.createSequentialGroup()
                        .addGroup(pn_hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pn_hoadonLayout.setVerticalGroup(
            pn_hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_hoadonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_hoadonLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pn_main.add(pn_hoadon, "hoadon");

        qlnv_nv.setBorder(javax.swing.BorderFactory.createTitledBorder("Nhân viên"));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("NHÂN VIÊN");

        tb_qlnv_nhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên", "Địa chỉ", "Số điện thoại", "Cửa hàng", "Chức vụ"
            }
        ));
        tb_qlnv_nhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlnv_nhanvienMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tb_qlnv_nhanvien);

        txt_nv_ma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel27.setText("Mã");

        jLabel28.setText("Tên");

        txt_nv_ten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel29.setText("Giới tính");

        jLabel30.setText("Địa chỉ");

        txt_nv_diachi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel31.setText("Số DT");

        txt_nv_sodt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel32.setText("Cửa hàng");

        cb_nv_cuahang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cb_nv_cuahang.setFocusable(false);

        jLabel33.setText("Chức vụ");

        cb_nv_chucvu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cb_nv_chucvu.setFocusable(false);

        btn_nv_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_nv_them.setText("Thêm");
        btn_nv_them.setBorderPainted(false);
        btn_nv_them.setFocusPainted(false);
        btn_nv_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nv_themActionPerformed(evt);
            }
        });

        btn_nv_timkiem.setBackground(new java.awt.Color(204, 204, 204));
        btn_nv_timkiem.setText("Tìm kiếm");
        btn_nv_timkiem.setBorderPainted(false);
        btn_nv_timkiem.setFocusPainted(false);
        btn_nv_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nv_timkiemActionPerformed(evt);
            }
        });

        btn_nv_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_nv_capnhat.setText("Cập nhật");
        btn_nv_capnhat.setBorderPainted(false);
        btn_nv_capnhat.setFocusPainted(false);
        btn_nv_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nv_capnhatActionPerformed(evt);
            }
        });

        btn_nv_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_nv_xoa.setText("Xóa");
        btn_nv_xoa.setBorderPainted(false);
        btn_nv_xoa.setFocusPainted(false);
        btn_nv_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nv_xoaActionPerformed(evt);
            }
        });

        rd_nv_nam.setText("Nam");
        rd_nv_nam.setFocusPainted(false);

        rd_nv_nu.setText("Nữ");
        rd_nv_nu.setFocusPainted(false);

        jLabel57.setText("Mật khẩu");

        javax.swing.GroupLayout qlnv_nvLayout = new javax.swing.GroupLayout(qlnv_nv);
        qlnv_nv.setLayout(qlnv_nvLayout);
        qlnv_nvLayout.setHorizontalGroup(
            qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlnv_nvLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(qlnv_nvLayout.createSequentialGroup()
                        .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qlnv_nvLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(44, 44, 44)
                                .addComponent(txt_nv_ten, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qlnv_nvLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_nv_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_nv_timkiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_nv_them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(qlnv_nvLayout.createSequentialGroup()
                        .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(qlnv_nvLayout.createSequentialGroup()
                                .addComponent(rd_nv_nam)
                                .addGap(18, 18, 18)
                                .addComponent(rd_nv_nu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(qlnv_nvLayout.createSequentialGroup()
                                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_nv_sodt)
                                    .addComponent(txt_nv_diachi))
                                .addGap(77, 77, 77)))
                        .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_nv_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_nv_capnhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(qlnv_nvLayout.createSequentialGroup()
                        .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(qlnv_nvLayout.createSequentialGroup()
                                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel57))
                                .addGap(18, 18, 18)
                                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cb_nv_chucvu, 0, 165, Short.MAX_VALUE)
                                    .addComponent(txt_nv_matkhau)))
                            .addGroup(qlnv_nvLayout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(18, 18, 18)
                                .addComponent(cb_nv_cuahang, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel31))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        qlnv_nvLayout.setVerticalGroup(
            qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlnv_nvLayout.createSequentialGroup()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txt_nv_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nv_them))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txt_nv_ten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nv_timkiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(btn_nv_capnhat)
                    .addComponent(rd_nv_nam)
                    .addComponent(rd_nv_nu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txt_nv_diachi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nv_xoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txt_nv_sodt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(cb_nv_cuahang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(cb_nv_chucvu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_nvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(txt_nv_matkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );

        qlnv_cv.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức vụ"));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("CHỨC VỤ");

        tb_qlnv_chucvu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên"
            }
        ));
        tb_qlnv_chucvu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlnv_chucvuMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tb_qlnv_chucvu);

        txt_cv_ten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_cv_ma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel34.setText("Mã");

        jLabel35.setText("Tên");

        btn_cv_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_cv_xoa.setText("Xóa");
        btn_cv_xoa.setBorderPainted(false);
        btn_cv_xoa.setFocusPainted(false);
        btn_cv_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cv_xoaActionPerformed(evt);
            }
        });

        btn_cv_timkiem.setBackground(new java.awt.Color(204, 204, 204));
        btn_cv_timkiem.setText("Tìm kiếm");
        btn_cv_timkiem.setBorderPainted(false);
        btn_cv_timkiem.setFocusPainted(false);
        btn_cv_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cv_timkiemActionPerformed(evt);
            }
        });

        btn_cv_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_cv_them.setText("Thêm");
        btn_cv_them.setBorderPainted(false);
        btn_cv_them.setFocusPainted(false);
        btn_cv_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cv_themActionPerformed(evt);
            }
        });

        btn_cv_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_cv_capnhat.setText("Cập nhật");
        btn_cv_capnhat.setBorderPainted(false);
        btn_cv_capnhat.setFocusPainted(false);
        btn_cv_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cv_capnhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout qlnv_cvLayout = new javax.swing.GroupLayout(qlnv_cv);
        qlnv_cv.setLayout(qlnv_cvLayout);
        qlnv_cvLayout.setHorizontalGroup(
            qlnv_cvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlnv_cvLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlnv_cvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlnv_cvLayout.createSequentialGroup()
                        .addGroup(qlnv_cvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(qlnv_cvLayout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_cv_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(qlnv_cvLayout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addGap(29, 29, 29)
                                .addComponent(txt_cv_ten)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(qlnv_cvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_cv_capnhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_cv_timkiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_cv_them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_cv_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        qlnv_cvLayout.setVerticalGroup(
            qlnv_cvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlnv_cvLayout.createSequentialGroup()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlnv_cvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(qlnv_cvLayout.createSequentialGroup()
                        .addGroup(qlnv_cvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txt_cv_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(qlnv_cvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(txt_cv_ten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(qlnv_cvLayout.createSequentialGroup()
                        .addComponent(btn_cv_them)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cv_timkiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cv_capnhat)))
                .addGap(7, 7, 7)
                .addComponent(btn_cv_xoa)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_nhanvienLayout = new javax.swing.GroupLayout(pn_nhanvien);
        pn_nhanvien.setLayout(pn_nhanvienLayout);
        pn_nhanvienLayout.setHorizontalGroup(
            pn_nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_nhanvienLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(qlnv_nv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(qlnv_cv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        pn_nhanvienLayout.setVerticalGroup(
            pn_nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_nhanvienLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pn_nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(qlnv_cv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qlnv_nv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pn_main.add(pn_nhanvien, "card5");

        qlch_kh.setBorder(javax.swing.BorderFactory.createTitledBorder("Khách hàng"));
        qlch_kh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qlch_khMouseClicked(evt);
            }
        });

        tb_qlch_khachhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên", "Địa chỉ", "Số điện thoại"
            }
        ));
        tb_qlch_khachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlch_khachhangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tb_qlch_khachhang);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("KHÁCH HÀNG");

        jLabel16.setText("Mã");

        jLabel17.setText("Tên");

        jLabel18.setText("Tên đệm");

        jLabel19.setText("Họ");

        jLabel20.setText("Ngày sinh");

        jLabel21.setText("SDT");

        jLabel22.setText("Địa chỉ");

        jLabel23.setText("Thành phố");

        jLabel24.setText("Quốc gia");

        cb_kh_tp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cần Thơ", "Cao Bằng", "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "TP Hồ Chí Minh", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái" }));

        txt_kh_quocgia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_kh_diachi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_kh_ma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_kh_ten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_kh_tendem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_kh_ho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_kh_ngaysinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_kh_sdt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btn_kh_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_kh_them.setText("Thêm");
        btn_kh_them.setBorderPainted(false);
        btn_kh_them.setFocusPainted(false);
        btn_kh_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kh_themActionPerformed(evt);
            }
        });

        btn_kh_timkiem.setBackground(new java.awt.Color(204, 204, 204));
        btn_kh_timkiem.setText("Tìm kiếm");
        btn_kh_timkiem.setBorderPainted(false);
        btn_kh_timkiem.setFocusPainted(false);
        btn_kh_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kh_timkiemActionPerformed(evt);
            }
        });

        btn_kh_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_kh_capnhat.setText("Cập nhật");
        btn_kh_capnhat.setBorderPainted(false);
        btn_kh_capnhat.setFocusPainted(false);
        btn_kh_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kh_capnhatActionPerformed(evt);
            }
        });

        btn_kh_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_kh_xoa.setText("Xóa");
        btn_kh_xoa.setBorderPainted(false);
        btn_kh_xoa.setFocusPainted(false);
        btn_kh_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kh_xoaActionPerformed(evt);
            }
        });

        jLabel58.setText("Mật khẩu");

        javax.swing.GroupLayout qlch_khLayout = new javax.swing.GroupLayout(qlch_kh);
        qlch_kh.setLayout(qlch_khLayout);
        qlch_khLayout.setHorizontalGroup(
            qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlch_khLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addGroup(qlch_khLayout.createSequentialGroup()
                        .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlch_khLayout.createSequentialGroup()
                                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel58))
                                .addGap(18, 18, 18)
                                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_kh_matkhau)
                                    .addComponent(txt_kh_quocgia)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlch_khLayout.createSequentialGroup()
                                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_kh_tp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_kh_sdt)
                                    .addComponent(txt_kh_diachi)
                                    .addComponent(txt_kh_ngaysinh)))
                            .addGroup(qlch_khLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(47, 47, 47)
                                .addComponent(txt_kh_ma))
                            .addGroup(qlch_khLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(45, 45, 45)
                                .addComponent(txt_kh_ten))
                            .addGroup(qlch_khLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_kh_tendem)
                                    .addComponent(txt_kh_ho)))
                            .addComponent(jLabel19))
                        .addGap(89, 89, 89)
                        .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_kh_timkiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_kh_them, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_kh_capnhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_kh_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        qlch_khLayout.setVerticalGroup(
            qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlch_khLayout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_kh_ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_kh_them))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txt_kh_ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_kh_timkiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txt_kh_tendem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_kh_capnhat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txt_kh_ho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_kh_xoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txt_kh_ngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txt_kh_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txt_kh_diachi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cb_kh_tp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txt_kh_quocgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(txt_kh_matkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );

        qlch_ch.setBorder(javax.swing.BorderFactory.createTitledBorder("Cừa hàng"));

        tb_qlch_cuahang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên", "Địa chỉ", "Thành phố", "Quốc gia"
            }
        ));
        tb_qlch_cuahang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlch_cuahangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tb_qlch_cuahang);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("CỬA HÀNG");

        jLabel13.setText("Mã");

        jLabel15.setText("Tên");

        jLabel36.setText("Địa chỉ");

        jLabel59.setText("Thành phố");

        jLabel60.setText("Quốc gia");

        txt_ch_ma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_ch_ten.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txt_ch_ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ch_tenActionPerformed(evt);
            }
        });

        txt_ch_diachi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        cb_ch_thanhpho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cần Thơ", "Cao Bằng", "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "TP Hồ Chí Minh", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái" }));

        txt_ch_quocgia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btn_ch_xoa.setBackground(new java.awt.Color(204, 204, 204));
        btn_ch_xoa.setText("Xóa");
        btn_ch_xoa.setBorderPainted(false);
        btn_ch_xoa.setFocusPainted(false);
        btn_ch_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ch_xoaActionPerformed(evt);
            }
        });

        btn_ch_them.setBackground(new java.awt.Color(204, 204, 204));
        btn_ch_them.setText("Thêm");
        btn_ch_them.setBorderPainted(false);
        btn_ch_them.setFocusPainted(false);
        btn_ch_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ch_themActionPerformed(evt);
            }
        });

        btn_ch_timkiem.setBackground(new java.awt.Color(204, 204, 204));
        btn_ch_timkiem.setText("Tìm kiếm");
        btn_ch_timkiem.setBorderPainted(false);
        btn_ch_timkiem.setFocusPainted(false);
        btn_ch_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ch_timkiemActionPerformed(evt);
            }
        });

        btn_ch_capnhat.setBackground(new java.awt.Color(204, 204, 204));
        btn_ch_capnhat.setText("Cập nhật");
        btn_ch_capnhat.setBorderPainted(false);
        btn_ch_capnhat.setFocusPainted(false);
        btn_ch_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ch_capnhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout qlch_chLayout = new javax.swing.GroupLayout(qlch_ch);
        qlch_ch.setLayout(qlch_chLayout);
        qlch_chLayout.setHorizontalGroup(
            qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qlch_chLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addGroup(qlch_chLayout.createSequentialGroup()
                        .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qlch_chLayout.createSequentialGroup()
                                .addComponent(jLabel60)
                                .addGap(26, 26, 26)
                                .addComponent(txt_ch_quocgia))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qlch_chLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_ch_ma))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qlch_chLayout.createSequentialGroup()
                                .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel59)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18)
                                .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cb_ch_thanhpho, 0, 217, Short.MAX_VALUE)
                                    .addComponent(txt_ch_diachi)
                                    .addComponent(txt_ch_ten))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_ch_timkiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ch_xoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ch_capnhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ch_them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        qlch_chLayout.setVerticalGroup(
            qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qlch_chLayout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(qlch_chLayout.createSequentialGroup()
                        .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_ch_ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ch_ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(txt_ch_diachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59)
                            .addComponent(cb_ch_thanhpho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(qlch_chLayout.createSequentialGroup()
                        .addComponent(btn_ch_them)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_ch_timkiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_ch_capnhat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_ch_xoa)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qlch_chLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(txt_ch_quocgia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_khLayout = new javax.swing.GroupLayout(pn_kh);
        pn_kh.setLayout(pn_khLayout);
        pn_khLayout.setHorizontalGroup(
            pn_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_khLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(qlch_kh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(qlch_ch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        pn_khLayout.setVerticalGroup(
            pn_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_khLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pn_khLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(qlch_kh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(qlch_ch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pn_main.add(pn_kh, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_navigation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_navigation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void spMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spMouseClicked
        pn_qlsp.setVisible(true);
        pn_hoadon.setVisible(false);
        pn_hello.setVisible(false);
    }//GEN-LAST:event_spMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        System.exit(0);
    }//GEN-LAST:event_logoutMouseClicked

    private void qlhdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qlhdMouseClicked
        pn_hoadon.setVisible(true);
        pn_qlsp.setVisible(false);
        pn_hello.setVisible(false);
    }//GEN-LAST:event_qlhdMouseClicked

    private void tb_qlsp_ttspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlsp_ttspMouseClicked
        showDetailQLSP();
    }//GEN-LAST:event_tb_qlsp_ttspMouseClicked

    private void btn_ttsp_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttsp_xoaActionPerformed
        deleteSP();
    }//GEN-LAST:event_btn_ttsp_xoaActionPerformed

    private void btn_ttsp_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttsp_capnhatActionPerformed
        updateSP();
    }//GEN-LAST:event_btn_ttsp_capnhatActionPerformed

    private void btn_ttsp_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ttsp_themActionPerformed
        addSP();

    }//GEN-LAST:event_btn_ttsp_themActionPerformed
    private int checkFindCTSP = 0;
    private void tb_qlsp_chitietspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlsp_chitietspMouseClicked
        showDetailctsp();
    }//GEN-LAST:event_tb_qlsp_chitietspMouseClicked
///////////////////

    private boolean checkEmptyctsp() {
        if (txt_ctsp_giaban.getText().isBlank() || txt_ctsp_giaban.getText().isBlank()
                || txt_ctsp_mota.getText().isBlank() || txt_ctsp_nambh.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "can not empty");
            return false;
        }
        return true;
    }

    private boolean checkValidate() {
        Pattern num1 = Pattern.compile("^[0-9]+(\\.)[0-9]+$");
        boolean ok = true;
        if (txt_ctsp_giaban.getText().isBlank() || txt_ctsp_giaban.getText().isBlank()
                || txt_ctsp_mota.getText().isBlank() || txt_ctsp_nambh.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "can not empty");
            ok = false;
        } else if (!num1.matcher(txt_ctsp_giaban.getText()).find()) {
            ok = false;
            JOptionPane.showMessageDialog(rootPane, "sai định dạng");
        } else if (!num1.matcher(txt_ctsp_gianhap.getText()).find()) {
            ok = false;
            JOptionPane.showMessageDialog(rootPane, "sai định dạng");
        }

        return ok;
    }

    public boolean checkNumber(String mark) {
        Pattern regexDouble = Pattern.compile("^[0-9]+(\\.)[0-9]+$");
        if (!regexDouble.matcher(mark).find()) {
            JOptionPane.showMessageDialog(this, "khong dung dinh dang");
            return false;
        } else {
            return true;
        }
    }


    private void btn_ctsp_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ctsp_themActionPerformed

        if (checkEmptyctsp()) {
            ChiTietSP ctsp = new ChiTietSP();
            if (checkEmptyctsp()) {
                ctsp.setSanPham((SanPham) cb_ctsp_sp.getSelectedItem());
                ctsp.setNsx((NSX) cb_ctsp_nsx.getSelectedItem());
                ctsp.setMauSac((MauSac) cb_ctsp_mausac.getSelectedItem());
                ctsp.setDongSP((DongSP) cb_ctsp_dongsp.getSelectedItem());
                ctsp.setNamBH(Integer.parseInt(txt_ctsp_nambh.getText()));
                ctsp.setSoLuongTon(Integer.parseInt(txt_SLton.getText()));
                ctsp.setGiaNhap(Double.parseDouble(txt_ctsp_gianhap.getText()));
                ctsp.setGiaBan(Double.parseDouble(txt_ctsp_giaban.getText()));
                ctsp.setMoTa(txt_ctsp_mota.getText());
                iChiTietSPService.add(ctsp);
                JOptionPane.showMessageDialog(this, "Save Success !");
                lctsp = iChiTietSPService.getAll();
                load_QLSP_CTSP(lctsp);
            }

        }


    }//GEN-LAST:event_btn_ctsp_themActionPerformed

    private void btn_ctsp_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ctsp_capnhatActionPerformed

        int row = tb_qlsp_chitietsp.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            if (checkEmptyctsp()) {
                int index = tb_qlsp_chitietsp.getSelectedRow();
                lctsp = iChiTietSPService.getAll();
                ChiTietSP ctsp = lctsp.get(index);
                ctsp.setSanPham((SanPham) cb_ctsp_sp.getSelectedItem());
                ctsp.setNsx((NSX) cb_ctsp_nsx.getSelectedItem());
                ctsp.setMauSac((MauSac) cb_ctsp_mausac.getSelectedItem());
                ctsp.setDongSP((DongSP) cb_ctsp_dongsp.getSelectedItem());
                ctsp.setNamBH(Integer.parseInt(txt_ctsp_nambh.getText()));
                ctsp.setSoLuongTon(Integer.parseInt(txt_SLton.getText()));
                ctsp.setGiaNhap(Double.parseDouble(txt_ctsp_gianhap.getText()));
                ctsp.setGiaBan(Double.parseDouble(txt_ctsp_giaban.getText()));
                ctsp.setMoTa(txt_ctsp_mota.getText());
                iChiTietSPService.update(ctsp, ctsp.getId());

                JOptionPane.showMessageDialog(this, " update success");
                load_QLSP_CTSP(iChiTietSPService.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "update Fail");
            }
        }


    }//GEN-LAST:event_btn_ctsp_capnhatActionPerformed

    private void btn_ctsp_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ctsp_xoaActionPerformed
        int row = tb_qlsp_chitietsp.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Chắc chắn xoá?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                lctsp = iChiTietSPService.getAll();
                ChiTietSP ctsp = lctsp.get(row);
                int rowAffected = iChiTietSPService.delete(ctsp.getId());
                if (rowAffected > 0) {
                    JOptionPane.showMessageDialog(this, "delete success");
                    cleanForm();
                    load_QLSP_CTSP(lctsp);
                    LoadTable(spvw);
                } else {
                    JOptionPane.showMessageDialog(this, "delete Fail");

                }
            }

        }
    }//GEN-LAST:event_btn_ctsp_xoaActionPerformed

    private void tb_msMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_msMouseClicked

        int row = tb_ms.getSelectedRow();
        txt_ms_ma.setText((String) tb_ms.getValueAt(row, 0));
        txt_ms_ten.setText((String) tb_ms.getValueAt(row, 1));

    }//GEN-LAST:event_tb_msMouseClicked

    private void btn_ms_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ms_xoaActionPerformed
        deleteMauSac();
    }//GEN-LAST:event_btn_ms_xoaActionPerformed

    private void btn_ms_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ms_capnhatActionPerformed
        updateMauSac();
    }//GEN-LAST:event_btn_ms_capnhatActionPerformed

    private void btn_ms_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ms_themActionPerformed
        addMauSac();
    }//GEN-LAST:event_btn_ms_themActionPerformed

    private void tb_nsxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_nsxMouseClicked
        int row = tb_nsx.getSelectedRow();
        txt_nsx_ma.setText((String) tb_nsx.getValueAt(row, 0));
        txt_nsx_ten.setText((String) tb_nsx.getValueAt(row, 1));
    }//GEN-LAST:event_tb_nsxMouseClicked

    private void btn_nsx_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nsx_xoaActionPerformed
        deleteNSX();
    }//GEN-LAST:event_btn_nsx_xoaActionPerformed

    private void btn_nsx_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nsx_capnhatActionPerformed
        updateNSX();
    }//GEN-LAST:event_btn_nsx_capnhatActionPerformed

    private void btn_nsx_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nsx_themActionPerformed
        addNSX();
    }//GEN-LAST:event_btn_nsx_themActionPerformed

    private void tb_dspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_dspMouseClicked
        int row = tb_dsp.getSelectedRow();
        txt_dsp_ma.setText((String) tb_dsp.getValueAt(row, 0));
        txt_dsp_ten.setText((String) tb_dsp.getValueAt(row, 1));
    }//GEN-LAST:event_tb_dspMouseClicked

    private void btn_dsp_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dsp_xoaActionPerformed
        deleteDongSP();
    }//GEN-LAST:event_btn_dsp_xoaActionPerformed

    private void btn_dsp_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dsp_capnhatActionPerformed
        updateDongSP();
    }//GEN-LAST:event_btn_dsp_capnhatActionPerformed

    private void btn_dsp_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dsp_themActionPerformed
        addDongSP();
    }//GEN-LAST:event_btn_dsp_themActionPerformed

    private void txt_tk_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tk_spActionPerformed

    }//GEN-LAST:event_txt_tk_spActionPerformed

    private void txt_tk_spKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tk_spKeyReleased
        lsp = iSanPhamService.findByName(txt_tk_sp.getText());
        load_QLSP_SP(lsp);
    }//GEN-LAST:event_txt_tk_spKeyReleased

    private void txt_tk_msKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tk_msKeyReleased
        lms = iMauSacService.findByName(txt_tk_ms.getText());
        load_QLSP_MS(lms);
    }//GEN-LAST:event_txt_tk_msKeyReleased

    private void txt_tk_nsxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tk_nsxKeyReleased
        lnsx = iNsxService.findByName(txt_tk_nsx.getText());
        load_QLSP_NSX(lnsx);
    }//GEN-LAST:event_txt_tk_nsxKeyReleased

    private void txt_tk_dspKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tk_dspKeyReleased
        ldsp = iDongSPService.findByName(txt_tk_dsp.getText());
        load_QLSP_DongSP(ldsp);
    }//GEN-LAST:event_txt_tk_dspKeyReleased

    private void txt_tk_ctspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tk_ctspActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tk_ctspActionPerformed

    private void txt_tk_ctspKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tk_ctspKeyReleased
        lctsp = iChiTietSPService.findByName(txt_tk_ctsp.getText());
        load_QLSP_CTSP(lctsp);
    }//GEN-LAST:event_txt_tk_ctspKeyReleased

    private void cb_ctsp_dongspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ctsp_dongspActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ctsp_dongspActionPerformed

    private void cb_ctsp_spItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_ctsp_spItemStateChanged
        for (SanPham sp : iSanPhamService.getAll()) {
            if (sp.getTen().equals(comboSanPham.getSelectedItem().toString())) {
                lblhienthi.setText(sp.getMa());
                break;
            }

        }
    }//GEN-LAST:event_cb_ctsp_spItemStateChanged

    private void cb_ctsp_spActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ctsp_spActionPerformed

    }//GEN-LAST:event_cb_ctsp_spActionPerformed

    private void tb_qlsp__qlhdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlsp__qlhdMouseClicked
        ChiTietHoaDonViewModel chiTietHoaDonViewModel = new ChiTietHoaDonViewModel();
        int row = tb_qlsp__qlhd.getSelectedRow();
        chiTietHoaDonViewModel.setTenSp((String) tb_qlsp__qlhd.getValueAt(row, 0));
        chiTietHoaDonViewModel.setSoLuong(1);
        chiTietHoaDonViewModel.setDonGia((Double) tb_qlsp__qlhd.getValueAt(row, 3));
        cthdvm.add(chiTietHoaDonViewModel);
        addTableGioHang(cthdvm);
    }//GEN-LAST:event_tb_qlsp__qlhdMouseClicked

    private void txt_sanpham_qlhdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sanpham_qlhdKeyReleased

        spvw = ispviewmodelService.findByName(txt_sanpham_qlhd.getText());
        LoadTable(ispviewmodelService.getAll());
    }//GEN-LAST:event_txt_sanpham_qlhdKeyReleased

    private void tb_hoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_hoadonMouseClicked

        load_HD();
    }//GEN-LAST:event_tb_hoadonMouseClicked

    private void tb_qlnv_nhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlnv_nhanvienMouseClicked

    }//GEN-LAST:event_tb_qlnv_nhanvienMouseClicked

    private void btn_nv_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nv_themActionPerformed

    }//GEN-LAST:event_btn_nv_themActionPerformed

    private void btn_nv_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nv_timkiemActionPerformed

    }//GEN-LAST:event_btn_nv_timkiemActionPerformed

    private void btn_nv_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nv_capnhatActionPerformed

    }//GEN-LAST:event_btn_nv_capnhatActionPerformed

    private void btn_nv_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nv_xoaActionPerformed

    }//GEN-LAST:event_btn_nv_xoaActionPerformed

    private void tb_qlnv_chucvuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlnv_chucvuMouseClicked
        int row = tb_qlnv_chucvu.getSelectedRow();
        txt_cv_ma.setText((String) tb_qlnv_chucvu.getValueAt(row, 0));
        txt_cv_ten.setText((String) tb_qlnv_chucvu.getValueAt(row, 1));
    }//GEN-LAST:event_tb_qlnv_chucvuMouseClicked

    private void btn_cv_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cv_xoaActionPerformed

    }//GEN-LAST:event_btn_cv_xoaActionPerformed

    private void btn_cv_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cv_timkiemActionPerformed

    }//GEN-LAST:event_btn_cv_timkiemActionPerformed

    private void btn_cv_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cv_themActionPerformed

    }//GEN-LAST:event_btn_cv_themActionPerformed

    private void btn_cv_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cv_capnhatActionPerformed

    }//GEN-LAST:event_btn_cv_capnhatActionPerformed

    private void tb_qlch_khachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlch_khachhangMouseClicked

    }//GEN-LAST:event_tb_qlch_khachhangMouseClicked

    private void btn_kh_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kh_themActionPerformed

    }//GEN-LAST:event_btn_kh_themActionPerformed

    private void btn_kh_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kh_timkiemActionPerformed

    }//GEN-LAST:event_btn_kh_timkiemActionPerformed

    private void btn_kh_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kh_capnhatActionPerformed

    }//GEN-LAST:event_btn_kh_capnhatActionPerformed

    private void btn_kh_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kh_xoaActionPerformed

    }//GEN-LAST:event_btn_kh_xoaActionPerformed

    private void tb_qlch_cuahangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlch_cuahangMouseClicked
        int row = tb_qlch_cuahang.getSelectedRow();
        txt_ch_ma.setText((String) tb_qlch_cuahang.getValueAt(row, 0));
        txt_ch_ten.setText((String) tb_qlch_cuahang.getValueAt(row, 1));
        txt_ch_diachi.setText((String) tb_qlch_cuahang.getValueAt(row, 2));
        cb_ch_thanhpho.setSelectedItem((String) tb_qlch_cuahang.getValueAt(row, 3));
        txt_ch_quocgia.setText((String) tb_qlch_cuahang.getValueAt(row, 4));
    }//GEN-LAST:event_tb_qlch_cuahangMouseClicked

    private void btn_ch_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ch_xoaActionPerformed

    }//GEN-LAST:event_btn_ch_xoaActionPerformed

    private void btn_ch_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ch_themActionPerformed

    }//GEN-LAST:event_btn_ch_themActionPerformed

    private void btn_ch_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ch_timkiemActionPerformed

    }//GEN-LAST:event_btn_ch_timkiemActionPerformed

    private void btn_ch_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ch_capnhatActionPerformed

    }//GEN-LAST:event_btn_ch_capnhatActionPerformed

    private void txt_ch_tenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ch_tenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ch_tenActionPerformed

    private void pn_nvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pn_nvMouseClicked


    }//GEN-LAST:event_pn_nvMouseClicked

    private void pn_khachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pn_khachhangMouseClicked

        pn_khachhang.setVisible(true);
    }//GEN-LAST:event_pn_khachhangMouseClicked

    private void rd_chottActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_chottActionPerformed
        defaultTableModel = (DefaultTableModel) tb_hoadon.getModel();
        defaultTableModel.setRowCount(0);
        int stthd = 1;

        for (HoaDon x : iHoaDonService.getAll()) {
            if (x.getTinhTrang() == 0) {
                defaultTableModel.addRow(new Object[]{
                    stthd++, x.getMa(), x.getNgayTao(), x.getNhanVien().getTen(), x.getStatus()});
            }

        }

    }//GEN-LAST:event_rd_chottActionPerformed

    private void rd_tatcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_tatcaActionPerformed
        load_HD();
    }//GEN-LAST:event_rd_tatcaActionPerformed

    private void rd_dahuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_dahuyActionPerformed
        defaultTableModel = (DefaultTableModel) tb_hoadon.getModel();
        defaultTableModel.setRowCount(0);
        int stthd = 1;

        for (HoaDon x : iHoaDonService.getAll()) {
            if (x.getTinhTrang() == 2) {
                defaultTableModel.addRow(new Object[]{
                    stthd++, x.getMa(), x.getNgayTao(), x.getNhanVien().getTen(), x.getStatus()});
            }

        }
    }//GEN-LAST:event_rd_dahuyActionPerformed

    private void rd_dattActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rd_dattActionPerformed
        defaultTableModel = (DefaultTableModel) tb_hoadon.getModel();
        defaultTableModel.setRowCount(0);
        int stthd = 1;

        for (HoaDon x : iHoaDonService.getAll()) {
            if (x.getTinhTrang() == 1) {
                defaultTableModel.addRow(new Object[]{
                    stthd++, x.getMa(), x.getNgayTao(), x.getNhanVien().getTen(), x.getStatus()});
            }

        }
    }//GEN-LAST:event_rd_dattActionPerformed

    private void cb_ctsp_nsxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_ctsp_nsxItemStateChanged
        for (NSX nsx : iNsxService.getAll()) {
            if (nsx.getTen().equals(comboNSX.getSelectedItem().toString())) {
                lblhienthi1.setText(nsx.getMa());
                break;
            }

        }
    }//GEN-LAST:event_cb_ctsp_nsxItemStateChanged

    private void cb_ctsp_mausacItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_ctsp_mausacItemStateChanged
        for (MauSac ms : iMauSacService.getAll()) {
            if (ms.getTen().equals(comboMauSac.getSelectedItem().toString())) {
                lblhienthi2.setText(ms.getMa());
                break;
            }

        }
    }//GEN-LAST:event_cb_ctsp_mausacItemStateChanged

    private void cb_ctsp_dongspItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_ctsp_dongspItemStateChanged
        for (DongSP dongsp : iDongSPService.getAll()) {
            if (dongsp.getTen().equals(comboDongSP.getSelectedItem().toString())) {
                lblhienthi3.setText(dongsp.getMa());
                break;
            }

        }
    }//GEN-LAST:event_cb_ctsp_dongspItemStateChanged

    private void btn_taohdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taohdActionPerformed
        iHoaDonService.add();
        loadData();
    }//GEN-LAST:event_btn_taohdActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        pn_hello.setVisible(false);
        pn_hoadon.setVisible(false);
        pn_kh.setVisible(false);
        pn_nhanvien.setVisible(true);
        pn_qlsp.setVisible(false);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void qlch_khMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qlch_khMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_qlch_khMouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        pn_hello.setVisible(false);
        pn_hoadon.setVisible(false);
        pn_kh.setVisible(true);
        pn_nhanvien.setVisible(false);
        pn_qlsp.setVisible(false);                            
        
    }//GEN-LAST:event_jLabel11MouseClicked

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QLBH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLBH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLBH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLBH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLBH().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ch_capnhat;
    private javax.swing.JButton btn_ch_them;
    private javax.swing.JButton btn_ch_timkiem;
    private javax.swing.JButton btn_ch_xoa;
    private javax.swing.JButton btn_ctsp_capnhat;
    private javax.swing.JButton btn_ctsp_them;
    private javax.swing.JButton btn_ctsp_xoa;
    private javax.swing.JButton btn_cv_capnhat;
    private javax.swing.JButton btn_cv_them;
    private javax.swing.JButton btn_cv_timkiem;
    private javax.swing.JButton btn_cv_xoa;
    private javax.swing.JButton btn_dsp_capnhat;
    private javax.swing.JButton btn_dsp_them;
    private javax.swing.JButton btn_dsp_xoa;
    private javax.swing.JButton btn_kh_capnhat;
    private javax.swing.JButton btn_kh_them;
    private javax.swing.JButton btn_kh_timkiem;
    private javax.swing.JButton btn_kh_xoa;
    private javax.swing.JButton btn_ms_capnhat;
    private javax.swing.JButton btn_ms_them;
    private javax.swing.JButton btn_ms_xoa;
    private javax.swing.JButton btn_nsx_capnhat;
    private javax.swing.JButton btn_nsx_them;
    private javax.swing.JButton btn_nsx_xoa;
    private javax.swing.JButton btn_nv_capnhat;
    private javax.swing.JButton btn_nv_them;
    private javax.swing.JButton btn_nv_timkiem;
    private javax.swing.JButton btn_nv_xoa;
    private javax.swing.JButton btn_taohd;
    private javax.swing.JButton btn_thanhtoan;
    private javax.swing.JButton btn_ttsp_capnhat;
    private javax.swing.JButton btn_ttsp_them;
    private javax.swing.JButton btn_ttsp_xoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cb_ch_thanhpho;
    private javax.swing.JComboBox<String> cb_ctsp_dongsp;
    private javax.swing.JComboBox<String> cb_ctsp_mausac;
    private javax.swing.JComboBox<String> cb_ctsp_nsx;
    private javax.swing.JComboBox<String> cb_ctsp_sp;
    private javax.swing.JComboBox<String> cb_kh_tp;
    private javax.swing.JComboBox<String> cb_nv_chucvu;
    private javax.swing.JComboBox<String> cb_nv_cuahang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_title_gener;
    private javax.swing.JLabel lbl_title_gener1;
    private javax.swing.JLabel lbl_title_gener2;
    private javax.swing.JLabel lbl_title_gener3;
    private javax.swing.JLabel lblhienthi;
    private javax.swing.JLabel lblhienthi1;
    private javax.swing.JLabel lblhienthi2;
    private javax.swing.JLabel lblhienthi3;
    private javax.swing.JPanel logout;
    private javax.swing.JPanel pn_hello;
    private javax.swing.JPanel pn_hoadon;
    private javax.swing.JPanel pn_kh;
    private javax.swing.JPanel pn_khachhang;
    private javax.swing.JPanel pn_main;
    private javax.swing.JPanel pn_navigation;
    private javax.swing.JPanel pn_nhanvien;
    private javax.swing.JPanel pn_nv;
    private javax.swing.JPanel pn_qlsp;
    private javax.swing.JPanel qlch_ch;
    private javax.swing.JPanel qlch_kh;
    private javax.swing.JPanel qlhd;
    private javax.swing.JPanel qlnv_cv;
    private javax.swing.JPanel qlnv_nv;
    private javax.swing.JPanel qlsp_ctsp;
    private javax.swing.JPanel qlsp_sp;
    private javax.swing.JPanel qlsp_sp1;
    private javax.swing.JPanel qlsp_sp2;
    private javax.swing.JPanel qlsp_sp3;
    private javax.swing.JRadioButton rd_chott;
    private javax.swing.JRadioButton rd_dahuy;
    private javax.swing.JRadioButton rd_datt;
    private javax.swing.JRadioButton rd_nv_nam;
    private javax.swing.JRadioButton rd_nv_nu;
    private javax.swing.JRadioButton rd_tatca;
    private javax.swing.JPanel sp;
    private javax.swing.JTable tb_dsp;
    private javax.swing.JTable tb_giohang;
    private javax.swing.JTable tb_hoadon;
    private javax.swing.JTable tb_ms;
    private javax.swing.JTable tb_nsx;
    private javax.swing.JTable tb_qlch_cuahang;
    private javax.swing.JTable tb_qlch_khachhang;
    private javax.swing.JTable tb_qlnv_chucvu;
    private javax.swing.JTable tb_qlnv_nhanvien;
    private javax.swing.JTable tb_qlsp__qlhd;
    private javax.swing.JTable tb_qlsp_chitietsp;
    private javax.swing.JTable tb_qlsp_ttsp;
    private javax.swing.JTextField txt_SLton;
    private javax.swing.JTextField txt_ch_diachi;
    private javax.swing.JTextField txt_ch_ma;
    private javax.swing.JTextField txt_ch_quocgia;
    private javax.swing.JTextField txt_ch_ten;
    private javax.swing.JTextField txt_ctsp_giaban;
    private javax.swing.JTextField txt_ctsp_gianhap;
    private javax.swing.JTextField txt_ctsp_mota;
    private javax.swing.JTextField txt_ctsp_nambh;
    private javax.swing.JTextField txt_cv_ma;
    private javax.swing.JTextField txt_cv_ten;
    private javax.swing.JTextField txt_dsp_ma;
    private javax.swing.JTextField txt_dsp_ten;
    private javax.swing.JTextField txt_kh_diachi;
    private javax.swing.JTextField txt_kh_ho;
    private javax.swing.JTextField txt_kh_ma;
    private javax.swing.JTextField txt_kh_matkhau;
    private javax.swing.JTextField txt_kh_ngaysinh;
    private javax.swing.JTextField txt_kh_quocgia;
    private javax.swing.JTextField txt_kh_sdt;
    private javax.swing.JTextField txt_kh_ten;
    private javax.swing.JTextField txt_kh_tendem;
    private javax.swing.JTextField txt_mahd;
    private javax.swing.JTextField txt_ms_ma;
    private javax.swing.JTextField txt_ms_ten;
    private javax.swing.JTextField txt_ngaytao;
    private javax.swing.JTextField txt_nsx_ma;
    private javax.swing.JTextField txt_nsx_ten;
    private javax.swing.JTextField txt_nv_diachi;
    private javax.swing.JTextField txt_nv_ma;
    private javax.swing.JTextField txt_nv_matkhau;
    private javax.swing.JTextField txt_nv_sodt;
    private javax.swing.JTextField txt_nv_ten;
    private javax.swing.JTextField txt_sanpham_qlhd;
    private javax.swing.JTextField txt_tennv;
    private javax.swing.JTextField txt_tienkhachdua;
    private javax.swing.JTextField txt_tienthua;
    private javax.swing.JTextField txt_tk_ctsp;
    private javax.swing.JTextField txt_tk_dsp;
    private javax.swing.JTextField txt_tk_ms;
    private javax.swing.JTextField txt_tk_nsx;
    private javax.swing.JTextField txt_tk_sp;
    private javax.swing.JTextField txt_tongtien;
    private javax.swing.JTextField txt_ttsp_ma;
    private javax.swing.JTextField txt_ttsp_ten;
    // End of variables declaration//GEN-END:variables
    private void cleanForm() {
        txt_ctsp_giaban.setText("");
        txt_ctsp_gianhap.setText("");
        txt_ctsp_mota.setText("");
        txt_ctsp_nambh.setText("");
        txt_dsp_ma.setText("");
        txt_dsp_ten.setText("");
        txt_mahd.setText("");
        txt_ms_ma.setText("");
        txt_ms_ten.setText("");
        txt_ngaytao.setText("");
        txt_nsx_ma.setText("");
        txt_nsx_ten.setText("");
        txt_sanpham_qlhd.setText("");
        txt_tennv.setText("");
        txt_tienkhachdua.setText("");
        txt_tienthua.setText("");
        txt_tongtien.setText("");
        txt_ttsp_ma.setText("");
        txt_ttsp_ten.setText("");

        txt_tk_nsx.setText("");
        txt_tk_ms.setText("");
        txt_tk_dsp.setText("");
        txt_tk_sp.setText("");

        cb_ctsp_dongsp.setSelectedItem(0);
        cb_ctsp_mausac.setSelectedItem(0);
        cb_ctsp_nsx.setSelectedItem(0);
        cb_ctsp_sp.setSelectedItem(0);
    }
}
