import java.awt.*;
 
public class BoardSpace {
 
    private Checker checker;
    private boolean isPrimarySpaceColor;
    private boolean isSelected;
    private boolean isDestinationOption; //is the space a destination for the checker currently clicked?
    private Color color;
 
    public BoardSpace(boolean isPrimarySpaceColor){
        this.isPrimarySpaceColor = isPrimarySpaceColor;
        checker = null;
        color = isPrimarySpaceColor ? Game.PRIMARY_SPACE_COLOR : Game.SECONDARY_SPACE_COLOR;
        resetMarkings();
    }
 
    public void resetMarkings(){
        isSelected = false;
        isDestinationOption = false;
    }
 
    public Checker getChecker(){
        return checker;
    }
 
    public boolean hasChecker(){
        if(checker != null){
            return true;
        }
        return false;
    }
 
    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }
 
    public void placeChecker(Checker checker){
        this.checker = checker;
    }
 
    public Checker pickUpChecker(){
        Checker tempChecker = checker;
        checker = null;
        return tempChecker;
    }
 
    public void removeCheckerFromGame(){
        checker = null;
    }
 
    public void markDestinationOption() {
        this.isDestinationOption = true;
    }
 
    public boolean getIsDestinationOption(){
        return isDestinationOption;
    }
 
    public void draw(int row, int col){
        StdDraw.setPenColor(this.color);
        double xPos       = col + Game.HALF_WIDTH_OF_SPACE;
        double yPos       = row + Game.HALF_HEIGHT_OF_SPACE;
        double halfWidth  =       Game.HALF_WIDTH_OF_SPACE;
        double halfHeight =       Game.HALF_HEIGHT_OF_SPACE;
 
        //Color space slightly darker if it is currently selected
        if(isSelected){
            StdDraw.setPenColor(this.color.darker());
        }
 
        //If the checker on this BoardSpace can be moved, color the whole box magenta if clicked on
        if( isSelected && (checker != null) && (checker.canJump() || checker.canSlide()) ){
            StdDraw.setPenColor(StdDraw.MAGENTA);
        }
 
        //If this space is a possibility for where a currently selected checker could move, outline the BoardSpace in magenta
        if(isDestinationOption){
            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.filledRectangle(xPos, yPos, halfWidth, halfHeight);
            StdDraw.setPenColor(this.color);
            StdDraw.filledRectangle(xPos, yPos, halfWidth*0.9, halfHeight*0.9);
        }else{ //Otherwise, just draw the BoardSpace
            StdDraw.filledRectangle(xPos, yPos, halfWidth, halfHeight);
        }
 
        if(checker!=null){
            checker.draw(row, col);
        }
 
    }
}