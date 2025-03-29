package TwentyFortyEight;

public class Cell {
    private int value;
    public boolean hovered = false;
    public int nowX;
    public int nowY;
    public int targetX;
    public int targetY;
    public static int speed = 45;

    //public Cell(int value, int x, int y) {

    //}
    public Cell(int value, int nowX, int nowY, int targetX, int targetY){
        this.value = value;
        this.nowX = nowX;
        this.nowY = nowY;
        this.targetX = targetX;
        this.targetY = targetY;
    }
    public int getValue(){
        return this.value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void move(){
        if (nowX != targetX){
            if (nowX < targetX){
                if (nowX < targetX - speed){
                    nowX += speed;
                }
                else{
                    nowX = targetX;
                }
            }
            else{
                if (nowX > targetX + speed){
                    nowX -= speed;
                }
                else{
                    nowX = targetX;
                }
            }

        }
        if (nowY != targetY){
            if (nowY < targetY){
                if (nowY < targetY - speed){
                    nowY += speed;
                }
                else{
                    nowY = targetY;
                }
            }
            else{
                if (nowY > targetY + speed){
                    nowY -= speed;
                }
                else{
                    nowY = targetY;
                }
                
            }
        }
    
    }


}