package etc.interesting.analyze.programmerEQ;
/**
 * The real
 */
public class Actually {
    static class Programmer{
        private int EQ;
        public int getEQ() {
            return this.EQ;
        }
        public void setEQ(int EQ) {
            this.EQ=EQ;
        }
    }
    public static void main(String[] args) {
        Programmer you = new Programmer();
        you.setEQ(0);
        System.out.println(you.EQ);
    }
}
