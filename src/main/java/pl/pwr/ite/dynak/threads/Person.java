package pl.pwr.ite.dynak.threads;

@lombok.Getter
public abstract class Person {
    protected String name;
    protected int tickSpeed;
    protected int maxRandomTickSpeed;
}
