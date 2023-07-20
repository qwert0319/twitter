package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import data.User;

/*
 * 
 */
public class Users {
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MASTER_TWITTER";
	static final String password = "1q2w3e4r";

	// 데이터 등록시 사용할 function
	public static int create(String id, String pass, String nick) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);

			// 2. 요청객체 준비
			String sql = "INSERT INTO USERS(ID, PASS, NICK) VALUES(?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pass);
			ps.setString(3, nick);

			int r = ps.executeUpdate();
			conn.close();

			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 특정ID를 가진 데이터를 찾고자할 때 사용할 function
	public static User findById(String id) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);
			// 2. 요청객체 준비
			String sql = "SELECT * FROM USERS WHERE ID=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			User one = null;
			if (rs.next()) {
				one = new User();
				one.setId(id);
				one.setPass(rs.getString("pass"));
				one.setNick(rs.getString("nick"));
				one.setProfile(rs.getString("profile"));
				one.setJoined(rs.getDate("joined"));
				one.setImg(rs.getString("img"));
			}
			conn.close();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 특정ID의 컬럼값을 변경하는 function 만들기
	public static int updateOne(String id, String nick, String profile, String img) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);

			// 2. 요청객체 준비
			String sql = "UPDATE USERS SET NICK=?, PROFILE=?, IMG=? WHERE ID=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nick);
			ps.setString(2, profile);
			ps.setString(3, img);
			ps.setString(4, id);

			int r = ps.executeUpdate(); // 1이면 처리, 0이면 데이터를 못찾은거고
			conn.close();

			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // 쿼리가 잘못됬다거나 ... 등등
		}

	}

	// 특정 키워드에 매치되는 유저 찾는 function
	public static List<User> findByKeyWord(String keyword) {
		// 특정ID를 가진 데이터를 찾고자할 때 사용할 function
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);
			// 2. 요청객체 준비
			String sql = "SELECT USERS.*, NVL(FOLLOWERS.CNT, 0) AS CNT FROM USERS LEFT JOIN FOLLOWERS ON USERS.ID = FOLLOWERS.TARGET ";
			sql += " WHERE ID LIKE '%'||?|| '%' OR NICK LIKE ? OR PROFILE LIKE ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, keyword);
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			// 3. 요청후 응답 처리
			ResultSet rs = ps.executeQuery();
			List<User> list = new ArrayList<>();

			while (rs.next()) {
				User one = new User();
				one.setId(rs.getString("id"));
				one.setPass(rs.getString("pass"));
				one.setNick(rs.getString("nick"));
				one.setProfile(rs.getString("profile"));
				one.setJoined(rs.getDate("joined"));
				one.setImg(rs.getString("img"));
				one.setFollwerCnt(rs.getInt("cnt"));
				list.add(one);
			}

			conn.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
