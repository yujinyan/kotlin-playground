@SuppressWarnings("ALL")
public class StaticDemo {
    static class Foo {
        static int foo = loadFoo();

        static int loadFoo() {
            throw new IllegalStateException();
        }

        static class Bar {
            static int bar = 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(Foo.foo);
    }
}
