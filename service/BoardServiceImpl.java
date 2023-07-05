package team2.service;

/**
 *
 * 게임판에 대한 메서드 작성
 *
 * 내용
 * 		1. gamestart() : 게임플레이어 선정(2인, 4인)
 * 		2. gameend() : 게임 종료
 * 		3. order() : 플레이어에 대한 랜덤값에 의한 순서 정하기
 *		4. rank() : 게임 종료에 따른 플레이어 순위 표시
 *
 * @param
 *   1. int players
 *   2. String name
 * @return
 *   1. players 수에 대한 게임진행, 게임인원선정, 게임순서(랜덤값)
 *   2. name 사용자가 원하는 게임플레이어 이름 설정
 *
 *
 * @author 김성진
 * @since 1.0
 * @history '23.02.07
 *
 */

import team2.vo.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static team2.Utils.*;

public class BoardServiceImpl implements BoardService{
    private static int playerNum;
    ArrayList<Integer> orderNum = new ArrayList<>();
    static ArrayList<Player> players = new ArrayList<>();
    static ArrayList<Player> playerRank = new ArrayList<>();

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int players) {
        playerNum = players;
    }

    public void addPlayerRank(Player player) {
        playerRank.add(player);
    }

    // 플레이어 추가
    public void playerRegister() {
        String name = nextLine("플레이어 이름을 입력하세요 > ");
        Player player = new Player(name);
        players.add(player);
    }

    // 이름으로 플레이어 찾기
    public Player findBy(String name) {
        Player player = null;
        for (Player value : players) {
            if (value.getName().equals(name)) {
                player = value;
                break;
            }
        }
        return player;
    }

    // 인원 수에 따라 게임 시작
    @Override
    public void gameStart() {
        int players = nextInt("몇명이 플레이하겠습니까? 1 : 2인 | 2 : 4인");

        // 예외 처리
        while (players < 0 || players > 2) {
            System.out.println("범위 내의 숫자를 입력하세요(1 ~ 2)");
            players = nextInt("1. 게임시작  2. 게임종료");
        }

        setPlayerNum(players * 2);

        switch (players) {
            case 1 -> System.out.println("2인 게임시작");
            case 2 -> System.out.println("4인 게임시작");
        }
    }

    // 게임 종료
    @Override
    public void gameEnd() {
        System.out.println("게임을 종료합니다");
        System.exit(0);
    }

    // 플레이어 순서 정하기
    public void order() {
        // 게임 시작 시 지정했던 인원수만큼 플레이어 생성
        for (int i = 0; i < getPlayerNum(); i++) {
            playerRegister();
            getPlayers().get(i).order = ((int) (Math.random() * Integer.MAX_VALUE));
        }

        // 정렬하여 순서 출력
        Collections.sort(getPlayers());
        for (int i = 0; i < getPlayerNum(); i++) {
            System.out.println((i + 1) + "번 순서는 " + getPlayers().get(i).getName() + "입니다.");
        }
    }

    // 게임 최종 순위 출력
    @Override
    public void rank() {
        Collections.reverse(playerRank); // 플레이어가 탈락한 순서 리스트 역순 정렬
        System.out.println("====순위====");
        ArrayList<Player> rank = new ArrayList<>(playerRank.subList(0, playerNum));
        for (Player player : rank) {
            System.out.println(player); // 순위 출력

        }
    }

    // 플레이어 탈락 체크
    public void failCheck() {
        for (Player player : getPlayers()) {
            List<Boolean> statusList = new ArrayList<>(); // 카드 상태 리스트
            if (player.isPlay()) {
                for (int k = 0; k < player.getCard().size(); k ++) { // 플레이어의 모든 카드 확인
                    statusList.add(player.getCard().get(k).getStatus()); // 카드 상태 리스트에 한 플레이어의 모든 카드 상태값 추가
                }
            }

            if (!statusList.contains(false)) { // 만약 상태값이 false를 포함하고 있지 않으면
                addPlayerRank(player); // 탈락한 플레이어 리스트에 플레이어 추가
                player.setPlay(false); // 플레이어의 게임 상태값 false로 변경 (게임에 참여하지 않음)
            }
        }
    }

    // 게임이 끝났는지 확인하는 메서드
    public void endCheck() {
        ArrayList<Boolean> play = new ArrayList<>(); // 플레이어의 플레이 상태값을 넣을 메서드 
        for (int i = 0; i < getPlayers().size(); i++) { 
            play.add(getPlayers().get(i).isPlay()); // 플레이어들의 게임 상태값 추가
        }
        
        if (Collections.frequency(play, true) == 1) { // 플레이중인 플레이어가 한명일 때
            for (Player player : getPlayers()) {
                if (player.isPlay()) {
                    addPlayerRank(player); // 마지막 남은 플레이어 탈락한 플레이어 리스트에 추가
                }
            }
            rank(); // 랭킹 출력
            gameEnd(); // 게임 종료
        }
    }
}