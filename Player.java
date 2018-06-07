import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class Player {

    private String name;
    private Color color;
    private int mission;
    public int ravitaillement;
    List<Integer> territoire = new LinkedList<>();
    boolean isHuman;
    int renfort_paysconquis;


    public Player(String name, Color color, int mission, int ravitaillement) {
        this.name = name;
        this.color = color;
        this.mission = mission;
        this.ravitaillement = ravitaillement;
    }

    public void setPays(List<Integer> ter){
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

    public List<Integer> getTerritoire() {
        return territoire;
    }

    public void setTerritoire(List<Integer> territoire) {
        this.territoire = territoire;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

}