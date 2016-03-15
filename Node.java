
class Node<T> extends ANode<T> {
    T data;
    //basic constructor
    Node(T data) {
        super();
        this.data = data;   
    }
    //convenience constructor
    Node(T data, ANode<T> prev, ANode<T> next) {
        this.data = data;
        if (next == null || prev == null) {
            throw new IllegalArgumentException();
        } 
        else {
            this.next = next;
            this.next.setPrev(this);
            this.prev = prev;
            this.prev.setNext(this);
        }
    }
    //returns size of node
    int countSize() {
        return 1 + this.next.countSizeHelp();
    }
    //returns size of node 
    int countSizeHelp() {
        return 1 + this.next.countSizeHelp();
    }
    //returns size of node
    T remove() {
        this.prev.setNext(this.next);
        this.next.setPrev(this.prev);
        return this.data;
    }
    //should never be called 
    ANode<T> find(IPred<T> pred) {
        throw new RuntimeException("How did you get here?");
    }
    //helper for find
    ANode<T> findHelp(IPred<T> pred) {
        if (pred.apply(this.data)) {
            return this;
        } 
        else {
            return this.next.findHelp(pred);
        }
    }
    //checks if this node's data is the same as that data
    boolean sameNode(T t) {
        return t == this.data;
    }
    //helper for same node
    void removeNodeHelp(ANode<T> n, Boolean check) {
        if (n.sameNode(this.data)) {
            this.remove();
        } 
        else {
            this.next.removeNodeHelp(n, check);
        }
    }
}