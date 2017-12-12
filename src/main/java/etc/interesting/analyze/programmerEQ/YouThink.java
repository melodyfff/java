package etc.interesting.analyze.programmerEQ;

/**
 * What you think
 */
public class YouThink {
    static class Programmer{
        private final int EQ=0;
        public int getEQ() {
            return this.EQ;
        }
    }
    public static void main(String[] args) {
        Programmer you = new Programmer();
        System.out.println(you.EQ);
    }

}
