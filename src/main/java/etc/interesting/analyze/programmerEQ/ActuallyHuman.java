package etc.interesting.analyze.programmerEQ;


/**
 * The real in human analyze
 */
public class ActuallyHuman {
    static class Human{
        private int EQ;
        Human(){
            this.EQ=(int)(Math.random()*300);
        }
        public int getEQ(){
            return this.EQ;
        }
        public void setEQ(int newEQ){
            this.EQ=newEQ;
        }
    }
    static class Programmer extends Human{
        Programmer(){
            super();
        }
        Programmer(int EQ){
            super();
            super.setEQ(EQ);
        }
        public void work(int days){
            //Do nothing with EQ
        }
    }
    public static void main(String[] args) {
        Programmer you = new Programmer(1);
        System.out.println("您成为了一个程序员，您的初始EQ："+you.getEQ());
        you.work(365);
        System.out.println("经历了365天的工作，您现在的EQ是："+you.getEQ());

        Programmer xm = new Programmer();
        System.out.println("小明成为了一个程序员，他的初始EQ："+xm.getEQ());
        you.work(365);
        System.out.println("经历了365天的工作，他现在的EQ是："+xm.getEQ());
    }
}
