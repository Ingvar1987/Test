import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestStringCalculator {
    static Scanner scanner = new Scanner(System.in);
    static String result = "";

    public static void main(String[] args) {
        System.out.println("Введите выражение [\"a\" + \"b\", \"a\" - \"b\", \"a\" * x, \"a\" / x] где a и b - строки а x - число от 1 до 10 включительно:");

        String userInput = scanner.nextLine();
        try {
            result = parseAndCalculate(userInput);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String parseAndCalculate(String expression) throws Exception {
        // Шаблон для разбиения строки
        Pattern pattern = Pattern.compile("\"([^\"]{1,10})\"\\s*([+\\-*/])\\s*(\"([^\"]{1,10})\"|(\\d+))");
        Matcher matcher = pattern.matcher(expression);

        if (!matcher.matches()) {
            throw new Exception("Некорректный формат выражения!");
        }

        String str1 = matcher.group(1);  // Первая строка
        String operation = matcher.group(2);  // Оператор (+, -, *, /)
        String str2 = matcher.group(4);  // Вторая строка (если есть)
        String numStr = matcher.group(5);  // Число (если есть)

        if (str2 != null) { // Если второй аргумент строка
            return calculateStringOperation(str1, str2, operation.charAt(0));
        } else { // Если второй аргумент число
            int num = Integer.parseInt(numStr);
            if (num < 1 || num > 10) {
                throw new Exception("Число должно быть от 1 до 10 включительно!");
            }
            return calculateStringNumberOperation(str1, num, operation.charAt(0));
        }
    }

    // Метод для операций со строками
    public static String calculateStringOperation(String str1, String str2, char op) throws Exception {
        switch (op) {
            case '+':
                return limitLength(str1 + str2);
            case '-':
                return limitLength(str1.replaceFirst(Pattern.quote(str2), ""));
            default:
                throw new Exception("Операция " + op + " не поддерживается для строк!");
        }
    }

    // Метод для операций строка-число
    public static String calculateStringNumberOperation(String str1, int num, char op) throws Exception {
        switch (op) {
            case '*':
                return limitLength(str1.repeat(num));
            case '/':
                if (num > str1.length()) {
                    throw new Exception("Число больше длины строки!");
                }
                return limitLength(str1.substring(0, str1.length() / num));
            default:
                throw new Exception("Операция " + op + " не поддерживается для числа!");
        }
    }

    // Ограничение длины результата до 40 символов
    private static String limitLength(String result) {
        return result.length() > 40 ? result.substring(0, 40) + "..." : result;
    }
}
