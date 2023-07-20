package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.Tweet;
import data.User;

public class Tweets {
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MASTER_TWITTER";
	static final String password = "1q2w3e4r";

	// 1. 데이터 등록하기
	public static int create(String code, String writer, String body) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);

			// 2. 요청객체 준비
			String sql = "INSERT INTO TWEETS(CODE, WRITER, BODY) VALUES(?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			ps.setString(2, writer);
			ps.setString(3, body);

			int r = ps.executeUpdate();
			conn.close();

			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 2. 최신 글 5개 찾기
	public static List<Tweet> findLatest(int limit) {
		try {  
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);
			// 2. 요청객체 준비
			String sql = "SELECT * FROM (SELECT * FROM TWEETS JOIN USERS ON USERS.ID = TWEETS.WRITER ORDER BY WRITED DESC) WHERE ROWNUM <=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, limit);
			// 3. 요청후 응답 처리
			ResultSet rs = ps.executeQuery();
			List<Tweet> list = new ArrayList<>();
			while (rs.next()) {
				Tweet one = new Tweet();
			
					one.setCode(rs.getString("code"));
					one.setWriter(rs.getString("writer"));
					one.setBody(rs.getString("body"));
					one.setWrited(rs.getDate("writed"));
					// 추출할수 있는 방법이 생겼을 거임.
					one.setWriterNick(rs.getString("nick"));
					one.setWriterImg(rs.getString("img"));
					
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
