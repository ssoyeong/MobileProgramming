package gachon.mpclass.pearth;

import android.app.Application;

public class ListViewItem_shareboard {
    private String title ;
    private String content;
    private String imgUrl;
    private String fileName;

    ListViewItem_shareboard(){

    }

    public ListViewItem_shareboard(String title,String content,String imgUrl,String fileName){
        this.title=title;
        this.content=content;
        this.imgUrl = imgUrl;
        this.fileName = fileName;
    }
    public String getTitle() {
        return title ;
    }
    public String getContent(){return content;}
    public String getImgUrl(){return imgUrl;}
    public String getFileName(){return fileName;}




    public void setTitle(String title) {
        this.title = title ;
    }
    public void setContent(String content){this.content=content;}
    public void setImgUrl(String imgUrl){this.imgUrl=imgUrl;}
}