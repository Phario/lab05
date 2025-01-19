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
    private boolean hasPaid;
    private int foodType;
    private BlockingQueue<Client> primaryQueue;
    private List<BlockingQueue<Client>> foodQueueList;
    private List<BlockingQueue<Client>> checkoutQueueList;
    private Client[] table;
    public Client(String name,
                  int tickSpeed,
                  int maxRandomTickSpeed,
                  BlockingQueue<Client> primaryQueue,
                  List<BlockingQueue<Client>> foodQueueList,
                  List<BlockingQueue<Client>> checkoutQueueList,
                  Client[] table)
    {
        this.name = name;
        this.tickSpeed = tickSpeed;
        this.foodQueueList = foodQueueList;
        this.checkoutQueueList = checkoutQueueList;
        this.primaryQueue = primaryQueue;
        this.table = table;
        this.maxRandomTickSpeed = maxRandomTickSpeed;
    }
    private synchronized void waitForFood() throws InterruptedException {
        while (!this.hasFood) {
            wait();
        }
    }
    private synchronized void waitForPayment() throws InterruptedException {
        while (!this.hasPaid) {
            wait();
        }
    }
    private void getInLine(BlockingQueue<Client> queue) throws InterruptedException {
        queue.put(this);
    }
    private BlockingQueue<Client> chooseLine(List<BlockingQueue<Client>> queueList) throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
        int queueId = 0;
        int minQueueSize = Integer.MAX_VALUE;
        int tempQueueSize;
        for (BlockingQueue<Client> queue : queueList) {
            if (!queue.isEmpty()) {
                tempQueueSize = queue.size();
                if (tempQueueSize < minQueueSize) {
                    minQueueSize = tempQueueSize;
                    queueId = queueList.indexOf(queue);
                }
            }
            else return queue;
        }
        return queueList.get(queueId);
    }
    private synchronized void sitDown(Client[] table) throws InterruptedException {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                table[i] = this;
                break;
            }
        }
    }
    private void eat() throws InterruptedException {
        Thread.sleep(tickSpeed + ThreadLocalRandom.current().nextInt(maxRandomTickSpeed));
    }
    public synchronized void setHasFood() {
        this.hasFood = true;
        this.foodType = ThreadLocalRandom.current().nextInt(0, 5);
        notify();
    }
    public synchronized int setHasPaid() {
        try {
            this.hasPaid = true;
            return foodType;
        } finally {
            notify();
        }
    }
    private void finishCycle(Client[] table) throws InterruptedException {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                if (table[i].getName().equals(name)) {
                    table[i] = null;
                    break;
                }
            }
        }
    }
    @Override
    public void run() {
        try {
            logger.info("Client {} started", name);
            getInLine(this.primaryQueue); //main queue
            logger.info("Client {} queued into main queue", name);
            Thread.sleep(100 + tickSpeed + ThreadLocalRandom.current().nextInt(maxRandomTickSpeed));
            this.primaryQueue.take();
            getInLine(chooseLine(this.foodQueueList)); //serving queue
            logger.info("Client {} queued into food queue", name);
            waitForFood();
            getInLine(chooseLine(this.checkoutQueueList)); //checkout queue
            logger.info("Client {} queued into checkout queue", name);
            waitForPayment();
            sitDown(this.table);
            logger.info("Client {} sat at the table", name);
            eat();
            Thread.sleep(3*tickSpeed + ThreadLocalRandom.current().nextInt(maxRandomTickSpeed));
            finishCycle(table);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
