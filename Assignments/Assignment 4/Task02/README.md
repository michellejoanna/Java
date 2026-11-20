# Task 2 – Hospital Outpatient Appointment Desk (8 marks)

Event table: Department combo / selection / ItemListener / refresh doctor list; Doctor combo / selection / ItemListener / refresh slots; Date field / Enter / ActionListener / validate date and refresh slots; Book button / ActionEvent / ActionListener / validate, confirm and book; window close / WINDOW_CLOSING / WindowAdapter / ask before closing when a booking is pending.

Hierarchy: JFrame with BorderLayout; heading at NORTH, appointment form at CENTER using GridLayout or GridBagLayout, action/status at SOUTH. Form contains patient ID, department, doctor, date and slot.

Handler pseudocode: Refresh doctors by reading selected department, replacing doctor model and clearing/reloading slots. Booking validates all fields, checks availability, asks confirmation, saves booking and shows reference. If invalid or unavailable, show an error and retain fields.

Window trace: main schedules JFrame creation on EDT; close triggers windowClosing; if booking pending ask confirmation; yes disposes, no leaves window open. Applet lifecycle differs: init, start, paint, stop, destroy. JFrame does not use applet lifecycle.
