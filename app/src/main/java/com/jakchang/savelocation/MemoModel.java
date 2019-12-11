package com.jakchang.savelocation;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class MemoModel implements Parcelable {
    int id;
    String latitude;
    String longitude;
    Bitmap mainImg;
    String nation;
    String city;
    String address;
    String title;
    String tag;
    String date;
    String uri1;
    String uri2;
    String uri3;
    String uri4;
    String text;

    public MemoModel(){}

    protected MemoModel(Parcel in) {
        id = in.readInt();
        latitude = in.readString();
        longitude = in.readString();
        nation = in.readString();
        city = in.readString();
        address = in.readString();
        title = in.readString();
        tag = in.readString();
        date = in.readString();
        uri1 = in.readString();
        uri2 = in.readString();
        uri3 = in.readString();
        uri4 = in.readString();
        text = in.readString();
        mainImg = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public MemoModel(int id,String latitude, String longitude, Bitmap mainImg, String address,String title, String tag, String date, String uri1, String uri2, String uri3, String uri4, String text) {
        this.id=id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mainImg = mainImg;
        this.address = address;
        this.title = title;
        this.tag = tag;
        this.date = date;
        this.uri1 = uri1;
        this.uri2 = uri2;
        this.uri3 = uri3;
        this.uri4 = uri4;
        this.text = text;
    }

    public static final Creator<MemoModel> CREATOR = new Creator<MemoModel>() {
        @Override
        public MemoModel createFromParcel(Parcel in) {
            return new MemoModel(in);
        }

        @Override
        public MemoModel[] newArray(int size) {
            return new MemoModel[size];
        }
    };

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUri1() {
        return uri1;
    }

    public void setUri1(String uri1) {
        this.uri1 = uri1;
    }

    public String getUri2() {
        return uri2;
    }

    public void setUri2(String uri2) {
        this.uri2 = uri2;
    }

    public String getUri3() {
        return uri3;
    }

    public void setUri3(String uri3) {
        this.uri3 = uri3;
    }

    public String getUri4() {
        return uri4;
    }

    public void setUri4(String uri4) {
        this.uri4 = uri4;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getMainImg() {
        return mainImg;
    }

    public void setMainImg(Bitmap mainImg) {
        this.mainImg = mainImg;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(nation);
        dest.writeString(city);
        dest.writeString(address);
        dest.writeString(title);
        dest.writeString(tag);
        dest.writeString(date);
        dest.writeString(uri1);
        dest.writeString(uri2);
        dest.writeString(uri3);
        dest.writeString(uri4);
        dest.writeString(text);
        dest.writeParcelable(mainImg,flags);
    }
}
