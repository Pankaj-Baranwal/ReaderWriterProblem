import java.util.Scanner;

/**
 * Created by Pankaj on 1/11/16.
 */
public class MainSemaphoreClass {
    private static ThreadClass[] totalReaderThreads, totalWriterThreads;
    private static int currentReaderCount = 0, currentWriterCount = 0;
    
    private static Scanner sn = new Scanner(System.in);

    public static void main(String[] args) {
        
        int number_of_readers = 0, number_of_writers = 0;
        System.out.println("How many reader threads do you want?");
        number_of_readers = sn.nextInt();
        System.out.println("How many writer threads do you want?");
        number_of_writers = sn.nextInt();
        totalReaderThreads = new ThreadClass[number_of_readers];
        totalWriterThreads = new ThreadClass[number_of_writers];
        
        initialise();
        
        while (true) {
            menu();
        }
    }
    
    /*
        Function name: initialise
        Arguments: No argument
        Return type: void
        Description: This function initializes all the reader and writer threads.
    */
    private static void initialise() {
        for (int i = 0; i < totalReaderThreads.length; i++) {
            totalReaderThreads[i] = new ThreadClass();
            // readOrWrite 'r' if it is a reader thread and 'w' if it is a writer thread.
            totalReaderThreads[i].readOrWrite('r');
        }
        for (int i = 0; i < totalWriterThreads.length; i++) {
            totalWriterThreads[i] = new ThreadClass();
            totalWriterThreads[i].readOrWrite('w');
        }
    }
    
    /*
        Function name: menu
        Arguments: No argument
        Return type: void
        Description: Options for GUI input from user
    */
    private static void menu() {
        System.out.println("What do you want?");
        System.out.println("Press 1 for starting a reader thread");
        System.out.println("Press 2 for starting a writer thread");
        // user_value decides if it is a reader thread or a writer thread
        int user_value = sn.nextInt();
        if (user_value == 1) {
            // if maximum reader threads has been reached, no more reader threads can be created.
            if (currentReaderCount >= totalReaderThreads.length - 1) {
                System.out.println("Max readers reached.");
            } else {
                // add another reader thread
                currentReaderCount++;
                // Let the reader thread start working
                totalReaderThreads[currentReaderCount].start();
            }
        } else if (user_value == 2) {
            // if maximum writer threads has been reached, no more writer threads can be created.
            if (currentWriterCount >= totalWriterThreads.length - 1) {
                System.out.println("Max writers reached.");
            } else {
                // add another writer thread
                currentWriterCount++;
                // Let the writer thread start working
                totalWriterThreads[currentWriterCount].start();
            }
        }
    }
}
