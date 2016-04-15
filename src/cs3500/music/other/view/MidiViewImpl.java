package cs3500.music.other.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.other.model.HeadBeat;
import cs3500.music.other.model.IBeat;
import cs3500.music.other.model.IMusic;
import cs3500.music.other.model.Note;
import cs3500.music.other.model.Pitch;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IMusicView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private boolean flag = false;
  private IMusic nt;


  public MidiViewImpl() {

    Synthesizer temps = null;
    Receiver rec = null;

    try {
      temps = MidiSystem.getSynthesizer();
      rec = temps.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    synth = temps;
    receiver = rec;
    try {
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }


  public MidiViewImpl(MockSynth sy, MockReceiver mr) {

    synth = sy;
    receiver = mr;
    try {
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
   * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
   * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
   * Synthesizer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
   * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li> <li>
   *   {@link
   * MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul> <li>{@link
   * MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li> </ul> </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki
   * /General_MIDI
   * </a>
   */

  public void playNote() throws InvalidMidiDataException, InterruptedException {


    MidiMessage start;
    MidiMessage stop;


    for (int i = 0; i < nt.getSize(); i++) {
      if (flag == true) {
        List<Note> lon = getNotes();
        for (Note n : lon) {
          if (i == n.getStarttime()) {
            start = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrumentt(),
                    n.getPitch().pitchNumber() + 23, n.getVolumee());
            this.receiver.send(start, n.getStarttime());
          }

          if (i == n.getEndTime()) {
            stop = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrumentt(),
                    n.getPitch().pitchNumber() + 23, n.getVolumee());
            this.receiver.send(stop, n.getEndTime());
          }
        }
      } else {
        i--;
      }

      try {
        Thread.sleep(nt.getTempo() / 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    this.receiver.close();

  }


  @Override
  public void initialize(IMusic m) throws InvalidMidiDataException, InterruptedException {
    this.nt = m;
    playNote();
  }


  public Receiver getReceiver() {
    return this.receiver;
  }


  public Synthesizer getSynth() {
    return this.synth;
  }



  //a method will resume the music.
  public void start() {
    flag = true;
  }


  //a method will stop the music.
  public void stop() {
    flag = false;
  }


  //a a helper method will resume the music.
  public void setFlag(Boolean b) {
    flag = b;
  }


  //a method will remove some note from  the music.
  public void remove(int x, int y) {
    List<Pitch> lop2 = getPitches();
    //reverse the list
    Collections.reverse(lop2);
    Pitch pp = lop2.get(y);
    int pn = pp.pitchNumber();
    HeadBeat hb;

    if (nt.getCollections().get(pp).get(x) instanceof HeadBeat) {
      hb = (HeadBeat) nt.getCollections().get(pp).get(x);
      int duration = hb.getEnd() - hb.getPosition();
      nt.remove(new Note(new Pitch(pn), duration), hb.getPosition());
    }
  }


  //a method will add some note to the composition based on the input information;

  /**
   *
   * @param x
   * @param y
   * @param duration
   * @param instrument
   * @param volume
   */
  public void addNote(int x, int y, int duration, int instrument, int volume) {
    List<Pitch> lop2 = getPitches();
    //reverse the list
    Collections.reverse(lop2);
    Pitch pp = lop2.get(y);
    int pn = pp.pitchNumber();

    Note n1 = new Note(new Pitch(pn), duration);
    n1.Set(x, x + duration, instrument, pn, volume);
    nt.addNote(n1, x);

  }

  private List<Note> getNotes() {

    int size = nt.getSize();
    Set<Pitch> lop = nt.getCollections().keySet();
    ArrayList<Pitch> lop1 = new ArrayList<Pitch>();


    //get the pitches that have value in it
    for (Pitch p : lop) {
      TreeMap<Integer, IBeat> hm1 = nt.getCollections().get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        lop1.add(p);
      }
    }
    //transfer all the head beats into a list of notes;
    ArrayList<Note> lon = new ArrayList<>();
    for (int j = 0; j < size; j++) {
      for (Pitch p : lop1) {
        if (nt.getCollections().get(p).get(j) instanceof HeadBeat) {
          HeadBeat b = (HeadBeat) nt.getCollections().get(p).get(j);
          Note n = new Note(b.getPitch(), b.getEnd() - b.getPosition());
          n.Set(b.getPosition(), b.getEnd(), b.getInstrument(),
                  b.getPitch().pitchNumber(), b.getVolume());
          lon.add(n);
        }
      }
    }
    return lon;
  }


  private ArrayList<Pitch> getPitches() {

    Set<Pitch> lop = nt.getCollections().keySet();
    ArrayList<Pitch> lop1 = new ArrayList<Pitch>();
    ArrayList<Pitch> lop2 = new ArrayList<Pitch>();


    for (Pitch p : lop) {
      TreeMap<Integer, IBeat> hm1 = nt.getCollections().get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        lop1.add(p);
      }
    }
    int min = lop1.get(0).hashCode();


    // get the pitches that are supposed to be displayed;
    int max = lop1.get(lop1.size() - 1).hashCode();
    for (int l = min; l < max + 1; l++) {
      Pitch p1 = new Pitch(l);
      lop2.add(p1);
    }

    return lop2;
  }


  //a method will remove a few notes from  the music.
  public void deleteAll(ArrayList<int[]> lop) {

    for (int i = 0; i < lop.size(); i++) {
      remove(lop.get(i)[0], lop.get(i)[1]);
    }

  }


  public void setMode(IMusic m) {
    this.nt = m;
  }



}
