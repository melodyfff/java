package etc.interesting.analyze.programmerEQ;

/**
 * What you think in human analyze
 */
public class YouThinkHuman {
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
        //EQ随着工作时间的增长呈下降趋势
        public void work(int days){
            super.setEQ(super.getEQ()-days);
        }
    }
    public static void main(String[] args) {
        Programmer you = new Programmer();
        System.out.println("您成为了一个程序员，您的初始EQ："+you.getEQ());
        you.work(365);
        System.out.println("经历了365天的工作，您现在的EQ是："+you.getEQ());

        Programmer xm = new Programmer();
        System.out.println("小明成为了一个程序员，他的初始EQ："+xm.getEQ());
        xm.work(365);
        System.out.println("经历了365天的工作，他现在的EQ是："+xm.getEQ());
        //虽然小于0，但是大家都一样，都有这么一天的
    }
}