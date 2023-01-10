import extensions.CSVFile;
import extensions.File;
class Pokemath extends Program{


    final String CHEMIN_SAUVEGARDE = "../ressources/Joueur.csv";
    final String CHEMIN_LISTE_ATTAQUES = "../ressources/ListeAttaques.csv";
    final String CHEMIN_LISTE_POKEMONS = "../ressources/ListePokemon.csv";
    final String CHEMIN_LISTE_NIVEAU = "../ressources/Niveau.csv";


    ////////////////////////////////////////////////////////////////////////////////
    //                           FONCTIONS DE CLASSES                             //
    ////////////////////////////////////////////////////////////////////////////////

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

    Move newMove(String name, Element element, int power){
        Move move = new Move();
        move.name = name;
        move.element = element;
        move.power = power;
        return move;
    }

    String toString(Pokemon poke){
        return "[Nom: " + poke.name + ", Niveau: " + poke.niveau + ", PV: " + poke.pv + "]";
    }

    void testToStringPokemon(){
        assertEquals("[Nom: bulbizarre, Niveau: 10, PV: 45]", toString(newPokemon("bulbizarre", 10, 45, 1, Element.PLANTE, newMove("tranche herbe", Element.PLANTE, 5))));
        assertEquals("[Nom: salameche, Niveau: 10, PV: 45]", toString(newPokemon("salameche", 10, 45, 1, Element.FEU, newMove("lance flamme", Element.FEU, 8))));
        assertEquals("[Nom: carapuce, Niveau: 10, PV: 45]", toString(newPokemon("carapuce", 10, 45, 1, Element.EAU, newMove("pistolet a eau", Element.EAU, 6))));
    }

    String toString(Move move){
        return move.name + " qui fait " + move.power + " de degats.";
    }

    void testToStringMove(){
        assertEquals("tranche herbe qui fait 5 de degats.", toString(newMove("tranche herbe", Element.PLANTE, 5)));
        assertEquals("lance flamme qui fait 8 de degats.", toString(newMove("lance flamme", Element.FEU, 8)));
        assertEquals("pistolet a eau qui fait 6 de degats.", toString(newMove("pistolet a eau", Element.EAU, 6)));
    }


 
    
    ////////////////////////////////////////////////////////////////////////////////
    //                      FONCTIONS DE CONVERTION DE TYPE                       //
    ////////////////////////////////////////////////////////////////////////////////
    
    // Fonction qui convertit une chaine contenant un nombre en un entier
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


    Element toElement(String chaine){
        if(equals(chaine, "ACIER")){
            return Element.ACIER;
        }else if(equals(chaine, "COMBAT")){
            return Element.COMBAT;
        }else if(equals(chaine, "DRAGON")){
            return Element.DRAGON;
        }else if(equals(chaine, "EAU")){
            return Element.EAU;
        }else if(equals(chaine, "ELECTRIK")){
            return Element.ELECTRIK;
        }else if(equals(chaine, "FEE")){
            return Element.FEE;
        }else if(equals(chaine, "FEU")){
            return Element.FEU;
        }else if(equals(chaine, "GLACE")){
            return Element.GLACE;
        }else if(equals(chaine, "INSECTE")){
            return Element.INSECTE;
        }else if(equals(chaine, "PLANTE")){
            return Element.PLANTE;
        }else if(equals(chaine, "POISON")){
            return Element.POISON;
        }else if(equals(chaine, "PSY")){
            return Element.PSY;
        }else if(equals(chaine, "ROCHE")){
            return Element.ROCHE;
        }else if(equals(chaine, "SOL")){
            return Element.SOL;
        }else if(equals(chaine, "SPECTRE")){
            return Element.SPECTRE;
        }else if(equals(chaine, "TENEBRES")){
            return Element.TENEBRES;
        }else if(equals(chaine, "VOL")){
            return Element.VOL;
        }else if(equals(chaine, "NORMAL")){
            return Element.NORMAL;
        }else{
            return Element.AUCUN;
        } 
    }

    void testToElement(){
        assertEquals(Element.ACIER,toElement("ACIER"));
        assertEquals(Element.NORMAL,toElement("NORMAL"));
        assertEquals(Element.FEE,toElement("FEE"));
    }

    ////////////////////////////////////////////////////////////////////////////////
    //                            LECTURE_FICHIERS_CSV                            //
    ////////////////////////////////////////////////////////////////////////////////

    
    // Renvoie le numéro de ligne d'une chaine dans la première colonne
    int getNumLigne(CSVFile fichier, String chaine) {
        for(int numLigne = 1; numLigne < rowCount(fichier); numLigne++) {
            if (equals(getCell(fichier, numLigne, 0), chaine)) {
                return numLigne;
            }
        }
        return -1;
    }

    void testGetNumLigne() {
        assertEquals(2, getNumLigne(loadCSV(CHEMIN_LISTE_ATTAQUES, ';'), "Triplattaque"));
        assertEquals(618, getNumLigne(loadCSV(CHEMIN_LISTE_ATTAQUES, ';'), "Lumière du Néant"));
        assertEquals(1, getNumLigne(loadCSV(CHEMIN_LISTE_POKEMONS), "Pikachu"));
        assertEquals(5, getNumLigne(loadCSV(CHEMIN_LISTE_POKEMONS), "Sablaireau"));
    }

    // Retourne une ligne donnée dans un fichier
    String[] getLigne(CSVFile fichier, int numLigne) {
        String[] ligne = new String[columnCount(fichier)];
        for (int numColonne = 0; numColonne < columnCount(fichier); numColonne++) {
            ligne[numColonne] = "";
        }
        for (int numColonne = 0; numColonne < columnCount(fichier, numLigne); numColonne++) {
            ligne[numColonne] = getCell(fichier, numLigne, numColonne);
        }
        return ligne;
    }

    void testGetLigne() {
        assertArrayEquals(new String[]{"Triplattaque","Tri Attack","80","100","0","10","NORMAL","SPECIAL","Le lanceur envoie trois boules d’énergie simultanément. Peut aussi paralyser, brûler ou geler l’ennemi."}, getLigne(loadCSV(CHEMIN_LISTE_ATTAQUES, ';'), 2));
        assertArrayEquals(new String[]{"Lumière du Néant","Light of Ruin","140","90","0","5","FEE","SPECIAL",""}, getLigne(loadCSV(CHEMIN_LISTE_ATTAQUES, ';'), 618));
        assertArrayEquals(new String[]{"Pikachu","211","146","116","136","136","216","ELECTRIK","","Mimi-Queue","Vive-Attaque","Cage-Éclair","Tonerre"}, getLigne(loadCSV(CHEMIN_LISTE_POKEMONS), 1));
        assertArrayEquals(new String[]{"Sablaireau","75","100","110","45","55","65","SOL","","Griffe","Jet de Sable","Roulade","Combo-Griffe"}, getLigne(loadCSV(CHEMIN_LISTE_POKEMONS), 5));
    }

    Move loadAttaque(String nom) {
        CSVFile listeAttaques = loadCSV(CHEMIN_LISTE_ATTAQUES, ';');
        String[] stats = getLigne(listeAttaques, getNumLigne(listeAttaques, nom));
        return newMove(stats[0], toElement(stats[6]), toInt(stats[2]));

    }

    void testLoadAttaque() {
        Move attaque = loadAttaque("Triplattaque");
        assertEquals("Triplattaque", attaque.name);
        assertEquals(Element.NORMAL, attaque.element);
        assertEquals(80, attaque.power);
        Move attaque2 = loadAttaque("Larcin");
        assertEquals("Larcin", attaque2.name);
        assertEquals(Element.TENEBRES, attaque2.element);
        assertEquals(60, attaque2.power);

    }

    Pokemon loadPokemon(String nom) {
        CSVFile listePokemon = loadCSV(CHEMIN_LISTE_POKEMONS);
        String[] stats = getLigne(listePokemon, getNumLigne(listePokemon, nom));
        return newPokemon(stats[0], 100, toInt(stats[1]), toInt(stats[6]), toElement(stats[7]), loadAttaque(stats[9]));
    }

    void testLoadPokemon() {
        Pokemon pokemon = loadPokemon("Pikachu");
        assertEquals("Pikachu", pokemon.name);
        assertEquals(100, pokemon.niveau);
        assertEquals(Element.ELECTRIK, pokemon.type1);
        Pokemon pokemon2 = loadPokemon("Sablaireau");
        assertEquals("Sablaireau", pokemon2.name);
        assertEquals(100, pokemon2.niveau);
        assertEquals(Element.SOL, pokemon2.type1);

    }




    ////////////////////////////////////////////////////////////////////////////////
    //                             FONCTIONS DE JEU                               //
    ////////////////////////////////////////////////////////////////////////////////

    int questionPvAdversesRestantApresAttaque(Pokemon joueur, Pokemon adverse){
        println("Le " + adverse.name + " adverse a " + adverse.pv + " points de vies. Si votre " + joueur.name + " attaque avec " + joueur.attaque.name + " qui fait " + joueur.attaque.power + " de degats.");
        println("Combien reste t'il de points de vies a " + adverse.name +" ?");   
        return adverse.pv-joueur.attaque.power;
    }

    int questionNombreAttaquesNecessairesPourKO(Pokemon joueur, Pokemon adverse) {
        println("Le " + adverse.name + " adverse a " + adverse.pv + " points de vies. Si votre " + joueur.name + " attaque avec " + joueur.attaque.name + " qui fait " + joueur.attaque.power + " de degats.");
            println("Il faut attaquer combien de fois " + adverse.name +" avec " + joueur.attaque.name + " pour qu'il soit KO ?");   
            int pv = adverse.pv;
            int bonneReponse = 0;
            while(pv>0){
                pv = pv-joueur.attaque.power;
                bonneReponse++;    
            }
            return bonneReponse;
    }

    int questionPvJoueurRestantApresSoin(Pokemon joueur, Pokemon adverse) {
        int aleaSoin = (int) (random()*10) + 1;
        int aleaAttaque = (int) random()*3 + 1;
        int pvPerdu = adverse.attaque.power * aleaAttaque;
        println("Le pokemon " + joueur.name + " tiens comme objet les restes qui permet de le soigner de "+ aleaSoin +" pv " );
        println("Sachant qu'il possede " + joueur.pv +" pv et qu'il a  perdu pendant le combat "+ pvPerdu+ " pv, a combien de pv " + joueur.name + " sera t'il ?");
        return joueur.pv - pvPerdu + aleaSoin;
    }

    int questionPourcentageDeVieRestant(Pokemon joueur, Pokemon adverse) {
        int aleaPourCent = (int) (random()*100) + 1;
        int aleaAttaque = (int) random()*3 + 1;
        int pvPerdu = adverse.attaque.power * aleaAttaque;
        println("Le pokemon " + joueur.name + " à "+ joueur.pv +" pv " );
        println("Sachant qu'il a perdu " + pvPerdu +" pv par l'attaque "+ adverse.attaque.name + " , à combien DE POURCENTAGE de vie est t'il ?");
        return pvPerdu*100/joueur.pv;
    }
    // Fonction qui joue un niveau
    boolean niveau(int numNiveau, Pokemon joueur, Pokemon adverse, int idxJoueur) {
        boolean res = false;
        int bonneReponse = 0;
        if(numNiveau==1){
            bonneReponse = questionPvAdversesRestantApresAttaque(joueur, adverse);
        }else if(numNiveau==2){
            bonneReponse = questionNombreAttaquesNecessairesPourKO(joueur, adverse);
        }else if(numNiveau==3){
            bonneReponse = questionPvJoueurRestantApresSoin(joueur, adverse);
        }else if(numNiveau == 4 || numNiveau == 5){
            bonneReponse = questionPourcentageDeVieRestant(joueur, adverse);
        }

        int reponseJoueur = readInt();
        if (bonneReponse == reponseJoueur) {
            println("Bravo ! La reponse etait bien de " + bonneReponse + ".");
            res = true;
            CSVFile listeJoueur = loadCSV(CHEMIN_SAUVEGARDE, ',');
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
            saveCSV(csv, CHEMIN_SAUVEGARDE);
            println("Le niveau " + (numNiveau+1) + " est DISPONIBLE !!!");
        } else {
            println("Perdu :( " + joueur.name + " est K.O.");
        }
        return res;
    }

    void jouerNiveau(int idxJoueur){

        //Pokemon[] tableauPokemon = new Pokemon[]{bulbizarre, salameche, carapuce};
        Pokemon[] tableauPokemon = new Pokemon[]{loadPokemon("Pikachu"), loadPokemon("Dracaufeu"), loadPokemon("Évoli"), loadPokemon("Rafflesia"), loadPokemon("Sablaireau")};

        int numNiveau = choisirNiveau(idxJoueur);

        int aleaJoueur = (int) (random()*5);
        int aleaAdverse = (int) (random()*5);
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


    ////////////////////////////////////////////////////////////////////////////////
    //                              FONCTIONS DE MENU                             //
    ////////////////////////////////////////////////////////////////////////////////

    // Fonction qui affiche la liste des niveaux
    void afficherListeNiveau(CSVFile listeNiveau, CSVFile listeJoueur, int idxJoueur) {
        println("Liste des niveaux disponibles :");
        println();
        for(int idx = 1; idx < columnCount(listeJoueur); idx++) {
            if(equals(getCell(listeJoueur, idxJoueur, idx),"true")){
                println(getCell(listeNiveau, idx-1, 0));
            }
        }
        println();
        println("Liste des niveaux bloque:");
        println();
        for(int idx = 1; idx < columnCount(listeJoueur); idx++) {
            if(equals(getCell(listeJoueur, idxJoueur, idx),"false")){
                println(getCell(listeNiveau, idx-1, 0));
            }
        }
    }




    // Fonction qui demande au joueur de choisir un niveau
    int choisirNiveau(int idxJoueur){

        CSVFile listeNiveau = loadCSV(CHEMIN_LISTE_NIVEAU, ',');
        CSVFile listeJoueur = loadCSV(CHEMIN_SAUVEGARDE, ',');

        afficherListeNiveau(listeNiveau, listeJoueur, idxJoueur);

        
        
        println();
        boolean choisiNiveauDispo = false;
        int choixNiveau = 0;
        while(!choisiNiveauDispo){
            print("Saisissez le niveau que vous voulez lancer : ");
            choixNiveau = readInt();
            println();

            if(choixNiveau>rowCount(listeNiveau) || choixNiveau<=0){
                println("Le niveau " + choixNiveau + " n'existe pas.");

            }else if(equals(getCell(listeJoueur, idxJoueur, choixNiveau),"true")){
                println("Vous avez choisi le niveau " + choixNiveau);
                choisiNiveauDispo = true;

            }else{
                println("Le niveau " + choixNiveau + " n'est pas disponible.");
            }
        }
        return choixNiveau;
    }
    
    //Crée un nouveau joueur
    void creerJoueur(String nomJoueur){
        CSVFile listeJoueur = loadCSV(CHEMIN_SAUVEGARDE, ',');
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
        saveCSV(csv, CHEMIN_SAUVEGARDE);
        print("Bienvenue " + nomJoueur + " tu es un nouveau joueur !!!");
    }

    // Demande le nom du joueur
    int demandeJoueur(){
        boolean joueurInscrit = false;
        print("Veuillez inscrire votre nom : ");
        String nomJoueur = readString();
        CSVFile listeJoueur = loadCSV(CHEMIN_SAUVEGARDE, ',');
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

    ////////////////////////////////////////////////////////////////////////////////
    //                              ALGO PRINCIPAL                                //
    ////////////////////////////////////////////////////////////////////////////////

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