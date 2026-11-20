# Task 1 – GUI Design Review (6 marks)

Deficiencies: UI and payment logic are mixed; one long if/else listener handles every action; String action commands may be compared incorrectly with ==; empty, nonnumeric and negative amounts are not validated; database work on the Event Dispatch Thread freezes the window; user feedback is weak; Receipt is not gated on successful payment; resources and exceptions are not handled safely.

The repaired implementation separates FeePaymentWindow from FeePaymentService, validates input, performs work through SwingWorker, reports errors and enables Receipt only after successful payment.

Design defence (under 100 words): Separating the window from the payment service makes each part easier to test and maintain. The window collects input and displays feedback, while the service validates and processes payment. SwingWorker performs slow work away from the Event Dispatch Thread, preventing the interface from freezing. Separate action handlers keep each responsibility clear. A single listener with a long if–else chain becomes harder to extend as buttons are added.
