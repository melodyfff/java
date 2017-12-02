package io.iostream;/**
* @Description: 
* @author xinchen 2016年10月17日 下午10:36:42   
*/
//Shorthand class for opening an input file

import java.io.*;

public class InFile extends DataInputStream {
	public InFile(String filename) throws FileNotFoundException {
		super(new BufferedInputStream(new FileInputStream(filename)));
	}

	public InFile(File file) throws FileNotFoundException {
		this(file.getPath());
	}
}
