package jdbc_boards.view;

import jdbc_boards.Controller.BoardMenu;
import jdbc_boards.vo.Board;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BoardMenu boardMenu = new BoardMenu();  // 강한 결합
        try {
            boardMenu.boardMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
