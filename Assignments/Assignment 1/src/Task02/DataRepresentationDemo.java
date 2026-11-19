import java.util.Arrays;
import java.util.Scanner;

public class DataRepresentationDemo {
    public static void main(String[] args) {
        int[] slotsPerFloor = {2, 3, 1};
        String[][] slots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slots.length; f++) slots[f] = new String[slotsPerFloor[f]];

        slots[0][0] = "TN01AA1111";
        slots[1][2] = "TN02BB2222";
        for (int f = 0; f < slots.length; f++)
            System.out.println("Floor " + f + ": " + Arrays.toString(slots[f]));

        int minutes = 130;
        long startedHours = (minutes + 59L) / 60L;
        double rate = 30.0;
        System.out.println("Billable hours = " + startedHours);
        System.out.println("Fee = Rs " + (startedHours * rate));
        System.out.println("Wrong integer division: " + (minutes / 60) + " hours");

        // Uncomment for interactive input:
        // Scanner sc = new Scanner(System.in);
        // System.out.print("Enter vehicle number: ");
        // String regNo = sc.nextLine();
    }
}