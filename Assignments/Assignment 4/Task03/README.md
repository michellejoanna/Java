# Task 3 – Campus Library Search Screen (9 marks)

Use BorderLayout for the root: title NORTH, expanding results CENTER, controls/status SOUTH. Use FlowLayout for the search row and bottom navigation. Put JTable inside JScrollPane in the CENTER so it grows while search controls and buttons retain preferred sizes. Nested BorderLayout/FlowLayout is easier to maintain than absolute positioning. GridBagLayout can work but needs constraints; absolute positioning does not resize reliably. Typical layout work is proportional to component count; visible table rendering depends on rows and columns.

Capture three real screenshots, for example 650x420, 900x600 and 1200x750, and explain how the table grows while controls remain compact.
