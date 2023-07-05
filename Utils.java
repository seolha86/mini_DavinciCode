package team2;
/**

 * 문자열 입출력
 * 
 * @param String msg : 입력받은 문자열
 * @return 해당 문자열이 문자면 문자 출력, 숫자면 숫자출력 
 * 
 * @author 김성진
 * @since 1.0
 * @histroy '23.2.6
 * 
 * 
 */


import java.util.Scanner;

public class Utils {
	private static Scanner scanner = new Scanner(System.in);
	
	public static String nextLine(String msg) {
		System.out.println(msg);
		return scanner.nextLine();
	}
	public static int nextInt(String msg) {
		return Integer.parseInt(nextLine(msg));
	}
}
