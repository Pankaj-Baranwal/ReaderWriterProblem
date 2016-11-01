/**
 * Created by root on 1/11/16.
 */
public class ThreadClass extends Thread {
    private char readOrWrite = 'r';
    private static int wrt_semaphore = 1, mutex_semaphore = 1;
    private static int reader_count = 0;
    private int waiting = 0;
    private long writerSleepTime = 5000, readerSleepTime = 4000;
    private String line_break = "------------------x-----------------";

    public void readOrWrite(char value) {
        readOrWrite = value;
    }

    @Override
    public void run() {
        super.run();
        if (readOrWrite == 'w') {
            while (isBusy(wrt_semaphore)){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            waitWriter();
            waitReader();
            writeData();
            signalWriter();
            signalReader();
        } else if (readOrWrite == 'r') {
            while (isBusy(mutex_semaphore)) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (reader_count == 0)
                waitWriter();
            waitReader();
            reader_count++;
            signalReader();
            readData();
        }
    }

    private boolean isBusy(int semaphore) {
        if (semaphore != 1 && waiting == 0) {
            waiting = 1;
            System.out.println(line_break);
            System.out.println("Thread " + getId() + " waiting.");
            System.out.println(line_break);
        }
        return semaphore != 1;
    }

    private void waitWriter() {
        System.out.println(line_break);
        System.out.println("Write privilege Locked");
        System.out.println(line_break);
        wrt_semaphore = 0;
    }

    private void writeData() {
        waiting = 0;
        System.out.println(line_break);
        System.out.println("Thread " + getId() + " is writing.");
        System.out.println(line_break);
        try {
            sleep(writerSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(line_break);
        System.out.println("Thread " + getId() + " has done writing.");
        System.out.println(line_break);
    }

    private void signalWriter() {
        System.out.println(line_break);
        System.out.println("Write privilege Unlocked");
        System.out.println(line_break);
        wrt_semaphore = 1;
    }

    private void waitReader() {
        System.out.println(line_break);
        System.out.println("Read privilege Locked");
        System.out.println(line_break);
        mutex_semaphore = 0;
    }

    private void signalReader() {
        System.out.println(line_break);
        System.out.println("Read privilege Unlocked");
        System.out.println(line_break);
        mutex_semaphore = 1;
    }

    private void readData() {
        System.out.println(line_break);
        System.out.println("Thread " + getId() + " is reading.");
        System.out.println(line_break);
        try {
            sleep(readerSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(line_break);
        System.out.println("Thread " + getId() + " has done reading.");
        System.out.println(line_break);
        if (--reader_count == 0)
            signalWriter();
    }
}