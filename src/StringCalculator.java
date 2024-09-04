import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");

        String input = scanner.nextLine();
        try {
            String result = calculate(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String expression) throws Exception {
        // Регулярное выражение для парсинга входной строки
        Pattern pattern = Pattern.compile("^\"([^\"]{1,10})\"\\s*([\\+\\-\\*/])\\s*(\"([^\"]{1,10})\"|(\\d+))$");
        Matcher matcher = pattern.matcher(expression);

        if (!matcher.matches()) {
            throw new Exception("Некорректное выражение!");
        }

        String str1 = matcher.group(1);
        String operation = matcher.group(2);
        String str2 = matcher.group(4);
        String numStr = matcher.group(5);

        if (str2 != null) { // если вторым аргументом является строка
            if (operation.equals("+")) {
                return limitLength(str1 + str2);
            } else if (operation.equals("-")) {
                return limitLength(str1.replaceFirst(Pattern.quote(str2), ""));
            } else {
                throw new Exception("Некорректная операция для строк!");
            }
        } else { // если вторым аргументом является число
            int num = Integer.parseInt(numStr);
            if (num < 1 || num > 10) {
                throw new Exception("Число должно быть от 1 до 10 включительно!");
            }

            if (operation.equals("*")) {
                return limitLength(str1.repeat(num));
            } else if (operation.equals("/")) {
                int subLength = str1.length() / num;
                return limitLength(str1.substring(0, subLength));
            } else {
                throw new Exception("Некорректная операция с числом!");
            }
        }
    }

    private static String limitLength(String result) {
        return result.length() > 40 ? result.substring(0, 40) + "..." : result;
    }
}