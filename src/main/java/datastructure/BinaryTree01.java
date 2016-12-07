package datastructure;

public class BinaryTree01 {
     
	
	public static void preOrder(BinaryTree root){   //先序遍历
		if(root != null){
			System.out.print(root.data+" ");
			preOrder(root.left);
			preOrder(root.right);
		}
	}
	
	public static void inOrder(BinaryTree root){   //中序遍历
		if(root != null){
			inOrder(root.left);
			System.out.print(root.data+" ");
			inOrder(root.right);
		}
	}
	
	public static void postOrder(BinaryTree root){ //后序遍历 
		if(root !=null){
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.data+" ");
		}
	}
	
	public static void main(String[] args) {
		int[] array = {9,6,10,3,12,1};
		
		BinaryTree root=new BinaryTree(array[0]); //创建二叉树
		
		for (int i = 1; i < array.length; i++) {
			root.insert(root, array[i]);          //向二叉树中插入数据
		}
		
		System.out.println("共有"+array.length+"个数字");
		
		System.out.println("先序：");
		preOrder(root);
		System.out.println();
		
		System.out.println("中序：");
		inOrder(root);
		System.out.println();
		
		System.out.println("后序：");
		postOrder(root);
		System.out.println();
		

	}

}
