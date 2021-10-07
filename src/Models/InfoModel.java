/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author BenNguyen
 */
public class InfoModel {

    public InfoModel() {
    }

    public InfoModel(String GioiTinh, String TinhCach, String Diem) {
        this.GioiTinh = GioiTinh;
        this.TinhCach = TinhCach;
        this.Diem = Diem;
    }

    private String GioiTinh;
    private String TinhCach;
    private String Diem;

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getTinhCach() {
        return TinhCach;
    }

    public void setTinhCach(String TinhCach) {
        this.TinhCach = TinhCach;
    }

    public String getDiem() {
        return Diem;
    }

    public void setDiem(String Diem) {
        this.Diem = Diem;
    }

}
