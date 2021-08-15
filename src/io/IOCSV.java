package io;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOCSV {

	public void runIO() {    
		try (InputStreamReader isr = new InputStreamReader( // 檔案讀取路徑  
				new FileInputStream("D:\\PRO1\\TainanCity.CSV"),"UTF-8"); 
				BufferedReader br = new BufferedReader(isr); 
				BufferedWriter bw = new BufferedWriter(  // 檔案輸出路徑
						new FileWriter("D:\\PRO1\\CSV_output.CSV"));) { 
			String line = null;
			System.out.println("=======================資料讀取=======================");
			while ((line = br.readLine()) != null) {
				/** 輸出Console **/
				System.out.println(line);
				
				/** 寫出 **/
				bw.write(line,0,line.length()); // 寫一行資料到新檔案中
				bw.newLine(); // 換行
			}
			System.out.println("\nIO Success!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
