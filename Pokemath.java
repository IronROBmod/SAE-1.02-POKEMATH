class Pokemath extends Program{

    Pokemon newPokemon(String name,int pv,int attack,int attackSpe,int defense,int defenseSpe,int vitesse,Type type1,Type type2,Move move1,Move move2,Move move3,Move move4,boolean player){
        Pokemon poke = new Pokemon();
        poke.name = name;
        poke.pv = pv;
        poke.attack = attack;
        poke.attackSpe = attackSpe;
        poke.defense = defense;
        poke.defenseSpe = defenseSpe;
        poke.vitesse = vitesse;
        poke.type1 = type1;
        poke.type2 = type2;
        poke.move1 = move1;
        poke.move2 = move2;
        poke.move3 = move3;
        poke.move4 = move4;
        poke.player = player;
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
        move.staab = staab;
        move.description = description;
        return move;
    }
    //si le type de l'attaque est le même que le type du pokemon, le bonus est de 1.5

    Move isStaab(Move move, Pokemon pokemon){
        if(move.type == pokemon.type1 || move.type == pokemon.type2){
            move.power =move.power+(move.power/2);
        }
        return move;
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

    // defini si le pokemon apartiens au joueur ou au bot

    Pokemon isPlayer(Pokemon pokemon){
        pokemon.player = true;
        return pokemon;
    }

    //gerer touches du clavier

    boolean isKeyPressed(String key){
        return keyDown(key);
    }

    //arrwo up = haut

    boolean isUpPressed(){
        return isKeyPressed("up");
    }



    String toString(Pokemon poke){
        String str = "";
        str =poke.name = poke.name + "\n" +poke.pv + "\n" + poke.attack + "\n" + poke.attackSpe + "\n" + poke.defense + "\n" + poke.defenseSpe + "\n" + poke.vitesse + "\n" + poke.type1 ;
        if(poke.type2 != null){
            str = str + "\n" + poke.type2;
        }
        return str;
    }
    void algorithm(){
        println("Veuillez choisir une langue de préférence :" + "\n" + "1 : FR" + "\n" + "2 : EN");
        int choix = readInt();
        String test = "";
        if(choix == 1){
            test = "Bonjour toi moi c'est Armandino";
        }else if(choix == 2){
            test = "Hello you I'm Armandino";
        }
        
        for(int i = 0;i<length(test);i++){
            print(charAt(test,i));
            delay(100);
        }  
        






        








    }
}