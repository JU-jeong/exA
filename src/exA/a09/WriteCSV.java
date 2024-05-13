package exA.a09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * CSV ファイル「商品一覧.csv」を読み込み、画面で入力した販売数を元に「販売数」と「売上金額」の２項目を追加した「売上.csv」を作成するプログラムです。
 * @author ju_jeongseok
 * @param unitPrice ユーザーが入力した販売数量と単価を掛けて売上高を計算する際に使用する変数です。
 * @param count 現在処理中のレコードのフィールド数を追跡するために使用されます。
 * @param headOutCount ヘッダーフィールドが現れる回数を追跡するために使用されます。 ヘッダーが複数回現れる場合、特定の条件でレコードの処理をスキップするために使用されます。
 * @param unitCount 現在処理中のフィールド数を追跡するために使用されます。 特定の条件に応じてフィールドをスキップしたり追加の作業を実行したりします。
 * @param filePath ファイル経路
 * @param file CSVファイルを読み込んで処理するために、そのファイルを指すFileオブジェクトを生成します。
 */
public class WriteCSV{
	private static int unitPrice = 0;
	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * 1. mainメソッドは、複数のカウンタ(count、headOutCount、unitCount)を初期化し、CSVファイルのファイル経路を定義します。
		 * 次に、ファイルのFileオブジェクトとファイルを読み取るためのScannerオブジェクトを作成します。
		 */
		int count = 0;
		int headOutCount = 0;
		int unitCount = 0;
		String filePath = "C:\\Users\\ju_jeongseok\\Downloads\\pleiades-2021-12-java-win-64bit_20220106\\pleiades\\workspace\\exA\\src\\exA\\a09\\商品一覧.csv"; 
		File file = new File(filePath);
		/*
		 * 2. Scannerオブジェクトはファイルの最初の行を読み、コンマに基づいてフィールドを分割し、 これらのフィールドをheadFieldsにコピーします。
		 */
		try (Scanner scanner = new Scanner(file)) {
			String line = scanner.nextLine();
			String[] fields = line.split(",", -1);
			String[] headFields = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				headFields[i] = fields[i];
			}
			/*
			 * 3. whileループは、ファイルの残りの行を繰り返します。 各行は、最初の行と同じ方法でフィールドに分割されます。
			 */
			while (scanner.hasNextLine()) {
				String line2 = scanner.nextLine();
				String[] fields2 = line2.split(",", -1);
				/*
				 * 4. for ループは、各行のフィールドを繰り返します。 フィールドが空の場合、countとunit
				 * Countを増加させ、フィールドが設定されていないというメッセージを出力します。 フィールドが空でない場合は、カウントが3と同じか確認します。
				 * もし正しければ、残りのループをスキップします。 そうでない場合は、対応するヘッダー フィールドとフィールド値を出力します。
				 * フィールドが空でない場合は、unitCountが3と同じか確認します。 もし正しければ、フィールド値を整数で解析し、unitPriceに割り当てます。
				 */
				for (String field : fields2) {
					if (field.trim().isEmpty()) {
						if (unitCount == 2) {
							headOutCount += 4;
							break;
						}
						System.out.println(headFields[count] + ": 未設定\t");
						count++;
						unitCount++;
					} else {
						if (count == 3) {
							continue;
						}
						System.out.print(headFields[count] + ": ");
						String fieldComma = field.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
						System.out.print(fieldComma + "\t");
						unitCount++;
						if (unitCount == 3) {
							unitPrice = Integer.parseInt(field);
						}
						System.out.println();
						count++;
					}
				}
				/*
				 * 5. ファイルの各行を処理した後、コードは販売量を入力するようにというメッセージをユーザーに表示します。 入力された数量が 1 - 100
				 * の範囲内にあるまで表示し続けます。
				 */
				int salesQuantity = -1;
				while (true) {
					if (headOutCount == 4) {
						break;
					}
					try {
						System.out.print("販売数: ");
						Scanner scanner2 = new Scanner(System.in);
						String salesQuantityGenuine = scanner2.nextLine();
						salesQuantity = Integer.parseInt(salesQuantityGenuine);
						if (salesQuantity > 0 && salesQuantity <= 100) {
							break;
						} else {
							System.out.println("入力できる販売数は 1～100 の範囲だけです.");
						}
					} catch (NumberFormatException e) {
						System.out.println("正しい形の入力ではありません.");
					}
				}
				/*
				 * 6. 有効な販売量が入力されると、コードは販売量とunitPriceを掛け合わせて販売額を計算し、 販売額をコンマで形式化して出力します。
				 */
				if (salesQuantity >= 0) {
					// int unitPrice = Integer.parseInt(fields[2].trim().replaceAll("[^\\d]", "0"));
					int salesAmount = salesQuantity * unitPrice;
					String salesComma = Integer.toString(salesAmount).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
					// System.out.print(salesComma + "\t");
					System.out.println("売上金額: " + salesComma);
				}
				/*
				 * 7. count、unitCount、headOutCountを0にリセットし、空の行を追加し、ファイルの次の行に対してプロセスを繰り返します。
				 */
				count = 0;
				unitCount = 0;
				headOutCount = 0;
				System.out.println();
			}
		}
		System.out.println(file.getName() + "を出力しました。");
	}
}
