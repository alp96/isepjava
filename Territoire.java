import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Territoire {

    String name;
    int owner;
    int id;
    int region;
    int territoire;
    List<Integer> adajacent = new LinkedList<Integer>();
    List<Soldier> soldier = new LinkedList<>();
    List<Cannon> cannon = new LinkedList<>();
    List<Cavalry> cavalry = new LinkedList<>();

    public Territoire(String name, int id, int region, int territoire) {
        this.name = name;
        this.region = region;
        this.territoire = territoire;
        this.id = id;
    }
}
