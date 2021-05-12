package gachon.mpclass.pearth;

import android.app.Application;

public class ListViewItem {
    private String title ;
    private String content;
    private String imgUrl;
    private String tag;
    private String fileName;
    private String uid;

    ListViewItem(){

    }

    public ListViewItem(String title,String content,String imgUrl,String tag,String fileName,String uid){
        this.title=title;
        this.content=content;
        this.imgUrl = imgUrl;
        this.tag=tag;
        this.fileName = fileName;
        this.uid = uid;
    }
    public String getTitle() {
        return title ;
    }
    public String getContent(){return content;}
    public String getImgUrl(){return imgUrl;}
    public String getTag(){return tag;}
    public String getFileName(){return fileName;}
    public String getUid(){return uid;}




    public void setTitle(String title) {
        this.title = title ;
    }
    public void setContent(String content){this.content=content;}
    public void setImgUrl(String imgUrl){this.imgUrl=imgUrl;}
    public void setTag(String tag){this.tag=tag;}

}