package team2.service;
/**
 * 플레이어 서비스에 대한 메서드 작성
 *
 * 1. 카드 맞추기 기능
 * 2. 카드 맞춘 후 추가로 맞출 것인지, 플레이어 턴을 넘길것인지에 대한 기능
 * 3. 카드 못맞춘 경우 플레이어가 넘어가는 기능
 *
 * @param
 *   1. String name
 *   2. int index
 *   3. int value
 * @return
 *   1. 플레이어가 입력한 플레이어이름
 *   2. 플레이어가 선정한 랜덤값의 카드 덱의 주소값
 *   3. 해당 플레이어 덱 안에 있는 가지고 있는 카드값
 *
 * @author 김성진
 * @since 1.0
 * @history '23.02.07

 * @author 김설하
 * @history '23.02.09'
 * adjust : 카드를 맞췄을 시 flip
 * answerCheck : 정답일 경우 더 진행할지 턴을 넘길지 선택
 *
 * @history '23.02.13'
 * 이미 맞춘 카드는 다시 맞추기 못하도록 설정
 */

import team2.vo.Card;
import team2.vo.Player;

import java.util.ArrayList;

import static team2.Utils.nextInt;

public class PlayerServiceImpl implements PlayerService{
    BoardServiceImpl board = new BoardServiceImpl();
    Deck deck = new Deck();

    // 맞추려는 카드 고르기
    public boolean adjust() {
        System.out.println("누구의 카드를 맞추시겠습니까?");
        int pick = nextInt("");

        while (pick <= -1 || pick > board.getPlayerNum() - 1) {
            System.err.println("다시 선택해 주세요");
            System.out.println("누구의 카드를 맞추시겠습니까?");
            pick = nextInt("");
        }

        String name = board.getPlayers().get(pick).getName();
        int index = nextInt("몇 번째 카드 숫자를 맞추시겠습니까?");
        while (index < 0 || index > board.getPlayers().get(pick).getCard().size()) {
            System.err.println("범위 내의 숫자를 입력하세요(1 ~ " + board.getPlayers().get(pick).getCard().size() + ")");
            index = nextInt("몇 번째 카드 숫자를 맞추시겠습니까?");
        }

        int value = nextInt("카드의 숫자는?");
        while (value < -1 || value > 11) {
            System.err.println("범위 내의 숫자를 입력하세요(-1 ~ 11)");
            value = nextInt("카드의 숫자는?");
        }

        // 이미 맞춘 카드 다시 맞추지 못하도록
        if (board.getPlayers().get(pick).getCard().get(index - 1).getStatus()) {
            System.out.println("이미 맞춘 카드입니다. 다른 카드를 골라주세요");
            return true;
        } else { // 맞춘 카드가 아닐 때 리턴
            return answerCheck(name, index -1, value);
        }
    }

    // 카드의 값이 맞는지 확인
    public boolean answerCheck(String name, int index, int value) {
        Player p = board.findBy(name);
        if (p.getCard().get(index).getValue().equals(value)) {
            p.getCard().get(index).flip();
            System.out.println("맞았습니다! " + p.getName() + " 의 " + (index + 1) + "번째 카드는 " + p.getCard().get(index) + " 였습니다.");

            // 카드를 맞췄을 때마다 탈락한 사람이 있는지, 게임이 끝나야 하는지 확인
            board.failCheck();
            board.endCheck();

            // 플레이어가 카드를 더 맞출지 턴을 넘길지 확인
            int pass = nextInt("더 맞추시겠습니까?  1. 진행  2. 턴 종료");

            while (pass < 0 || pass > 2) {
                System.out.println("범위 내의 숫자를 입력하세요(1 ~ 2)");
                pass = nextInt("더 맞추시겠습니까?  1. 진행  2. 턴 종료");
            }

            switch (pass) {
                case 1 -> {
                    return true;
                }
                case 2 -> {
                    return false;
                }
            }

            return true;
        } else {
            System.out.println("틀렸습니다.");
            return false;
        }
    }

    // 플레이어가 조커를 어느 위치에 둘지 확인
    public void joker(Player p) {
        // 만약 조커의 상태가 true이면 위치 변경 불가
        for (int i = 0; i < p.getCard().size(); i++) {
            Card c = p.getCard().get(i);
            if (c.getValue() == -1 && c.getStatus()) {
                return;
            }
        }

        // 카드의 숫자만 담는 리스트 생성 후, 숫자만 추가
        ArrayList<Integer> value = new ArrayList<>();
        for (int i = 0; i < p.getCard().size(); i ++) {
            value.add(p.getCard().get(i).getValue());
        }

        // 카드에 조커가 포함되어 있으면
        if (value.contains(-1)) {
            deck.myCard(p); // 현재 내 카드 리스트 출력, 조커를 배치할 위치 설정
            p.getCard().add((nextInt("어느 위치에 조커를 배치하겠습니까") - 1), (p.getCard().remove(value.indexOf(-1))));
        }
    }
}
