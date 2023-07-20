package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.TweetAttach;

public class TweetAttaches {
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MASTER_TWITTER";
	static final String password = "1q2w3e4r";

	// 현재 인덱스 값 얻어오기
	public static int findLastIdx() {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);
			String sql1 = "SELECT VALUE FROM TWEET_ATTACHES_IDX";
			PreparedStatement ps = conn.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			int lastIdx = rs.getInt("value");
			
			String sql2 = "UPDATE TWEET_ATTACHES_IDX SET VALUE=VALUE+1";

			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.executeUpdate();
			
			conn.close();

			return lastIdx;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 데이터 등록
	public static int create(int idx, String uri, String tweetCode) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);

			// 2. 요청객체 준비
			String sql = "INSERT INTO TWEET_ATTACHES VALUES(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idx);
			ps.setString(2, uri);
			ps.setString(3, tweetCode);

			int r = ps.executeUpdate();
			conn.close();

			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 특정 트윗코드의 첨부물들 가지고 오기
	public static List<TweetAttach> findByTweetCode(String tweetCode) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);
			// 2. 요청객체 준비
			String sql = "SELECT * FROM TWEET_ATTACHES WHERE TWEET_CODE=? ORDER BY IDX";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, tweetCode);
			ResultSet rs = ps.executeQuery();
			List<TweetAttach> list = new ArrayList<>();
			while (rs.next()) {
				TweetAttach one = new TweetAttach();
				one.setIdx(rs.getInt("idx"));
				one.setTweetCode(rs.getString("tweet_code"));
				one.setUri(rs.getString("uri"));
				// 추출할수 있는 방법이 생겼을 거임.
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
