public class MyClass {
  public int multiply(int x, int y) {
    // the following is just an example
    return x / y;
}
  
  public void check(int x, int y) {
    if (x > 999) {
      throw new IllegalArgumentException("X should be less than 1000");
    }
  }
}
