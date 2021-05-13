package gachon.mpclass.pearth;

public class User {

    private int report;
    private int share;
    private int score;

    public User(){

    }

    public User(int report, int share, int score){
        this.report = report;
        this.share = share;
        this.score = score;
    }

    public int getReport(){
        return report;
    }

    public void setReport(int report){
        this.report = report;
    }

    public int getShare(){
        return share;
    }

    public void setShare(int share){
        this.share = share;
    }
    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
