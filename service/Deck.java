package team2.service;
/**
 *
 Deck(카드 팩)에 대한 메서드 작성

 * 내용
 * 		1. 카드 : 검정, 흰색 카드에 따른 조커(-1), 0~12씩 생성
 * 		2. sort() : 뽑은 카드에 대한 정렬(검정이 흰색보다 우선, 숫자에 대한 오름차순, 조커는 플레이어 마음)
 *
 *  @param
 *    1. int color
 *    2. int num
 *    3. Card cards
 *  @return
 *    1. 컬러(흑, 백) 선택
 *    2. 플레이어가 선택한 컬러에 대한 카드숫자(랜덤값) 값 저장
 *    3. 랜덤값으로 선정된 카드들에 대한 오름차순 정렬 값
 *
 *  @author 김성진
 *  @since 1.0
 *  @history '23.02.07
 *
 */

import team2.vo.Card;
import team2.vo.Player;

import java.util.*;

import static team2.Utils.nextInt;

public class Deck {
    BoardServiceImpl board = new BoardServiceImpl();
    // 검정색, 흰색 카드 덱
    private ArrayList<Integer> whiteValues = new ArrayList<>(List.of(-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
    private ArrayList<Integer> blackValues = new ArrayList<>(List.of(-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));

    public ArrayList<Integer> getWhiteValues() {
        return whiteValues;
    }
    public ArrayList<Integer> getBlackValues() {
        return blackValues;
    }

    // 카드 나눠주기 (한장)
    public Card handOut() {
        System.out.println("카드 색을 골라주세요");
        int color = nextInt("0 : 검정색  1 : 흰색");
        // color 값이 0이면 검정덱, 1이면 흰덱
        ArrayList<Integer> tmp = color == 0 ? blackValues : whiteValues;
        int num = (int) (Math.random() * tmp.size());

        // 상태 유지 예외처리 
        while (tmp.size() == 0) {
            System.out.println((color == 0 ? "검정색" : "흰색") + " 카드가 없습니다. 다른 카드를 골라주세요");
            color = nextInt("0 : 검정색  1 : 흰색");
        }

        while (color < 0 || color > 2) {
            System.out.println("허용되지 않는 카드색깔입니다. 0과 1의 숫자를 입력하세요");
            color = nextInt("0 : 검정색  1 : 흰색");
            if(color == 0 || color == 1) {
                break;
            }
        }
        
        // color가 0이면 검정색, 1이면 흰색 카드 리턴, 뽑힌 카드는 삭제
        return new Card(color == 0 ? "검정색" : "흰색", tmp.remove(num));
    }

    // 카드 정렬
    public ArrayList<Card> sort(ArrayList<Card> cards) {
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                // 값이 같을 땐 color (검 -> 흰) 순서로 정렬
                if (o1.getValue() == o2.getValue()) {
                    return o1.getColor().compareTo(o2.getColor());
                }
                return o1.getValue() - o2.getValue();
            }
        });
        return cards;
    }

    // 내 카드 목록 출력
    public void myCard(Player p) {
        System.out.print("내 카드 [");
        for (int j = 0; j < p.getCard().size(); j++) {
            System.out.print(p.getCard().get(j).myCard());
            if (j < p.getCard().size() - 1) System.out.print(", ");
            else System.out.println("]");
        }
    }
}

