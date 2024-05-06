package com.example.myreadingapp.Recycler;

public class Model {
    String id, namebook, img, pdfLink;

    public Model(String id, String namebook, String img, String pdfLink, String videoLink) {
        this.id = id;
        this.namebook = namebook;
        this.img = img;
        this.pdfLink = pdfLink;

    }

    public Model() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamebook() {
        return namebook;
    }

    public void setNamebook(String namebook) {
        this.namebook = namebook;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }


}
