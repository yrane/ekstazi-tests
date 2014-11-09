class Node{
  Node next = null;
  int data;

  public Node(int d){
    data = d;
  }

  void appendNode(int d){
    Node new_node = new Node(d);
    Node current = this;
    while (current.next != null){
      current = current.next;
    }
    current.next = new_node;  
  }
}
