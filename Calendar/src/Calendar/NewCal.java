// NewCal.java
package Calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NewCal {

    private JFrame frame;
    private JPanel TopPanel;
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JPanel calendarPanel;

    private int currentYear;
    private int currentMonth;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NewCal window = new NewCal();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NewCal() {
        initialize();
        setCurrentDate();
        updateCalendar();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBackground(Color.WHITE);
        frame.setBounds(100, 100, 890, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TopPanel = new JPanel();
        TopPanel.setBackground(new Color(254, 228, 139));
        frame.getContentPane().add(TopPanel, BorderLayout.NORTH);
        TopPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        yearLabel = new JLabel("Year:");
        TopPanel.add(yearLabel);

        yearComboBox = new JComboBox<>();
        yearComboBox.setBackground(new Color(255, 255, 255));
        for (int i = 2000; i <= 2024; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }
        yearComboBox.addActionListener(e -> {
            updateMonthComboBox();
            updateCalendar();
        });
        TopPanel.add(yearComboBox);

        monthLabel = new JLabel("Month:");
        TopPanel.add(monthLabel);

        monthComboBox = new JComboBox<>();
        monthComboBox.setBackground(new Color(255, 255, 255));
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(String.valueOf(i));
        }
        monthComboBox.addActionListener(e -> {
            updateCalendar();
        });
        TopPanel.add(monthComboBox);

        prevButton = new JButton("<< Prev");
        prevButton.addActionListener(e -> {
            movePrevMonth();
            updateCalendar();
        });
        prevButton.setBackground(new Color(255, 255, 255));
        TopPanel.add(prevButton);

        nextButton = new JButton("Next >>");
        nextButton.addActionListener(e -> {
            moveNextMonth();
            updateCalendar();
        });
        nextButton.setBackground(new Color(255, 255, 255));
        TopPanel.add(nextButton);

        calendarPanel = new JPanel();
        calendarPanel.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(calendarPanel, BorderLayout.CENTER);
        calendarPanel.setLayout(new GridLayout(0, 7));
    }

    private void setCurrentDate() {
        Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH) + 1;
        yearComboBox.setSelectedItem(String.valueOf(currentYear));
        monthComboBox.setSelectedItem(String.valueOf(currentMonth));
    }

    private void movePrevMonth() {
        if (currentMonth == 1) {
            currentYear--;
            currentMonth = 12;
        } else {
            currentMonth--;
        }
        yearComboBox.setSelectedItem(String.valueOf(currentYear));
        monthComboBox.setSelectedItem(String.valueOf(currentMonth));
    }

    private void moveNextMonth() {
        if (currentMonth == 12) {
            currentYear++;
            currentMonth = 1;
        } else {
            currentMonth++;
        }
        yearComboBox.setSelectedItem(String.valueOf(currentYear));
        monthComboBox.setSelectedItem(String.valueOf(currentMonth));
    }

    private void updateMonthComboBox() {
        currentYear = Integer.parseInt((String) yearComboBox.getSelectedItem());
        monthComboBox.setSelectedItem(String.valueOf(currentMonth));
    }

    private void updateCalendar() {
        calendarPanel.removeAll();

        Calendar cal = new GregorianCalendar(currentYear, currentMonth - 1, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int maxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        String[] dayNames = { "일", "월", "화", "수", "목", "금", "토" };
        Color[] dayColors = { Color.RED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLUE };
        for (int i = 0; i < dayNames.length; i++) {
            JLabel label = new JLabel(dayNames[i]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(dayColors[i]);
            calendarPanel.add(label);
        }

        for (int i = 1; i < firstDayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int i = 1; i <= maxDayOfMonth; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setPreferredSize(new Dimension(50, 50));
            button.setBackground(Color.WHITE);
            int dayOfWeek = (firstDayOfWeek + i - 2) % 7;
            if (dayOfWeek == 0) {
                button.setForeground(dayColors[0]);
            } else if (dayOfWeek == 6) {
                button.setForeground(dayColors[6]);
            } else {
                button.setForeground(Color.BLACK);
            }
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showDatePopup(button);
                }
            });
            calendarPanel.add(button);
        }

        frame.revalidate();
        frame.repaint();
    }

    private void showDatePopup(JButton button) {
        String buttonText = button.getText();
        String date = String.format("%d-%02d-%02d", currentYear, currentMonth, Integer.parseInt(buttonText));
        DatePopup popup = new DatePopup(frame, button, date, buttonText);
        popup.setVisible(true);
    }

    private JButton getButtonByDate(String date) {
        Component[] components = calendarPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(date)) {
                    return button;
                }
            }
        }
        return null;
    }

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
            setLocationRelativeTo(null);
        }

        private void saveInformation() {
            if (button != null) {
                String information = textField.getText();
                // HTML 형식으로 줄 바꿈 및 스타일 지정
                String updatedText = "<html>" + buttonText + "<br><font color='black'>" + information + "</font></html>";
                button.setText(updatedText);
            }
            dispose();
        }
    }
}
