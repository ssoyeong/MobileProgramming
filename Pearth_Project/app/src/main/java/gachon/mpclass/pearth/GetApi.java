package gachon.mpclass.pearth;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GetApi {

    public ArrayList<Store> getAllXmlData (String SIGUN, String DONG) {
        ArrayList<Store> allStores = new ArrayList<>();
        int totalCount = getTotalCount(SIGUN, DONG, 1000, 0, 0, "");

        for(int i=0 ; i <= totalCount/1000 ; i++){
            allStores.addAll(getXmlData(SIGUN, DONG, 1000, i, 0, ""));
        }

        return allStores;
    }

    public ArrayList<Store> getXmlData(String SIGUN, String DONG, int pSize, int pIndex, int searchType, String query){
        StringBuffer buffer=new StringBuffer();
        ArrayList<Store> stores = new ArrayList<>();

        String queryUrl = getApiUrl(SIGUN, DONG, pSize, pIndex, searchType, query);

        try{
            URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
//            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            URLConnection urlcon = url.openConnection();
            urlcon.setReadTimeout(10000);
            InputStream is = urlcon.getInputStream();

            String name=null, lat=null, longt=null, sigun=null, type=null, addr=null, roadAddr=null, tel=null, zip=null;

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){

                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT: // 파싱 시작...
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("row")){ // 객체 태그 시작
                            name=null;
                            lat=null;
                            longt=null;
                            sigun=null;
                            type=null;
                            addr=null;
                            roadAddr=null;
                            tel=null;
                            zip=null;
                        } // 첫번째 검색결과
                        else if(tag.equals("CMPNM_NM")){ // 상호명
                            xpp.next();
                            name = xpp.getText();
                        }
                        else if(tag.equals("REFINE_WGS84_LAT")){ // 위도
                            xpp.next();
                            lat = xpp.getText();
                        }
                        else if(tag.equals("REFINE_WGS84_LOGT")){ // 경도
                            xpp.next();
                            longt = xpp.getText();
                        }
                        else if(tag.equals("SIGUN_NM")){ // 시군명
                            xpp.next();
                            sigun = xpp.getText();
                        }
                        else if(tag.equals("INDUTYPE_NM")){ // 업종명
                            xpp.next();
                            type = xpp.getText();
                        }
                        else if(tag.equals("REFINE_LOTNO_ADDR")){ // 지번주소
                            xpp.next();
                            addr = xpp.getText();
                        }
                        else if(tag.equals("REFINE_ROADNM_ADDR")){ // 도로명주소
                            xpp.next();
                            roadAddr = xpp.getText();
                        }
                        else if(tag.equals("TELNO")){ // 전화번호
                            xpp.next();
                            tel = xpp.getText();
                        }
                        else if(tag.equals("REFINE_ZIP_CD")){ // 우편번호
                            xpp.next();
                            zip = xpp.getText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("row")){
                            if(lat != null && longt != null) {
                                stores.add(new Store(name, lat, longt, type, addr, tel));
                            }
                        }
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        // 파싱 끝
        return stores; // Store arrayList 반환

    }//getXmlData method....

    public String getApiUrl (String SIGUN, String DONG, int pSize, int pIndex, int searchType, String query) {
        String apiKey = "eb10e06504d3495db11fa551c6872d6b";
        String queryUrl="https://openapi.gg.go.kr/RegionMnyFacltStus?KEY=" + apiKey;

        if(pSize != 0){ // 페이지 사이즈
            queryUrl = queryUrl + "&pSize=" + pSize;
        }
        if(pIndex != 0){ // 페이지 인덱스
            queryUrl = queryUrl + "&pIndex=" + pIndex;
        }

        try {
            if(SIGUN != "") {
                queryUrl = queryUrl + "&SIGUN_NM=" + URLEncoder.encode(SIGUN, "UTF-8"); // 시군명
            }
            if (DONG != "") {
                queryUrl = queryUrl + "&REFINE_LOTNO_ADDR=" + URLEncoder.encode(DONG, "UTF-8");
                ; // 지번주소
            }

            switch (searchType) {
                case 0: // 검색어 없음
                    break;
                case 1: // 상호명
                    queryUrl = queryUrl + "&CMPNM_NM=" + URLEncoder.encode(query, "UTF-8");
                    ;
                    break;
                case 2: // 도로명주소
                    queryUrl = queryUrl + "&REFINE_ROADNM_ADDR=" + URLEncoder.encode(query, "UTF-8");
                    ;
                    break;
            }
        }catch (UnsupportedEncodingException e){}

        return queryUrl;
    } // getApiUrl method...

    public int getTotalCount(String SIGUN, String DONG, int pSize, int pIndex, int searchType, String query){
        StringBuffer buffer=new StringBuffer();
        String queryUrl = getApiUrl(SIGUN, DONG, pSize, pIndex, searchType, query);
        int totalCount = 0;

        try{
            URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결


            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){

                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT: // 파싱 시작...
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("head")){ // 객체 태그 시작
                        } // 첫번째 검색결과
                        else if(tag.equals("list_total_count")){ // 상호명
                            xpp.next();
                            totalCount = Integer.parseInt(xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return totalCount;

    }
}
