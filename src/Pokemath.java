import extensions.CSVFile;
import extensions.File;
class Pokemath extends Program{

    Pokemon newPokemon(String name, int niveau, int pv, int vitesse, Element type1, Move attaque){
        Pokemon poke = new Pokemon();
        poke.name  =name;
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
    boolean niveau(int numNiveau, Pokemon joueur, Pokemon adverse) {
        boolean res = false;
        int bonneReponse = 0;
        while(numNiveau==1 || numNiveau==2 || numNiveau==3 || numNiveau==4 || numNiveau==5){
        if(numNiveau==1){
            println("Le " + adverse.name + " adverse a " + adverse.pv + " points de vies. Si votre " + joueur.name + " attaque avec " + joueur.attaque.name);
            println("Combien reste t'il de points de vies a " + adverse.name +" ?");   
            bonneReponse = adverse.pv-joueur.attaque.power;
        }else if(numNiveau==2){
            println("Le " + adverse.name + " adverse a " + adverse.pv + " points de vies. Si votre " + joueur.name + " attaque avec " + joueur.attaque.name);
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
        }

        int reponseJoueur = readInt();
        if (bonneReponse == reponseJoueur) {
            println("Bravo ! La reponse etait bien de " + bonneReponse + ".");
            res = true;
            CSVFile listeNiveau = loadCSV("../ressources/Niveau.csv", ',');
            int nbLigne = rowCount(listeNiveau);
            int nbColonne = columnCount(listeNiveau);
            String [][] csv = new String[nbLigne][nbColonne];
            for(int lig = 0; lig < nbLigne; lig++) {
                for(int col = 0; col < nbColonne; col++) {
                    if(lig==numNiveau && col==2){
                        csv[lig][col]="true";
                    }else{
                        csv[lig][col]=getCell(listeNiveau, lig, col);
                    }
                }
            }
            saveCSV(csv, "../ressources/Niveau.csv");
            println("Le niveau " + (numNiveau+1) + " est DISPONIBLE !!!");
        } else {
            println("Perdu :( " + joueur.name + " est K.O.");
        }
        return res;
    }

    void jouerNiveau(){
        Move tranchHerbe = newMove("tranche herbe", Element.PLANTE, 5);
        Move lanceFlamme = newMove("lance flamme", Element.FEU, 8);
        Move pistoletEau = newMove("pistolet a eau", Element.EAU, 10);

        Pokemon bulbizarre = newPokemon("bulbizarre", 10, 45, 1, Element.PLANTE, tranchHerbe);
        Pokemon salameche = newPokemon("salameche", 10, 40, 3, Element.FEU, lanceFlamme);
        Pokemon carapuce = newPokemon("carapuce", 10, 50, 2, Element.EAU, pistoletEau);

        Pokemon[] tableauPokemon = new Pokemon[]{bulbizarre, salameche, carapuce};

        int numNiveau = choisirNiveau();

        int aleaJoueur = (int) (random()*3);
        int aleaAdverse = (int) (random()*3);
        while(aleaJoueur==aleaAdverse){
            aleaAdverse = (int) (random()*3);
        }

        boolean niveau = niveau(numNiveau, tableauPokemon[aleaJoueur], tableauPokemon[aleaAdverse]);
        int nbtour  = 2;
        while(niveau == false && nbtour != 0){
            println("Retente ta chance ! Il te reste " + nbtour  + " essaies");
            niveau = niveau(numNiveau, tableauPokemon[aleaJoueur], tableauPokemon[aleaAdverse]);
            nbtour= nbtour -1;
        }
    }

    // Fonction qui demande au joueur de choisir un niveau
    int choisirNiveau(){
        CSVFile listeNiveau = loadCSV("../ressources/Niveau.csv", ',');
        int nbLigne = rowCount(listeNiveau);
        int nbColonne = columnCount(listeNiveau);

        println("Liste des niveaux disponibles :");
        println();
        for(int idx = 0; idx < nbLigne; idx++) {
            if(equals(getCell(listeNiveau, idx, 2),"true")){
                println(getCell(listeNiveau, idx, 0) + " (" +getCell(listeNiveau, idx, 1) + ")");
            }
        }
        println();
        println("Liste des niveaux bloque:");
        println();
        for(int idx = 0; idx < nbLigne; idx++) {
            if(equals(getCell(listeNiveau, idx, 2),"false")){
                println(getCell(listeNiveau, idx, 0) + " (" +getCell(listeNiveau, idx, 1) + ")");
            }
        }
        println();
        boolean choisiNiveauDispo = true;
        int choixNiveau = 0;
        while(choisiNiveauDispo && (choixNiveau < 1 || choixNiveau > nbLigne)){
            print("Saisissez le niveau que vous voulez lancer : ");
            choixNiveau = readInt();
            println();
            println("Vous avez choisi le niveau " + choixNiveau);
            if(choixNiveau < 1 || choixNiveau > nbLigne){
                println("Ce niveau n'existe pas.");
            }
            if(equals(getCell(listeNiveau, choixNiveau-1, 2),"true")){
                choisiNiveauDispo = false;
            }else{
                println("Mais il n'est pas disponible.");
            }
        }
        return choixNiveau;
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
        
        while(!equals(stopjeu,"n")){
            jouerNiveau();
            print("Veut-tu continuer ? (y/n)");
            println();
            stopjeu = readString();
        }  
    }
}