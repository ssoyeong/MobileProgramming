package gachon.mpclass.pearth;


//import net.daum.mf.map.api.MapPOIItem;
//import net.daum.mf.map.api.MapPoint;
//import net.daum.mf.map.api.MapView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;



public class MapFragment extends Fragment {

    private static final String STORESKEY = "stores_key";
    private ArrayList<Store> listStores = new ArrayList<>();
//    MapPoint[] mapPoints = new MapPoint[2];
    double[] points = new double[4];
    String SIGUN = "";
    String DONG = "";
    int size = 0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args == null){
            Toast.makeText(getActivity(), "위치 정보를 받아오고 있습니다.", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getActivity(), "지도를 불러오고 있습니다.", Toast.LENGTH_SHORT).show();
            listStores = args.getParcelableArrayList(STORESKEY);
            SIGUN = args.getString("SIGUN");
            DONG = args.getString("DONG");
        }

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

//        final MapView mapView = new MapView(getContext());
//        ViewGroup mapViewContainer = rootView.findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);
//
//        if(listStores.size() == 0){
//            GpsTracker gpsTracker = new GpsTracker(getContext()); // GpsTracker 객체 생성
//            double latitude = gpsTracker.getLatitude(); // 위도
//            double longitude = gpsTracker.getLongitude(); //경도
//            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true); // 현재 위치로 중심점 이동
//            Toast.makeText(getActivity(), "가맹점이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
//        } else {
//            for(Store store : listStores){
//                if(store.getLat() != null && store.getLongt() != null){
//                    double newLat = Double.valueOf(store.getLat());
//                    double newLongt = Double.valueOf(store.getLongt());
//
//                    if(points[0] == 0 || points[0] < newLat){
//                        points[0] = newLat;
//                    }
//                    if(points[1] == 0 || points[1] < newLongt){
//                        points[1] = newLongt;
//                    }
//                    if(points[2] == 0 || points[2] > newLat){
//                        points[2] = newLat;
//                    }
//                    if(points[3] == 0 || points[3] > newLongt){
//                        points[3] = newLongt;
//                    }
//
//                    MapPOIItem marker = new MapPOIItem();
//                    marker.setItemName(store.getName());
//                    marker.setTag(0);
//                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(newLat, newLongt));
//                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//                    mapView.addPOIItem(marker);
//                }
//            }
//
//            mapPoints[0] = MapPoint.mapPointWithGeoCoord(points[0], points[1]); // 최저점
//            mapPoints[1] = MapPoint.mapPointWithGeoCoord(points[2], points[3]); // 최고점
//
//            mapView.fitMapViewAreaToShowMapPoints(mapPoints);
//        }

        return rootView;
    }


}

