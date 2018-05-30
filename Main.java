import jdk.management.cmm.SystemResourcePressureMXBean;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {

        Interface test = new Interface();

        int nombre_joueur = 5;

        int[] ravitaillement = {0,0,40,35,30,25,20};

        List<String> nom_pays = new LinkedList<>(Arrays.asList("Australie Orientale","Indonésie","Nouvelle-Guinée","Australie Occidentale",
                "Alaska","Alberta","Amérique Centrale","Etat de l'Est","Groenland","Territoire du Nord-Ouest","Ontario",
                "Québec","Etat de l'Ouest","Afghanistan","Chine","Inde","Tchita","Japon","Kamchatka","Moyen-Orient","Mongolie",
                "Siam","Sibérie","Oural","Yakoutie","Congo","Afrique Orientale","Egypte","Madagascar","Afrique du Nord","Afrique su Sud","Argentine",
                "Brésil","Pérou","Vénézuela","Grande-Bretagne","Islande","Europe du Nord","Scandinavie","Europe du Sud",
                "Ukraine","Europe Occidentale"));

        List<String> liste_pays = new LinkedList<>(Arrays.asList("0101","0102","0103","0104","0201","0202","0203","0204",
                "0205","0206","0207","0208","0209","0301","0302","0303","0304","0305","0306","0307","0308","0309","0310",
                "0311","0312","0401","0402","0403","0404","0405","0406","0501","0502","0503","0504","0601","0602","0603",
                "0604","0605","0606","0607"));

        //List<String> nom_joueur = new LinkedList<>(Arrays.asList("Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5", "Joueur 6"));

        List<String> liste_relation = new LinkedList<>(Arrays.asList("0002","0003","0102","0103","0121","0200","0201","0203",
                "0300","0301","0302","0405","0409","0418","0504","0509","0510","0512","0607","0612","0634","0706","0710",
                "0711","0712","0809","0810","0811","0836","0904","0905","0908","0910","1005","1007","1008","1009","1011",
                "1012","1107","1108","1110","1205","1206","1207","1210","1314","1315","1319","1323","1340","1413","1415",
                "1420","1421","1422","1423","1513","1514","1519","1521","1618","1620","1622","1624","1718","1720","1804",
                "1816","1817","1820","1824","1913","1915","1926","1927","1939","1940","2014","2016","2017","2018","2022",
                "2101","2114","2115","2214","2216","2220","2223","2224","2313","2314","2322","2340","2416","2418","2422",
                "2526","2529","2530","2619","2625","2627","2628","2629","2630","2719","2726","2729","2739","2826","2830",
                "2925","2926","2927","2932","2941","3025","3026","3028","3132","3133","3229","3231","3233","3234","3331",
                "3332","3334","3406","3432","3433","3536","3537","3538","3541","3629","3635","3637","3639","3735","3738",
                "3739","3740","3741","3835","3836","3837","3840","3919","3927","3937","3940","3941","4013","4019","4023",
                "4037","4038","4039","4135","4137","4139","4129"));

        List<Unit> liste_unite = new ArrayList<Unit>();
        liste_unite.add(new Soldier());
        liste_unite.add(new Cavalry());
        liste_unite.add(new Cannon());

        List<Territoire> liste_pays2 = new ArrayList<Territoire>();
        for (int k  = 0; k < 42; k++){
            int region = Integer.valueOf(liste_pays.get(k).substring(0, 2));
            int territoire = Integer.valueOf(liste_pays.get(k).substring(2, 4));
            liste_pays2.add(new Territoire(nom_pays.get(k),k,region,territoire));
        }

        List<Player> liste_joueur;

        Player joueur0 = new Player("Joueur 1", Color.blue, 0,ravitaillement[nombre_joueur]);
        Player joueur1 = new Player("Joueur 2", Color.red, 0,ravitaillement[nombre_joueur]);
        Player joueur2 = new Player("Joueur 3", Color.green, 0,ravitaillement[nombre_joueur]);
        Player joueur3 = new Player("Joueur 4", Color.yellow, 0,ravitaillement[nombre_joueur]);
        Player joueur4 = new Player("Joueur 5", Color.white, 0,ravitaillement[nombre_joueur]);
        Player joueur5 = new Player("Joueur 6", Color.black, 0,ravitaillement[nombre_joueur]);
        liste_joueur = Arrays.asList(joueur0,joueur1,joueur2,joueur3,joueur4,joueur5);

        for (int k = 0; k < nombre_joueur; k++){

        }

        //génération des relations entre pays
        for (int k = 0; k < liste_relation.size(); k++){
            liste_pays2.get(Integer.valueOf(liste_relation.get(k).substring(0,2))).adajacent.add(Integer.valueOf(liste_relation.get(k).substring(2,4)));
        }

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
        List<Integer> liste_pays3 = new LinkedList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,
                22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41));
        for (int i = 0; i < 42; i++){
            //affectation pays
            int choix_pays = ThreadLocalRandom.current().nextInt(0, liste_pays3.size());

            int pays_choisi = liste_pays3.get(choix_pays);
            liste_joueur.get(compteur).territoire.add(pays_choisi);
            liste_pays2.get(pays_choisi).owner = compteur; //assigne le pays à un joueur

            liste_pays2.get(pays_choisi).soldier.add(new Soldier()); //Assigne un soldat au pays.
            liste_joueur.get(compteur).ravitaillement--; //retrait d'un soldat au total tavitaillement

            liste_pays3.remove(choix_pays);

            compteur++;

            if (compteur > nombre_joueur - 1){
                compteur = 0;
            }
        }

        for (int k = 0; k < nombre_joueur; k++){
            while(liste_joueur.get(k).ravitaillement > 0) {
                int choix_unite;
                do {
                    choix_unite = ThreadLocalRandom.current().nextInt(0, 3);
                } while (liste_unite.get(choix_unite).getCost() > liste_joueur.get(k).ravitaillement);
                int choix_pays = ThreadLocalRandom.current().nextInt(0, liste_joueur.get(k).territoire.size());
                if (choix_unite == 0) {
                    liste_pays2.get(liste_joueur.get(k).getTerritoire().get(choix_pays)).soldier.add(new Soldier());
                }
                else if(choix_unite == 1){
                    liste_pays2.get(liste_joueur.get(k).getTerritoire().get(choix_pays)).cavalry.add(new Cavalry());
                }
                else{
                    liste_pays2.get(liste_joueur.get(k).getTerritoire().get(choix_pays)).cannon.add(new Cannon());
                }
                liste_joueur.get(k).ravitaillement -= liste_unite.get(choix_unite).getCost();
            }
        }

        for (int i = 0; i < nombre_joueur; i++) { //Affichage des territoires possédés par joueurs
            System.out.println("\nLe " + liste_joueur.get(i).getName() + " a comme territoires :");
            Collections.sort(liste_joueur.get(i).territoire); // tri des listes de territoires
            for (int j = 0; j < liste_joueur.get(i).territoire.size(); j++) {
                System.out.print(liste_joueur.get(i).territoire.get(j) + " ");
            }
            System.out.println("\nIl lui reste " + liste_joueur.get(i).ravitaillement + " renfort");
        }

        System.out.println(" ");
        while (0<1) { //affhichage des territoires au choix du joueur
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            Territoire pays_test = liste_pays2.get(i);

            //System.out.println(pays_test.owner);
            System.out.println("Le territoire " + pays_test.name + " est le territoire " + pays_test.territoire +
                    " de la région " + pays_test.region + " et est la propriété de " + liste_joueur.get(pays_test.owner).getName());
            System.out.println("Y sont stationnés " + pays_test.soldier.size() + " soldats, " + pays_test.cavalry.size()
                    + " cavaliers et " + pays_test.cannon.size() + " canons.");
            System.out.println("Le pays a comme pays frontaliers : ");
            for (int k = 0; k < pays_test.adajacent.size(); k++){
                System.out.print(liste_pays2.get(pays_test.adajacent.get(k)).name + "   ");
            }
            System.out.println("\n");
        }
    }
}