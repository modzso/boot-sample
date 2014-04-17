package parser;

public class CharBuffer {

    private static final int BUFFER_INCREMENT = 64;
    private static final int BUFFER_INITIAL_SIZE = 128;
    private char[] buffer = new char[BUFFER_INITIAL_SIZE];
    private int index = 0;

    public void add(char c) {
        if (index + 1 >= buffer.length) {
            char[] newBuffer = new char[buffer.length + BUFFER_INCREMENT];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
        buffer[index++] = c;
    }

    @Override
    public String toString() {
        return new String(buffer, 0, index);
    }

    public void reset() {
        buffer = new char[BUFFER_INITIAL_SIZE];
        index = 0;
    }
}
