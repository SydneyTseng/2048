package TwentyFortyEight;

public class Cell {
    private int value;
    public boolean hovered = false;
    //private int x;
    //private int y;

    //public Cell(int value, int x, int y) {

    //}
    public Cell(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public void setValue(int value){
        this.value = value;
    }

}