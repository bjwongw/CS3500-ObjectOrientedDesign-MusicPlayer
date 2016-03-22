Alex Aubuchon & Brendon Wong
CS 3500: Object-Oriented Design

                                MUSIC PLAYER README

+--------------------------------------------------------------------------------------------------+
| Changes to the model from HW04:                                                                  |
+--------------------------------------------------------------------------------------------------+
    (We started with Brendon's codebase)
    - Changed IMusicModel's noteToPlay and getNotes to return Set<Note> instead of List<Note>. We
        did this because iterating over indices is not particularly useful, and it removes the
        possibility of there being duplicates.
    - Changed the internal model representation of all notes from Map<Pitch, List<Note>> to
        Map<Integer(Beat), Set<Note>>. This made much more sense in terms of organization, as
        well as getting the notes to play at a given beat.
    - Removed the Pitch class, and consolidated it into the Note class via an internal enum Pitch.
        This has the benefit of simplifying the code and construction of a Note, as well as reducing
        the number of objects that need to be dealt with.
    - Added getLowest/HighestNote methods to the model interface.
    - Changed addNote such that it allows any note to be added (even if there are overlaps), but
        only maintains a single entry of a given note.
    - Changed editNote such that it now takes two notes (the one to remove and the one to replace
        it).
    - Recoded various methods in the model class due to the above changes (namely the new data
        representation and addNote behavior).
    - Changed the Note class so that it is immmutable after instantiation. Once instantiated, the
        data cannot be corrupted by access to their fields (which we provide).

+--------------------------------------------------------------------------------------------------+
| Changes made to accommodate views in HW05:                                                       |
+--------------------------------------------------------------------------------------------------+

    - Added getPitchRange to the model interface. This helps for displaying the range of pitches
        in visual views.
    - NOTE: we're considering moving all the code (and associated methods) for printMusic from the
        GenericMusicModel to the ConsoleView class in the future.
    - Added a tempo field to the model, and a getTempo method to the interface.
    - Added a new constructor to the model for passing a tempo in.
    - Added the required builder, which works with the MusicReader.
    - Added midi pitch methods to the Note class.
    - Added volume and instrument fields/getters to the Note class.
    - Extended compareTo and equals/hashCode on Note for the new fields.

+--------------------------------------------------------------------------------------------------+
| Design explanation:                                                                              |
+--------------------------------------------------------------------------------------------------+

    Our model implementation (GenericMusicModel) implements the IMusicModel interface, which
    includes the methods that we think a view or controller will need to properly interact/use the
    underlying data. The GenericMusicModel contains two fields: notes and tempo. The tempo is
    the tempo at which the piece should be played, and the notes field is a
    Map<int, Set<Note>>, representing all the notes in the model. We decided to make notes a Map,
    because it made the most sense in terms of organization. Since it maps from the beat numbers
    to the set of notes that start at that beat, you can easily return all the notes to be played
    at a particular time. We went with a Set<Note> so that there couldn't be any duplicate Notes.

    We represented notes as their own class, giving them fields for pitch, octave, start beat,
    duration (measured in beats), instrument (in terms of MIDI), and volume. The pitch field is
    actually an Enum nested within the Note class, because it is directly associated with notes.
