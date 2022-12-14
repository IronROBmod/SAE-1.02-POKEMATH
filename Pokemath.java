class Pokemath extends Program{
   // int formuleDegats =  (((((((poke.niveau × 2 ÷ 5) + 2) × move.power × Att[Spé] ÷ 50) ÷ Def[Spé]) × Mod1) + 2) × CC × Mod2 × R ÷ 100) × STAB × Type1 × Type2 × Mod3;
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

    /*void attaquer(Pokemon poke1, Pokemon poke2) {
        /*poke2.pv = poke2.pv - poke1.move;
        println(poke1.name + "a utilisé [nom_attaque] sur " + poke2.name);
        poke1.pv = poke1.pv - poke2.;
        println(poke2.name + "a utilisé [nom_attaque] sur " + poke1.name);
    }*/

    /*void combat(Pokemon[] pokeJ1, Pokemon[] pokeJ2) { //test
        while(toutLesPokeSontKO(pokeJ1) || toutLesPokeSontKO(pokeJ2)) {
            attaquer(pokeJ1, pokeJ2);
            attaquer(pokeJ1, pokeJ2);
       
    }*/
    String[] initniveau(int nombre){
        String[] tab = new String[nombre];
        int nbniveau = 1;
        for(int i = 0;i<nombre;i++){
            tab[i] ="Niveau " + nbniveau;
            nbniveau++;
        }
        return tab;
    }
    String toString(String[] tab){
        String str = "";
        for(int i = 0;i<length(tab);i++){
            str = str + tab[i] + "     ";
        }
        return str;
    }
    void algorithm(){
        clearScreen();
        println("Veuillez choisir une langue de préférence :" + "\n" + "1 : FR" + "\n" + "2 : EN");
        int choix = readInt();
        String test = "";
        if(choix == 1){
            test = "Bonjour toi moi c'est Armandino";
        }else if(choix == 2){
            test = "Hello you I'm Armandino";
        }
        clearScreen();
        for(int i = 0;i<length(test);i++){
            print(charAt(test,i));
            delay(100);
        } 
        println();
        loadCSV("ListePokemon.csv",';');
        println(toString(initniveau(3)));
        int choixniveau = readInt();
               String choixlvl ="Vous avez choisi le niveau " + choixniveau;
            if(choixniveau == 1){
                println(choixlvl);
            }else if(choixniveau == 2){
                println(choixlvl);
            }else if(choixniveau == 3){
                println(choixlvl);
            }
    }
}