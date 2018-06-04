package io.file;

import java.io.File;
import java.io.FilenameFilter;

/**   
 * @Title: DirList.java 
 * @Package io
 * @Description:  实现目录列表器
 * @author xinchen
 * @date 2016年8月13日 下午3:02:08 
 * @version  
 */
public class DirList {

	public static void main(String[] args) {
		try{
			//获取根项目所有文件夹dir的名字    /	获取上级目录
			File path = new File(".");
			String[] list;
			System.out.println("args lenght:"+args.length);
			if(args.length == 0){
				list = path.list();
			}else{
				//此处内部类要改成静态，不然会报错
				list = path.list(new DirFilter(args[0]));
			}
			for (int i = 0; i < list.length; i++) {
				//循环输出文件夹dir的名字
				System.out.println(list[i]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	


	/**
	 * 	 此处如果写成class DirFilter implements FilenameFilter{}
	 * 静态方法中直接调用动态内部类会报这样错误:
	 * No enclosing instance of type DirList is accessible.
	 * Must qualify the allocation with an enclosing i
	 */
	public static class DirFilter implements FilenameFilter{
		String afn;
		
		DirFilter(String afn){
			this.afn = afn;
		}
		
		@Override
		public boolean accept(File dir, String name) {
			String f = new File(name).getName();
			return f.indexOf(afn) != -1;
		}
		
	}

}
