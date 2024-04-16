import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    private static final BlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {
        Thread generatorThread = new Thread(() -> {
            String letters = "abc";
            for (int i = 0; i < 10000; i++) {
                String text = generateText(letters, 100000);
                try {
                    queueA.put(text);
                    queueB.put(text);
                    queueC.put(text);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadA = new Thread(() -> {
            int maxCountA = 0;
            String maxTextA = "";
            while (true) {
                try {
                    String text = queueA.take();
                    int countA = countChar(text, 'a');
                    if (countA > maxCountA) {
                        maxCountA = countA;
                        maxTextA = text;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(() -> {
            int maxCountB = 0;
            String maxTextB = "";
            while (true) {
                try {
                    String text = queueB.take();
                    int countB = countChar(text, 'b');
                    if (countB > maxCountB) {
                        maxCountB = countB;
                        maxTextB = text;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadC = new Thread(() -> {
            int maxCountC = 0;
            String maxTextC = "";
            while (true) {
                try {
                    String text = queueC.take();
                    int countC = countChar(text, 'c');
                    if (countC > maxCountC) {
                        maxCountC = countC;
                        maxTextC = text;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        generatorThread.start();
        threadA.start();
        threadB.start();
        threadC.start();
    }

    public static String generateText(String letters, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(letters.length());
            sb.append(letters.charAt(index));
        }

        return sb.toString();
    }

    public static int countChar(String text, char ch) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }
}
