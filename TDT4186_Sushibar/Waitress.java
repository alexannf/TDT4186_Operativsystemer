package TDT4186_Sushibar;

import java.util.Random;

/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    WaitingArea waitingArea;
    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        // If the sushi bar is still open or we have remaining customers after the door has closed
        // we still have orders to take
        while(SushiBar.isOpen || !waitingArea.isEmpty()){
                if(waitingArea.isEmpty()){
                    try {
                        // Wait until the thread of waitingArea gives a notification
                        synchronized(waitingArea) {
                            waitingArea.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else{
                    // Represents the customer that is first in line in queue in the waiting area
                    Customer customer = waitingArea.next();
                    SushiBar.write(Thread.currentThread().getName()+
                            " Customer: "+customer.getCustomerID()+" is now fetched");

                    // Randomly generate the time it takes for a customer to receive an order they have made
                    // generated using the "waitressWait" variable in the SushiBar class
                    int orderingTime = new Random().nextInt(
                            SushiBar.waitressWait/2) + SushiBar.waitressWait/2 + 1;
                    try{
                        Thread.sleep(orderingTime);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    customer.order();

                }
        }
    }


}

