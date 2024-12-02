package oopp.team16.model.gameLogic.Cards;

//göra samma som färg
public enum CardType{
    NUMBER("NumberCard"),
    REVERSE("SpecialCard"),
    WILDCARD("SpecialCard"),
    DRAW_TWO("SpecialCard"),
    DRAW_FOUR("SpecialCard"),
    SKIP("SpecialCard");


    private final String t;
    CardType(String t){
        this.t = t;
    }

   public String getType() {
       return t;
   }



}
