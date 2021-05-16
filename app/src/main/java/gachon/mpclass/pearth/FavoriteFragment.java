package gachon.mpclass.pearth;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteFragment extends Fragment {


    ListView listView;
    //    ScrollView scrollView;
    private ArrayList<Store> allStores = new ArrayList<Store>();
    private ArrayList<Store> listStores = new ArrayList<Store>();
    View rootView;
    StoreAdapter storeAdapter;
    SharedPreferences sh_Pref;
    private Context mContext = null;

    private FirebaseAuth firebaseAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("Users");
    String uid;
    int index;
    ArrayList<String> favorites;

    String SIGUN = "";
    String DONG = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        Log.d("uid", uid);



        allStores.add(new Store("걸구쟁이네", "37.464159", "127.12277", "한식당", "서울특별시 송파구 문정동 송파대로 111", "02-401-4320"));
        allStores.add(new Store("스윗솔", "37.50872", "127.08157", "비건 채식 레스토랑", "서울특별시 송파구 잠실동 225번지 자연빌라 2층 201호", "070-8888-3816"));
        allStores.add(new Store("블렌드랩", "37.50129", "127.10353", "카페", "서울특별시 송파구 석촌동 257 1층", "070-4922-2700"));
        allStores.add(new Store("씨젬므주르", "37.50817", "127.1069", "비건 프렌치 레스토랑", "서울특별시 송파구 송파동 백제고분로41길 25", "050713474142"));
        allStores.add(new Store("제로비건", "37.51308", "127.09642", "채식 전문식당", "서울특별시 송파구 잠실3동 올림픽로 240", "02-2143-1609"));
        allStores.add(new Store("해피비건", "37.49582", "127.12215", "화장품 산업", "서울특별시 송파구 가락동 송파대로28길 43", "070-8800-7766"));
        allStores.add(new Store("닥터비건", "37.52199", "127.04464", "비건 채식 레스토랑", "서울특별시 강남구 청담동 17-7", "02-543-2030"));
        allStores.add(new Store("비건이삼", "37.51508", "127.04876", "비건 베이커리", "서울특별시 강남구 삼성동 26-33", "050713634460"));

        System.out.println("함수 시작 전");
        getFavoriteData();
        System.out.println("함수 시작 후");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_favorite, container, false);

        mContext = this.getContext();

        listView = (ListView) rootView.findViewById(R.id.listView);
        storeAdapter = new StoreAdapter(getLayoutInflater(), listStores);
        listView.setAdapter(storeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Intent intent = new Intent(getActivity(), PopUpActivity.class);

                    Store obj = (Store) listView.getAdapter().getItem(position);

                    Bundle mybundle = new Bundle();
                    mybundle.putParcelable("store", obj);
                    mybundle.putString("name", obj.getName());
                    mybundle.putString("address", obj.getAddr());
                    mybundle.putString("telephone", obj.getTel());
                    mybundle.putString("type", obj.getType());

                    intent.putExtras(mybundle);
                    startActivity(intent);
                }

            }
        });

        return rootView;
    }


    public void getFavoriteData(){

//        conditionRef.child(uid).child("favorite").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (listStores.size() == 0) {
//                    for (DataSnapshot child : snapshot.getChildren()) {
//                        String key = child.getKey();
//                        Log.d("Store", key);
//
//                        for (Store s : allStores) {
//                            if (s.getName().equals(key)) {
//                                Log.d("keyStore", key);
//                                listStores.add(new Store(s.getName(), s.getLat(), s.getLongt(), s.getType(), s.getAddr(), s.getTel()));
//
//                            }
//                        }
//                    }
//                }
//                else {
//
//                    ArrayList<String> keyList = new ArrayList<String>();
//
//                    for (DataSnapshot child : snapshot.getChildren()) {
//                        String key = child.getKey();
//                        keyList.add(key);
//                        Log.d("Store", key);
//
//                        for (Store s : allStores) {
//                            if (s.getName().equals(key)) {
//                                Log.d("keyStore", key);
//
//                                Store s1 = new Store(s.getName());
//                                if (!listStores.contains(s1)) {
//                                    listStores.add(new Store(s.getName(), s.getLat(), s.getLongt(), s.getType(), s.getAddr(), s.getTel()));
//                                    Log.d("insert", s.getName());
//                                }
//                            }
//                        }
//                    }
//                }
//
//                storeAdapter.notifyDataSetChanged();
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                throw error.toException();
//            }
//        });

        System.out.println("getFavoriteData 시작");
//        storeAdapter.clearData();
//        storeAdapter.notifyDataSetChanged();
        System.out.println("listStores 클리어");
        System.out.println("listStores 사이즈: " + listStores.size());


        favorites = new ArrayList<>();
        conditionRef.child(uid).child("favorite").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data : snapshot.getChildren()) {
                    System.out.println(data.getKey());
                    favorites.add(data.getKey());
                }

                System.out.println(favorites);

                for(String str : favorites){

                    for(Store s1 : allStores){
                        if(s1.getName().equals(str) && !listStores.contains(s1)){
                            listStores.add(new Store(s1.getName(), s1.getLat(), s1.getLongt(), s1.getType(), s1.getAddr(), s1.getTel()));
                            System.out.println("리스트에 추가 완료");
                        }
                    }
                }

                storeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println("getFavoriteData 종료");
    }
}


