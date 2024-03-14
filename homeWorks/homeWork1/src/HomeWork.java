import java.util.*;
import java.util.stream.Collectors;

public class HomeWork {
    public static void main(String[] args) {
        Streams.Department dep1 = new Streams.Department("dep1");
        Streams.Department dep2 = new Streams.Department("dep2");
        List<Streams.Person> listPersons = getPeople(dep1, dep2);

        HomeWork hw1 = new HomeWork();
        System.out.println("Home Work task1: ");
        hw1.printNamesOrdered(listPersons);

        System.out.println("\nHome Work task2:");
        System.out.println(hw1.printDepartmentOldestPerson(listPersons));

        System.out.println("\nHome Work task3:");
        System.out.println(hw1.findFirstPersons(listPersons));

        System.out.println("\nHome Work task4:");
        System.out.println(hw1.findTopDepartment(listPersons));
    }
    //Реализовать методы, описанные ниже:

    /**
     * Вывести на консоль отсортированные (по алфавиту) имена персон
     */
    public void printNamesOrdered(List<Streams.Person> persons) {
        List<String> sortedNames = persons.stream()
                .map(Streams.Person::getName)
                .sorted()
                .toList();
        sortedNames.forEach(System.out::println);
    }

    /**
     * В каждом департаменте найти самого взрослого сотрудника.
     * Вывести на консоль маппинг department -> personName
     * Map<Department, Person>
     */
    /*
    public Map<Streams.Department, Streams.Person> printDepartmentOldestPerson(List<Streams.Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Streams.Person::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Streams.Person::getAge)),
                                oldestPerson -> oldestPerson.orElse(null))));
    }*/
    public Map<Streams.Department, String> printDepartmentOldestPerson(List<Streams.Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Streams.Person::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Streams.Person::getAge)),
                                oldestPerson -> oldestPerson.map(p -> " -> "
                                        + p.getName() + ", age " + p.getAge()).orElse(null))));
    }

    /**
     * Найти 10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000
     */
    public List<Streams.Person> findFirstPersons(List<Streams.Person> persons) {
        return persons.stream()
                .filter(person -> person.getAge() < 30 && person.getSalary() > 50_000)
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Найти департамент, чья суммарная зарплата всех сотрудников максимальна
     */
    public Optional<Streams.Department> findTopDepartment(List<Streams.Person> persons) {
        Map<Streams.Department, Double> departmentSalaries = new HashMap<>();

        for (Streams.Person person : persons) {
            departmentSalaries.merge(person.getDepartment(), person.getSalary(), Double::sum);
        }

        return departmentSalaries.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

    }

    private static List<Streams.Person> getPeople(Streams.Department dep1, Streams.Department dep2) {
        Streams.Person person1 = new Streams.Person("Анна", 25, 35690, dep1);
        Streams.Person person2 = new Streams.Person("Яна", 45, 65000, dep2);
        Streams.Person person3 = new Streams.Person("Игорь", 24, 120000, dep1);
        Streams.Person person4 = new Streams.Person("Aгния", 18, 45000, dep2);
        Streams.Person person5 = new Streams.Person("Алина", 58, 35690, dep1);
        Streams.Person person6 = new Streams.Person("Ярик", 19, 65000, dep1);
        List<Streams.Person> listPersons = new ArrayList<>();
        listPersons.add(person1);
        listPersons.add(person2);
        listPersons.add(person3);
        listPersons.add(person4);
        listPersons.add(person5);
        listPersons.add(person6);
        return listPersons;
    }

}