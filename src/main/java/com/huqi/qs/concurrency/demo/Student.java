package com.huqi.qs.concurrency.demo;

public class Student extends Thread {
    private String name;
    private Punishment punishment;

    public Student(String name, Punishment punishment) {
        //2、调用Thread构造方法，设置threadName
        super(name);
        this.name = name;
        this.punishment = punishment;
    }

    public void copyWord() {
        int count = 0;
        String threadName = Thread.currentThread().getName();

        while (true) {
            if (punishment.getLeftCopyCount() > 0) {
                int leftCopyCount = punishment.getLeftCopyCount();
                System.out.println(threadName + "线程-" + name + "抄写" + punishment.getWordToCopy() + "。还要抄写" + --leftCopyCount + "次");
                punishment.setLeftCopyCount(leftCopyCount);
                count++;
            } else {
                break;
            }
        }
        System.out.println(threadName + "线程-" + name + "一共抄写了" + count + "次！");
    }

    //3、重写run方法，调用copyWord完成任务
    @Override
    public void run() {
        copyWord();
    }

    public static void main(String[] args) {
        Punishment punishment = new Punishment(100, "internationalization");

        Student xiaoming = new Student("小明", punishment);
        xiaoming.start();

        Student xiaozhang = new Student("小张", punishment);
        xiaozhang.start();

        Student xiaozhao = new Student("小赵", punishment);
        xiaozhao.start();
    }
}