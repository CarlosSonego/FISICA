import javax.swing.*;
import java.awt.event.*;

public class fisica implements ActionListener {

    JFrame frame;
    JButton calcularBtn, resetBtn;
    JTextField varEspacoInput, varTempoInput, resultadoCalculo;
    String tipoCalculo;

    fisica() {
        Object[] op = {"Velocidade media", "km", "horas"};
        String resp = (String) JOptionPane.showInputDialog(null,
                "Selecione a conta a ser realizada: \n", "Calculadora",
                JOptionPane.PLAIN_MESSAGE,
                null, op, "Velocidade media");

        tipoCalculo = resp;

        frame = new JFrame();

        if (resp.equals("km")) {
            frame.setTitle("Calcular KM percorridos");
            initFrameComponents("Velocidade média (km/h):", "Variação de tempo (horas):", "KM:");
        } else if (resp.equals("Velocidade media")) {
            frame.setTitle("Calcular Velocidade Média");
            initFrameComponents("Variação de espaço (km):", "Variação de tempo (horas):", "Velocidade Média:");
        } else if (resp.equals("horas")) {
            frame.setTitle("Calcular Tempo em horas");
            initFrameComponents("Variação de espaço (km):", "Velocidade média (km/h):", "Horas:");
        }
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
                String resultado = "";

                if (tipoCalculo.equals("km")) {
                    double kmPercorridos = varEspaco * varTempo;
                    resultado = "KM: " + String.format("%.2f", kmPercorridos);
                } else if (tipoCalculo.equals("Velocidade media")) {
                    double velocidadeMedia = varEspaco / varTempo;
                    resultado = "Velocidade Média: " + String.format("%.2f", velocidadeMedia) + " km/h";
                } else if (tipoCalculo.equals("horas")) {
                    double tempo = varEspaco / varTempo;
                    resultado = "Horas: " + String.format("%.2f", tempo);
                }

                resultadoCalculo.setText(resultado);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == resetBtn) {
            varEspacoInput.setText("");
            varTempoInput.setText("");
            resultadoCalculo.setText("");
        }
    }

    private void initFrameComponents(String label1, String label2, String resultadoLabel) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel varEspacoLabel = createLabel(label1, 40, 25, 200, 30);
        varEspacoInput = createTextField("", 240, 25, 100, 30, true, true);

        JLabel varTempoLabel = createLabel(label2, 40, 75, 200, 30);
        varTempoInput = createTextField("", 240, 75, 100, 30, true, true);

        calcularBtn = new JButton("Calcular");
        calcularBtn.setBounds(80, 150, 100, 30);
        calcularBtn.addActionListener(this);
        calcularBtn.setFocusable(false);

        resetBtn = new JButton("Reset");
        resetBtn.setBounds(200, 150, 100, 30);
        resetBtn.addActionListener(this);
        resetBtn.setFocusable(false);

        resultadoCalculo = createTextField(resultadoLabel, 40, 200, 300, 30, false, false);

        frame.add(varEspacoLabel);
        frame.add(varEspacoInput);
        frame.add(varTempoLabel);
        frame.add(varTempoInput);
        frame.add(calcularBtn);
        frame.add(resetBtn);
        frame.add(resultadoCalculo);
        frame.setVisible(true);
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
