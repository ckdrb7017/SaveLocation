package com.jakchang.savelocation.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memolist")
public class MemoEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name ="latitude")
    String latitude;
    @ColumnInfo(name ="longitude")
    String longitude;
    @ColumnInfo(name ="nation")
    String nation;
    @ColumnInfo(name ="city")
    String city;
    @ColumnInfo(name ="address")
    String address;
    @ColumnInfo(name ="title")
    String title;
    @ColumnInfo(name ="tag")
    String tag;
    @ColumnInfo(name ="date")
    String date;
    @ColumnInfo(name ="uri1")
    String uri1;
    @ColumnInfo(name ="uri2")
    String uri2;
    @ColumnInfo(name ="uri3")
    String uri3;
    @ColumnInfo(name ="uri4")
    String uri4;
    @ColumnInfo(name ="text")
    String text;
    @ColumnInfo(name ="fontType")
    String fontType;
    @ColumnInfo(name ="isDeleted")
    String isDeleted;

    public MemoEntity(){}


/*
    public MemoEntity(int id, String latitude, String longitude, String nation, String city, String address, String title, String tag
            , String date, String uri1, String uri2, String uri3, String uri4, String text,String fontType,String isDeleted) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nation = nation;
        this.city = city;
        this.address = address;
        this.title = title;
        this.tag = tag;
        this.date = date;
        this.uri1 = uri1;
        this.uri2 = uri2;
        this.uri3 = uri3;
        this.uri4 = uri4;
        this.text = text;
        this.fontType=fontType;
        this.isDeleted=isDeleted;
    }*/


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

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
