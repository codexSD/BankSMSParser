package com.codexSoftSD.bankCardsTemplateManager.data.sms;

import androidx.annotation.NonNull;

public class Sms {
    public String id;
    public String address;
    public String msg;
    public long date;

    @NonNull
    @Override
    public String toString() {
        return "Sms{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}
