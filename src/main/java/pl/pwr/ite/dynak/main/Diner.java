package pl.pwr.ite.dynak.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pwr.ite.dynak.threads.Clerk;
import pl.pwr.ite.dynak.threads.Client;
import pl.pwr.ite.dynak.threads.Cook;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
@lombok.Getter
@lombok.Setter
public class Diner {
    private static final Logger logger = LoggerFactory.getLogger(Diner.class);
    private final int tickSpeed;
    private final int maxRandomTickSpeed;
    private final int maxQueueSize;
    ArrayList<Clerk> clerks;
    ArrayList<Cook> cooks;
    BlockingQueue<Client> primaryQueue;
    BlockingQueue<Client> foodQueue0;
    BlockingQueue<Client> foodQueue1;
    BlockingQueue<Client> checkoutQueue0;
    BlockingQueue<Client> checkoutQueue1;
    BlockingQueue<Client> checkoutQueue2;
    BlockingQueue<Client> checkoutQueue3;
    ArrayList<BlockingQueue<Client>> foodQueueList;
    ArrayList<BlockingQueue<Client>> checkoutQueueList;
    Client[] table;
    private final Map<String, Thread> clientThreads = new ConcurrentHashMap<>();
    public Diner(int maxQueueSize, int tickSpeed, int maxRandomTickSpeed) {
        this.maxQueueSize = maxQueueSize;
        this.tickSpeed = tickSpeed;
        this.maxRandomTickSpeed = maxRandomTickSpeed;
        this.clerks = new ArrayList<>();
        this.cooks = new ArrayList<>();
        this.foodQueueList = new ArrayList<>();
        this.checkoutQueueList = new ArrayList<>();
        this.primaryQueue = new LinkedBlockingQueue<>(maxQueueSize);
        this.foodQueue0 = new LinkedBlockingQueue<>(maxQueueSize);
        this.foodQueue1 = new LinkedBlockingQueue<>(maxQueueSize);
        this.checkoutQueue0 = new LinkedBlockingQueue<>(maxQueueSize);
        this.checkoutQueue1 = new LinkedBlockingQueue<>(maxQueueSize);
        this.checkoutQueue2 = new LinkedBlockingQueue<>(maxQueueSize);
        this.checkoutQueue3 = new LinkedBlockingQueue<>(maxQueueSize);
        this.table = new Client[30];
    }
    private void startStaff(){
        this.foodQueueList.add(foodQueue0);
        this.foodQueueList.add(foodQueue1);
        this.checkoutQueueList.add(checkoutQueue0);
        this.checkoutQueueList.add(checkoutQueue1);
        this.checkoutQueueList.add(checkoutQueue2);
        this.checkoutQueueList.add(checkoutQueue3);
        Cook cookZero = new Cook("j", tickSpeed,maxRandomTickSpeed,foodQueue0);
        Cook cookOne = new Cook("k", tickSpeed,maxRandomTickSpeed,foodQueue1);
        cooks.add(cookZero);
        cooks.add(cookOne);
        Thread cookZeroThread = new Thread(cookZero);
        Thread cookOneThread = new Thread(cookOne);
        cookZeroThread.start();
        cookOneThread.start();
        Clerk clerkZero = new Clerk("l", tickSpeed,maxRandomTickSpeed,checkoutQueue0);
        Clerk clerkOne = new Clerk("m", tickSpeed,maxRandomTickSpeed,checkoutQueue1);
        Clerk clerkTwo = new Clerk("n", tickSpeed,maxRandomTickSpeed,checkoutQueue2);
        Clerk clerkThree = new Clerk("o", tickSpeed,maxRandomTickSpeed,checkoutQueue3);
        clerks.add(clerkZero);
        clerks.add(clerkOne);
        clerks.add(clerkTwo);
        clerks.add(clerkThree);
        Thread clerkZeroThread = new Thread(clerkZero);
        Thread clerkOneThread = new Thread(clerkOne);
        Thread clerkTwoThread = new Thread(clerkTwo);
        Thread clerkThreeThread = new Thread(clerkThree);
        clerkZeroThread.start();
        clerkOneThread.start();
        clerkTwoThread.start();
        clerkThreeThread.start();
    }
    private void startMultipleClients() {
        for (int i = 0; i < maxQueueSize; i++) {
            String name = String.valueOf((char) ('A' + i));
            startClient(name);
        }
    }
    private void restartClients() {
        new Thread(() -> {
            while (true) {
                try {
                    for (Map.Entry<String, Thread> entry : clientThreads.entrySet()) {
                        String name = entry.getKey();
                        Thread thread = entry.getValue();

                        if (!thread.isAlive()) {
                            logger.info("Restarting client thread: {}", name);
                            startClient(name);
                        }
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    private void startClient(String name) {
        Client client = new Client(
                name,
                this.tickSpeed,
                this.maxRandomTickSpeed,
                this.primaryQueue,
                this.foodQueueList,
                this.checkoutQueueList,
                this.table);
        Thread newThread = new Thread(client);
        newThread.setName(name);
        clientThreads.put(name, newThread);
        newThread.start();
    }

    public void startSimulation() {
        startStaff();
        startMultipleClients();
        restartClients();
    }
    public static void main(String[] args) {
        Diner diner = new Diner(20, 1000, 500);
        diner.startSimulation();
    }

}
