import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author GYSHGX868
 */
public class Main {
  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    OutputWriter out = new OutputWriter(outputStream);
    TaskD solver = new TaskD();
    solver.solve(1, in, out);
    out.close();
  }

  static class TaskD {
    private long[] factorial;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
      int h = in.nextInt();
      int w = in.nextInt();
      int a = in.nextInt();
      int b = in.nextInt();
      factorial = IntegerUtils.generateFactorial(h + w + 1, MiscUtils.MOD7);
      long answer = 0;
      for (int i = b + 1; i <= w; i++) {
        long value = binomialCoefficients(w - i + a - 1, w - i);
        long coef = binomialCoefficients(i - 1 + h - a - 1, h - a - 1);
        answer = (answer + coef * value % MiscUtils.MOD7) % MiscUtils.MOD7;
      }
      out.printLine(answer);
      // long corner = IntegerUtils.binomialCoefficient(a + 2, b, MiscUtils.MOD7);
    }

    private long binomialCoefficients(int n, int m) {
      long a = factorial[n];
      long b = (factorial[m] * factorial[n - m]) % MiscUtils.MOD7;
      return (a * IntegerUtils.power(b, MiscUtils.MOD7 - 2, MiscUtils.MOD7)) % MiscUtils.MOD7;
    }

  }

  static class InputReader {
    private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    private InputReader.SpaceCharFilter filter;

    public InputReader(InputStream stream) {
      this.stream = stream;
    }

    public int read() {
      if (numChars == -1) {
        throw new InputMismatchException();
      }
      if (curChar >= numChars) {
        curChar = 0;
        try {
          numChars = stream.read(buf);
        } catch (IOException e) {
          throw new InputMismatchException();
        }
        if (numChars <= 0) {
          return -1;
        }
      }
      return buf[curChar++];
    }

    public int nextInt() {
      int c = read();
      while (isSpaceChar(c)) {
        c = read();
      }
      int sgn = 1;
      if (c == '-') {
        sgn = -1;
        c = read();
      }
      int res = 0;
      do {
        if (c < '0' || c > '9') {
          throw new InputMismatchException();
        }
        res *= 10;
        res += c - '0';
        c = read();
      } while (!isSpaceChar(c));
      return res * sgn;
    }

    public boolean isSpaceChar(int c) {
      if (filter != null) {
        return filter.isSpaceChar(c);
      }
      return isWhitespace(c);
    }

    public static boolean isWhitespace(int c) {
      return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public interface SpaceCharFilter {
      boolean isSpaceChar(int ch);

    }

  }

  static class IntegerUtils {
    private IntegerUtils() {
    }
    
    public static long power(long base, long exponent, long mod) {
      if (base >= mod) {
        base %= mod;
      }
      if (exponent == 0) {
        return 1 % mod;
      }
      long result = power(base, exponent >> 1, mod);
      result = result * result % mod;
      if ((exponent & 1) != 0) {
        result = result * base % mod;
      }
      return result;
    }

    public static long[] generateFactorial(int count, long module) {
      long[] result = new long[count];
      if (module == -1) {
        if (count != 0) {
          result[0] = 1;
        }
        for (int i = 1; i < count; i++) {
          result[i] = result[i - 1] * i;
        }
      } else {
        if (count != 0) {
          result[0] = 1 % module;
        }
        for (int i = 1; i < count; i++) {
          result[i] = (result[i - 1] * i) % module;
        }
      }
      return result;
    }

  }

  static class MiscUtils {
    public static final int MOD7 = (int) (1e9 + 7);

    private MiscUtils() {
    }

  }

  static class OutputWriter {
    private final PrintWriter writer;

    public OutputWriter(OutputStream outputStream) {
      writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public OutputWriter(Writer writer) {
      this.writer = new PrintWriter(writer);
    }

    public void close() {
      writer.close();
    }

    public void printLine(long i) {
      writer.println(i);
    }

  }
}

