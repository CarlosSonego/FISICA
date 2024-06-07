import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraVelocidadeMedia extends JFrame {

    private JLabel labelTitulo;
    private JComboBox<String> comboOperacoes;
    private JLabel labelRepeticoes;
    private JTextField campoRepeticoes;
    private JButton botaoIniciar;
    private int contadorRepeticoes = 0;
    private int totalRepeticoes = 0;
    private double[] vetorKm;
    private double[] vetorHoras;

    public CalculadoraVelocidadeMedia() {
        super("Calculadora de Velocidade Média");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
            painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
                getContentPane().add(painelPrincipal);

        labelTitulo = new JLabel("Escolha a operação e número de repetições:");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelPrincipal.add(labelTitulo);

        JPanel painelOperacoes = new JPanel();

            painelOperacoes.setLayout(new FlowLayout(FlowLayout.CENTER));
            comboOperacoes = new JComboBox<>(new String[]{"Velocidade Média"});
            comboOperacoes.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelOperacoes.add(comboOperacoes);
            painelPrincipal.add(painelOperacoes);

        JPanel painelRepeticoes = new JPanel();

            painelRepeticoes.setLayout(new FlowLayout(FlowLayout.CENTER));
            labelRepeticoes = new JLabel("Número de repetições:");
            labelRepeticoes.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelRepeticoes.add(labelRepeticoes);
            campoRepeticoes = new JTextField(10);
            campoRepeticoes.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelRepeticoes.add(campoRepeticoes);
            painelPrincipal.add(painelRepeticoes);

        botaoIniciar = new JButton("Iniciar");
        botaoIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoIniciar.addActionListener(new ActionListener() {

    @Override
        public void actionPerformed(ActionEvent e) {
            iniciarOperacoes();
}
});
        painelPrincipal.add(botaoIniciar);
            setVisible(true);
    }

    private void iniciarOperacoes() {
        try {
            String operacaoSelecionada = (String) comboOperacoes.getSelectedItem();
            totalRepeticoes = Integer.parseInt(campoRepeticoes.getText());

            if (totalRepeticoes > 0) {
                contadorRepeticoes = 0;

                vetorKm = new double[totalRepeticoes];
                vetorHoras = new double[totalRepeticoes];
                realizarOperacao(operacaoSelecionada);
            } else {
                JOptionPane.showMessageDialog(this, "Número deve ser maior que zero.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "insira um número válido para repetições.");
        }
    }

    private void realizarOperacao(String operacao) {
        contadorRepeticoes++;

        JFrame janelaOperacao = new JFrame("Operação " + contadorRepeticoes + "/" + totalRepeticoes);
        janelaOperacao.setSize(400, 300);
        janelaOperacao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaOperacao.setLocationRelativeTo(null);

        JPanel painelOperacao = new JPanel();
        painelOperacao.setLayout(new BoxLayout(painelOperacao, BoxLayout.Y_AXIS));
        janelaOperacao.getContentPane().add(painelOperacao);

        JLabel labelOperacao = new JLabel("Operação " + contadorRepeticoes + "/" + totalRepeticoes + ": " + operacao);
        labelOperacao.setFont(new Font("Arial", Font.BOLD, 16));
        labelOperacao.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelOperacao.add(labelOperacao);

        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new GridLayout(4, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelOperacao.add(painelCampos);

        JLabel labelKm = new JLabel("Km percorridos:");
        JTextField campoKm = new JTextField(10);
        campoKm.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCampos.add(labelKm);
        painelCampos.add(campoKm);

        JLabel labelHoras = new JLabel("Horas:");
        JTextField campoHoras = new JTextField(10);
        campoHoras.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCampos.add(labelHoras);
        painelCampos.add(campoHoras);

        JLabel labelVelocidadeMedia = new JLabel("Velocidade Média:");
        JTextField campoVelocidadeMedia = new JTextField(10);
        campoVelocidadeMedia.setEditable(false); 
        campoVelocidadeMedia.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCampos.add(labelVelocidadeMedia);
        painelCampos.add(campoVelocidadeMedia);

        JButton botaoVerResultado = new JButton("Resultado");
        botaoVerResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoVerResultado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularOperacao(operacao, campoKm, campoHoras, campoVelocidadeMedia);

                try {
                    vetorKm[contadorRepeticoes - 1] = Double.parseDouble(campoKm.getText());
                    vetorHoras[contadorRepeticoes - 1] = Double.parseDouble(campoHoras.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "insira valores válidos para Km e Horas.");
                    return;
                }

                painelCampos.revalidate();
                painelCampos.repaint();

                Timer timer = new Timer(2000, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        janelaOperacao.dispose(); 
                        if (contadorRepeticoes < totalRepeticoes) {
                            realizarOperacao(operacao); 
                        } else {
                            mostrarResultadosAnteriores();
                        }
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
        painelCampos.add(botaoVerResultado);

        janelaOperacao.setVisible(true);
    }

    private void mostrarResultadosAnteriores() {

        JFrame janelaResultados = new JFrame("Resultados Anteriores e Velocidade Média Total");
        janelaResultados.setSize(400, 300);
        janelaResultados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaResultados.setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        janelaResultados.getContentPane().add(painelPrincipal);

        JLabel labelTitulo = new JLabel("Resultados Anteriores e Velocidade Média Total");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelTitulo);

        JPanel painelResultados = new JPanel();
        painelResultados.setLayout(new BoxLayout(painelResultados, BoxLayout.Y_AXIS));
        painelResultados.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.add(painelResultados);

        for (int i = 0; i < totalRepeticoes; i++) {
            JLabel labelResultado = new JLabel("Operação " + (i + 1) + ": " +
                    "Velocidade Média = " + String.format("%.2f", vetorKm[i] / vetorHoras[i]));
            labelResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelResultados.add(labelResultado);

            painelResultados.add(Box.createRigidArea(new Dimension(0, 10)));
            painelResultados.add(new JSeparator(SwingConstants.HORIZONTAL));
            painelResultados.add(Box.createRigidArea(new Dimension(0, 10)));
        }

double somaKm = 0;
double somaHoras = 0;

for (int i = 0; i < totalRepeticoes; i++) {
    somaKm += vetorKm[i];
    somaHoras += vetorHoras[i];
}

double velocidadeMediaTotal = somaKm / somaHoras;
JLabel labelVelocidadeMediaTotal = new JLabel("Velocidade Média Total: " + String.format("%.2f", velocidadeMediaTotal));
labelVelocidadeMediaTotal.setFont(new Font("Arial", Font.BOLD, 14));
labelVelocidadeMediaTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
painelPrincipal.add(labelVelocidadeMediaTotal);

JButton botaoReiniciar = new JButton("Reiniciar Programa");
botaoReiniciar.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
        reiniciarPrograma();
    }
});

JPanel painelBotao = new JPanel();
painelBotao.setLayout(new FlowLayout(FlowLayout.CENTER));
painelBotao.add(botaoReiniciar);
painelPrincipal.add(painelBotao);

janelaResultados.setVisible(true);
}

private void reiniciarPrograma() {

    vetorKm = null;
vetorHoras = null;
contadorRepeticoes = 0;
totalRepeticoes = 0;
campoRepeticoes.setText("");
comboOperacoes.setSelectedIndex(0);

Window[] windows = Window.getWindows();

for (Window window : windows) {
    
if (window instanceof JFrame) {
JFrame frame = (JFrame) window;
if (frame.getTitle().equals("Resultados Anteriores e Velocidade Média Total")) {
    frame.dispose();
}
}
}

dispose();

new CalculadoraVelocidadeMedia();
}

private void calcularOperacao(String operacao, JTextField campoKm, JTextField campoHoras, JTextField campoVelocidadeMedia) {
double km = Double.parseDouble(campoKm.getText());
double horas = Double.parseDouble(campoHoras.getText());

switch (operacao) {
case "Velocidade Média":

double velocidadeMedia = km / horas;
campoVelocidadeMedia.setText(String.format("%.2f", velocidadeMedia));
break;

default:
JOptionPane.showMessageDialog(null, "Operação inválida.");
}
}
public static void main(String[] args) {
new CalculadoraVelocidadeMedia();
}
}
