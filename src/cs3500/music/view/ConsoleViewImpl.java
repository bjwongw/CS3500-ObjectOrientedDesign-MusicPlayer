package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import java.io.IOException;
import java.util.*;

/**
 * A view to put the text version of the given composition to console.
 */
public class ConsoleViewImpl implements View {
  private IMusicModel model;
  private Appendable output;

  /**
   * Constructor for the ConsoleViewImpl
   *
   * @param output the output of this view
   */
  public ConsoleViewImpl(Appendable output) {
    this.output = output;
  }

  /**
   * Creates a list of numbers represented by Strings that are right-justified and padded with
   * leading spaces so that the length of each string is equal to the number of digits in the given
   * argument highestBeat.
   *
   * @param highestBeat the highest beat that needs to be represented in this range
   * @return a list of numbers represented by strings, starting from 0.
   */
  private List<String> createBeatRange(int highestBeat) {
    int padding = String.valueOf(highestBeat).length();
    String format = "%" + Integer.toString(padding) + "s";
    List<String> result = new ArrayList<>();
    result.add(String.format(format, ""));
    for (int i = 0; i <= highestBeat; i++) {
      result.add(String.format(format, Integer.toString(i)));
    }
    return result;
  }

  /**
   * Creates a list of strings. The first entry is the given pitch string. It is centered based on
   * the given width parameter (e.g. if width was 5 and the Pitch was C#, the resulting entry would
   * be "  C# ". The following strings represent either rests (lack of a note represented by a
   * string full of spaces), or notes. The start of a note is represented by an X, which is also
   * centered like the pitch (e.g. "  X  "). If the note is more than one duration long, each
   * following beat that the note is held is represented by a | (e.g. "  |  ").
   *
   * <p>It is assumed that the given set of notes are all in the same pitch as the one given.</p>
   *
   * @param pitchString the pitch for this list of strings
   * @param notes       all the notes corresponding to the given pitch
   * @param length      the length of the list (number of beats + 1 for the pitch symbol)
   * @param width       the desired length of each string
   * @return a list of strings corresponding to the rests and notes for the given pitch.
   */
  private List<String> createPitchStrings(String pitchString, Set<Note> notes, int length, int
    width) {
    List<String> result = new ArrayList<>(length);

    for (int i = 0; i < length; i++) {
      result.add(i, centerString("", width));
    }

    result.add(0, centerString(pitchString, width));

    if(!(notes == null)) {
      for (Note n : notes) {
        if (n.toString().equals(pitchString)) {
          result.set(n.getStart() + 1, centerString("X", width));
          for (int i = n.getStart() + 1; i < n.getStart() + n.getDuration(); i++) {
            if (!Objects.equals(result.get(i+1), centerString("X", width))) { // prevents
              // overriding a note start
              result.set(i+1, centerString("|", width));
            }
          }
        }
      }
    }

    return result;
  }

  /**
   * Centers the given string, fitting it into the given width. If the size of the string is larger
   * than the given width, the string simply cuts off the excess from the right to fit within the
   * desired width. If the given string cannot be perfectly centered, it is skewed one space to the
   * right.
   *
   * @param str   the string to center
   * @param width the desired width of the returned string
   * @return the given string centered in the given width
   */
  private String centerString(String str, int width) {
    if (str.length() >= width) {
      return str.substring(0, width);
    } else {
      int padSize = width - str.length();
      int padEnd = str.length() + padSize / 2;
      str = String.format("%-" + padEnd + "s", str);
      return String.format("%" + width + "s", str);
    }
  }

  /**
   * Returns all notes in this composition as a map from the pitch string of a note ("pitchoctave")
   * to the set of notes with that pitch string.
   *
   * @return the map from pitch string to set of notes
   */
  private Map<String, Set<Note>> getPitchMap(Set<Note> notes) {
    Map<String, Set<Note>> result = new HashMap<>();
    for(Note n : notes) {
      if(!result.containsKey(n.toString())) {
        result.put(n.toString(), new HashSet<>());
      }
      result.get(n.toString()).add(n);
    }
    return result;
  }

  /**
   * Returns a String that represents all the notes in this model. The result is also printed into
   * the console.
   *
   * <p>The return String is viewed as a table. The leftmost column shows all the beats in the
   * composition, from 0 up to the last beat played by the last note in the model. The next column
   * to the right is the lowest pitch in this model, with every ascending pitch in the columns to
   * the right. The rightmost pitch represented is the highest pitch in this model.</p>
   *
   * <p>The start of each note is marked by an 'X', which is placed on its respective start beat
   * and pitch. If the note is more than one beat long, each subsequent beat that the note is
   * sustained will be represented by a '|'. Rests in the piece are represented as whitespace.
   * </p>
   *
   * @return a String that represents all the Notes in this model
   */
  private String printMusic() {
    StringBuilder sb = new StringBuilder();
    Set<Note> notes = this.model.getNotes();
    if (notes.isEmpty()) {
      sb.append("");
    } else {
      List<String> pitchRange = this.model.getPitchRange();
      List<String> beatNumbers = createBeatRange(this.model.finalBeat());
      List<List<String>> pitchNotes = new ArrayList<>(pitchRange.size());
      Map<String, Set<Note>> noteMap = this.getPitchMap(notes);

      int lastBeat = this.model.finalBeat();
      for (int k = 0; k < pitchRange.size(); k++) {
        pitchNotes.add(createPitchStrings(pitchRange.get(k), noteMap.get(pitchRange.get(k)),
          lastBeat + 1, 5));
      }

      boolean first = true;
      for (int i = 0; i < beatNumbers.size(); i++) {
        if (!first) {
          sb.append("\n");
        }
        sb.append(beatNumbers.get(i));
        for (List<String> pitchNote : pitchNotes) {
          sb.append(pitchNote.get(i));
        }
        first = false;
      }
    }
    return sb.toString();
  }

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;

    try {
      output.append(printMusic());
    } catch (IOException io) {
      io.printStackTrace();
    }

  }

  @Override
  public void play() {
    // do nothing
  }

  @Override
  public void pause() {
    // do nothing
  }

  @Override
  public void reset() {
    // do nothing
  }

  @Override
  public boolean addTickHandler(Runnable r) {
    return false;
  }
}
