package exA.a02;

import java.time.LocalDate;

public class Sample {
	public static void main(String[] args) {

		// 1.今日の日付を年の部分と月の部分に分ける
		LocalDate dateNow = LocalDate.now();
		String[] dateNowSplit = dateNow.toString().split("-");
		int[] dateNowSplitInt = new int[dateNowSplit.length];
		for (int i = 0; i < dateNowSplit.length; i++) {
			dateNowSplitInt[i] = Integer.parseInt(dateNowSplit[i]);
		}

		// 2-1. 12月20日を越えた場合。
		if (dateNowSplitInt[1] == 12) {
			if (dateNowSplitInt[2] >= 20) {
				dateNowSplitInt[0] += 1;
				dateNowSplitInt[1] = 1;
							}
			
		}

		// 2-2. 20日を越えた場合。
		else {
			if (dateNowSplitInt[2] >= 20) {
				dateNowSplitInt[1] += 1;
			}
		}
		
		//3. コンソールに出力
		String dateNowSplitIntZero = String.format("%02d", dateNowSplitInt[1]);
		System.out.println("現在日付： " + dateNowSplitInt[0] + "年 " + (dateNowSplitInt[1] - 1) + "月 "
				+ dateNowSplitInt[2] + "日");
		System.out.println("年月度： " + dateNowSplitInt[0] + " 年度 " + dateNowSplitIntZero + " 月度");
	}
}