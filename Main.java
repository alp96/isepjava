import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Interface test = new Interface();

        int nombre_joueur = 5;

        int[] ravitaillement = {0,0,40,35,30,25,20};

        //List<String> liste_pays;
        List<String> liste_pays = new LinkedList<>(Arrays.asList("0101","0102","0103","0104","0201","0202","0203","0204","0205","0206","0207","0208","0209",
                "0301","0302","0303","0304","0305","0306","0307","0308","0309","0310","0311","0312","0401","0402","0403",
                "0404","0405","0406","0501","0502","0503","0504","0601","0602","0603","0604","0605","0606","0607"));

        List<Player> liste_joueur;
        Player joueur0 = new Player("Joueur 1", Color.blue, 0,ravitaillement[nombre_joueur]);
        Player joueur1 = new Player("Joueur 2", Color.red, 0,ravitaillement[nombre_joueur]);
        Player joueur2 = new Player("Joueur 3", Color.green, 0,ravitaillement[nombre_joueur]);
        Player joueur3 = new Player("Joueur 4", Color.yellow, 0,ravitaillement[nombre_joueur]);
        Player joueur4 = new Player("Joueur 5", Color.white, 0,ravitaillement[nombre_joueur]);
        Player joueur5 = new Player("Joueur 6", Color.black, 0,ravitaillement[nombre_joueur]);
        liste_joueur = Arrays.asList(joueur0,joueur1,joueur2,joueur3,joueur4,joueur5);



        //instantiation des missions
        List<Mission> liste_mission;
        Mission mission0 = new Mission(3,6,"Détruire le joueur X","detruire",0,"",false,0);
        Mission mission1 = new Mission(2,3,"Conquérir tous les territoires","conquerir",42,"territoire",false,0);
        Mission mission2 = new Mission(1,6,"Contrôler 3 régions et au moins 18 territoires","conquerir",3,"region",false,0);
        Mission mission3 = new Mission(3,6,"Contrôler 18 territoires avec au moins 2 armées","conquerir",18,"territoire",false,2);
        Mission mission4 = new Mission(2,3,"Contrôler 30 territoires","conquerir",30,"territoire",false,0);
        Mission mission5 = new Mission(4,5,"Contrôler 24 territoires","conquerir",24,"territoire",false,0);
        Mission mission6 = new Mission(6,6,"Contrôler 21 territoires","conquerir",21,"territoire",false,0);
        Mission mission7 = new Mission(1,6,"Contrôler l'Asie et 1 autre région","conquerir",1,"region",true,0);
        liste_mission=Arrays.asList(mission0,mission1,mission2,mission3,mission4,mission5,mission6,mission7);

        /*for (Mission miss: liste_mission){
            if (miss.getCondition_min() >= nombre_joueur){
                ThreadLocalRandom.current().nextInt(0, nombre_joueur + 1);
            }
        }*/

        for (int nombre = 0; nombre < nombre_joueur; nombre++) {

            //affectation mission
            int mission;
            do {
                mission = ThreadLocalRandom.current().nextInt(0, 7+1);
            }while(nombre_joueur < liste_mission.get(mission).joueurs_min || nombre_joueur > liste_mission.get(mission).joueurs_max);

            //System.out.println("Le " + liste_joueur.get(nombre).getName() + " a pour couleur " + liste_joueur.get(nombre).getColor() + " et a comme mission \"" + liste_mission.get(mission).getName() + "\"");
            //System.out.println("Il posède en outre " + liste_joueur.get(nombre).getRavitaillement() + " renforts");
        }

        int compteur = 0;
        for (int i = 0; i < 42; i++){
            //affectation pays
            int choix_pays = ThreadLocalRandom.current().nextInt(0, liste_pays.size());

            String pays_choisi = liste_pays.get(choix_pays);
            liste_joueur.get(compteur).territoire.add(pays_choisi);

            liste_pays.remove(choix_pays);

            compteur++;

            if (compteur > nombre_joueur - 1){
                compteur = 0;
            }

        }
        for (int i = 0; i < nombre_joueur; i++) {
            System.out.println("\nLe " + liste_joueur.get(i).getName() + " a comme territoires :");
            for (int j = 0; j < liste_joueur.get(i).territoire.size(); j++) {
                System.out.print(liste_joueur.get(i).territoire.get(j) + " ");
            }
        }
    }
}