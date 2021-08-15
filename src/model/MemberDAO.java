package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDAO {

	private Connection conn;

	// 創連線
	public void createConn() throws Exception {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String urlStr = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;"
				+ "databaseName=PetHospital;user=sa;password=123123123";

		this.conn = DriverManager.getConnection(urlStr);
		boolean status = !conn.isClosed();
		if (status) {
			System.out.println("open connection......");
		}
	}

	// 關連線
	public void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
			System.out.println("close connection......");
		}
	}

	// 查詢資料
	public void query() throws SQLException {
		String sqlStr = "select * from TainanCity";
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sqlStr);
		while (rs.next()) {
			System.out.println("ID:" + rs.getInt(1) + " " + "醫院:" + rs.getString(2) + "\t" + "負責人:" + rs.getString(3)
					+ "\t" + "地址:" + rs.getString(4) + " \t" + "電話:" + rs.getString(5));
		}
		rs.close();
		state.close();
	}

	// 透過地區查詢資料
	public Member findByAddress(String address) throws SQLException {

		String sqlStr = "  SELECT * FROM TainanCity WHERE ADDRESS LIKE  '%" + address + "%'  ";
		PreparedStatement preState = conn.prepareStatement(sqlStr);
		ResultSet rs = preState.executeQuery();
		Member m = null;
		while (rs.next()) {
			m = new Member();
			m.setMemberId(rs.getInt("id")); // 用資料庫欄位名稱拿到資料
			m.setMemberHospitalname(rs.getNString("hospitalname"));
			m.setMemberPrincipal(rs.getNString("principal"));
			m.setMemberAddress(rs.getNString("address"));
			m.setMemberPhone(rs.getString("phone"));
			System.out.println("ID:" + m.getMemberId() + " 醫院:" + m.getMemberHospitalname() + " \t負責人:" + rs.getString(3)
					+ "\t地址:" + m.getMemberAddress() + "\t電話:" + m.getMemberPhone());
		}
		rs.close();
		preState.close();
		return m;
	}

	// 新增資料方法
	public void addMember(Member m) throws SQLException {
		String sqlStr = "Insert into TainanCity"
		+ "(id,hospitalname,principal,address,phone)" + " values(?,?,?,?,?)";

		PreparedStatement preState = conn.prepareStatement(sqlStr);
		preState.setInt(1, m.getMemberId());
		preState.setNString(2, m.getMemberHospitalname());
		preState.setNString(3, m.getMemberPrincipal());
		preState.setNString(4, m.getMemberAddress());
		preState.setNString(5, m.getMemberPhone());
		preState.executeUpdate();
		preState.close();
	}
	
	// 修改資料(知道ID 根據線索改電話)
	public void updateByIdAndName(Member m) throws SQLException {
		String sqlStr = "Update TainanCity Set phone = ?" + " where id = ?";
		PreparedStatement preState = conn.prepareStatement(sqlStr);
		preState.setString(1, m.getMemberPhone());
		preState.setInt(2, m.getMemberId());
		preState.execute();
		preState.close();

	}

	// 刪除資料(ID)
	public void deleteMemberById(int id) throws SQLException {
		String sqlStr = "Delete from TainanCity where id = ?";
		PreparedStatement preState = conn.prepareStatement(sqlStr);
		preState.setInt(1, id);
		preState.execute();
		preState.close();
		System.out.println("ID:"+id+" 資料刪除");
	}

	// CSV寫入資料庫
	public void CSVIntoDatabase() throws SQLException { // try-with-resources 自動關閉流
		try (BufferedReader br = new BufferedReader(new InputStreamReader( 
				new FileInputStream("D:\\Pro1\\TainanCity.CSV"), "UTF-8"));) { // 檔案讀取路徑

			String data;
			String sqlStrInsertInto = "insert into TainanCity VALUES (?, ?, ?, ?, ?)";
			String sqlStrDelete = "delete from TainanCity";
			PreparedStatement preState = conn.prepareStatement(sqlStrInsertInto);
			PreparedStatement preStateDelete = conn.prepareStatement(sqlStrDelete);
			preStateDelete.execute();// 
			preStateDelete.close();
			boolean firstLine = true;
			while ((data = br.readLine()) != null) { // 讀出CSV檔的值
				// 將資料內的文字以,號分隔並去除逗號間的空格並儲存在result陣列
				String result[] = data.trim().split(","); 
				if (firstLine) { // 跳過資料表名稱
					firstLine = false;
					continue;
				}
				for (int i = 0; i < result.length; i++) {
					// 當i=0時preState.setString(1, result[0]);
					if (i == 0) {
						int resultId = Integer.parseInt(result[0]);
						preState.setInt(1, resultId);
						System.out.println("第 " + result[0] + " 筆資料 新增成功");
					} else {
						preState.setString(i + 1, result[i]);
					}
				}
				preState.addBatch();// addBatch()不得放在for迴圈內否則會造成錯誤
			}
			preState.executeBatch();// 批次寫入資料庫
			preState.close();
			System.out.println("\n寫入完成......");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
