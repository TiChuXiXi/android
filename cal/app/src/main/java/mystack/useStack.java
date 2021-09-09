package mystack;

import android.util.Log;

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
//        Log.v("number", mylist.toString());
        return mylist;
    }
    private boolean compare(char a, char b){
        if(a == '(')
            return true;
        else return (a == '+' || a == '-') && (b == '*' || b == '/');
    }
    public String getResult(String str){
        List<String> reversePolish = divide(str);
//        Log.v("number", reversePolish.toString());
        String errorMessage = "error";
        double number1 = 0;
        double number2 = 0;
        while(reversePolish.size() != 0){
            String strNum = reversePolish.get(0);
            if(isNumeric(strNum)) {
                numbers.push(Double.parseDouble(strNum));
//                Log.v("number", strNum);
            }
            else if(strNum.length() != 1){
                return errorMessage;
            }
            else{
//                Log.v("number", strNum);
                if(!numbers.isEmpty())
                    number1 = numbers.pop();
                else
                    return errorMessage;
                if(!numbers.isEmpty())
                    number2 = numbers.pop();
                else
                    return errorMessage;
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
//                Log.v("number", String.valueOf(result));
            }
            reversePolish.remove(0);
        }
        double step = numbers.pop();
        if(numbers.isEmpty())
            numbers.push(step);
        else
            return errorMessage;
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
}
