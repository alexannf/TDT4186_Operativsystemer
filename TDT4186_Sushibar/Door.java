package TDT4186_Sushibar;

import java.util.Random;

/**
 * This class implements the Door component of the sushi bar
 * assignment The Door corresponds to the Producer in the
 * producer/consumer problem
 */
public class Door implements Runnable {

    private WaitingArea waitingArea;
    /**
     * Creates a new Door. Make sure to save the
     * @param waitingArea   The customer queue waiting for a seat
     */
    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This method will run when the door thread is created (and started).
     * The method should create customers at random intervals and try to put them in the waiting area.
     */
    @Override
    public void run() {
        while(SushiBar.isOpen){
            if(waitingArea.isFull()){
                try {
                    // wait until the thread of waitingArea gives a notification
                    synchronized(waitingArea) {
                        waitingArea.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                // Randomly generate the time it takes for the door to "generate" a new customer
                // generated using the "doorWait" variable in the SushiBar class
                int randomWait = new Random().nextInt(SushiBar.doorWait);
                try {
                    Thread.sleep(randomWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Creates a new customer after "randomWait" time has passed
                Customer customer = new Customer();
                SushiBar.write(Thread.currentThread().getName()+
                        " Customer: "+customer.getCustomerID()+" is now waiting");
                this.waitingArea.enter(customer);
            }
        }
        SushiBar.write("***** DOOR CLOSED. *****");
    }
}
