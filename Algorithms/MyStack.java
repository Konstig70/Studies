package Demot;

public class MyStack {
    private int[] stack; //Normi taulukko toimii pohjana
    private int maxSize;
    private int top = -1; //kertoo mones alkio on ylin -1 tarkoittaa tyhjää

    /// Konstruktori
    public MyStack(int count) {
        stack = new int[count];
        maxSize = count;
    }

    /// Jos käyttäjä ei anna pinolle määrää niin laitetaan sille arvo 10
    public MyStack() {
        //stack = new int[10];
        maxSize = 10;
    }

    public void push(int x) {
        top++;
        stack[top] = x;
    }

    public int pop() {
        int x = stack[top];
        stack[top] = 0;
        top--;
        return x;
    }

    public int size() { //Julkinen getteri :((((((((
        return top + 1;
    } //julkinen getteri :(

    public int MaxSize(){
        return maxSize;
    }

    public boolean isEmpty() {
        return top == -1; //Voisi tehdä eritavalla, mutta koska top saa arvon -1 vain jos stack on tyhjä
    }

    public int top() {
        return stack[top];
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack(5);
        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }
        int siirrettävä = stack.pop();
        int[] loput = new int[2];
        for (int i = 0; i < 2; i++) {
            loput[i] = stack.pop();
        }
        stack.push(siirrettävä);
        for (int i = 1; i >= 0; i--) {
            stack.push(loput[i]);
        }
        for (int i = 0; i < stack.MaxSize(); i++) {
            System.out.println(stack.pop());
        }

    }
}


