package TDT4186_Sushibar;

import java.util.Random;

/**
 * This class implements a customer, which is used for holding data
 * and update the statistics
 */
public class Customer {


    private int customerID;
    /**
     *  Creates a new Customer.  Each customer should be given a
     *  unique ID
     */
    public Customer() {
        this.customerID = SushiBar.customerID.get();
        SushiBar.customerID.increment();
    }


    /**
     * Here you should implement the functionality for ordering food
     * as described in the assignment.
     */
    public void order() {
        // nextInt range is inclusive 0 and exclusive max, so we must add "1"
        // randomly generates a number which represents the total amount of orders a customer makes
        int totalOrders = new Random().nextInt(SushiBar.maxOrder) + 1;

        // randomly generates a number which represents how many orders of the total amount that are served "in house"
        int inHouseOrders = new Random().nextInt(totalOrders+1);

        // Send amount of orders to update statistics for the sushi bar
        SushiBar.totalOrders.add(totalOrders);
        SushiBar.servedOrders.add(inHouseOrders);
        SushiBar.takeawayOrders.add(totalOrders-inHouseOrders);

        SushiBar.write(Thread.currentThread().getName()+" Customer: "+this.getCustomerID()+" is now eating");

        // Represents the time the customer is sitting in the sushi bar, eating.
        int eatingTime = new Random().nextInt(SushiBar.customerWait/2) + SushiBar.customerWait/2 + 1;
        try {
            Thread.sleep(eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SushiBar.write(Thread.currentThread().getName()+" Customer: "+this.getCustomerID()+" is now leaving");
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        return this.customerID;
    }

    // for printing purposes
    @Override
    public String toString(){
        return "Customer nr: "+this.customerID;
    }
}
