package com.longrise.sharelib.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-8-23.
 */

public class ShareBean implements Serializable {

    private String url;//分享链接地址

    private String title;//分享的标题

    private String desc;//分享的描述

    private int thumb;//分享的本地资源

    private String image;//分享的网络图片

    private int qrCode;//二维码图片

    public int getQrCode() {
        return qrCode;
    }

    public void setQrCode(int qrCode) {
        this.qrCode = qrCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
