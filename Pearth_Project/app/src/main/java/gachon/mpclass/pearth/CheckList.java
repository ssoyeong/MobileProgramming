package gachon.mpclass.pearth;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CheckList implements Parcelable {

    ArrayList<CheckListData> CheckList;
    public CheckList(){}
    protected CheckList(Parcel in) {

        CheckList=new ArrayList<>();
        in.readTypedList(CheckList,CheckListData.CREATOR);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(CheckList);
    }

    public ArrayList<CheckListData> getCheckListData()
    {
        return CheckList;
    }
    public void setCheckListData(ArrayList<CheckListData> CheckList)
    {
        this.CheckList=CheckList;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckList> CREATOR = new Creator<CheckList>() {
        @Override
        public CheckList createFromParcel(Parcel in) {
            return new CheckList(in);
        }

        @Override
        public CheckList[] newArray(int size) {
            return new CheckList[size];
        }
    };
}
