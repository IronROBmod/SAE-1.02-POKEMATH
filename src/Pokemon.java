class Pokemon{

    String name;
    int niveau;
    int pv;
    int vitesse;
    Element type1;
    Move attaque;

    Pokemon(String name, int niveau, int pv, int vitesse, Element type1, Move attaque){
        this.name  =name;
        this.niveau = niveau;
        this.pv = pv;
        this.vitesse =vitesse;
        this.type1 =type1;
        this.attaque = attaque;
    }

    public String toString(){
        return "[Nom: " + this.name + ", Niveau: " + this.niveau + ", PV: " + this.pv + "]";
    }
}