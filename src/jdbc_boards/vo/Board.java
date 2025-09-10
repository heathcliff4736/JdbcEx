package jdbc_boards.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private int bno;
    private String btitle;
    private String bcontent;
    private String bwriter;
    private Date bdate;

    @Override
    public String toString() {
        return "-----------------------------------------\n" +
                "   번호      : " + bno + "\n" +
                "   제목      : " + btitle + "\n" +
                "   내용      : " + bcontent + "\n" +
                "   작성자    : " + bwriter + "\n" +
                "   작성일    : " + bdate + "\n" +
                "-----------------------------------------\n";
    }
}
