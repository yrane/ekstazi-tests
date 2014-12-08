public class Coordinates {
  private int x;
  private int y;

  public Coordinates(int x, int y) {
    this.x = y;
    this.y = x;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void print() {
    System.out.println("x=" + x + ", y=" + y);
  }
}
