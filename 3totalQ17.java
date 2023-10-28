public class Main {
    public static void main(String[] args){
        int total = 0;
        a:
        for (int i = 0; i < 5; i++) {
            b:
            for (int j = 0; j < 5; j++) {
                if (i % 2 ==2)
                    continue a;
                if (j > 3)
                    break b;
                total += j;
            }
        }
        System.out.println(total);//12
    }
}
