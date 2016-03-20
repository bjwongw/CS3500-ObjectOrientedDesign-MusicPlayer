package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

/**
 * A view for a range of Note.Pitches
 */
public class GuiViewPanel extends JPanel {

  //height and width in pixels of each square cell
  private static final int CELL_HEIGHT = 20;
  private static final int PITCH_COL_WIDTH = 50;
  private static final int BEAT_ROW_HEIGHT = 50;

  private final IMusicModel model;
  private final JPanel pitchPanel;
  private final JPanel workingPanel;
  private final JPanel beatPanel;
  private final JPanel notesPanel;

  private final JPanel[][] notesPanels;

  /**
   * Constructs the GuiViewPanel
   *
   * @param model the music model to represent
   */
  GuiViewPanel(IMusicModel model) {
    super();
    this.model = model;
    this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

    this.pitchPanel = createPitchPanel();
    this.beatPanel = createBeatPanel();
    this.notesPanel = createNotesPanel();
    this.workingPanel = createWorkingPanel();

    this.add(pitchPanel);
    this.add(workingPanel);
  }

  private JPanel createPitchPanel() {

  }

  private JPanel createBeatPanel() {

  }

  private JPanel createNotesPanel() {

  }

  private JPanel createWorkingPanel() {

  }

  private List<Rectangle> createNoteSquares(Note n) {
    List<Rectangle> noteSquares = new ArrayList<>();
    if (n != null) {
      for (int i = n.getStart(); i < n.getStart() + n.getDuration(); i++) {
        noteSquares.add(new Rectangle(0, 0, BEAT_DIMENSION, BEAT_DIMENSION));
      }
    }
    return noteSquares;
  }

  private List<Graphics2D> createNoteImages() {
    List<Graphics2D> result = new ArrayList<>();
//    int range =
//    Set<Note> notes = model.getNotes();
    return result;
  }

  /**
   * Creates a list containing every pitch string between the lowest note in the model and the
   * highest note in the model (inclusively).
   *
   * @return a list of all the Pitches from the lowest note to the highest note
   */
  private List<String> createPitchRange() {
    java.util.List<String> result = new ArrayList<>();
    List<Note.Pitch> pitches = Arrays.asList(Note.Pitch.values());

    Note lowestNote = model.getLowestNote();
    Note highestNote = model.getHighestNote();
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

    List<String> pitchRange = createPitchRange();
    for (int i = 0; i < pitchRange.size(); i++) {
      g.drawString(pitchRange.get((pitchRange.size() - 1) - i), 10, i*20);
    }
  }
}
