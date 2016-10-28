package bean;

/**
 * Created by tjliqy on 2016/10/18.
 */
public class Poker implements Comparable<Poker>{
    private Suit suit;
    private int num;

    public Poker(Suit suit, int num) throws Exception {
        this.suit = suit;
        if (num > 0 && num < 14){
            this.num = num;
        }else {
            throw new Exception("初始化卡牌时数字" + num + "超出范围");
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public int getNum() {
        return num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Poker poker = (Poker) o;

        if (num != poker.num) return false;
        return suit == poker.suit;

    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + num;
        return result;
    }

    @Override
    public int compareTo(Poker o) {
        if (suit == o.getSuit()){
            return num - o.getNum();
        }
        return suit.hashCode() - o.getSuit().hashCode();
    }
}
