//represents a node of a double ended queue
abstract class ANode<T> {
    ANode<T> next;
    ANode<T> prev;
    //sets the prev field to given node
    void setPrev(ANode<T> prev) {
        this.prev = prev;
    }
    //sets the next field to given node
    void setNext(ANode<T> next) {
        this.next = next;
    }
    abstract int countSize();
    abstract int countSizeHelp();
    abstract T remove();
    abstract ANode<T> find(IPred<T> pred);
    abstract ANode<T> findHelp(IPred<T> pred);
    abstract void removeNodeHelp(ANode<T> n, Boolean check);
    abstract boolean sameNode(T that);
}