Alex Aubuchon & Brendon Wong
CS 3500: Object-Oriented Design

                                    MUSIC PLAYER README

+--------------------------------------------------------------------------------------------------+
| Changes made from previous homework assignments                                                  |
+--------------------------------------------------------------------------------------------------+

    - Added the sustainedNotes(int beat) method to the IMusicModel interface, and subsequently in
        the GenericMusicModel class. We finally realized why this method was necessarily because of
        the scrolling requirement for the GuiView. We needed to know the sustained notes so that
        if you scrolled halfway through a note (it's head is off screen but part of it should still
        be on screen), the tail would still show up. Because of this, we then had to edit the
        ConcreteGuiView to account for sustained notes when drawing the panel.
    - Added constants in the GuiViewImpl and ConcreteGuiView classes to prevent 'magic numbers'
        floating around the code. This made the math calculations significantly easier to think
        about and implement.
    - Changed the way createPitchPanel, createBeatPanel, and createNotePanel in ConcreteGuiPanel
        were made so that they were always a fixed size. Doing this allows you to start a program
        with an empty model and build a composition using the controller. Before, you would get
        exceptions if you tried to initialize a ConcreteGuiView with an empty model. Additionally,
        the views are now uniform, so you always have the same sized grid.
    - Moved a bulk of the set-up code from the ConcreteGuiView constructor to the updatePanel()
        method, which can be called anywhere. This was particularly useful to enable the display of
        model changes and view shifting.


+--------------------------------------------------------------------------------------------------+
| Design explanation:                                                                              |
+--------------------------------------------------------------------------------------------------+

    - Our CompositeView simply holds a MidiViewImpl and a GuiViewImpl, delegating all of its
        methods to those two classes. It's incredibly simply, and all the functionality required.
    - The KeyboardHandler and MouseHandler classes each extend their respective listeners and
        enable mapping of Runnables to key/mouse types/clicks, presses, and releases. This is
        necessary for the controller's operations.
    - The GuiController creates all of its necessary handlers instances and adds them into its
        Keyboard and MouseHandlers. Within the GuiController, we defined the necessary Runnable
        classes needed for its functionality. It had to be able to keep track of time, shift its
        views, reset, play, and pause the views. It can also add, delete, and move notes around
        in its contained IMusicModel.


+--------------------------------------------------------------------------------------------------+
| Using the GuiController:                                                                         |
+--------------------------------------------------------------------------------------------------+


PLAYBACK:

    To play the composition displayed on the GUI view, simply press the spacebar. To pause, press
    the spacebar again at any given time. Pressing the spacebar again will play the song, and so on.
    If you wish to restart the song, press the 'R' key. Then, you can press the spacebar to play the
    piece.

VIEW OPERATIONS:

    To jump to the end of the song (does not change the current beat), press the 'END' key. If you
    wish to jump to the start of the song (does not change the current beat), press the 'HOME' key.

    To visually move forward in time, press the right arrow key. To visually move back, press the
    left arrow key. Note: you cannot move left past beat 0.

    To visually move up in pitch, press the up arrow key. To visually move down, press the down
    arrow key. Note: you cannot move down below C0.

COMPOSITION BUILDING:

    To add a note to the composition, simply press the 'A' key where your mouse is hovering. The
    default note duration is 1. To change this, simply enter the desired duration length on your
    keyboard (there is no dialogue, just press the number keys) and then press 'A' where you want
    to add the note. Once you have placed the note, you can then change the duration again.

    If you want to remove a note from the composition, hover your mouse over the start of the note
    you wish to delete, and press the 'D' key.

    To move a note, press and hold on the starting beat of the note, then drag your mouse to the
    new desired position and release.

SUGGESTED JAR USAGE:
    java -jar [jar file] [music file] gui composite
