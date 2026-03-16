# Overview

The goal of this project was to implement the core data structure used by terminal emulators to store
and manipulate text. The terminal buffer maintains a grid of character cells representing the visible screen, 
along with a scrollback history that stores lines that have scrolled off the screen.

Each cell contains: a unicode character, foreground and background color, style flags (bold, italic, underline)
The buffer also maintains a cursor position indicating where the next character will be written.

### Data Structure Design

#### TerminalBuffer

The main class is TerminalBuffer, which manages: 
- screen content
- scrollback history
- cursor position
- current text attributes

The screen and scrollback are stored as List<TerminalLine>.
This structure was chosen because:
- lines can easily be added or removed
- scrolling operations are simple
- because we need to move through positions we have to use ArrayList

#### TerminalLine

Each TerminalLine represents one row of the terminal and is stored as List<TerminalCell>.
It also contains a wrapped flag indicating whether the line wraps to the next line or it fits on one line.
(this flag is important for correctly handling text reflow during resizing)

#### TerminalCell

Each TerminalCell stores: a unicode code point, styling attributes and a flag indicating whether the cell is
a continuation of a wide character
Using separate cell object allows each character to maintain independent styling information.

### Cursor Management

The buffer keeps track of the cursor using (cursorX, cursorY).
Cursor movement operations ensure the cursor always remains inside the screen bounds. When text reaches the end
of screen, the buffer scrolls and the top line moves into scrollback history.

### Writing and Editing Operations

#### Write

Writing text overwrites existing content starting at the cursor position. Characters are written sequentially
and the cursor advances accordingly. If the cursor reaches the end of a line, the text wraps to the next line.

#### Insert

Insert operations shift existing characters to the right before placing the new character. this preserves 
existing content while inserting new characters at the cursor location.

### Scrollback

When the cursor moves beyond the bottom of the screen, the buffer scrolls:
- the top line of the screen is removed
- it is added to the scrollback buffer
- a new empty line is added at the bottom
The scrollback size is capped using a configurable maximum size.

### Resize Handling

Resizing the terminal requires reorganizing existing text to fit the new dimensions.

The implementation collects cells from the current screen and reconstructs lines based on the new width. 
The wrapped flag is used to determine whether lines should be merged during reflow.

if the new screen height is smaller than the previous height, excess lines are moved into scrollback.

### Wide Character Suppport

Some characters, such as CJK ideographs and emoji, occupy two terminal cells instead of one.
To suppport this:
- characters are stored as Unicode code points
- wide characters occupy two adjacent cells
- the second cell is marked as a continuation cell

This ensures that wide characters are not split across lines or corrupted by editing operations.


## Trade-offs and Design Decisions

#### Using Lists for Storage

ArrayList was used to store lines and cells. This simplifies indexing and keeps implementation
straightforwars. However, shifting characters during insert operations requires copying cells.

#### Storing Code Points Instead of char

Characters are stored as Unicode code points rather than Java char values. This allows correct handling
of emoji and other characters.

The trade-off is slightly increased complexity when converting code points to displayable characters.


