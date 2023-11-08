public class Sample {
    public void method(int num){
        if (num < 0) return;
        System.out.println("A");
        return;
        //System.out.println("B");//到達不可能なコード
    }
}
