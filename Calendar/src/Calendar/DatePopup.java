package Calendar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DatePopup extends JDialog {
    private JTextField textField;
    private JButton button;
    private String date;
    private String buttonText;

    public DatePopup(JFrame parent, JButton button, String date, String buttonText) {
        super(parent, "Information for " + date, true);
        this.button = button;
        this.date = date;
        this.buttonText = buttonText;
        initialize();
    }

    private void initialize() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel dateLabel = new JLabel("Date: " + date);
        panel.add(dateLabel, BorderLayout.NORTH);

        textField = new JTextField(20);
        panel.add(textField, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveInformation();
            }
        });
        panel.add(saveButton, BorderLayout.SOUTH);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // 중앙 정렬
    }

    private void saveInformation() {
        if (button != null) {
            String information = textField.getText();
            String updatedText = buttonText + "\n" + information;
            button.setText(updatedText);
        }
        dispose();
    }
}
