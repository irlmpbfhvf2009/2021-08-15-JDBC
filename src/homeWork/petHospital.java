package homeWork;
import java.sql.SQLException;
import io.IOCSV;
import model.Member;
import model.MemberDAO;
import model.MemberDAOFactory;
import java.util.Scanner;
public class petHospital {
	static Scanner sca = new Scanner(System.in); // 創建Scanner對象，用於接收鍵盤輸入的數據
	public static void main(String[] args) {
		MemberDAO mDao = MemberDAOFactory.getMemberDAO();
		IOCSV ioCSV = new IOCSV();
		boolean exit = false; // 判斷迴圈結束
		try {
			mDao.createConn(); // 建立連線
			long startTime=System.currentTimeMillis();   //獲取開始時間  	
			System.out.println("\n=====輸入文字使用功能=====\nIO:CSV檔案讀取.寫出\nDATA:資料輸入資料庫\n"
					+ "\n臺南市寵物醫院\nALL:總查詢 A:查詢 B:新增 C:修改(電話) D:刪除 E:退出");
			// 使用nextLine()方法獲取用戶輸入的字符串
			String str = sca.nextLine();
			while (exit != true) { // exit=false 時進入迴圈
				switch (str) {
				case "IO": // IO
					ioCSV.runIO();
					System.out.println("\n=====輸入文字使用功能=====\nIO:CSV檔案讀取.寫出\nDATA:資料輸入資料庫\n"
							+ "\n臺南市寵物醫院\nALL:總查詢 A:查詢 B:新增 C:修改(電話) D:刪除 E:退出");
					str = sca.nextLine();
					continue;
				case "DATA": // 輸入資料庫
					mDao.CSVIntoDatabase();
					System.out.println("\n=====輸入文字使用功能=====\nIO:CSV檔案讀取.寫出\nDATA:資料輸入資料庫\n"
							+ "\n臺南市寵物醫院\nALL:總查詢 A:查詢 B:新增 C:修改(電話) D:刪除 E:退出");
					str = sca.nextLine();
					continue;
				// 總查詢
				case "ALL":
					mDao.query();
					System.out.println("臺南市寵物醫院  ALL:總查詢 A:查詢 B:新增 C:修改 D:刪除 E:退出");
					str = sca.nextLine();
					continue;
				// 查詢
				case "A":
					System.out.println("\n==========輸入地區或路名查詢資料==========\n");
					System.out.println("---東區---新營區---永康區---仁德區---麻豆區---歸仁區---");
					System.out.println("---西區---安平區---安南區---佳里區---新化區---新市區---");
					System.out.println("---南區---善化區---白河區---下營區---學甲區---將軍區---");
					System.out.println("---北區---楠西區---七股區---鹽水區---中西區---西港區---");
					String queryAddress = sca.nextLine();
					mDao.findByAddress(queryAddress);
					System.out.println("臺南市寵物醫院  ALL:總查詢 A:查詢 B:新增 C:修改 D:刪除 E:退出");
					str = sca.nextLine();
					continue;
				// 新增
				case "B":
					Member m1 = new Member();
					System.out.println("輸入ID(不重複)");
					int intoId = sca.nextInt();
					System.out.println("輸入醫院名稱");
					String z = sca.nextLine();
					String intoHospitalName = sca.nextLine();
					System.out.println("輸入負責人");
					String intoPrincipal = sca.nextLine();
					System.out.println("輸入住址");
					String intoAddress = sca.nextLine();
					System.out.println("輸入電話");
					String intoPhone = sca.nextLine();
					
					m1.setMemberId(intoId);
					m1.setMemberHospitalname(intoHospitalName);
					m1.setMemberPrincipal(intoPrincipal);
					m1.setMemberAddress(intoAddress);
					m1.setMemberPhone(intoPhone);
					mDao.addMember(m1);
					System.out.println("新增成功\nID:"+m1.getMemberId()+"\t醫院:"+m1.getMemberHospitalname()+"\t負責人:"+m1.getMemberPrincipal()
					+"\t地址:"+m1.getMemberAddress()+"\t電話:"+m1.getMemberPhone());
					System.out.println("臺南市寵物醫院  ALL:總查詢 A:查詢 B:新增 C:修改 D:刪除 E:退出");
					str = sca.nextLine();
					continue;
				// 修改
				case "C":
					Member m2 = new Member();
					System.out.println("輸入新的電話");
					String updatePhone = sca.nextLine();
					System.out.println("指定ID修改");
					int updateId = sca.nextInt();
					m2.setMemberPhone(updatePhone);
					m2.setMemberId(updateId);
					mDao.updateByIdAndName(m2);
					System.out.println("ID:"+m2.getMemberId()+" 電話:"+m2.getMemberPhone()+" 修改完成");
					System.out.println("臺南市寵物醫院  ALL:總查詢 A:查詢 B:新增 C:修改 D:刪除 E:退出");
					str = sca.nextLine();
					continue;
				// 刪除
				case "D":
					System.out.println("輸入ID刪除資料");
					int deleteId = sca.nextInt();
					mDao.deleteMemberById(deleteId);
					System.out.println("臺南市寵物醫院  ALL:總查詢 A:查詢 B:新增 C:修改 D:刪除 E:退出");
					str = sca.nextLine();
					continue;
				// 離開
				case "E":
					exit = true; // exit=true 時退出迴圈
					long endTime=System.currentTimeMillis(); // 獲取結束時間
					System.out.println("Exit");
					System.out.println("程式執行時間: "+((endTime-startTime)/1000)+"秒"); 
					break;
				// 輸入錯誤
				default:
					str = sca.nextLine();
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				mDao.closeConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}