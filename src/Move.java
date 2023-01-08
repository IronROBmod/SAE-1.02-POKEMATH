class Move{

    String name;
    Element element;
    int power;

    Move(String name, Element element, int power){
        this.name = name;
        this.element = element;
        this.power = power;
    }

    public String toString(){
        return this.name + " qui fait " + this.power + " de degats.";
    }
}