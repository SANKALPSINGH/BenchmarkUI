package com.hike.appBenchmark.models;

/**
 *
 * @author kumarpratyush
 *
 */

public class Percentile {

    private int percentile;
    private String msisdn;
    private String chatMsisdn;

    public Percentile(int percentile, String msisdn, String chatMsisdn) {
        this.percentile = percentile;
        this.msisdn = msisdn;
        this.chatMsisdn = chatMsisdn;
    }

    public Percentile() {}

    public int getPercentile() {
        return percentile;
    }

    public void setPercentile(int percentile) {
        this.percentile = percentile;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getChatMsisdn() {
        return chatMsisdn;
    }

    public void setChatMsisdn(String chatMsisdn) {
        this.chatMsisdn = chatMsisdn;
    }

}
