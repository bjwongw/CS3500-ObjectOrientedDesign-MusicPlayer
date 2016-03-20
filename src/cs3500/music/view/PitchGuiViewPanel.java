package cs3500.music.view;

import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A view for a range of Note.Pitches
 */
public class PitchGuiViewPanel extends JPanel {

  private Note lowestNote;
  private Note highestNote;

  /**
   * Changes the lowestNote field to reference the given note.
   *
   * @param n the given note
   * @throws IllegalArgumentException if the given note is greater than the current highest note,
   * or null
   */
  void setLowestNote(Note n) {
    if (lowestNote == null) {
      lowestNote = n;
      return ;
    }
    if (n != null) {
      if (highestNote.compareTo(n) < 0) {
        throw new IllegalArgumentException("Lowest note cannot be greater than the highest note");
      } else {
        lowestNote = n;
      }
    } else {
      throw new IllegalArgumentException("Cannot set lowest note to null");
    }
  }

  /**
   * Changes the highestNote field to reference the given note.
   *
   * @param n the given note
   * @throws IllegalArgumentException if the given note is less than the current lowest note,
   * or null
   */
  void setHighestNote(Note n) {
    if (highestNote == null) {
      highestNote = n;
      return ;
    }
    if (n != null) {
      if (lowestNote.compareTo(n) > 0) {
        throw new IllegalArgumentException("Highest note cannot be less than the lowest note");
      } else {
        highestNote = n;
      }
    } else {
      throw new IllegalArgumentException("Cannot set highest note to null");
    }
  }

  /**
   * Creates a list containing every pitch string between the lowestNote and highestNote
   * (inclusively).
   *
   * @param lowestNote  the lower bound
   * @param highestNote the upper bound
   * @return a list of all the Pitches from the lowestNote to the highestNote
   */
  private List<String> createPitchRange(Note lowestNote, Note highestNote) {
    java.util.List<String> result = new ArrayList<>();
    List<Note.Pitch> pitches = Arrays.asList(Note.Pitch.values());

    int lowestOctave = lowestNote.getOctave();
    int highestOctave = highestNote.getOctave();

    int lowestPitchSymbolIdx = lowestNote.getPitch().ordinal();
    int highestPitchSymbolIdx = highestNote.getPitch().ordinal();

    int pitchSpread = ((highestPitchSymbolIdx - lowestPitchSymbolIdx) % pitches.size());
    int totalPitches = ((highestOctave - lowestOctave) * pitches.size()) + pitchSpread;

    for (int i = lowestPitchSymbolIdx; i <= lowestPitchSymbolIdx + totalPitches; i++) {
      if (i % pitches.size() == 0 && i != lowestPitchSymbolIdx) {
        lowestOctave += 1;
      }
      Note.Pitch pitchSymbol = pitches.get(i % pitches.size());
      result.add(pitchSymbol.toString() + lowestOctave);
    }
    return result;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    List<String> pitchRange = createPitchRange(lowestNote, highestNote);
    for (int i = 0; i < pitchRange.size(); i++) {
      g.drawString(pitchRange.get((pitchRange.size() - 1) - i), 10, i*20);
    }
  }
}
