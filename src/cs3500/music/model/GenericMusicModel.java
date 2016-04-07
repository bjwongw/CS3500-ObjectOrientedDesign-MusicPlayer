package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
   * @throws IllegalArgumentException if the given tempo is less than 0
   */
  public GenericMusicModel(int tmp) {
    if (tmp < 0) {
      throw new IllegalArgumentException("Cannot have a tempo less than 0");
    }
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
      if (tempo < 0) {
        throw new IllegalArgumentException("Cannot have a tempo less than 0");
      }
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument, int pitch,
                                                   int volume) {
      notes.add(new Note(Note.midiToPitch(pitch), Note.midiToOctave(pitch), start, end - start + 1,
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
        if (first) {
          first = false;
          lowest = n;
        }
        if (n.compareTo(lowest) < 0) {
          lowest = n;
        }
      }
    }
    if (first) {
      throw new IllegalStateException("No notes in composition");
    }
    return lowest;
  }

  @Override
  public Note getHighestNote() {
    Note highest = null;
    boolean first = true;
    for (Map.Entry<Integer, Set<Note>> e : this.notes.entrySet()) {
      for (Note n : e.getValue()) {
        if (first) {
          first = false;
          highest = n;
        } else if (n.compareTo(highest) > 0) {
          highest = n;
        }
      }
    }
    if (first) {
      throw new IllegalStateException("No notes in composition");
    }
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
    Note lowestNote;
    Note highestNote;

    try {
      lowestNote = this.getLowestNote();
      highestNote = this.getHighestNote();
    } catch (IllegalStateException e) {
      return new ArrayList<>();
    }

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
  public Set<Note> sustainedNotes(int beat) {
    if (beat < 0) {
      throw new IllegalArgumentException("Cannot have a negative start time");
    }
    Set<Note> sustainedNotes = new HashSet<>();
    for (int i : this.notes.keySet()) {
      if (i <= beat) {
        for (Note n : this.notes.get(i)) {
          if (n.getStart() < beat && n.playsDuring(beat)) {
            sustainedNotes.add(n);
          }
        }
      }
    }
    return sustainedNotes;
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
}
