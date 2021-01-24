package com.dreamernguyen.demoyokafo;

import android.net.Uri;

public class TinNhan {
    private String idNguoiGui;
    private String idNguoiNhan;
    private String noiDung;
    private Uri linkAnh;

    public TinNhan(String idNguoiGui, String idNguoiNhan, String noiDung) {
        this.idNguoiGui = idNguoiGui;
        this.idNguoiNhan = idNguoiNhan;
        this.noiDung = noiDung;
    }

    public String getIdNguoiGui() {
        return idNguoiGui;
    }

    public void setIdNguoiGui(String idNguoiGui) {
        this.idNguoiGui = idNguoiGui;
    }

    public String getIdNguoiNhan() {
        return idNguoiNhan;
    }

    public void setIdNguoiNhan(String idNguoiNhan) {
        this.idNguoiNhan = idNguoiNhan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Uri getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(Uri linkAnh) {
        this.linkAnh = linkAnh;
    }
}
