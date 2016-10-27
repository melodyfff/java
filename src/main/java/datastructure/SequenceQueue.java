package datastructure;

import java.util.Arrays;

/**   
* @Description: 队列的顺序存储结构及实现
* 				队列是一种特殊的线性表，
* 				它只允许在表的前端(front)进行删除操作，
* 				只允许在表的后端（rear）进行插入操作。
* @author xinchen 2016年9月4日 下午3:33:24   
*/
public class SequenceQueue<T> {
	private int DEFAULT_SIZE = 10;//默认大小
	
	//数组长度
	private int capacity;
	//定义一个数组保存顺序队列元素
	private Object[] elementData;
	private int front = 0;//队首
	private int rear = 0;//队尾
	
	//以默认长度创建队列
	public SequenceQueue(){
		capacity = DEFAULT_SIZE;
		elementData = new Object[capacity];
	}
	/** 
	* 以一个初始化元素来创建队列
	* @param element 
	*/
	public SequenceQueue(T element){
		//调用本类的默认构造函数
		this();
		elementData[0] = element;
		rear++;
	}
	
	/** 
	* 以指定长度的数组来创建顺序队列
	* @param element
	* @param initsize 
	*/
	public SequenceQueue(T element,int initsize){
		this.capacity = initsize;
		elementData = new Object[capacity];
		elementData[0] = element;
		rear++;
	}
	
	/** 
	* 获取顺序队列大小   
	*/
	public int length(){
		return rear - front;
	}
	
	/** 
	* @Description: 插入队列
	* @param element         
	* @throws IndexOutOfBoundsException
	*/
	public void add(T element){
		if(rear > capacity -1){
			throw new IndexOutOfBoundsException("队列已满异常");
		}
		elementData[rear++] = element;
	}
	
	/** 
	* @Description: 移除队列       
	* @throws IndexOutOfBoundsException
	*/
	public T remove(){
		if(empty()){
			throw new IndexOutOfBoundsException("空队列异常");
		}
		T oldData = (T) elementData[front]; 
		elementData[front++] = null;
		return oldData;
	}
	
	/** 
	* 判断队列是否为空   
	* @return boolean     
	*/
	public boolean empty(){
		return rear == front;
	}
	/** 
	* @Description: 返回队列顶部元素，不删除
	* @throws IndexOutOfBoundsException
	*/
	public T element(){
		if(empty()){
			throw new IndexOutOfBoundsException("空队列异常");
		}
		return (T) elementData[front];
	}
	/** 
	* 清空顺序队列
	*/
	public void clear(){
		//将数组元素赋值为null
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
            StringBuilder sb = new StringBuilder("[");  
            for (int i = front  ; i < rear ; i++ )  
            {  
                sb.append(elementData[i].toString() + ", ");  
            }  
            int len = sb.length();  
            return sb.delete(len - 2 , len).append("]").toString();  
        }  
    }  
	
	public static void main (String[] args){
		SequenceQueue queue = new SequenceQueue();
		queue.add(1);
		queue.add("OK");
		queue.add(1.11);
		
		System.out.println(queue.remove());
		System.out.println(queue.remove());
		System.out.println(queue.remove());
	}
}
