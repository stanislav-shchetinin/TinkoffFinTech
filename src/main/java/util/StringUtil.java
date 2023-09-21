package util;

public class StringUtil {
    /**
     * <p>Метод считает полиномимальный хеш строки</p>
     * <p>В качестве параметра принимается строка, состоящая только из символов с номерами в UTF-8:</p>
     * 34-123 (латинские буквы и частовстречающиеся символы), 1040-1103 (буквы русского алфавита), 1105(ё), 1025(Ё)
     * <p>Иначе выдаст IllegalArgumentException (метод characterNormalization)</p>
     * Итого символов: 156, большее простое число = 157 (= final int p)
     * @return хешкод, переданной строки
     * */
    public static long regionNameHashCode(String string) throws IllegalArgumentException{
        long result = 0;
        final int p = 163;
        for (int i = 0; i < string.length(); ++i){
            char ch = characterNormalization(string.charAt(i));
            result = result * p + ch;
        }
        return result;
    }

    /**
     * Метод нормализации значения символа
     * Переводит полученный char в отрезок [1; 157]
     * */
    private static char characterNormalization(char ch) throws IllegalArgumentException{
        if (ch >= '!' && ch <= 'z'){ //34-123
            return (char) (ch - '!' + 1);
        } else if (ch >= 'А' && ch <= 'я'){
            return (char) ('z' + ch - 'А' + 1);
        } else if (ch == 'Ё'){
            return 156;
        } else if (ch == 'ё'){
            return 157;
        } else {
            throw new IllegalArgumentException(String.format("Символ не может %s быть в имени города", ch));
        }
    }
}
