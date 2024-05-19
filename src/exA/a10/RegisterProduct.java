package exA.a10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * 画面で入力した件数分、連続して商品情報(商品名、単価)を入力し、入力後に一覧表示しするクラス
 * 
 * @author ju_jeongseok
 * @param count 入力された回数だけループを繰り返すために使用される変数
 * @param methodCount 最初の1回のメソッドリターン値をfalseにするための操作
 * @param productCode csvファイルの一行に入力される情報の商品コード
 * @param duplicateExamine 商品名を比較する元素を保存する配列
 * @param productName csvファイルの一行に入力される情報の商品名
 * @param priceStr csvファイルの一行に入力される情報の単価、文字列
 * @param price csvファイルの一行に入力される情報の単価、整数型
 * @param priceFormat 単価の出力形式を指定
 * @param filePath ファイル パスの指定
 * @param file 指定パスのファイル
 * @param csvWriter ファイルを作成するためのライター
 * @param bf ファイルを読み込むためのリーダー
 */

public class RegisterProduct {
	// 1. メインメソッドと商品重複確認メソッドの両方で使用される変数の宣言
	private static int count = 0;
	private static int methodCount = 0;
	private static int productCode = 1;
	private static String[] duplicateExamine;

	public static void main(String[] args) throws IOException {
		// 2. 商品情報入力部に使われる変数の宣言と出力形式のためのフォーマット設定
		String productName = null;
		String priceStr = null;
		int price = 0;
		DecimalFormat priceFormat = new DecimalFormat("###.###");
		
		// 3. ファイル パスの指定と指定パスのファイル作成
		String filePath = "C:\\Users\\ju_jeongseok\\Downloads\\pleiades-2021-12-java-win-64bit_20220106\\pleiades\\workspace\\exA2\\src\\exA\\a10\\product.csv";
		File file = new File(filePath);
		
		// 4.ファイル共通部ヘッダ作成
		BufferedWriter csvWriter = new BufferedWriter(new FileWriter(filePath));
		csvWriter.write("商品一覧");
		csvWriter.write("\n");
		csvWriter.write("商品コード 商品名 単価");
		csvWriter.write("\n");

		// 5.入力商品の回数指定
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("入力する商品の数を入力してください。");
		String countProto = bf.readLine();
		count = Integer.parseInt(countProto);
		duplicateExamine = new String[count];
		
		// 6.指定した回数だけ繰り返して商品の情報を登録
		while (productCode <= count) {
			System.out.println("商品コード： " + String.format("%03d", productCode));
			System.out.println("商品名： ");

			// 6-1. duplicateExamineメソッドを利用して商品名を入力すると、同一商品名を持つ他のコードの商品が存在するか探索と空白入力の処理
			while (true) {
				productName = bf.readLine();
				if(productName.trim().length() == 0) {
					System.out.println("値を入力してください。");
					System.out.println();
					continue;
				}
				if (duplicateExamine(productName)) {
					System.out.println("同名の商品の登録は不可です。");
				} else {
					break;
				}
			}

			// 6-2. 単価入力後、指定範囲の値かどうかを判別そして空白、文字列入力に対する処理
			System.out.println("単価： ");
			while (true) {
				try {
					priceStr = bf.readLine();
					price = Integer.parseInt(priceStr);
					if (price > 0 && price <= 999999) {
						break;
					}
					System.out.println("範囲以内の値を入力してください。");
					System.out.println();
					continue;
				}catch(NumberFormatException e) {
					System.out.println("正しい形の値を入力してください。");
					System.out.println();
					continue;
				}
				
			}
			/* 6-3.入力して収集した値を出力形態指定後、
			productクラスの生成者に引数で入力して生成されたオブジェクトを同様にproductクラスのtoStringメソッドを通じて
			csvファイルに入力して改行、以後の作業を繰り返す。*/
			Product product = new Product(String.format("%03d", productCode), productName, priceFormat.format(price));
			csvWriter.write(product + "\n");
			productCode++;
		}
		csvWriter.close();

		System.out.println();

		// 7.指定経路に作成されたcsvファイルの情報を読み込む後コンマを外して出力
		BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
		String line;
		while ((line = csvReader.readLine()) != null) {
			String[] data = (line + ",,,").split(",", -1);
			data = java.util.Arrays.copyOf(data, 3);
			for (int i = 0; i < data.length; i++) {
				System.out.print(data[i].replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",") + " ");
			}
			System.out.println();
		}
		csvReader.close();
	}
	

	//商品名 重複確認のためのメソッド
	
	/**
	 * 重複確認のためのメソッド
	 * @param productName
	 * 	名前が比較される値
	 * @return true
	 * 	二番目の呼び出しからは、前で保存された商品の名前と比較し、同一値が存在	
	 * @return false
	 * 	最初の呼び出しは比較する値が存在しない
	 * @throws IOException
	 */
	static boolean duplicateExamine(String productName) throws IOException {
		// 1.入力値を配列の一番外側に保存
		duplicateExamine[productCode - 1] = productName;

		// 2.最初の呼び出しは比較する値が存在しないため、自動的にfalseを返しループを脱出
		if (methodCount == 0) {
			methodCount++;
			return false;
		}

		// 3.二番目の呼び出しからは、前で保存された商品の名前と比較し、同一値が存在しない場合はループを脱出、存在する場合はループを繰り返して入力を続けるようにする。
		for (int i = 0; i < productCode - 1; i++) {
			if (duplicateExamine[i].equals(productName)) {
				return true;
			}
		}
		return false;
	}
}
