package com.bikshanov.bloodpressurelog;

import androidx.annotation.StringDef;

import java.util.Date;
import java.util.UUID;

public class MeasurementResult {

    private UUID mId;
    private int mSysBloodPressure;
    private int mDiaBloodPressure;
    private int mPulse;
    private Date mDate;
    private String mHealth;
    private String mComment;
    private boolean mArrhythmia;
    private String mArm;

    public MeasurementResult() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public int getSysBloodPressure() {
        return mSysBloodPressure;
    }

    public int getDiaBloodPressure() {
        return mDiaBloodPressure;
    }

    public int getPulse() {
        return mPulse;
    }

    public Date getDate() {
        return mDate;
    }

    public String getHealth() {
        return mHealth;
    }

    public String getComment() {
        return mComment;
    }

    public void setSysBloodPressure(int sysBloodPressure) {
        mSysBloodPressure = sysBloodPressure;
    }

    public void setDiaBloodPressure(int diaBloodPressure) {
        mDiaBloodPressure = diaBloodPressure;
    }

    public void setPulse(int pulse) {
        mPulse = pulse;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setHealth(String health) {
        mHealth = health;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public boolean isArrhythmia() {
        return mArrhythmia;
    }

    public void setArrhythmia(boolean arrhythmia) {
        mArrhythmia = arrhythmia;
    }

    public String getArm() {
        return mArm;
    }

    public void setArm(String arm) {
        mArm = arm;
    }
}
