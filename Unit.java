
import java.util.*;

/**
 * 
 */
public abstract class Unit {

    private int cost;
    private int min_power;
    private int max_power;
    private double priorityATT;
    private double priorityDEF;

    /**
     * Movement per turn and counter per turn
     */
    private int mpt;
    private int cpt = 0;


    public Unit(int cost, int min_power, int max_power, double priorityATT, double priorityDEF, int mpt) {
        this.cost = cost;
        this.min_power = min_power;
        this.max_power = max_power;
        this.priorityATT = priorityATT;
        this.priorityDEF = priorityDEF;
        this.mpt = mpt;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMin_power() {
        return min_power;
    }

    public void setMin_power(int min_power) {
        this.min_power = min_power;
    }

    public int getMax_power() {
        return max_power;
    }

    public void setMax_power(int max_power) {
        this.max_power = max_power;
    }

    public double getPriorityATT() {
        return priorityATT;
    }

    public void setPriorityATT(double priorityATT) {
        this.priorityATT = priorityATT;
    }

    public double getPriorityDEF() {
        return priorityDEF;
    }

    public void setPriorityDEF(double priorityDEF) {
        this.priorityDEF = priorityDEF;
    }

    public int getMpt() {
        return mpt;
    }

    public void setMpt(int mpt) {
        this.mpt = mpt;
    }

    public int getCpt() {
        return cpt;
    }

    public void setCpt(int cpt) {
        this.cpt = cpt;
    }

    public void addCpt() {
        this.cpt++;
    }
}