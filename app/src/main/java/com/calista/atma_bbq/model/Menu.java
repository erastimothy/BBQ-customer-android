package com.calista.atma_bbq.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Menu implements Serializable {
    @SerializedName("id_menu")
    private int id_menu;

    @SerializedName("nama_menu")
    private String nama_menu;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("harga_menu")
    private double harga_menu;

    @SerializedName("unit")
    private String unit; //plate

    @SerializedName("gambar_menu")
    private String gambar_menu;

    @SerializedName("ukuran_penyajian")
    private int ukuran_penyajian;

    @SerializedName("unit_bahan")
    private String unit_bahan; //gram

    @SerializedName("sisa_stok")
    private int sisa_stok;

    public Menu(int id_menu, String nama, String deskripsi, double harga_menu, String unit, String gambar_menu, int ukuran_penyajian, String unit_bahan, int sisa_stok) {
        this.id_menu = id_menu;
        this.nama_menu = nama;
        this.deskripsi = deskripsi;
        this.harga_menu = harga_menu;
        this.unit = unit;
        this.gambar_menu = gambar_menu;
        this.ukuran_penyajian = ukuran_penyajian;
        this.unit_bahan = unit_bahan;
        this.sisa_stok = sisa_stok;
    }

    public int getSisa_stok() {
        return sisa_stok;
    }

    public void setSisa_stok(int sisa_stok) {
        this.sisa_stok = sisa_stok;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama) {
        this.nama_menu = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getHarga_menu() {
        return harga_menu;
    }

    public void setHarga_menu(double harga_menu) {
        this.harga_menu = harga_menu;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGambar_menu() {
        return gambar_menu;
    }

    public void setGambar_menu(String gambar_menu) {
        this.gambar_menu = gambar_menu;
    }

    public int getUkuran_penyajian() {
        return ukuran_penyajian;
    }

    public void setUkuran_penyajian(int ukuran_penyajian) {
        this.ukuran_penyajian = ukuran_penyajian;
    }

    public String getUnit_bahan() {
        return unit_bahan;
    }

    public void setUnit_bahan(String unit_bahan) {
        this.unit_bahan = unit_bahan;
    }
}
