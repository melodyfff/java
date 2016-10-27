package algorithm;

/**
 * 网易内推2017笔试题
 * Created by ChenXin on 2016/10/27.
 */

import java.util.Scanner;

/**
你就是一个画家！你现在想绘制一幅画，但是你现在没有足够颜色的颜料。
为了让问题简单，我们用正整数表示不同颜色的颜料。
你知道这幅画需要的n种颜色的颜料，你现在可以去商店购买一些颜料，
但是商店不能保证能供应所有颜色的颜料，所以你需要自己混合一些颜料。
混合两种不一样的颜色A和颜色B颜料可以产生(A XOR B)这种颜色的颜料(新产生的颜料也可以用作继续混合产生新的颜色,XOR表示异或操作)。
本着勤俭节约的精神，你想购买更少的颜料就满足要求，所以兼职程序员的你需要编程来计算出最少需要购买几种颜色的颜料？
 */

public class Painter {
    public int getColorNum(int[] arr){
        int k = 0;
        int n = arr.length, col = 0;
        int bitArr[][] = getBitArray(arr);

        //高斯消元
        for(col = 0; col < 32 && k < n; col++, k++){
            int i = 0;
            for(i = k; i < n && bitArr[i][col] == 0; i++);//找到col列的第一个非0元素
            if(i == n){
                k--;
                continue;
            }
            bitArr = changeLine(bitArr, k, i);
            for(int j = i + 1; j < n; j++){
                if(bitArr[j][col] == 0){
                    continue;
                }
                for(int p = col; p < 32; p++){
                    bitArr[j][p] ^= bitArr[k][p]; //消元
                }
            }
        }
        return k;
    }

    //改变两行的位置
    public int[][] changeLine(int[][] arr, int m, int n){
        for(int i = 0; i < arr[0].length; i++){
            int temp = arr[m][i];
            arr[m][i] = arr[n][i];
            arr[n][i] = temp;
        }
        return arr;
    }

    //计算二进制矩阵
    public int[][] getBitArray(int[] arr){
        int n = arr.length;
        int bitArr[][] = new int[n][32];
        for(int i = 0; i < n; i++){
            for(int j = 31; j >= 0 && arr[i] > 0; j--){
                bitArr[i][j] = arr[i] & 1;
                arr[i] >>= 1;
            }
        }
        return bitArr;
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int n = sc.nextInt();
            int arr[] = new int[n];
            for(int i = 0; i < n; i++){
                arr[i] = sc.nextInt();
            }
            int k = new Painter().getColorNum(arr);
            System.out.println(k);
        }
        sc.close();
    }
}
