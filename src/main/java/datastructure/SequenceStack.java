package datastructure;
/**   
* @Description: 顺序表实现栈
* @author xinchen 2016年9月4日 下午2:28:56   
*/
public class SequenceStack {
	//存放栈内的元素数组
	private Object[] elementData;
	//记录栈内元素个数
	private int size = 0;
	//栈的容量增长大小
	private int capacityIncrement;
	
	//以指定初始化容量创建栈
	public SequenceStack(int initcapacity){
		elementData = new Object[initcapacity];
	}
	public SequenceStack(int initcapacity,int capacityIncrement){
		//this()让构造方法调用同一个类的另一个构造方法初始化一个栈
		this(initcapacity);
		this.capacityIncrement = capacityIncrement;
	}
	
	//确保底层数组容量能容纳栈内所有元素
	public void ensureCapacity(){
		//增加容量
		if(elementData.length == size){
				Object[] oldElements = elementData;
				int newLength = 0;
				//已经设置capacityIncrement
				if(capacityIncrement > 0){
					newLength = elementData.length + capacityIncrement;
				}else{
					//将长度扩充到原来的1.5倍    double to int
					newLength = (int) (elementData.length * 1.5);
				}
				elementData = new Object[newLength];
				//将原数组的元素复制到新数组中
				System.arraycopy(oldElements, 0, elementData, 0, size);
		}
	}
	
	//向栈顶压入一个元素，入栈
	public void push(Object object){
		ensureCapacity();
		elementData[size++] = object;
//		elementData[size] = object;
//		size++;
	}
	//出栈
	public Object pop(){
		if(size == 0){
			throw new RuntimeException("空栈异常");
		}
		return elementData[--size];
	}
	//获得栈内元素个数
	public int size(){
		return size;
	}
	
	public static void main(String[] args){
		SequenceStack stack = new SequenceStack(10);
		//向栈顶压入10个元素
		for(int i = 0;i < 10;i++){
			stack.push("元素" + i);
			System.out.println("元素" + i + "入栈" );
		}
		
		//依次弹出10个元素
		for(int i = 0;i < 10;i++){
			System.out.println(stack.pop()+"出栈");
		}
	}
	
}
