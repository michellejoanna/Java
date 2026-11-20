# Task 4 – Event-Handling Forensics (7 marks)

The event method must be spelled mouseClicked with exact capitalization. Without @Override, a misspelling may silently create a different method rather than overriding the listener method. Drawing directly in the click callback is temporary: minimize and restore triggers repaint and clears pixels. Store each Point, update the count, call repaint, and redraw stored points in paintComponent. Repaint time is O(n) for n points; storage is O(n), rather than O(width × height) for a pixel buffer.
