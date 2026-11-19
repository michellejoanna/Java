class EncapsulatedTicket {
    private final String registrationNumber;
    private Integer slotNumber;
    private long durationMinutes;

    EncapsulatedTicket(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.isBlank())
            throw new IllegalArgumentException("Registration number required");
        this.registrationNumber = registrationNumber;
    }
    public String getRegistrationNumber() { return registrationNumber; }
    public Integer getSlotNumber() { return slotNumber; }
    public void setSlotNumber(Integer slotNumber) {
        if (slotNumber == null || slotNumber < 0)
            throw new IllegalArgumentException("Invalid slot number");
        this.slotNumber = slotNumber;
    }
    public void setDurationMinutes(long durationMinutes) {
        if (durationMinutes < 0)
            throw new IllegalArgumentException("Duration cannot be negative");
        this.durationMinutes = durationMinutes;
    }
    public long getDurationMinutes() { return durationMinutes; }
}
public class EncapsulationDemo {
    public static void main(String[] args) {
        EncapsulatedTicket t = new EncapsulatedTicket("TN01AB1234");
        t.setSlotNumber(4);
        t.setDurationMinutes(75);
        Integer anotherSlot = Integer.valueOf(4);
        System.out.println(t.getRegistrationNumber() + " slot " + t.getSlotNumber());
        System.out.println("Value comparison with equals: " + t.getSlotNumber().equals(anotherSlot));
    }
}