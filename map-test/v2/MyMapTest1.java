import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MyMapTest1 {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testStandardMap() {
    // Standard Map
    Map<String, Integer> map = new HashMap<String, Integer>();
    map.put("Pranjal", 1);
    map.put("Student", 2);
    map.put("Pranjal", 3);
    assertEquals(map.get("Pranjal"), (Integer)3);

    for (int i = 0; i < 10; i++) {
      map.put(String.valueOf(i), i);
    }
    assertEquals(map.size(), 12);

    assertEquals(map.get("7"), (Integer)7);
    map.keySet();
  }
} 
