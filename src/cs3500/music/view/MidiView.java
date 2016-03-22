package cs3500.music.view;

import sun.rmi.runtime.Log;

import java.io.Console;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.midi.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * The audio view (in MIDI) for an IMusicModel.
 */
public class MidiView implements IMusicView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private final IMusicModel model;
  private Queue<Integer> channels;

  public MidiView(IMusicModel model) {
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
   * Given a note, plays on the MIDI synthesizer associated with this object. Plays the note
   * immediately regardless of its internal start. Sustains the note for its duration, relative to
   * the tempo of the song. If the midi objects are broken, throws a runtime exception.
   *
   * @param n the note to play
   */
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
    for (int i : this.channels) {
      if (channelArray[i].getProgram() == instrument) {
        return i;
      }
    }
    for (int i = 0; i < 16; i++) {
      if (channelArray[i] != null && !this.channels.contains(i)) {
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
    System.console().printf("MIDI view does not need initialization.");
  }

  @Override
  public void update(int beat) {
    this.model.notesToPlay(beat).forEach(this::playNote);
  }
}
