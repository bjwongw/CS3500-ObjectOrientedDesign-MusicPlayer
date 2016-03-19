package cs3500.music.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Specifies operations for any generic music model
 */
public class GenericMusicModel implements IMusicModel {

  private final Map<Integer, Set<Note>> notes;
  private Note lowestNote;
  private Note highestNote;

  /**
   * Constructs the default Generic Music Model.
   */
  public GenericMusicModel() {
    this.notes = new TreeMap<>();
  }

  /**
   * Returns the set of Notes
   *
   * @return the set of Notes
   */
  public Set<Note> getNotes() {
    Set<Note> result = new HashSet<>();
    for (Set<Note> s : this.notes.values()) {
      for (Note n : s) {
        result.add(n);
      }
    }
    return result;
  }

  @Override
  public Set<Note> notesToPlay(int beat) {
    if (beat < 0) {
      throw new IllegalArgumentException("Cannot have a negative start time");
    }
    return new HashSet<>(this.notes.get(beat));
  }

  @Override
  public void addNote(Note note) {
    if (!this.notes.containsKey(note.getStart())) {
      Set<Note> set = new HashSet<>();
      set.add(note);
      notes.put(note.getStart(), set);
    } else {
      Set<Note> set = this.notes.get(note.getStart());
      if (set.contains(note)) {
        return;
      }
      set.add(note);
    }

    if(note.compareTo(this.lowestNote) < 0) {
      this.lowestNote = note;
    }
    if(note.compareTo(this.highestNote) > 0) {
      this.highestNote = note;
    }
  }

  @Override
  public void removeNote(Note note) {
    Set<Note> noteValues = notes.get(note.getStart());
    if (noteValues != null) {
      if (!noteValues.remove(note)) { // this call removes the item if it's in the set
        throw new IllegalArgumentException("That note was not in this model");
      }
    } else {
      throw new IllegalArgumentException("That note was not in this model");
    }
  }

  @Override
  public void editNote(Note note, Note.Pitch pitch, int octave, int start, int duration, int
          volume, int instrument) {
    if (start < 0) {
      throw new IllegalArgumentException("Cannot have a negative start time");
    }
    removeNote(note);
    addNote(new Note(pitch, octave, start, duration, volume, instrument));
  }

  @Override
  public void combinePieces(IMusicModel otherMusic) {
    Set<Note> otherNotes = otherMusic.getNotes();
    otherNotes.forEach(this::addNote);
  }

  @Override
  public void addMusicToTail(IMusicModel otherMusic) {
    int lastBeat = maxBeat();
    Set<Note> otherNotes = otherMusic.getNotes();
    for (Note n : otherNotes) {
      this.addNote(new Note(n.getPitch(), n.getOctave(), n.getStart() + lastBeat, n.getDuration
              (), n.getVolume(), n.getInstrument()));
    }
  }

  /**
   * Determines the last beat to occur in this model.
   *
   * @return the last beat to occur in the this model.
   */
  private int maxBeat() {
    int maxBeat = 0;
    for (Note n : this.getNotes()) {
      int endOfBeat = n.getStart() + (n.getDuration() - 1); // -1 because the start value counts
      // as a beat
      if (endOfBeat > maxBeat) {
        maxBeat = endOfBeat;
      }
    }
    return maxBeat;
  }

  /**
   * Creates a list containing every pitch string between the lowestNote and highestNote (inclusively).
   *
   * @param lowestPitch  the lower bound
   * @param highestPitch the upper bound
   * @return a list of all the Pitches from the lowestPitch to the highestPitch
   */
  private List<String> createPitchRange(Note lowestPitch, Note highestPitch) {
    List<String> result = new ArrayList<>();
    List<Note.Pitch> pitches = Arrays.asList(Note.Pitch.values());

    int lowestOctave = lowestPitch.getOctave();
    int highestOctave = highestPitch.getOctave();

    int lowestPitchSymbolIdx = lowestPitch.getPitch().ordinal();
    int highestPitchSymbolIdx = highestPitch.getPitch().ordinal();

    int pitchSpread = ((highestPitchSymbolIdx - lowestPitchSymbolIdx) % pitches.size());
    int totalPitches = ((highestOctave - lowestOctave) * pitches.size()) + pitchSpread;

    for (int i = lowestPitchSymbolIdx; i <= lowestPitchSymbolIdx + totalPitches; i++) {
      if (i % pitches.size() == 0 && i != lowestPitchSymbolIdx) {
        lowestOctave += 1;
      }
      Note.Pitch pitchSymbol = pitches.get(i % pitches.size());
      result.add(pitchSymbol.getStringWithOctave(lowestOctave));
    }
    return result;
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
   * Creates a list of strings. The first entry is the given pitch string. It is centered
   * based on the given width parameter (e.g. if width was 5 and the Pitch was C#, the resulting
   * entry would be "  C# ". The following strings represent either rests (lack of a note
   * represented by a string full of spaces), or notes. The start of a note is represented by an X,
   * which is also centered like the pitch (e.g. "  X  "). If the note is more than one duration
   * long, each following beat that the note is held is represented by a | (e.g. "  |  ").
   *
   * <p>It is assumed that the given set of notes are all in the same pitch as the one given.</p>
   *
   * @param pitchString  the pitch for this list of strings
   * @param notes  all the notes corresponding to the given pitch
   * @param length the length of the list (number of beats + 1 for the pitch symbol)
   * @param width  the desired length of each string
   * @return a list of strings corresponding to the rests and notes for the given pitch.
   */
  private List<String> createPitchStrings(String pitchString, Set<Note> notes, int length, int width) {
    List<String> result = new ArrayList<>(length);

    result.add(centerString(pitchString, width));

    for(Note n : notes) {
      if(n.toString() == pitchString) {
        result.add(n.getStart(), centerString("X", width));
        for(int i = n.getStart() + 1; i < n.getStart() + n.getDuration(); i++) {
          result.add(i, centerString("|", width));
        }
      }
    }

    for(int i = 0; i < length; i ++) {
      if(result.get(i) == null) {
        result.add(i, centerString("", width));
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

  @Override
  public String printMusic() {
    StringBuilder sb = new StringBuilder();
    if (notes.isEmpty()) {
      sb.append("");
    } else {
      List<String> pitchRange = createPitchRange(this.lowestNote, this.highestNote);
      List<String> beatNumbers = createBeatRange(maxBeat());
      List<List<String>> pitchNotes = new ArrayList<>(pitchRange.size());

      int k = 0;
      for(String s : pitchRange) {
        pitchNotes.add(createPitchStrings(pitchRange.get(k), this.getNotes(), this.maxBeat() + 1, 5));
        k++;
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
    //System.out.println(sb.toString());
    return sb.toString();
  }
}
