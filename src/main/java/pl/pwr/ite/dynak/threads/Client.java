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
    private Client[] table;
    public Client(String name,
                  int tickSpeed,
                  int maxRandomTickSpeed,
                  BlockingQueue<Client> primaryQueue,
                  BlockingQueue<Client> secondaryQueue,
                  List<BlockingQueue<Client>> foodQueueList,
                  List<BlockingQueue<Client>> checkoutQueueList,
                  Client[] table)
    {
        this.name = name;
        this.tickSpeed = tickSpeed;
        this.foodQueueList = foodQueueList;
        this.checkoutQueueList = checkoutQueueList;
        this.primaryQueue = primaryQueue;
        this.secondaryQueue = secondaryQueue;
        this.table = table;
        this.maxRandomTickSpeed = maxRandomTickSpeed;
    }
    private void getInLine(BlockingQueue<Client> queue) throws InterruptedException {
        queue.put(this);
    }
    private BlockingQueue<Client> chooseLine(List<BlockingQueue<Client>> queueList) throws InterruptedException {
        int queueId = 0;
        int minQueueSize = 30; //TODO: remove placeholder
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
    public void setHasFood() {
        this.hasFood = true;
        this.foodType = ThreadLocalRandom.current().nextInt(0, 5);
    }
    @Override
    public void run() {
        try {
        getInLine(this.primaryQueue); //main queue
        getInLine(chooseLine(this.foodQueueList)); //serving queue
        getInLine(chooseLine(this.checkoutQueueList)); //checkout queue
        sitDown(this.table);
        eat();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        logger.info("Client {} started", name);
    }
}
