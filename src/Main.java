import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

class Calc {
    String toCalc;
    String suffix;
    boolean hasDouble;
    HashMap<Character, Integer> hashMap = new HashMap<>();

    public Calc(String toCalc) {
        this.toCalc = toCalc;
        hashMap.put('+', 1);
        hashMap.put('-', 1);
        hashMap.put('*', 2);
        hashMap.put('/', 2);
        hashMap.put('%', 2);
        try {
            suffix = toSuffix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toSuffix() throws Exception {
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();

        if (toCalc.charAt(toCalc.length() - 1) == '=') {
            toCalc = toCalc.substring(0, toCalc.length() - 1);
        }

        outer:
        for (int i = 0; i < toCalc.length(); i++) {
            char c = toCalc.charAt(i);
            if(c == ' '){
                continue ;
            }
            if (c == '-') {
                if (i == 0) {
                    sb.append('0');
                    sb.append(' ');
                } else if (toCalc.charAt(i - 1) == '(') {
                    sb.append('0');
                    sb.append(' ');
                }
            }
            if ((c <= '9' && c >= '0') || c == '.') {
                while ((c <= '9' && c >= '0') || c == '.') {

                    if (c == '.') {
                        hasDouble = true;
                    }
                    sb.append(c);
                    i++;
                    if (i >= toCalc.length()) {

                        break outer;
                    }
                    c = toCalc.charAt(i);
                }
                sb.append(' ');
                c = toCalc.charAt(i);
                if(c == ' '){
                    continue;
                }
            }
            if (!((c <= '9' && c >= '0') || c == '.')) {
                if (stack.empty()) {
                    stack.push(c);
                } else if (c == '(') {
                    stack.push('(');
                } else if (c == ')') {
                    while (!stack.empty() && stack.peek() != '(') {
                        sb.append(stack.peek());
                        stack.pop();
                        sb.append(' ');
                        if (stack.empty()) {
                            throw new Exception();
                        }
                    }
                    stack.pop();
                } else if (hashMap.get(c) == 1) {
                    while (!stack.empty() && stack.peek() != '(' && hashMap.get(stack.peek()) >= 1) {
                        sb.append(stack.peek());
                        sb.append(' ');
                        stack.pop();
                    }
                    stack.push(c);
                } else if (hashMap.get(c) == 2) {
                    while (!stack.empty() && stack.peek() != '(' && hashMap.get(stack.peek()) >= 2) {
                        sb.append(stack.peek());
                        sb.append(' ');
                        stack.pop();
                    }
                    stack.push(c);

                }
            }

        }
        while (!stack.empty()) {
            sb.append(' ');
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    public String calcRes() throws Exception {
        if (hasDouble) {

            Stack<BigDecimal> nums = new Stack<BigDecimal>();
            String s = suffix;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if ((c <= '9' && c >= '0') || c == '.') {
                    StringBuilder temp = new StringBuilder();
                    while ((c <= '9' && c >= '0') || c == '.') {
                        temp.append(c);
                        i++;
                        c = s.charAt(i);
                    }
                    nums.push(new BigDecimal(temp.toString()));
                }
                if (c == '+') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    BigDecimal temp1 = nums.pop();
                    BigDecimal temp2 = nums.pop();
                    nums.push(temp2.add(temp1));
                }
                if (c == '-') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    BigDecimal temp1 = nums.pop();
                    BigDecimal temp2 = nums.pop();
                    nums.push(temp2.subtract(temp1));
                }
                if (c == '*') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    BigDecimal temp1 = nums.pop();
                    BigDecimal temp2 = nums.pop();
                    nums.push(temp2.multiply(temp1));
                }
                if (c == '/') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    BigDecimal temp1 = nums.pop();
                    BigDecimal temp2 = nums.pop();
                    if (temp1.compareTo(new BigDecimal("0")) == 0) {
                        throw new Exception();
                    }
                    nums.push(temp2.divide(temp1));
                }
                if (c == '%') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    BigDecimal temp1 = nums.pop();
                    BigDecimal temp2 = nums.pop();
                    if (temp1.compareTo(temp2) == 0) {
                        throw new Exception();
                    }
                    nums.push(temp2.divideAndRemainder(temp1)[1]);
                }
            }
            if (nums.size() != 1) {
                throw new Exception();
            }
            return nums.peek().toString();
        }
        else {
            Stack<Integer> nums = new Stack<Integer>();
            String s = suffix;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if ((c <= '9' && c >= '0') || c == '.') {
                    StringBuilder temp = new StringBuilder();
                    while ((c <= '9' && c >= '0') || c == '.') {
                        temp.append(c);
                        i++;
                        c = s.charAt(i);
                    }
                    nums.push(Integer.parseInt(temp.toString()));
                }
                if (c == '+') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    int temp1 = nums.pop();
                    int temp2 = nums.pop();
                    nums.push(temp2 + temp1);
                }
                if (c == '-') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    int temp1 = nums.pop();
                    int temp2 = nums.pop();
                    nums.push(temp2 - temp1);
                }
                if (c == '*') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    int temp1 = nums.pop();
                    int temp2 = nums.pop();
                    nums.push(temp2 * temp1);
                }
                if (c == '/') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    int temp1 = nums.pop();
                    int temp2 = nums.pop();
                    if (temp1 == 0) {
                        throw new Exception();
                    }
                    nums.push(temp2 / temp1);
                }
                if (c == '%') {
                    if (nums.size() < 2) {
                        throw new Exception();
                    }
                    int temp1 = nums.pop();
                    int temp2 = nums.pop();
                    if (temp1 == 0) {
                        throw new Exception();
                    }
                    nums.push(temp2 % temp1);
                }
            }
            if (nums.size() != 1) {
                throw new Exception();
            }
            return nums.peek().toString();
        }
    }

    public void printRes() throws Exception {
        System.out.println(calcRes());
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        Calc calc = new Calc(str);
        try {
            calc.printRes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
