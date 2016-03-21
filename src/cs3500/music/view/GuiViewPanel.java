package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A view for a range of Note.Pitches
 */
public class GuiViewPanel extends JPanel {

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
  private final List<List<NoteSquares>> noteSquares;

  /**
   * Constructs the GuiViewPanel
   *
   * @param model the music model to represent
   */
  GuiViewPanel(IMusicModel model) {
    super();
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(gridBag);
    this.model = model;
    this.pitchPanel = createPitchPanel();
    this.beatPanel = createBeatPanel();
    this.notesPanel = createNotesPanel();
    this.noteSquares = createNoteSquares();

    c.gridx = 1;
    c.gridy = 0;
    gridBag.setConstraints(beatPanel, c);
    this.add(beatPanel);

    c.gridx = 0;
    c.gridy = 1;
    gridBag.setConstraints(pitchPanel, c);
    this.add(pitchPanel);

    c.gridx = 1;
    c.gridy = 1;
    gridBag.setConstraints(notesPanel, c);
    this.add(notesPanel);
  }

  private List<List<NoteSquares>> createNoteSquares() {
  }

  private JPanel createPitchPanel() {
    JPanel pitchP = new JPanel();
    pitchP.setLayout(new BoxLayout(pitchP, BoxLayout.PAGE_AXIS));
    List<String> pitchRange = model.getPitchRange();
    for (int i = 0; i < pitchRange.size(); i++) {
      pitchP.add(new JLabel(pitchRange.get((pitchRange.size() - 1) - i)));
    }
    return pitchP;
  }

  private JPanel createBeatPanel() {
    JPanel beatP = new JPanel();
    int lastBeat = model.finalBeat() / 16;
    for (int i = 0; i <= lastBeat; i++) {
      beatP.add(new JLabel(Integer.toString(i*16)));
    }
    return beatP;
  }

  private JPanel createNotesPanel() {
    int pitches = model.getHighestNote().getMidiPitch() - model.getLowestNote().getMidiPitch() + 1;
    int beats = model.finalBeat() / 4;
    JPanel notesP = new JPanel(new GridLayout(pitches, beats));
    notesP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    JPanel[][] panelHolder = new JPanel[pitches][beats];
    for (int i = 0; i < pitches; i++) {
      for (int j = 0; j < beats; j++) {
        JPanel tempP = new JPanel();
        tempP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tempP.add(new JLabel(String.format("%s, %s", i, j)));
        panelHolder[i][j] = tempP;
        notesP.add(panelHolder[i][j]);
      }
    }
    return initializeNotesPanel(notesP);
  }

  private JPanel initializeNotesPanel(JPanel notesP) {

    return notesP;
  }

  private List<Rectangle> createNoteSquares(Note n) {
    List<Rectangle> noteSquares = new ArrayList<>();
    if (n != null) {
      for (int i = n.getStart(); i < n.getStart() + n.getDuration(); i++) {
        noteSquares.add(new Rectangle(0, 0, SQUARE_DIM, SQUARE_DIM));
      }
    }
    return noteSquares;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
}
