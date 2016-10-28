package bean;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by tjliqy on 2016/10/18.
 */
public class Player implements Comparable<Player> {

    private TreeSet<Poker> pokersInHand;
    private TreeSet<Poker> pokersForPunish;
    private boolean isRealPlayer;
    private boolean isFinished;
    private String name;


    public Player(){
        this("");
    }
    public Player(String name){
        this(name,false);
    }

    public Player(String name, boolean isRealPlayer) {
        this.pokersInHand = new TreeSet<>();
        this.pokersForPunish = new TreeSet<>();
        this.isRealPlayer = isRealPlayer;
    }


    public void addPoker(Poker poker){
        pokersInHand.add(poker);
    }

    public void removePoker(Poker poker) throws Exception {
        if (pokersInHand.contains(poker)){
            pokersInHand.remove(poker);
        }else {
            throw new Exception("此人没有这张牌:" + poker.getSuit() + poker.getNum());
        }
        if (pokersInHand.size() == 0){
            isFinished = true;
        }
    }
    public void punish(Poker poker) throws Exception {
        removePoker(poker);
        pokersForPunish.add(poker);
    }
    public void setRealPlayer(boolean realPlayer) {
        isRealPlayer = realPlayer;
    }

    public boolean isRealPlayer() {
        return isRealPlayer;
    }


    public TreeSet<Poker> getPokersInHand() {
        return pokersInHand;
    }

    public TreeSet<Poker> getPokersForPunish() {
        return pokersForPunish;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public int compareTo(Player o) {
        return this.getPunishScore() - o.getPunishScore();
    }

    public int getPunishScore(){
        int score = 0;
        for (Poker p : pokersForPunish) {
            score += p.getNum();
        }
        return score;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
