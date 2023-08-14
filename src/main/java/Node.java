public class Node<T> {
    public static void main (String args[]){
        Node<String> x = new Node<String>("Hello",null);
        Node<String> y = new Node<String>("World", x);
        System.out.println(y.getNext().getData());
        System.out.println(y.getData());
    }
    private T data;
    private Node<T>  next;
    public Node(T d, Node<T>  n){
        data = d;
        next = n;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNext(Node<T>   next) {
        this.next = next;
    }
}
