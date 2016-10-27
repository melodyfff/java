package datastructure;
/**   
* @Description: 队列的链式存储结构及实现
* @author xinchen 2016年9月4日 下午5:10:32   
*/
public class LinkQueue <T>{
	//定义一个内部类Node，Node实例代表链队列的节点
	private class Node{
		//保存节点数据
		private T data;
		//指向下个节点
		private Node next;
		
		public Node(){
			
		}
		public Node(T data,Node next){
			this.data = data;
			this.next = next;
		}
	}
	
	//保存队列头尾节点
	private Node front;
	private Node rear;
	//保存队列中的节点数
	private int size;
	
	//创建空链队列
	public LinkQueue(){
		//空链队列，front和rear都是null  
		front = null;
		rear = null;
	}
	
	/** 
	* 以指定数据元素来创建链队列，该链队列只有一个元素
	* @param element 
	*/
	public LinkQueue(T element){
		front = new Node(element,null);
		//只有一个节点，front、rear都指向该节点  
		rear = front;
		size ++;
	}
	/** 
	* 返回队列长度
	* @return  int     
	*/
	public int length(){
		return size;
	}
	/** 
	* 入队
	* @param element         
	*/
	public void add(T element){
		if(front == null){
			front = new Node(element,null);
			rear = front;
		}else{
			//创建新节点
			Node newNode = new Node(element,null);
			//让尾节点的next指向新增的节点
			rear.next = newNode;
			//以新节点作为新的尾节点  
			rear = newNode;
		}
		size++;
	}
	/** 
	* 删除front端节点 
	* @return   T     
	*/
	public T remove(){
		Node oldFront = front;
		front = front.next;
		oldFront.next = null;
		size--;
		return oldFront.data;
	}
	/** 
	* 访问末端节点值 
	* @return  T      
	*/
	public T last(){
		return rear.data;
	}
	/** 
	* 判断是否为空
	* @return   boolean     
	*/
	public boolean empty(){
		return size == 0;
	}
	/** 
	* 清空节点      
	*/
	public void clear(){
		//将front、rear两个节点赋为null  
		front = null;
		rear = null;
		size = 0;
	}
	public String toString()  
    {  
        //链队列为空链队列时  
        if (empty())  
        {  
            return "[]";  
        }  
        else  
        {  
            StringBuilder sb = new StringBuilder("[");  
            for (Node current = front ; current != null  
                ; current = current.next )  
            {  
                sb.append(current.data.toString() + ", ");  
            }  
            int len = sb.length();  
            return sb.delete(len - 2 , len).append("]").toString();  
        }  
    }  
	
	public static void main(String[] args){
		LinkQueue queue = new LinkQueue();
		queue.add("test");
		queue.add("test");
		queue.add("test");
		
		System.out.println(queue.length());
		
		System.out.println(queue.remove());
		System.out.println(queue.remove());
		System.out.println(queue.remove());
	}
}
