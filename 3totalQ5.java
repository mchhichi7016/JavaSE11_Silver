public class Main {
    public static void main(String[] args){
        int a = 1;
        for (int b = 0,total = 0; b <= 5; b++) {
            total = a * b;
        }
        System.out.println(total);//totalの使用は中括弧内に限られる。
    }
}
