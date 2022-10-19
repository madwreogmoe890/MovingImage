package org.movinganimation;


import javax.swing.*;

public class Progress {
    private final Timer timer;
    private long startTime;
    private long now;
    private final double progressPerSecond;

    Progress(ProgressAction action, double progressPerSecond) {
        this.progressPerSecond = progressPerSecond;

        timer = new Timer(40, event -> {
            now = System.currentTimeMillis();
            action.runAction(getProgress() / 100);
        });
    }

    private double getProgress() {
        return getTotalProgress() % 100;
    }

    private double getTotalProgress() {
        double diff = now - startTime;
        return diff / 1000 * progressPerSecond;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        timer.restart();
    }
}
