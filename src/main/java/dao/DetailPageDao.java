package dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.CommentDetailDto;
import dto.CommentDto;
import dto.DetailPageDto;
import dto.MakingMovieDto;
import dto.MakingPersonDto;
import dto.MoreComWriteDto;
import dto.MoreCommentDto;
import dto.MovieEvaStarDto;
import dto.ProfileDto;
import dto.RatedRDto;
import dto.RecentListDto;
import dto.ReplyDto;
import dto.RightUplDto;
import dto.ScoreDescDto;
import dto.StaffDto;
import dto.StarByMemberDto;
import dto.WriteByMemberCommentDto;

public class DetailPageDao {
	Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "test";
		String pw = "1234";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}
	
	// 헤더 프로필 사진 가져오기
	public String getProfileImg(int mm_idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String profile_img = "";
		try {
			String sql = "SELECT profile_img FROM member WHERE mm_idx = ?";
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				profile_img = rs.getString("profile_img");
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return profile_img;
	}
 	
	// 영화 상세페이지 (메인 페이지에서 영화 클릭했을 때)
	public ArrayList<DetailPageDto> showList(int movie_idx) {
		ArrayList<DetailPageDto> list = new ArrayList<DetailPageDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT  "
					+ "    m.movie_idx AS \"영화ID\",  "
					+ "    m.movie_background AS \"영화배경화면\",  "
					+ "    m.movie_title AS \"영화제목\",  "
					+ "    m.release_year AS \"영화개봉년도\", "
					+ "    g.genre_name AS \"장르\",  "
					+ "    m.movie_time AS \"영화시간\",  "
					+ "    a.age_name AS \"연령\",  "
					+ "    m.poster AS \"영화포스터\",  "
					+ "    m.movie_description AS \"영화설명\",  "
					+ "    ROUND(AVG(s.score), 1) AS \"별점\" "
					+ "FROM movie m "
					+ "INNER JOIN genre g ON m.genre_idx = g.genre_idx "
					+ "INNER JOIN age a ON m.age_idx = a.age_idx "
					+ "INNER JOIN star s ON m.movie_idx = s.movie_idx "
					+ "WHERE m.movie_idx = ? "
					+ "GROUP BY m.movie_idx, m.movie_background, m.movie_title, m.release_year, g.genre_name, m.movie_time, a.age_name, m.poster, m.movie_description";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				movie_idx = rs.getInt("영화ID");
				String movie_background = rs.getString("영화배경화면");
				String movie_title = rs.getString("영화제목");
				String release_year = rs.getString("영화개봉년도");
				String genre_name = rs.getString("장르");
				String movie_time = rs.getString("영화시간");
				String age_name = rs.getString("연령");
				String poster = rs.getString("영화포스터");
				String movie_description = rs.getString("영화설명");
				int score = rs.getInt("별점");
				
				DetailPageDto dto = new DetailPageDto(movie_idx, movie_background, movie_title, release_year, genre_name, movie_time, age_name, poster, movie_description, score);
				list.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
 		return list;
	}
	
	// 영화 상세페이지 (메인 페이지에서 영화 클릭했을 때)
	// 출연/제작 정보 출력
	// 인물_IDX 가져오기
	public ArrayList<StaffDto> showStaff(int movie_idx) throws Exception {
	    ArrayList<StaffDto> staff = new ArrayList<StaffDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	    	String sql = "SELECT * "
	    			+ "FROM ( "
	    			+ "    SELECT p.person_idx,  "
	    			+ "           p.person_name,  "
	    			+ "           r.job,  "
	    			+ "           p.person_img "
	    			+ "    FROM role r "
	    			+ "    INNER JOIN person p ON r.role_idx = p.role_idx "
	    			+ "    INNER JOIN movie_person mp ON p.person_idx = mp.person_idx "
	    			+ "    WHERE movie_idx = ? "
	    			+ "    ORDER BY p.person_idx "
	    			+ ") "
	    			+ "WHERE ROWNUM <= 12";
	    	
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, movie_idx);
	    	rs = pstmt.executeQuery();
	    	
	    	while(rs.next()) {
	    		String person_name = rs.getString("person_name");
	    		String job = rs.getString("job");
	    		String person_img = rs.getString("person_img");
	    		int person_idx = rs.getInt("person_idx");
	    		
	    		StaffDto dto = new StaffDto(person_name, job, person_img, person_idx);
	    		staff.add(dto);
	    	}
	    	rs.close();
	    	pstmt.close();
	    	conn.close();
	    } catch(Exception e) { e.printStackTrace(); }
	    finally {
	    	try {
	    		if(rs != null) rs.close();
	    		if(pstmt != null) pstmt.close();
	    		if(conn != null) conn.close();
	    	} catch(SQLException e) { e.printStackTrace(); }
	    }
	    return staff;
	}
	
	// 영화 상세페이지 (메인 페이지에서 영화 클릭했을 때)
	// 코멘트 (전체) 출력 8개 (작성일자 순서)
	// 평가글_IDX 여기서 가져오기
	public ArrayList<CommentDto> showAll(int movie_idx) {
	    ArrayList<CommentDto> commentList = new ArrayList<CommentDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "WITH latest_scores AS ( "
	                + "    SELECT s.mm_idx, s.movie_idx, s.score "
	                + "    FROM ( "
	                + "        SELECT s.mm_idx, s.movie_idx, s.score, "
	                + "               ROW_NUMBER() OVER (PARTITION BY s.mm_idx, s.movie_idx ORDER BY s.star_idx DESC) AS rn "
	                + "        FROM star s "
	                + "    ) s "
	                + "    WHERE s.rn = 1 "
	                + "),  "
	                + "RankedMembers AS (  "
	                + "    SELECT  "
	                + "        m.profile_img,  "
	                + "        m.nickname,  "
	                + "        ROUND(  "
	                + "            (SELECT AVG(ls.score)   "
	                + "             FROM latest_scores ls   "
	                + "             WHERE ls.mm_idx = m.mm_idx AND ls.movie_idx = 1), 1) AS score,  "
	                + "        m.mm_idx "
	                + "    FROM member m  "
	                + "    WHERE EXISTS (SELECT 1 FROM evaluation_article ea WHERE ea.mm_idx = m.mm_idx AND ea.movie_idx = ?) "
	                + "    GROUP BY m.profile_img, m.nickname, m.mm_idx  "
	                + "),  "
	                + "RankedArticles AS (  "
	                + "    SELECT  "
	                + "        ea.eva_idx,  "
	                + "        ea.content, "
	                + "        ea.mm_idx, "
	                + "        ea.write_date, "
	                + "        ROW_NUMBER() OVER (PARTITION BY ea.mm_idx ORDER BY ea.eva_idx DESC) AS rn  "
	                + "    FROM evaluation_article ea  "
	                + "    WHERE ea.movie_idx = ?  "
	                + "),  "
	                + "ArticleCounts AS (  "
	                + "    SELECT  "
	                + "        ea.eva_idx,  "
	                + "        (SELECT COUNT(*) FROM love_eva WHERE eva_idx = ea.eva_idx) AS love_eva_idx,  "
	                + "        (SELECT COUNT(*) FROM reply WHERE eva_idx = ea.eva_idx) AS reply_idx  "
	                + "    FROM evaluation_article ea  "
	                + "    WHERE ea.movie_idx = ?  "
	                + ")  "
	                + "SELECT * FROM ( "
	                + "    SELECT  "
	                + "        rm.profile_img,  "
	                + "        rm.nickname,  "
	                + "        COALESCE(rm.score, 0) AS score,  "
	                + "        ra.eva_idx,  "
	                + "        ra.content,  "
	                + "        ac.love_eva_idx,  "
	                + "        ac.reply_idx, "
	                + "        ra.write_date "
	                + "    FROM RankedMembers rm  "
	                + "    JOIN RankedArticles ra ON rm.mm_idx = ra.mm_idx AND ra.rn = 1 "
	                + "    JOIN ArticleCounts ac ON ra.eva_idx = ac.eva_idx "
	                + "    ORDER BY ra.eva_idx DESC "
	                + ") WHERE ROWNUM <= 8";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, movie_idx);
	        pstmt.setInt(2, movie_idx);
	        pstmt.setInt(3, movie_idx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            String profile_img = rs.getString("profile_img");
	            String nickname = rs.getString("nickname");
	            double score = rs.getDouble("score");
	            int eva_idx = rs.getInt("eva_idx");
	            String content = rs.getString("content");
	            int love_eva_idx = rs.getInt("love_eva_idx");
	            int reply_idx = rs.getInt("reply_idx");

	            CommentDto dto = new CommentDto(profile_img, nickname, score, content, eva_idx, love_eva_idx, reply_idx);
	            commentList.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return commentList;
	}
	
	// 인물 상세 페이지 (개봉연도, 포스터, 인물의 역할(배우인지 감독인지), 좋아요 개수)
	public ArrayList<MakingPersonDto> showPersonInfo(int person_idx) {
	    ArrayList<MakingPersonDto> person = new ArrayList<MakingPersonDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT DISTINCT p.person_img, p.person_name, "
	                + "r.job, COUNT(lp.person_idx) AS \"좋아요 개수\" "
	                + "FROM love_person lp "
	                + "LEFT JOIN person p ON lp.person_idx = p.person_idx "
	                + "LEFT JOIN role r ON p.role_idx = r.role_idx "
	                + "WHERE lp.person_idx = ?  "
	                + "GROUP BY p.person_img, p.person_name, r.job "
	                + "ORDER BY p.person_name";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, person_idx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            String person_img = rs.getString("person_img");
	            String person_name = rs.getString("person_name");
	            String job = rs.getString("job");
	            int like_count = rs.getInt("좋아요 개수");

	            MakingPersonDto dto = new MakingPersonDto(person_img, person_name, job, like_count);
	            person.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return person;
	}

	
	// 인물 상세 페이지 (개봉연도, 포스터, 인물의 역할(배우인지 감독인지), 영화 제목, 별점)
	public ArrayList<MakingMovieDto> showMovieInfo(int person_idx) throws Exception {
		ArrayList<MakingMovieDto> movie = new ArrayList<MakingMovieDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT m.movie_idx, m.release_year, m.poster, r.job, "
					+ "m.movie_title, ROUND(AVG(s.score), 1) AS \"score\" "
					+ "FROM movie m "
					+ "LEFT JOIN star s ON m.movie_idx = s.movie_idx "
					+ "LEFT JOIN movie_person mp ON s.movie_idx = mp.movie_idx "
					+ "LEFT JOIN person p ON mp.person_idx = p.person_idx "
					+ "LEFT JOIN role r ON p.role_idx = r.role_idx "
					+ "WHERE p.person_idx = ? "
					+ "GROUP BY m.movie_idx, m.release_year, m.poster, r.job, m.movie_title";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, person_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int movie_idx = rs.getInt("movie_idx");
				String release_year = rs.getString("release_year");
				String poster = rs.getString("poster");
				String movie_title = rs.getString("movie_title");
				String job = rs.getString("job");
				double score = rs.getDouble("score");
				
				MakingMovieDto dto = new MakingMovieDto(movie_idx, release_year, poster, movie_title, job, score);
				movie.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return movie;
	}
	
	// 코멘트 상세 페이지(=평가글 상세 페이지) (프로필 사진, 닉네임, 작성일자, 영화 제목, 개봉 연도, 별점, 평가글 내용, 좋아요 개수, 댓글 개수)
	// WatchaPedia_Comment_Detail.jsp
	public ArrayList<CommentDetailDto> showComment(int eva_idx) throws Exception {
		ArrayList<CommentDetailDto> comment = new ArrayList<CommentDetailDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CommentDetailDto dto = null;
		try {
			String sql = "WITH EvaluationDetails AS ( "
					+ "    SELECT  "
					+ "        ea.eva_idx, "
					+ "        TO_CHAR(ea.write_date, 'YY/MM/DD') AS write_date, "
					+ "        mo.movie_title, "
					+ "        mo.poster, "
					+ "        mo.release_year, "
					+ "        ea.content "
					+ "    FROM evaluation_article ea "
					+ "    LEFT JOIN movie mo ON ea.movie_idx = mo.movie_idx "
					+ "    WHERE ea.eva_idx = ? "
					+ "), "
					+ "StarRatings AS ( "
					+ "    SELECT  "
					+ "        ea.eva_idx, "
					+ "        ROUND(AVG(s.score), 1) AS score "
					+ "    FROM star s "
					+ "    LEFT JOIN evaluation_article ea ON s.movie_idx = ea.movie_idx "
					+ "    WHERE ea.eva_idx = ? "
					+ "    GROUP BY ea.eva_idx "
					+ "), "
					+ "LikeCounts AS ( "
					+ "    SELECT  "
					+ "        ea.eva_idx, "
					+ "        COUNT(le.eva_idx) AS \"좋아요 개수\" "
					+ "    FROM love_eva le "
					+ "    LEFT JOIN evaluation_article ea ON le.eva_idx = ea.eva_idx "
					+ "    WHERE ea.eva_idx = ? "
					+ "    GROUP BY ea.eva_idx "
					+ "), "
					+ "ReplyCounts AS ( "
					+ "    SELECT  "
					+ "        ea.eva_idx, "
					+ "        COUNT(r.eva_idx) AS \"댓글 개수\" "
					+ "    FROM reply r "
					+ "    LEFT JOIN evaluation_article ea ON r.eva_idx = ea.eva_idx "
					+ "    WHERE ea.eva_idx = ? "
					+ "    GROUP BY ea.eva_idx "
					+ ") "
					+ "SELECT  "
					+ "    m.profile_img, "
					+ "    m.nickname, "
					+ "    ed.eva_idx, "
					+ "    ed.write_date, "
					+ "    ed.movie_title, "
					+ "    ed.poster, "
					+ "    ed.release_year, "
					+ "    sr.score AS \"별점\", "
					+ "    ed.content, "
					+ "    COALESCE(lc.\"좋아요 개수\", 0) AS \"좋아요 개수\", "
					+ "    COALESCE(rc.\"댓글 개수\", 0) AS \"댓글 개수\" "
					+ "FROM member m "
					+ "LEFT JOIN evaluation_article ea ON m.mm_idx = ea.mm_idx "
					+ "LEFT JOIN EvaluationDetails ed ON ea.eva_idx = ed.eva_idx "
					+ "LEFT JOIN StarRatings sr ON ea.eva_idx = sr.eva_idx "
					+ "LEFT JOIN LikeCounts lc ON ea.eva_idx = lc.eva_idx "
					+ "LEFT JOIN ReplyCounts rc ON ea.eva_idx = rc.eva_idx "
					+ "WHERE ea.eva_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eva_idx);
			pstmt.setInt(2, eva_idx);
			pstmt.setInt(3, eva_idx);
			pstmt.setInt(4, eva_idx);
			pstmt.setInt(5, eva_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String profile_img = rs.getString("profile_img");
				String nickname = rs.getString("nickname");
				String release_year = rs.getString("release_year");
				String write_date = rs.getString("write_date");
				String movie_title = rs.getString("movie_title");
				String poster = rs.getString("poster");
				double score = rs.getDouble("별점");
				String content = rs.getString("content");
				int love_eva_idx = rs.getInt("좋아요 개수");
				int reply_eva_idx = rs.getInt("댓글 개수");
				
				dto = new CommentDetailDto(profile_img, nickname, release_year, write_date, movie_title, poster, score, content, love_eva_idx, reply_eva_idx);
				comment.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); } 
		}
		return comment;
	}
	
	// 코멘트 클릭했을 때 (회색) 코멘트 상세 페이지(=평가글 상세 페이지) (프로필 사진, 닉네임, 댓글 내용, 댓글 작성일자, 댓글 좋아요 개수)
	public ArrayList<ReplyDto> showReply(int eva_idx) throws Exception {
		ArrayList<ReplyDto> reply = new ArrayList<ReplyDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT r.reply_idx, m.mm_idx, m.profile_img, m.nickname, "
					+ "r.reply_content, TO_CHAR(r.reply_date, 'YY/MM/DD') AS reply_date, COUNT(reply_like_idx) AS reply_like_idx "
					+ "FROM member m "
					+ "LEFT JOIN reply r ON m.mm_idx = r.mm_idx "
					+ "LEFT JOIN reply_like rl ON r.reply_idx = rl.reply_idx "
					+ "WHERE r.eva_idx = ? "
					+ "GROUP BY r.reply_idx, m.mm_idx, m.profile_img, m.nickname, "
					+ "r.reply_content, r.reply_date "
					+ "ORDER BY r.reply_date";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eva_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reply_idx = rs.getInt("reply_idx");
				int mm_idx = rs.getInt("mm_idx");
				String profile_img = rs.getString("profile_img");
				String nickname = rs.getString("nickname");
				String reply_content = rs.getString("reply_content");
				String reply_date = rs.getString("reply_date");
				int reply_like_idx = rs.getInt("reply_like_idx");
				
				ReplyDto dto = new ReplyDto(reply_idx, mm_idx, profile_img, nickname, reply_content, reply_date, reply_like_idx);
				reply.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		
		return reply;
	}
	
	// 코멘트 (=평가글) 전체 개수 구하기 (한 영화에 대한)
	public int allCommentCount(int movie_idx) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0; 
		try {
			String sql = "SELECT COUNT(*) AS eva_idx FROM evaluation_article WHERE movie_idx = ?";
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("eva_idx");
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return count;
	}
	
	// 코멘트 (=평가글) 전체 개수 구하기 (영화에 상관없이)
	public int allEvaCount() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int eva_count = 0;
		try {
			String sql = "SELECT COUNT(*) FROM evaluation_article";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				eva_count = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return eva_count;
	}
	
	// 더보기 클릭 시 Pagination (좋아요 순 DESC)
	public ArrayList<MoreCommentDto> showScroll(int movie_idx, int pageNum) throws Exception {
		ArrayList<MoreCommentDto> scroll = new ArrayList<MoreCommentDto>();
	    
		int endNum = pageNum * 3;
	    int startNum = endNum - 2;
	    
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	    	String sql = "WITH RankedArticles AS (  "
	    			+ "    SELECT   "
	    			+ "        ea.eva_idx,  "
	    			+ "        ea.mm_idx,  "
	    			+ "        ea.content,  "
	    			+ "        ea.write_date,  "
	    			+ "        (SELECT COUNT(*) FROM love_eva WHERE love_eva.eva_idx = ea.eva_idx) AS 좋아요_개수,  "
	    			+ "        ROW_NUMBER() OVER (ORDER BY (SELECT COUNT(*) FROM love_eva WHERE love_eva.eva_idx = ea.eva_idx) DESC, ea.write_date ASC) AS rn "
	    			+ "    FROM evaluation_article ea  "
	    			+ "    WHERE ea.movie_idx = ?  "
	    			+ "),  "
	    			+ "RankedMembers AS ( "
	    			+ "    SELECT   "
	    			+ "        m.profile_img,   "
	    			+ "        m.nickname,   "
	    			+ "        m.mm_idx,  "
	    			+ "        (SELECT ROUND(AVG(s.score), 1) FROM star s WHERE s.mm_idx = m.mm_idx) AS score "
	    			+ "    FROM member m "
	    			+ "    GROUP BY m.profile_img, m.nickname, m.mm_idx "
	    			+ "),  "
	    			+ "ArticleCounts AS (  "
	    			+ "    SELECT   "
	    			+ "        ea.eva_idx,  "
	    			+ "        (SELECT COUNT(*) FROM reply WHERE reply.eva_idx = ea.eva_idx) AS 댓글_개수  "
	    			+ "    FROM evaluation_article ea  "
	    			+ "    WHERE ea.movie_idx = ?  "
	    			+ ") "
	    			+ "SELECT rm.profile_img,   "
	    			+ "       rm.nickname,   "
	    			+ "       rm.mm_idx,   "
	    			+ "       rm.score,   "  // score 필드 추가
	    			+ "       ra.eva_idx,   "
	    			+ "       ra.content,  "
	    			+ "       ra.write_date,  "
	    			+ "       ra.좋아요_개수,  "
	    			+ "       ac.댓글_개수  "
	    			+ "FROM RankedArticles ra "
	    			+ "JOIN RankedMembers rm ON ra.mm_idx = rm.mm_idx  "
	    			+ "JOIN ArticleCounts ac ON ra.eva_idx = ac.eva_idx  "
	    			+ "WHERE ra.rn BETWEEN ? AND ?  "
	    			+ "ORDER BY ra.좋아요_개수 DESC, ra.write_date ASC";
	    	conn = getConnection(); 
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, movie_idx);
	    	pstmt.setInt(2, movie_idx);
	    	pstmt.setInt(3, startNum);  // 페이지네이션 시작 번호
	    	pstmt.setInt(4, endNum);    // 페이지네이션 끝 번호
	    	rs = pstmt.executeQuery();
	    	
	    	while (rs.next()) {
	    		String profile_img = rs.getString("profile_img");
	    		String nickname = rs.getString("nickname");
	    		double score = rs.getDouble("score");  // score 필드 가져오기
	    		String content = rs.getString("content");
	    		int love_eva_idx = rs.getInt("좋아요_개수");
	    		int reply_idx = rs.getInt("댓글_개수");
	    		int eva_idx = rs.getInt("eva_idx");
	    		
	    		MoreCommentDto dto = new MoreCommentDto(profile_img, nickname, score, content, love_eva_idx, reply_idx, eva_idx);
	    		scroll.add(dto);
	    	}
	    	rs.close();
	    	pstmt.close();
	    	conn.close();
	    } catch(Exception e) { e.printStackTrace(); }
	    finally {
	    	try {
	    		if(rs != null) rs.close();
	    		if(pstmt != null) pstmt.close();
	    		if(conn != null) conn.close();
	    	} catch(SQLException e) { e.printStackTrace(); }
	    }
	    return scroll;
	}
	
	// 코멘트 더보기 클릭 시 좋아요 순서 마지막 페이지 번호
	public int getLastPageNum(int movie_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int lastPageNum = -1;
		try {
			String sql = "SELECT COUNT(*) FROM evaluation_article WHERE movie_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				lastPageNum = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return (lastPageNum + 2) / 3;
	}
	
	// 더보기 클릭 시 Pagination (작성일자 순 DESC)
	public ArrayList<MoreComWriteDto> showWrite(int movie_idx, int pageNum) throws Exception {
		ArrayList<MoreComWriteDto> write = new ArrayList<MoreComWriteDto>();

		int endNum = pageNum * 3;	// 페이지 사이즈
		int startNum = endNum - 2;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "WITH RankedMembers AS ( "
					+ "    SELECT  "
					+ "        m.profile_img,  "
					+ "        m.nickname,  "
					+ "        ROUND(AVG(s.score), 1) AS score,  "
					+ "        m.mm_idx, "
					+ "        ROW_NUMBER() OVER (ORDER BY m.mm_idx ASC) AS rn "
					+ "    FROM member m "
					+ "    LEFT JOIN evaluation_article ea ON m.mm_idx = ea.mm_idx "
					+ "    LEFT JOIN star s ON ea.mm_idx = s.mm_idx "
					+ "    WHERE ea.movie_idx = ? "
					+ "    GROUP BY m.profile_img, m.nickname, m.mm_idx "
					+ "), "
					+ "RankedArticles AS ( "
					+ "    SELECT  "
					+ "        ea.eva_idx, "
					+ "        ea.mm_idx, "
					+ "        ea.content, "
					+ "        ea.write_date, "
					+ "        ROW_NUMBER() OVER (ORDER BY ea.write_date DESC) AS rn "
					+ "    FROM evaluation_article ea "
					+ "    WHERE ea.movie_idx = ? "
					+ "), "
					+ "ArticleCounts AS ( "
					+ "    SELECT  "
					+ "        ea.eva_idx, "
					+ "        (SELECT COUNT(*) FROM love_eva WHERE eva_idx = ea.eva_idx) AS \"좋아요 개수\", "
					+ "        (SELECT COUNT(*) FROM reply WHERE eva_idx = ea.eva_idx) AS \"댓글 개수\" "
					+ "    FROM evaluation_article ea "
					+ "    WHERE ea.movie_idx = ? "
					+ ") "
					+ "SELECT  "
					+ "    rm.profile_img,  "
					+ "    rm.nickname,  "
					+ "    rm.score,  "
					+ "    ra.eva_idx, "
					+ "    ra.content, "
					+ "    ac.\"좋아요 개수\", "
					+ "    ac.\"댓글 개수\" "
					+ "FROM RankedMembers rm "
					+ "JOIN RankedArticles ra ON rm.mm_idx = ra.mm_idx "
					+ "JOIN ArticleCounts ac ON ra.eva_idx = ac.eva_idx "
					+ "WHERE ra.rn BETWEEN ? AND ? " // 페이지네이션 조건 추가
					+ "ORDER BY ra.write_date DESC";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			pstmt.setInt(2, movie_idx);
			pstmt.setInt(3, movie_idx);
			pstmt.setInt(4, startNum);
			pstmt.setInt(5, endNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String profile_img = rs.getString("profile_img");
				String nickname = rs.getString("nickname");
				double score = rs.getDouble("score");
				String content = rs.getString("content");
				int love_eva_idx = rs.getInt("좋아요 개수");
				int reply_idx = rs.getInt("댓글 개수");
				int eva_idx = rs.getInt("eva_idx");
				
				MoreComWriteDto dto = new MoreComWriteDto(profile_img, nickname, score, content, love_eva_idx, reply_idx, eva_idx);
				write.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return write;
	}
	
	// 평가글 작성 (영화 상세 페이지)
	public void insertComment(int movie_idx, String content, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO evaluation_article (eva_IDX, movie_IDX, content, mm_idx, write_date) "
					+ "VALUES (seq_evaluation_article.nextval, ?, ?, ?, sysdate)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			pstmt.setString(2, content);
			pstmt.setInt(3, mm_idx);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 평가글 수정 (영화 상세 페이지) 수정하면 그 페이지 그대로 로드됨
	public void updateComment(String content, int eva_idx, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE evaluation_article "
					+ "SET content = ? "
					+ "WHERE eva_idx = ? AND mm_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, eva_idx);
			pstmt.setInt(3, mm_idx);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 평가글 삭제 (영퐈 상세 페이지) 삭제하면 메인 페이지로 넘어감
	public void deleteComment(int eva_idx, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM evaluation_article WHERE eva_idx = ? AND mm_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eva_idx);
			pstmt.setInt(2, mm_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 영화 메인 페이지 (지금 뜨는 코멘트)
	public ArrayList<RightUplDto> showRight() throws Exception {
		ArrayList<RightUplDto> right = new ArrayList<RightUplDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "WITH latest_scores AS ( "
					+ "    SELECT s.mm_idx, s.movie_idx, s.score "
					+ "    FROM ( "
					+ "        SELECT s.mm_idx, s.movie_idx, s.score, "
					+ "               ROW_NUMBER() OVER (PARTITION BY s.mm_idx, s.movie_idx ORDER BY s.star_idx DESC) AS rn "
					+ "        FROM star s "
					+ "    ) s "
					+ "    WHERE s.rn = 1 "
					+ "),  "
					+ "RankedMembers AS (  "
					+ "    SELECT  "
					+ "        m.profile_img,  "
					+ "        m.nickname,  "
					+ "        ROUND(  "
					+ "            (SELECT AVG(ls.score)   "
					+ "             FROM latest_scores ls   "
					+ "             WHERE ls.mm_idx = m.mm_idx AND ls.movie_idx = 1), 1) AS score,  "
					+ "        m.mm_idx "
					+ "    FROM member m  "
					+ "    GROUP BY m.profile_img, m.nickname, m.mm_idx  "
					+ "),  "
					+ "RankedArticles AS (  "
					+ "    SELECT  "
					+ "        ea.eva_idx,  "
					+ "        ea.content, "
					+ "        ea.mm_idx, "
					+ "        ea.write_date, "
					+ "        ROW_NUMBER() OVER (ORDER BY ea.eva_idx DESC) AS row_num "
					+ "    FROM evaluation_article ea  "
					+ "),  "
					+ "ArticleCounts AS (  "
					+ "    SELECT  "
					+ "        ea.eva_idx,  "
					+ "        (SELECT COUNT(*) FROM love_eva WHERE eva_idx = ea.eva_idx) AS love_eva_idx,  "
					+ "        (SELECT COUNT(*) FROM reply WHERE eva_idx = ea.eva_idx) AS reply_idx  "
					+ "    FROM evaluation_article ea  "
					+ "),  "
					+ "MovieDetails AS ( "
					+ "    SELECT  "
					+ "        mo.movie_idx,  "
					+ "        mo.movie_title,  "
					+ "        mo.poster,  "
					+ "        ea.eva_idx  "
					+ "    FROM movie mo  "
					+ "    JOIN evaluation_article ea ON mo.movie_idx = ea.movie_idx "
					+ ") "
					+ "SELECT  "
					+ "    md.movie_title,  "
					+ "    rm.mm_idx,  "
					+ "    rm.profile_img,  "
					+ "    rm.nickname,  "
					+ "    COALESCE(rm.score, 0) AS score,  "
					+ "    ra.eva_idx,  "
					+ "    ra.content,  "
					+ "    md.poster,  "
					+ "    ac.love_eva_idx AS \"좋아요 개수\",  "
					+ "    ac.reply_idx AS \"댓글 개수\",  "
					+ "    md.movie_idx "
					+ "FROM RankedMembers rm  "
					+ "JOIN RankedArticles ra ON rm.mm_idx = ra.mm_idx "
					+ "JOIN ArticleCounts ac ON ra.eva_idx = ac.eva_idx "
					+ "JOIN MovieDetails md ON ra.eva_idx = md.eva_idx "
					+ "WHERE ra.row_num <= 3 "
					+ "ORDER BY ra.eva_idx DESC";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String movie_title = rs.getString("movie_title");
				int mm_idx = rs.getInt("mm_idx");
				String profile_img = rs.getString("profile_img");
				String nickname = rs.getString("nickname");
				double score = rs.getDouble("score");
				int eva_idx = rs.getInt("eva_idx");
				String content = rs.getString("content");
				String poster = rs.getString("poster");
				int love_eva_idx = rs.getInt("좋아요 개수");
				int reply_idx = rs.getInt("댓글 개수");
				int movie_idx = rs.getInt("movie_idx");
				
				RightUplDto dto = new RightUplDto(movie_title, mm_idx, profile_img, nickname, score, eva_idx, content, poster, love_eva_idx, reply_idx, movie_idx);
				right.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return right;
	}
	
	// 영화 메인 페이지 (최근 개봉한 영화)
	// 영화_IDX 가져오기
	public ArrayList<RecentListDto> showRecent() throws Exception {
		ArrayList<RecentListDto> recent = new ArrayList<RecentListDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT *  "
					+ "FROM ( "
					+ "    SELECT m.poster, m.movie_title, m.release_year, ROUND(AVG(s.score),1) AS score, m.movie_idx"
					+ "    FROM movie m "
					+ "    LEFT JOIN star s ON m.movie_idx = s.movie_idx "
					+ "    GROUP BY m.poster, m.movie_title, m.release_year, m.movie_idx "
					+ "    ORDER BY m.release_year DESC "
					+ ")  "
					+ "WHERE ROWNUM <= 5";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String poster = rs.getString("poster");
				String movie_title = rs.getString("movie_title");
				String release_year = rs.getString("release_year");
				double score = rs.getDouble("score");
				int movie_idx = rs.getInt("movie_idx");
				
				RecentListDto dto = new RecentListDto(poster, movie_title, release_year, score, movie_idx);
				recent.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return recent;
	}
	
	// 영화 메인 페이지 (별점 높은 순위)
	// 영화_IDX 가져오기
	public ArrayList<ScoreDescDto> showScoDesc() throws Exception {
		ArrayList<ScoreDescDto> desc = new ArrayList<ScoreDescDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * "
					+ "FROM ( "
					+ "    SELECT m.poster, m.movie_title, m.release_year, ROUND(AVG(s.score), 1) AS score, m.movie_idx "
					+ "    FROM movie m "
					+ "    LEFT JOIN star s ON m.movie_idx = s.movie_idx "
					+ "    GROUP BY m.poster, m.movie_title, m.release_year, m.movie_idx "
					+ "    ORDER BY score DESC "
					+ ") "
					+ "WHERE ROWNUM <= 5";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String poster = rs.getString("poster");
				String movie_title = rs.getString("movie_title");
				String release_year = rs.getString("release_year");
				double score = rs.getDouble("score");
				int movie_idx = rs.getInt("movie_idx");
				
				ScoreDescDto dto = new ScoreDescDto(poster, movie_title, release_year, score, movie_idx);
				desc.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return desc;
	}
	
	// 영화 메인 페이지 (청소년 관람불가 분류)
	// 영화_IDX 가져오기
	public ArrayList<RatedRDto> showRate(int avg_idx) throws Exception {
		ArrayList<RatedRDto> rate = new ArrayList<RatedRDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * "
					+ "FROM ( "
					+ "    SELECT m.poster, m.movie_title, m.release_year, ROUND(AVG(s.score), 1) AS score, m.movie_idx "
					+ "    FROM movie m "
					+ "    LEFT JOIN star s ON m.movie_idx = s.movie_idx "
					+ "    WHERE m.age_idx = ? "
					+ "    GROUP BY m.poster, m.movie_title, m.release_year, m.movie_idx "
					+ "    ORDER BY score DESC "
					+ ") "
					+ "WHERE ROWNUM <= 5";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, avg_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String poster = rs.getString("poster");
				String movie_title = rs.getString("movie_title");
				String release_year = rs.getString("release_year");
				double score = rs.getDouble("score");
				int movie_idx = rs.getInt("movie_idx");
				
				RatedRDto dto = new RatedRDto(poster, movie_title, release_year, score, movie_idx, avg_idx);
				rate.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return rate;
	}
	
	// 영화 프로필 페이지
	public ArrayList<ProfileDto> showProfile(int mm_idx) throws Exception {
		ArrayList<ProfileDto> profile = new ArrayList<ProfileDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT "
					+ "    m.profile_img, "
					+ "    m.nickname, "
					+ "    m.id, "
					+ "    COUNT(DISTINCT mo.movie_idx) AS star_count, "
					+ "    COUNT(DISTINCT ea.eva_idx) AS eva_count "
					+ "FROM member m "
					+ "LEFT JOIN evaluation_article ea ON m.mm_idx = ea.mm_idx "
					+ "LEFT JOIN star s ON ea.mm_idx = s.mm_idx "
					+ "LEFT JOIN movie mo ON s.movie_idx = mo.movie_idx "
					+ "WHERE m.mm_idx = ? "
					+ "GROUP BY m.profile_img, m.nickname, m.id";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String profile_img = rs.getString("profile_img");
				String nickname = rs.getString("nickname");
				String id = rs.getString("id");
				int star_count = rs.getInt("star_count");
				int eva_count = rs.getInt("eva_count");
				
				ProfileDto dto = new ProfileDto(profile_img, nickname, id, star_count, eva_count);
				profile.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return profile;
	}
	
	// 프로필 별점 평가한 영화들
	public ArrayList<MovieEvaStarDto> showMovieStar(int mm_idx) throws Exception {
		ArrayList<MovieEvaStarDto> star = new ArrayList<MovieEvaStarDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT DISTINCT m.poster, m.movie_title, m.movie_idx, s.score "
					+ "FROM movie m "
					+ "LEFT JOIN star s ON m.movie_idx = s.movie_idx "
					+ "LEFT JOIN member me ON s.mm_idx = me.mm_idx "
					+ "WHERE me.mm_idx = ? "
					+ "  AND s.score = ( "
					+ "      SELECT MAX(s2.score) "
					+ "      FROM star s2 "
					+ "      WHERE s2.movie_idx = m.movie_idx "
					+ "        AND s2.mm_idx = me.mm_idx "
					+ "  )";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String poster = rs.getString("poster");
				String movie_title = rs.getString("movie_title");
				int movie_idx = rs.getInt("movie_idx");
				double score = rs.getDouble("score");
				
				MovieEvaStarDto dto = new MovieEvaStarDto(poster, movie_title, movie_idx, score);
				star.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return star;
	}
	
	// 무한 스크롤 적용
	// 프로필 코멘트(=평가글) 작성한 div
	public ArrayList<WriteByMemberCommentDto> showWriteByMember(int mm_idx, int pageNum) throws Exception {
		ArrayList<WriteByMemberCommentDto> member = new ArrayList<WriteByMemberCommentDto>();

		int endNum = pageNum * 3;
		int startNum = endNum - 2;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "WITH latest_scores AS (  "
					+ "    SELECT s.mm_idx, s.movie_idx, s.score  "
					+ "    FROM (  "
					+ "        SELECT s.mm_idx, s.movie_idx, s.score,  "
					+ "               ROW_NUMBER() OVER (PARTITION BY s.movie_idx ORDER BY s.star_idx DESC) AS rn  "
					+ "        FROM star s  "
					+ "    ) s  "
					+ "    WHERE s.rn = 1  "
					+ ")  "
					+ "SELECT *  "
					+ "FROM (  "
					+ "    SELECT mo.movie_idx, m.profile_img, m.nickname, mo.poster,  "
					+ "           mo.movie_title, mo.release_year, ea.content, ea.eva_idx,  "
					+ "           (SELECT COUNT(love_eva_idx) FROM love_eva le WHERE le.eva_idx = ea.eva_idx) AS love_eva_idx,  "
					+ "           (SELECT COUNT(reply_idx) FROM reply r WHERE r.eva_idx = ea.eva_idx) AS reply_idx,  "
					+ "           COALESCE(ls.score, 0) AS score,  "
					+ "           ROWNUM AS row_num  "
					+ "    FROM member m  "
					+ "    LEFT JOIN evaluation_article ea ON m.mm_idx = ea.mm_idx  "
					+ "    LEFT JOIN movie mo ON ea.movie_idx = mo.movie_idx  "
					+ "    LEFT JOIN latest_scores ls ON ls.movie_idx = mo.movie_idx AND ls.mm_idx = m.mm_idx  "
					+ "    WHERE m.mm_idx = ?  "
					+ ")  "
					+ "WHERE row_num >= ? AND row_num <= ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			pstmt.setInt(2, startNum);
			pstmt.setInt(3, endNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int movie_idx = rs.getInt("movie_idx");
				String profile_img = rs.getString("profile_img");
				String nickname = rs.getString("nickname");
				String poster = rs.getString("poster");
				String movie_title = rs.getString("movie_title");
				String release_year = rs.getString("release_year");
				String content = rs.getString("content");
				int love_eva_idx = rs.getInt("love_eva_idx");
				int reply_idx = rs.getInt("reply_idx");
				int eva_idx = rs.getInt("eva_idx");
				double score = rs.getDouble("score");
				
				WriteByMemberCommentDto dto = new WriteByMemberCommentDto(movie_idx, profile_img, nickname, poster, movie_title, release_year, content, love_eva_idx, reply_idx, eva_idx, score);
				member.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return member;
	}
	
	// 무한스크롤 마지막 페이지 번호 가져오기
	public int getLastScrollNumber(int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			String sql = "SELECT COUNT(*) FROM evaluation_article WHERE mm_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return result;
	}
	
	// 프로필 코멘트(=평가글) 작성한 div 별점만 따로 구하기
	public ArrayList<StarByMemberDto> getStarByMember(int mm_idx, int movie_idx) throws Exception {
		ArrayList<StarByMemberDto> starByMember = new ArrayList<StarByMemberDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT score FROM star WHERE mm_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, mm_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int score = rs.getInt("score");
				
				StarByMemberDto dto = new StarByMemberDto(score);
				starByMember.add(dto);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return starByMember;
	}
	
	// 해당 평가글 좋아요 개수 SELECT문
	public int evaCount(int eva_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			String sql = "SELECT COUNT(love_eva_idx) AS love_eva_idx FROM love_eva WHERE eva_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eva_idx);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				count = rs.getInt("love_eva_idx");
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return count;
	}
	
	// 코멘트(평가글) 좋아요를 눌렀는지 확인하는 쿼리문
	public boolean checkEva(int eva_idx, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean checkEva = false;
		try {
			String sql = "SELECT COUNT(love_eva_idx) AS love_eva_idx FROM love_eva WHERE eva_idx = ? AND mm_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eva_idx);
			pstmt.setInt(2, mm_idx);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				checkEva = rs.getInt("love_eva_idx") > 0;
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return checkEva;
	}
	
	// 평가글 좋아요 클릭 시 INSERT문
	public void evaIncrease(int eva_idx, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO love_eva(love_eva_idx, mm_idx, eva_idx)"
					+ " VALUES(seq_love_eva.nextval, ?, ?)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eva_idx);
			pstmt.setInt(2, mm_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	
	// 평가글 좋아요 취소 클릭 시 DELETE문
	public void evaDecrease(int eva_idx, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM love_eva WHERE eva_idx = ? AND mm_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eva_idx);
			pstmt.setInt(2, mm_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 해당 댓글의 좋아요 개수 SELECT문
	public int replyCount(int reply_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			String sql = "SELECT COUNT(reply_like_idx) AS reply_like_idx FROM reply_like WHERE reply_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply_idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("reply_like_idx");
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return result;
	}
	
	// 댓글 좋아요를 눌렀는지 확인하는 쿼리문
	public boolean checkLike(int reply_idx, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean check = false;
		try {
			String sql = "SELECT COUNT(reply_like_idx) AS reply_like_idx FROM reply_like WHERE reply_idx = ? AND mm_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply_idx);
			pstmt.setInt(2, mm_idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				check = rs.getInt("reply_like_idx") > 0;
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return check;
	}
	
	// 댓글 좋아요 버튼 클릭 시 좋아요 개수 증가 INSERT문
	public void replyLikeIncrease(int reply_idx, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO reply_like(reply_like_idx, reply_idx, mm_idx)"
					+ " VALUES(seq_reply_like.nextval, ?, ?)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply_idx);
			pstmt.setInt(2, mm_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 댓글 좋아요 취소 버튼 클릭 시 좋아요 개수 감소 DELETE문
	public void replyLikeDecrease(int reply_idx, int mm_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM reply_like WHERE reply_idx = ? AND mm_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply_idx);
			pstmt.setInt(2, mm_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 댓글 작성 INSERT문
	public void writeReply(int mm_idx, String reply_content, int eva_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO reply (reply_IDX, mm_IDX, reply_content, eva_idx, reply_date)"
					+ " VALUES(seq_reply.nextval, ?, ?, ?, sysdate)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			pstmt.setString(2, reply_content);
			pstmt.setInt(3, eva_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 댓글 삭제 DELETE문
	public void deleteReply(int reply_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM reply WHERE reply_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 댓글 수정 UPDATE문
	public void updateReply(String reply_content, int reply_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE reply SET reply_content = ?, reply_date = sysdate "
					+ "WHERE reply_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply_content);
			pstmt.setInt(2, reply_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 별점 클릭 시 저장했는지 확인하는 메소드
	public int checkStarExists(int mm_idx, int movie_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int starAvg = 0;
		try {
			String sql = "SELECT score  "
					+ "FROM ( "
					+ "    SELECT score  "
					+ "    FROM star  "
					+ "    WHERE mm_idx = ?  "
					+ "    AND movie_idx = ?  "
					+ "    ORDER BY star_idx DESC "
					+ ")  "
					+ "WHERE ROWNUM = 1";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			pstmt.setInt(2, movie_idx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				starAvg = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return starAvg;
	}
	
	// 별점 평가 INSERT문
	public void starInsert(int mm_idx, int movie_idx, int score) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO star(star_idx, mm_idx, movie_idx, score) VALUES(seq_star.nextval, ?, ?, ?)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			pstmt.setInt(2, movie_idx);
			pstmt.setInt(3, score);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 별점 삭제 DELETE문
	public void starDelete(int mm_idx, int movie_idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM star WHERE mm_idx = ? AND movie_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mm_idx);
			pstmt.setInt(2, movie_idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
	
	// 로그인
	public int loginCheck(String id, String password) throws Exception {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mm_idx = 0; // null로 초기화
		try {
			String sql = "SELECT mm_idx FROM member WHERE id = ? AND password = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mm_idx = rs.getInt("mm_idx"); // mm_idx를 가져옴.
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	    return mm_idx; // mm_idx를 반환
	}
	
	// 회원가입 아이디 중복체크
	public int registerDuplication(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			String sql = "SELECT id FROM member WHERE id = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("id").equals(id)) {
					result = 1;
				}
			} else {
				result = 0;
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return result;
	}
	
	// 회원가입
	public void registerCheck(String nickname, String id, String password) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO member(mm_idx, nickname, id, password) "
					+ " VALUES(seq_member.nextval, ?, ?, ?)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			pstmt.setString(2, id);
			pstmt.setString(3, password);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(Exception e) { e.printStackTrace(); }
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
	}
}
