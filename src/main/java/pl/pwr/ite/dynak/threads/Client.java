package pl.pwr.ite.dynak.threads;
import lombok.Lombok;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@lombok.Getter
@lombok.Setter
public class Client extends Person implements Runnable{
    public Client(String name) {
        this.name = name;
    }
    private void getInLine() {
        //Get into the initial queue
    }
    private void queueIntoServing() {
        //choose and queue into the serving line
    }
    private void chooseFood() {
        //waits to simulate choosing food
    }
    private void queueIntoCheckout() {
        //choose queue from checkout and pay for the meal
    }
    private void chooseLine(List<BlockingQueue<Person>> queueList) {
        int queueId;
        int queueSize;
        int tempQueueSize;
        for (BlockingQueue<Person> queue : queueList) {

        }
    }
    private void sitDown() {}
    private void eat() {}
    @Override
    public void run() {
        getInLine();
        queueIntoServing();
        queueIntoCheckout();
        sitDown();
        eat();
    }
}
