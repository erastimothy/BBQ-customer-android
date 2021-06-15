package com.calista.atma_bbq.response;

import com.calista.atma_bbq.model.Detail_Pesanan;
import com.calista.atma_bbq.model.Pembayaran;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Detail_PesananResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private List<Detail_Pesanan> detail_pesanans = null;

    @SerializedName("message")
    @Expose
    private String message;

    public List<Detail_Pesanan> getDetail_pesanans() {return detail_pesanans;}

    public String getMessage() {return message;}

    public void setDetail_pesanans(List<Detail_Pesanan> detail_pesanans){this.detail_pesanans = detail_pesanans;}

    public void setMessage(String message){this.message = message;}
}
