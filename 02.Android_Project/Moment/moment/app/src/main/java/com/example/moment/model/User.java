package com.example.moment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String u_userid;
    private String u_userpw;
    private String u_name;
    private String u_nick;
    private String u_profileimg;
    private String u_local;

    public String getU_userid() {
        return u_userid;
    }

    public void setU_userid(String u_userid) {
        this.u_userid = u_userid;
    }

    public String getU_userpw() {
        return u_userpw;
    }

    public void setU_userpw(String u_userpw) {
        this.u_userpw = u_userpw;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_nick() {
        return u_nick;
    }

    public void setU_nick(String u_nick) {
        this.u_nick = u_nick;
    }

    public String getU_profileimg() {
        return u_profileimg;
    }

    public void setU_profileimg(String u_profileimg) {
        this.u_profileimg = u_profileimg;
    }

    public String getU_local() {
        return u_local;
    }

    public void setU_local(String u_local) {
        this.u_local = u_local;
    }
    public User(){}
    public User(String u_userid, String u_userpw, String u_name, String u_nick, String u_profileimg, String u_local) {
        this.u_userid = u_userid;
        this.u_userpw = u_userpw;
        this.u_name = u_name;
        this.u_nick = u_nick;
        this.u_profileimg = u_profileimg;
        this.u_local = u_local;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_userid='" + u_userid + '\'' +
                ", u_userpw='" + u_userpw + '\'' +
                ", u_name='" + u_name + '\'' +
                ", u_nick='" + u_nick + '\'' +
                ", u_profileimg='" + u_profileimg + '\'' +
                ", u_local='" + u_local + '\'' +
                '}';
    }

    protected User(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel in) {
        //직렬화를 풀어주는 메소드 (input)
        u_userid = in.readString();
        u_userpw = in.readString();
        u_name = in.readString();
        u_nick = in.readString();
        u_local = in.readString();
        u_profileimg = in.readString();
    }//end of readFromParcel


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(u_userid);
        out.writeString(u_userpw);
        out.writeString(u_name);
        out.writeString(u_nick);
        out.writeString(u_local);
        out.writeString(u_profileimg);
    }
}
