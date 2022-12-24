package com.laporankeuangan.Model;

public class UploadImagesModel {
    private String nameImg;
    private String ImgUrl;

    public UploadImagesModel(){

    }
    public UploadImagesModel(String nameImg, String ImgUrl){
        this.ImgUrl = ImgUrl;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
