
import java.awt.Color;

/**
 * 
 */
public class Player {

    private String name;
    private Color color;
    private int _reserve;

    /**
     * Default constructor
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this._reserve = 40;
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
    public getReserve()
    {
    return _reserve;
    }
    public ravitaillement(int nbTroupes)
    {
    _reserve -= nbTroupes;
    }
    public perteTroupes(int nbTroupes)
    {
    _reserve += nbTroupes;
    }

}