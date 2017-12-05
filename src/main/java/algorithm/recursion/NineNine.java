package algorithm.recursion;

/**
 * Nine Multiply Nine
 * 递归
 * @author Xin Chen
 * @date 2017/12/5 13:13
 */
public class NineNine {

    public static void multiply(int baseNumber) {
        if ((baseNumber - 1) > 0 || baseNumber == 1) {
            for (int i = baseNumber; (i - 1) > 0 || i == 1; i--) {
                System.out.printf("%d X %d = %d \t", i, baseNumber, baseNumber * (i));
            }
            System.out.println("\n");
            multiply(--baseNumber);
        }
    }

    public static void main(String[] args) {
        multiply(9);
    }

}
