public class ThreadMain
        implements Runnable {

        public int count;
        public Thread worker;

        public ThreadMain(int count) {
            this.count = count;
            worker = new Thread(this);
            worker.start();
        }

        public void run() {
            try {
                Thread.sleep(count);
                System.out.println(
                "Delayed Hello World");

            } catch(InterruptedException e) {
                e.printStackTrace();
        }
    }
}
