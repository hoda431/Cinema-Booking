import java.util.*;

class Task_CinemaBooking {

    static class Seat {
        final char row;
        final int number;

        Seat(char row, int number) {
            this.row = row;
            this.number = number;
        }

        @Override
        public String toString() {
            return "" + row + number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Seat other = (Seat) o;
            return row == other.row && number == other.number;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, number);
        }
    }

    public static void main(String[] args) {

        HashMap<Seat, String> bookings = new HashMap<>();
        HashSet<Seat> broken = new HashSet<>();

        // Part A
        bookings.put(new Seat('A', 1), "Ali");
        bookings.put(new Seat('A', 2), "Sara");
        bookings.put(new Seat('B', 5), "Omar");
        bookings.put(new Seat('C', 3), "Mona");
        System.out.println("Bookings:           " + bookings);

        // Part B
        System.out.println("Who booked C3:      " + bookings.get(new Seat('C', 3)));

        // Part C
        System.out.println("Is A2 taken:        " + bookings.containsKey(new Seat('A', 2)));
        System.out.println("Is E6 taken:        " + bookings.containsKey(new Seat('E', 6)));

        // Part D
        String existing = bookings.putIfAbsent(new Seat('B', 5), "Youssef");
        if (existing == null) {
            System.out.println("Youssef -> B5:      booked");
        } else {
            System.out.println("Youssef -> B5:      refused, taken by " + existing);
        }

        // Part E
        bookings.remove(new Seat('A', 1));
        bookings.put(new Seat('D', 2), "Ali");
        System.out.println("After Ali moved:    " + bookings);

        // Part F
        broken.add(new Seat('E', 1));
        broken.add(new Seat('E', 2));
        if (!broken.contains(new Seat('E', 1))) {
            bookings.put(new Seat('E', 1), "Hana");
            System.out.println("Hana -> E1:         booked");
        } else {
            System.out.println("Hana -> E1:         refused, seat is broken");
        }

        // Part G
        System.out.println("Seats booked:       " + bookings.size());

        System.out.println();
        printChart(bookings, broken);
    }

    static void printChart(HashMap<Seat, String> bookings, HashSet<Seat> broken) {
        System.out.println("     1  2  3  4  5  6");
        for (char row = 'A'; row <= 'E'; row++) {
            System.out.print(row + "  ");
            for (int number = 1; number <= 6; number++) {
                Seat seat = new Seat(row, number);
                if (bookings.containsKey(seat)) {
                    System.out.print("  X");
                } else if (broken.contains(seat)) {
                    System.out.print("  #");
                } else {
                    System.out.print("  .");
                }
            }
            System.out.println();
        }
    }
}

/*
 Q1.Why did we have to override hashcode() and equals()?
 HashMap uses hashCode() to find the bucket and equals() to compare keys.
 Without overriding them, objects are compared by memory address, so two new Seat('B', 5) objects are never equal.
 Q2. Delete your hashCode() but keep equals(), and run again.
     What happens? Why?
    النتايج بتبقى غلط,المقعدين المتساويين غالبا بيطلع ليهم hashcode  مختلف
    ف ال mapبتدور ف bucket غلط وم تلاقيش ال key
    get ترجع null  ,containsKey ترجع false
    and putIfAbsent تحجز b5تاني ل يوسف ب
لو ال objects متساويين بال equals , لازم يكون ليهم نفس ال hashmap

    Q3. Why are row and number final? What could go wrong if the number
     of a booked seat could be changed after it was put in the map?

    If the number changed after put,
    the hash code would change, but the entry would stay in the old bucket and the map could no longer find it. final prevents this.

     Q4. Change equals(Object o) to equals(Seat o). What happens?
     Then also remove @Override and run again. What happens now?
     With @Override, it doesn't compile, because the parameter must be Object. Without @Override, it compiles but only overloads equals, so HashMap still uses the original one and gives wrong results silently.

 BONUS. Replace the whole Seat class with this one line:
            record Seat(char row, int number) {}
        Run it. What happens, and why?
*/