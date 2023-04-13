import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CardLayoutEg extends JPanel {
   private CardLayout cardlayout = new CardLayout();
   private TitlePanel titlePanel = new TitlePanel(this);
   private SubjectPanel subjectPanel = new SubjectPanel(this);

   public CardLayoutEg() {
      setLayout(cardlayout);
      add(titlePanel, titlePanel.getName());
      add(subjectPanel, subjectPanel.getName());
   }

   public void nextCard() {
      cardlayout.next(this);
   }

   public void showCard(String key) {
      cardlayout.show(this, key);
   }

   private static void createAndShowGui() {
      JFrame frame = new JFrame("CardLayout Example");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(new CardLayoutEg());
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }

}

class TitlePanel extends JPanel {
   public static final String TITLE_PANEL = "title panel";
   private static final int PREF_W = 900;
   private static final int PREF_H = 750;
   private static final String TITLE = "My Application Title";
   private static final float POINTS = 46f;
   private CardLayoutEg cardLayoutEg;

   public TitlePanel(CardLayoutEg cardLayoutEg) {
      setName(TITLE_PANEL);
      this.cardLayoutEg = cardLayoutEg;

      JLabel titleLabel = new JLabel(TITLE, SwingConstants.CENTER);
      titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, POINTS));

      JButton subjectButton = new JButton(new SubjectAction("Subjects"));
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(subjectButton);

      setLayout(new BorderLayout());
      add(titleLabel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.PAGE_END);
   }

   @Override
   public Dimension getPreferredSize() {
      if (isPreferredSizeSet()) {
         return super.getPreferredSize();
      }
      return new Dimension(PREF_W, PREF_H);
   }

   private class SubjectAction extends AbstractAction {
      public SubjectAction(String name) {
         super(name);
         int mnemonic = (int) name.charAt(0);
         putValue(MNEMONIC_KEY, mnemonic);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         cardLayoutEg.showCard(SubjectPanel.SUBJECT_PANEL);
      }
   }
}

class SubjectPanel extends JPanel {
   public static final String SUBJECT_PANEL = "subject panel";
   private static final String[] COLUMN_NAMES = {"Subject", "Owner", "Status", "Date Added"};
   DefaultTableModel tableModel = new DefaultTableModel(COLUMN_NAMES, 10);
   private JTable table = new JTable(tableModel);
   private CardLayoutEg cardLayoutEg;

   public SubjectPanel(CardLayoutEg cardLayoutEg) {
      setBorder(BorderFactory.createTitledBorder("Subject Panel"));
      setName(SUBJECT_PANEL);
      this.cardLayoutEg = cardLayoutEg;

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(1, 0, 10, 0));
      buttonPanel.add(new JButton("New Subject"));
      buttonPanel.add(new JButton("Refresh"));
      buttonPanel.add(new JButton(new TitleAction("Title")));
      buttonPanel.add(new JButton(new ExitAction("Exit")));

      JPanel bottomPanel = new JPanel();
      bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
      bottomPanel.add(Box.createHorizontalGlue());
      bottomPanel.add(buttonPanel);

      setLayout(new BorderLayout());
      add(new JScrollPane(table), BorderLayout.CENTER);
      add(bottomPanel, BorderLayout.PAGE_END);
   }

   private class TitleAction extends AbstractAction {
      public TitleAction(String name) {
         super(name);
         int mnemonic = (int) name.charAt(0);
         putValue(MNEMONIC_KEY, mnemonic);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         cardLayoutEg.showCard(TitlePanel.TITLE_PANEL);
      }
   }

   private class ExitAction extends AbstractAction {
      public ExitAction(String name) {
         super(name);
         int mnemonic = KeyEvent.VK_X;
         putValue(MNEMONIC_KEY, mnemonic);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         Component component = (Component) e.getSource();
         Window win = SwingUtilities.getWindowAncestor(component);
         win.dispose();
      }
   }
}