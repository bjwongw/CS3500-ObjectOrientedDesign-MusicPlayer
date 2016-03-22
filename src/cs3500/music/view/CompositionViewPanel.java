package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A type of JPanel that is used to display Notes in an IMusicModel.
 */
public class CompositionViewPanel extends JPanel {

  //height and width in pixels of each square cell
  private static final int CELL_HEIGHT = 20;
  private static final int CELL_WIDTH = 80;
  private static final int SQUARE_DIM = CELL_HEIGHT;
  private static final int PITCH_COL_WIDTH = 50;
  private static final int BEAT_ROW_HEIGHT = 50;

  private final IMusicModel model;
  private final JPanel pitchPanel;
  private final JPanel beatPanel;
  private final JPanel notesPanel;

  /**
   * Constructs the CompositionViewPanel.
   *
   * @param model the music model to represent
   */
  CompositionViewPanel(IMusicModel model) {
    super();
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    setLayout(gridBag);
    this.model = model;
    this.pitchPanel = createPitchPanel();
    this.beatPanel = createBeatPanel();
    this.notesPanel = createNotesPanel();

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.NORTH;
    gridBag.setConstraints(pitchPanel, constraints);
    this.add(pitchPanel);

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.SOUTHWEST;
    gridBag.setConstraints(beatPanel, constraints);
    this.add(beatPanel);

    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    gridBag.setConstraints(notesPanel, constraints);
    this.add(notesPanel);
  }

  /**
   * Creates a JPanel to represent the range of Pitches in this CompositionViewPanel's model. They
   * are stacked vertically.
   *
   * @return a JPanel representing the range of Pitches vertically
   */
  private JPanel createPitchPanel() {
    int dim = NoteSquares.PREF_H;
    JPanel pitchP = new JPanel();
    pitchP.setLayout(new BoxLayout(pitchP, BoxLayout.PAGE_AXIS));
    List<String> pitchRange = model.getPitchRange();
    for (int i = 0; i < pitchRange.size(); i++) {
      JLabel pitchLabel = new JLabel(pitchRange.get((pitchRange.size() - 1) - i));
      pitchLabel.setBorder(BorderFactory.createEmptyBorder(dim / 5, 0, 0, dim / 5));
      pitchP.add(pitchLabel);
    }
    return pitchP;
  }

  /**
   * Creates a JPanel to represent the range of beats in this CompositionViewPanel's model. The
   * beat labels are measured in increments of 16, starting from 0. Each beat label spans the width
   * of four NoteSquares panels.
   *
   * @return a JPanel representing the beats in this CompositionViewPanel's model, starting from 0
   * and increasing in increments of 16 horizontally
   */
  private JPanel createBeatPanel() {
    int dim = NoteSquares.PREF_W;
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    JPanel beatP = new JPanel(gridBag);
    int lastBeat = model.finalBeat() / 16;
    for (int i = 0; i <= lastBeat; i++) {
      JLabel beatLabel = new JLabel(Integer.toString(i*16));
      beatLabel.setPreferredSize(new Dimension(dim*16, dim));
      constraints.gridx = i;
      constraints.gridy = 0;
      gridBag.setConstraints(beatLabel, constraints);
      beatP.add(beatLabel);
    }
    return beatP;
  }

  /**
   * Creates a JPanel to represent all the notes contained in this CompositionViewPanel's model.
   * The layout of this panel is a GridLayout, each cell containing four beats (four quarter notes).
   *
   * @return a JPanel representing all the notes in this CompositionViewPanel's model.
   */
  private JPanel createNotesPanel() {
    int pitches = model.getHighestNote().getMidiPitch() - model.getLowestNote().getMidiPitch() + 1;
    int beats = (model.finalBeat() / 4) + 1;
    JPanel notesP = new JPanel(new GridLayout(pitches, beats));
    notesP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    List<List<NoteSquares>> panelHolder = new ArrayList<>();
    for (int i = 0; i < pitches; i++) {
      panelHolder.add(new ArrayList<>());
      for (int j = 0; j < beats; j++) {
        NoteSquares squares = new NoteSquares();
        squares.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelHolder.get(i).add(squares);
        notesP.add(panelHolder.get(i).get(j));
      }
    }
    initializeNotesPanel(panelHolder);
    return notesP;
  }

  /**
   * Given a {@code List<List<NoteSquares>>}, this method adjusts all the NoteSquares to represent
   * notes from this CompositionViewPanel's model. Each start beat of a Note is associated with a
   * black square in the NoteSquare, and each sustained beat for that same Note is represented as a
   * green square (with color value of RGB(42, 255, 55)).
   *
   * NOTE: may need to control for invariants, such as the notesP parameter being the wrong size
   * in comparison to the model field in this CompositionViewPanel.
   * @param notesP the grid of NoteSquares to be altered to represent the Notes in this model.
   */
  private void initializeNotesPanel(List<List<NoteSquares>> notesP) {
    List<Note> noteList = new ArrayList<>(model.getNotes());
    Collections.sort(noteList);
    int highestPitch = model.getHighestNote().getMidiPitch();
    for (Note n : noteList) {
      int pitchIndex = highestPitch - n.getMidiPitch();
      List<NoteSquares> pitchList = notesP.get(pitchIndex);
      int start = n.getStart();
      pitchList.get(start / 4).setNoteColor(start % 4, Color.BLACK);
      for (int i = start + 1; i < start + n.getDuration(); i++) {
        pitchList.get(i / 4).setNoteColor(i % 4, new Color(42, 255, 55));
      }
    }
  }
}
