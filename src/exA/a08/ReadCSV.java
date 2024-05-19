package exA.a08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
/**
 * CSVファイルを読み込み、その内容を出力するクラス
 * @author ju_jeongseok
 * @param br BufferedReaderオブジェクト
 * @param fileName 読み込むCSVファイルのパス
 * @param file CSVファイルのFileオブジェクト
 * @param line CSVファイルから読み込まれた1行のデータ
 * @param data CSVファイルから分割された1行のデータ配列
 */
public class ReadCSV {
	public static void main(String[] args) throws IOException {
		BufferedReader br = null;
		String fileName = "C:\\Users\\microsoft\\Downloads\\exA\\exA\\src\\exA\\a08\\商品一覧.csv";
		try {
			File file = new File(fileName);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			// 1.
			/*例外処理のためのtry catch文及びExceptionを宣言。file　objectを作ってdirectoryのファイルのbyteを
			1-1. 最初にFileInputStreamを通じて読み込んだ後、
			1-2. InputStreamReaderをつうじてencoding後文字列に変換、
			1-3. 最後にBufferedReaderで囲んで効率よく処理とエーラを防ぐ。
			 */
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = (line + ",,,").split(",", -1);
				data = java.util.Arrays.copyOf(data, 3);
				// 2. 
				/*
				ファイルを1列づつ読み込むためにString lineを生成。
				commaを基準に文字列をわけて、空白の連続出力（未設定中腹現状）を防ぐために
				１列に三つの分けられた文字列をdataとして出力制限。
				*/
				for (int i = 0; i < data.length; i++) {
					if (data[i].isEmpty() || data[i] == null) {
						System.out.print(data[i].replaceAll("", "未設定").replaceAll("null", "未設定") + " ");
					} else {
						System.out.print(data[i].replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",") + " ");
					}
				}
				System.out.println();
			}
			// 3. 'data'配列で次のstreamが空白又はnullだった場合、"未設定"を出力するように設定。値が存在する場合、commaをつけて出力。
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			br.close();
		}
		// 4. 例外処理後stream閉じる
	}
}