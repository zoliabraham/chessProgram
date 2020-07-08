package ChessGame;

public class Vector {
    public int x;
    public int y;

    public Vector(float x,float y) {
        this.x = Math.round(x);
        this.y = Math.round(y);
    }

    public Vector(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public static boolean inSquare(Vector v,Vector squareStart, Vector squareSize){
        if(     v.x>squareStart.x && v.x < squareStart.x + squareSize.x
             && v.y>squareStart.y && v.y < squareStart.y + squareSize.y)
            return true;
        return false;
    }

    public Vector reverseY() {
        return new Vector(x,-y);
    }

    public boolean isEqual(Vector vector) {
        return x == vector.x && y == vector.y;
    }

    public Vector muliply(int i) {
        return new Vector(x*i,y*i);
    }

    public Vector getDirection(){
        return new Vector(divide(x,Math.abs(x)),divide(y,Math.abs(y)));
    }

    private int divide(int numerator, int denominator){
        if(denominator==0)
            return 0;
        return numerator/denominator;
    }
}
