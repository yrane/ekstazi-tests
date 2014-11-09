class DeleteNode{

  Node deleteNode(Node head, int d){
    // Node del_node = new Node(d);
    Node current = head;

    if (current.d == d){
      return head.next; //Head deleted
    }

    while (current.next != null){
      if (current.next.data == d){
        current.next = current.next.next;
        return head;   //head is intact
      }
      current = current.next;
    }
    return head;
  }
}
