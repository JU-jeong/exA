package exA.a02;

import java.time.LocalDate;

public class ClosingDate {
	public static void main(String[] args) {

		// 1.今日の日付を年の部分と月の部分に分ける
		LocalDate dateNow = LocalDate.now();
		String[] dateNowSplit = dateNow.toString().split("-");
		int[] dateNowSplitInt = new int[dateNowSplit.length];
		for (int i = 0; i < dateNowSplit.length; i++) {
			dateNowSplitInt[i] = Integer.parseInt(dateNowSplit[i]);
		}

		// 2. 12月20日を越えた場合、年と月に+1を付けて返す。
		if (dateNowSplitInt[1] == 12) {
			if (dateNowSplitInt[2] >= 20) {
				dateNowSplitInt[0] += 1;
				dateNowSplitInt[1] = 1;
				String dateNowSplitIntZero = String.format("%02d", dateNowSplitInt[1]);
				System.out.println("現在日付： " + dateNowSplitInt[0] + "年 " + (dateNowSplitInt[1] - 1) + "月 "
						+ dateNowSplitInt[2] + "日");
				System.out.println("年月度： " + dateNowSplitInt[0] + " 年度 " + dateNowSplitIntZero + " 月度");
			}

			// 3. 12月で20日を越えていない場合、年と月をそのままに返す。
			else {
				String dateNowSplitIntZero = String.format("%02d", dateNowSplitInt[1]);
				System.out.println(
						"現在日付： " + dateNowSplitInt[0] + "年 " + dateNowSplitInt[1] + "月 " + dateNowSplitInt[2] + "日");
				System.out.println("年月度： " + dateNowSplitInt[0] + " 年度 " + dateNowSplitIntZero + " 月度");
			}
		}

		// 4. 12月ではない月で20日を越えた場合、月だけに+1を付けて返す。
		else {
			if (dateNowSplitInt[2] >= 20) {
				dateNowSplitInt[1] += 1;
				String dateNowSplitIntZero = String.format("%02d", dateNowSplitInt[1]);
				System.out.println("現在日付： " + dateNowSplitInt[0] + "年 " + (dateNowSplitInt[1] - 1) + "月 "
						+ dateNowSplitInt[2] + "日");
				System.out.println("年月度： " + dateNowSplitInt[0] + " 年度 " + dateNowSplitIntZero + " 月度");
			}

			// 5. 12月ではない月で20日を越えていない場合、年と月をそのままに返す。
			else {
				String dateNowSplitIntZero = String.format("%02d", dateNowSplitInt[1]);
				System.out.println(
						"現在日付： " + dateNowSplitInt[0] + "年 " + dateNowSplitInt[1] + "月 " + dateNowSplitInt[2] + "日");
				System.out.println("年月度： " + dateNowSplitInt[0] + " 年度 " + dateNowSplitIntZero + " 月度");
			}
		}
	}
}