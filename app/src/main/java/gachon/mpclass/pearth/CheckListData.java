package gachon.mpclass.pearth;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class  CheckListData implements Parcelable{

    String CheckList_item;
    boolean CheckList_done;

    public CheckListData(String item, boolean done)
    {
        CheckList_item=item;
        CheckList_done=done;

    }
    public boolean isChecked(){
        return CheckList_done;
    }
    //객체를 받아올 때 호출
    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected CheckListData(Parcel in) {
        CheckList_item=in.readString();
        CheckList_done=in.readBoolean();
    }

    //객체를 전달할 때 호출
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CheckList_item);
        dest.writeBoolean(CheckList_done);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckListData> CREATOR = new Creator<CheckListData>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public CheckListData createFromParcel(Parcel in) {
            return new CheckListData(in);
        }

        @Override
        public CheckListData[] newArray(int size) {
            return new CheckListData[size];
        }
    };
}
