package IO.file;

/**
 * @Description:
 * @author xinchen
 */
public class TestMain {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("main() don't have any args!");
			return;
		}
		System.out.println("mian() args include:");
		for (int i = 0; i < args.length; i++) {
			System.out.println("arg" + (i + 1) + "value:" + args[i]);
		}

	}

}
