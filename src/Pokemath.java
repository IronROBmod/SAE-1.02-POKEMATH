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

    Pokemon newPokemon(String nom, int pv, int vitesse, Element element1, Element element2, Move attaque){
        Pokemon poke = new Pokemon();
        poke.nom = nom;
        poke.pv = pv;
        poke.MAXPV = pv;
        poke.vitesse = vitesse;
        poke.element1 = element1;
        poke.element2 = element2;
        poke.attaque = attaque;
        return poke;
    }

    Move newMove(String nom, Element element, int power){
        Move move = new Move();
        move.nom = nom;
        move.element = element;
        move.power = power;
        return move;
    }

    String toString(Pokemon poke){
        String infoPoke = "[Nom: " + poke.nom + ", Type : " + poke.element1 ;
        if(poke.element2 != Element.AUCUN ){
            infoPoke = infoPoke +  " / " + poke.element2 ;
        }
        infoPoke = infoPoke + ", Points de Vie : " + poke.pv + "]";
        return infoPoke ;
    }

    void testToStringPokemon(){
        //assertEquals("[Nom: Bulbizarre, Type : PLANTE, Points de Vie : 45]", toString(loadPokemon("Bulbizarre")));    pas dans la liste
        assertEquals("[Nom: Dracaufeu, Type : FEU / VOL, Points de Vie : 297]", toString(loadPokemon("Dracaufeu")));
        //assertEquals("[Nom: Salameche, Type : FEU, Points de Vie : 45]", toString(loadPokemon("Salameche")));     pas dans la liste
        assertEquals("[Nom: Carapuce, Type : EAU, Points de Vie : 229]", toString(loadPokemon("Carapuce")));
        assertEquals("[Nom: Pikachu, Type : ELECTRIK, Points de Vie : 211]", toString(loadPokemon("Pikachu")));
        assertEquals("[Nom: Evoli, Type : NORMAL, Points de Vie : 251]", toString(loadPokemon("Evoli")));
    }

    String toString(Move move){
        return move.nom + " qui fait " + move.power + " de degats.";
    }

    void testToStringMove(){
        assertEquals("tranche herbe qui fait 5 de degats.", toString(newMove("tranche herbe", Element.PLANTE, 5)));
        assertEquals("lance flamme qui fait 8 de degats.", toString(newMove("lance flamme", Element.FEU, 8)));
        assertEquals("pistolet a eau qui fait 6 de degats.", toString(newMove("pistolet a eau", Element.EAU, 6)));
    }


 
    
    ////////////////////////////////////////////////////////////////////////////////
    //                      FONCTIONS DE CONVERTION DE TYPE                       //
    ////////////////////////////////////////////////////////////////////////////////
    
    // Fonction qui convertit une chaine contenant un nombre en un entier ( permet de lire les stats dans les csv)
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
    boolean intIsValide(String chaine){
        for(int cpt = 0 ; length(chaine) > cpt ; cpt++){
            if(charAt(chaine,cpt) >= '0' && charAt(chaine,cpt) <= '9'){
                return true;
            }
        }
        return false;
    }

    String intToString(int nombre) {
        return nombre + "";
    }

    void testIntToString(){
        assertEquals("0", intToString(0));
        assertEquals("1", intToString(1));
        assertEquals("2", intToString(2));
        assertEquals("3", intToString(3));
        assertEquals("4", intToString(4)); 
        assertEquals("5", intToString(5));
        assertEquals("6", intToString(6));
        assertEquals("7", intToString(7));
        assertEquals("8", intToString(8));
        assertEquals("9", intToString(9));
        assertEquals("35", intToString(35));
        assertEquals("100", intToString(100));

    }

    // Convertit une chaine vers un type element
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
        }else if(equals(chaine,"NORMAL")){
            return Element.NORMAL;
        }
        return Element.AUCUN;
    }

    void testToElement(){
        assertEquals(Element.ACIER,toElement("ACIER"));
        assertEquals(Element.NORMAL,toElement("NORMAL"));
        assertEquals(Element.FEE,toElement("FEE"));
        assertEquals(Element.AUCUN,toElement(""));
    }

    // Converti une chaine en tableau de chaine avec une ligne par case
    String[] toTab(String chaine) {
        // Compte le nombre de retour à la ligne dans la chaine
        int nbRetourLigne = 0;
        for(int idxChaine = 0; idxChaine < length(chaine); idxChaine++) {
            if(charAt(chaine, idxChaine) == '\n') {
                nbRetourLigne++;
            }
        }
        // Crée et initialise un tableau de chaines vide
        String[] tab = new String[nbRetourLigne + 1];
        for (int idx = 0; idx < length(tab); idx++) {
            tab[idx] = "";
        }

        // Remplie le tableau avec la chaine
        int idxTab = 0;
        for(int idxChaine = 0; idxChaine < length(chaine); idxChaine++) {
            // Si retour à la ligne, passe à la case suivante
            if(charAt(chaine, idxChaine) == '\n') {
                idxTab++;
            // Sinon, ajoute le charactère à la chaine de la case actuelle
            } else {
                tab[idxTab] = tab[idxTab] + charAt(chaine, idxChaine);
            }
        }
        return tab;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //                            LECTURE DE FICHIERS                             //
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
        assertArrayEquals(new String[]{"Pikachu","211","146","116","136","136","216","ELECTRIK","","Mimi-Queue","Vive-Attaque","Cage-Éclair","Tonnerre"}, getLigne(loadCSV(CHEMIN_LISTE_POKEMONS), 1));
        assertArrayEquals(new String[]{"Sablaireau","291","236","256","126","146","166","SOL","","Griffe","Jet de Sable","Roulade","Combo-Griffe"}, getLigne(loadCSV(CHEMIN_LISTE_POKEMONS), 5));
    }

    Move loadAttaque(String nom) {
        CSVFile listeAttaques = loadCSV(CHEMIN_LISTE_ATTAQUES, ';');
        String[] stats = getLigne(listeAttaques, getNumLigne(listeAttaques, nom));
        return newMove(stats[0], toElement(stats[6]), toInt(stats[2]));

    }

    void testLoadAttaque() {
        Move attaque = loadAttaque("Triplattaque");
        assertEquals("Triplattaque", attaque.nom);
        assertEquals(Element.NORMAL, attaque.element);
        assertEquals(80, attaque.power);
        Move attaque2 = loadAttaque("Larcin");
        assertEquals("Larcin", attaque2.nom);
        assertEquals(Element.TENEBRES, attaque2.element);
        assertEquals(60, attaque2.power);

    }

    Pokemon loadPokemon(String nom) {
        CSVFile listePokemon = loadCSV(CHEMIN_LISTE_POKEMONS);
        String[] stats = getLigne(listePokemon, getNumLigne(listePokemon, nom));
        return newPokemon(stats[0], toInt(stats[1]), toInt(stats[6]), toElement(stats[7]), toElement(stats[8]), loadAttaque(stats[9]));
    }

    void testLoadPokemon() {
        Pokemon pokemon = loadPokemon("Pikachu");
        assertEquals("Pikachu", pokemon.nom);
        assertEquals(Element.ELECTRIK, pokemon.element1);
        Pokemon pokemon2 = loadPokemon("Sablaireau");
        assertEquals("Sablaireau", pokemon2.nom);
        assertEquals(Element.SOL, pokemon2.element1);

    }

    // Renvoie sous forme de chaine un fichier texte
    String lireFichierTxt(String chemin) {
        File fichier = newFile(chemin);
        String chaine = "";
        while (ready(fichier)){
            chaine = chaine + readLine(fichier) + "\n";
        }
        return substring(chaine, 0, length(chaine) -1);
    }




    ////////////////////////////////////////////////////////////////////////////////
    //                             FONCTIONS DE JEU                               //
    ////////////////////////////////////////////////////////////////////////////////

    int calculDegat(Pokemon pokemonAttaquant, Pokemon pokemonDefenseur) {
        return pokemonAttaquant.attaque.power * 100 / (int)((random() * 40) + 80  );
    }

    boolean questionDuelAttaque(Pokemon pokemonJoueur, Pokemon pokemonAdverse) {
        int degats = calculDegat(pokemonJoueur, pokemonAdverse);
        println("Le " + pokemonAdverse.nom + " adverse a " + pokemonAdverse.pv + " points de vie restant.");
        println("Votre " + pokemonJoueur.nom + " utilise " + pokemonJoueur.attaque.nom + " et inflige " + degats + " degats au " + pokemonAdverse.nom + " adverse");
        print("Combien reste t'il de points de vie au " + pokemonAdverse.nom + " adverse après votre attaque ? ");
        return (readInt() == pokemonAdverse.pv - degats);
    }

    boolean questionDuelDefense(Pokemon pokemonJoueur, Pokemon pokemonAdverse) {
        int degats = calculDegat(pokemonAdverse, pokemonJoueur);
        println("Votre " + pokemonJoueur.nom + " a " + pokemonJoueur.pv + " points de vie restant.");
        println("Le " + pokemonAdverse.nom + " utilise " + pokemonAdverse.attaque.nom + " et inflige " + degats + " degats à votre " + pokemonJoueur.nom);
        print("Combien reste t'il de points de vie à votre " + pokemonJoueur.nom + " après son attaque ? ");
        return (readInt() == pokemonJoueur.pv - degats);
    }

    // Lance un duel
    boolean combat(Pokemon pokemonJoueur, Pokemon pokemonAdverse) {
        boolean tourJoueur = true;
        while (pokemonJoueur.pv > 0 && pokemonAdverse.pv > 0) {
            clearScreen();
            println();
            afficherCombat(pokemonJoueur, pokemonAdverse);
            if (tourJoueur) {
                if(questionDuelAttaque(pokemonJoueur, pokemonAdverse)) {
                    pokemonAdverse.pv = pokemonAdverse.pv - calculDegat(pokemonJoueur, pokemonAdverse);
                    println("Bonne réponse !");
                    println("Les dégats ont étés infligés au pokémon adverse. :)");

                } else {
                    println("Mauvaise réponse");
                    println("Les dégats n'ont pas étés infligés au pokémon adverse. :(");
                }

                delay(2000);
                
                tourJoueur = false;
            } else {
                if(questionDuelDefense(pokemonJoueur, pokemonAdverse)) {
                    pokemonAdverse.pv = pokemonAdverse.pv - calculDegat(pokemonJoueur, pokemonAdverse);
                    println("Bonne réponse !");
                    println("Votre pokémon n'a reçu aucun dégats. :)");

                } else {
                    println("Mauvaise réponse");
                    println("Votre a reçu des dégats. :(");
                }

                readString();
                tourJoueur = true;
            }
            
        }
        if (pokemonAdverse.pv <= 0) {
            return true;
        }
        return false;
    }

    /*void testCombat() {
        combat(loadPokemon("Evoli"), loadPokemon("Dracaufeu"));
    }*/
    
    // Génere les stats d'un pokemon
    String[] affichageStats(Pokemon pokemon) {
        String[] affichageStats = new String[3];
        affichageStats[0] = "Nom : " + pokemon.nom;
        affichageStats[1] = "Pv actuel : " + pokemon.pv;
        affichageStats[2] = "Pv max : " +pokemon.MAXPV;
        for(int idxTab = 0; idxTab <length(affichageStats); idxTab++) {
            for(int idxChaine = length(affichageStats[idxTab]); idxChaine < 70; idxChaine++) {
                affichageStats[idxTab] = affichageStats[idxTab] + " ";
            }
        }
        return affichageStats;
    }

    // Affiche le combat
    void afficherCombat(Pokemon pokemonJoueur, Pokemon pokemonAdverse) {
        // Charge le dessin ascii des pokemons
        String[] asciiPokemonJoueur = toTab(lireFichierTxt("../ressources/AsciiArt/" + pokemonJoueur.nom + "_Face.txt"));
        String[] asciiPokemonAdverse = toTab(lireFichierTxt("../ressources/AsciiArt/" + pokemonAdverse.nom + "_Face.txt"));

        
        for(int idx = 0; idx < length(affichageStats(pokemonJoueur)); idx++) {
            print(affichageStats(pokemonJoueur)[idx]);
            println(affichageStats(pokemonAdverse)[idx]);
            
        }
        for(int idx = 0; idx < length(asciiPokemonAdverse); idx++) {

            print(colorByElement(pokemonJoueur) + asciiPokemonJoueur[idx] + ANSI_RESET);// Affiche le coté joueur
            print(" ");
            println(colorByElement(pokemonAdverse) + asciiPokemonAdverse[idx] + ANSI_RESET);// Affiche le coté adverse
            
        }
        
    }

    String colorByElement(Pokemon poke){
        if(poke.element1 == Element.ACIER || poke.element1 == Element.NORMAL || poke.element1 == Element.VOL){
            return ANSI_WHITE;
        }else if(poke.element1 == Element.EAU || poke.element1 == Element.DRAGON){
            return ANSI_BLUE;
        }else if(poke.element1 == Element.ELECTRIK){
            return ANSI_YELLOW;
        }else if(poke.element1 == Element.FEU || poke.element1 == Element.COMBAT ||  poke.element1 == Element.SOL || poke.element1 == Element.ROCHE){
            return ANSI_RED;
        }else if(poke.element1 == Element.PLANTE || poke.element1 == Element.INSECTE){
            return ANSI_GREEN;
        }else if(poke.element1 == Element.GLACE){
            return ANSI_CYAN;
        }else if(poke.element1 == Element.PSY || poke.element1 == Element.TENEBRES || poke.element1 == Element.POISON || poke.element1 == Element.FEE || poke.element1 == Element.SPECTRE ){
            return ANSI_PURPLE;
        }
        return ANSI_RESET;
    }
    boolean stab (Pokemon poke,Move move){
        if(poke.element1 == move.element || poke.element2 == move.element && poke.element2 !=null ){
            return true;
        }else{
            return false;
        }
    }
    void testStab(){
        Pokemon pokemon = loadPokemon("Pikachu");
        Move move = loadAttaque("Tonnerre");
        assertTrue(stab(pokemon,move));
        Move move1 = loadAttaque("Vive-Attaque");
        assertFalse(stab(pokemon,move1));
    }
    int questionPvAdversesRestantApresAttaque(Pokemon joueur, Pokemon adverse){
        println("Le " + adverse.nom + " adverse a " + adverse.pv + " points de vies. Si votre " + joueur.nom + " attaque avec " + joueur.attaque.nom + " qui fait " + joueur.attaque.power + " de degats.");
        println("Combien reste t'il de points de vies a " + adverse.nom +" ?");
        return adverse.pv-joueur.attaque.power;
    }

    int questionNombreAttaquesNecessairesPourKO(Pokemon joueur, Pokemon adverse) {
        println("Le " + adverse.nom + " adverse a " + adverse.pv + " points de vies. Si votre " + joueur.nom + " attaque avec " + joueur.attaque.nom + " qui fait " + joueur.attaque.power + " de degats.");
        println("Il faut attaquer combien de fois " + adverse.nom +" avec " + joueur.attaque.nom + " pour qu'il soit KO ?");   
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
        println("Le pokemon " + joueur.nom + " tiens comme objet les restes qui permet de le soigner de "+ aleaSoin +" pv " );
        println("Sachant qu'il possede " + joueur.pv +" pv et qu'il a  perdu pendant le combat "+ pvPerdu+ " pv, a combien de pv " + joueur.nom + " sera t'il ?");
        return joueur.pv - pvPerdu + aleaSoin;
    }

    int questionPourBaisseDeVitesse(Pokemon joueur, Pokemon adverse) {
        int aleaVitesse = (int) (random()*100) + 1;
        int aleaAttaque = (int) random()*3 + 1;

        println("Votre " + joueur.nom + " à "+ joueur.vitesse +" de vitesse " );
        println("Sachant que pendant le combat, " + joueur.nom +" à vue ca vitesse divisé par 2 par la capacité "+ loadAttaque("Toile Gluante").nom + " du " + adverse.nom +" qui à " + adverse.vitesse + " de vitesse");
        println("Qui attaquera en 1er ? (Rappel : c'est celui qui à la vitesse la plus haute qui attaquera en 1er) ");
        println("1 : " + joueur.nom);
        println("2 : " + adverse.nom);
        println("Si vous pensez que les 2 pokémons auront la même vitesse, selectionner 0");
        if(joueur.vitesse /2 > adverse.vitesse){
            return 1;
        }else if(joueur.vitesse /2 == adverse.vitesse){
            return 0;
        }
        return 2;
    }

    int questionDegatAvecStab(Pokemon joueur, Pokemon adverse) {
        if(stab(joueur,joueur.attaque)) {
            println("Le pokemon " + joueur.nom + " possede le type " + joueur.element1 + " / " + joueur.element2 +" et l'attaque " + joueur.attaque.nom + " est de type " + joueur.attaque.element +" qui poséde "+ joueur.attaque.power + " de dégats, donc il y a STAB (donc il a un bonus de 50% sur son attaque)");
            println("Combien de degats fait l'attaque " + joueur.attaque.nom + " avec STAB ?");
            return joueur.attaque.power + joueur.attaque.power/2;
        }else{
            println("Le pokemon " + joueur.nom + " possede le type " + joueur.element1 + " et l'attaque " + joueur.attaque.nom + " est de type " + joueur.attaque.element +" qui poséde "+ joueur.attaque.power + " de dégats, donc il n'y a pas STAB");
            println("Combien de degats fait l'attaque " + joueur.attaque.nom + " sans STAB ?");
            return joueur.attaque.power;
        }
    }

    void sauvegardeNiveauReussi(int numNiveau, int idxJoueur){
        CSVFile listeJoueur = loadCSV(CHEMIN_SAUVEGARDE, ',');
        String [][] csv = new String[rowCount(listeJoueur)][columnCount(listeJoueur)];
        for(int lig = 0; lig < rowCount(listeJoueur); lig++) {
            for(int col = 0; col < columnCount(listeJoueur); col++) {
                if(col==numNiveau+2 && lig==idxJoueur){
                    csv[lig][col]="true";
                }else{
                    csv[lig][col]=getCell(listeJoueur, lig, col);
                }
            }
        }
        saveCSV(csv, CHEMIN_SAUVEGARDE);
    }

    void sauvegardeScoreDuel(int score, int idxJoueur){
        CSVFile listeJoueur = loadCSV(CHEMIN_SAUVEGARDE, ',');
        String [][] csv = new String[rowCount(listeJoueur)][columnCount(listeJoueur)];
        for(int lig = 0; lig < rowCount(listeJoueur); lig++) {
            for(int col = 0; col < columnCount(listeJoueur); col++) {
                if(col == 1 && lig == idxJoueur){
                    csv[lig][col] = "" + score;
                }else{
                    csv[lig][col]=getCell(listeJoueur, lig, col);
                }
            }
        }
        saveCSV(csv, CHEMIN_SAUVEGARDE);
    }

    // Fonction qui joue un niveau
    boolean jouerNiveau(int numNiveau, Pokemon joueur, Pokemon adverse, int idxJoueur) {
        boolean res = false;
        int bonneReponse = 0;
        afficherCombat(joueur, adverse);
        if(numNiveau==1){
            bonneReponse = questionPvAdversesRestantApresAttaque(joueur, adverse);
        }else if(numNiveau==2){
            bonneReponse = questionNombreAttaquesNecessairesPourKO(joueur, adverse);
        }else if(numNiveau==3){
            bonneReponse = questionPvJoueurRestantApresSoin(joueur, adverse);
        }else if(numNiveau == 4){
            bonneReponse = questionPourBaisseDeVitesse(joueur, loadPokemon("Parasect"));
        }else if(numNiveau == 5){
            bonneReponse = questionDegatAvecStab(joueur, adverse);
        }else if(numNiveau == 6){
            modeDuel(idxJoueur);
        }
        
        int reponseJoueur = readInt();

        if (bonneReponse == reponseJoueur) {
            println("Bravo ! La reponse etait bien de " + bonneReponse + ".");
            res = true;
            sauvegardeNiveauReussi(numNiveau,idxJoueur);
            println("Le niveau " + (numNiveau+1) + " est DISPONIBLE !!!");
        } else {
            println("Perdu :( " + joueur.nom + " est K.O.");
        }
        return res;
    }

    void lancerNiveau(int idxJoueur){
        Pokemon[] tableauPokemon = new Pokemon[]{loadPokemon("Carapuce"),loadPokemon("Parasect"),loadPokemon("Pikachu"),loadPokemon("Mewtwo") ,loadPokemon("Dracaufeu"), loadPokemon("Evoli"), loadPokemon("Rafflesia"), loadPokemon("Sablaireau")};
        int numNiveau = choisirNiveau(idxJoueur);
        int aleaJoueur = (int) (random()*8);
        int aleaAdverse = (int) (random()*8);
        boolean niveau = true;
        while(aleaJoueur==aleaAdverse){
            aleaAdverse = (int) (random()*8);
        }
        if(numNiveau != 0) {
            niveau = jouerNiveau(numNiveau, tableauPokemon[aleaJoueur], tableauPokemon[aleaAdverse], idxJoueur);
        }
        
        int nbtour  = 2;
        while(niveau == false && nbtour != 0){
            println("Retente ta chance ! Il te reste " + nbtour  + " essaies");
            niveau = jouerNiveau(numNiveau, tableauPokemon[aleaJoueur], tableauPokemon[aleaAdverse], idxJoueur);
            nbtour= nbtour -1;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////
    //                              FONCTIONS DE MENU                             //
    ////////////////////////////////////////////////////////////////////////////////

    // Affichage de l'écran titre
    void afficherEcranTitre() {
        clearScreen();
        println();
        File titre = newFile("../ressources/title.txt");
         while(ready(titre)){
            println(ANSI_RED + readLine(titre) + ANSI_RESET);
        }
        println();
        println(ANSI_YELLOW + "Appuyez sur entrée pour continuer" + ANSI_RESET);
        readString();
        
        clearScreen();
        println("Bienvenue sur PokeMath !");
        println();
    }
    // Fonction qui affiche la liste des niveaux
    void afficherListeNiveau(CSVFile listeNiveau, CSVFile listeJoueur, int idxJoueur) {
        println("Liste des niveaux disponibles :");
        println();
        for(int idx = 1; idx < columnCount(listeJoueur) ; idx++) {
            if(equals(getCell(listeJoueur, idxJoueur, idx),"true")){
                println(getCell(listeNiveau, idx-1, 0));
            }
        }
        println();
        println("Liste des niveaux bloque:");
        println();
        for(int idx = 1; idx < columnCount(listeJoueur) ; idx++) {
            if(equals(getCell(listeJoueur, idxJoueur, idx),"false")){
                println(getCell(listeNiveau, idx-1, 0));
            }
        }
        println();
        println("Tapez 0 pour quitter");
    }

    void modeDuel(int idxJoueur){
        boolean quitter = false;
        int score = 0;
        String[] listePoke = new String[]{"Pikachu", "Dracaufeu", "Evoli", "Rafflesia", "Sablaireau", "Parasect", "Mewtwo", "Carapuce"};
        while(!quitter) {
            if (combat(loadPokemon(listePoke[(int)(random() * 8)]), loadPokemon(listePoke[(int)(random() * 8)]))) {
                score++;
            }
            if(!equals(readString(), "oui")) {
                quitter = true;
            }
        }
        sauvegardeScoreDuel(score, idxJoueur);

    }
    


    // Fonction qui demande au joueur de choisir un niveau
    int choisirNiveau(int idxJoueur){

        CSVFile listeNiveau = loadCSV(CHEMIN_LISTE_NIVEAU, ',');
        CSVFile listeJoueur = loadCSV(CHEMIN_SAUVEGARDE, ',');

        afficherListeNiveau(listeNiveau, listeJoueur, idxJoueur);
        println();
        boolean choisiNiveauDispo = false;
        String choixNiveau = "";
        while(!choisiNiveauDispo){
            String phraseNiveauChoix = "Saisissez le niveau que vous voulez lancer : ";
            for(int idx = 0 ; idx< length(phraseNiveauChoix) ; idx++){
                print(charAt(phraseNiveauChoix,idx));
                delay(50);
            }
    
            choixNiveau = readString();
            println();

            if(toInt(choixNiveau)>rowCount(listeNiveau) || toInt(choixNiveau) < 0){
                println("Le niveau " + choixNiveau + " n'existe pas.");

            }else if(equals(getCell(listeJoueur, idxJoueur, toInt(choixNiveau) + 1),"true") || intIsValide(getCell(listeJoueur, idxJoueur, toInt(choixNiveau) + 1)) ){
                println("Vous avez choisi le niveau " + choixNiveau);
                choisiNiveauDispo = true;

            }else if (!equals(choixNiveau, "0")){
                println("Le niveau " + choixNiveau + " n'est pas disponible.");
            }
        }
        return toInt(choixNiveau);
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
            }else if(col==1){
                csv[nbLigneJoueur][col]= "" + 0;
            }else if(col==2){
                csv[nbLigneJoueur][col]= "true";
            }else{
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
        String welcome = "Bienvenue " + nomJoueur + " ! :)";
        for(int idx=0; idx<nbLigneJoueur; idx++){
            if(equals(getCell(listeJoueur, idx, 0),nomJoueur)){
                for(int cpt = 0 ; cpt < length(welcome); cpt++){
                    println(charAt(welcome,cpt));
                    delay(50);
                }
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
        afficherEcranTitre();
        String stopjeu =  "";
        int idxJoueur = demandeJoueur();
        //Décomenter pour lancer le mode duel
        //modeDuel(idxJoueur); 
        while(!equals(stopjeu,"n")){
            lancerNiveau(idxJoueur);
            print("Veut-tu continuer ? (y/n)");
            println();
            stopjeu = readString();
        }  
    }
}