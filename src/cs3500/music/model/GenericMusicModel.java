package cs3500.music.model;

import java.util.*;
import java.util.stream.Collectors;

import cs3500.music.util.CompositionBuilder;

/**
 * Specifies operations for any generic music model
 */
public class GenericMusicModel implements IMusicModel {

  private final Map<Integer, Set<Note>> notes;
  private int tempo;

  /**
   * Constructs the default Generic Music Model.
   */
  public GenericMusicModel() {
    this.notes = new HashMap<>();
    this.tempo = 100000;
  }

  /**
   * Constructs a GenericMusicModel with the given tempo.
   *
   * @param tmp the desired tempo of this model
   */
  public GenericMusicModel(int tmp) {
    this.notes = new HashMap<>();
    this.tempo = tmp;
  }

  /**
   * Builder class for the GenericMusicModel. Implements CompositionBuilder so that it can build
   * the model from files.
   */
  public static final class Builder implements CompositionBuilder<IMusicModel> {

    private int tempo = 1000;
    private Set<Note> notes = new HashSet<>();

    @Override
    public IMusicModel build() {
      IMusicModel m = new GenericMusicModel(tempo);
      notes.forEach(m::addNote);
      return m;
    }

    @Override
    public CompositionBuilder<IMusicModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument, int pitch,
      int volume) {
      notes.add(new Note(Note.midiToPitch(pitch), Note.midiToOctave(pitch), start, end - start,
        instrument, volume));
      return this;
    }
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
  public void editNote(Note note, Note newNote) {
    removeNote(note);
    addNote(newNote);
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public Set<Note> getNotes() {
    Set<Note> result = new HashSet<>();
    for (Set<Note> n : this.notes.values()) {
      result.addAll(n.stream().collect(Collectors.toList()));
    }
    return result;
  }

  @Override
  public Note getLowestNote() {
    Note lowest = null;
    boolean first = true;
    for (Map.Entry<Integer, Set<Note>> e : this.notes.entrySet()) {
      for (Note n : e.getValue()) {
        if(first) {
          first = false;
          lowest = n;
        }
        if(n.compareTo(lowest) < 0) {
          lowest = n;
        }
      }
    }
    if(first) { throw new IllegalStateException("No notes in composition"); }
    return lowest;
  }

  @Override
  public Note getHighestNote() {
    Note highest = null;
    boolean first = true;
    for (Map.Entry<Integer, Set<Note>> e : this.notes.entrySet()) {
      for (Note n : e.getValue()) {
        if(first) {
          first = false;
          highest = n;
        } else if(n.compareTo(highest) > 0) {
          highest = n;
        }
      }
    }
    if(first) { throw new IllegalStateException("No notes in composition"); }
    return highest;
  }

  @Override
  public int finalBeat() {
    int finalBeat = 0;
    for (Note n : this.getNotes()) {
      int endOfBeat = n.getStart() + (n.getDuration() - 1); // -1 because the start value counts
      // as a beat
      if (endOfBeat > finalBeat) {
        finalBeat = endOfBeat;
      }
    }
    return finalBeat;
  }

  @Override
  public List<String> getPitchRange() {
    List<String> result = new ArrayList<>();
    List<Note.Pitch> pitches = Arrays.asList(Note.Pitch.values());

    Note lowestNote = this.getLowestNote();
    Note highestNote = this.getHighestNote();
    int lowestOctave = lowestNote.getOctave();
    int highestOctave = highestNote.getOctave();

    int lowestPitchSymbolIdx = lowestNote.getPitch().ordinal();
    int highestPitchSymbolIdx = highestNote.getPitch().ordinal();

    int pitchSpread = ((highestPitchSymbolIdx - lowestPitchSymbolIdx) % pitches.size());
    int totalPitches = ((highestOctave - lowestOctave) * pitches.size()) + pitchSpread;

    for (int i = lowestPitchSymbolIdx; i <= lowestPitchSymbolIdx + totalPitches; i++) {
      if (i % pitches.size() == 0 && i != lowestPitchSymbolIdx) {
        lowestOctave += 1;
      }
      Note.Pitch pitchSymbol = pitches.get(i % pitches.size());
      result.add(pitchSymbol.toString() + lowestOctave);
    }
    return result;
  }

  @Override
  public Set<Note> notesToPlay(int beat) {
    if (beat < 0) {
      throw new IllegalArgumentException("Cannot have a negative start time");
    }
    Set<Note> s = this.notes.get(beat);
    if (s == null) {
      return new HashSet<>();
    }
    return new HashSet<>(s);
  }

  @Override
  public void combinePieces(IMusicModel otherMusic) {
    Set<Note> otherNotes = otherMusic.getNotes();
    otherNotes.forEach(this::addNote);
  }

  @Override
  public void addMusicToTail(IMusicModel otherMusic) {
    int lastBeat = finalBeat();
    Set<Note> otherNotes = otherMusic.getNotes();
    for (Note n : otherNotes) {
      this.addNote(new Note(n.getPitch(), n.getOctave(), n.getStart() + lastBeat, n.getDuration
              (), n.getVolume(), n.getInstrument()));
    }
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
          result.add(n.getStart() + 1, centerString("X", width));
          for (int i = n.getStart() + 1; i < n.getStart() + n.getDuration(); i++) {
            result.add(i+1, centerString("|", width));
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
  private Map<String, Set<Note>> getPitchMap() {
    Map<String, Set<Note>> result = new HashMap<>();
    for(Note n : this.getNotes()) {
      if(!result.containsKey(n.toString())) {
        result.put(n.toString(), new HashSet<>());
      }
      result.get(n.toString()).add(n);
    }
    return result;
  }

  @Override
  public String printMusic() {
    StringBuilder sb = new StringBuilder();
    if (notes.isEmpty()) {
      sb.append("");
    } else {
      List<String> pitchRange = getPitchRange();
      List<String> beatNumbers = createBeatRange(finalBeat());
      List<List<String>> pitchNotes = new ArrayList<>(pitchRange.size());

      Map<String, Set<Note>> noteMap = this.getPitchMap();
      int k = 0;
      for (String s : pitchRange) {
        pitchNotes.add(createPitchStrings(pitchRange.get(k), noteMap.get(pitchRange.get(k)),
          this.finalBeat() + 1, 5));
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
    return sb.toString();
  }
}
