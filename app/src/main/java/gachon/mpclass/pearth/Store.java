package gachon.mpclass.pearth;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Store implements Parcelable {
    private String name = null;
    private String lat = null;
    private String longt = null;
    private String sigun = null;
    private String type = null;
    private String addr = null;
    private String roadAddr = null;
    private String tel = null;
    private String zip = null;

    public Store(String name, String lat, String longt, String sigun, String type, String addr, String roadAddr, String tel, String zip){
        this.name = name;
        this.lat = lat;
        this.longt = longt;
        this.sigun = sigun;
        this.type = type;
        this.addr = addr;
        this.roadAddr = roadAddr;
        this.tel = tel;
        this.zip = zip;
    }

    protected Store(Parcel in) {
        name = in.readString();
        lat = in.readString();
        longt = in.readString();
        sigun = in.readString();
        type = in.readString();
        addr = in.readString();
        roadAddr = in.readString();
        tel = in.readString();
        zip = in.readString();
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return this.getName() + "\n" + this.getTel() + "\n" + this.getRoadAddr();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }

    public String getSigun() {
        return sigun;
    }

    public void setSigun(String sigun) {
        this.sigun = sigun;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRoadAddr() {
        return roadAddr;
    }

    public void setRoadAddr(String roadAddr) {
        this.roadAddr = roadAddr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(lat);
        parcel.writeString(longt);
        parcel.writeString(sigun);
        parcel.writeString(type);
        parcel.writeString(addr);
        parcel.writeString(roadAddr);
        parcel.writeString(tel);
        parcel.writeString(zip);
    }
}
