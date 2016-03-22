Changes to the model from HW04:
(We started with Brendon's codebase)

-Changed all returned List<Note> to Set<Note>
-Changed the internal model representation of all notes to Map<Integer(Beat),
    Set<Note>> from Map<Pitch, List<Note>>
-Removed the pitch class, and consolidated it into the note class via an internal enum Pitch
-Added getLowest/HighestNote methods to the model interface
-Added getPitchRange to the model interface
-Added a tempo field to the model, and a getTempo method to the interface
-Added a new constructor to the model for passing a tempo in
-Added the required builder
-Changed addNote such that it allows any note to be added, but only maintaining a single
    entry of a given note
-Changed editNote such that it now takes two notes
-Recoded various methods in the model class due to the above changes (namely the new data
    representation and addNote behavior)
-Changed the Note class such that it is immmutable after instantiation so that data cannot
    be corrupted by access to their objects (which we provide)
-Added midi pitch methods to the Note class
-Added volume and instrument fields/getters to the Note class
-Added a toString method on Note that provides its pitch and octave
-Extended compareTo and equals/hashCode on Note for the new fields
