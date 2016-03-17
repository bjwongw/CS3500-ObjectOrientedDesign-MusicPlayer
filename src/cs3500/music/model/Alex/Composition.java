package cs3500.music.model.Alex;

/**
 * Interface to a Composition: a model for a song composed of notes.
 */
public interface Composition {

  /**
   * Returns a string representation of the entire composition with tones listed across the top,
   * and beats listed downwards on the left. Notes will be represented by X at the beat and note
   * where each starts, and |'s downward for each beat that the note persists.
   *
   * <p>As a technical specification: the output above consists of</p> <ul> <li>A column of numbers
   * representing the beats, printed right-justified and padded with leading spaces, that is
   * exactly as wide as necessary. (So if your piece is 999 beats long, it uses three columns of
   * characters; if it’s 1000 beats long, it uses four.)</li> <li>A sequence of columns, each five
   * characters wide, representing each pitch. The first line prints out the names of the pitches,
   * more-or-less centered within the five-character column. I.e., "  F2 " and " G#3 " or " D#10".
   * (Because we need to represent at least ten octaves, three-character columns won’t be wide
   * enough; furthermore, the string representation of a pitch cannot exceed 4 characters)</li>
   * <li>Use exactly as many columns as are needed for your piece, from its lowest to its highest
   * note.</li> <li>Each note-head is rendered as an "  X  ", and each note-sustain is rendered as
   * "  |  ". When a note is not played, five spaces are rendered (as "     ").</li> <li>As a
   * consequence: every line should be exactly the same length, as shown above.</li> <li>Every
   * item, including the last one, ends in a newline.</li>
   *
   * <p>For an example, view the following (the output will not have the border)</p>
   * <pre>
   * ╔════════════════════════════════════════════════╗
   * ║    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4 ║
   * ║ 0                 X                                            X                 ║
   * ║ 1                 |                                            |                 ║
   * ║ 2                 |                                  X                           ║
   * ║ 3                 |                                  |                           ║
   * ║ 4                 |                        X                                     ║
   * ║ 5                 |                        |                                     ║
   * ║ 6                 |                                  X                           ║
   * ║ 7                                                    |                           ║
   * ║ 8                 X                                            X                 ║
   * ║ 9                 |                                            |                 ║
   * ║10                 |                                            X                 ║
   * ║11                 |                                            |                 ║
   * ║12                 |                                            X                 ║
   * ║13                 |                                            |                 ║
   * ║14                 |                                            |                 ║
   * ║15                                                                                ║
   * ║16                 X                                  X                           ║
   * ║17                 |                                  |                           ║
   * ║18                 |                                  X                           ║
   * ║19                 |                                  |                           ║
   * ║20                 |                                  X                           ║
   * ║21                 |                                  |                           ║
   * ║22                 |                                  |                           ║
   * ║23                 |                                  |                           ║
   * ║24                 X                                            X                 ║
   * ║25                 |                                            |                 ║
   * ║26                                                                             X  ║
   * ║27                                                                             |  ║
   * ║28                                                                             X  ║
   * ║29                                                                             |  ║
   * ║30                                                                             |  ║
   * ║31                                                                             |  ║
   * ║32                 X                                            X                 ║
   * ║33                 |                                            |                 ║
   * ║34                 |                                  X                           ║
   * ║35                 |                                  |                           ║
   * ║36                 |                        X                                     ║
   * ║37                 |                        |                                     ║
   * ║38                 |                                  X                           ║
   * ║39                 |                                  |                           ║
   * ║40                 X                                            X                 ║
   * ║41                 |                                            |                 ║
   * ║42                 |                                            X                 ║
   * ║43                 |                                            |                 ║
   * ║44                 |                                            X                 ║
   * ║45                 |                                            |                 ║
   * ║46                 |                                            X                 ║
   * ║47                 |                                            |                 ║
   * ║48                 X                                  X                           ║
   * ║49                 |                                  |                           ║
   * ║50                 |                                  X                           ║
   * ║51                 |                                  |                           ║
   * ║52                 |                                            X                 ║
   * ║53                 |                                            |                 ║
   * ║54                 |                                  X                           ║
   * ║55                 |                                  |                           ║
   * ║56  X                                       X                                     ║
   * ║57  |                                       |                                     ║
   * ║58  |                                       |                                     ║
   * ║59  |                                       |                                     ║
   * ║60  |                                       |                                     ║
   * ║61  |                                       |                                     ║
   * ║62  |                                       |                                     ║
   * ║63  |                                       |                                     ║
   * ╚════════════════════════════════════════════════╝
   * </pre>
   *
   * <p>Returns "Your grammy is just a few clicks away! (There's nothing here...)" if the
   * composition is empty</p>
   *
   * Created by Alex on 3/4/2016.
   */
  String getStringRepresentation();

  /**
   * Returns the length in beats of the composition from 0 inclusive upwards
   *
   * @return a natural number of beats
   */
  int getLength();


}
