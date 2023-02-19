import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transposer extends JFrame implements ActionListener {
    private final JTextPane textPane = new JTextPane();

    public Transposer() {
        super("Transposer");
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        textPane.setFont(Font.decode(Font.MONOSPACED));
        add(new JScrollPane(textPane));
        JButton button = new JButton("Transpose");
        button.addActionListener(this);
        add(button, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String original = textPane.getText();
        String transposed = transpose(original);
        textPane.setText(transposed);
        textPane.setCaretPosition(0);
    }

    private static String transpose(String original) {
        StringBuilder transposed = new StringBuilder();
        String chord = "";
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if ((c >= 'A' && c <= 'G') || c == 'm' || c == '#') {
                chord += c;
            } else if (!chord.isEmpty()) {
                if (c == ' ' || c == '\n') {
                    transposed.append(pitch(chord));
                } else {
                    transposed.append(chord);
                }
                transposed.append(c);
                chord = "";
            } else {
                transposed.append(c);
            }
        }
        return transposed.toString();
    }

    private static String pitch(String chord) {
        char[] chars = chord.toCharArray();
        char tonic = chars[0];
        if (chars.length == 1) {
            if (tonic == 'C' || tonic == 'F') {
                return String.valueOf((char) (tonic - 1));
            } else {
                if (tonic == 'A') {
                    return "G#";
                } else {
                    return (char) (tonic - 1) + "#";
                }
            }
        } else if (chars.length == 2) {
            if (chars[1] == 'm') {
                char mode = chars[1];
                if (tonic == 'C' || tonic == 'F') {
                    return String.valueOf((char) (tonic - 1)) + mode;
                } else {
                    if (tonic == 'A') {
                        return "G#" + mode;
                    } else {
                        return (char) (tonic - 1) + "#" + mode;
                    }
                }
            } else if (chars[1] == '#') {
                return String.valueOf(tonic);
            } else {
                throw new RuntimeException(String.format("Unknown symbol \"%c\" in chord \"%s\"", chars[1], chord));
            }
        } else if (chars.length == 3) {
            return String.valueOf(tonic) + chars[2];
        } else {
            throw new RuntimeException(String.format("Chord \"%s\" is too long", chord));
        }
    }

    public static void main(String[] args) {
        new Transposer().setVisible(true);
    }
}
