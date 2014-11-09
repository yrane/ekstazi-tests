package package1;
public class NewClass {
  public void check_again(int x, int y) {
    if (x > 500) {
      throw new IllegalArgumentException("X should be less than 500");
    }
  }
}
