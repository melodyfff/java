package datastructure;

import java.util.ArrayDeque;
import java.util.Queue;

/**   
* @Description: 两个队列实现一个栈  (顺序队列)
* 将1、2、3依次入队列一， 然后最上面的3留在队列一，
* 将下面的2、3入队列二，将3出队列一，此时队列一空了，
* 然后把队列二中的所有数据入队列一；将最上面的2留在队列一，将下面的3入队列二
* 依次循环。
* @author xinchen 2016年9月4日 下午5:56:01   
*/

public class TwoQueueAsStack {
	Queue<Integer> queue1 = new ArrayDeque<Integer>();
	Queue<Integer> queue2 = new ArrayDeque<Integer>();
	
	public void push(int data){
		queue1.add(data);
	}
	
	public int pop() throws Exception{
		int data;
		if(queue1.size() == 0){
				throw new Exception("栈为空");
		}
		//当queue1中有元素存在时
		while (queue1.size() != 0){
			//当queue1只剩一个元素时，出栈
			if(queue1.size() == 1){
				data = queue1.poll();
				//判断是不是最好将queue2中的元素放入queue1中
				while(queue2.size() != 0){
					queue1.add(queue2.poll());
					//return data; //此处return不能终止while循环，效果和下面相同
				}
				return data;
			}
			queue2.add(queue1.poll());
		}
		throw new Exception("栈为空");//此处data没有初始化，若初始化data并返回可替代此句
	}
	
	public static void main(String[] args) throws Exception{
		long star = System.currentTimeMillis();
		TwoStackAsQueue stack = new TwoStackAsQueue();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		long end = System.currentTimeMillis();
		System.out.println(end - star);
	}
}
