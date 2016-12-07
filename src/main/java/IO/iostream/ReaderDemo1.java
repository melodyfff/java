package IO.iostream;

import java.io.*;

/**
 * Description：
 * Created by ChenXin on 2016/10/31.
 */
public class ReaderDemo1 {
    public static String read(String filepath) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        String encoding = "GBK";
        File file = new File(filepath);
        // 考虑到编码格式
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding);
        // 读取所有
        //BufferedReader bufferedReader = new BufferedReader(reader);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
        String lineTxt;
        while ((lineTxt = bufferedReader.readLine()) != null){
            stringBuffer.append(lineTxt+"\n");
        }
        reader.close();
        return stringBuffer.toString();
    }

    public static void main(String[] args) throws Exception {
        String filepath = ReaderDemo1.class.getClassLoader().getResource("docker.txt").getPath();
        System.out.println(read(filepath));
    }
}
