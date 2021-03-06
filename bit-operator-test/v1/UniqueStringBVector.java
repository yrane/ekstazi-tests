//Here the complexity is O(n) only but we are NOT using extra space
//To ask: Whether we have ASCII or Unicode String?

class UniqueStringBVector{
  public boolean match_string(String test_str)
  {
    //if (test_str.length() > 256)      //We can not check 256 as int is 32 bits
      //return false;                   //long only.
    int checker = 0;
    for (int i = 0; i < test_str.length(); i++)
    {
      int val = test_str.charAt(i) - 'a'; //Get value 0 to 25 to fill those bits

      if ((checker & (1 << val)) > 0){    //If bit already set which is checked
        return false;                     //by an (&)AND condition        
      }
      // This sets the bit at val position from right.
      // So for z, 25th bit from right is set in checker
      checker |= (1 << val);    // checker = checker |(or) val to set the bit
      //System.out.println(checker);
    }
    return true;
  }
}
