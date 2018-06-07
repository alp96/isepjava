import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import static java.lang.Math.floor;

public class Main {

    /*public static int getUserInput(String errorMessage, int greaterThan, int lessThan){
        greaterThan = 0;
        lessThan = 0;
        int res = -1;
        Scanner scan = new Scanner(System.in);
        do {
            if (scan.hasNextInt()) {
                res = scan.nextInt();
            } else {
                System.out.println(errorMessage);
                scan.nextLine();
            }
        } while (res > greaterThan || res < lessThan);
        return res;
    }*/

    public static int input(String message, int valeur_inf, int valeur_sup){
        int choix = -1;
        Scanner scan = new Scanner(System.in);
        do{
            try {
                choix = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(message);
                scan.nextLine();
            }
        } while (choix < valeur_inf || choix > valeur_sup);
        return choix;
    }

    public static void ravitaillement(int nombre_joueur, List<Player> liste_joueur, List<Unit> liste_unite, List<Territoire> liste_territoire){
        for (int k = 0; k < nombre_joueur; k++){
            while(liste_joueur.get(k).ravitaillement > 0) {
                if (liste_joueur.get(k).isHuman) {

                } else {
                    int choix_unite;
                    do {
                        choix_unite = ThreadLocalRandom.current().nextInt(0, 3);
                    } while (liste_unite.get(choix_unite).getCost() > liste_joueur.get(k).ravitaillement);
                    int choix_pays = ThreadLocalRandom.current().nextInt(0, liste_joueur.get(k).territoire.size());
                    if (choix_unite == 0) {
                        liste_territoire.get(liste_joueur.get(k).getTerritoire().get(choix_pays)).soldier.add(new Soldier());
                    } else if (choix_unite == 1) {
                        liste_territoire.get(liste_joueur.get(k).getTerritoire().get(choix_pays)).cavalry.add(new Cavalry());
                    } else {
                        liste_territoire.get(liste_joueur.get(k).getTerritoire().get(choix_pays)).cannon.add(new Cannon());
                    }
                    liste_joueur.get(k).ravitaillement -= liste_unite.get(choix_unite).getCost();
                }
            }
        }
    }

    public static List<List<Territoire>> chemin(int id_joueur, int nombre_deplacement, List<List<Territoire>> liste_chemin, List<Territoire> liste_territoire) {

        if (nombre_deplacement == 0) {
            return liste_chemin;
        }

        List<List<Territoire>> list_pays = new LinkedList<>();
        for (List<Territoire> pays : liste_chemin) {
            for (int l = 0; l < pays.get(pays.size() - 1).adjacent.size(); l++) {
                List<Territoire> ajout_territoire = new LinkedList<>();
                list_pays.add(ajout_territoire);
                for (int m = 0; m < pays.size() - 1; m++) {
                    if (id_joueur == liste_territoire.get(pays.get(m).id).owner) {
                        ajout_territoire.add(new Territoire(liste_territoire.get(pays.get(m).id)));
                    }
                }
                if (id_joueur == liste_territoire.get(pays.get(pays.size() - 1).id).owner) {
                    ajout_territoire.add(new Territoire(liste_territoire.get(pays.get(pays.size() - 1).id)));
                }
            }
        }

        int id_territoire = -1;
        int territoire_adjacent = -1;

        for (List<Territoire> pays : list_pays) {

            Territoire dernier_element = pays.get(pays.size() - 1);
            territoire_adjacent++;
            if (id_territoire != dernier_element.id) {
                territoire_adjacent = 0;
                id_territoire = dernier_element.id;
            }
            if (id_joueur == liste_territoire.get(pays.get(pays.size() - 1).adjacent.get(territoire_adjacent)).owner) {
                pays.add(liste_territoire.get(dernier_element.adjacent.get(territoire_adjacent)));
            }
        }

        //Ne garde que le premier et le dernier élement de chaque liste-chemin
        List<List<Territoire>> list_pays2 = new LinkedList<>();
        for (List<Territoire> pays : list_pays) {
            List<Territoire> list = new LinkedList<>();
            list.add(pays.get(0));
            list.add(pays.get(pays.size() - 1));
            list_pays2.add(list);
        }

        //retire les chemins départ/destinations identiques
        for (Iterator<List<Territoire>> iter = list_pays2.listIterator(); iter.hasNext(); ) {
            List<Territoire> territoireList = iter.next();
            if (territoireList.get(0).id == territoireList.get(territoireList.size() - 1).id) {
                iter.remove();
            }
        }

        //Retire les doublons de la liste
        List<List<Territoire>> list_pays3 = new LinkedList<>();
        for (List<Territoire> pays : list_pays2) {
            String a_chercher = pays.get(1).name;
            boolean pas_present = true;
            for (List<Territoire> pays_dispo : list_pays3) {
                if (Objects.equals(pays_dispo.get(1).name, a_chercher)) {
                    pas_present = false;
                    break;
                }
            }
            if (pas_present) {
                list_pays3.add(pays);
            }
        }

        //itérations sur la valeur de déplacement de l'unité
        return chemin(id_joueur, nombre_deplacement - 1, list_pays3, liste_territoire);
    }

    public static void main(String[] args) {

        //Interface test = new Interface();

        int nombre_joueur = 6;

        int[] ravitaillement = {0,0,40,35,30,25,20}; //liste des ravitaillements suivant le nombre de joueurs

        List<String> nom_pays = new LinkedList<>(Arrays.asList("Australie Orientale","Indonésie","Nouvelle-Guinée","Australie Occidentale",
                "Alaska","Alberta","Amérique Centrale","Etat de l'Est","Groenland","Territoire du Nord-Ouest","Ontario",
                "Québec","Etat de l'Ouest","Afghanistan","Chine","Inde","Tchita","Japon","Kamchatka","Moyen-Orient","Mongolie",
                "Siam","Sibérie","Oural","Yakoutie","Congo","Afrique Orientale","Egypte","Madagascar","Afrique du Nord","Afrique du Sud","Argentine",
                "Brésil","Pérou","Vénézuela","Grande-Bretagne","Islande","Europe du Nord","Scandinavie","Europe du Sud",
                "Ukraine","Europe Occidentale"));

        List<String> liste_pays = new LinkedList<>(Arrays.asList("0101","0102","0103","0104","0201","0202","0203","0204",
                "0205","0206","0207","0208","0209","0301","0302","0303","0304","0305","0306","0307","0308","0309","0310",
                "0311","0312","0401","0402","0403","0404","0405","0406","0501","0502","0503","0504","0601","0602","0603",
                "0604","0605","0606","0607")); //liste codes pays

        List<String> nom_joueur = new LinkedList<>(Arrays.asList("Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4", "Joueur 5", "Joueur 6"));

        List<String> nom_region = new LinkedList<>(Arrays.asList("Océanie","Amérique du Nord", "Asie","Afrique",
                "Amérique du Sud","Europe"));

        List<Integer> valeur_renfort = new LinkedList<>(Arrays.asList(2,5,7,3,2,5));

        List<Color> liste_couleur = new ArrayList<>(); //liste des couleurs des joueurs
        liste_couleur.add(Color.blue);
        liste_couleur.add(Color.red);
        liste_couleur.add(Color.green);
        liste_couleur.add(Color.yellow);
        liste_couleur.add(Color.orange);
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

        List<Territoire> liste_territoire = new ArrayList<>();
        for (int k  = 0; k < 42; k++){
            int region = Integer.valueOf(liste_pays.get(k).substring(0, 2));
            int territoire = Integer.valueOf(liste_pays.get(k).substring(2, 4));
            liste_territoire.add(new Territoire(nom_pays.get(k),k,region,territoire));
        }

        List<Region> liste_regions = new LinkedList<>();
        for (int  k = 0; k < 6; k++){
            List<Territoire> terriRegion = new LinkedList<>();
            for (int j = 0; j < 42; j++){
                if (Integer.valueOf(liste_pays.get(j).substring(0,2)) - 1 == k){
                    terriRegion.add(liste_territoire.get(liste_pays.indexOf(liste_pays.get(j))));
                }
            }
            liste_regions.add(new Region(nom_region.get(k),terriRegion,valeur_renfort.get(k)));
        }

        List<Player> liste_joueur = new LinkedList<>();

        for (int k = 0; k < nombre_joueur; k++){
            liste_joueur.add(new Player(nom_joueur.get(k),liste_couleur.get(k),0,ravitaillement[nombre_joueur]));
            if (k == 0){ //déclaration de 5 bots et d'un joueur humain
                liste_joueur.get(k).isHuman = false;
            }
            else{
                liste_joueur.get(k).isHuman = false;
            }
        }

        //génération des relations entre pays
        for (int k = 0; k < liste_relation.size(); k++){
            liste_territoire.get(Integer.valueOf(liste_relation.get(k).substring(0,2))).adjacent.add(Integer.valueOf(liste_relation.get(k).substring(2,4)));
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
            liste_territoire.get(pays_choisi).owner = compteur; //assigne le pays à un joueur

            liste_territoire.get(pays_choisi).soldier.add(new Soldier()); //Assigne un soldat au pays.
            liste_joueur.get(compteur).ravitaillement--; //retrait d'un soldat au total tavitaillement

            liste_pays3.remove(choix_pays);

            compteur++;

            if (compteur > nombre_joueur - 1){
                compteur = 0;
            }
        }

        //attribution des unités restantes dans les pays
        ravitaillement(liste_joueur.size(),liste_joueur, liste_unite, liste_territoire);

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
        }

        int numero_tour = 0;
        while (0<1){ //déroulé du jeu dans cette boucle infini
            numero_tour++; //numéro du tour augmente chaque tour
            System.out.println("\nTour n° " + numero_tour + ". ");

            for (int k = 0; k < nombre_joueur; k++){
                for (int j = 0; j < liste_regions.size(); j++){
                    boolean controle = true;
                    for (int l = 0; l < liste_regions.get(j).liste_territoire.size(); l++) {
                        controle = true;
                        if (liste_regions.get(j).liste_territoire.get(l).owner != k){
                            controle = false;
                            break;
                        }
                    }
                    if (controle){
                        liste_joueur.get(k).ravitaillement = liste_regions.get(j).valeur_renfort;
                        System.out.println("Le joueur " + (int) (k+1) + " reçoit " + liste_regions.get(j).valeur_renfort + " renforts pour détenir entièrement la région " + liste_regions.get(j).name);
                    }
                }

            }

            if (numero_tour > 1){ //attribution des nouveaux renforts
                for (int k = 0; k < liste_joueur.size(); k++){
                    if (liste_joueur.get(k).renfort_paysconquis != 0){
                        liste_joueur.get(k).ravitaillement += liste_joueur.get(k).renfort_paysconquis;
                        System.out.println("Le joueur " + (int) (k+1) + " reçoit " + liste_joueur.get(k).renfort_paysconquis + " renforts pour avoir conquis des territoires le tour précédent");
                        liste_joueur.get(k).renfort_paysconquis = 0;
                    }
                    liste_joueur.get(k).ravitaillement += (int) floor(liste_joueur.get(k).territoire.size()/3);
                }
                ravitaillement(liste_joueur.size(),liste_joueur, liste_unite, liste_territoire);
            }

            int count;
            for (count = 0; count < liste_joueur.size(); count++){

                int tour_joueur = ordre_final.get(count);
                while(liste_joueur.get(tour_joueur).territoire.isEmpty()){
                    count++;
                    tour_joueur = ordre_final.get(count);
                }
                for (int k = 0; k < 6; k++){
                    boolean gagnant = true;
                    for (int j = 0; j < 42; j++){
                        if (liste_territoire.get(j).owner != k){
                            gagnant = false;
                            break;
                        }
                    }
                    if (gagnant){
                        System.out.println("VICTOIRE DU JOUEUR " + (int) (k+1));
                        while (0<1){}
                    }
                }

                System.out.println("    Au tour de " + liste_joueur.get(tour_joueur).getName() + " !");


                if (liste_joueur.get(tour_joueur).isHuman) {
                    System.out.println("Liste de vos territoires (ID) :");
                    for (int j = 0; j < liste_joueur.get(ordre_final.get(count)).territoire.size(); j++) {
                        System.out.print(liste_territoire.get(liste_joueur.get(ordre_final.get(count)).territoire.get(j)).name + " (" +
                                liste_joueur.get(ordre_final.get(count)).territoire.get(j) + ")  ");
                    }
                }
                    Scanner scan = new Scanner(System.in);
                    while (0<1) {
                        int choix = -1;
                        int compteur_troupes = 0;
                        do { //vérification de l'input user
                            if (liste_joueur.get(tour_joueur).isHuman) {
                                System.out.println("\nChoisissez avec quel territoire vous désirez interagir");
                                choix = input("Merci de rentrer l'ID d'un territoire", 0, 41);
                            } else{
                                do {
                                    compteur_troupes++;
                                    choix = ThreadLocalRandom.current().nextInt(0, 42);
                                }while ((liste_territoire.get(choix).cavalry.size() + liste_territoire.get(choix).cannon.size()
                                        +liste_territoire.get(choix).soldier.size() <= 1 ) || compteur_troupes < 1000);
                            }
                        } while (liste_territoire.get(choix).owner != ordre_final.get(count));

                        Territoire pays_choisi = liste_territoire.get(choix);

                        System.out.println(pays_choisi.name + " : territoire " + pays_choisi.territoire +
                                " de la région " + pays_choisi.region);
                        System.out.println("Y sont stationnés " + pays_choisi.soldier.size() + " soldats, " + pays_choisi.cavalry.size()
                                + " cavaliers et " + pays_choisi.cannon.size() + " canons.");
                        System.out.println("Le pays a comme pays frontaliers : ");
                        for (int k = 0; k < pays_choisi.adjacent.size(); k++) {
                            System.out.print(liste_territoire.get(pays_choisi.adjacent.get(k)).name + " (" + pays_choisi.adjacent.get(k) +
                                    ") : "  + liste_joueur.get(liste_territoire.get(pays_choisi.adjacent.get(k)).owner).getName() + "   ");
                        }
                        if (pays_choisi.soldier.size() + pays_choisi.cannon.size() + pays_choisi.cavalry.size() > 1) {

                            if (liste_joueur.get(tour_joueur).isHuman) {
                                System.out.println("\n\nQue désirez-vous faire ?\n1) Attaquer depuis ce pays\n2) Déplacer des troupes"
                                        + "\n3) Fin du tour");

                                choix = input("Merci de rentrer un nombre valide !", 1, 3);
                            } else{
                                int decision = ThreadLocalRandom.current().nextInt(0, 100);
                                if (decision < 40) {
                                    choix = 1;
                                    System.out.println("\n      Attaque");
                                } else if (decision < 40) {
                                    choix = 2;
                                    System.out.println("\n      Déplacement");
                                } else {
                                    choix = 3;
                                    System.out.println("\n      Passe");
                                }
                            }

                            if (choix == 1) {
                                if (pays_choisi.soldier.size() > 1 || pays_choisi.cannon.size() > 0 || pays_choisi.cavalry.size() > 0) {

                                    if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Choix de la cible :");}
                                    compteur = 0;
                                    for (int k = 0; k < pays_choisi.adjacent.size(); k++) {
                                        if (liste_territoire.get(pays_choisi.adjacent.get(k)).owner != ordre_final.get(count)) {
                                            if (liste_joueur.get(tour_joueur).isHuman) {
                                                System.out.print(liste_territoire.get(pays_choisi.adjacent.get(k)).name + " (" + pays_choisi.adjacent.get(k) + ")   ");
                                            }
                                        } else {
                                            compteur++;
                                        }
                                    }
                                    if (compteur != pays_choisi.adjacent.size()) {
                                        if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("\n");}
                                        do {
                                            if (liste_joueur.get(tour_joueur).isHuman) {
                                                choix = input("Merci de rentrer un nombre valide !", 0, 42);
                                            }else{
                                                choix = ThreadLocalRandom.current().nextInt(0,42);
                                            }
                                        }
                                        while (liste_territoire.get(choix).owner == ordre_final.get(count) || !pays_choisi.adjacent.contains(choix)); //vérification que le pays appratient à un autre joueur et qu'il est à coté

                                        if (!liste_joueur.get(tour_joueur).isHuman){
                                            System.out.println("Le " + liste_joueur.get(tour_joueur).getName() + " attaque le territoire "
                                                    + liste_territoire.get(choix).name + " du " + liste_joueur.get(liste_territoire.get(choix).owner).getName());
                                        }

                                        int pays_defenseur = choix;
                                        List<Unit> armee_def = new LinkedList<>();

                                        if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Vous pouvez envoyer jusqu'à 3 unités au combat.");}
                                        List<Unit> armee_attaque = new LinkedList<>();
                                        int unite_total = pays_choisi.cannon.size() + pays_choisi.soldier.size() + pays_choisi.cavalry.size();

                                        if (pays_choisi.soldier.size() > 0) {
                                            if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Combien de soldats à envoyer ?");}
                                            do {
                                                if (liste_joueur.get(tour_joueur).isHuman) {
                                                    try {
                                                        choix = scan.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Merci de rentrer un nombre valide !");
                                                        scan.nextLine();
                                                    }
                                                } else{
                                                    choix = ThreadLocalRandom.current().nextInt(0,4);
                                                }
                                            }
                                            while (choix < 0 || choix > pays_choisi.soldier.size() || choix > 3 || choix >= unite_total);
                                            for (int k = 0; k < choix; k++) {
                                                armee_attaque.add(new Soldier());
                                            }
                                        }
                                        if (armee_attaque.size() < 3 && (unite_total - armee_attaque.size() > 1)) { //avant de rajouter d'autres troupes on vérifie que 3 soldats max soient pris
                                            if (pays_choisi.cavalry.size() > 0) {
                                                if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Combien de cavaliers à envoyer ?");}
                                                do {
                                                    if (liste_joueur.get(tour_joueur).isHuman) {
                                                        try {
                                                            choix = scan.nextInt();
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Merci de rentrer un nombre valide !");
                                                            scan.nextLine();
                                                        }
                                                    } else{
                                                        choix = ThreadLocalRandom.current().nextInt(0,4);
                                                    }
                                                }
                                                while (choix < 0 || choix > pays_choisi.cavalry.size() || choix + armee_attaque.size() > 3 || choix + armee_attaque.size() >= unite_total);
                                                for (int k = 0; k < choix; k++) {
                                                    armee_attaque.add(new Cavalry());
                                                }
                                            }

                                            if (armee_attaque.size() < 3 && (unite_total - armee_attaque.size() > 1)) {
                                                if (pays_choisi.cannon.size() > 0) {
                                                    if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Combien de canons à envoyer ?");}
                                                    do {
                                                        if (liste_joueur.get(tour_joueur).isHuman) {
                                                            try {
                                                                choix = scan.nextInt();
                                                            } catch (InputMismatchException e) {
                                                                System.out.println("Merci de rentrer un nombre valide !");
                                                                scan.nextLine();
                                                            }
                                                        } else{
                                                            choix = ThreadLocalRandom.current().nextInt(0,4);
                                                            System.out.println("3");
                                                        }
                                                    }

                                                    while (choix < 0 || choix > pays_choisi.cannon.size() || choix + armee_attaque.size() > 3 || choix + armee_attaque.size() >= unite_total);
                                                    for (int k = 0; k < choix; k++) {
                                                        armee_attaque.add(new Cannon());
                                                    }
                                                }
                                            }
                                        }
                                        if (armee_attaque.size() == 0){
                                            System.out.println("        Aucune armée n'a été finalement envoyé.");
                                        }
                                        else if (armee_attaque.size() > 0) {
                                            for (int k = 0; k < liste_territoire.get(pays_defenseur).soldier.size(); k++) {
                                                armee_def.add(new Soldier());
                                            }
                                            for (int k = 0; k < liste_territoire.get(pays_defenseur).cannon.size(); k++) {
                                                armee_def.add(new Cannon());
                                            }
                                            for (int k = 0; k < liste_territoire.get(pays_defenseur).cavalry.size(); k++) {
                                                armee_def.add(new Cavalry());
                                            }
                                            if (armee_def.size() > 2) {
                                                for (int k = 2; k < armee_def.size(); k++) {
                                                    armee_def.remove(k);
                                                }
                                            }

                                            List<List<Integer>> liste_tirage = new LinkedList();
                                            List<List<Unit>> liste_armee = new LinkedList<>();

                                            List<Integer> tirage_attaque = new LinkedList<>();
                                            List<Integer> tirage_defense = new LinkedList<>();

                                            List<Integer> liste_id_att = new LinkedList<>();
                                            List<Integer> liste_id_def = new LinkedList<>();

                                            List<Integer> liste_prio_att = new LinkedList<>();
                                            List<Integer> liste_prio_def = new LinkedList<>();

                                            liste_tirage.add(tirage_attaque);
                                            liste_tirage.add(tirage_defense);
                                            liste_armee.add(armee_attaque);
                                            liste_armee.add(armee_def);

                                            List<List<Integer>> comparaison = new LinkedList<>(Arrays.asList(liste_id_att,
                                                    tirage_attaque, liste_prio_att, liste_id_def, tirage_defense, liste_prio_def));


                                            for (int k = 0; k < armee_attaque.size(); k++) {
                                                liste_id_att.add(armee_attaque.get(k).id);
                                                tirage_attaque.add(ThreadLocalRandom.current().nextInt(armee_attaque.get(k).getMin_power(), armee_attaque.get(k).getMax_power() + 1));
                                                liste_prio_att.add(armee_attaque.get(k).getPriorityATT());
                                            }
                                            for (int k = 0; k < armee_def.size(); k++) {
                                                liste_id_def.add(armee_def.get(k).id);
                                                tirage_defense.add(ThreadLocalRandom.current().nextInt(armee_def.get(k).getMin_power(), armee_def.get(k).getMax_power() + 1));
                                                liste_prio_def.add(armee_def.get(k).getPriorityDEF());
                                            }

                                            while (!armee_attaque.isEmpty() && !armee_def.isEmpty()) {
                                                int max_attaque = 0;
                                                int max_def = 0;
                                                int id_attaque = 0;
                                                int id_def = 0;
                                                for (int k = 0; k < tirage_attaque.size(); k++) {
                                                    if (comparaison.get(1).get(k) > max_attaque) {
                                                        id_attaque = k;
                                                        max_attaque = comparaison.get(1).get(k);
                                                    } else if (comparaison.get(1).get(k) == max_attaque) {
                                                        if (comparaison.get(2).get(k) < comparaison.get(2).get(id_attaque)) {
                                                            id_attaque = k;
                                                        }
                                                    }
                                                }
                                                for (int k = 0; k < tirage_defense.size(); k++) {
                                                    if (comparaison.get(4).get(k) > max_def) {
                                                        id_def = k;
                                                        max_def = comparaison.get(4).get(k);
                                                    } else if (comparaison.get(4).get(k) == max_def) {
                                                        if (comparaison.get(5).get(k) < comparaison.get(5).get(id_def)) {
                                                            id_def = k;
                                                        }
                                                    }
                                                }

                                                //System.out.println(comparaison); //Affichage des tableaux de dés pour vérifier

                                                //suppression des pertes du territoire concerné
                                                if (max_attaque > max_def) {
                                                    System.out.println("Un " + armee_def.get(id_def).name + " du " + liste_joueur.get(liste_territoire.get(pays_defenseur).owner).getName() + " a été détruit !");
                                                    if (armee_def.get(id_def).id == 0) {
                                                        liste_territoire.get(pays_defenseur).soldier.remove(0);
                                                    } else if (armee_def.get(id_def).id == 1) {
                                                        liste_territoire.get(pays_defenseur).cavalry.remove(0);
                                                    } else {
                                                        liste_territoire.get(pays_defenseur).cannon.remove(0);
                                                    }
                                                    if (liste_territoire.get(pays_defenseur).cannon.size() == 0
                                                            && liste_territoire.get(pays_defenseur).cavalry.size() == 0
                                                            && liste_territoire.get(pays_defenseur).soldier.size() == 0) {
                                                        System.out.println("Victoire ! Le pays est conquis !");
                                                        int renfort_supp = ThreadLocalRandom.current().nextInt(0, 100);
                                                        if (renfort_supp > 49) {
                                                            liste_joueur.get(ordre_final.get(count)).renfort_paysconquis++;
                                                        }
                                                        liste_joueur.get(ordre_final.get(count)).territoire.add(liste_territoire.get(pays_defenseur).id);
                                                        Collections.sort(liste_joueur.get(ordre_final.get(count)).territoire);
                                                        liste_territoire.get(pays_defenseur).owner = ordre_final.get(count);
                                                        for (int k = 0; k < armee_attaque.size(); k++) {
                                                            if (armee_attaque.get(k).id == 0) {
                                                                pays_choisi.soldier.remove(0);
                                                                liste_territoire.get(pays_defenseur).soldier.add(new Soldier());
                                                            } else if (armee_attaque.get(k).id == 1) {
                                                                pays_choisi.cavalry.remove(0);
                                                                liste_territoire.get(pays_defenseur).cavalry.add(new Cavalry());
                                                            } else {
                                                                pays_choisi.cannon.remove(0);
                                                                liste_territoire.get(pays_defenseur).cannon.add(new Cannon());
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    System.out.println("Un " + armee_attaque.get(id_attaque).name + " du " + liste_joueur.get(pays_choisi.owner).getName() +
                                                            " a été détruit !");
                                                    if (armee_attaque.get(id_attaque).id == 0) {
                                                        pays_choisi.soldier.remove(0);
                                                    } else if (armee_attaque.get(id_attaque).id == 1) {
                                                        pays_choisi.cavalry.remove(0);
                                                    } else {
                                                        pays_choisi.cannon.remove(0);
                                                    }
                                                }
                                                //suppression de la référence de ces unités dans les combats suivants
                                                armee_def.remove(id_def);
                                                armee_attaque.remove(id_attaque);
                                                for (int k = 0; k < 6; k++) {
                                                    if (k >= 3) {
                                                        comparaison.get(k).remove(id_def);
                                                    } else {
                                                        comparaison.get(k).remove(id_attaque);
                                                    }
                                                }
                                            }
                                            System.out.println(armee_attaque.size());
                                        }
                                    }
                                    else{
                                        System.out.println("Pas de territoire voisin à envahir !");
                                    }
                                }else {
                                    System.out.println("Pas assez de troupes pour attaquer un voisin !");
                                }
                            }
                            else if (choix == 2) {

                                //vérification du nombre de pays adjacent appartenant au joueur
                                int compteur_possession = 0;
                                for (int k = 0; k < pays_choisi.adjacent.size(); k++){
                                    if (liste_territoire.get(pays_choisi.adjacent.get(k)).owner == tour_joueur){
                                        compteur_possession++;
                                    }
                                }

                                if (compteur_possession > 0) {
                                    int taille_deplacement;
                                    List<Unit> armee_deplacement;

                                    do {
                                        armee_deplacement = new LinkedList<>();
                                        int taille_armee_territoire = pays_choisi.soldier.size() + pays_choisi.cannon.size() + pays_choisi.cavalry.size();
                                        taille_deplacement = 0;

                                        if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Choissisez combien d'unités envoyer :");}

                                        if (pays_choisi.soldier.size() > 0) {
                                            if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Combien de soldats à envoyer ?");}
                                            do {
                                                if (liste_joueur.get(tour_joueur).isHuman) {
                                                    try {
                                                        choix = scan.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Merci de rentrer un nombre valide !");
                                                        scan.nextLine();
                                                    }
                                                }
                                                else {
                                                    choix = ThreadLocalRandom.current().nextInt(0,pays_choisi.soldier.size());
                                                }

                                            } while (choix < 0 || choix > pays_choisi.soldier.size() || choix > taille_armee_territoire - 1);
                                            taille_deplacement += choix;

                                            for (int k = 0; k < choix; k++) {
                                                armee_deplacement.add(new Soldier());
                                            }
                                        }
                                        if (pays_choisi.cavalry.size() > 0 && taille_deplacement + 1 < taille_armee_territoire) {
                                            if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Combien de cavaliers à envoyer ?");}
                                            do {
                                                if (liste_joueur.get(tour_joueur).isHuman) {
                                                    try {
                                                        choix = scan.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Merci de rentrer un nombre valide !");
                                                        scan.nextLine();
                                                    }
                                                } else {
                                                    choix = ThreadLocalRandom.current().nextInt(0,pays_choisi.cavalry.size());
                                                }
                                            }
                                            while (choix < 0 || choix > pays_choisi.cavalry.size() || choix + taille_deplacement > taille_armee_territoire - 1);
                                            taille_deplacement += choix;

                                            for (int k = 0; k < choix; k++) {
                                                armee_deplacement.add(new Cavalry());
                                            }
                                        }
                                        if (pays_choisi.cannon.size() > 0 && taille_deplacement + 1 < taille_armee_territoire) {
                                            if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Combien de canons à envoyer ?");}
                                            do {
                                                if (liste_joueur.get(tour_joueur).isHuman) {
                                                    try {
                                                        choix = scan.nextInt();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Merci de rentrer un nombre valide !");
                                                        scan.nextLine();
                                                    }
                                                }else{
                                                    choix = ThreadLocalRandom.current().nextInt(0,pays_choisi.cannon.size());
                                                }
                                            }
                                            while (choix < 0 || choix > pays_choisi.cannon.size() || choix + taille_deplacement > taille_armee_territoire - 1);

                                            for (int k = 0; k < choix; k++) {
                                                armee_deplacement.add(new Cannon());
                                            }
                                        }
                                    } while (taille_deplacement <= 0);

                                    int deplacement = 100;
                                    for (int k = 0; k < armee_deplacement.size(); k++) {
                                        if (armee_deplacement.get(k).getMpt() < deplacement) {
                                            deplacement = armee_deplacement.get(k).getMpt();
                                        }
                                    }
                                    List<List<Territoire>> chemin_disponible = new LinkedList<>();
                                    List<List<Territoire>> chemin_resultat;
                                    List<Territoire> chemin_depart = new LinkedList<>();
                                    chemin_depart.add(pays_choisi);
                                    chemin_disponible.add(chemin_depart);

                                    if (liste_joueur.get(tour_joueur).isHuman) {System.out.println("Choisissez le pays de déplacement :");}

                                    chemin_resultat = chemin(tour_joueur, deplacement, chemin_disponible, liste_territoire);

                                    if (!(chemin_resultat.size() == 1 && chemin_resultat.get(0).size() == 1)) {
                                        for (int k = 0; k < chemin_resultat.size(); k++) {
                                            System.out.println((k + 1) + ") " + chemin_resultat.get(k).get(1).name);
                                        }
                                        do {
                                            if (liste_joueur.get(tour_joueur).isHuman) {
                                                try {
                                                    choix = scan.nextInt();
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Merci de rentrer un nombre valide !");
                                                    scan.nextLine();
                                                }
                                            } else{
                                                choix = ThreadLocalRandom.current().nextInt(1,chemin_resultat.size()+1);
                                            }

                                        } while (choix < 1 || choix > chemin_resultat.size());

                                        for (int k = 0; k < armee_deplacement.size(); k++){
                                            if (armee_deplacement.get(k).id == 0){
                                                pays_choisi.soldier.remove(0);
                                                liste_territoire.get(chemin_resultat.get(choix-1).get(1).id).soldier.add(new Soldier()); //à changer
                                            }
                                            else{
                                                if (armee_deplacement.get(k).id == 1){
                                                    pays_choisi.cavalry.remove(0);
                                                    liste_territoire.get(chemin_resultat.get(choix-1).get(1).id).cavalry.add(new Cavalry());
                                                }
                                                else{
                                                    pays_choisi.cannon.remove(0);
                                                    liste_territoire.get(chemin_resultat.get(choix-1).get(1).id).cannon.add(new Cannon());
                                                }
                                            }
                                        }
                                    } else {
                                        System.out.println("Pas de territoire en votre possession à proximité !");
                                    }
                                }
                                else{
                                    System.out.println("Pas de territoire à proximité en votre possession pour déplacer des troupes.");
                                }
                            }
                            else{
                                break;
                            }
                        }
                        else{
                            System.out.println("\nPas assez de soldat présent pour interagir avec ce pays");
                        }
                    }
                }
            }
        }
    }