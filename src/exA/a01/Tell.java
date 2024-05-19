package exA.a01;

import java.util.Scanner;
/**
 * コマンドプロンプトから入力されたハイフン区切りの電話番号を項目別に表示するクラス
 * 
 * @author ju_jeongseok
 * @param emerNum エラーを発生させるための変数
 */

public class Tell {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String number = sc.next();
		sc.close();
		/**
		 * エラー発生及び番号表示のための変数宣言
		 */
		
		int emerNum = 0;

		String[] numberParts = number.split("-");
		try {
			/**
			 * 緊急電話番号入力時にエラー発生
			 */
			emerNum = Integer.parseInt(numberParts[1]);

			System.out.println("市外局番: " + numberParts[0]);
			System.out.println("市内局番： " + numberParts[1]);
			System.out.println("加入者番号： " + numberParts[2]);

		} catch (ArrayIndexOutOfBoundsException e) {
			//エラー処理
			emerNum = Integer.parseInt(numberParts[0]);
			System.out.println(numberParts[0]+"は緊急通報用の電話番号です。");
		}
	}
}