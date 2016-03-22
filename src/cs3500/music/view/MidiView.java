package cs3500.music.view;

import java.util.LinkedList;
import java.util.List;
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
  private IMusicModel model;
  private Queue<Integer> channels;

  public MidiView() throws MidiUnavailableException {
    this(MidiSystem.getSynthesizer());
  }

  public MidiView(Synthesizer synth) {
    try {
      this.synth = synth;
      this.receiver = synth.getReceiver();
      this.channels = new LinkedList<>();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
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

  /**
   * Plays this song in its entirety. Does not pass back control until the song has finished.
   */
  private void play() {
    if (this.model == null) {
      throw new IllegalStateException("Cannot play the Midi model without providing a model (via" +
              " initialize) first!");
    }
    int currentBeat = 0;

    while (currentBeat <= this.model.finalBeat()) {
      this.update(currentBeat);
      try {
        Thread.sleep(Integer.toUnsignedLong(model.getTempo() / 1000));
      } catch (InterruptedException e) {
        //TODO what to do here
      }
      currentBeat += 1;
    }
  }

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    channels.clear();
    play();
  }

  @Override
  public void update(int beat) {
    this.model.notesToPlay(beat).forEach(this::playNote);
  }

  /**
   * Mock synthesizer for testing MidiView. Passes info about each send() call to a given
   * StringBuilder.
   */
  public static class MockSynthesizer implements Synthesizer {
    private final StringBuilder log;

    protected MockSynthesizer(StringBuilder s) {
      this.log = s;
    }

    @Override
    public Receiver getReceiver() throws MidiUnavailableException {
      return new MockReceiver(this.log);
    }

    /**
     * Mock receiver for testing MidiView.
     */
    private class MockReceiver implements Receiver {

      private StringBuilder log;

      MockReceiver(StringBuilder s) {
        this.log = s;
      }

      @Override
      public void send(MidiMessage message, long timeStamp) {
        if (!(message instanceof ShortMessage)) {
          this.log.append("send call: Non-ShortMessage sent!");
        } else {
          ShortMessage m = (ShortMessage) message;
          String s = String.format("send call: Command %d, MIDIPitch %d; Volume %d; " +
                          "timeStamp %d\n", m.getCommand(), m.getData1(), m
                          .getData2(),
                  timeStamp);

          this.log.append(s);
        }
      }

      @Override
      public void close() {
        this.log.append("Receiver closed\n");
      }
    }

    @Override
    public Info getDeviceInfo() {
      return null;
    }

    @Override
    public void open() throws MidiUnavailableException {
      log.append("Synthesizer opened\n");
    }

    @Override
    public void close() {
      log.append("Synthesizer closed\n");
    }

    @Override
    public boolean isOpen() {
      return true;
    }

    @Override
    public long getMicrosecondPosition() {
      return 0;
    }

    @Override
    public List<Receiver> getReceivers() {
      return null;
    }

    @Override
    public int getMaxReceivers() {
      return 0;
    }

    @Override
    public int getMaxTransmitters() {
      return 0;
    }

    @Override
    public Transmitter getTransmitter() throws MidiUnavailableException {
      return null;
    }

    @Override
    public List<Transmitter> getTransmitters() {
      return null;
    }

    @Override
    public int getMaxPolyphony() {
      return 0;
    }

    @Override
    public long getLatency() {
      return 0;
    }

    @Override
    public MidiChannel[] getChannels() {
      return new MidiChannel[0];
    }

    @Override
    public VoiceStatus[] getVoiceStatus() {
      return new VoiceStatus[0];
    }

    @Override
    public boolean isSoundbankSupported(Soundbank soundbank) {
      return false;
    }

    @Override
    public boolean loadInstrument(Instrument instrument) {
      return false;
    }

    @Override
    public void unloadInstrument(Instrument instrument) {

    }

    @Override
    public boolean remapInstrument(Instrument from, Instrument to) {
      return false;
    }

    @Override
    public Soundbank getDefaultSoundbank() {
      return null;
    }

    @Override
    public Instrument[] getAvailableInstruments() {
      return new Instrument[0];
    }

    @Override
    public Instrument[] getLoadedInstruments() {
      return new Instrument[0];
    }

    @Override
    public boolean loadAllInstruments(Soundbank soundbank) {
      return false;
    }

    @Override
    public void unloadAllInstruments(Soundbank soundbank) {

    }

    @Override
    public boolean loadInstruments(Soundbank soundbank, Patch[] patchList) {
      return false;
    }

    @Override
    public void unloadInstruments(Soundbank soundbank, Patch[] patchList) {

    }

  }
}
