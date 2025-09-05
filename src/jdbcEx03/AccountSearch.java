package jdbcEx03;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountSearch {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();

        String selectSql = "SELECT * FROM accounts";

        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

            ResultSet rs = pstmt.executeQuery(selectSql);
            while (rs.next()) {
                jdbcEx03.vo.Account account = new jdbcEx03.vo.Account();
                account.setAno(rs.getString(1));
                account.setOwner(rs.getString(2));
                account.setBalance(rs.getBigDecimal(3));
                System.out.println("예금주 : " + account.getOwner() + "  계좌번호 : " + account.getAno());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
