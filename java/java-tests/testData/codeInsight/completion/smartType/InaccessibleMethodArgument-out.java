class B {
    void test() {
        int xxx = 2;

        A.foo(xxx);<caret>
    }
}

class A {
    private static void foo(int x){} //foo is private
}