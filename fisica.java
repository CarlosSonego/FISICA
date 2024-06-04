import javax.swing.*;
import java.awt.event.*;

public class fisica implements ActionListener {

    JFrame frame;
    JButton calcularBtn, resetBtn;
    JTextField varEspacoInput, varTempoInput, resultadoVelocidade;

    fisica() {
        
        frame = new JFrame("Calcular Velocidade Média");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel varEspacoLabel = createLabel("Variação de espaço (km):", 40, 25, 200, 30);
        varEspacoInput = createTextField("", 240, 25, 100, 30, true, true);

        JLabel varTempoLabel = createLabel("Variação de tempo (horas):", 40, 75, 200, 30);
        varTempoInput = createTextField("", 240, 75, 100, 30, true, true);

        calcularBtn = new JButton("Calcular");
        calcularBtn.setBounds(80, 150, 100, 30);
        calcularBtn.addActionListener(this);
        calcularBtn.setFocusable(false);

        resetBtn = new JButton("Reset");
        resetBtn.setBounds(200, 150, 100, 30);
        resetBtn.addActionListener(this);
        resetBtn.setFocusable(false);

        resultadoVelocidade = createTextField("Velocidade Média: ", 40, 200, 300, 30, false, false);

        frame.add(varEspacoLabel);
        frame.add(varEspacoInput);
        frame.add(varTempoLabel);
        frame.add(varTempoInput);
        frame.add(calcularBtn);
        frame.add(resetBtn);
        frame.add(resultadoVelocidade);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new teste();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcularBtn) {
            try {
                double varEspaco = Double.parseDouble(varEspacoInput.getText());
                double varTempo = Double.parseDouble(varTempoInput.getText());

                double velocidadeMedia = varEspaco / varTempo;
                resultadoVelocidade.setText("Velocidade Média: " + String.format("%.2f", velocidadeMedia) + " km/h");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == resetBtn) {
            varEspacoInput.setText("");
            varTempoInput.setText("");
            resultadoVelocidade.setText("Velocidade Média: ");
        }
    }

    private JTextField createTextField(String label, int x, int y, int w, int h, boolean edit, boolean focus) {
        JTextField textField = new JTextField(label);
        textField.setBounds(x, y, w, h);
        textField.setEditable(edit);
        textField.setFocusable(focus);
        return textField;
    }

    private JLabel createLabel(String label, int x, int y, int w, int h) {
        JLabel labelText = new JLabel(label);
        labelText.setBounds(x, y, w, h);
        return labelText;
    }
}
