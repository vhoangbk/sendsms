package vn.app.sendsms.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoangnv on 12/28/16.
 */

public class Message extends Object{

    private int id;
    @SerializedName("id")
    private String idServer;
    @SerializedName("content_sms")
    private String contentSms;
    @SerializedName("date_send")
    private long dateSend;
    @SerializedName("number_receiver")
    private String numberReceiver;

    //0: not send, 1: sent
    private int sendFlag;

    public Message(){

    }

    public Message(String idServer, String contentSms, long dateSend, String numberReceiver) {
        this.idServer = idServer;
        this.contentSms = contentSms;
        this.dateSend = dateSend;
        this.numberReceiver = numberReceiver;
        this.sendFlag = 0;
    }

    public Message(String idServer, String contentSms, long dateSend, String numberReceiver, int sendFlag) {
        this.idServer = idServer;
        this.contentSms = contentSms;
        this.dateSend = dateSend;
        this.numberReceiver = numberReceiver;
        this.sendFlag = sendFlag;
    }

    public int getId() {
        return id;
    }

    public String getIdServer() {
        return idServer;
    }

    public String getContentSms() {
        return contentSms;
    }

    public long getDateSend() {
        return dateSend;
    }

    public String getNumberReceiver() {
        return numberReceiver;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdServer(String idServer) {
        this.idServer = idServer;
    }

    public void setContentSms(String contentSms) {
        this.contentSms = contentSms;
    }

    public void setDateSend(long dateSend) {
        this.dateSend = dateSend;
    }

    public void setNumberReceiver(String numberReceiver) {
        this.numberReceiver = numberReceiver;
    }

    public void setSendFlag(int sendFlag) {
        this.sendFlag = sendFlag;
    }

    public int getSendFlag() {
        return sendFlag;
    }
}
