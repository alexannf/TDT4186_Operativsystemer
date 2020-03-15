package TDT4186_Sushibar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SushiBar {
    // SushiBar settings.
    private static int waitingAreaCapacity = 20;
    private static int waitressCount = 7;
    private static int duration = 5;
    public static int maxOrder = 15;
    public static int waitressWait = 60; // Random in the range 30-60
    public static int customerWait = 2500; // Random in the range 1250-2500
    public static int doorWait = 120; // Random in the range 1-120
    public static boolean isOpen = true;

    // Creating log file.
    private static File log;
    private static String path = "./";

    // Variables related to statistics.
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;

    // We use this variable to increment our unique customer IDs
    public static SynchronizedInteger customerID;



    public static void main(String[] args) {
        log = new File(path + "log.txt");

        // Initializing shared variables for counting number of orders.
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);
        customerID = new SynchronizedInteger(1);

        // *** Initializing all needed objects and threads for the Sushi Bar to work ***
        new Clock(SushiBar.duration);
        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);

        // Each waitress will have their own thread, putting them in this list for easy, safe termination
        List<Thread> waitresses = new ArrayList<>();

        // Create a thread for every waitress. Number of waitresses indicated by waitressCount
        for (int i=0;i<waitressCount;i++){
            // Assigns a thread for each waitress (each consumer)
            Thread waitress = new Thread(new Waitress(waitingArea));
            waitress.start();
            waitresses.add(waitress);
        }

        // The door (producer) is executed in this thread
        Thread door = new Thread(new Door(waitingArea));
        door.start();

        // Joining each individual thread with this main thread to safely ensure that all threads are done executing.
        for (Thread waitressThread : waitresses) {
            try {
                waitressThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            door.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        write("***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****");

        SushiBar.write("Total number of orders: " + totalOrders.get());
        SushiBar.write("Orders eaten here: " + servedOrders.get());
        SushiBar.write("Orders taken out: " + takeawayOrders.get());
    }

    // Writes actions in the log file and console.
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
