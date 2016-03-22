package cs3500.music.view;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * Tests for the MidiView class using its mock synthesizer/receiver.
 */
public class MidiViewTest {

  StringBuilder log = new StringBuilder();
  StringBuilder expected = new StringBuilder();
  Synthesizer synth = new MidiView.MockSynthesizer(log);
  MidiView view = new MidiView(synth);

  /**
   * Adds a note to the end of both the model and the expected log of notes played
   *
   * @param p     pitch of the note
   * @param o     octave of the note
   * @param d     duration of the note in beats
   * @param i     instrument value of the note
   * @param v     volume of the note
   * @param model model to add the note to
   * @param log   log to add the send calls to
   */
  void addNote(Note.Pitch p, int o, int d, int i, int v, IMusicModel model, StringBuilder log) {
    Note n = new Note(p, o, model.finalBeat(), d, i, v);
    model.addNote(n);
    log.append(String.format("send call: Command %d, MIDIPitch %d; Volume %d; " +
            "timeStamp %d\n", ShortMessage.NOTE_ON, n.getMidiPitch(), v, -1));
    log.append(String.format("send call: Command %d, MIDIPitch %d; Volume %d; " +
            "timeStamp %d\n", ShortMessage.NOTE_OFF, n.getMidiPitch(), v, n.getDuration() *
            model.getTempo()));
  }

  /**
   * Checks if log1 contains every line in log2, possibly out of order
   * @param log1 the first log
   * @param log2 the second log
   * @return true if log1 has all of the lines in log2
   */
  boolean compareLogs(StringBuilder log1, StringBuilder log2) {
    ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(log1.toString().split("\n")));
    ArrayList<String> list2 = new ArrayList<String>(Arrays.asList(log2.toString().split("\n")));

    for(String s : list2) {
      if(!list1.contains(s)) {
        return false;
      }
    }
    return true;
  }
}
