/*
Wu Po Yu 
pw2440
My Stack uses native methods of a linklist to imitate a stack
*/

public class MyStack<T> {
    //instantiate a Linkedlist object
    private java.util.LinkedList<T> model;
    //constructor
    public MyStack(){
	model = new java.util.LinkedList<T>();
    }
    //push method, addfirst
    public void push(T t){
	model.addFirst(t); 
    }
    //pop method, remove top of the stack, return what is removed
    public T pop(){
	    T data = model.getFirst();
	    model.removeFirst();	
	    return data;
    }
    //return size
    public int getSize() {
	return model.size(); 
    }
    //see if stack is empty
    public boolean isEmpty(){
	return model.isEmpty();
    } 
    //return top element, but no popping
    public T peek(){
	T elem = model.getFirst();
	return elem;
    }
    
}