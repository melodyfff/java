package datastructure;
/**   
* @Description: 
* @author xinchen 2016年8月30日 下午11:45:16   
*/
public class SequenceListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SequenceList a = new SequenceList<>();
		a.add(1);
		a.add(2);
		a.insert("s", 1);
		a.delete(2);
		System.out.println(a.size());
		System.out.println(a.toString());
	}

}
