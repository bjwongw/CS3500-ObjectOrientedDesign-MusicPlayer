package cs3500.music.view.testing;

import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

/**
 * Mock synthesizer for testing MidiView. Passes info about each send() call to a given
 * StringBuilder.
 */
public class MockSynthesizer implements Synthesizer {
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
        String s = String.format("send call: Command %d; Channel %d; MIDIPitch %d; Volume %d; " +
                "timeStamp %d", m.getCommand(), m.getChannel(), m.getData1(), m.getData2(),
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
