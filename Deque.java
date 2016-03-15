//represents a double ended queue
class Deque<T> {
    Sentinel<T> header;
    //constructor
    Deque() {
        this.header = new Sentinel<T>();
    }
    //constructor
    Deque(Sentinel<T> header) {
        this.header = header;
    }
    //returns size of deque
    int size() {
        return header.countSize();
    }
    //adds an item at beginning of list
    void addAtHead(T t) {
        this.header.addAtHead(t);
    }
    //adds an item at end of list
    void addAtTail(T t) {
        this.header.addAtTail(t);
    }
    //removes an item from beginning of list
    T removeFromHead() {
        return this.header.removeFromHead();
    }
    //removes an item from end of list
    T removeFromTail() {
        return this.header.removeFromTail();
    }
    //finds an item in this list based on given predicate
    ANode<T> find(IPred<T> pred) {
        return this.header.find(pred);
    }
    //removes a node with given data from this deque
    void removeNode(ANode<T> n) {
        this.header.removeNodeHelp(n, false);
    }
}