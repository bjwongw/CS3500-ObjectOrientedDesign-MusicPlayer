package cs3500.music.other.model;


/**
 * Created by renyuan on 3/2/16.
 */

// a class represents different pitch in western music system by pitch name and octave name
public class Pitch implements Comparable<Pitch> {


  // an enum that represents differecnt pitch name
  public enum MusicPitch {

    C("C", 1), CSharp("C♯", 2), D("D", 3), DSharp("D♯", 4), E("E", 5), F("F", 6), FSharp("F♯", 7),
    G("G", 8), GSharp("G♯", 9), A("A", 10), ASharp("A♯", 11), B("B", 12);
    private String name;
    private int pitchRank;

    // constructor for enum

    /**
     * @param name      the name of the pitch
     * @param pitchRank the rank of the ptich
     * @INVARIANT: input pitchRank must go between 1 to 10
     */
    MusicPitch(String name, int pitchRank) {
      this.name = name;
      this.pitchRank = pitchRank;
    }
  }

  ;

  private MusicPitch pitch;
  private int octave;


  //constructor

  /**
   * @param pitch  the MusicPitch of the pitch
   * @param octave the octave of the pitch
   */
  public Pitch(MusicPitch pitch, int octave) {
    if (octave < 1 || octave > 10) {
      throw new IllegalArgumentException("invalid octave");
    }
    this.pitch = pitch;
    this.octave = octave;

  }


  public Pitch(int PitchNumber) {

    double octive;
    if (PitchNumber < 13) {
      octive = 1;
    } else {
      if (PitchNumber % 12 == 0) {
        octive = PitchNumber / 12;
      } else {
        octive = Math.floor(PitchNumber / 12) + 1;
      }
    }

    int rank = PitchNumber % 12;


    MusicPitch mp = MusicPitch.A;
    switch (rank) {
      case 1:
        mp = MusicPitch.C;
        break;
      case 2:
        mp = MusicPitch.CSharp;
        break;
      case 3:
        mp = MusicPitch.D;
        break;
      case 4:
        mp = MusicPitch.DSharp;
        break;
      case 5:
        mp = MusicPitch.E;
        break;
      case 6:
        mp = MusicPitch.F;
        break;
      case 7:
        mp = MusicPitch.FSharp;
        break;
      case 8:
        mp = MusicPitch.G;
        break;
      case 9:
        mp = MusicPitch.GSharp;
        break;
      case 10:
        mp = MusicPitch.A;
        break;
      case 11:
        mp = MusicPitch.ASharp;
        break;
      case 0:
        mp = MusicPitch.B;
        break;
    }
    this.pitch = mp;
    this.octave = (int) octive;

  }


  /**
   * @return the string form of the pitch
   */
  public String toString() {
    return this.pitch.name + this.octave;
  }

  @Override
  public int hashCode() {
    return this.pitch.pitchRank + (this.octave - 1) * 12;
  }

  public int pitchNumber() {
    return this.hashCode();
  }

  public Pitch IntCoverntToPitch(int PitchNumber) {


    double octive;
    if (PitchNumber < 13) {
      octive = 1;
    } else {
      if (PitchNumber % 12 == 0) {
        octive = PitchNumber / 12;
      } else {
        octive = Math.floor(PitchNumber / 12) + 1;
      }
    }

    int rank = PitchNumber % 12;


    MusicPitch mp = MusicPitch.A;
    switch (rank) {
      case 1:
        mp = MusicPitch.C;
        break;
      case 2:
        mp = MusicPitch.CSharp;
        break;
      case 3:
        mp = MusicPitch.D;
        break;
      case 4:
        mp = MusicPitch.DSharp;
        break;
      case 5:
        mp = MusicPitch.E;
        break;
      case 6:
        mp = MusicPitch.F;
        break;
      case 7:
        mp = MusicPitch.FSharp;
        break;
      case 8:
        mp = MusicPitch.G;
        break;
      case 9:
        mp = MusicPitch.GSharp;
        break;
      case 10:
        mp = MusicPitch.A;
        break;
      case 11:
        mp = MusicPitch.ASharp;
        break;
      case 0:
        mp = MusicPitch.B;
        break;
    }

    return new Pitch(mp, (int) octive);

  }


  @Override
  public boolean equals(Object o) {
    return (o instanceof Pitch) && (((Pitch) o).hashCode() == this.hashCode());
  }

  @Override
  public int compareTo(Pitch o) {
    return -(o.hashCode() - this.hashCode());
  }


  public MusicPitch getPitch() {
    return this.pitch;
  }

  public int getOctave() {
    return this.octave;
  }

  public void setPitch(MusicPitch p) {
    this.pitch = p;
  }

  public void setOctave(int o) {
    this.octave = o;
  }


}
