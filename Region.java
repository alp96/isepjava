import java.util.LinkedList;
import java.util.List;

public class Region{
    String name;
    List<Territoire> liste_territoire;
    int valeur_renfort;

    public Region(String name, List<Territoire> liste_territoire, int valeur_renfort) {
        this.name = name;
        this.liste_territoire = liste_territoire;
        this.valeur_renfort = valeur_renfort;
    }
}