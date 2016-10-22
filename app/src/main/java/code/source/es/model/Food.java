package code.source.es.model;

import java.io.Serializable;

/**
 * Created by zhang on 16-10-14.
 */

public class Food implements Serializable {
    private String name;
    private int cost;
    private boolean isOrder;
    private String intention;
    public Food(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean getOrder() {
        return isOrder;
    }
    public void setOrder(boolean order) {
        isOrder = order;
    }
    public String getIntention() {
        return intention;
    }
    public void setIntention(String intention) {
        this.intention = intention;
    }
}
