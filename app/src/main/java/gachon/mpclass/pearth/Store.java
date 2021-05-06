package gachon.mpclass.pearth;


import android.os.Parcel;
import android.os.Parcelable;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Store implements Parcelable{

    private String name = null;
    private String lat = null;
    private String longt = null;
    private String type = null;
    private String addr = null;
    private String tel = null;



    public Store(){
    }


    public Store(String name, String lat, String longt, String type, String addr, String tel){
        this.name = name;
        this.lat = lat;
        this.longt = longt;
        this.type = type;
        this.addr = addr;
        this.tel = tel;
    }

    protected Store(Parcel in) {
        name = in.readString();
        lat = in.readString();
        longt = in.readString();
        type = in.readString();
        addr = in.readString();
        tel = in.readString();
    }

    public static final Parcelable.Creator<Store> CREATOR = new Parcelable.Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };


    @Override
    public String toString() {
        return this.getName() + "\n" + this.getTel() + "\n" + this.getAddr();
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
        parcel.writeString(type);
        parcel.writeString(addr);
        parcel.writeString(tel);
    }


    public ArrayList<Store> readStore() {

        String path = Store.class.getResource("").getPath();
        ArrayList<Store> storeList = new ArrayList<Store>();

        try {
            File file = new File(path + "Vegan Restaurants Dataset.xlsx");

            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            int rowIndex=0;
            int colIndex=0;

            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();
            for(rowIndex=2; rowIndex < rows; rowIndex++){

                Store ed = new Store();

                XSSFRow row = sheet.getRow(rowIndex);
                XSSFCell cell = row.getCell(2);

                ed.setName(String.valueOf(row.getCell(0)));
                ed.setAddr(String.valueOf(row.getCell(1)));
                ed.setTel(String.valueOf(row.getCell(2)));
                ed.setType(String.valueOf(row.getCell(3)));
                ed.setLat(String.valueOf(row.getCell(4)));
                ed.setLongt(String.valueOf(row.getCell(5)));

                storeList.add(ed);


            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return storeList;
    }
}

