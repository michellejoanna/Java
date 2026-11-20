# Task 5 – Same Screen, Different Event-Handling Styles (7 marks)

CurrencyConverter demonstrates four styles: frame implements ActionListener, named inner class, anonymous inner class and lambda. Enter in the amount field also converts. Results and invalid amounts appear in dialogs. Window closing uses WindowAdapter.

Comparison: frame listener is simple but can clutter the frame; named inner class is reusable and clear; anonymous inner class is explicit but verbose; lambda is concise for short handlers. A shared Action or method reference can be attached to a button, menu item and key binding so one handler is reused. A conversion takes O(1) time and O(1) extra space.
