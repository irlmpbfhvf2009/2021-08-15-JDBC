package test;

import java.util.Scanner;

public class Demo {
	public static void main(String[] args) {
		// 創建Scanner對象，用於接收鍵盤輸入的數據
        Scanner sca = new Scanner(System.in);
        // 提示
        System.out.println("使用next方法接收用户的输入:");
            // 使用Next方法獲取用户輸入的字符串
            String str = sca.next();
            // 將用戶輸入的字符串打印
            System.out.println("用戶輸入的是:" + str);
            // 關閉Scanner，防止資源占用
            sca.close();
        }
}



