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
    private String GT;
    private String KhoiA;
    private String KhoiB;
    private String KhoiC;
    private String KhoiD;
    private String XuHuong;
    private String QD;
    private String TanTam;
    private String TuongTac;
    private String CoiMo;
    private String NangKhieu;
    private String UocMo;
    private String Job;

    public InfoModel() {
    }

    public InfoModel(String GT, String KhoiA, String KhoiB, String KhoiC, String KhoiD, String XuHuong, String QD, String TanTam, String TuongTac, String CoiMo, String NangKhieu, String UocMoCV, String Job) {
        this.GT = GT;
        this.KhoiA = KhoiA;
        this.KhoiB = KhoiB;
        this.KhoiC = KhoiC;
        this.KhoiD = KhoiD;
        this.XuHuong = XuHuong;
        this.QD = QD;
        this.TanTam = TanTam;
        this.TuongTac = TuongTac;
        this.CoiMo = CoiMo;
        this.NangKhieu = NangKhieu;
        this.UocMo = UocMoCV;
        this.Job = Job;
    }

    public String getGT() {
        return GT;
    }

    public void setGT(String GT) {
        this.GT = GT;
    }

    public String getKhoiA() {
        return KhoiA;
    }

    public void setKhoiA(String KhoiA) {
        this.KhoiA = KhoiA;
    }

    public String getKhoiB() {
        return KhoiB;
    }

    public void setKhoiB(String KhoiB) {
        this.KhoiB = KhoiB;
    }

    public String getKhoiC() {
        return KhoiC;
    }

    public void setKhoiC(String KhoiC) {
        this.KhoiC = KhoiC;
    }

    public String getKhoiD() {
        return KhoiD;
    }

    public void setKhoiD(String KhoiD) {
        this.KhoiD = KhoiD;
    }

    public String getXuHuong() {
        return XuHuong;
    }

    public void setXuHuong(String XuHuong) {
        this.XuHuong = XuHuong;
    }

    public String getQD() {
        return QD;
    }

    public void setQD(String QD) {
        this.QD = QD;
    }

    public String getTanTam() {
        return TanTam;
    }

    public void setTanTam(String TanTam) {
        this.TanTam = TanTam;
    }

    public String getTuongTac() {
        return TuongTac;
    }

    public void setTuongTac(String TuongTac) {
        this.TuongTac = TuongTac;
    }

    public String getCoiMo() {
        return CoiMo;
    }

    public void setCoiMo(String CoiMo) {
        this.CoiMo = CoiMo;
    }

    public String getNangKhieu() {
        return NangKhieu;
    }

    public void setNangKhieu(String NangKhieu) {
        this.NangKhieu = NangKhieu;
    }

    public String getUocMoCV() {
        return UocMo;
    }

    public void setUocMoCV(String UocMoCV) {
        this.UocMo = UocMoCV;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String Job) {
        this.Job = Job;
    }

}
