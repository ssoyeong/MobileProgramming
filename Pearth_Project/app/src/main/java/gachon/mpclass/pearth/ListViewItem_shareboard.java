package gachon.mpclass.pearth;

import android.app.Application;

public class ListViewItem_shareboard {
    private String title ;
    private String content;
    private String imgUrl;
    private String location;
    private String fileName;
    private String uid;

    ListViewItem_shareboard(){

    }

    public ListViewItem_shareboard(String title,String content,String imgUrl,String location,String fileName,String uid){
        this.title=title;
        this.content=content;
        this.imgUrl = imgUrl;
        this.location = location;
        this.fileName = fileName;
        this.uid = uid;
    }
    public String getTitle() {
        return title ;
    }
    public String getContent(){return content;}
    public String getImgUrl(){return imgUrl;}
    public String getLocation(){return location;}
    public String getFileName(){return fileName;}
    public String getUid(){return uid;}





    public void setTitle(String title) {
        this.title = title ;
    }
    public void setContent(String content){this.content=content;}
    public void setImgUrl(String imgUrl){this.imgUrl=imgUrl;}
    public void setLocation(String location){this.location = location;}
}