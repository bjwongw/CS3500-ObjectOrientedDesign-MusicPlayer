package cs3500.music.other.model;

/**
 * Created by renyuan on 3/3/16.
 */

// represents a beat after the first beat in a note
public class TailBeat implements IBeat {

  private Pitch pitch;
  private int position;


  public TailBeat(Pitch pitch, int position) {
    this.pitch = pitch;
    this.position = position;
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


  @Override
  public Pitch getPitch() {
    return pitch;
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

  /**
   * Determine if it's an first beat or not
   *
   * @return if it's first.
   */
  @Override
  public boolean isOnset() {
    return false;
  }

  /**
   * The String form of the note.
   *
   * @return "X" if it's Onset, "|" if it's not Onset
   */
  @Override
  public String toString() {
    return "|";
  }
}
