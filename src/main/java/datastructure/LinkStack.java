package datastructure;
/**   
* @Description: 链表实现栈
* @author xinchen 2016年9月4日 下午3:13:43   
*/
public class LinkStack<T> {
	private class Node<T>{
		//节点数据
		private T data;
		//下个节点
		private Node next;
		
		public Node(){
			
		}
		//初始化全部属性
		public Node(T data,Node next){
			this.data = data;
			this.next = next;
		}
	}
	//栈顶
	private Node top;
	//栈中节点数
	private int size;
	public LinkStack(){
		top = null;//空栈，值为null
	}
	//以指定数据元素创建栈，该链栈只有一个元素
	public LinkStack(T element){
		top = new Node(element,null);
		size++;
	}
	
	//返回链栈长度
	public int length(){
		return size;
	}
	//进栈
	public void push(T element){
		top = new Node(element,top);
		size++;
	}
	//出栈
	public T pop(){
		Node oldTop = top;
		top = top.next;
		oldTop.next = null;
		size--;
		return (T) oldTop.data;
	}
	//访问栈顶，但不删除
	public T peek(){
		return (T) top.data;
	}
	
	public boolean empty(){
		return size == 0;
	}
	
	public void clear(){
		//将底层数组所有元素赋为null
		top = null;
		size = 0;
	}
	
	public String toString()  
    {  
        //链栈为空链栈时  
        if (empty())  
        {  
            return "[]";  
        }  
        else  
        {  
            StringBuilder sb = new StringBuilder("[");  
            for (Node current = top ; current != null  
                ; current = current.next )  
            {  
                sb.append(current.data.toString() + ", ");  
            }  
            int len = sb.length();  
            return sb.delete(len - 2 , len).append("]").toString();  
        }  
    }  
	
	public static void main(String[] args){
		LinkStack stack = new LinkStack();
		stack.push(1);
		stack.push("OK");
		stack.push(1.1);
		
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
	}
}
