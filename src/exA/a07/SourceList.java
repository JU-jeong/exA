package exA.a07;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 本課題で作成したJavaソースファイルの一覧を絶対パスで表示するクラス。
 * @author ju_jeongseok
 * @param br ファイル パスを入力するための入力オブジェクト
 * @param directoryPath ファイル経路
 * @param searchDirectory そのディレクトリ内のファイルまたはフォルダーを読み取るために作成されたファイルオブジェクト
 */
public class SourceList {
	public static void main(String[] args) throws IOException {
		// 1. ディレクトリを設定して探索し、File objectに入れる。
		System.out.println("ディレクトリ経路を入力してください。");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		String directoryPath = br.readLine();
		//String directoryPath = "C:\\Users\\ju_jeongseok\\Downloads\\pleiades-2021-12-java-win-64bit_20220106\\pleiades\\workspace\\exA";
		File searchDirectory = new File(directoryPath);
		// 2. ディレクトリにファイル,又はフォルダーが存在する場合中身を配列に入れてファイルだった場合整列
		try {
			if (searchDirectory.exists() && searchDirectory.isDirectory()) {
				File[] searchDirectoryFiles = searchDirectory.listFiles();
				if (searchDirectoryFiles != null) {
					Arrays.sort(searchDirectoryFiles);
					/*
					 * 3-1. searchDirectoryFilesの現在listがfileだった場合 : 頭文出力後、ファイルの目録を探索しながらjava
					 * ファイルだけを出力。
					 */
					System.out.println("Java ソースファイル一覧");
					for (File file : searchDirectoryFiles) {
						if (file.isFile() && file.getName().endsWith(".java")) {
							System.out.println(file.getAbsolutePath());
							/*
							 * 3-2. searchDirectoryFilesの現在listがフォルダーだった場合 :
							 * また同じく、新しいフォルダーの中で探索作業を繰り返す。searchDirectoryAgainメソッド使用。
							 */
						} else if (file.isDirectory()) {
							searchDirectoryAgain(file);
						}
					}
				} else {
					// 3-3. 最深部のディレクトリが空の場合
					System.out.print("ディレクトリが空っぽです。");
				}
			} else {
				// 3-4. ディレクトリが存在しない場合
				System.out.println("存在しない経路です。");
			}
			// 3-5. 権限のないファイルを読もうとすると発生する例外です。
		} catch (SecurityException e) {
			System.out.print("権限がありまぜん。");
		}
	}
	/**
	* ディレクトリおよびそのサブディレクトリ内のJavaソースファイルを再帰的に検索します。(mainメソッドの14-33までの繰り返し)
	* @param directory 検索するディレクトリ
	*/
	public static void searchDirectoryAgain(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			Arrays.sort(files);
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".java")) {
					System.out.println(file.getAbsolutePath());
				} else if (file.isDirectory()) {
					searchDirectoryAgain(file);
				}
			}
		}
	}
}
