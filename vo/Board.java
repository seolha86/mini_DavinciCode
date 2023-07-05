package team2.vo;
/**
 *

 * 내용
 * 		1. 플레이어 수에 따른 순서정하기
 * 		2. 카드 뒤집기 기능 구현
 * 			- 어떤 플레이어가 상대방의 카드를 골라 맞추면 카드오픈
 * 			- 못 맞추면 그대로 진행
 *
 * @param
 *   1. int playerOrder
 *   2. int reverse
 *
 * @return
 *   1. 게임플레이어 인원(2인 vs 4인)
 *   2. 카드 뒤집기
 *
 *
 * @author 김성진
 * @since 1.0
 * @history '23.02.07
 *
 *
 */

public class Board {

	// 플레이어순서에 대한 변수(1,2 or 1,2,3,4)
	private int playerOrder;

	// 뒤집기에 대한 변수(0,1)
	private int reverse;

	public Board() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Board(int playerOrder, int reverse) {
		super();
		this.playerOrder = playerOrder;
		this.reverse = reverse;
	}

	public int getPlayerOrder() {
		return playerOrder;
	}

	public void setPlayerOrder(int playerOrder) {
		this.playerOrder = playerOrder;
	}

	public int getReverse() {
		return reverse;
	}

	public void setReverse(int reverse) {
		this.reverse = reverse;
	}

	@Override
	public String toString() {
		return "Board [playerOrder=" + playerOrder + ", reverse=" + reverse + "]";
	}


}
