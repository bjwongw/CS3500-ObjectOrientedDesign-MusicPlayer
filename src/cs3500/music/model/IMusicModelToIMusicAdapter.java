package cs3500.music.model;

import cs3500.music.other.model.*;
import cs3500.music.other.model.Note;

import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * Adapts an IMusicModel object to an IMusic class
 */
public class IMusicModelToIMusicAdapter implements IMusic {
  private final IMusicModel adaptee;

  /**
   * Constructs an IMusicModelToIMusicAdapter
   *
   * @param model the adaptee
   */
  public IMusicModelToIMusicAdapter(IMusicModel model) {
    this.adaptee = Objects.requireNonNull(model);
  }

  @Override
  public void playSimultaneously(IMusic m) {
    // do nothing because this is not needed
  }

  @Override
  public void playConsecutively(IMusic m) {
    // do nothing because this is not needed
  }

  @Override
  public void addNote(Note n, int startPosition) {
    // do nothing because this is not needed
  }

  @Override
  public void remove(Note n, int startPosition) {
    // do nothing because this is not needed
  }

  @Override
  public void rise(Note n, int startPosition) {
    // do nothing because this is not needed
  }

  @Override
  public void fall(Note n, int startPosition) {
    // do nothing because this is not needed
  }

  /**
   * Converts a MIDI pitch into an other.model.Pitch object. Uses some workarounds due to
   * differences of assumptions and invariants.
   *
   * @param midiPitch the MIDI pitch to convert
   * @return the Pitch translation of the given MIDI pitch
   */
  private static Pitch midiPitchToOtherPitch(int midiPitch) {
    // creates a dummy pitch to mutate because the other implementation assumes different
    // invariants and has a different scale for pitches (it's a gross but necessary workaround)
    Pitch result = new Pitch(1);
    result.setOctave(cs3500.music.model.Note.midiToOctave(midiPitch)); // sets the octave
    result.setPitch(Pitch.MusicPitch.values()[midiPitch % 12]); // sets the pitch
    return result;
  }

  @Override
  public TreeMap<Pitch, TreeMap<Integer, IBeat>> getCollections() {
    cs3500.music.model.Note lowestNote = this.adaptee.getLowestNote();
    cs3500.music.model.Note highestNote = this.adaptee.getHighestNote();
    int lowestPitch = lowestNote.getMidiPitch();
    int highestPitch = highestNote.getMidiPitch();

    // creates the TreeMaps for the range of Pitches
    TreeMap<Pitch, TreeMap<Integer, IBeat>> result = new TreeMap<>();
    for (int i = lowestPitch; i <= highestPitch; i++) {
      Pitch pitch = midiPitchToOtherPitch(i);
      result.put(pitch, new TreeMap<>());
    }

    Set<cs3500.music.model.Note> adapteeNotes = this.adaptee.getNotes();

    // creates the HeadBeat and TailBeats for each of the notes in the model
    for (cs3500.music.model.Note n : adapteeNotes) {
      Pitch pitch = midiPitchToOtherPitch(n.getMidiPitch());
      TreeMap<Integer, IBeat> beatMap = result.get(pitch);
      int noteEnd = n.getStart() + n.getDuration() - 1;
      IBeat head = new HeadBeat(pitch, n.getStart(), noteEnd, n.getInstrument(), n.getVolume());
      beatMap.put(n.getStart(), head);
      if (n.getStart() != noteEnd) {
        for (int i = n.getStart() + 1; i <= noteEnd; i++) {
          IBeat tail = new TailBeat(pitch, i);
          beatMap.put(i, tail);
        }
      }
    }
    return result;
  }

  @Override
  public int getSize() {
    return this.adaptee.finalBeat();
  }

  @Override
  public void prepareAddNote() {
    // do nothing because this is not needed
  }

  @Override
  public int getTempo() {
    return this.adaptee.getTempo();
  }

  @Override
  public String print() {
    return "";
  }
}
