public class Main {
    public static void main(String[] args){
        Sample s = new Sample();
        String val = s.setValue("hello");
        s.setValue();
        System.out.println(val);

    }
    
}
