
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */
public class Player {

    private String name;
    private Color color;
    private int mission;
    public int ravitaillement;
    List<String> territoire = new LinkedList<>();


    /**
     * Default constructor
     */
    public Player(String name, Color color, int mission, int ravitaillement) {
        this.name = name;
        this.color = color;
        this.mission = mission;
        this.ravitaillement = ravitaillement;
    }

    public void setPays(List<String> ter){
        this.territoire=ter;
    }

    public int getRavitaillement() {
        return ravitaillement;
    }

    public void setRavitaillement(int ravitaillement) {
        this.ravitaillement = ravitaillement;
    }

    public int getMission() {
        return mission;
    }

    public void setMission(int mission) {
        this.mission = mission;
    }

    public List<String> getTerritoire() {
        return territoire;
    }

    public void setTerritoire(List<String> territoire) {
        this.territoire = territoire;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return
     */
    public Color getColor() {
        return this.color;
    }

}