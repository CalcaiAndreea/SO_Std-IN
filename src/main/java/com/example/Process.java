package com.example;

public class Process {
    private String pid;
    private double duration;
    private double initialDuration;
    private double remainingTime;
    private double finishTime;
    private int priority;

    public Process(String pid, double duration) {
        this.pid = pid;
        this.duration = duration;
        this.initialDuration = duration;
        this.remainingTime = duration;
        this.priority = 1; // prioritate implicită
    }

    public Process(String pid, double duration, int priority) {
        this.pid = pid;
        this.duration = duration;
        this.initialDuration = duration;
        this.remainingTime = duration;
        this.priority = priority;
    }

    public String getPid() { return pid; }
    public double getDuration() { return duration; }
    public double getInitialDuration() { return initialDuration; }
    public double getRemainingTime() { return remainingTime; }
    public void setRemainingTime(double t) { this.remainingTime = t; }
    public double getFinishTime() { return finishTime; }
    public void setFinishTime(double t) { this.finishTime = t; }
    public int getPriority() { return priority; }
    public void setPriority(int p) { this.priority = p; }
}
