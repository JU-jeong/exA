package exA.a04;

import java.io.IOException;
import java.util.Scanner;


/**
 * コマンドプロンプトから入力された備考文字列が
 * 20バイトを超えていた場合、切り捨てて表示するクラス。
 * @author ju_jeongseok
 * @param sc 値入力
 * @param howManyBytes 入力されたバイトのサイズ
 * @param twenty 切り捨ての基準になる値
 * @return バイトが20バイト以下の場合、原文のまま返還,20を超えたら繰り返しを終了し、累積された値をビルダーバッファに追加された値を返却		 
 */
public class SubStringByte {
	public static void main(String[] args) throws IOException {
		// 1. 文字列の入力
		Scanner sc = new Scanner(System.in);
		String soManyBytes = sc.nextLine();
		sc.close();

		// 2.文字列をeuc-jpでエンコードしてバイトに変換後20バイト分だけ残すメソッド実行後に出力(utf-8基準では全角文字一文字を3バイトと認識するため、一文字が2バイトと認識される出力形式でエンコードしました。)
		String howManyBytes = new String(soManyBytes.getBytes("euc-jp"), "euc-jp");
		String enoughBytes = byteCuter(howManyBytes, 20);
		System.out.println(new String(enoughBytes.getBytes("euc-jp"), "euc-jp"));
	}

	//バイト数による文字列削減出力のためのメソッド
	public static String byteCuter(String howManyBytes, int twenty){
		//1. バッファのサイズ、半角&全角文字の区分のための変数設定
		if (howManyBytes.toString().getBytes().length > twenty) {
			StringBuilder stringBuilder = new StringBuilder(twenty);
			int byteCount = 0;

			//2. 引数文字列をcharに変更後、UnicodeBlockメソッドで日本語の全角文字であるかをチェック、正しければ2バイトだから+2を、それ以外は1バイトだから+1を変数に追加
			for (char ch : howManyBytes.toCharArray()) {
				if (Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
						|| Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.HIRAGANA
						|| Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.KATAKANA) {
					//2-1. 全角文字で認識
					byteCount += 2;
				} else {
					//2-2. 半角文字として認識
					byteCount++;
				}
				//3. 累積バイト数が20を超えたら繰り返しを終了し、累積された値をビルダーバッファに追加
				if (byteCount > twenty)
					break;
				stringBuilder.append(ch);
			}
			//4. 追加された値を返却
			return stringBuilder.toString();
		} else {
			//1-1. バイトが20バイト以下の場合、原文のまま返還
			return howManyBytes;
		}
	}
}
