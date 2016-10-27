package Serialize;

import java.io.Serializable;

/**
 * @Description:
 * @author xinchen 2016年9月16日 下午4:36:30
 */
public class Employee implements Serializable {
	public String name;
	public String address;
	public transient int SSN;
	public int number;

	public void mailCheck() {
		System.out.println("Mailing a check to " + name + " " + address);
	}
}
