package lecture1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        /*
        PlainInterface plainInterface = new PlainInterface() {
            @Override
            public String action(int x, int y) {
                return String.valueOf(x + y); //преобразовали число к строке
            }
        };

        System.out.println(plainInterface.action(5, 38));
    */
        //---тоже самое в виде лямбда-----//
        /*
        PlainInterface plainInterface = (x, y) -> String.valueOf(5 + 38);
        System.out.println(plainInterface.action(5, 38));
        PlainInterface plainInterface1 = (x, y) -> String.valueOf(Integer.compare(x, y));
        System.out.println(plainInterface.action(5, 38));
         */
        //---меняем возвращаемое значение интерфейса со стинг на инт---//
        PlainInterface plainInterface = Integer::sum;
        PlainInterface plainInterface1 = Integer::compare;
        System.out.println(plainInterface.action(5, 38));
        System.out.println(plainInterface1.action(5, 38));


        //------------Stream Api-----------//
        List<String> list = Arrays.asList("Привет", "мир", "я", "родилась");
        //String result =list.stream().filter(str -> str.length() > 4).toList().toString();
        //System.out.println(result);
        list.stream().filter(str -> str.length() > 4).forEach(System.out::println);
        list.stream().filter(str -> str.length() > 4).filter(str -> str.contains("е")).forEach(System.out::println);

    }
}
