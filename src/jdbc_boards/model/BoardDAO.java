package jdbc_boards.model;

import jdbc_boards.vo.Board;
import util.DBUtil;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

    List<Board> boardList = new ArrayList<>();

    public boolean createBoard(Board board) {
        String sql = "INSERT INTO boardtable(btitle,bcontent,bwriter) values(?,?,?)";
        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setString(3, board.getBwriter());
            int affected = pstmt.executeUpdate();
            boolean result = affected > 0;
            // 생성된 PK를 Board 객체에 반영 -> Auto increment 처리 방식
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if(rs.next()) {
                    int newbno = rs.getInt(1);
                    board.setBno(newbno);
                    boardList.add(board);
                }
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Board board) {
        String sql = "UPDATE boardtable SET btitle = ?, bcontent = ? WHERE bno = ?";
        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setInt(3, board.getBno());
            int affected = pstmt.executeUpdate();
            if(affected > 0) {
                return true;
            } else {
                System.out.println("존재하지 않는 글 번호입니다.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int bno) {
        String sql = "DELETE FROM boardtable WHERE bno =?";
        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, bno);
            int affected = pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clear(){
        String sql = "TRUNCATE TABLE boardtable";
        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.executeUpdate();
            System.out.println("Table cleared");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to clear table");
        }
    }

    public List<Board> selectAll() {
        String sql = "SELECT * FROM boardtable ORDER BY bdate DESC";
        List<Board> boardList = new ArrayList<>();
        try(
            Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            try (ResultSet rs = pstmt.executeQuery();){
                while(rs.next()) {
                    Board board = new Board();
                    board.setBno(rs.getInt("bno"));
                    board.setBtitle(rs.getString("btitle"));
                    board.setBcontent(rs.getString("bcontent"));
                    board.setBwriter(rs.getString("bwriter"));
                    board.setBdate(rs.getDate("bdate"));
                    boardList.add(board);
                }
                return boardList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Board selectOne(int bno) {
        String sql = "SELECT * FROM boardtable WHERE bno = ?";
        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, bno);
            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    Board board = new Board();
                    board.setBno(rs.getInt("bno"));
                    board.setBtitle(rs.getString("btitle"));
                    board.setBcontent(rs.getString("bcontent"));
                    board.setBwriter(rs.getString("bwriter"));
                    board.setBdate(rs.getDate("bdate"));

                    return board;
                } else {
                    System.out.println("찾는 글이 없습니다.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
