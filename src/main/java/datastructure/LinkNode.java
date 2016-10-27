package datastructure;

/**
 * @author vov
 * 定义结点类
 */
public class LinkNode {
   private int data;
   private LinkNode link;
   
/**
 * @param initialData 结点存储的数据类型
 * @param initialLink 下个结点的引用
 */
   
public LinkNode(int initialData,LinkNode initialLink){
	   this.data = initialData;
	   this.link = initialLink;
   }

public int getData() {
	return data;
  }

public void setData(int data) {
	this.data = data;
  }

public LinkNode getLink() {
	return link;
  }  

public void setLink(LinkNode link) {
	this.link = link;
  }
}
