package Test;

public class Test01 {
    //함수형 인터페이스 Runnable의 있는 단 하나의 추상메드 run()을 오버라이딩 한 것

    public static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                while (true){
                    Thread.sleep(500);
                    System.out.println("Hello");
                }
            }catch (Exception e){
                System.out.println("Intterupt ");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("lamda Test");
    }


}
