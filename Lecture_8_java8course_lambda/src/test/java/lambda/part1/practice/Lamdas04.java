package lambda.part1.practice;

import com.google.common.base.Predicates;
import org.junit.Test;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lamdas04 {

    static class Person {
        public String name;
        public int age;
        public GENDER gender;

        public Person(String name, int age, GENDER gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        enum GENDER {
            MALE,
            FEMALE
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }
    }

    @FunctionalInterface
    public interface PersonInterface {
        void accept();
    }

    @FunctionalInterface
    public interface PersonInterfacePredicate<Person> {
        boolean test(Person p);
    }

    @FunctionalInterface
    public interface PersonInterfaceCompare<Person> {
        int compare(Person p1, Person p2);
    }


    @Test
    public void testLamda01() {


        Person anton = new Person("Anton", 22, Person.GENDER.MALE);

        // expression as arrow function without params
        PersonInterface peronInterface = () -> System.out.println("Person: " + anton); // Closure

        // expression as arrow function with param
        PersonInterfacePredicate<Person> personInterfaceIsFemale = (Person p) -> p.gender == Person.GENDER.FEMALE;
        PersonInterfacePredicate<Person> testAgeGender = this::testAgeGender;

        // lambda as statement
        PersonInterfaceCompare<Person> personPersonInterfaceCompare = (Person p1, Person p2) -> {
            System.out.println("Compare : " + p1 + ", " + p2);
            return p1.name.compareTo(p2.name);
        };

        // expression as method reference
        PersonInterfacePredicate<Person> testAgeGenderAsMethodRef = this::testAgeGender;
        PersonInterfacePredicate<Person> testAgeGenderAsStaticMethodRef = Lamdas04::isMale;

        Integer sumAge = Stream.of(new Person("Elizaveta", 23, Person.GENDER.FEMALE),
                new Person("Artem", 21, Person.GENDER.MALE),
                new Person("Vera", 2, Person.GENDER.FEMALE))
                .filter(((Predicate<Person>) Lamdas04::isMale).negate())
                .mapToInt(Person::getAge)
                .sum();

        System.out.println("Age " + sumAge);
    }

    private boolean testAgeGender(Person p) {
        return p.gender == Person.GENDER.FEMALE && p.age < 65 && p.age > 18;
    }

    private static boolean isMale(Person p) {
        return p.gender != Person.GENDER.FEMALE;
    }
}
