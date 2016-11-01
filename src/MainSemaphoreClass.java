import java.util.Scanner;

/**
 * Created by root on 1/11/16.
 */
public class MainSemaphoreClass {
    private static ThreadClass[] totalReaderThreads, totalWriterThreads;
    private static Scanner sn = new Scanner(System.in);
    private static int currentReaderCount = 0, currentWriterCount = 0;

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

    private static void initialise() {
        for (int i = 0; i < totalReaderThreads.length; i++) {
            totalReaderThreads[i] = new ThreadClass();
            totalReaderThreads[i].readOrWrite('r');
        }
        for (int i = 0; i < totalWriterThreads.length; i++) {
            totalWriterThreads[i] = new ThreadClass();
            totalWriterThreads[i].readOrWrite('w');
        }
    }

    private static void menu() {
        System.out.println("What do you want?");
        System.out.println("Press 1 for starting a reader thread");
        System.out.println("Press 2 for starting a writer thread");
        int user_value = sn.nextInt();
        if (user_value == 1) {
            if (currentReaderCount >= totalReaderThreads.length - 1) {
                System.out.println("Max readers reached.");
            } else {
                currentReaderCount++;
                totalReaderThreads[currentReaderCount].start();
            }
        } else if (user_value == 2) {
            if (currentWriterCount >= totalWriterThreads.length - 1) {
                System.out.println("Max writers reached.");
            } else {
                currentWriterCount++;
                totalWriterThreads[currentWriterCount].start();
            }
        }
    }
}