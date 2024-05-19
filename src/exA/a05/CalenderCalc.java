package exA.a05;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Scanner;
/**
 * 
 * @author ju_jeongseok
 * @param sc 入力を読み取るために使用されるScannerオブジェクトです。
 * @param dataSource 入力日付文字列を保持するための変数です。
 * @param dateSourceYear 入力日付文字列から抽出された年のコンポーネントを保持します。
 * @param dateSourceMonth 入力日付文字列から抽出された月のコンポーネントを保持します。
 * @param dateSourceDay 入力日付文字列から抽出された日のコンポーネントを保持します。
 * @param dateSourceStr[] 入力日付文字列の年、月、日のコンポーネントを文字列として保持する配列です。
 * @param dateSourceInt[] 入力日付文字列の年、月、日のコンポーネントを整数として保持する配列です。
 * @param startDate 入力した開始日を表すLocalDateオブジェクトです。
 * @param endDate 開始日の年の終わりを表すLocalDateオブジェクトです。
 * @param startWeek startDateの日付がその年の何回目の週に当たるか
 * @param endWeekYear endDateの日付がその年の何回目の週に当たるか
 * @param lastDate 月の最後日
 * @param dayOfWeek 月の最後日の曜日
 * @param dayOfWeekNumber 月曜日=1 ～ 日曜日=7として、月の最後日の曜日の番号を返す。
 * @param monthWeekCount 週数の計算に使われる
 * @param day 月の最後の日
 * @param sundayloop 月の最後日から、同じ月の一番近い日曜日の日付
 */
public class CalenderCalc {
	public static void main(String[] args) {
		// 1. 文字列入力
		Scanner sc = new Scanner(System.in);
		String dateSource = sc.nextLine();
		sc.close();

		// 2. 文字列から年月日に当たる部分をそれぞれ分ける。
		String dateSourceYear = dateSource.substring(0, 4);
		String dateSourceMonth = dateSource.substring(4, 6);
		String dateSourceDay = dateSource.substring(6, 8);

		// 3. 分けられた部分を配列に入れる。
		String[] dateSourceStr = new String[3];
		dateSourceStr[0] = dateSourceYear;
		dateSourceStr[1] = dateSourceMonth;
		dateSourceStr[2] = dateSourceDay;

		// 変数は、利用する直前で宣言
		// 4. 計算のためのint型に変換
		int[] dateSourceInt = new int[3];
		for (int i = 0; i < dateSourceInt.length; i++) {
			dateSourceInt[i] = Integer.parseInt(dateSourceStr[i]);
		}

		// 5. 指定出力形式に変換 (2020/04/01)
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		// 6. 計算のための変数設定
		LocalDate startDate = LocalDate.of(dateSourceInt[0], dateSourceInt[1], dateSourceInt[2]);
		LocalDate endDate = LocalDate.of(dateSourceInt[0], 12, 31);

		// 6-1. startDateの日付がその年の何回目の週に当たるか
		int startWeek = startDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
		// 6-2. endDateの日付がその年の何回目の週に当たるか
		int endWeekYear = endDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
		// 6-3. 月の最後日
		LocalDate lastDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		// 6-4. 月の最後日の曜日
		DayOfWeek dayOfWeek = lastDate.getDayOfWeek();
		// 6-5. 月曜日=1 ～ 日曜日=7として、月の最後日の曜日の番号を返す。
		int dayOfWeekNumber = dayOfWeek.getValue();
		// 6-6. 週数の計算に使われる
		int monthWeekCount = 1;
		// 6-7. 月の最後の日
		int day = lastDate.getDayOfMonth();

		// 7. 月の最後日から、同じ月の一番近い日曜日の日付を返す。
		int sundayloop = day;
		if (dayOfWeekNumber != 7) {
			sundayloop = day - dayOfWeekNumber;
		}

		// 8. 該当月の最終日の数(30日、31日など)を求め、7を除いて月の週数を求め、0になると繰り返しドアを脱出して合算した週数を得られるように処理しました。
		for (int i = 0; i < monthWeekCount; i++) {
			if (sundayloop <= 0) {
				break;
			}
			monthWeekCount++;
			sundayloop -= 7;
		}

		// 9. 出力
		System.out.println("カレンダー作成日： " + startDate.format(dtf));
		System.out.println(dateSourceInt[0] + "年" + dateSourceInt[1] + "月の週数： " + monthWeekCount);
		System.out.println("年末までの週数： " + (endWeekYear - startWeek));
	}
}