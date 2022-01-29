import java.awt.*;
 
public class Game {
  /*
      These are constant values that you can access in any
      class file. If you try to access one of these constants
      outside of the Game class, then you will need to include
      "Game." before the variable reference. For example, to
      access PLAYER1_CHECKER_COLOR in the checker class, you would
      need to type this:
          Game.PLAYER1_CHECKER_COLOR
      If you only typed this in the Checkers class...
          PLAYER1_CHECKER_COLOR
      ...then Java would not know what you meant and throw an error.
  */
 
    public static final int BOARD_NUM_OF_ROWS = 8;
    public static final int BOARD_NUM_OF_COLS = 8;
    public static final double WIDTH_OF_SPACE = 1.0;
    public static final double HEIGHT_OF_SPACE = 1.0;
    public static final double HALF_WIDTH_OF_SPACE = WIDTH_OF_SPACE / 2.0;
    public static final double HALF_HEIGHT_OF_SPACE = HEIGHT_OF_SPACE / 2.0;
    public static final Color PRIMARY_SPACE_COLOR = StdDraw.WHITE;
    ;
    public static final Color SECONDARY_SPACE_COLOR = StdDraw.BLUE;
    public static final Color PLAYER1_CHECKER_COLOR = StdDraw.RED;
    public static final Color PLAYER2_CHECKER_COLOR = StdDraw.BLACK;
 
    private BoardSpace[][] boardSpaces;
    private boolean isTurnForPlayer1;
    private boolean redWin;
    private boolean blackWin;
 
    public Game() {
 
        boardSpaces = new BoardSpace[BOARD_NUM_OF_ROWS][BOARD_NUM_OF_COLS];
        isTurnForPlayer1 = true;
        redWin = false;
        blackWin = false;
 
        //create a new BoardSpace object, and store it in each spot of the grid
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                boolean isPrimaryColor = (row % 2) == (col % 2); //if both the row and col index values are even or both odd, then the board space should be
                boardSpaces[row][col] = new BoardSpace(isPrimaryColor);
            }
        }
 
        //Place Checkers in starting positions
        boolean checkerPlacedBelongsToPlayer1 = true;
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            if (row == 3) {
                row = row + 2; //skip middle two rows when placing checkers
                checkerPlacedBelongsToPlayer1 = false;
            }
            for (int col = 0; col < BOARD_NUM_OF_COLS; col = col + 2) {
                if (row % 2 == 1 && col == 0) { //on rows with an odd index, checkers need offset by 1 to the right
                    col++;
                }
                boardSpaces[row][col].placeChecker(new Checker(checkerPlacedBelongsToPlayer1));
            }
        }
        updateCheckersWithMovementOptions();
    }
 
    public void switchToNextPlayerTurn() {
        isTurnForPlayer1 = !isTurnForPlayer1;
    }
 
    //For each checker on the board, reset its movement options
    public void resetAllCheckerMovementOptions() {
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                Checker checker = boardSpaces[row][col].getChecker();
                if (checker != null) {
                    checker.resetMovementOptions();
                }
            }
        }
    }
 
    public void resetAllSpaceMarkings() {
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                boardSpaces[row][col].resetMarkings();
            }
        }
    }
 
    /*
    Questions to make sure you know the answers to:
        (a) Do we really need to clear out all the checker movement options and then remark them all again every single turn? If not, why do you think I took the approach I did?
        (b) How does the method resetAllCheckerMovementOptions() work?
        (c) How does the method switchToNextPlayerTurn() work? Does the game currently care whose turn it is?
        (d) Why do we call resetAllCheckerMovementOptions(), updateCheckersWithMovementOptions(), and switchToNextPlayerTurn()
        ONLY when a destination space is clicked?
     */
    public void setSpaceSelected(int rowClicked, int colClicked, int prevRowClicked, int prevColClicked) {
        BoardSpace spaceClicked = boardSpaces[rowClicked][colClicked];
        //If the user clicks on a space highlighted in yellow, then we know we need to move a checker. Otherwise, we know it is still the same player's turn and do what appears after this if statement.
        if (spaceClicked.getIsDestinationOption()) {
 
            BoardSpace spacePrevClicked = boardSpaces[prevRowClicked][prevColClicked];
            Checker checker = spacePrevClicked.pickUpChecker();
            spaceClicked.placeChecker(checker);
            if (checker.canJump()) {
                BoardSpace midSpace = boardSpaces[(rowClicked + prevRowClicked) / 2][(colClicked + prevColClicked) / 2];
                midSpace.removeCheckerFromGame();
            }
 
            //Clean-up for next turn
            resetAllSpaceMarkings(); //takes care of (2)
            resetAllCheckerMovementOptions(); //takes care of (3)
            switchToNextPlayerTurn();
            updateCheckersWithMovementOptions(); //takes care of (4)
            findWinner();
 
 
            return;
        }
        resetAllSpaceMarkings();
        spaceClicked.setIsSelected(true);
        Checker clickedChecker = spaceClicked.getChecker();
        if (clickedChecker != null && clickedChecker.canSlide()) {
            if (clickedChecker.getCanSlideUpLeft()) {
                BoardSpace destSpace = boardSpaces[rowClicked + 1][colClicked - 1];
                destSpace.markDestinationOption();
            }
            if (clickedChecker.getCanSlideUpRight()) {
                BoardSpace destSpace = boardSpaces[rowClicked + 1][colClicked + 1];
                destSpace.markDestinationOption();
            }
            if (clickedChecker.getCanSlideDownLeft()) {
                BoardSpace destSpace = boardSpaces[rowClicked - 1][colClicked - 1];
                destSpace.markDestinationOption();
            }
            if (clickedChecker.getCanSlideDownRight()) {
                BoardSpace destSpace = boardSpaces[rowClicked - 1][colClicked + 1];
                destSpace.markDestinationOption();
            }
        }
        if (clickedChecker != null && clickedChecker.canJump()) {
            if (clickedChecker.getCanJumpUpLeft()) {
                BoardSpace destSpace = boardSpaces[rowClicked + 2][colClicked - 2];
                destSpace.markDestinationOption();
            }
        }
        if (clickedChecker != null && clickedChecker.canJump()) {
            if (clickedChecker.getCanJumpUpRight()) {
                BoardSpace destSpace = boardSpaces[rowClicked + 2][colClicked + 2];
                destSpace.markDestinationOption();
            }
        }
        if (clickedChecker != null && clickedChecker.canJump()) {
            if (clickedChecker.getCanJumpDownLeft()) {
                BoardSpace destSpace = boardSpaces[rowClicked - 2][colClicked - 2];
                destSpace.markDestinationOption();
            }
        }
        if (clickedChecker != null && clickedChecker.canJump()) {
            if (clickedChecker.getCanJumpDownRight()) {
                BoardSpace destSpace = boardSpaces[rowClicked - 2][colClicked + 2];
                destSpace.markDestinationOption();
            }
        }
    }
 
    public void checkForKings() {
 
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row = row + 1) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
 
                Checker checker = boardSpaces[row][col].getChecker();
 
                if (checker != null) {
 
                    if ((checker.getBelongsToPlayer1() && row == (BOARD_NUM_OF_ROWS - 1)) || (!checker.getBelongsToPlayer1() && row == (0))) {
 
                        checker.setKing();
                    }
 
                }
 
            }
 
        }
 
    }
 
 
    public void updateCheckersWithMovementOptions() {
 
        checkForKings();
 
        updateCheckersWithPossibleJumps();
        boolean start = true;
        boolean foundJump = false;
 
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                Checker checker = boardSpaces[row][col].getChecker();
 
                if (checker != null) {
 
                    if ((isTurnForPlayer1 && checker.getBelongsToPlayer1()) || (!isTurnForPlayer1 && !checker.getBelongsToPlayer1())) {
 
                        if (checker.canJump()) {
 
                            foundJump = true;
                        }
                    }
 
                }
            }
 /* if black HAS to jump when available (can't do anythine else but eat on his turn)
            if (foundJump) {
 
                resetAllCheckerMovementOptions();
 
                updateCheckersWithPossibleJumps();
            } else {
 
                updateCheckersWithPossibleSlides();
            }*/
            if (start) {
 
                resetAllCheckerMovementOptions();
                updateCheckersWithPossibleSlides();
                updateCheckersWithPossibleJumps();
            }
 
        }
 
    }
 
    public boolean doesCheckerExistWithPossibleJump() {
        //double for loop
        return false;
    }
 
    public boolean doesCheckerExistWithPossibleSlide() {
        //nonsense
        return false;
    }
 
    public void updateCheckersWithPossibleJumps() {
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                Checker checker = boardSpaces[row][col].getChecker();
 
                boolean canJumpUpLeft = false;
                boolean canJumpUpRight = false;
                boolean canJumpDownRight = false;
                boolean canJumpDownLeft = false;
                if (checker != null) {
                    if (isTurnForPlayer1 && checker.getBelongsToPlayer1()) {
                        canJumpUpLeft = canCheckerJumpRelDir(checker, row, col, 1, -1);
                        canJumpUpRight = canCheckerJumpRelDir(checker, row, col, 1, 1);
 
                        if (checker.getIsKing()) {
                            canJumpDownLeft = canCheckerJumpRelDir(checker, row, col, -1, -1);
                            canJumpDownRight = canCheckerJumpRelDir(checker, row, col, -1, 1);
                        }
                    } else if (!isTurnForPlayer1 && !checker.getBelongsToPlayer1()) {
                        canJumpDownLeft = canCheckerJumpRelDir(checker, row, col, -1, -1);
                        canJumpDownRight = canCheckerJumpRelDir(checker, row, col, -1, 1);
 
                        if (checker.getIsKing()) {
                            canJumpUpLeft = canCheckerJumpRelDir(checker, row, col, 1, -1);
                            canJumpUpRight = canCheckerJumpRelDir(checker, row, col, 1, 1);
                        }
                    }
                    checker.setPossibleJumpDirections(canJumpUpLeft, canJumpUpRight, canJumpDownLeft, canJumpDownRight);
                }
            }
        }
 
 
    }
 
    public boolean canCheckerJumpRelDir(Checker checker, int startRow, int startCol, int rowDir, int colDir) {
        if (checker != null) {
            int destRow = startRow + (rowDir * 2);
            int destCol = startCol + (colDir * 2);
            boolean oppositeColor = false;
            if (isSpaceOnBoard(destRow, destCol)) {
                int midRow = startRow + rowDir;
                int midCol = startCol + colDir;
 
                Checker middleChecker = boardSpaces[midRow][midCol].getChecker();
 
                if ((middleChecker != null) && (checker.getBelongsToPlayer1() != middleChecker.getBelongsToPlayer1())) {
                    oppositeColor = true;
                }
                if ((boardSpaces[destRow][destCol].getChecker() == null) && oppositeColor) {
                    return true;
                } 
            }
        }
        return false;
    }
 
 
    public void updateCheckersWithPossibleSlides() {
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                Checker checker = boardSpaces[row][col].getChecker();
 
                boolean canSlideUpLeft = false;
                boolean canSlideUpRight = false;
                boolean canSlideDownRight = false;
                boolean canSlideDownLeft = false;
                if (checker != null) {
                    if (isTurnForPlayer1 && checker.getBelongsToPlayer1()) {
                        canSlideUpLeft = canCheckerSlideRelDir(checker, row, col, 1, -1);
                        canSlideUpRight = canCheckerSlideRelDir(checker, row, col, 1, 1);
 
                        if (checker.getIsKing()) {
                            canSlideDownLeft = canCheckerSlideRelDir(checker, row, col, -1, -1);
                            canSlideDownRight = canCheckerSlideRelDir(checker, row, col, -1, 1);
                        }
                    } else if (!isTurnForPlayer1 && !checker.getBelongsToPlayer1()) {
                        canSlideDownLeft = canCheckerSlideRelDir(checker, row, col, -1, -1);
                        canSlideDownRight = canCheckerSlideRelDir(checker, row, col, -1, 1);
 
                        if (checker.getIsKing()) {
                            canSlideUpLeft = canCheckerSlideRelDir(checker, row, col, 1, -1);
                            canSlideUpRight = canCheckerSlideRelDir(checker, row, col, 1, 1);
                        }
                    }
                    checker.setPossibleSlideDirections(canSlideUpLeft, canSlideUpRight, canSlideDownLeft, canSlideDownRight);
                }
            }
        }
    }
 
    public boolean canCheckerSlideRelDir(Checker checker, int startRow, int startCol, int rowDir, int colDir) {
        if (checker != null) {
            int destRow = startRow + rowDir;
            int destCol = startCol + colDir;
            if (isSpaceOnBoard(destRow, destCol)) {
                if (boardSpaces[destRow][destCol].getChecker() == null) {
                    return true;
                }
            }
        }
        return false;
    }
 
    public boolean getRedWin() {
        return redWin;
    }
 
    public boolean getBlackWin() {
        return blackWin;
    }
 
    public void findWinner() {
 
        boolean foundRedMove = false;
        boolean foundBlackMove = false;
 
        boolean foundRedChecker = false;
        boolean foundBlackChecker = false;
 
        for (int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                Checker checker = boardSpaces[row][col].getChecker();
                if (checker != null) {
                    if (checker.getBelongsToPlayer1()) {
                        foundRedChecker = true;
                        if (checker.canSlide() || checker.canJump()) {
                            foundRedMove = true;
                        }
                    }
                    if (!checker.getBelongsToPlayer1()) {
                        foundBlackChecker = true;
                        if (checker.canSlide() || checker.canJump()) {
                            foundBlackMove = true;
                        }
                    }
                }
            }
        }
        if (foundRedChecker && !foundBlackChecker) {
            redWin = true;
        } else if (!foundRedChecker && foundBlackChecker) {
            blackWin = true;
        } else if (!foundRedMove && isTurnForPlayer1) {
            blackWin = true;
        } else if (!foundBlackMove && !isTurnForPlayer1) {
            redWin = true;
        }
        if (getRedWin()) {
            System.out.println("Player 1 wins!");
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0, 0, 10, 10);
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.text(4, 4, "Player 1 wins!");
            StdDraw.show();
            StdDraw.pause(20000);
            System.exit(0);
        } else if (getBlackWin()) {
            System.out.println("Player 2  wins!");
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0, 0, 10, 10);
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.text(4, 4, "Player 2 wins!");
            StdDraw.show();
            StdDraw.pause(20000);
            System.exit(0);
        }
 
    }
 
 
    public static boolean isSpaceOnBoard(int rowNum, int colNum) {
        return (rowNum >= 0 && rowNum < Game.BOARD_NUM_OF_ROWS && colNum >= 0 && colNum < Game.BOARD_NUM_OF_COLS);
    }
 
    public void draw() {
        for (int row = 0; row < Game.BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < Game.BOARD_NUM_OF_COLS; col++) {
                boardSpaces[row][col].draw(row, col);
            }
        }
    }
}