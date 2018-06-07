import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Territoire {

    String name;
    int owner;
    int id;
    int region;
    int territoire;
    int coordonnee_x;
    int coordonnee_y;
    List<Integer> adjacent = new LinkedList<>();
    List<Soldier> soldier = new LinkedList<>();
    List<Cannon> cannon = new LinkedList<>();
    List<Cavalry> cavalry = new LinkedList<>();
    Color couleur;


    public Territoire(String name, int id, int region, int territoire) {
        this.name = name;
        this.region = region;
        this.territoire = territoire;
        this.id = id;
    }

    public Territoire(Territoire other) {
        this.name = other.name;
        this.region = other.region;
        this.territoire = other.territoire;
        this.id = other.id;
        this.adjacent = other.adjacent;
        this.owner = other.owner;
        this.soldier = other.soldier;
        this.cannon = other.cannon;
        this.cavalry = other.cavalry;
        this.couleur = other.couleur;
        this.coordonnee_x = other.coordonnee_x;
        this.coordonnee_y = other.coordonnee_y;
    }
}
