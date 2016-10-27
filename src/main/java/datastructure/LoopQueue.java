package datastructure;

import java.util.Arrays;

/**   
* @Description: 
* @author xinchen 2016年9月4日 下午4:15:55   
*/
public class LoopQueue<T> {
	private int DEFAULT_SIZE = 10;//默认长度
	
	//数组容量
	private int capacity;
	//定义数组存储循环队列中的元素
	private Object[] elementData;
	
	private int front = 0;//队首
	private int rear = 0;//队尾
	
	//创建默认长度队列
	public LoopQueue(){
		capacity = DEFAULT_SIZE;
		elementData = new Object[capacity];
	}
	public LoopQueue(T element){
		//调用本类的默认构造函数初始化队列
		this();
		elementData[0] = element;
		rear++;
	}
	/** 
	 * 以指定长度初始化队列
	* @param element
	* @param initSize 
	*/
	public LoopQueue(T element,int initSize){
		this.capacity = initSize;
		elementData = new Object[capacity];
		elementData[0] = element;
		rear++;
	}
	
	/** 
	* @Description: 循环队列获取队列长度
	* @return int     
	*/
	public int length(){
		if(empty()){
			return 0;
		}
		//循环队列获取长度
		return rear > front ? rear - front : capacity - (front - rear);
	}
	
	/** 
	* @Description: 入队
	* @param element       
	* @throws IndexOutOfBoundsException
	*/
	public void add(T element){
		if(rear == front && elementData[front] != null){
			throw new IndexOutOfBoundsException("队列已满异常");
		}
		elementData[rear++] = element;
		//如果rear已经到头，等于数组容量 capacity，转头
		rear = rear == capacity ? 0 : rear;
	}
	
	/** 
	* @Description: 出队
	* @return   T
	* @throws IndexOutOfBoundsException
	*/
	public T remove(){
		if(empty()){
			throw new IndexOutOfBoundsException("空队列异常");
		}
		//保留队列front端元素
		T oldData = (T) elementData[front];
		//释放队列的front端元素
		elementData[front++] = null;
		//如果front已经到头，那就转头  
		front = front == capacity ? 0 : front;
		return oldData;
	}
	/** 
	* @Description: 返回队顶元素
	* @return  T   
	* @throws IndexOutOfBoundsException
	*/
	public T element(){
		if(empty()){
			throw new IndexOutOfBoundsException("空队列异常");
		}
		return (T) elementData[front];
	}
	/**    
	* 判断是否为空
	*/
	public boolean empty(){
		//rear==front且rear处的元素为null  
		return rear == front && elementData[rear] == null;
	}
	
	/** 
	* @Description: 清空循环队列       
	*/
	public void clear(){
		//将队列元素赋值为null
		Arrays.fill(elementData, null);
		front = 0;
		rear = 0;
	}
	public String toString()  
    {  
        if (empty())  
        {  
            return "[]";  
        }  
        else  
        {  
            //如果front < rear，有效元素就是front到rear之间的元素  
            if (front < rear)  
            {  
                StringBuilder sb = new StringBuilder("[");  
                for (int i = front  ; i < rear ; i++ )  
                {  
                    sb.append(elementData[i].toString() + ", ");  
                }  
                int len = sb.length();  
                return sb.delete(len - 2 , len).append("]").toString();  
            }  
            //如果front >= rear，有效元素为front->capacity之间、0->front之间的  
            else  
            {  
                StringBuilder sb = new StringBuilder("[");  
                for (int i = front  ; i < capacity ; i++ )  
                {  
                    sb.append(elementData[i].toString() + ", ");  
                }  
                for (int i = 0 ; i < rear ; i++)  
                {  
                    sb.append(elementData[i].toString() + ", ");  
                }  
                int len = sb.length();  
                return sb.delete(len - 2 , len).append("]").toString();  
            }  
        }  
    }  
	
	public static void main(String[] args){
		LoopQueue queue = new LoopQueue();
		for(int i = 0;i < 10;i++){
			queue.add("test");
			
	
			System.out.println("front位置"+queue.front);
			System.out.println("rear位置"+queue.rear);
		}
		
			System.out.println("此时队列长度"+queue.length());
		for(int i = 0;i < 10;i++){
			System.out.println(queue.remove());
		}
			
			
		
	}
	
}
