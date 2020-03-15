package TDT4186_Sushibar;


import java.util.LinkedList;


/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {

    // FIFO data structure containing customers with a fixed size
    LinkedList<Customer> queue;
    int maxSize;
    /**
     * Creates a new waiting area.
     *
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        // initializes the queue with the given size as constraint
        this.queue =  new LinkedList<Customer>();
        this.maxSize = size;
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) {
        // Adds the customer to the queue that represents the waiting area
        this.queue.add(customer);
        // Notifies all threads in this threads monitor. In real time only waitress-threads should be waiting
        // for a customer to "walk in the door"
        notifyAll();
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        // Removes the person "first in line" at the queue in the waiting area, freeing up a space
        Customer nextCustomer = this.queue.pop();
        // Notifies all threads in this threads monitor. In real time only the door thread should be waiting
        // for a free space in the waiting area
        notifyAll();

        return nextCustomer;
    }

    // Helper method to put waitress threads in waiting-states
    public boolean isEmpty(){
        return this.queue.isEmpty();
    }

    // Helper method to put the door thread in a waiting-state
    public boolean isFull(){
        return this.queue.size() == this.maxSize;
    }


}
