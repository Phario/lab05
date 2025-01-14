package pl.pwr.ite.dynak.threads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import java.util.concurrent.ThreadLocalRandom;

@lombok.Getter
@lombok.Setter
public class Client extends Person implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private boolean hasFood;
    private int foodType;
    private BlockingQueue<Client> primaryQueue;
    private BlockingQueue<Client> secondaryQueue;
    private List<BlockingQueue<Client>> foodQueueList;
    private List<BlockingQueue<Client>> checkoutQueueList;
    public void setHasFood() {
        this.hasFood = true;
        this.foodType = ThreadLocalRandom.current().nextInt(0, 5);
    }
    public Client(String name,
                  int tickSpeed,
                  BlockingQueue<Client> primaryQueue,
                  BlockingQueue<Client> secondaryQueue,
                  List<BlockingQueue<Client>> foodQueueList,
                  List<BlockingQueue<Client>> checkoutQueueList)
    {
        this.name = name;
        this.tickSpeed = tickSpeed;
        this.foodQueueList = foodQueueList;
        this.checkoutQueueList = checkoutQueueList;
        this.primaryQueue = primaryQueue;
        this.secondaryQueue = secondaryQueue;
    }
    private void getInLine(BlockingQueue<Client> queue) {
        //Get into the initial queue
    }
    private BlockingQueue<Client> chooseLine(List<BlockingQueue<Client>> queueList) throws InterruptedException {
        int queueId = 0;
        int queueSize;
        int tempQueueSize;
        for (BlockingQueue<Client> queue : queueList) {
            if (!queue.isEmpty()) {
                //some algorithm to choose the smallest queue
            } return queueList.get(queueId);
        }
        return queueList.get(queueId);
    }
    private void sitDown() {}
    private void eat() {}
    @Override
    public void run() {
        /*getInLine(this.primaryQueue); //main queue
        try {
            getInLine(chooseLine(this.foodQueueList)); //serving queue
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            getInLine(chooseLine(this.checkoutQueueList)); //checkout queue
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sitDown();
        eat();
        */
        System.out.println("Client " + name + " started");
    }
}
