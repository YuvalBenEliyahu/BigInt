import java.util.ArrayList;

import static java.lang.Character.isDigit;

public class BigInt {
    private ArrayList nums;// ArrayList to store digits of the number
    private char sign;// char to store the sign of the number +/-

    public BigInt(String s) {
        this.checkValidNumber(s);
        this.nums = new ArrayList();
        for (int i = 1; i < s.length(); i++) {
            if (isDigit(s.charAt(i))) this.nums.add(s.charAt(i)); // check every char in the input string is digit
            else throw new IllegalArgumentException("Input contain non-digits characters");
        }
        this.removeStartingZeros();
    }

    private void checkValidNumber(String s) { // method to check if the input string is a valid number
        if (s.length() == 0) // empty input
            throw new IllegalArgumentException("No number entered");
        if (s.charAt(0) == '+') this.sign = '+';
        else if (s.charAt(0) == '-') this.sign = '-';
        else throw new IllegalArgumentException("No +/- sign entered");
        if (s.length() == 1) // only sign with no number
            throw new IllegalArgumentException("No number entered");
    }

    protected ArrayList getArray() {
        return nums;
    }

    protected int getNum(int i) {
        return Character.getNumericValue((char) nums.get(i));
    }

    protected char getSign() {
        return sign;
    }

    protected int getSize() {
        return nums.size();
    }

    protected void setSign(char sign) {
        this.sign = sign;
    }

    public BigInt plus(BigInt other) {// method to add two BigInt objects
        //checking the sign of the numbers to preform the correct way of sum
        String sum = "";
        if ((this.sign == '+' && other.getSign() == '-')) {
            if (isBigger(other)) {
                sum = minusCalculate(other);
                sum = '+' + sum;
            } else {
                sum = other.minusCalculate(this);
                sum = '-' + sum;
            }

        }
        if ((this.sign == '-' && other.getSign() == '+')) {
            if (isBigger(other)) {
                sum = minusCalculate(other);
                sum = '-' + sum;
            } else {
                sum = other.minusCalculate(this);
                sum = '+' + sum;
            }

        }
        if ((this.sign == '+' && other.getSign() == '+')) {
            sum = plusCalculate(other);
            sum = '+' + sum;
        }
        if ((this.sign == '-' && other.getSign() == '-')) {
            sum = plusCalculate(other);
            sum = '-' + sum;

        }
        return new BigInt(sum);
    }

    private String plusCalculate(BigInt other) {// method to perform the calculation of adding operation

        //remove leading zero's
        this.removeStartingZeros();
        other.removeStartingZeros();

        //init
        int rest = 0, digit = 0;
        int numSize = this.getSize() - 1;
        int otherSize = other.getSize() - 1;
        String sum = "";


        while (numSize >= 0 || otherSize >= 0) {
            if (numSize >= 0 && otherSize >= 0) digit = getNum(numSize) + other.getNum(otherSize) + rest;
            if (numSize >= 0 && otherSize < 0) digit = getNum(numSize) + rest;
            if (numSize < 0 && otherSize >= 0) digit = other.getNum(otherSize) + rest;
            sum = (char) (digit % 10 + '0') + sum;
            rest = digit / 10; // if digit lower than 10 it will be 0
            numSize--;
            otherSize--;
        }
        if (rest == 1) sum = '1' + sum;
        return sum;

    }

    public BigInt minus(BigInt other) {// method to subtract two BigInt objects
        //checking the sign of the number to preform the correct way of subtract
        String sum = "";
        if ((this.sign == '+' && other.getSign() == '-')) {
            sum = plusCalculate(other);
            sum = '+' + sum;
        }

        if ((this.sign == '-' && other.getSign() == '+')) {
            sum = plusCalculate(other);
            sum = '-' + sum;
        }

        if ((this.sign == '+' && other.getSign() == '+')) {
            if (isBigger(other)) {
                sum = minusCalculate(other);
                sum = '+' + sum;
            } else {
                sum = other.minusCalculate(this);
                sum = '-' + sum;
            }
        }
        if ((this.sign == '-' && other.getSign() == '-')) {
            if (isBigger(other)) {
                sum = minusCalculate(other);
                sum = '-' + sum;
            } else {
                sum = other.minusCalculate(this);
                sum = '+' + sum;
            }

        }
        return new BigInt(sum);
    }


    private String minusCalculate(BigInt other) {
        this.removeStartingZeros();
        other.removeStartingZeros();

        int rest = 0, digit = 0;
        int numSize = this.getSize() - 1;
        int otherSize = other.getSize() - 1;
        String sum = "";


        while (numSize >= 0 || otherSize >= 0) {
            if (numSize >= 0 && otherSize >= 0) digit = getNum(numSize) - other.getNum(otherSize) - rest;
            if (numSize >= 0 && otherSize < 0) digit = getNum(numSize) - rest;
            if (numSize < 0 && otherSize >= 0) digit = other.getNum(otherSize) - rest;
            sum = (char) (((digit + 10) % 10) + '0') + sum;
            if (digit < 0) rest = 1;
            else rest = 0;
            numSize--;
            otherSize--;
        }

        if (rest == 1) sum = '1' + sum;

        return sum;

    }

    public BigInt multiply(BigInt other) {// method to multiply two BigInt objects
        if (other.isBigger(this)) return other.multiply(this);//the bigger number will be the multiplier

        //init
        BigInt firstNum = new BigInt("+0");
        String number;
        int otherNum, rest;

        //removing leading zero's
        this.removeStartingZeros();
        other.removeStartingZeros();
        //for each num in other
        for (int i = other.getSize() - 1; i >= 0; i--) {
            otherNum = other.getNum(i);
            number = "";
            rest = 0;
            // adding starting zero's (multiplying the number 10*i)
            for (int k = other.getSize() - 1 - i; k > 0; k--) {
                number = '0' + number;
            }
            // multiply each other.num with this.num
            for (int j = this.getSize() - 1; j >= 0; j--) {
                rest += otherNum * this.getNum(j);
                number = (char) ((rest % 10) + '0') + number;
                rest = rest / 10;
            }
            if (rest != 0) number = (char) (rest + '0') + number;

            BigInt secondNum = new BigInt("+" + number);
            firstNum = firstNum.plus(secondNum);

        }
        // adding correct sign
        if ((this.getSign() == '+' && other.getSign() == '+') || (this.getSign() == '-' && other.getSign() == '-'))
            firstNum.setSign('+');
        else firstNum.setSign('-');
        return firstNum;
    }

    public BigInt divide(BigInt other) {
        if (isZero(other)) {
            throw new ArithmeticException("Divide by zero");
        }
        if (other.isBigger(this))//if divider is bigger then result is 0
            return new BigInt("+0");

        //removing leading zero's
        this.removeStartingZeros();
        other.removeStartingZeros();
        //init

        int count = 1;
        BigInt temp = new BigInt(this.toString());
        while (temp.isBigger(other)) {
            //removing the divide number count times
            temp = new BigInt("+" + temp.minusCalculate(other));
            temp.removeStartingZeros();
            count++;
        }
        if ((this.getSign() == '+' && other.getSign() == '+') || (this.getSign() == '-' && other.getSign() == '-'))
            return new BigInt('+' + Integer.toString(count));
        else return new BigInt('-' + Integer.toString(count));

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BigInt))
            return false;
        return nums.equals(((BigInt) obj).getArray()) && this.getSign() == ((BigInt) obj).getSign();
    }

    public int compareTo(BigInt other) {//compare if BigInt object is grater(1),equals(0),lower(-1) then other BigInt object
        if (this.isBigger(other)) {
            if (this.getSign() == '+')
                return 1;
            if (this.getSign() == '-')
                return -1;
        }
        if (other.isBigger(this)) {
            if (other.getSign() == '+')
                return -1;
            if (other.getSign() == '-')
                return 1;
        }
        if (this.getSign()=='+' && other.getSign() == '-')
            return 1;
        if (this.getSign()=='-' && other.getSign() == '+')
            return -1;
        return 0;
    }


    private boolean isZero(BigInt other) {// check if number is a zero
        other.removeStartingZeros();
        if (other.getSize() == 1)
            return true;
        return false;
    }

    private boolean isBigger(BigInt other) { // check if the absolut number is bigger than other absolut number
        if (nums.size() > other.getArray().size()) return true;
        if (nums.size() == other.getArray().size()) for (int i = 0; i < nums.size(); i++) {
            if (this.getNum(i) > other.getNum(i)) return true;
            if (this.getNum(i) < other.getNum(i)) return false;
        }
        return false;
    }

    private void removeStartingZeros() { // removing starting zero's
        if ((this.getNum(0) == 0) && this.getSize() != 1) {
            nums.remove(0);
            removeStartingZeros();
        }
    }

    @Override
    public String toString() {
        String num = "";
        removeStartingZeros();
        for (int i = 0; i < nums.size(); i++) {
            num += nums.get(i);
        }
        return sign + num;
    }


}
