package cs3500.music.other.model;

/**
 * Created by renyuan on 3/2/16.
 */
public class Note {

  private Pitch pitch;
  private int duration;

  // revised!
  private int starttime;
  private int endTime;
  private int instrumentt;
  private int volumee;


  //Changes made here: I added four more fields here to store the information proviede at this
  // assignment


  /**
   * @param pitch    the pitch of the note
   * @param duration the duration of the note
   */
  public Note(Pitch pitch, int duration) {
    this.pitch = pitch;
    this.duration = duration;
    starttime = 1;
    endTime = 3;
    instrumentt = 1;
    volumee = 1;


  }

  /**
   * rise a note to next pitch;
   *
   * // INVARIANT: the hashcode of this pitch cant not be greater than 119
   */
  public void rise() {
    if (this.hashCode() == 120) {
      throw new IllegalArgumentException("my application doest support octave greater than 10");
    } else {
      this.pitch = new Pitch(this.pitch.hashCode() + 1);
    }
  }


  /**
   * * // INVARIANT: the hashcode of this pitch cant not be less than 2
   */
  public void fall() {
    if (this.hashCode() < 2) {
      throw new IllegalArgumentException("you can lower the lowest pitch");
    } else {
      this.pitch = new Pitch(this.pitch.hashCode() - 1);
    }
  }


  public Note Set(int start, int end, int instrument, int pitchh, int volume) {

    starttime = start;
    endTime = end;
    instrumentt = instrument;
    volumee = volume;
    duration = end - start;
    pitch = new Pitch(pitchh);
    return this;
  }


  public int getStarttime() {
    return starttime;
  }

  public int getEndTime() {
    return endTime;
  }

  public int getInstrumentt() {
    return instrumentt;
  }

  public int getVolumee() {
    return volumee;
  }

  public int getDuration() {
    return duration;
  }

  public Pitch getPitch() {
    return pitch;
  }

  @Override
  public String toString() {
    return "Pitch: " + this.pitch.pitchNumber() + " duration: " + duration +
            " starttime: " + starttime + " end time: " + endTime +
            " instrument: " + instrumentt + " volumee: " + volumee;
  }


}
