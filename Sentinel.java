class Sentinel<T> extends ANode<T> {
    //empty constructor
    Sentinel() {
        this.next = this;
        this.prev = this;
    }
    //returns this deques's size
    int countSize() {
        return this.next.countSizeHelp();
    }
    //helper for countSize()
    int countSizeHelp() {
        return 0;
    }
    //adds given item at the beginning of this list
    void addAtHead(T t) {
        new Node<T>(t, this, this.next);
    }
    //adds given item at the end of this list
    void addAtTail(T t) {
        new Node<T>(t, this.prev, this);
    }
    //removes item from beginning of this list
    T removeFromHead() {
        if (this.next == this) {
            throw new RuntimeException("This list is already empty!");
        } 
        else {
            return this.next.remove();
        }
    }
    //removes item from end of this list
    T removeFromTail() {
        return this.prev.remove();
    }
    //removes item from this list
    T remove() {
        throw new RuntimeException("This list is already empty!");
    }
    //finds item in this list based on given pred
    ANode<T> find(IPred<T> pred) {
        return this.next.findHelp(pred);
    }
    //helper for find
    ANode<T> findHelp(IPred<T> pred) {
        return this;
    }
    //helper for removeNode
    void removeNodeHelp(ANode<T> n, Boolean check) {
        if (!check) {
            this.next.removeNodeHelp(n, true);
        }
    } 
    //checks if this node's data is the same as given data
    boolean sameNode(T that) {
        return false;
    }
}
