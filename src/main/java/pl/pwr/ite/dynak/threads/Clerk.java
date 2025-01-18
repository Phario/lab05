package pl.pwr.ite.dynak.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Clerk extends Person implements Runnable {
    private boolean simulationRunning = true;
    private static final Logger logger = LoggerFactory.getLogger(Clerk.class);
    private BlockingQueue<Client> checkoutQueue;
    public Clerk(String name, int tickSpeed, int maxRandomTickSpeed, BlockingQueue<Client> checkoutQueue) {
        this.name = name;
        this.tickSpeed = tickSpeed;
        this.maxRandomTickSpeed = maxRandomTickSpeed;
        this.checkoutQueue = checkoutQueue;
    }
    private void checkout(BlockingQueue<Client> checkoutQueue) throws InterruptedException {
        Thread.sleep(tickSpeed + ThreadLocalRandom.current().nextInt(maxRandomTickSpeed));
        logger.info("Client has food of type {}",checkoutQueue.take().getFoodType());
    }
    @Override
    public void run() {
        while(simulationRunning) {
            try {
                checkout(checkoutQueue);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
