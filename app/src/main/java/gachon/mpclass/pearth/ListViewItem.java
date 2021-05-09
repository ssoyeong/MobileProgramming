package gachon.mpclass.pearth;

import android.app.Application;

public class ListViewItem {
    private String title ;
    private String content;
    private String imgUrl;
    private String tag;
    private String fileName;

    ListViewItem(){

    }

    public ListViewItem(String title,String content,String imgUrl,String tag,String fileName){
        this.title=title;
        this.content=content;
        this.imgUrl = imgUrl;
        this.tag=tag;
        this.fileName = fileName;
    }
    public String getTitle() {
        return title ;
    }
    public String getContent(){return content;}
    public String getImgUrl(){return imgUrl;}
    public String getTag(){return tag;}
    public String getFileName(){return fileName;}




    public void setTitle(String title) {
        this.title = title ;
    }
    public void setContent(String content){this.content=content;}
    public void setImgUrl(String imgUrl){this.imgUrl=imgUrl;}
    public void setTag(String tag){this.tag=tag;}

}