package Serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Description:
 * @author xinchen 2016年9月16日 下午4:38:32
 */
public class SerializeDemo {
	public static void main(String[] args) {
		Employee e = new Employee();
		e.name = "Reyan Ali";
		e.address = "Phokka Kuan, Ambehta Peer";
		e.SSN = 11122333;
		e.number = 101;
		try {
			FileOutputStream fileOut = new FileOutputStream("d:\\employee.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(e);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in d:\\employee.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
}
