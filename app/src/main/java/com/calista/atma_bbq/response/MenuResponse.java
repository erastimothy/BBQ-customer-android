package com.calista.atma_bbq.response;

import com.calista.atma_bbq.model.Menu;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuResponse {

    @SerializedName("data")
    @Expose
    private List<Menu> menus = null;

    @SerializedName("message")
    @Expose
    private String message;

    public List<Menu> getMenus() {return menus;}

    public String getMessage() {return message;}

    public void setMenus(List<Menu> menus){this.menus = menus;}

    public void setMessage(String message){this.message = message;}

}
