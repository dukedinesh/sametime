package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SignatureDetail{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("signature_img")
    @Expose
    private String signatureImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignatureImg() {
        return signatureImg;
    }

    public void setSignatureImg(String signatureImg) {
        this.signatureImg = signatureImg;
    }

}