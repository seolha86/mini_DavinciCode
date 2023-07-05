package team2;

/**

 * @내용
 * 		1. 시작화면 구성
 * 		2. 게임 시작과 종료 외에는 사용자 입력값 x
 * 		3.
 *
 * @author 김성진
 * @since 1.0
 * @histroy '23.2.6
 *
 *
 */



import team2.service.BoardServiceImpl;
import team2.service.Deck;
import team2.service.PlayerServiceImpl;
import team2.vo.Player;

import java.util.Objects;

import static team2.Utils.*;

//게임시작 화면 출력
public class Main {
    public static void main(String[] args) throws Exception {
        BoardServiceImpl board = new BoardServiceImpl();
        PlayerServiceImpl play = new PlayerServiceImpl();
        Deck deck = new Deck();

        System.out.println("\r\n"
                + "______                _               _      _   _____             _       \r\n"
                + "|  _  \\              (_)             | |    (_) /  __ \\           | |      \r\n"
                + "| | | |  __ _ __   __ _  _ __    ___ | |__   _  | /  \\/  ___    __| |  ___ \r\n"
                + "| | | | / _` |\\ \\ / /| || '_ \\  / __|| '_ \\ | | | |     / _ \\  / _` | / _ \\\r\n"
                + "| |/ / | (_| | \\ V / | || | | || (__ | | | || | | \\__/\\| (_) || (_| ||  __/\r\n"
                + "|___/   \\__,_|  \\_/  |_||_| |_| \\___||_| |_||_|  \\____/ \\___/  \\__,_| \\___|\r\n"
                + "                                                                      \r\n"
                + "");

        System.out.println("다빈치 코드 게임에 오신것을 환영합니다.");
        System.out.println("게임을 시작하시겠습니까?");

        int input = Utils.nextInt("1. 게임시작  2. 게임종료");

        while (input < 0 || input > 2) {
            System.out.println("범위 내의 숫자를 입력하세요(1 ~ 2)");
            input = Utils.nextInt("1. 게임시작  2. 게임종료");
        }

        switch (input) {
            case 1 ->  // 게임시작
                    board.gameStart();
            case 2 -> // 게임종료
                    board.gameEnd();
        }

        board.order();

        // 순서대로 게임 시작
        int turn = 0;
        for (int i = 0; ; i++) {
            Player p = board.getPlayers().get(i % board.getPlayerNum());

            if (p.isPlay()) {
                nextLine(p.getName() + "님의 차례입니다. 엔터키를 입력하세요");

                if (turn / board.getPlayerNum() < 1) {
                    System.out.println("시작패를 뽑아주세요");

                    // 게임 진행 인원에 따라 시작패 개수 설정
                    int startCard = board.getPlayerNum() == 2 ? 4 : 3;

                    // 시작 패 분배
                    for (int j = 0; j < startCard; j++) {
                        p.getCard(deck.handOut());
                    }

                    deck.sort(p.getCard());
                    play.joker(p);
                    deck.myCard(p);
                } else {
                    // 카드덱에 남아있는 카드가 없으면 카드를 뽑지 않고 계속 진행
                    if (turn / board.getPlayerNum() > 1) {
                        if (!(deck.getBlackValues().size() == 0) && !(deck.getWhiteValues().size() == 0)) {
                            p.getCard(deck.handOut());
                            deck.sort(p.getCard());
                            play.joker(p);
                        } else {
                            System.out.println("더 이상 뽑을 카드가 없습니다. 게임을 계속 진행합니다.");
                        }
                    }

                    for (int j = 0; j < board.getPlayerNum(); j++) {
                        if (board.getPlayers().get(j) != p) {
                            System.out.print(board.getPlayers().get(j) + " 의 카드 ");
                            System.out.println(board.getPlayers().get(j).getCard());
                        }
                    }

                    deck.myCard(p);

                    // 맞출 수 있는 플레이어 출력
                    for (int j = 0; j < board.getPlayerNum(); j++) {
                        if (Objects.equals(board.getPlayers().get(j).getName(), p.getName())) continue;
                        if (board.getPlayers().get(j).isPlay()) {
                            System.out.print(j + " : " + board.getPlayers().get(j).getName() + "\t");
                        }
                    }

                    // 맞추거나, 턴을 종료할 때까지 반복
                    while (play.adjust()) {
                        for (int j = 0; j < board.getPlayerNum(); j++) {
                            if (board.getPlayers().get(j) != p) {
                                System.out.print(board.getPlayers().get(j) + " 의 카드 ");
                                System.out.println(board.getPlayers().get(j).getCard());
                            }
                        }

                        deck.myCard(p);
                        
                        for (int j = 0; j < board.getPlayerNum(); j++) {
                            if (Objects.equals(board.getPlayers().get(j).getName(), p.getName())) continue;
                            if (board.getPlayers().get(j).isPlay()) {
                                System.out.print(j + " : " + board.getPlayers().get(j).getName() + "\t");
                            }
                        }
                    }
                }
            }
            turn++;
        }
    }
}