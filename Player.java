
import java.awt.Color;

/**
 * 
 */
public class Player {

    private String name;
    private Color color;

    /**
     * Default constructor
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
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