package datastructure;

import java.util.Arrays;

/**   
* @Description: 用数组实现线性表
* @author xinchen 2016年8月30日 下午10:13:37   
*/
public class SequenceList<T> {
	//默认长度
	private int DEFAULT_SIZE = 20;
	//保存数组长度
	private int capacity;
	//定义一个数组用于保存线性表元素
	private Object[] elementData;
	//保存当前元素个数
	private int size = 0;
	
	/** 
	* Description: 以默认长度创建空线性表
	*/
	public SequenceList(){
		capacity = DEFAULT_SIZE;
		elementData = new Object[capacity];
	}
	/** 
	* Description: 以一个初始化元素创建线性表
	*/
	public SequenceList(T element){
		this();
		elementData[0] = element;
		size++;
	}
	/** 
	* Description: 以指定长度的数组来创建线性表
	* @param element 指定顺序表中的第一个元素
	* @param initsize 指定线性表底层数组长度
	*/
	public SequenceList(T element,int initsize){
		capacity = 1;
		//把capacity设为大于initSize的最小的2的n次方  
		while(capacity < initsize){
			//此处是左移操作，转换为2进制补0  
			//左移n位相当于求 2的n次方
			//如    x<<=n  相当于  x*2^n
			capacity <<= 1;//左移1位
		}
		elementData = new Object[capacity];
		elementData[0] = element;
		size++;
	}
	/** 
	* @Description: 获取当前线性表大小 
	* @return int  
	*/
	public int length(){
		return size;
	}
	/** 
	* @Description: 根据index查找线性表中指定元素
	* @param int 
	* @return int  
	*/
	public T get(int i){
		if(i < 0 || i > size - 1){
			throw new IndexOutOfBoundsException("线性表索引越界");
		}
		return (T)elementData[i];
	}
	/**  
	* @Description: 查找指定元素索引
	* @param element    
	* @return int
	*/
	public int locate(T element){
		for(int i = 0;i < size;i++){
			if(elementData[i].equals(element)){
				return i;
			}
		}
		return -1;
	}
	/** 
	* @Description: 
	* @param  element
	* @param  index     
	* @return IndexOutOfBoundsException    
	* @throws 
	*/
	public void insert(T element,int index){
		if(index < 0 || index > size){
			throw new IndexOutOfBoundsException("线性表索引越界");
		}
		ensureCapacity(size + 1); 
		//从index之后的所有元素向后移动一个
		//比如： int[] fun ={0,1,2,3,4,5,6}; System.arraycopy(fun,0,fun,3,3); 则结果为：{0,1,2,0,1,2,6};
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}
	//数组长度扩容
	public void ensureCapacity(int minCapacity){
		//如果原有数组长度小于目前所需要的数组长度
		if(minCapacity > capacity){
			//不断地将capacity * 2，直到capacity大于minCapacity为止  
			while(capacity < minCapacity){
				capacity <<= 1;
			}
			//JDK6新增方法 Arrays.copyOf
			//int[] arr1 = {1, 2, 3, 4, 5}; 
			//int[] arr2 = Arrays.copyOf(arr1, 10);
			//arr2 : 1 2 3 4 5 0 0 0 0 0
			elementData = Arrays.copyOf(elementData, capacity);
		}
	}
	
	/** 
	* @Description: 新增一个元素
	* @param element         
	*/
	public void add(T element){
		insert(element,size);
	}
	
	/** 
	* @Description: 删除线性表中指定索引处的元素
	* @param  index    
	* @return T     
	* @throws IndexOutOfBoundsException
	*/
	public T delete(int index){
		if(index < 0||index >size){
			throw new IndexOutOfBoundsException("线性表索引越界");
		}
		T oldValue = (T)elementData[index];
		int numMoved = size - index -1 ;
		if(numMoved > 0){
			 System.arraycopy(elementData,index+1,elementData,index,numMoved);  
		}
		//清空最后一个元素
		elementData[--size] = null;
		return oldValue;
	}
	
	/** 
	* @Description: 删除最后一个元素    
	* @return T     
	*/
	public T removeLast(){
		return delete(size - 1);
	}
	/** 
	* @Description: 判断是否为空
	* @return boolean     
	*/
	public boolean empty(){
		return size == 0;
	}
	public int size(){
		return size;
	}
	/** 
	* @Description: 清空线性表     
	*/
	public void clear(){
		Arrays.fill(elementData, null);
		size = 0;
	}
	public String toString(){
		if(size == 0){
			return "[]";
		}else{
			String str = "[ ";
			for(int i = 0;i < size;i++){
				str = str + elementData[i] + " ";
			}
			return str+"]";
		}
	}
	
	
	
}
