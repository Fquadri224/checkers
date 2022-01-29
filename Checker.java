import java.awt.*;
 
public class Checker {
    private boolean belongsToPlayer1;
    private Color color;
 
    private boolean canJumpUpLeft;
    private boolean canJumpUpRight;
    private boolean canJumpDownLeft;
    private boolean canJumpDownRight;
    private boolean canSlideUpLeft;
    private boolean canSlideUpRight;
    private boolean canSlideDownLeft;
    private boolean canSlideDownRight;
    private boolean isKing;
 
    public Checker(boolean belongsToPlayer1){
        this.belongsToPlayer1 = belongsToPlayer1;
        color = belongsToPlayer1 ? Game.PLAYER1_CHECKER_COLOR : Game.PLAYER2_CHECKER_COLOR;
        resetMovementOptions();
        isKing = false;
    }
 
    public boolean canJump(){
        if(canJumpUpLeft || canJumpUpRight || canJumpDownLeft || canJumpDownRight){
            return true;
        }
        return false;
    }
 
    public boolean canSlide(){
        if(canSlideUpRight || canSlideUpLeft || canSlideDownRight || canSlideDownLeft){
            return true;
        }
        return false;
    }
 
    public void resetMovementOptions(){
        canJumpUpLeft = false;
        canJumpUpRight = false;
        canJumpDownLeft = false;
        canJumpDownRight = false;
        canSlideUpLeft = false;
        canSlideUpRight = false;
        canSlideDownLeft = false;
        canSlideDownRight = false;
    }
 
    public void setKing(){
 
        this.isKing = true;
    }
 
    public void setCanJumpUpLeft(boolean canJumpUpLeft){
        this.canJumpUpLeft = canJumpUpLeft;
    }
 
    public void setCanJumpUpRight(boolean canJumpUpRight){
        this.canJumpUpRight = canJumpUpRight;
    }
 
    public void setCanJumpDownLeft(boolean canJumpDownLeft){
        this.canJumpDownLeft = canJumpDownLeft;
    }
 
    public void setCanJumpDownRight(boolean canJumpDownRight){
        this.canJumpDownRight = canJumpDownRight;
    }
 
    public void setPossibleJumpDirections(boolean canJumpUpLeft, boolean canJumpUpRight, boolean canJumpDownLeft, boolean canJumpDownRight){
        this.canJumpUpLeft = canJumpUpLeft;
        this.canJumpUpRight = canJumpUpRight;
        this.canJumpDownLeft = canJumpDownLeft;
        this.canJumpDownRight = canJumpDownRight;
 
        if (isKing) {
        }
        else if (this.belongsToPlayer1 && !isKing) {
            this.canJumpDownLeft = false;
            this.canJumpDownRight = false;
        }
        else if (!this.belongsToPlayer1 && !isKing) {
            this.canJumpUpLeft = false;
            this.canJumpUpRight = false;
        }
 
    }
 
 
    public void setPossibleSlideDirections(boolean canSlideUpLeft, boolean canSlideUpRight, boolean canSlideDownLeft, boolean canSlideDownRight){
        this.canSlideUpLeft = canSlideUpLeft;
        this.canSlideUpRight = canSlideUpRight;
        this.canSlideDownLeft = canSlideDownLeft;
        this.canSlideDownRight = canSlideDownRight;
 
        if (isKing) {
        }
        else if (this.belongsToPlayer1 && !isKing) {
            this.canSlideDownLeft = false;
            this.canSlideDownRight = false;
        }
        else if (!this.belongsToPlayer1 && !isKing) {
            this.canSlideUpLeft = false;
            this.canSlideUpRight = false;
        }
    }
 
    public boolean getIsKing() {
 
        return this.isKing;
    }
 
    public boolean getBelongsToPlayer1() {
        return belongsToPlayer1;
    }
 
    public boolean getCanJumpUpLeft() {
        return canJumpUpLeft;
    }
 
    public boolean getCanJumpUpRight() {
        return canJumpUpRight;
    }
 
    public boolean getCanJumpDownLeft() {
        return canJumpDownLeft;
    }
 
    public boolean getCanJumpDownRight() {
        return canJumpDownRight;
    }
 
    public boolean getCanSlideUpLeft() {
        return canSlideUpLeft;
    }
 
    public boolean getCanSlideUpRight() {
        return canSlideUpRight;
    }
 
    public boolean getCanSlideDownLeft() {
        return canSlideDownLeft;
    }
 
    public boolean getCanSlideDownRight() {
        return canSlideDownRight;
    }
 
    public void draw(int rowIndex, int colIndex){
        if((canJump() || canSlide()) && this.belongsToPlayer1){
            StdDraw.setPenColor(StdDraw.PINK);
            StdDraw.filledCircle(colIndex+0.5, rowIndex+0.5, 0.50);
        }
        else if((canJump() || canSlide()) && !this.belongsToPlayer1){
            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            StdDraw.filledCircle(colIndex+0.5, rowIndex+0.5, 0.50);
        } 
 
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(colIndex+0.5, rowIndex+0.5, 0.37);
 
        if (getIsKing()) {
            StdDraw.setPenColor(StdDraw.CYAN);
            //StdDraw.filledCircle(right/left, how high/low, radisu size);
            /* num1
            StdDraw.filledCircle(colIndex+0.3, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.5, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.7, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.3, rowIndex+0.6, 0.04);
            StdDraw.filledCircle(colIndex+0.4, rowIndex+0.55, 0.04);
            StdDraw.filledCircle(colIndex+0.7, rowIndex+0.6, 0.04);
            StdDraw.filledCircle(colIndex+0.5, rowIndex+0.7, 0.04);
            StdDraw.filledCircle(colIndex+0.6, rowIndex+0.55, 0.04);
            StdDraw.filledCircle(colIndex+0.4, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.6, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.3, rowIndex+0.5, 0.04);
            StdDraw.filledCircle(colIndex+0.7, rowIndex+0.5, 0.04);
            StdDraw.filledCircle(colIndex+0.45, rowIndex+0.625, 0.04);
            StdDraw.filledCircle(colIndex+0.55, rowIndex+0.625, 0.04);
            */

            /* num2
            StdDraw.filledCircle(colIndex+0.25, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.5, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.75, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.25, rowIndex+0.6, 0.04);
            StdDraw.filledCircle(colIndex+0.4, rowIndex+0.55, 0.04);
            StdDraw.filledCircle(colIndex+0.75, rowIndex+0.6, 0.04);
            StdDraw.filledCircle(colIndex+0.5, rowIndex+0.7, 0.04);
            StdDraw.filledCircle(colIndex+0.6, rowIndex+0.55, 0.04);
            StdDraw.filledCircle(colIndex+0.4, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.6, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.25, rowIndex+0.5, 0.04);
            StdDraw.filledCircle(colIndex+0.75, rowIndex+0.5, 0.04);
            StdDraw.filledCircle(colIndex+0.45, rowIndex+0.625, 0.04);
            StdDraw.filledCircle(colIndex+0.55, rowIndex+0.625, 0.04);
            StdDraw.filledCircle(colIndex+0.32, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.32, rowIndex+0.575, 0.04);
            StdDraw.filledCircle(colIndex+0.67, rowIndex+0.4, 0.04);
            StdDraw.filledCircle(colIndex+0.67, rowIndex+0.575, 0.04);
            */
        }
    }
 
 
}