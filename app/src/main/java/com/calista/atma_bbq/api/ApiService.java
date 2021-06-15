package com.calista.atma_bbq.api;

import com.calista.atma_bbq.response.Detail_PesananResponse;
import com.calista.atma_bbq.response.MenuResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("menu")
    Call<MenuResponse> getAllMenu ();
    /*
        {
            "message": "Retrieve All Success",
            "data": [
                        {
                            "id_menu": 1,
                            "nama_menu": "Beef Short Plate",
                            "deskripsi": "Potongan daging sapi dari bagian otot perut, bentuknya panjang dan\ndatar",
                            "unit": "Plate",
                            "harga_menu": "20000.00",
                            "kategori": "Utama",
                            "gambar_menu": "/images/1619098312.jpg",
                            "id_bahan": 1,
                            "created_at": "2021-04-02 15:09:31",
                            "updated_at": "2021-04-23 16:36:03",
                            "nama_bahan": "Beef Short Plate",
                            "ukuran_penyajian": 50,
                            "sisa_stok": 250,
                            "unit_bahan": "gram"
                        }
                    ]
        }

     */



    @GET("detailPesanan/pembayaran/{pembayaran_id}")
    Call<Detail_PesananResponse> getDetailPesanans (@Path("pembayaran_id") int pembayaran_id);

    /*

    {
        "message": "Retrieve Detail Pesanans Success",
        "data": [
                    {
                    "id_detail": 1,
                    "kuantitas": 1,
                    "subtotal_kuantitas": 15000,
                    "status_pesanan": "Proses",
                    "id_menu": 2,
                    "id_pembayaran": 1,
                    "created_at": "2021-05-14T12:02:31.000000Z",
                    "updated_at": "2021-05-14T12:40:29.000000Z",
                    "menu": {
                            "id_menu": 2,
                            "nama_menu": "Chicken Slice",
                            "deskripsi": "Potongan daging dari bagian dada ayam",
                            "unit": "Plate",
                            "harga_menu": "15000.00",
                            "kategori": "Utama",
                            "gambar_menu": "/images/1619079888.jpeg",
                            "id_bahan": 2,
                            "created_at": "2021-04-03T04:57:30.000000Z",
                            "updated_at": "2021-04-22T08:24:48.000000Z"
                        }
                    }
            ]
    }
     */

    @POST("detailPesanan")
    @FormUrlEncoded
    Call<Detail_PesananResponse> createPesanan(@Field("kuantitas") int kuantitas,
                                    @Field("subtotal_kuantitas") float subtotal_kuantitas,
                                    @Field("status_pesanan") String status_pesanan,
                                    @Field("id_menu") int id_menu,
                                    @Field("id_pembayaran") int id_pembayaran);


    @PUT("detailPesanan/{id_pesanan}/update")
    @FormUrlEncoded
    Call<Detail_PesananResponse> updatePesanan(@Path("id_pesanan") int id_pesanan,
                                               @Field("kuantitas") int kuantitas);


}
