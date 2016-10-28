package View;

import bean.Player;
import bean.Poker;
import presenter.PokerHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by tjliqy on 2016/10/18.
 */
public class TerminalView {
    public static void main(String[] args){
        //开始游戏
        System.out.println("点击回车开始游戏");
        sin();

        //初始化游戏
        System.out.println("输入游戏人数");
        int playerNum = Integer.valueOf(sin());
        Stack<String> playerNames = new Stack<>();
        for (int i = 0; i < playerNum; i++) {
            System.out.println("输入玩家" + i + "姓名");
            playerNames.add(sin());
        }
        PokerHelper.startGame(playerNum,playerNames);

        //回合开始
        do {
            //输出桌面上的牌
            TreeSet<Poker> pokersOnTable = PokerHelper.getPokerOnTable();
            System.out.println("桌上所有被打出的牌：");
            StringBuffer sbPokersOnTable = new StringBuffer("");

            for (Poker p: pokersOnTable) {
                sbPokersOnTable.append(p.getSuit()).append(p.getNum()).append(" ");
            }
            System.out.println(sbPokersOnTable);
            //输出玩家手牌
            Player player = PokerHelper.getPlayerInCurrentRound();
            System.out.println(PokerHelper.getOrder() + "玩家 " + player.getName() + " 手牌区：");
            StringBuffer sbPokersInHand = new StringBuffer("");
            List<Poker> pokersInHand = new ArrayList<>();
            pokersInHand.addAll(player.getPokersInHand());
            int count1 = 0;
            for (Poker p : pokersInHand) {
                sbPokersInHand.append(count1).append(".").append(p.getSuit()).append(p.getNum()).append(" ");
                count1++;
            }
            System.out.println(sbPokersInHand);
            //输出玩家扣牌
            System.out.println(PokerHelper.getOrder() +"玩家 " + player.getName() + " 扣牌区：");
            StringBuffer sbPokersForPunish = new StringBuffer("");
            List<Poker> pokersForPunish = new ArrayList<>();
            pokersForPunish.addAll(player.getPokersForPunish());
            int count2 = 0;
            for (Poker p : pokersForPunish) {
                sbPokersForPunish.append(count2).append(".").append(p.getSuit()).append(p.getNum()).append(" ");
                count2++;
            }
            System.out.println(sbPokersForPunish);
            if (PokerHelper.check()){
                int num;
                do {
                    System.out.println("输入出牌序号：");
                    num = Integer.valueOf(sin());
                }while (!PokerHelper.check(pokersInHand.get(num)));
                PokerHelper.play(pokersInHand.get(num));
            }else {
                System.out.println("输入扣牌序号：");
                PokerHelper.punish(pokersInHand.get(Integer.valueOf(sin())));
            }
        }while (!PokerHelper.finishRound());

        List<Player> list = new ArrayList<>(PokerHelper.getResult());
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第"+i+"名：玩家 " +list.get(i).getName() + " 分数：" + list.get(i).getPunishScore());
        }
    }

    private static String sin(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void soutPokers(Collection collection){
        List<Poker> list = new ArrayList<>();
        list.addAll(collection);
        StringBuffer sb = new StringBuffer("");

    }
}
