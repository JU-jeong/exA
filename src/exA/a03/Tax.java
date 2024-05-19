package exA.a03;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * ら入力された金額と税率から税込金額と税抜金額を計算し、消費税額と合わせて表示するクラス
 * 
 * @author ju_jeongseok
 * @param priceAndTaxStr  金額
 * @param priceAndTaxInt  金額
 * @param directTaxNumber 税額計算のためのint -> doubleえの変換
 * @param df              形式出力のための変数
 * @param taxPrint        税率
 * @param taxInclude      税込金額
 * @param includedTax     消費税
 * @param taxExclude      税抜金額
 * @param excludedTax     消費税額
 */
public class Tax {
	public static void main(String[] args) {
		/**
		 * 1. 入力された金額と税率を空白を基準にわけるための配列宣言
		 * 
		 * 
		 * 
		 * @param priceAndTaxInt 金額
		 */
		String[] priceAndTaxStr;
		int[] priceAndTaxInt = new int[2];
		// 1-5. 金額の範囲設定と税率との分離のためのloop文の作成
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				priceAndTaxStr = sc.nextLine().split(" ");
				priceAndTaxInt[0] = Integer.parseInt(priceAndTaxStr[0]);
				if (priceAndTaxInt[0] >= 0 && priceAndTaxInt[0] <= 99999) {
					sc.close();
					break;
				} else {
					System.out.println("正しい範囲の値を入力してください。");
				}
			} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
				System.out.println("正しい範囲の値を入力してください。");
			}
		}

		priceAndTaxInt[1] = Integer.parseInt(priceAndTaxStr[1]);
		double directTaxNumber = priceAndTaxInt[1] * 0.01;
		DecimalFormat df = new DecimalFormat("###,###");
		int taxPrint = priceAndTaxInt[1];
		String taxInclude = df.format(Math.floor(priceAndTaxInt[0] * (1 + directTaxNumber)));
		String includedTax = df.format((priceAndTaxInt[0] * directTaxNumber));
		String taxExclude = df.format(Math.round(priceAndTaxInt[0] / (1 + directTaxNumber)));
		String excludedTax = df.format((priceAndTaxInt[0] / (1 + directTaxNumber)) * directTaxNumber);

		// 6.出力
		System.out.println("税率: " + taxPrint + "%");
		System.out.println("税込金額: " + taxInclude + "円" + "   " + "消費税額: " + includedTax + "円");
		System.out.println("税抜金額: " + taxExclude + "円" + "   " + "消費税額: " + excludedTax + "円");
	}
}