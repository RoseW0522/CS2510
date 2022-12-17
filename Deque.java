import java.util.function.Predicate;
import tester.Tester;

// predicate that tests of two strings are the same
class SameString implements Predicate<String> {
  String s;
  
  SameString(String s) {
    this.s = s;
  }
  
  public boolean test(String that) {
    return this.s.equals(that);
  }
}

// represent a list that can add things on at both ends
class Deque<T> {
  Sentinel<T> header;
  
  // constructor for Deque
  Deque() {
    this.header = new Sentinel<T>();
  }
  
  // constructor for Deque takes in Sentinel
  Deque(Sentinel<T> header) {
    this.header = header;
  }
  
  //count the number of nodes in a list Deque
  int size() {
    return this.header.size();
  }
  
  // EFFECT: insert T to the front of the list
  void addAtHead(T t) {
    this.header.addAtHead(t);
  }
  
  // EFFECT: insert T to the tail of the list
  void addAtTail(T t) {
    this.header.addAtTail(t);
  }
  
  // EFFECT: remove the first node from this Deque
  T removeFromHead() {
    return this.header.removeFromHead();
  }
  
  // EFFECT: remove the last node from this Deque
  T removeFromTail() {
    return this.header.removeFromTail();
  }
  
  // takes an IPred<T> and produces the first node
  // in this Deque for which the given predicate returns true
  ANode<T> find(Predicate<T> pred) {
    return this.header.find(pred);
  }
  
  // remove the given node from this Deque
  void removeNode(ANode<T> n) {
    n.removeNodeHelper();
  }
}

// abstract class for deque nodes
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;
  
  // constructor for deque nodes
  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }
  
  // constructor for deque nodes with no arguments
  ANode() {
    this.next = null;
    this.prev = null;
  }
  
  // EFFECT: calculate the size of the this linked list of nodes
  abstract int sizeHelper();
  
  // EFFECT: remove this node from ANode
  abstract T removeHelper();
  
  // takes an IPred<T> and produces the first node
  // in this ANode for which the given predicate returns true
  abstract ANode<T> findHelper(Predicate<T> pred);
  
  // remove the node in ANode class
  abstract void removeNodeHelper();
}

// a sentinel of a list
class Sentinel<T> extends ANode<T> {
  
  // constructor of sentinel
  Sentinel() {
    this.next = this;
    this.prev = this;
  }
  
  // count the number of nodes in the Deque list
  int size() {
    return this.next.sizeHelper();
  }
  
  // count the number of nodes in the sentinel
  int sizeHelper() {
    return 0;
  }
  
  // EFFECT: add note at the head of sentinel
  void addAtHead(T t) {
    new Node<T>(t, this.next, this);
  }
  
  // EFFECT: add note at the tail of sentinel
  void addAtTail(T t) {
    new Node<T>(t, this, this.prev);
  }
  
  // EFFECT: remove the Head of the node from the sentinel
  T removeFromHead() {
    return this.next.removeHelper();
  }
  
  // EFFECT: remove the tail of the node from the sentinel
  T removeFromTail() {
    return this.prev.removeHelper();
  }
  
  // EFFECT: remove the node from A Sentinel
  T removeHelper() {
    throw new RuntimeException("Can't try to remove on a Sentinel!");
  }
  
  // takes an IPred<T> and produces the first node
  // in this Sentinel for which the given predicate returns true
  ANode<T> find(Predicate<T> pred) {
    return this.next.findHelper(pred);
  }
  
  // takes an IPred<T> and produces the first node
  // in this Sentinel for which the given predicate returns true
  ANode<T> findHelper(Predicate<T> pred) {
    return this;
  }
  
  // remove the given node in Sentinel class
  void removeNodeHelper() {
    return;
  }
}

// a node inside a deque
class Node<T> extends ANode<T> {
  T data;
  
  // node constructor
  Node(T data) {
    super(null, null);
    this.data = data;
  }
  
  // node constructor
  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    this.next = next;
    this.prev = prev;
    if (next == null || prev == null) {
      throw new IllegalArgumentException("You cannot make a node pointing to null objects!");
    }
    else {
      this.next.prev = this;
      this.prev.next = this;
    }
  }
  
  // count the number of nodes in the Node
  int sizeHelper() {
    return 1 + this.next.sizeHelper();
  }
  
  // EFFECT: remove the node from Node
  T removeHelper() {
    prev.next = this.next;
    next.prev = this.prev;
    return this.data;
  }
  
  // takes an IPred<T> and produces the first node
  // in this Node for which the given predicate returns true
  ANode<T> findHelper(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.findHelper(pred);
    }
  }
  
  // remove the given node in Node class
  void removeNodeHelper() {
    this.removeHelper();
  }
}

class ExamplesDeque {
  
  // examples
  Sentinel<String> sent1;
  ANode<String> nodeA;
  ANode<String> nodeB;
  ANode<String> nodeC;
  ANode<String> nodeD;
  Deque<String> deque1;
  
  Sentinel<String> sent2;
  ANode<Integer> node1;
  ANode<Integer> node2;
  ANode<Integer> node3;
  ANode<Integer> node4;
  Deque<String> deque2;
  
  Sentinel<Integer> sent3;
  Sentinel<String> sentTest;
  
  Deque<Integer> deque3;
  Deque<String> dequeTest;
  
  // initialize the Deque lists
  void initData() {
    sent1 = new Sentinel<String>();
    sent2 = new Sentinel<String>();
    nodeA = new Node<String>("abcde", sent2, sent2);
    nodeB = new Node<String>("bcdef", sent2, nodeA);
    nodeC = new Node<String>("cdefg", sent2, nodeB);
    nodeD = new Node<String>("defgh", sent2, nodeC);
    
    sent3 = new Sentinel<Integer>();
    node1 = new Node<Integer>(1, sent3, sent3);
    node2 = new Node<Integer>(2, sent3, node1);
    node3 = new Node<Integer>(3, sent3, node2);
    node4 = new Node<Integer>(4, sent3, node3);
    
    deque1 = new Deque<String>();
    deque2 = new Deque<String>(this.sent2);
    deque3 = new Deque<Integer>(this.sent3);
    
    sentTest = new Sentinel<String>();
    dequeTest = new Deque<String>(this.sentTest);
  }
  
  void testNew(Tester t) {
    this.initData();
    t.checkExpect(this.deque2.header, this.sent2);
    t.checkExpect(this.nodeA.prev, this.sent2);
    t.checkExpect(this.nodeB.next, this.nodeC);
    t.checkExpect(this.nodeC.prev, this.nodeA.next);
    t.checkExpect(this.nodeD == null, false);
    t.checkExpect(this.sent1.prev, this.sent1);
    t.checkExpect(this.sent1.next.next, this.sent1);
    t.checkExpect(this.sent2.next, this.nodeA);
    t.checkExpect(this.sent2.prev, this.nodeD);
  }
  
  // test the method size
  void testSize(Tester t) {
    this.initData();
    t.checkExpect(this.sent2.size(), 4);
    t.checkExpect(this.sent1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque3.size(), 4);
  }
  
  ANode<String> constructNullNode() {
    return new Node<String>("", new Node<String>(""), null);
  }
  
  void testConstructingNullNodes(Tester t) {
    t.checkException(new IllegalArgumentException(
        "You cannot make a node pointing to null objects!"), this, "constructNullNode");
  }
  
  // test the method addAtHead
  void testAddAtHead(Tester t) {
    initData();
    t.checkExpect(this.sent2.next, this.nodeA);
    t.checkExpect(this.sent1.next, this.sent1);
    
    this.deque1.addAtHead("defgh");
    this.deque1.addAtHead("cdefg");
    this.deque1.addAtHead("bcdef");
    this.deque1.addAtHead("abcde");
    t.checkExpect(deque1, deque2);
  }
  
  // test the method addAdTail
  void testAddAtTail(Tester t) {
    initData();
    this.dequeTest.addAtTail("abcde");
    this.dequeTest.addAtTail("bcdef");
    this.dequeTest.addAtTail("cdefg");
    this.dequeTest.addAtTail("defgh");
    t.checkExpect(this.dequeTest, this.deque2);
  }
  
  // test the method removeFromHead
  void testRemoveFromHead(Tester t) {
    initData();
    this.sent2.removeFromHead();
    this.sent2.removeFromHead();
    this.sent2.removeFromHead();
    this.sent2.removeFromHead();
    t.checkExpect(this.sent2, this.sent1);
    t.checkException(new IllegalArgumentException(
        "You cannot make a node pointing to null objects!"),
        this, "constructNullNode");
    
    initData();
    this.deque2.removeFromHead();
    t.checkExpect(this.deque2.header.next, this.nodeB);
    t.checkExpect(this.deque2.header.prev, this.nodeD);
  }
  
  // test the method removeFromTail
  void testRemoveFromTail(Tester t) {
    initData();
    this.sent2.removeFromTail();
    t.checkExpect(this.sent2.next, this.nodeA);
    t.checkExpect(this.sent2.prev, this.nodeC);
    t.checkException(new RuntimeException(
        "Can't try to remove on a Sentinel!"), sent1, "removeFromTail");
    
    initData();
    this.deque2.removeFromTail();
    t.checkExpect(this.deque2.header.next, this.nodeA);
  }

  // test the method find
  void testFind(Tester t) {
    initData();
    t.checkExpect(this.deque2.find(new SameString("abcde")), this.sent2.next);
    t.checkExpect(this.deque2.find(new SameString("apple")), this.sent2);
    t.checkExpect(this.deque1.find(new SameString("null")), this.sent1);
  }
  
  // test the method removeNode
  void testRemoveNode(Tester t) {
    initData();
    this.deque2.removeNode(nodeB);
    t.checkExpect(this.deque2.header.next.next, this.nodeC);
    
    this.deque1.removeNode(nodeB);
    t.checkExpect(this.deque1.header, sent1);
    
    initData();
    this.sent1.removeNodeHelper();
    t.checkExpect(sent1.next, sent1);
    
    initData();
    this.deque1.addAtHead("defgh");
    this.deque1.addAtHead("bcdef");
    this.deque1.addAtHead("abcde");
    deque2.removeNode(nodeC);
    t.checkExpect(deque2, this.deque1);
  }
}