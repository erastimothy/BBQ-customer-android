package com.calista.atma_bbq.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Detail_Pesanan implements Serializable {
    @SerializedName("id_detail")
    private int id_detail;

    @SerializedName("kuantitas")
    private int kuantitas;

    @SerializedName("subtotal_kuantitas")
    private double subtotal_kuantitas;

    @SerializedName("status_pesanan")
    private String status_pesanan;

    @SerializedName("menu")
    private Menu menu;

    public Detail_Pesanan(int id_detail, int kuantitas, double subtotal_kuantitas, String status_pesanan, Menu menu) {
        this.id_detail = id_detail;
        this.kuantitas = kuantitas;
        this.subtotal_kuantitas = subtotal_kuantitas;
        this.status_pesanan = status_pesanan;
        this.menu = menu;
    }

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public double getSubtotal_kuantitas() {
        return subtotal_kuantitas;
    }

    public void setSubtotal_kuantitas(double subtotal_kuantitas) {
        this.subtotal_kuantitas = subtotal_kuantitas;
    }

    public String getStatus_pesanan() {
        return status_pesanan;
    }

    public void setStatus_pesanan(String status_pesanan) {
        this.status_pesanan = status_pesanan;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
