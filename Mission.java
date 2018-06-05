public class Mission {
    int joueurs_min;
    int joueurs_max;
    String name;
    String type;
    int nombre;
    String controle;
    boolean option_asie;
    int option_armees;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }


    public boolean getOption_asie() {
        return option_asie;
    }

    public void setOption_asie(boolean option_asie) {
        this.option_asie = option_asie;
    }

    public int getOption_armees() {
        return option_armees;
    }

    public void setOption_armees(int option_armees) {
        this.option_armees = option_armees;
    }

    public int getJoueurs_min() {
        return joueurs_min;
    }

    public void setJoueurs_min(int joueurs_min) {
        this.joueurs_min = joueurs_min;
    }

    public int getJoueurs_max() {
        return joueurs_max;
    }

    public void setJoueurs_max(int joueurs_max) {
        this.joueurs_max = joueurs_max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mission(int condition_min, int condition_max, String name, String type, int nombre, String controle, boolean option_asie, int option_armees) {
        this.joueurs_min = condition_min;
        this.joueurs_max = condition_max;
        this.name = name;
        this.type = type;
        this.nombre = nombre;
        this.controle = controle;
        this.option_asie = option_asie;
        this.option_armees = option_armees;
    }
}