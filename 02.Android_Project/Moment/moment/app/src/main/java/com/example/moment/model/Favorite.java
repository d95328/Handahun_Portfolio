package com.example.moment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private String f_userid;
    private int f_bno;
    private String f_favorites;
    private String f_ddabong;

    public Favorite() {}

    public Favorite(String f_userid, int f_bno, String f_favorites, String f_ddabong) {
        this.f_userid = f_userid;
        this.f_bno = f_bno;
        this.f_favorites = f_favorites;
        this.f_ddabong = f_ddabong;
    }

    public String getF_userid() {
        return f_userid;
    }
    public void setF_userid(String f_userid) {
        this.f_userid = f_userid;
    }
    public int getF_bno() {
        return f_bno;
    }
    public void setF_bno(int f_bno) {
        this.f_bno = f_bno;
    }
    public String getF_favorites() {
        return f_favorites;
    }
    public void setF_favorites(String f_favorites) {
        this.f_favorites = f_favorites;
    }
    public String getF_ddabong() {
        return f_ddabong;
    }
    public void setF_ddabong(String f_ddabong) {
        this.f_ddabong = f_ddabong;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "f_userid='" + f_userid + '\'' +
                ", f_bno=" + f_bno +
                ", f_favorites='" + f_favorites + '\'' +
                ", f_recommend='" + f_ddabong + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(f_userid);
        dest.writeInt(f_bno);
        dest.writeString(f_favorites);
        dest.writeString(f_ddabong);
    }

    protected Favorite(Parcel in) {
        f_userid = in.readString();
        f_bno = in.readInt();
        f_favorites = in.readString();
        f_ddabong = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
