import java.util.Arrays;

public class Sportsman {
    private String lastName;
    private int id;
    private double jumpLength;

    public Sportsman(String lastName, int id, double jumpLength) {
        this.lastName = lastName;
        this.id = id;
        this.jumpLength = jumpLength;
    }

    // Геттеры и сеттеры
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getJumpLength() {
        return jumpLength;
    }

    public void setJumpLength(double jumpLength) {
        this.jumpLength = jumpLength;
    }

    // Метод для вывода информации о спортсмене
    public void displayInfo() {
        System.out.println("Фамилия: " + lastName);
        System.out.println("Идентификационный номер: " + id);
        System.out.println("Длина прыжка: " + jumpLength + " метров");
    }

    // Метод для сравнения спортсменов по длине прыжка
    public int compareJumpLength(Sportsman other) {
        if (this.jumpLength < other.jumpLength) {
            return -1;
        } else if (this.jumpLength > other.jumpLength) {
            return 1;
        } else {
            return 0;
        }
    }

    public static String getTop3Jumpers(Sportsman[] athletes) {
        // Сортируем массив спортсменов по убыванию длины прыжка
        Arrays.sort(athletes, (a, b) -> Double.compare(b.getJumpLength(), a.getJumpLength()));

        StringBuilder result = new StringBuilder("Топ-3 спортсменов по длине прыжка:\n");
        for (int i = 0; i < 3 && i < athletes.length; i++) {
            Sportsman athlete = athletes[i];
            result.append(i + 1).append(". ").append(athlete.getLastName())
                    .append(" (идентификационный номер: ").append(athlete.getId())
                    .append(", длина прыжка: ").append(athlete.getJumpLength()).append(" метров)\n");
        }

        return result.toString();
    }
}

