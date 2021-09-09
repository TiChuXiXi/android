package mystack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class useStack {
    protected Stack<Double> numbers = new SeqStack<>();
    protected Stack<Character> operators = new SeqStack<>();

    private List<String> divide(String str){
        List<String> mylist = new ArrayList<>();
        if(str == null || str.length() == 0)
            return null;
        char[] arr = str.toCharArray();
        int index = 0;
        for(int i = 0; i < str.length(); i++){
            if((str.charAt(i) < '0' || str.charAt(i) > '9') && str.charAt(i) != '.'){
                String num = str.substring(index, i);
                mylist.add(num);
                index = i + 1;
                if(str.charAt(i) == '(')
                    operators.push(str.charAt(i));
                else if(str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/'){
                    while(!operators.isEmpty() && !compare(operators.peek(), str.charAt(i))){
                        mylist.add(String.valueOf(operators.pop()));
                    }
                    operators.push(str.charAt(i));
                } else if(str.charAt(i) == ')'){
                    while (!operators.isEmpty()) {
                        if(operators.peek() != '(')
                            mylist.add(String.valueOf(operators.pop()));
                        else{
                            operators.pop();
                            break;
                        }
                    }
                }
            }
        }
        mylist.add(str.substring(index));
        while (!operators.isEmpty()) {
            mylist.add(String.valueOf(operators.pop()));
        }
        return mylist;
    }
    private boolean compare(char a, char b){
        if(a == '(')
            return true;
        else return (a == '+' || a == '-') && (b == '*' || b == '/');
    }
    public String getResult(String str){
        List<String> reversePolish = divide(str);
//        for(int i = 0; i < reversePolish.size(); i++){
//            String strNum = reversePolish.get(i);
//            if(isNumeric(strNum))
//                numbers.push(Double.parseDouble(strNum));
//            else {
//                double number1 = numbers.pop();
//                double number2 = numbers.pop();
//                double result = 0;
//                switch (strNum) {
//                    case "+":
//                        result = number2 + number1;
//                        break;
//                    case "-":
//                        result = number2 - number1;
//                        break;
//                    case "*":
//                        result = number2 * number1;
//                        break;
//                    case "/":
//                        result = number2 / number1;
//                        break;
//                    default:
//                        break;
//                }
//                numbers.push(result);
//            }
//        }
        double number1 = 0;
        double number2 = 0;
        while(reversePolish.size() != 0){
            String strNum = reversePolish.get(0);
            if(isNumeric(strNum))
                numbers.push(Double.parseDouble(strNum));
            else {
                if(!numbers.isEmpty())
                    number1 = numbers.pop();
                else
                    return "error";
                if(!numbers.isEmpty())
                    number2 = numbers.pop();
                else
                    return "error";
                double result = 0;
                switch (strNum) {
                    case "+":
                        result = number2 + number1;
                        break;
                    case "-":
                        result = number2 - number1;
                        break;
                    case "*":
                        result = number2 * number1;
                        break;
                    case "/":
                        result = number2 / number1;
                        break;
                    default:
                        break;
                }
                numbers.push(result);
            }
            reversePolish.remove(0);
        }
        double step = numbers.pop();
        if(numbers.isEmpty())
            numbers.push(step);
        else
            return "error";
        return String.valueOf(numbers.pop());
    }
    private boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }

//    public boolean enabled(String str){
//        // 错误情况，运算符连续
//        String error1 = "/[\\+\\-\\*\\/]{2,}/";
//        if(str.matches(error1))
//            return false;
//        // 空括号
//        String error2 = "/\\(\\)/";
//        if(str.matches(error2))
//            return false;
//        // 错误情况，(后面是运算符
//        String error3 = "/\\([\\+\\-\\*\\/]/";
//        if(str.matches(error3))
//            return false;
//        // 错误情况，(前面不是运算符
//        String error4 = "/[^\\+\\-\\*\\/]\\(/";
//        if(str.matches(error4))
//            return false;
//        // 错误情况，)前面是运算符
//        String error5 = "/[\\+\\-\\*\\/]\\)/";
//        if(str.matches(error5))
//            return false;
//        // 错误情况，)后面不是运算符
//        String error6 = "/\\)[^\\+\\-\\*\\/]/";
//        if(str.matches(error6))
//            return false;
//        //运算符号不能在首末位
//        String error7 = "/^[\\+\\-\\*\\/.]|[\\+\\-\\*\\/.]$/";
//        if(str.matches(error7))
//            return false;
//        //左右括号匹配
//        Stack<Character> bracketStack = new SeqStack<>();
//        for(int i = 0; i < str.length(); i++){
//            char item = str.charAt(i);
//            if(item == '(')
//                bracketStack.push('(');
//            else if(item == ')'){
//                if(!bracketStack.isEmpty())
//                    bracketStack.pop();
//                else
//                    return false;
//            }
//        }
//        return true;
//    }
}
