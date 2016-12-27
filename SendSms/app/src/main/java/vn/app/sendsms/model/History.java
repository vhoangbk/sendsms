package vn.app.sendsms.model;

/**
 * Created by hoangnv on 12/28/16.
 */

public class History extends Object{

    int id;
    String from, to, content, time;

    public History(){
    }

    public History(String from, String to, String content, String time) {
        super();
        this.from = from;
        this.to = to;
        this.content = content;
        this.time = time;
    }

    public History(int id, String from, String to, String content,
                   String time) {
        super();
        this.id = id;
        this.from = from;
        this.to = to;
        this.content = content;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
