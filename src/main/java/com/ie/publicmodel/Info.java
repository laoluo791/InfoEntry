package com.ie.publicmodel;

public class Info {
    private int state;
    private String msg;

    public Info() {
    }

    public Info(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Info{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
