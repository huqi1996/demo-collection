package com.huqi.qs.concurrency.demo;

public class Punishment {
    private int leftCopyCount;
    private String wordToCopy;

    public Punishment() {
    }

    public Punishment(int leftCopyCount, String wordToCopy) {
        this.leftCopyCount = leftCopyCount;
        this.wordToCopy = wordToCopy;
    }

    public int getLeftCopyCount() {
        return leftCopyCount;
    }

    public void setLeftCopyCount(int leftCopyCount) {
        this.leftCopyCount = leftCopyCount;
    }

    public String getWordToCopy() {
        return wordToCopy;
    }

    public void setWordToCopy(String wordToCopy) {
        this.wordToCopy = wordToCopy;
    }
}
