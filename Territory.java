import java.util.ArrayList;
import java.awt.Color;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;


public class Territory {



    //
    // Attributes //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    private String name;
    private ArrayList<Territory> adjacents = new ArrayList<>();

    /**
     * Each territory will have a specific color.
     * This color will be used to differentiate all the territories.
     */
    private Color color;

    /**
     * Each territory can have a player, and therefore armies within affiliated only to this player.
     */
    private Player player;
    private ArrayList<Soldier> army_soldiers = new ArrayList<>();
    private ArrayList<Rider> army_riders = new ArrayList<>();
    private ArrayList<Cannon> army_cannons = new ArrayList<>();


    public Territory(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdjacents(ArrayList<Territory> adjacents) {
        this.adjacents = adjacents;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Territory> getAdjacents() {
        return adjacents;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Soldier> getArmy_soldiers() {
        return army_soldiers;
    }

    public void setArmy_soldiers(ArrayList<Soldier> army_soldiers) {
        this.army_soldiers = army_soldiers;
    }

    public ArrayList<Rider> getArmy_riders() {
        return army_riders;
    }

    public void setArmy_riders(ArrayList<Rider> army_riders) {
        this.army_riders = army_riders;
    }

    public ArrayList<Cannon> getArmy_cannons() {
        return army_cannons;
    }

    public void setArmy_cannons(ArrayList<Cannon> army_cannons) {
        this.army_cannons = army_cannons;
    }











