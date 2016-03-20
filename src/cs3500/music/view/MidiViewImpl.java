package cs3500.music.view;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.sound.midi.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IMusicView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private final IMusicModel model;
  private Queue<Integer> channels;

  public MidiViewImpl(IMusicModel model) {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.model = model;
      this.channels = new LinkedList<>();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */
  public void playNote() throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  private void playNote(Note n) {
    MidiMessage start;
    MidiMessage stop;
    try {
      start = new ShortMessage(ShortMessage.NOTE_ON, getChannel(n.getInstrument()),
        n.getMidiPitch(), n.getVolume());
      stop = new ShortMessage(ShortMessage.NOTE_OFF, getChannel(n.getInstrument()),
        n.getMidiPitch(), n.getVolume());
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException(e);
    }
    long duration = this.synth.getMicrosecondPosition() + n.getDuration() * model.getTempo();

    this.receiver.send(start, -1);
    this.receiver.send(stop, duration);
  }

  /**
   * Finds a channel in this.synth that is playing the given instrument. If no such channel can be
   * found, chooses the least recently "allocated" channel of this method, and sets its program to
   * instrument.
   *
   * Does not extend beyond the 16 channels guaranteed by MIDI.
   *
   * @param instrument the midi instrument value
   * @return the index of the channel
   */
  private int getChannel(int instrument) {
    MidiChannel[] channelArray = this.synth.getChannels();
    for(int i : this.channels) {
      if(channelArray[i].getProgram() == instrument) {
        return i;
      }
    }
    for(int i = 0; i < 16; i ++) {
      if(channelArray[i] != null && !this.channels.contains(i)) {
        channelArray[i].programChange(instrument);
        this.channels.add(i);
        return i;
      }
    }
    int i = this.channels.remove();
    channelArray[i].programChange(instrument);
    this.channels.add(i);
    return i;
  }

  @Override
  public void initialize() {

  }

  @Override
  public void update(int beat) {
    this.model.notesToPlay(beat).forEach(this::playNote);
  }
}
