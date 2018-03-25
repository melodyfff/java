package io.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author xinchen 2016年10月17日 下午10:20:47
 * @description: 获取当前目录文件结构
 */
public class DirList3 {

    private final static Logger LOGGER = LoggerFactory.getLogger(DirList3.class);

    //main()现在的自变量是 final
    public static void main(final String[] args) {
        try {
            File path = new File(".");
            String[] list;
            if (args.length == 0) {
                list = path.list();
            } else {
                list = path.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String n) {
                        String f = new File(n).getName();
                        return f.indexOf(args[0]) != -1;
                    }
                });
            }

            for (int i = 0; i < list.length; i++) {
                LOGGER.info(list[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
