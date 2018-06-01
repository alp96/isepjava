import jdk.management.cmm.SystemResourcePressureMXBean;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {

        Interface test = new Interface();

        int nombre_joueur = 6;

        int[] ravitaillement = {0,0,40,35,30,25,20}; //liste des ravitaillements suivant le nombre de joueurs

        List<String> nom_pays = new LinkedList<>(Arrays.asList("Australie Orientale","Indonésie","Nouvelle-Guinée","Australie Occidentale",
                "Alaska","Alberta","Amérique Centrale","Etat de l'Est","Groenland","Territoire du Nord-Ouest","Ontario",
                "Québec","Etat de l'Ouest","Afghanistan","Chine","Inde","Tchita","Japon","Kamchatka","Moyen-Orient","Mongolie",
                "Siam","Sibérie","Oural","Yakoutie","Congo","Afrique Orientale","Egypte","Madagascar","Afrique du Nord","Afrique su Sud","Argentine",
                "Brésil","Pérou","Vénézuela","Grande-Bretagne","Islande","Europe du Nord","Scandinavie","Europe du Sud",
                "Ukraine","Europe Occidentale"));

        List<String> liste_pays = new LinkedList<>(Arrays.asList("0101","0102","0103","0104","0201","0202","0203","0204",
                "0205","0206","0207","0208","0209","0301","0302","0303","0304","0305","0306","0307","0308","0309","0310",
                "0311","0312","0401","0402","0403","0404","0405","0406","0501","0502","0503","0504","0601","0602","0603",
                "0604","0605","0606","0607")); //liste codes pays

        List<String> nom_joueur = new LinkedList<>(Arrays.asList("Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5", "Joueur 6"));

        List<Color> liste_couleur = new ArrayList<>(); //liste des couleurs des joueurs
        liste_couleur.add(Color.blue);
        liste_couleur.add(Color.red);
        liste_couleur.add(Color.green);
        liste_couleur.add(Color.yellow);
        liste_couleur.add(Color.white);
        liste_couleur.add(Color.black);

        List<String> liste_relation = new LinkedList<>(Arrays.asList("0002","0003","0102","0103","0121","0200","0201","0203",
                "0300","0301","0302","0405","0409","0418","0504","0509","0510","0512","0607","0612","0634","0706","0710",
                "0711","0712","0809","0810","0811","0836","0904","0905","0908","0910","1005","1007","1008","1009","1011",
                "1012","1107","1108","1110","1205","1206","1207","1210","1314","1315","1319","1323","1340","1413","1415",
                "1420","1421","1422","1423","1513","1514","1519","1521","1618","1620","1622","1624","1718","1720","1804",
                "1816","1817","1820","1824","1913","1915","1926","1927","1939","1940","2014","2016","2017","2018","2022",
                "2101","2114","2115","2214","2216","2220","2223","2224","2313","2314","2322","2340","2416","2418","2422",
                "2526","2529","2530","2619","2625","2627","2628","2629","2630","2719","2726","2729","2739","2826","2830",
                "2925","2926","2927","2932","2941","3025","3026","3028","3132","3133","3229","3231","3233","3234","3331",
                "3332","3334","3406","3432","3433","3536","3537","3538","3541","3608","3635","3637","3639","3735","3738",
                "3739","3740","3741","3835","3836","3837","3840","3919","3927","3937","3940","3941","4013","4019","4023",
                "4037","4038","4039","4135","4137","4139","4129")); //liste relations entre pays

        List<Unit> liste_unite = new ArrayList<>();
        liste_unite.add(new Soldier());
        liste_unite.add(new Cavalry());
        liste_unite.add(new Cannon());

        List<Territoire> liste_pays2 = new ArrayList<>();
        for (int k  = 0; k < 42; k++){
            int region = Integer.valueOf(liste_pays.get(k).substring(0, 2));
            int territoire = Integer.valueOf(liste_pays.get(k).substring(2, 4));
            liste_pays2.add(new Territoire(nom_pays.get(k),k,region,territoire));
        }

        List<Player> liste_joueur = new LinkedList<>();

        /*Player joueur0 = new Player("Joueur 1", Color.blue, 0,ravitaillement[nombre_joueur]);
        Player joueur1 = new Player("Joueur 2", Color.red, 0,ravitaillement[nombre_joueur]);
        Player joueur2 = new Player("Joueur 3", Color.green, 0,ravitaillement[nombre_joueur]);
        Player joueur3 = new Player("Joueur 4", Color.yellow, 0,ravitaillement[nombre_joueur]);
        Player joueur4 = new Player("Joueur 5", Color.white, 0,ravitaillement[nombre_joueur]);
        Player joueur5 = new Player("Joueur 6", Color.black, 0,ravitaillement[nombre_joueur]);
        liste_joueur = Arrays.asList(joueur0,joueur1,joueur2,joueur3,joueur4,joueur5);*/

        for (int k = 0; k < nombre_joueur; k++){
            liste_joueur.add(new Player(nom_joueur.get(k),liste_couleur.get(k),0,ravitaillement[nombre_joueur]));
            if (k == 0){ //décalration de 5 bots et d'un joueur humain
                liste_joueur.get(k).isHuman = true;
            }
            else{
                liste_joueur.get(k).isHuman = false;
            }
        }

        //génération des relations entre pays
        for (int k = 0; k < liste_relation.size(); k++){
            liste_pays2.get(Integer.valueOf(liste_relation.get(k).substring(0,2))).adajacent.add(Integer.valueOf(liste_relation.get(k).substring(2,4)));
        }

        //instantiation des missions
        //à modifier
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

        //génération de l'ordre de jeu
        List<Integer> ordre_temp = new LinkedList<>();
        List<Integer> ordre_final = new ArrayList<>();
        for (int k = 0; k < liste_joueur.size(); k++){
            ordre_temp.add(k);
        }
        for (int k = 0; k < liste_joueur.size(); k++){
            int ordre = ThreadLocalRandom.current().nextInt(0,ordre_temp.size());
            ordre_final.add(ordre_temp.get(ordre));
            ordre_temp.remove(ordre);
        }
        System.out.println("L'ordre du jeu sera : ");
        for (int k = 0; k < liste_joueur.size(); k++){
            System.out.print(liste_joueur.get(ordre_final.get(k)).getName() + " ");
        }

        System.out.println(" ");
        for (int i = 0; i < nombre_joueur; i++) { //Affichage des territoires possédés par joueurs
            System.out.println("\nLe " + liste_joueur.get(i).getName() + " a comme territoires :");
            Collections.sort(liste_joueur.get(i).territoire); // tri des listes de territoires
            for (int j = 0; j < liste_joueur.get(i).territoire.size(); j++) {
                System.out.print(liste_joueur.get(i).territoire.get(j) + " ");
            }
            //System.out.println("\nIl lui reste " + liste_joueur.get(i).ravitaillement + " renfort");
        }



        int numero_tour = 0;
        while (0<1){ //déroulé du jeu dans cette boucle infini
            numero_tour++; //numéro du tour augmente chaque tour
            System.out.println("\nTour n° " + numero_tour + ". ");
            int count;
            for (count = 0; count < liste_joueur.size(); count++){

                int tour_joueur = ordre_final.get(count);
                //System.out.println(tour_joueur);

                System.out.println("Au tour de " + liste_joueur.get(tour_joueur).getName() + " !");

                if (liste_joueur.get(tour_joueur).isHuman){
                    System.out.println("Liste de vos territoires (ID) :");
                    for (int j = 0; j < liste_joueur.get(ordre_final.get(count)).territoire.size(); j++) {
                        System.out.print(liste_pays2.get(liste_joueur.get(ordre_final.get(count)).territoire.get(j)).name + " (" +
                                liste_joueur.get(ordre_final.get(count)).territoire.get(j) + ")  ");
                    }
                    Scanner scan = new Scanner(System.in);
                    while (0<1) {
                        int choix = -1;
                        do { //vérification de l'input user
                            do {
                                try {
                                    System.out.println("\nChoisissez avec quel territoire vous désirez interagir");
                                    choix = scan.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Merci de rentrer l'ID d'un territoire");
                                    scan.nextLine();
                                }
                            } while (choix < 0 || choix > 41);
                        } while (liste_pays2.get(choix).owner != ordre_final.get(count));

                        Territoire pays_choisi = liste_pays2.get(choix);

                        System.out.println(pays_choisi.name + " : territoire " + pays_choisi.territoire +
                                " de la région " + pays_choisi.region);
                        System.out.println("Y sont stationnés " + pays_choisi.soldier.size() + " soldats, " + pays_choisi.cavalry.size()
                                + " cavaliers et " + pays_choisi.cannon.size() + " canons.");
                        System.out.println("Le pays a comme pays frontaliers : ");
                        for (int k = 0; k < pays_choisi.adajacent.size(); k++) {
                            System.out.print(liste_pays2.get(pays_choisi.adajacent.get(k)).name + " (" + pays_choisi.adajacent.get(k) +
                                    ") : "  + liste_joueur.get(liste_pays2.get(pays_choisi.adajacent.get(k)).owner).getName() + "   ");
                        }
                        if (pays_choisi.soldier.size() > 1 || pays_choisi.cannon.size() > 0 || pays_choisi.cavalry.size() > 0) {
                            do {
                                try {
                                    System.out.println("\n\nQue désirez-vous faire ?\n1) Attaquer depuis ce pays\n2) Déplacer des troupes");
                                    choix = scan.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Merci de rentrer un nombre valide !");
                                    scan.nextLine();
                                }
                            } while (choix < 1 || choix > 2);

                            if (choix == 1) {
                                if (pays_choisi.soldier.size() > 1 || pays_choisi.cannon.size() > 0 || pays_choisi.cavalry.size() > 0) {
                                    System.out.println("Choix de la cible :");
                                    for (int k = 0; k < pays_choisi.adajacent.size(); k++) {
                                        if (liste_pays2.get(pays_choisi.adajacent.get(k)).owner != ordre_final.get(count)) {
                                            System.out.print(liste_pays2.get(pays_choisi.adajacent.get(k)).name + " (" + pays_choisi.adajacent.get(k) + ")   ");
                                        }
                                    }
                                    System.out.println("\n");
                                    do {
                                        do{
                                            try {
                                                choix = scan.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("Merci de rentrer un nombre valide !");
                                                scan.nextLine();
                                            }
                                        } while (choix < 0 || choix > 41);
                                    } while (liste_pays2.get(choix).owner == ordre_final.get(count) || !pays_choisi.adajacent.contains(choix)); //vérification que le pays appratient à un autre joueur et qu'il est à coté

                                    int pays_defenseur = choix;
                                    List<Unit> armee_def = new LinkedList<>();

                                    System.out.println("Vous pouvez envoyer jusqu'à 3 unités au combat.");
                                    List<Unit> armee_attaque = new LinkedList<>();
                                    int unite_total = pays_choisi.cannon.size() + pays_choisi.soldier.size() + pays_choisi.cavalry.size();

                                    if (pays_choisi.soldier.size() > 0){
                                        System.out.println("Combien de soldats à envoyer ?");
                                        do{
                                            try {
                                                choix = scan.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("Merci de rentrer un nombre valide !");
                                                scan.nextLine();
                                            }
                                        } while (choix < 0 || choix > pays_choisi.soldier.size() || choix > 3 || choix >= unite_total);
                                        for (int k = 0; k < choix; k++){
                                            armee_attaque.add(new Soldier());
                                        }
                                    }
                                    if (armee_attaque.size() < 3 && (unite_total - armee_attaque.size() > 1)) { //avant de rajouter d'autres troupes on vérifie que 3 soldats max soient pris
                                        if (pays_choisi.cavalry.size() > 0) {
                                            System.out.println("Combien de cavaliers à envoyer ?");
                                            do {
                                                try {
                                                    choix = scan.nextInt();
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Merci de rentrer un nombre valide !");
                                                    scan.nextLine();
                                                }
                                            }
                                            while (choix < 0 || choix > pays_choisi.cavalry.size() || choix + armee_attaque.size() > 3 || choix + armee_attaque.size() >= unite_total);
                                            for (int k = 0; k < choix; k++) {
                                                armee_attaque.add(new Cavalry());
                                            }
                                        }

                                        if (armee_attaque.size() < 3 && (unite_total - armee_attaque.size() > 1)) {
                                            if (pays_choisi.cannon.size() > 0) {
                                                System.out.println("Combien de canons à envoyer ?");
                                                do {
                                                    try {
                                                        choix = scan.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Merci de rentrer un nombre valide !");
                                                        scan.nextLine();
                                                    }
                                                }
                                                while (choix < 0 || choix > pays_choisi.cannon.size() || choix + armee_attaque.size() > 3 || choix + armee_attaque.size() >= unite_total);
                                                for (int k = 0; k < choix; k++) {
                                                    armee_attaque.add(new Cannon());
                                                }
                                            }
                                        }
                                    }
                                    if (armee_attaque.size() > 0){
                                        for (int k = 0; k < liste_pays2.get(pays_defenseur).soldier.size(); k++){
                                            armee_def.add(new Soldier());
                                        }
                                        for (int k = 0; k < liste_pays2.get(pays_defenseur).cannon.size(); k++){
                                            armee_def.add(new Cannon());
                                        }
                                        for (int k = 0; k < liste_pays2.get(pays_defenseur).cavalry.size(); k++){
                                            armee_def.add(new Cavalry());
                                        }
                                        if (armee_def.size() > 2){
                                            for (int k = 2; k < armee_def.size(); k++){
                                                armee_def.remove(k);
                                            }
                                        }
                                        List<List<Integer>> liste_tirage = new LinkedList();
                                        List<Integer> tirage_attaque = new LinkedList<>();
                                        List<Integer> tirage_defense = new LinkedList<>();
                                        List<List<Unit>> liste_armee = new LinkedList<>();

                                        liste_tirage.add(tirage_attaque);
                                        liste_tirage.add(tirage_defense);
                                        liste_armee.add(armee_attaque);
                                        liste_armee.add(armee_def);

                                        for (int k = 0; k < armee_attaque.size(); k++){
                                            tirage_attaque.add(ThreadLocalRandom.current().nextInt(armee_attaque.get(k).getMin_power(), armee_attaque.get(k).getMax_power()+1));
                                            System.out.println(tirage_attaque.get(k));
                                        }
                                        for (int k = 0; k < armee_def.size(); k++){
                                            tirage_defense.add(ThreadLocalRandom.current().nextInt(armee_def.get(k).getMin_power(), armee_def.get(k).getMax_power()+1));
                                            System.out.println(tirage_defense.get(k));
                                        }

                                        /*//on trie les listes dans l'ordre descendant
                                        Collections.max(tirage_attaque, Collections.reverseOrder());
                                        Collections.max(tirage_defense, Collections.reverseOrder());

                                        int min_troupe;
                                        if (tirage_attaque.size() > tirage_defense.size()){
                                            min_troupe = tirage_defense.size();
                                        }
                                        else{
                                            min_troupe = tirage_attaque.size();
                                        }

                                        for (int k = 0; k < min_troupe; k++){
                                            if (tirage_attaque.get(k) > tirage_defense.get(k)){
                                                perte_def.add(armee_def.get(k));
                                                if ()
                                            }
                                        }*/

                                        do {
                                            int max_attaque = 0;
                                            int max_def = 0;
                                            int prio_att = 0;
                                            int prio_def = 0;
                                            List<Integer> max = new ArrayList<>();
                                            max.add(max_attaque);
                                            max.add(max_def);
                                            for (int k = 0; k < 2; k++) { //on parcoure les 2 listes de dés pour trouver le max
                                                for (int j = 0; j < liste_tirage.get(k).size(); j++) {
                                                    if (max.get(k) < liste_tirage.get(k).get(j)){
                                                        max.set(k,liste_tirage.get(k).get(j));
                                                        if (k == 0) {
                                                            prio_att = liste_armee.get(k).get(j).getPriorityATT();
                                                        }
                                                        else{
                                                            prio_def = liste_armee.get(k).get(j).getPriorityDEF();
                                                        }
                                                    }
                                                    else if (max.get(k) == liste_tirage.get(k).get(j)){
                                                        if (liste_armee.get(k).get(j).getPriorityATT() > prio_att && k == 0){
                                                            
                                                        }
                                                    }
                                                }
                                            }
                                        }while(armee_attaque.size() != 0 || armee_def.size() != 0);
                                    }
                                    break;

                                } else {
                                    System.out.println("Pas assez de troupes pour attaquer un voison !");
                                }
                            }
                        }
                        else{
                            System.out.println("\nPas assez de soldat présent pour interagir avec ce pays");
                        }
                    }
                }
                else{
                    //rien si c'est une IA pour le moment
                }
            }
        }






        /*System.out.println("Tappez le code pays voulu (0 à 41)");
        while (0<1) { //affichage des territoires selon les choix du joueur
            int i;
            do {
                Scanner sc = new Scanner(System.in);
                i = sc.nextInt();
            }while(i < 0 || i > 41);
            Territoire pays_test = liste_pays2.get(i);

            System.out.println("Le territoire " + pays_test.name + " est le territoire " + pays_test.territoire +
                    " de la région " + pays_test.region + " et est la propriété de " + liste_joueur.get(pays_test.owner).getName());
            System.out.println("Y sont stationnés " + pays_test.soldier.size() + " soldats, " + pays_test.cavalry.size()
                    + " cavaliers et " + pays_test.cannon.size() + " canons.");
            System.out.println("Le pays a comme pays frontaliers : ");
            for (int k = 0; k < pays_test.adajacent.size(); k++){
                System.out.print(liste_pays2.get(pays_test.adajacent.get(k)).name + " (" + pays_test.adajacent.get(k)+ ")   ");
            }
            System.out.println("\n");
        }*/
    }
}