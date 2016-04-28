package cs3500.music.model.repeats;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.NoteImpl;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.RepeatBuilder;

/**
 * Model that handles repeats admirably.
 */
public class RepeatModel extends GenericMusicModel implements IRepeatModel {

  private final Map<Integer, Repeat> repeatMap;
  private int previousBeatVisited = -1;
  private int previousBeatAnswered = 0;

  public RepeatModel(int tempo) {
    super(tempo);
    this.repeatMap = new HashMap<>();
  }

  public static class Builder implements RepeatBuilder<IRepeatModel> {

    private int tempo = 1000;
    private Set<Note> notes = new HashSet<>();
    private Map<Integer, Repeat> repeats = new HashMap<>();

    @Override
    public RepeatBuilder<IRepeatModel> addGoToStartOnceRepeat(int beat) {
      this.repeats.put(beat, new GoToBeatOnce(beat, 0));
      return this;
    }

    @Override
    public RepeatBuilder<IRepeatModel> addGoToBeatOnceRepeat(int beat, int goTo) {
      this.repeats.put(beat, new GoToBeatOnce(beat, goTo));
      return this;
    }

    @Override
    public RepeatBuilder<IRepeatModel> addSkipSection(int goBackBeat, int goBackToBeat, int skipAtBeat) {
      Repeat r = new GoBackThenSkip(goBackBeat, goBackToBeat, skipAtBeat);
      repeats.put(skipAtBeat, r);
      repeats.put(goBackBeat, r);
      return this;
    }

    @Override
    public IRepeatModel build() {
      RepeatModel m = new RepeatModel(this.tempo);

      for(Map.Entry<Integer, Repeat> entry : repeats.entrySet()) {
        m.addRepeat(entry.getValue(), entry.getKey());
      }
      notes.forEach(m::addNote);
      return m;
    }

    @Override
    public CompositionBuilder<IRepeatModel> setTempo(int tempo) {
      if (tempo < 0) {
        throw new IllegalArgumentException("Cannot have a tempo less than 0");
      }
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<IRepeatModel> addNote(int start, int end, int instrument, int pitch, int volume) {
      if(end - start == 0) {
        end += 1;
      }
      notes.add(new NoteImpl(Note.midiToPitch(pitch), Note.midiToOctave(pitch), start, end - start,
              instrument, volume));
      return this;
    }
  }

  @Override
  public int nextBeat(int currentBeat) {
    if(currentBeat == previousBeatVisited) {
      return previousBeatAnswered;
    }

    if(this.repeatMap.containsKey(currentBeat)) {
      previousBeatVisited = currentBeat;
      previousBeatAnswered = this.repeatMap.get(currentBeat).nextBeat();
      return previousBeatAnswered;
    } else {
      previousBeatVisited = currentBeat;
      previousBeatAnswered = currentBeat + 1;
      return previousBeatAnswered;
    }
  }

  @Override
  public void reset() {
    for(Repeat r : repeatMap.values()) {
      r.reset();
    }
  }

  @Override
  public IRepeatModel addGoToStartOnceRepeat(int beat) {
    if(this.repeatMap.containsKey(beat)) {
      throw new IllegalStateException("Cannot have more than one repeat on a beat");
    } else {
      this.repeatMap.put(beat, new GoToBeatOnce(beat, 0));
    }

    return this;
  }

  @Override
  public IRepeatModel addGoToBeatOnceRepeat(int beat, int goTo) {
    if(this.repeatMap.containsKey(beat)) {
      throw new IllegalStateException("Cannot have more than one repeat on a beat");
    } else {
      this.repeatMap.put(beat, new GoToBeatOnce(beat, goTo));
    }

    return this;
  }

  @Override
  public IRepeatModel addAlternateRepeat(int goBackBeat, int goBackToBeat, int skipAtBeat) {
    if(this.repeatMap.containsKey(goBackBeat) || this.repeatMap.containsKey(skipAtBeat)) {
      throw new IllegalStateException("Cannot have more than one repeat on a beat");
    } else {
      Repeat r = new GoBackThenSkip(goBackBeat, goBackToBeat, skipAtBeat);
      this.repeatMap.put(skipAtBeat, r);
      this.repeatMap.put(goBackBeat, r);
    }

    return this;
  }

  @Override
  public Set<Repeat> getBasicRepeats() {
    Set<Repeat> out = new HashSet<>();
    for(Repeat r : this.repeatMap.values()) {
      if(r.isBasic()) {
        out.add(r);
      }
    }
    return out;
  }

  @Override
  public Map<Integer, Repeat> getAlternateRepeats() {
    Map<Integer, Repeat> out = new HashMap<>();
    for(Map.Entry<Integer, Repeat> e : this.repeatMap.entrySet()) {
      if(!e.getValue().isBasic()) {
        out.put(e.getKey(), e.getValue());
      }
    }
    return out;
  }

  private void addRepeat(Repeat r, int beat) {
    if(this.repeatMap.containsKey(beat)) {
      throw new IllegalStateException("Cannot have more than one repeat on the same beat");
    } else {
      this.repeatMap.put(beat, r);
    }
  }
}
