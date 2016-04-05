package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * A type of JPanel that is used to display Notes in an IMusicModel.
 */
public class CompositionViewPanel extends JPanel {

  // the lowest beat displayed
  private int columnStart;

  // the offset from the highest pitch in the model
  // a value of 0 indicates that the highest pitch is the first pitch displayed
  // a value of 1 indicates that the second highest pitch is the first pitch displayed, etc.
  private int rowStart;

  // the highest pitch to be displayed
  private int rowStartMidi;

  // the total range of beats to display
  private final int displayedBeats = 48;

  // the number of columns to show at a time
  private final int numColumns = displayedBeats / 4;

  // the number of rows to show at a time
  private final int numRows = 32;

  // the height of each cell containing notes (pixels)
  private final int height = 20;

  // the width of each cell containing notes (pixels)
  private final int width = height * 4;

  // the width of the space to the left of the note grid (pixels)
  private final int horizontalBuffer = 40;

  // the height of the space above the note (pixels)
  private final int verticalBuffer = 20;

  private final IMusicModel model;
  private JPanel pitchPanel;
  private JPanel beatPanel;
  private JPanel notesPanel;

  /**
   * Constructs the CompositionViewPanel.
   *
   * @param model the music model to represent
   */
  CompositionViewPanel(IMusicModel model) {
    super();
    this.model = model;
    this.rowStart = 0;
    this.rowStartMidi = model.getHighestNote().getMidiPitch() - rowStart;
    this.columnStart = 0;
    this.updatePanel();
  }

  public void updatePanel() {
    this.pitchPanel = createPitchPanel();
    this.beatPanel = createBeatPanel();
    this.notesPanel = createNotesPanel();

    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    setLayout(gridBag);

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.NORTH;
    this.add(pitchPanel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.SOUTHWEST;
    this.add(beatPanel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    this.add(notesPanel, constraints);
  }

  /**
   * Creates a JPanel to represent the range of Pitches in this CompositionViewPanel's model. They
   * are stacked vertically. The maximum number of pitches displayed are equal to this view's
   * numRows field. The first pitch displayed is the pitch associated with the rowStart field.
   *
   * @return a JPanel representing the range of Pitches vertically
   */
  private JPanel createPitchPanel() {
    int dim = NoteSquares.PREF_H;
    JPanel pitchP = new JPanel();
    pitchP.setLayout(new BoxLayout(pitchP, BoxLayout.PAGE_AXIS));
    pitchP.setPreferredSize(new Dimension(horizontalBuffer, height * numRows));
    List<String> pitchRange = model.getPitchRange();
    int rangeSize = pitchRange.size();
    for (int i = 0; i < rangeSize && i < numRows; i++) {
      JLabel pitchLabel = new JLabel(pitchRange.get((rangeSize - (rowStart + 1)) - i));
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
  /* TODO deal with final beat (if there's only one note at beat 1 for duration 1, too many
    columns are created. */
  private JPanel createBeatPanel() {
    int dim = NoteSquares.PREF_W;
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    JPanel beatP = new JPanel(gridBag);
    beatP.setPreferredSize(new Dimension(displayedBeats * dim, verticalBuffer));
    if (columnStart % 16 == 0) {
      for (int i = 0; i < displayedBeats / 16; i++) {
        JLabel beatLabel = new JLabel(Integer.toString(columnStart + (i * 16)));
        beatLabel.setPreferredSize(new Dimension(dim * 16, dim));
        constraints.gridx = i;
        constraints.gridy = 0;
        beatP.add(beatLabel, constraints);
      }
    } else {
      for (int i = 0; i <= displayedBeats / 16; i++) {
        JLabel beatLabel;
        if (i == 0) {
          beatLabel = new JLabel();
          int labelWidth = 16 - (columnStart % 16);
          beatLabel.setPreferredSize(new Dimension(dim * labelWidth, dim));
        } else if (i < displayedBeats / 16) {
          int beat = ((columnStart / 16) + i) * 16;
          beatLabel = new JLabel(Integer.toString(beat));
          beatLabel.setPreferredSize(new Dimension(dim * 16, dim));
        } else {
          int beat = ((columnStart / 16) + i) * 16;
          beatLabel = new JLabel(Integer.toString(beat));
          int labelWidth = columnStart % 16;
          beatLabel.setPreferredSize(new Dimension(dim * labelWidth, dim));
        }
        constraints.gridx = i;
        constraints.gridy = 0;
        beatP.add(beatLabel, constraints);
      }
    }
    return beatP;
  }

  /**
   * Creates a JPanel to represent all the notes contained in this CompositionViewPanel's model.
   * The layout of this panel is a GridLayout, each cell containing four beats (four quarter
   * notes).
   *
   * @return a JPanel representing all the notes in this CompositionViewPanel's model.
   */
  // TODO ensure the proper size of the notesPanel
  private JPanel createNotesPanel() {
    int pitchRows = model.getPitchRange().size();
    int rows = Integer.min(pitchRows, this.numRows);
    JPanel notesP = new JPanel(new GridLayout(rows, this.numColumns));
    notesP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    List<List<NoteSquares>> panelHolder = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      panelHolder.add(new ArrayList<>());
      for (int j = 0; j < this.numColumns; j++) {
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
   * NOTE: may need to control for invariants, such as the notesP parameter being the wrong size in
   * comparison to the model field in this CompositionViewPanel.
   *
   * @param notesP the grid of NoteSquares to be altered to represent the Notes in this model.
   */
  private void initializeNotesPanel(List<List<NoteSquares>> notesP) {
    for (int i = columnStart; i < columnStart + displayedBeats; i++) {
      List<Note> noteList = new ArrayList<>(model.notesToPlay(i));
      Collections.sort(noteList);
      for (Note n : noteList) {
        int pitchIndex = this.rowStartMidi - n.getMidiPitch();
        if (pitchIndex >= 0 && pitchIndex < numRows) {
          List<NoteSquares> pitchList = notesP.get(pitchIndex);
          int start = n.getStart() - columnStart;
          pitchList.get(start / 4).setNoteColor(start % 4, Color.BLACK);
//          int maxBoundary = this.displayedBeats;
//          for (int j = start + 1; j < start + n.getDuration() && j < maxBoundary; j++) {
//            pitchList.get(j / 4).setNoteColor(j % 4, new Color(42, 255, 55));
//          }
        }
      }
      List<Note> sustainedNotes = new ArrayList<>(model.sustainedNotes(i));
      Collections.sort(noteList);
      for (Note n : sustainedNotes) {
        int pitchIndex = this.rowStartMidi - n.getMidiPitch();
        if (pitchIndex >= 0 && pitchIndex < numRows) {
          List<NoteSquares> pitchList = notesP.get(pitchIndex);
          int start = i - columnStart;
          pitchList.get(start / 4).setNoteColor(start % 4, new Color(42, 255, 55));
        }
      }
    }
  }

  /**
   *
   * @param direction
   * @throws IllegalArgumentException
   */
  public void shift(String direction) {
    switch(direction) {
      case "left" :
        if (this.columnStart - 4 >= 0) {
          this.columnStart -= 4;
        }
        break;
      case "right" : // make it stop at the end of the piece
        this.columnStart += 4;
        break;
      case "up" :
        if (this.rowStart - 1 >= 0) {
          this.rowStart -= 1;
          this.rowStartMidi -= 1;
        }
        break;
      case "down" :
        this.rowStart += 1;
        this.rowStartMidi += 1;
        break;
      default :
        throw new IllegalArgumentException("Invalid direction");
    }
    this.updatePanel();
  }

  /**
   *
   */
  public void reset() {
    this.rowStart = 0;
    this.rowStartMidi = model.getHighestNote().getMidiPitch() - rowStart;
    this.columnStart = 0;
    this.updatePanel();
  }

//  @Override
//  public Dimension getPreferredSize() {
//    Dimension temp = new Dimension(this.pitchPanel.getWidth() + this.notesPanel.getWidth() + 40,
//      this.beatPanel.getHeight() + this.notesPanel.getHeight() + 40);
//    System.out.println("PitchPanel width: " + Integer.toString(pitchPanel.getWidth()));
//    System.out.println("NotesPanel width: " + Integer.toString(notesPanel.getWidth()));
//    System.out.println("BeatPanel height: " + Integer.toString(beatPanel.getHeight()));
//    System.out.println("NotesPanel height: " + Integer.toString(notesPanel.getHeight()));
//    System.out.println(temp);
////    return temp;
//    return new Dimension(1042, 702);
//  }
}
