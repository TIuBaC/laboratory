23 ВАРИАНТ

1 ЗАДАНИЕ

import java.util.*;

public class Zad1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите элементы массива через пробел:");
        String input = scanner.nextLine();

        String[] parts = input.trim().split("\\s+");

        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            while (true) {
                try {
                    arr[i] = Integer.parseInt(parts[i]);
                    break; // если всё ок, выходим из цикла
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный ввод: " + parts[i]);
                    System.out.print("Введите заново число вместо него: ");
                    parts[i] = scanner.nextLine();
                }
            }
        }

        int firstZero = -1, lastZero = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                if (firstZero == -1)
                    firstZero = i;
                lastZero = i;
            }
        }

        if (firstZero == -1 || lastZero == firstZero) {
            System.out.println("В массиве должно быть минимум два нуля!");
            return;
        }

        int size = lastZero - firstZero - 1;
        int[] B = new int[size];
        for (int i = 0; i < size; i++) {
            B[i] = arr[firstZero + 1 + i];
        }

        System.out.println("Новый массив B:");
        for (int num : B) {
            System.out.print(num + " ");
        }
        System.out.println("\nРазмер массива B: " + B.length);
    }
}


2 ЗАДАНИЕ

import java.util.Scanner;

public class Zad2 {
    public static void main(String[] args) {
        Triangle t1 = new Triangle();
        Triangle t2 = new Triangle();

        System.out.println("Введите координаты первого треугольника:");
        t1.read();

        System.out.println("Введите координаты второго треугольника:");
        t2.read();

        System.out.println("\nПервый треугольник:");
        t1.display();
        System.out.println("Периметр: " + t1.perimeter());

        System.out.println("\nВторой треугольник:");
        t2.display();
        System.out.println("Периметр: " + t2.perimeter());

        Triangle t3 = Triangle.add(t1, t2);
        System.out.println("\nРезультат сложения двух треугольников:");
        t3.display();
    }
}

class Triangle {
    private double x1, y1, x2, y2, x3, y3;

    public void init(double a1, double b1, double a2, double b2, double a3, double b3) {
        x1 = a1; y1 = b1;
        x2 = a2; y2 = b2;
        x3 = a3; y3 = b3;
    }

    public void read() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите x1 y1 x2 y2 x3 y3 через пробел: ");
        x1 = sc.nextDouble();
        y1 = sc.nextDouble();
        x2 = sc.nextDouble();
        y2 = sc.nextDouble();
        x3 = sc.nextDouble();
        y3 = sc.nextDouble();
    }

    public void display() {
        System.out.println("(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + "), (" + x3 + ", " + y3 + ")");
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double perimeter() {
        double a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double b = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double c = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        return a + b + c;
    }

    public static Triangle add(Triangle t1, Triangle t2) {
        Triangle result = new Triangle();
        result.init(
            t1.x1 + t2.x1, t1.y1 + t2.y1,
            t1.x2 + t2.x2, t1.y2 + t2.y2,
            t1.x3 + t2.x3, t1.y3 + t2.y3
        );
        return result;
    }
}


3 ЗАДАНИЕ


import java.util.Scanner;

class Mineral {
    double weight;        // вес минерала
    double pricePerGram;  // цена за грамм

    void read() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите вес минерала (грамм): ");
        weight = sc.nextDouble();
        System.out.print("Введите цену за грамм: ");
        pricePerGram = sc.nextDouble();
    }

    void display() {
        System.out.println("Вес: " + weight + " г, Цена за грамм: " + pricePerGram +
                           ", Стоимость добычи: " + getCost());
    }

    double getCost() {
        return weight * pricePerGram;
    }
}

class Mine {
    String name;       // название рудника
    Mineral m1, m2, m3; 
    double totalCost;  // общая стоимость добычи

    void read() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите название рудника: ");
        name = sc.nextLine();

        System.out.println("Введите данные для первого минерала:");
        m1 = new Mineral();
        m1.read();

        System.out.println("Введите данные для второго минерала:");
        m2 = new Mineral();
        m2.read();

        System.out.println("Введите данные для третьего минерала:");
        m3 = new Mineral();
        m3.read();

        calculateTotalCost();
    }

    void calculateTotalCost() {
        totalCost = m1.getCost() + m2.getCost() + m3.getCost();
    }

    double getProfit() {
        return 0.15 * totalCost;
    }

    Mineral getMostValuable() {
        Mineral mostValuable = m1;
        if (m2.getCost() > mostValuable.getCost()) mostValuable = m2;
        if (m3.getCost() > mostValuable.getCost()) mostValuable = m3;
        return mostValuable;
    }

    void display() {
        System.out.println("\nРудник: " + name);
        m1.display();
        m2.display();
        m3.display();
        System.out.println("Общая стоимость добычи: " + totalCost);
        System.out.println("Доходность рудника: " + getProfit());
        System.out.println("Самое ценное полезное ископаемое:");
        getMostValuable().display();
    }
}

public class Main {
    public static void main(String[] args) {
        Mine mine = new Mine();
        mine.read();       // Ввод данных
        mine.display();    // Вывод результатов
    }
}


4 ЗАДАНИЕ

record TriangleRecord(double x1, double y1,
                      double x2, double y2,
                      double x3, double y3) {

    static TriangleRecord add(TriangleRecord t1, TriangleRecord t2) {
        return new TriangleRecord(
            t1.x1() + t2.x1(), t1.y1() + t2.y1(),
            t1.x2() + t2.x2(), t1.y2() + t2.y2(),
            t1.x3() + t2.x3(), t1.y3() + t2.y3()
        );
    }

    double perimeter() {
        double a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double b = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double c = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        return a + b + c;
    }

    void display() {
        System.out.println("(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + "), (" + x3 + ", " + y3 + ")");
    }
}


import java.util.Scanner;

public class MainRecordDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Triangle t1 = new Triangle();
        t1.read();
        Triangle t2 = t1; // присвоение ссылки
        t2.setX1(999);    // меняем t2
        System.out.println("t1 после изменения t2:");
        t1.display();     // t1 тоже изменился! (ссылочный тип)

        System.out.println("\nВведите координаты для record:");
        System.out.print("x1 y1 x2 y2 x3 y3: ");
        double x1 = sc.nextDouble();
        double y1 = sc.nextDouble();
        double x2 = sc.nextDouble();
        double y2 = sc.nextDouble();
        double x3 = sc.nextDouble();
        double y3 = sc.nextDouble();

        TriangleRecord tr1 = new TriangleRecord(x1, y1, x2, y2, x3, y3);
        TriangleRecord tr2 = tr1; 
        // tr2.x1 = 999; 
        System.out.println("tr1 после присвоения tr2:");
        tr1.display();

        // 3. Пример сложения record
        TriangleRecord tr3 = TriangleRecord.add(tr1, new TriangleRecord(1,1,1,1,1,1));
        System.out.println("Сложение треугольников record:");
        tr3.display();
    }
}
