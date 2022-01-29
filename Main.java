public class Main {
    //for documentation on StdDraw Library: https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html

    private static Game game;
    private static boolean mouseDownDetected;
    private static int xAtMouseDown;
    private static int yAtMouseDown;
    private static int xAtMouseUp;
    private static int yAtMouseUp;


    public static void main(String[] args) {

        setDefaultScale();
        mouseDownDetected = false;

        game = new Game();
        while(true){
            StdDraw.enableDoubleBuffering();
            processMouseInput();
            draw();
            StdDraw.show();
            StdDraw.pause(50);
        }


    }

    public static void setDefaultScale(){
        StdDraw.setXscale(0, Game.BOARD_NUM_OF_COLS);
        StdDraw.setYscale(0, Game.BOARD_NUM_OF_ROWS);
    }

    private static int prevRowClicked = -1;
    private static int prevColClicked = -1;

    public static void setSpaceClicked(int rowClicked, int colClicked){
        game.setSpaceSelected(rowClicked, colClicked, prevRowClicked, prevColClicked);
        prevRowClicked = rowClicked;
        prevColClicked = colClicked;
    }

    public static void processMouseInput(){
        if(!mouseDownDetected && StdDraw.isMousePressed()){
            mouseDownDetected = true;
            xAtMouseDown = (int) StdDraw.mouseX();
            yAtMouseDown = (int) StdDraw.mouseY();
        }else if(mouseDownDetected && !StdDraw.isMousePressed()){
            mouseDownDetected = false;
            xAtMouseUp = (int) StdDraw.mouseX();
            yAtMouseUp = (int) StdDraw.mouseY();
            if(xAtMouseDown == xAtMouseUp && yAtMouseDown == yAtMouseUp){
                setSpaceClicked(yAtMouseUp, xAtMouseUp);
            }

        }
    }

    public static void draw() {
        game.draw();
    }

}
