package presenter;

import bean.Player;
import bean.Poker;
import bean.Suit;

import java.util.*;

/**
 * Created by tjliqy on 2016/10/18.
 */


public class PokerHelper {

    private static final int PLAYER_NUM = 4;
    private static TreeSet<Poker> pokerOnTable;
    private static Player[] players;
    private static int order;

    /**
     * 开始游戏，此方法中包括一些初始化的操作
     *
     * @param realPlayerNum 真实玩家的个数
     * @param playerNames 玩家的姓名栈
     */
    public static void startGame(int realPlayerNum, Stack<String> playerNames) {
        pokerOnTable = new TreeSet<>();
        initPlayers(realPlayerNum,playerNames);
        initPoker();
    }

    /**
     * 获取当前回合的玩家
     *
     * @return
     */
    public static Player getPlayerInCurrentRound() {
        return players[order];
    }

    public static int getOrder() {
        return order;
    }

    public static TreeSet<Poker> getPokerOnTable() {
        return pokerOnTable;
    }

    /**
     * 检查当前玩家手牌中是否存在可以打出的牌
     *
     * @return true为有牌可以打出，可以进行出牌操作（调用play方法）。false为无牌可出，可以进行扣牌操作（调用punish方法）
     */
    public static boolean check() {
        try {
            for (Poker poker : players[order].getPokersInHand()) {
                if (check(poker)){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查当前牌中是否可以打出
     *
     * @return true可以打出，可以进行出牌操作（调用play方法）。false不可出，可以进行扣牌操作（调用punish方法）
     */
    public static boolean check(Poker poker) {
        try {
            if (pokerOnTable.size() == 0) {
                if (poker.getNum() == 7 && poker.getSuit() == Suit.spade) {
                    return true;
                }
            } else {
                if (poker.getNum() < 7) {
                    if (pokerOnTable.contains(new Poker(poker.getSuit(), poker.getNum() + 1))) {
                        return true;
                    }
                } else if (poker.getNum() == 7) {
                    return true;
                } else if (poker.getNum() > 7) {
                    if (pokerOnTable.contains(new Poker(poker.getSuit(), poker.getNum() - 1))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 出牌
     *
     * @param poker 打出的牌
     */
    public static void play(Poker poker) {
        try {
            players[order].removePoker(poker);
            pokerOnTable.add(poker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 扣牌
     *
     * @param poker 扣下的牌
     */
    public static void punish(Poker poker) {
        try {
            players[order].punish(poker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束回合，返回游戏是否结束
     *
     * @return true为游戏结束，false为游戏继续。
     */
    public static boolean finishRound() {
        int finishCount = 0;
        do {
            if (order == PLAYER_NUM - 1) {
                order = 0;
            } else {
                order++;
            }
            if (++finishCount == PLAYER_NUM) {
                return true;
            }
        } while (players[order].isFinished());
        return false;
    }

    public static TreeSet<Player> getResult() {
        TreeSet<Player> set = new TreeSet<>();
        for (Player p : players) {
            set.add(p);
        }
        return set;
    }

    /**
     * 初始化玩家
     *
     * @param realPlayerNum 真实玩家的数量
     */
    private static void initPlayers(int realPlayerNum, Stack<String> names) {
        players = new Player[PLAYER_NUM];
        for (int i = 0; i < PLAYER_NUM; i++) {
            players[i] = new Player(i + "");
        }
        for (int i = 0; i < realPlayerNum; i++) {
            int numInList = (int) (Math.random() * (PLAYER_NUM - 1 + 1));
            while (players[numInList].isRealPlayer()) {
                if (numInList == PLAYER_NUM - 1) {
                    numInList = 0;
                } else {
                    numInList++;
                }
            }
            players[numInList].setRealPlayer(true);
            players[numInList].setName(names.pop());
        }
    }

    /**
     * 初始化所有卡牌，发牌
     */
    private static void initPoker() {
        List<Poker> allPoker = new ArrayList<>();
        try {
            for (int i = 1; i <= 13; i++) {
                allPoker.add(new Poker(Suit.club, i));
                allPoker.add(new Poker(Suit.hearts, i));
                allPoker.add(new Poker(Suit.spade, i));
                allPoker.add(new Poker(Suit.square, i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int dealOrder = 0;
        while (allPoker.size() != 0) {
            int numInList = (int) (Math.random() * (allPoker.size() - 1 + 1));
            Poker poker = allPoker.get(numInList);
            if (poker.getSuit().equals(Suit.spade) && poker.getNum() == 7) {
                order = dealOrder;
            }
            players[dealOrder].addPoker(poker);

            if (dealOrder == PLAYER_NUM - 1) {
                dealOrder = 0;
            } else {
                dealOrder++;
            }
            allPoker.remove(numInList);
        }
    }
}
