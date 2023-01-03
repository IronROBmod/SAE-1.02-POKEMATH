import extensions.CSVFile;
class Pokemath extends Program{
    Pokemon newPokemon(String name, int niveau, int pv,int attack,int attackSpe,int defense,int defenseSpe,int vitesse,Type type1,Type type2,Move[] attaques){
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

    Pokemon newPokemon(String name, int pv) {
        Pokemon pokemon = new Pokemon();
        pokemon.name = name;
        pokemon.pv = pv;
        return pokemon;
    }
    Move newMove(String name,Type type,int power, boolean physique, boolean speciale, boolean statut, boolean staab,String description){
        Move move = new Move();
        move.name = name;
        move.type = type;
        move.power = power;
        move.physique = physique;
        move.speciale = speciale;
        move.statut = statut;
        move.description = description;
        return move;
    }

    Move newMove(String name, int power) {
        Move move = new Move();
        move.name = name;
        move.power = power;
        return move;
    }
    //si le type de l'attaque est le même que le type du pokemon, le bonus est de 1.5

    double isStaab(Move move, Pokemon pokemon){
        double staab = 1;
        if(move.type == pokemon.type1 || move.type == pokemon.type2){
            staab = 1.5 ;
        }
        return staab;
    }
    //si le type de l'attaque est le même que le type du pokemon adverse, le malus est de 0.5

    Move isFaible(Move move, Pokemon pokemon){
        if(move.type == pokemon.type1 || move.type == pokemon.type2){
            move.power =move.power-(move.power/2);
        }
        return move;
    }
    //si le type de l'attaque est le faible que les types du pokemon adverse, le malus est de 2

    Move isSuperFaible(Move move, Pokemon pokemon){
        if(move.type == pokemon.type1 && move.type == pokemon.type2){
            move.power =move.power-(move.power);
        }
        return move;
    } 
    //si le type de l'attaque est une faiblesse aux types du pokemon adverse, le bonus est de 4

    Move isSuperStrenght(Move move, Pokemon pokemon){
        if(move.type == pokemon.type1 && move.type == pokemon.type2){
            move.power =move.power+(move.power*3);
        }
        return move;
    }


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

    // Fonction qui renvoie true gg
    boolean toutLesPokeSontKO(Pokemon[] tab) {
        for(int idx = 0; idx < length(tab); idx++) {
            if (tab[idx].pv > 0) {
                return false;
            }
        }
        return true;
    }


    int pvPerdu(Pokemon pokemon, Move attaqueUtilise) {
        int pvPerdu = ((poke.))
        /*
        *poke.niveau = niveau du pokemon 
        * 2 = coefficient de base
        * attaqueUtilise.attack = attaque du pokemon
        * attaqueUtilise.power = puissance de l'attaque
        * poke.defense = defense du pokemon adverse
        * 50 = coefficient de base
        */
    }

    boolean poserQuestionCombienDePvRestant(Pokemon PokemonAdversaire, Move attaqueUtilise) {
        println("Vous infligez " + attaqueUtilise.power + " dégats");
        println("Il reste " + PokemonAdversaire.pv + "pv au pokemon adverse");
        println("Combien reste t'il de pv au pokemon adverse ? ");
        if (readInt() == pvRestantApresAttaque(PokemonAdversaire, attaqueUtilise)) {
            return true;
        }
        return false;
    }

    void jouerNiveau(int numNiveau) {
        Pokemon pokemon = newPokemon("Fred", 160);
        Move move = newMove("Tacle", 30);
        if (poserQuestionCombienDePvRestant(pokemon, move)) {
            println("Bravo ! La réponse était bien " + pvRestantApresAttaque(pokemon, move) + " pv");
        } else {
            println("Perdu :( La réponse était " + pvRestantApresAttaque(pokemon, move) + " pv");
        }
    }

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
    String toString(String[] tab){
        String str = "";
        for(int i = 0;i<length(tab);i++){
            str = str + tab[i] + "     ";
        }
        return str;
    }
    void clearScreen2(){ // Le nom n'est pas valide apparement
        for(int i = 0;i<100;i++){
            println();
        }
    } 
    void algorithm(){
        clearScreen2();
        String Welcome ="WELCOME TO POKEMATH";
        for(int i = 0;i<length(Welcome);i++){
            print(charAt(Welcome,i));
            delay(100);
        }
        
        println();
        CSVFile ListePokemon = loadCSV("ListePokemon.csv");
       // println(getCell(ListePokemon,1,0)); permet de recup les info dun pokemon
        jouerNiveau(choisirNiveau());
    }

    void testPvRestantApresAttaque() {
        Pokemon pokemon = newPokemon("Fred", 160);
        Move move = newMove("Tacle", 30);
        assertEquals(130, pvRestantApresAttaque(pokemon, move));

    }
}