package team2.vo;

/**
 *
 * 플레이어에 대한 필드 설정
 *
 * @param
 *   1. String name
 *   2. int Order
 *   3. Card card
 *   4. Deck deck
 *
 * @return
 * 	 1. 게임플레이어 이름
 *   2. 플레이어 순서
 *   3. 카드 한장
 *   4. 카드 덱(처음 시작시 2인 : 4장, 3인 3장)
 *
 * @author 김성진
 * @since 1.0
 * @history '23.02.07
 *
 */

import team2.service.Deck;

import java.util.ArrayList;

public class Player implements Comparable<Player>{
    private String name;
    public int order;
    private boolean play = true;

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    private ArrayList<Card> card = new ArrayList<Card>();
    Deck deck = new Deck();

    public Player() {

    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCard() {
        return card;
//        return deck.sort(card);
    }

    public void setCard(ArrayList<Card> card) {
        this.card = card;
    }

    public void getCard(Card card) {
        this.card.add(card);
    }

    @Override
    public String toString() {
        return "Player [ " + name + " ]";
    }

    @Override
    public int compareTo(Player o) {
        return o.order- order;
    }
}
