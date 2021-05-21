package gachon.mpclass.pearth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.Instant;
import java.time.LocalDate;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter_shareboard extends BaseAdapter {
    int cnt = 0;
    Context context;
    ArrayList<ListViewItem_shareboard> data;
    int check = 0;
    DatabaseReference mDB = FirebaseDatabase.getInstance().getReference().child("report");
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("share");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ListViewAdapter_shareboard(Context context) {
        this.context = context;
    }

    public ListViewAdapter_shareboard(ArrayList<ListViewItem_shareboard> data) {
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
        Button report = (Button) convertView.findViewById(R.id.report);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete);
        ImageButton change = (ImageButton) convertView.findViewById(R.id.change);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_preview);
        String Uid = user.getUid(); //현재사용자
        String profile = "profile/" + list.getTitle() + ".png"; //글쓴 사용자의 프로필 사진
        String post = list.getUid(); //글쓴사용자

        report.setOnClickListener(new View.OnClickListener() {
            String ref = G.keyList.get(position); //클릭한 글의 고유주소

            //            String user = list.getUid();
            public void onClick(View v) {
                if (Uid != null) {
                    if (Uid.equals(post)) {
                        Toast.makeText(context, "내가 쓴 게시물은 신고할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        mDB.child("report" + cnt).child("postRef").setValue(ref);
                        mDB.child("report" + cnt).child("postUser").setValue(post);
                        mDB.child("report" + cnt).child("reportUser").setValue(Uid);
                        Toast.makeText(context, "신고되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
try{
        if (Uid != null) {
            if (Uid.equals(post)) {
                showButton(delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://pearth-7ec20.appspot.com").child("images_share/" + list.getFileName());

                    @Override
                    public void onClick(View v) {
                        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //사진 삭제 성공

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                                dbRef.child(G.keyList.get(position)).removeValue();
                            }
                        });

                        dbRef.child(G.keyList.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "삭제 성공", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dbRef.child(G.keyList.get(position)).removeValue();
                                Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                showButton(change);
                change.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.edit_share,null,false);
                        builder.setView(view);
                        final Button edit = (Button)view.findViewById(R.id.upload);
                        final EditText tit = (EditText)view.findViewById(R.id.title);
                        final EditText con = (EditText)view.findViewById(R.id.content);
                        con.setText(list.getContent());
                        tit.setText(list.getTitle());

                        final AlertDialog dialog = builder.create();
                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String editcon = con.getText().toString();
                                        String edittit = tit.getText().toString();
                                        list.setContent(editcon);
                                        list.setTitle(edittit);
                                        dbRef.child(G.keyList.get(position)).child("content").setValue(editcon);
                                        dbRef.child(G.keyList.get(position)).child("title").setValue(edittit);
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                dialog.dismiss();
                            }
                        });dialog.show();
                    }
                });
            } else {
                hideButton(delete);
                hideButton(change);
            }
        }}catch (NullPointerException ignored){

}


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