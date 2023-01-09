import extensions.CSVFile;
import extensions.File;
class Pokemath extends Program{

    Pokemon newPokemon(String name, int niveau, int pv, int vitesse, Element type1, Move attaque){
        Pokemon poke = new Pokemon();
        poke.name = name;
        poke.niveau = niveau;
        poke.pv = pv;
        poke.vitesse =vitesse;
        poke.type1 =type1;
        poke.attaque = attaque;
        return poke;
    }

    String toString(Pokemon poke){
        return "[Nom: " + poke.name + ", Niveau: " + poke.niveau + ", PV: " + poke.pv + "]";
    }
    void testToString(){
        assertEquals("[Nom: bulbizarre, Niveau: 10, PV: 45]", toString(newPokemon("bulbizarre", 10, 45, 1, Element.PLANTE, newMove("tranche herbe", Element.PLANTE, 5))));
        assertEquals("[Nom: salameche, Niveau: 10, PV: 45]", toString(newPokemon("salameche", 10, 45, 1, Element.FEU, newMove("lance flamme", Element.FEU, 8))));
        assertEquals("[Nom: carapuce, Niveau: 10, PV: 45]", toString(newPokemon("carapuce", 10, 45, 1, Element.EAU, newMove("pistolet a eau", Element.EAU, 6))));
    }
    void testToStringMove(){
        assertEquals("tranche herbe qui fait 5 de degats.", toString(newMove("tranche herbe", Element.PLANTE, 5)));
        assertEquals("lance flamme qui fait 8 de degats.", toString(newMove("lance flamme", Element.FEU, 8)));
        assertEquals("pistolet a eau qui fait 6 de degats.", toString(newMove("pistolet a eau", Element.EAU, 6)));
    }


    Move newMove(String name, Element element, int power){
        Move move = new Move();
        move.name = name;
        move.element = element;
        move.power = power;
        return move;
    }

    String toString(Move move){
        return move.name + " qui fait " + move.power + " de degats.";
    }

    ////////////////////////////////////////////////////////////////////////////////
    //                        FONCTIONS DE CONVERTION DE TYPE                     //
    ////////////////////////////////////////////////////////////////////////////////
    
    // Foncton qui convertit une chaine contenat un nombre en un entier
    int toInt(String str) {
        int idx = length(str) - 1;
        int multi = 1;
        int sum = 0;
        while (idx >= 0) {
            sum = sum + (charAt(str, idx) - '0') * multi;
            multi = multi * 10;
            idx = idx - 1;
        }
        return sum;
    }

    void testniveau(){
    }
    // Fonction qui joue un niveau
    boolean niveau(int numNiveau, Pokemon joueur, Pokemon adverse, int idxJoueur) {
        boolean res = false;
        int bonneReponse = 0;
        if(numNiveau==1){
            println("Le " + adverse.name + " adverse a " + adverse.pv + " points de vies. Si votre " + joueur.name + " attaque avec " + joueur.attaque.name + " qui fait " + joueur.attaque.power + " de degats.");
            println("Combien reste t'il de points de vies a " + adverse.name +" ?");   
            bonneReponse = adverse.pv-joueur.attaque.power;
        }else if(numNiveau==2){
            println("Le " + adverse.name + " adverse a " + adverse.pv + " points de vies. Si votre " + joueur.name + " attaque avec " + joueur.attaque.name + " qui fait " + joueur.attaque.power + " de degats.");
            println("Il faut attaquer combien de fois " + adverse.name +" avec " + joueur.attaque.name + " pour qu'il soit KO ?");   
            int pv = adverse.pv;
            while(pv>0){
                pv = pv-joueur.attaque.power;
                bonneReponse++;    
            }
        }else if(numNiveau==3){
            int aleaSoin = (int) (random()*10) + 1;
            int aleaAttaque = (int) random()*3 + 1;
            int pvPerdu = adverse.attaque.power * aleaAttaque;
            println("Le pokemon " + joueur.name + " tiens comme objet les restes qui permet de le soigner de "+ aleaSoin +" pv " );
            println("Sachant qu'il possede " + joueur.pv +" pv et qu'il a  perdu pendant le combat "+ pvPerdu+ " pv, a combien de pv " + joueur.name + " sera t'il ?");
            bonneReponse = joueur.pv - pvPerdu + aleaSoin;
        }else if(numNiveau==4){
            int aleaPourCent = (int) (random()*100) + 1;
            int aleaAttaque = (int) random()*3 + 1;
            int pvPerdu = adverse.attaque.power * aleaAttaque;
            println("Le pokemon " + joueur.name + " à "+ joueur.pv +" pv " );
            println("Sachant qu'il a perdu " + pvPerdu +" pv par l'attaque "+ adverse.attaque.name + " , à combien DE POURCENTAGE de vie est t'il ?");
            bonneReponse = pvPerdu*100/joueur.pv;
        }else if(numNiveau==5){
            int aleaPourCent = (int) (random()*100) + 1;
            int aleaAttaque = (int) random()*3 + 1;
            int pvPerdu = adverse.attaque.power * aleaAttaque;
            println("Le pokemon " + joueur.name + " à "+ joueur.pv +" pv " );
            println("Sachant qu'il a perdu " + pvPerdu +" pv par l'attaque "+ adverse.attaque.name + " , à combien DE POURCENTAGE de vie est t'il ?");
            bonneReponse = pvPerdu*100/joueur.pv;
        }

        int reponseJoueur = readInt();
        if (bonneReponse == reponseJoueur) {
            println("Bravo ! La reponse etait bien de " + bonneReponse + ".");
            res = true;
            CSVFile listeJoueur = loadCSV("../ressources/Joueur.csv", ',');
            int nbLigneJoueur = rowCount(listeJoueur);
            int nbColonneJoueur = columnCount(listeJoueur);
            String [][] csv = new String[nbLigneJoueur][nbColonneJoueur];
            for(int lig = 0; lig < nbLigneJoueur; lig++) {
                for(int col = 0; col < nbColonneJoueur; col++) {
                    if(col==numNiveau+1 && lig==idxJoueur){
                        csv[lig][col]="true";
                    }else{
                        csv[lig][col]=getCell(listeJoueur, lig, col);
                    }
                }
            }
            saveCSV(csv, "../ressources/Joueur.csv");
            println("Le niveau " + (numNiveau+1) + " est DISPONIBLE !!!");
        } else {
            println("Perdu :( " + joueur.name + " est K.O.");
        }
        return res;
    }

    void jouerNiveau(int idxJoueur){
        Move tranchHerbe = newMove("tranche herbe", Element.PLANTE, 5);
        Move lanceFlamme = newMove("lance flamme", Element.FEU, 8);
        Move pistoletEau = newMove("pistolet a eau", Element.EAU, 10);

        Pokemon bulbizarre = newPokemon("bulbizarre", 10, 45, 1, Element.PLANTE, tranchHerbe);
        Pokemon salameche = newPokemon("salameche", 10, 40, 3, Element.FEU, lanceFlamme);
        Pokemon carapuce = newPokemon("carapuce", 10, 50, 2, Element.EAU, pistoletEau);

        Pokemon[] tableauPokemon = new Pokemon[]{bulbizarre, salameche, carapuce};

        int numNiveau = choisirNiveau(idxJoueur);

        int aleaJoueur = (int) (random()*3);
        int aleaAdverse = (int) (random()*3);
        while(aleaJoueur==aleaAdverse){
            aleaAdverse = (int) (random()*3);
        }

        boolean niveau = niveau(numNiveau, tableauPokemon[aleaJoueur], tableauPokemon[aleaAdverse], idxJoueur);
        int nbtour  = 2;
        while(niveau == false && nbtour != 0){
            println("Retente ta chance ! Il te reste " + nbtour  + " essaies");
            niveau = niveau(numNiveau, tableauPokemon[aleaJoueur], tableauPokemon[aleaAdverse], idxJoueur);
            nbtour= nbtour -1;
        }
    }

    // Fonction qui demande au joueur de choisir un niveau
    int choisirNiveau(int idxJoueur){
        CSVFile listeNiveau = loadCSV("../ressources/Niveau.csv", ',');
        int nbLigneNiveau = rowCount(listeNiveau);
        int nbColonneNiveau = columnCount(listeNiveau);
        CSVFile listeJoueur = loadCSV("../ressources/Joueur.csv", ',');
        int nbLigneJoueur = rowCount(listeJoueur);
        int nbColonneJoueur = columnCount(listeJoueur);

        println("Liste des niveaux disponibles :");
        println();
        for(int idx = 1; idx < nbColonneJoueur; idx++) {
            if(equals(getCell(listeJoueur, idxJoueur, idx),"true")){
                println(getCell(listeNiveau, idx-1, 0));
            }
        }
        println();
        println("Liste des niveaux bloque:");
        println();
        for(int idx = 1; idx < nbColonneJoueur; idx++) {
            if(equals(getCell(listeJoueur, idxJoueur, idx),"false")){
                println(getCell(listeNiveau, idx-1, 0));
            }
        }
        println();
        boolean choisiNiveauDispo = true;
        int choixNiveau = 0;
        while(choisiNiveauDispo){
            print("Saisissez le niveau que vous voulez lancer : ");
            choixNiveau = readInt();
            println();
            println("Vous avez choisi le niveau " + choixNiveau);
            if(choixNiveau>nbLigneNiveau || choixNiveau<=0){
                println("Mais il n'existe pas.");
            }else if(equals(getCell(listeJoueur, idxJoueur, choixNiveau),"true")){
                choisiNiveauDispo = false;
            }else{
                println("Mais il n'est pas disponible.");
            }
        }
        return choixNiveau;
    }
     
    void creerJoueur(String nomJoueur){
        CSVFile listeJoueur = loadCSV("../ressources/Joueur.csv", ',');
        int nbLigneJoueur = rowCount(listeJoueur);
        int nbColonneJoueur = columnCount(listeJoueur);
        String [][] csv = new String[nbLigneJoueur+1][nbColonneJoueur];
        for(int lig = 0; lig < nbLigneJoueur; lig++) {
            for(int col = 0; col < nbColonneJoueur; col++) {
                csv[lig][col]=getCell(listeJoueur, lig, col);
            }
        }
        for(int col = 0; col < nbColonneJoueur; col++){
            if(col==0){
                csv[nbLigneJoueur][col]= nomJoueur;
                println("nomjoueur ok");
            }else if(col==1){
                println("true ok");
                csv[nbLigneJoueur][col]= "true";
            }else{
                println("false ok");
                csv[nbLigneJoueur][col]= "false";
            }
        }
        saveCSV(csv, "../ressources/Joueur.csv");
        print("Bienvenue " + nomJoueur + " tu es un nouveau joueur !!!");
    }

    int demandeJoueur(){
        boolean joueurInscrit = false;
        print("Veuillez inscrire votre nom : ");
        String nomJoueur = readString();
        CSVFile listeJoueur = loadCSV("../ressources/Joueur.csv", ',');
        int nbLigneJoueur = rowCount(listeJoueur);
        int nbColonneJoueur = columnCount(listeJoueur);
        int idxJoueur = 0;
        for(int idx=0; idx<nbLigneJoueur; idx++){
            if(equals(getCell(listeJoueur, idx, 0),nomJoueur)){
                println("Bienvenue " + nomJoueur + " ! :)");
                idxJoueur = idx;
                joueurInscrit = true;
            }
        }
        if(!joueurInscrit){
            creerJoueur(nomJoueur);
            idxJoueur = nbLigneJoueur;
        }
        return idxJoueur;
    }

    // Algo Principale
    void algorithm(){
        clearScreen();
        println();
        File titre = newFile("../ressources/title.txt");
         while (ready(titre)){
            println(readLine(titre));
        }
        println();
        println("Bienvenue sur PokeMath !");
        println();

        CSVFile ListePokemon = loadCSV("../ressources/ListePokemon.csv");
        //println(getCell(ListePokemon,1,0)); //permet de recup les info dun pokemon
        String stopjeu =  "";
        
        int idxJoueur = demandeJoueur();

        while(!equals(stopjeu,"n")){
            jouerNiveau(idxJoueur);
            print("Veut-tu continuer ? (y/n)");
            println();
            stopjeu = readString();
        }  
    }
}