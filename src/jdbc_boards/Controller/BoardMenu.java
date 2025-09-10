package jdbc_boards.Controller;

import jdbc_boards.model.BoardDAO;
import jdbc_boards.vo.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class BoardMenu {

    BoardDAO dao;
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public BoardMenu() {
        dao = new BoardDAO();
    }

    public void boardMenu() throws IOException {
        System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Update | 4.Delete | 5.Exit");
        System.out.print("메뉴 선택: ");
        int choice = 0;
        try {
            choice = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            System.out.println("입력도중 에러 발생");
        } catch (NumberFormatException e1) {
            System.out.println("숫자만 입력하라니까");
        } catch (Exception e2) {
            System.out.println("꿰엑 에라 모르겠다.");
        }
        switch (choice) {
            case 1 -> {
                //사용자에게 title,content를 입력받아서 Board 구성하여 createBoard()넘겨주자
                Board row = boardDataInput();
                boolean ack = dao.createBoard(row);
                if (ack == true) System.out.println("글이 성공적으로 입력되었습니다.");
                else {
                    System.out.println("입력 실패, 다시 시도 부탁드립니다. ");
                    //원하는 위치로 이동
                    boardMenu();
                }
            }
            case 2 -> {
                ReadMode();
            }
            case 3 -> {
                Board update = boardDataUpdate();
                boolean ackupdate = dao.update(update);
                if (ackupdate == true) System.out.println("글이 성공적으로 수정되었습니다.");
                else {
                    System.out.println("수정 실패, 다시 시도 부탁드립니다. ");
                    boardMenu();
                }
            }
            case 4 -> {
                DeleteMode();
            }
            case 5 -> {
                System.out.println("\nSee ya");
                return;
            }
            default -> {
                System.out.println("1~5의 숫자를 입력하시오.");
                break;
            }
        }
        boardMenu();
    }

    public Board boardDataInput() throws IOException {
        Board board = new Board();
        System.out.println("새로운 글 입력");
        System.out.print("제목 입력 : ");
        String title = input.readLine();
        board.setBtitle(title);
        System.out.print("내용 입력 : ");
        String content = input.readLine();
        board.setBcontent(content);
        System.out.print("작성자 : ");
        String writer = input.readLine();
        board.setBwriter(writer);
        return board;
    }

    public void ReadMode() throws IOException {
        System.out.println("Read mode :: \n :: Press 1 to Read one || Press 2 to Read all ::");
        int choice = Integer.parseInt(input.readLine());
        switch (choice) {
            case 1 -> {
                int bno = ReadOne();
                Board selectedBoard = dao.selectOne(bno);
                System.out.println(selectedBoard);
            }
            case 2 -> {
                List<Board> boardList = dao.selectAll();
                printBoardList(boardList);
            }
            default -> {
                System.out.println(":: Press 1 to Read one || Press 2 to Read all ::");
                boardMenu();
            }
        }
    }

    public int ReadOne() throws IOException {
        System.out.print("확인할 글 번호 입력 : ");
        int selectedBno = Integer.parseInt(input.readLine());
        return selectedBno;
    }

    public void DeleteMode() throws IOException {
        System.out.println("Delete mode :: \n :: Press 1 to Delete one || Press 2 to Delete all ::");
        int choice = Integer.parseInt(input.readLine());
        switch (choice) {
            case 1 -> {
                int bno = Deleteinput();
                dao.delete(bno);
            }
            case 2 -> dao.clear();
            default -> {
                System.out.println(":: Press 1 to Delete one || Press 2 to Delete all ::");
                boardMenu();
            }
        }
    }

    public int Deleteinput() throws IOException {
        System.out.print("삭제할 글 번호 입력 : ");
        int selectedBno = Integer.parseInt(input.readLine());
        return selectedBno;
    }

    public Board boardDataUpdate() throws IOException {
        Board board = new Board();
        System.out.println("UPDATE ::");
        System.out.print("수정할 글 번호 입력 : ");
        int bno = Integer.parseInt(input.readLine());
        board.setBno(bno);
        System.out.print("제목 입력 : ");
        String title = input.readLine();
        board.setBtitle(title);
        System.out.print("내용 입력 : ");
        String content = input.readLine();
        board.setBcontent(content);
        return board;
    }

    public void printBoardList(List<Board> boardList) {
        for (Board board : boardList) {
            System.out.println(board);
        }
    }
}
