package IO.iostream;/**   
* @Description: 
* @author xinchen 2016年10月17日 下午10:37:36   
*/
//Shorthand class for opening an output file
//for data storage.

import java.io.*;

public class OutFile extends DataOutputStream {
	public OutFile(String filename) throws IOException {
		super(new BufferedOutputStream(new FileOutputStream(filename)));
	}

	public OutFile(File file) throws IOException {
		this(file.getPath());
	}
}
