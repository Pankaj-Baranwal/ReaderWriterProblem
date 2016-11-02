/**
 * Created by Pankaj on 1/11/16.
 */
public class ThreadClass extends Thread {
    
    // Variable to decide whehter it is a reader thread or a writer thread
    private char readOrWrite = 'r';
    // Semaphore variables
    private static int wrt_semaphore = 1, mutex_semaphore = 1;
    // number of readers
    private static int reader_count = 0;
    // checks if current thread is waiting
    private int waiting = 0;
    // stores sleep time in milliseconds
    private long writerSleepTime = 5000, readerSleepTime = 4000;
    // part of UI
    private String line_break = "------------------x-----------------";
    
    /*
        Function name: readOrWrite
        Argument: char variable with value = 'r' if it is reader thread and value = 'w' if writer thread
        Returns: void
        Description: This function to ensure that a thread is always a reader or a writer thread.
    */
    public void readOrWrite(char value) {
        readOrWrite = value;
    }
    
    @Override
    public void run() {
        super.run();
        if (readOrWrite == 'w') {
            // Operations to be done if thread is writer thread
            
            // isBusy checks if read or write permission can be granted.
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
            // Operations to be done if thread is reader thread
            while (isBusy(mutex_semaphore)) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // If reader_count is 0, that means write permissions need to be unlocked. 
            // Further threads don't need to check this.
            if (reader_count == 0)
                waitWriter();
            waitReader();
            // Increase reader_count.
            reader_count++;
            signalReader();
            readData();
        }
    }

    /*
        Function name: isBusy
        Argument: int variable which stores value of semaphore
        Returns: True if thread needs to wait and false if thread can continue processing.
        Description: Function to check if thread needs to wait or if it can continue processing.
    */
    private boolean isBusy(int semaphore) {
        if (semaphore != 1 && waiting == 0) {
            // Current thread is in waiting state.
            waiting = 1;
            System.out.println(line_break);
            System.out.println("Thread " + getId() + " waiting.");
            System.out.println(line_break);
        }
        return semaphore != 1;
    }
    
    /*
        Function name: waitWriter
        Argument: No argument.
        Returns: void
        Description: Changes the value of write semaphore so as to lock read and write privileges.
    */    
    private void waitWriter() {
        System.out.println(line_break);
        System.out.println("Write privilege Locked");
        System.out.println(line_break);
        wrt_semaphore = 0;
    }

    
    /*
        Function name: writeData
        Argument: No argument.
        Returns: void
        Description: Code to access database and modify contents in it.
    */    
    private void writeData() {
        // Thread removed from waiting state
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

    
    /*
        Function name: signalWriter
        Argument: No argument.
        Returns: void
        Description: Changes the value of write semaphore so as to unlock read and write privileges.
    */    
    private void signalWriter() {
        System.out.println(line_break);
        System.out.println("Write privilege Unlocked");
        System.out.println(line_break);
        wrt_semaphore = 1;
    }

    
    /*
        Function name: waitReader
        Argument: No argument.
        Returns: void
        Description: Changes the value of read semaphore so as to lock read privileges.
    */    
    private void waitReader() {
        System.out.println(line_break);
        System.out.println("Read privilege Locked");
        System.out.println(line_break);
        mutex_semaphore = 0;
    }

    
    /*
        Function name: signalReader
        Argument: No argument.
        Returns: void
        Description: Changes the value of read semaphore so as to unlock read privileges.
    */    
    private void signalReader() {
        System.out.println(line_break);
        System.out.println("Read privilege Unlocked");
        System.out.println(line_break);
        mutex_semaphore = 1;
    }

    
    /*
        Function name: readData
        Argument: No argument.
        Returns: void
        Description: Access database to read its content without modifying it.
    */    
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
        // Condition to check if all reader threads have done processing. If yes, then unlock write privilege.
        if (--reader_count == 0)
            signalWriter();
    }
}
