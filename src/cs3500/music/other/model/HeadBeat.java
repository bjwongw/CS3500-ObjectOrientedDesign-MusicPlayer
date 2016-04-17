package cs3500.music.other.model;

/**
 * Created by renyuan on 3/3/16.
 */


// represents a beat thats the first beat of a note
public class HeadBeat implements IBeat {


  private Pitch pitch;
  private int position;
  private int end;
  private int instrument;
  private int volume;
  //revised!
  //Changes made here: I added three more fields, end, instrument, volume. The reason is that I
  // need  to store all the information about my note


  public HeadBeat(Pitch pitch, int position, int end, int instrument, int volume) {
    this.pitch = pitch;
    this.position = position;
    this.end = end;
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * Get the beat number of the tone in the track
   *
   * @return the beat number in the track
   */
  @Override
  public int getPosition() {
    return this.position;
  }

  /**
   * Set the beat number of the tone in the track
   *
   * @param position the new beat number.
   */
  @Override
  public void setPosition(int position) {
    this.position = position;
  }


  @Override
  public Pitch getPitch() {
    return pitch;
  }

  /**
   * Determine if it's an first beat or not
   *
   * @return if it's first.
   */
  @Override
  public boolean isOnset() {
    return true;
  }

  /**
   * The String form of the note.
   *
   * @return "X" if it's Onset, "|" if it's not Onset
   */
  @Override
  public String toString() {
    return "X";
  }


  public int getEnd() {
    return end;
  }


  public int getInstrument() {
    return this.instrument;
  }

  public int getVolume() {
    return this.volume;
  }

  public void setVolume(int v) {
    this.volume = v;
  }

  public void setInstrument(int i) {
    this.instrument = i;
  }


}
