/**
 * Created by root on 1/11/16.
 */
public class ThreadClass extends Thread {
    String readOrWrite = "r";
    int wrt_semaphore = 1, mutex_semaphore = 1;
    int reader_count = 0;

    public void readOrWrite(String value){
        readOrWrite = value;
    }

    @Override
    public void run() {
        super.run();


    }
}
