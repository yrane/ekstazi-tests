public class HelloWorldAnonymousClass {

    interface HelloWorld {
        public void greetSomeone(String someone);
    }
    public void sayHello() {

        class EnglishGreeting implements HelloWorld {
            String name = "world";
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hello " + name);
            }
        }
        HelloWorld englishGreeting = new EnglishGreeting();

        englishGreeting.greetsomeone("Yogesh");
    }
}
