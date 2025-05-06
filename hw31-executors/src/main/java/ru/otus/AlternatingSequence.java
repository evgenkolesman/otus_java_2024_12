package ru.otus;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AlternatingSequence {
    private static final Logger logger = LoggerFactory.getLogger(AlternatingSequence.class);
    public static final String THREAD_1 = "Поток 1";
    private static boolean isThread1Turn = true;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition thread1Turn = lock.newCondition();
    private static final Condition thread2Turn = lock.newCondition();
    public static void main(String[] args) {

        Thread thread1 = new Thread(new PrinterTask("Поток 1"), "Поток 1");
        Thread thread2 = new Thread(new PrinterTask("Поток 2"), "Поток 2");

        thread1.start();
        thread2.start();


    }

    static class PrinterTask implements Runnable {
        private final String name;
        private final SequenceGenerator generator;

        PrinterTask(String name) {
            this.name = name;
            this.generator = new SequenceGenerator();
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    lock.lock();
                    var isThread1 = name.equals(THREAD_1);
                    while (isThread1 != isThread1Turn) {
                        if (isThread1) {
                            thread1Turn.await();
                        } else {
                            thread2Turn.await();
                        }
                    }
                    int value = generator.getNext();
                    logger.info(name + ": " + value);
                    Thread.sleep(500);
                    // Меняем очередь
                    isThread1Turn = !isThread1Turn;

                    // Уведомляем следующий поток
                    if (isThread1) {
                        thread2Turn.signal();
                    } else {
                        thread1Turn.signal();
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;

                } finally {
                    lock.unlock();
                }
            }
        }


    }
}

class SequenceGenerator {
    private int current = 1;
    private boolean forward = true;

    public int getNext() {
        int value = current;

        if (forward) {
            if (current < 10) {
                current++;
            } else {
                forward = false;
                current--;
            }
        } else {
            if (current > 1) {
                current--;
            } else {
                forward = true;
                current = 2;
            }
        }

        return value;
    }
}