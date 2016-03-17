package cs3500.music.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Specifies operations for any generic music model
 */
public class GenericMusicModel implements IMusicModel {

  private final TreeMap<Pitch, TreeSet<Note>> notes;

  /**
   * Constructs the default Generic Music Model.
   */
  public GenericMusicModel() {
    this.notes = new TreeMap<>();
  }

  /**
   * Returns the list of Notes
   *
   * @return the list of Notes
   */
  public List<Note> getNotes() {
    List<Set<Note>> setNotesList = new ArrayList<>(notes.values());
    List<Note> result = new ArrayList<>();
    setNotesList.forEach(result::addAll);
    return result;
  }

  @Override
  public List<Note> notesToPlay(int beat) {
    if (beat < 0) { throw new IllegalArgumentException("Cannot have a negative start time"); }
    List<Note> result = new ArrayList<>();
    for (Set<Note> setNotes : notes.values()) {
      result
        .addAll(setNotes.stream().filter(n -> n.getStart() == beat).collect(Collectors.toList()));
    }
    return result;
  }

  @Override
  public void addNote(Note note) {
    if (!notes.containsKey(note.getPitch())) {
      TreeSet<Note> set = new TreeSet<>();
      set.add(note);
      notes.put(note.getPitch(), set);
    } else {
      TreeSet<Note> noteValues = notes.get(note.getPitch()); // all the same Pitch
      int noteEnd = (note.getStart() + note.getDuration()) - 1;
      if (noteValues.contains(note)) { return ; } // given note is a duplicate, so it's ignored
      boolean needToAdd = true;
      for (Note n : noteValues) { // notes are ordered by start time since they're in a TreeSet
        int curNoteEnd = (n.getStart() + n.getDuration()) - 1;
        if (n.getStart() == note.getStart()) { // notes have the same start
          if (n.getDuration() > note.getDuration()) {
            needToAdd = false; // note with same start has longer duration, so don't add
            break;
          } else {
            removeNote(n);
          }
        } else if (note.getStart() >= n.getStart() && note.getStart() <= curNoteEnd) {
          n.changeDuration(note.getStart() - n.getStart()); // sets the current note to end just
                                                            // before the start of the given note
        } else if (note.getStart() <= n.getStart() && noteEnd >= n.getStart()) {
          note.changeDuration(n.getStart() - note.getStart()); // sets the given note to end just
                                                          // before the start of the current note
        }
      }
      if (needToAdd) { noteValues.add(note); }
    }
  }

  @Override
  public void removeNote(Note note) {
    Set<Note> noteValues = notes.get(note.getPitch());
    if (noteValues != null) {
      if (!noteValues.remove(note)) { // this call removes the item if it's in the set
        throw new IllegalArgumentException("That note was not in this model");
      }
    }
    else {
      throw new IllegalArgumentException("That note was not in this model");
    }
  }

  @Override
  public void editNote(Note note, Pitch pitch, int start) {
    if (start < 0) { throw new IllegalArgumentException("Cannot have a negative start time"); }
    removeNote(note);
    note.changePitch(pitch);
    note.changeStart(start);
    addNote(note);
  }

  @Override
  public void combinePieces(IMusicModel otherMusic) {
    List<Note> otherNotes = otherMusic.getNotes();
    otherNotes.forEach(this::addNote);
  }

  @Override
  public void addMusicToTail(IMusicModel otherMusic) {
    int lastBeat = maxBeat();
    List<Note> otherNotes = otherMusic.getNotes();
    for (Note n : otherNotes) {
      n.changeStart(n.getStart() + lastBeat);
      n.changeDuration(n.getDuration() + lastBeat);
      this.addNote(n);
    }
  }

  /**
   * Determines the last beat to occur in this model.
   *
   * @return the last beat to occur in the this model.
   */
  private int maxBeat() {
    int maxBeat = 0;
    for (Set<Note> noteSet : notes.values()) {
      for (Note n : noteSet) {
        int endOfBeat = n.getStart() + (n.getDuration() - 1); // -1 because the start value counts
                                                              // as a beat
        if (endOfBeat > maxBeat) { maxBeat = endOfBeat; }
      }
    }
    return maxBeat;
  }

  /**
   * Creates a list containing every pitch between the lowestNote and highestNote (inclusively).
   *
   * @param lowestPitch the lower bound
   * @param highestPitch the upper bound
   * @return a list of all the Pitches from the lowestPitch to the highestPitch
   */
  private List<Pitch> createPitchRange(Pitch lowestPitch, Pitch highestPitch) {
    List<Pitch> result = new ArrayList<>();
    List<Pitch.PitchSymbol> pitches = Arrays.asList(Pitch.PitchSymbol.values());
    int lowestOctave = lowestPitch.getOctave();
    int highestOctave = highestPitch.getOctave();
    int lowestPitchSymbolIdx = lowestPitch.getPitchSymbol().ordinal();
    int highestPitchSymbolIdx = highestPitch.getPitchSymbol().ordinal();
    int pitchSpread = ((highestPitchSymbolIdx - lowestPitchSymbolIdx) % pitches.size());
    int totalPitches = ((highestOctave - lowestOctave) * pitches.size()) + pitchSpread;
    for (int i = lowestPitchSymbolIdx; i <= lowestPitchSymbolIdx + totalPitches; i++) {
      if (i % pitches.size() == 0 && i != lowestPitchSymbolIdx) { lowestOctave += 1; }
      Pitch.PitchSymbol pitchSymbol = pitches.get(i % pitches.size());
      result.add(new Pitch(pitchSymbol, lowestOctave));
    }
    return result;
  }

  /**
   * Creates a list of numbers represented by Strings that are right-justified and padded with
   * leading spaces so that the length of each string is equal to the number of digits in the
   * given argument highestBeat.
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
   * Creates a list of strings. The first entry is the given Pitch in string form. It is centered
   * based on the given width parameter (e.g. if width was 5 and the Pitch was C#, the resulting
   * entry would be "  C# ". The following strings represent either rests (lack of a note
   * represented by a string full of spaces), or notes. The start of a note is represented by an
   * X, which is also centered like the pitch (e.g. "  X  "). If the note is more than one duration
   * long, each following beat that the note is held is represented by a | (e.g. "  |  ").
   *
   * <p>It is assumed that the given set of notes are all in the same pitch as the one given.</p>
   *
   * @param pitch the pitch for this list of strings
   * @param notes all the notes corresponding to the given pitch
   * @param length the length of the list (number of beats + 1 for the pitch symbol)
   * @param width the desired length of each string
   * @return a list of strings corresponding to the rests and notes for the given pitch.
   */
  private List<String> createPitchStrings(Pitch pitch, Set<Note> notes, int length, int width) {
    String pitchString = centerString(pitch.toString(), width);
    List<String> result = new ArrayList<>();
    result.add(pitchString);
    length -= 1;
    if (notes == null || notes.isEmpty()) {
      for (int i = 0; i < length; i++) {
        result.add(centerString("", width));
      }
    } else {
      int curBeat = 0;
      for (Note n : notes) {
        while (curBeat != n.getStart() && curBeat < length) {
          result.add(centerString("", width));
          curBeat += 1;
        }
        if (n.getStart() == curBeat) {
          result.add(centerString("X", width));
          for (int i = 1; i < n.getDuration(); i++) {
            result.add(centerString("|", width));
          }
          curBeat += n.getDuration();
        }
      }
      if (curBeat != length) {
        while (curBeat < length) {
          result.add(centerString("", width));
          curBeat += 1;
        }
      }
    }
    return result;
  }

  /**
   * Centers the given string, fitting it into the given width. If the size of the string
   * is larger than the given width, the string simply cuts off the excess from the right to
   * fit within the desired width. If the given string cannot be perfectly centered, it is
   * skewed one space to the right.
   *
   * @param str the string to center
   * @param width the desired width of the returned string
   * @return the given string centered in the given width
   */
  private String centerString(String str, int width) {
    if (str.length() >= width) { return str.substring(0, width); }
    else {
      int padSize = width - str.length();
      int padEnd = str.length() + padSize / 2;
      str = String.format("%-" + padEnd + "s", str);
      return String.format("%" + width  + "s", str);
    }
  }

  @Override
  public String printMusic() {
    StringBuilder sb = new StringBuilder();
    if (notes.isEmpty()) {
      sb.append("");
    } else {
      Pitch lowestNote = notes.firstKey();
      Pitch highestNote = notes.lastKey();
      List<Pitch> pitchRange = createPitchRange(lowestNote, highestNote);
      List<String> beatNumbers = createBeatRange(maxBeat());
      List<List<String>> pitchNotes = new ArrayList<>(pitchRange.size());
      pitchNotes.addAll(
        pitchRange.stream().map(p -> createPitchStrings(p, notes.get(p), beatNumbers.size(), 5))
          .collect(Collectors.toList()));
      boolean first = true;
      for (int i = 0; i < beatNumbers.size(); i++) {
        if (!first) { sb.append("\n"); }
        sb.append(beatNumbers.get(i));
        for (List<String> pitchNote : pitchNotes) {
          sb.append(pitchNote.get(i));
        }
        first = false;
      }
    }
    System.out.println(sb.toString());
    return sb.toString();
  }
}
