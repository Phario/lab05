package pl.pwr.ite.dynak.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pwr.ite.dynak.threads.Client;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Diner {
    private final int tickSpeed;
    private final int maxQueueSize;
    BlockingQueue<Client> primaryQueue;
    BlockingQueue<Client> secondaryQueue;
    BlockingQueue<Client> foodQueue0;
    BlockingQueue<Client> foodQueue1;
    BlockingQueue<Client> checkoutQueue0;
    BlockingQueue<Client> checkoutQueue1;
    BlockingQueue<Client> checkoutQueue2;
    BlockingQueue<Client> checkoutQueue3;
    ArrayList<BlockingQueue<Client>> foodQueueList = new ArrayList<>();
    ArrayList<BlockingQueue<Client>> checkoutQueueList = new ArrayList<>();
    public Diner(int maxQueueSize, int tickSpeed) {
        this.maxQueueSize = maxQueueSize;
        this.tickSpeed = tickSpeed;
        this.primaryQueue = new LinkedBlockingQueue<>(maxQueueSize);
        this.foodQueue0 = new LinkedBlockingQueue<>(maxQueueSize);
        this.foodQueue1 = new LinkedBlockingQueue<>(maxQueueSize);
        this.secondaryQueue = new LinkedBlockingQueue<>(maxQueueSize);
        this.checkoutQueue0 = new LinkedBlockingQueue<>(maxQueueSize);
        this.checkoutQueue1 = new LinkedBlockingQueue<>(maxQueueSize);
        this.checkoutQueue2 = new LinkedBlockingQueue<>(maxQueueSize);
        this.checkoutQueue3 = new LinkedBlockingQueue<>(maxQueueSize);
    }
    private static final Logger logger = LoggerFactory.getLogger(Diner.class);
    private void startSimulation() throws InterruptedException {
        this.foodQueueList.add(foodQueue0);
        this.foodQueueList.add(foodQueue1);
        this.checkoutQueueList.add(checkoutQueue0);
        this.checkoutQueueList.add(checkoutQueue1);
        this.checkoutQueueList.add(checkoutQueue2);
        this.checkoutQueueList.add(checkoutQueue3);
        for (int i = 0; i < this.maxQueueSize; i++) {
            String name = String.valueOf((char)('A' + i));
            Client retard = new Client(
                    name,
                    this.tickSpeed,
                    this.primaryQueue,
                    this.secondaryQueue,
                    this.foodQueueList,
                    this.checkoutQueueList);
            Thread retardThread = new Thread(retard);
            retardThread.start();
            TimeUnit.SECONDS.sleep(1);
        }
    }
    public static void main(String[] args) {
        try {
            Diner diner = new Diner(20, 500);
            diner.startSimulation();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
