package pl.pwr.ite.dynak.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Cook extends Person implements Runnable{
    private boolean simulationRunning = true;
    private static final Logger logger = LoggerFactory.getLogger(Cook.class);
    private BlockingQueue<Client> cookQueue;
    public Cook(String name, int tickSpeed, int maxRandomTickSpeed, BlockingQueue<Client> cookQueue) {
        this.name = name;
        this.tickSpeed = tickSpeed;
        this.maxRandomTickSpeed = maxRandomTickSpeed;
        this.cookQueue = cookQueue;
    }
    private void giveFood(BlockingQueue<Client> cookQueue) throws InterruptedException {
        Thread.sleep(tickSpeed/10 + ThreadLocalRandom.current().nextInt(maxRandomTickSpeed));
        Client client = cookQueue.take();
        client.setHasFood();
        logger.info("Food given to client {}", client.getName());
    }
    @Override
    public void run() {
        while(simulationRunning) {
            try {
                giveFood(cookQueue);

            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
