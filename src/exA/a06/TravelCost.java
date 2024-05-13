package exA.a06;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * 入力された距離単価と往復距離を元に、1 日あたりの交通費と現在日付当月 1 ヶ月の交通費、
 * それぞれ課税額、非課税額を表示するクラス
 * @author ju_jeongseok
 * @param workingDays 現在の月の営業日数を表します。
 * @param moneyandLengthSplitInt 入力である距離と単価の整数部分を保持するために使用されます。
 * @param moneyandLengthSplitDouble 入力である距離と単価の小数部分を保持するために使用されます。
 * @param df 金額の出力をフォーマットするためのDecimalFormatオブジェクトです。
 * @param sc 入力を読み取るために使用されるScannerオブジェクトです。
 * @param moneyAndLength 距離と単価の入力を単一の文字列として保持します。
 * @param moneyAndLengthSplit[] moneyAndLength文字列を分割した後の距離と単価の値を文字列として保持する配列です。
 * @param moneyPerDay 距離と単価に基づいて計算された1日あたりの交通費を保持するために使用されます。
 * @param moneyPerMonth 1日あたりのコストと営業日数に基づいて計算された1ヶ月の交通費を保持するために使用されます。
 */
 
public class TravelCost {
	public static void main(String[] args) {
		// 1. workingDays, 現在の日付の月の営業日を返す。
		int workingDays = calculateWorkingDays(LocalDate.now().withDayOfMonth(1),
				LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()));

		// 2. 距離と単価の出力のための形式準備
		int moneyandLengthSplitInt = 0;
		double moneyandLengthSplitDouble = 0.00;
		DecimalFormat df = new DecimalFormat("###,###");

		// 3. 入力と型変換
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("距離と単価をそれぞれ入力してください。");
			String moneyAndLength = sc.nextLine();
			sc.close();
			String[] moneyAndLengthSplit = moneyAndLength.split(" ");
			moneyandLengthSplitInt = Integer.parseInt(moneyAndLengthSplit[0]);
			moneyandLengthSplitDouble = Double.parseDouble(moneyAndLengthSplit[1]);

			// 4. 入力できる金額は 1～100、距離は 0.01～100.00 の範囲
			if (moneyandLengthSplitInt >= 1 && moneyandLengthSplitInt <= 100 && moneyandLengthSplitDouble >= 0.01
					&& moneyandLengthSplitDouble <= 100.00) {
				break;
			} else {
				System.out.println("正しい形の値を入力してください。");
			}
		}

		// 5. 入力と型変換された距離単価と往復距離の出力(小数点の桁に数字があれば出力、なければ定数の形で出力)
		System.out.println("距離単価: " + df.format(moneyandLengthSplitInt) + "円");
		System.out.println("往復距離: "
				+ (moneyandLengthSplitDouble % 1 == 0 ? String.format("%.0f%s", moneyandLengthSplitDouble, "km")
						: String.format("%.2f%s", moneyandLengthSplitDouble, "km")));

		// 6. 距離単価と往復距離を活用して1日あたり交通費と1 ヶ月交通費を出力
		double moneyPerDay = 1;
		moneyPerDay = Math.floor(moneyPerDay * moneyandLengthSplitDouble);
		int moneyPerMonth = (int) (moneyPerDay * workingDays);
		System.out.println("営業日: " + workingDays + "日");
		System.out.println("1日あたり交通費: " + df.format(moneyPerDay) + "円");
		System.out.println("1 ヶ月交通費: " + df.format(moneyPerMonth) + "円");

		// 7. moneyTaxExcludedメソッドに往復距離を引数にして、課税額と非課税額をそれぞれ返えして出力
		if (moneyPerMonth >= moneyTaxExcluded(moneyandLengthSplitDouble)) {
			System.out
					.println("課税額: " + df.format((moneyPerMonth - moneyTaxExcluded(moneyandLengthSplitDouble))) + "円");
		} else {
			System.out.println("課税額: 0円");
		}
		System.out.println("非課税額: " + df.format(moneyTaxExcluded(moneyandLengthSplitDouble)) + "円");
	}

	// 8. メソッドmoneyTaxExcluded
	/**
	 * 
	 * @param myLength
	 * @return 0
	 * @return 31600
	 * @return myLength
	 * 
	 */
	public static int moneyTaxExcluded(double myLength) {
		// 8-1.距離が2km未満の場合、非課税額を最低値で設定、55km以上だった場合、非課税額を最大値で設定してリーダン
		if (myLength < 2) {
			return 0;
		}

		if (myLength >= 55) {
			return 31600;
		}

		/*
		 * 8-2. 距離とそれに応じて税額を調整するための2組の配列の準備
		 *
		 * 自動車や自転車などの交通用具を使用している人に支給する通勤手当 通勤距離が片道55キロメートル以上である場合 31,600円 同左
		 * 通勤距離が片道45キロメートル以上55キロメートル未満である場合 28,000円 同左 通勤距離が片道35キロメートル以上45キロメートル未満である場合
		 * 24,400円 同左 通勤距離が片道25キロメートル以上35キロメートル未満である場合 18,700円 同左
		 * 通勤距離が片道15キロメートル以上25キロメートル未満である場合 12,900円 同左 通勤距離が片道10キロメートル以上15キロメートル未満である場合
		 * 7,100円 同左 通勤距離が片道2キロメートル以上10キロメートル未満である場合 4,200円 同左 通勤距離が片道2キロメートル未満である場合
		 * （全額課税） 同左
		 */

		int[] lawlength = new int[] { 2, 10, 15, 25, 35, 45, 55 };
		int[] tax = new int[] { 4200, 7100, 12900, 18700, 24400, 28000, 31600 };

		// 8-3. 距離が当該税額に達するまで配列を巡回
		for (int i = 0; i < lawlength.length; i++) {
			if (myLength >= lawlength[i]) {
			} else {
				myLength = tax[i - 1];
			}
		}

		// 8-4. 距離における非課税額設定後返還
		return (int) myLength;
	}

	// 0. メソッドcalculateWorkingDays + getHolidayList + isWorkingDayによる勤務日の計算
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {

		// 1. 当該月の休日の一覧を計算するための休日リストを宣言（getHolidayListメソッド宣言）
		List<String> holidayList = getHolidayList(startDate.getMonthValue());

		// 3. 開始日が終了日より大きいか同じでない場合、各日が勤務日であることを確認
		int workingDays = 0;
		// 3-1. 開始日と終了日の間の各日を繰り返し、各日が勤務日であるかを確認後、勤務日の場合、勤務日カウンターを増加（isWorkingDayメソッド実行）
		while (!startDate.isAfter(endDate)) {
			if (isWorkingDay(startDate, holidayList)) {
				workingDays++;
			}
			// 5. 日取りを日取りにして巡回
			startDate = startDate.plusDays(1);
		}
		// 6. 計算された勤務日数を返還
		return workingDays;
	}

	// 2. メソッドgetHolidayList
	/**
	 * 
	 * @param month
	 * @return holidays
	 */
	private static List<String> getHolidayList(int month) {
		// 2-1. 休日(土曜日、日曜日)を入れるオブジェクトとその月の最初の日と最後の日を表すLocalDateオブジェクトを作成
		List<String> holidays = new ArrayList<>();
		LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
		LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

		// 2-2. 範囲内の各日付について休日であることを確認し、休日の場合は休日リストに追加
		while (!firstDayOfMonth.isAfter(lastDayOfMonth)) {
			if (firstDayOfMonth.getDayOfWeek() == DayOfWeek.SATURDAY
					|| firstDayOfMonth.getDayOfWeek() == DayOfWeek.SUNDAY) {
				holidays.add(firstDayOfMonth.toString());
			}
			firstDayOfMonth = firstDayOfMonth.plusDays(1);
		}
		// 2-3. 休日リストを返却
		return holidays;
	}

	// 4. メソッドisWorkingDay
	/**
	 * 
	 * @param date
	 * @param holidayList
	 * @return true
	 * @return false
	 */
	private static boolean isWorkingDay(LocalDate date, List<String> holidayList) {
		// 4-1. 曜日が土曜日や日曜日ではなく、日付が休日リストにないか確認後、両方の条件を満たしていれば勤務日を表すという意味でtrueを返還
		return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY
				&& !holidayList.contains(date.toString());
	}
}
