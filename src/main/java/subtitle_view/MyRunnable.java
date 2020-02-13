package subtitle_view;

public class MyRunnable implements Runnable {
    volatile boolean condition = false;
    String apple = "apple";
    String peach = "peach";
    String word = apple;

    public void run() {

        while (true) {

            System.out.println(apple);

            if (!word.equals(apple)) {
                System.out.println(word);
            }

            word = peach;

        }
    }
    public static void main(String args[]) {
        final MyRunnable r = new MyRunnable();
        new Thread(r).start();
        System.out.println("first\n");
        r.condition = true;
    }
}