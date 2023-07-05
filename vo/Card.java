package team2.vo;

/**
 * Card에 대한 필드 설정
 *
 * @param
 *   1. String color
 *   2. Integer value
 *   3. boolean status
 * @return
 *   1. 흑, 백
 *   2. 카드값(0~12, 조커)
 *   3. 카드 오픈된 상태값
 *
 *
 * @author 김성진
 * @since 1.0
 * @history '23.02.07
 *
 */

public class Card implements Comparable<Card>{
    private String color;
    private Integer value;
    private boolean status;

    public Card() {

    }

    public Card(String color, Integer value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void flip() {
        this.status = !status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String myCard() {
        String ret = null;
        ret = color + " : " + value;
        return ret;
    }

    @Override
    public String toString() {
        String ret = null;
        String val = getStatus() ? String.valueOf(getValue()) : "*";
        ret = color + " : " + val;
        return ret;
    }

    @Override
    public int compareTo(Card c) {
        return value - c.value;
    }
}
