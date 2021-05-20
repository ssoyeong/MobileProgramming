package gachon.mpclass.pearth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListViewAdapter_search extends BaseAdapter {
    int cnt = 0;
    Context context;
    ArrayList<ListViewItem_shareboard> data;
    int check = 0;
    DatabaseReference mDB = FirebaseDatabase.getInstance().getReference().child("report");
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("share");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ListViewAdapter_search(Context context) {
        this.context = context;
    }

    public ListViewAdapter_search(ArrayList<ListViewItem_shareboard> data) {
        this.data = data;

    }

    public void clear() {
        data.clear();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void showButton(ImageButton btn) {
        btn.setVisibility(View.VISIBLE);
    }

    public void hideButton(ImageButton btn) {
        btn.setVisibility(View.INVISIBLE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListViewItem_shareboard list = data.get(position);
        convertView = inflater.inflate(R.layout.shareboard, parent, false);
        TextView textView0 = (TextView) convertView.findViewById(R.id.textView0);
        TextView textView1 = (TextView) convertView.findViewById(R.id.textView1);
        TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_preview);
        ImageButton change = (ImageButton) convertView.findViewById(R.id.change);
        Button report = (Button) convertView.findViewById(R.id.report);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete);
        String Uid = user.getUid(); //현재사용자
        String profile = "profile/" + list.getTitle() + ".png"; //글쓴 사용자의 프로필 사진
        String post = list.getUid(); //글쓴사용자
        change.setVisibility(View.INVISIBLE);
        report.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);

        if (list.getImgUrl() == null)//사진이 있을때
        {
            textView0.setText("[" + list.getLocation() + "]");
            textView1.setText(list.getTitle());
            textView2.setText(list.getContent());
        } else {
            textView0.setText("[" + list.getLocation() + "]");
            textView1.setText(list.getTitle());
            textView2.setText(list.getContent());
            Glide.with(convertView)
                    .load(list.getImgUrl())
                    .into(imageView);
        }


        cnt++;
        return convertView;
    }
}