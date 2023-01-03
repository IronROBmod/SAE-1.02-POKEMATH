import extensions.CSVFile;
import extensions.File;
class Pokemath extends Program{
    // int formuleDegats =  (((((((poke.niveau × 2 ÷ 5) + 2) × move.power × Att[Spé] ÷ 50) ÷ Def[Spé]) × Mod1) + 2) × CC × Mod2 × R ÷ 100) × STAB × Type1 × Type2 × Mod3;
    // Fonction pour créer un nouveau pokemon selon les stats données
    Pokemon newPokemon(String name, int niveau, int pv,int attack,int attackSpe,int defense,int defenseSpe,int vitesse,Element type1,Element type2,Move[] attaques){
        Pokemon poke = new Pokemon();
        poke.name = name;
        poke.niveau = niveau;
        poke.pv = pv;
        poke.attack = attack;
        poke.attackSpe = attackSpe;
        poke.defense = defense;
        poke.defenseSpe = defenseSpe;
        poke.vitesse = vitesse;
        poke.type1 = type1;
        poke.type2 = type2;
        attaques = new Move[4];
        return poke;
    }

    // Fonction simplifié de création de pokemon
    Pokemon newPokemon(String name, int pv) {
        Pokemon pokemon = new Pokemon();
        pokemon.name = name;
        pokemon.pv = pv;
        return pokemon;
    }
    //Fonction pour créer un nouveau move
    Move newMove(String name,Element element,int power, Categorie categorie, String description){
        Move move = new Move();
        move.name = name;
        move.element = element;
        move.power = power;
        move.categorie = categorie;
        move.description = description;
        return move;
    }

    String toString(Move move){
        String str = move.name + "\n" + move.element + "\n" + move.power + "\n" + move.categorie;
        return str;
        /*if(!modeeasy && move.description != null){
            str = str + "\n" + move.description;
            return str;
        } 
        */
    }
    // Fonction simplifié de création de move
    Move newMove(String name, int power) {
        Move move = new Move();
        move.name = name;
        move.power = power;
        return move;
    }

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

    // fonction qui charche le fichier contenant la liste des moves
    CSVFile loadListMoves() {
        return loadCSV("../ressources/ListeAttaques.csv", ';');
    }
    void teststringToElement(){
        assertEquals(Element.ACIER,stringToElement("ACIER"));
        assertEquals(Element.NORMAL,stringToElement("NORMAL"));
        assertEquals(Element.FEE,stringToElement("FEE"));
    }

Categorie stringToCategorie(String chaine){
    if(equals(chaine,"SPECIALE")){
        return Categorie.SPECIALE;
    }if(equals(chaine,"PHYSIQUE")){
        return Categorie.PHYSIQUE;
    }else{
        return Categorie.STATUT;
    }
}

    Element stringToElement(String chaine){
        if(equals(chaine, "ACIER")){
            return Element.ACIER;
        }else if(equals(chaine, "COMBAT")){
            return Element.COMBAT;
        }else if(equals(chaine, "DRAGON")){
            return Element.DRAGON;
        }else if(equals(chaine, "EAU")){
            return Element.EAU;
        }else if(equals(chaine, "ELECTRQUE")){
            return Element.ELECTRIQUE;
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
        }else if(equals(chaine, "TENEBRE")){
            return Element.TENEBRE;
        }else if(equals(chaine, "VOL")){
            return Element.VOL;
        }else{
            return Element.NORMAL;
        } 
    }
    String[] getMove(CSVFile listMoves, String name) {
        for(int idx = 1; idx < rowCount(listMoves); idx++) {
            if (equals(getCell(listMoves, idx, 0), name)) {
                String[] move = new String[columnCount(listMoves, idx)];
                for(int idx2 = 0; idx2 < columnCount(listMoves, idx); idx2++) {
                    move[idx2] = getCell(listMoves, idx, idx2);
                }
                return move;
            }
        }
        return new String[columnCount(listMoves)];
    }

    Move getMove(String name) {
        String[] listeMoves = getMove(loadListMoves(), name);
        return newMove(listeMoves[0], stringToElement(listeMoves[6]), toInt(listeMoves[2]), stringToCategorie(listeMoves[7]), listeMoves[8]);
    }

    //si le type de l'attaque est le même que le type du pokemon, le bonus est de 1.5

    double isStaab(Move move, Pokemon pokemon){
        double staab = 1.0;
        if(move.element == pokemon.type1 || move.element == pokemon.type2){
            staab = 1.5 ;
        }
        return staab;
    }
    //si le type de l'attaque est le même que le type du pokemon adverse, le malus est de 0.5

    Move isFaible(Move move, Pokemon pokemon){
        if(move.element == pokemon.type1 || move.element == pokemon.type2){
            move.power =move.power-(move.power/2);
        }
        return move;
    }
    //si le type de l'attaque est le faible que les types du pokemon adverse, le malus est de 2

    Move isSuperFaible(Move move, Pokemon pokemon){
        if(move.element == pokemon.type1 && move.element == pokemon.type2){
            move.power =move.power-(move.power);
        }
        return move;
    } 
    //si le type de l'attaque est une faiblesse aux types du pokemon adverse, le bonus est de 4

    Move isSuperStrenght(Move move, Pokemon pokemon){
        if(move.element == pokemon.type1 && move.element == pokemon.type2){
            move.power =move.power+(move.power*3);
        }
        return move;
    }

    // Fonction qui affiche les stats d'un Pokemon
    String toString(Pokemon poke){
        String str = "";
        str =poke.name = poke.name + "\n" + 
            poke.niveau + "\n" + 
            poke.pv + "\n" + 
            poke.attack + "\n" + 
            poke.attackSpe + "\n" + 
            poke.defense + "\n" + 
            poke.defenseSpe + "\n" + 
            poke.vitesse + "\n" + 
            poke.type1 + "\n";
        if(poke.type2 != null){
            str = str + "\n" + poke.type2;
        }
        str = str + "\n" + 
            poke.attaques[0].name + " | " + 
            poke.attaques[1].name + " | " + 
            poke.attaques[2].name + " | " + 
            poke.attaques[3].name;

        return str;
    }

    // Fonction qui renvoie true si tout les pokemon d'une liste sont ko
    boolean toutLesPokeSontKO(Pokemon[] tab) {
        for(int idx = 0; idx < length(tab); idx++) {
            if (tab[idx].pv > 0) {
                return false;
            }
        }
        return true;
    }

    // Fonction qui calcule le nombre de pv restant après une attaque
    int pvRestantApresAttaque(Pokemon pokemon, Move attaqueUtilise) {
        int pvRestant = pokemon.pv - attaqueUtilise.power;
        if (pvRestant < 0) {
            pvRestant = 0;
        }
        return pvRestant;
    }
    // Fonction qui pose au joueur la question combien de pv il reste à l'adversaire
    boolean poserQuestionCombienDePvRestant(Pokemon PokemonAdversaire, Move attaqueUtilise) {
        println("Vous infligez " + attaqueUtilise.power + " dégats");
        println("Il reste " + PokemonAdversaire.pv + "pv au pokemon adverse");
        println("Combien reste t'il de pv au pokemon adverse ? ");
        if (readInt() == pvRestantApresAttaque(PokemonAdversaire, attaqueUtilise)) {
            return true;
        }
        return false;
    }
    // Fonction qui joue un niveau
    void jouerNiveau(int numNiveau) {
        Move move = newMove("Tacle", 30);
        Pokemon pokemon = newPokemon("Dracaufeu", 45, 160,50,50,50,50,50,Element.FEU,Element.FEU,new Move[]{move});
        if (poserQuestionCombienDePvRestant(pokemon, move)) {
            println("Bravo ! La réponse était bien " + pvRestantApresAttaque(pokemon, move) + " pv");
        } else {
            println("Perdu :( La réponse était " + pvRestantApresAttaque(pokemon, move) + " pv");
        }
    }
    // Fonction qui demande au joueur de choisir un niveau
    int choisirNiveau(){
        String[] liste = new String[]{"Piège de Rock", "Flamme Fourbe", "Herbe Surprise", "Métal Solide", "Roche Froide"};
        println("Liste des niveaux disponibles :");
        println();
        for(int idx = 0; idx < length(liste); idx++) {
            println("Niveau " + (idx + 1) + " : " + liste[idx]);
        }
        println();
        print("Saisissez le niveau que vous voulez lancer : ");
        int choixniveau = readInt();
        println();
        println("Vous avez choisi le niveau " + choixniveau + " : " + liste[choixniveau - 1]);
        return choixniveau - 1;
    }
    
    // Algo Principale
    void algorithm(){
        clearScreen();
        File test = newFile("../ressources/title.txt");
         while (ready(test)){
            println(readLine(test));
        }
        println("Bienvenue sur PokeMath !");
        println();
        CSVFile ListePokemon = loadCSV("../ressources/ListePokemon.csv");
       //println(getCell(ListePokemon,1,0)); permet de recup les info dun pokemon
        jouerNiveau(choisirNiveau());
        
    }

// Fonctions de test

    void testPvRestantApresAttaque() {
        Pokemon pokemon = newPokemon("Fred", 160);
        Move move = newMove("Tacle", 30);
        assertEquals(130, pvRestantApresAttaque(pokemon, move));

    }

    void testGetMove() {
        assertArrayEquals(new String[]{"Recyclage", "Recycle","-","-","-","10","NORMAL","AUTRE","Recycle un objet tenu à usage unique déjà utilisé lors du combat pour pouvoir l’utiliser à nouveau."}, getMove(loadListMoves(), "Recyclage"));
        assertArrayEquals(new String[]{"Flash","Flash","-","100","-","20","NORMAL","AUTRE","Explosion lumineuse qui fait baisser la Précision de l’ennemi. Permet aussi d’éclairer les grottes."}, getMove(loadListMoves(), "Flash"));
    }

    void testToInt() {
        assertEquals(123, toInt("123"));
    }

    void testGetMove2() {
        println(toString(getMove("Charge")));
    }
    








}