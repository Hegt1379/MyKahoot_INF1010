package TPKahoot;

public class Player {
    private String name;
    private int BRep = 0;
    private int MRep = 0;

    public Player (String name){
        this.name = name;
    }

    public void bonneReponse(){
        BRep++;
    }

    public void mauvaiseReponse(){
        MRep++;
    }

    public String getText(){
        return name + " " + BRep + " bonne réponses " + MRep + " mauvaise réponses " + (double)BRep / (BRep + MRep) * 100 + "%";
    }
}
