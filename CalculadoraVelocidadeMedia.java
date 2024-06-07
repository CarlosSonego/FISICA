import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraVelocidadeMedia extends JFrame {

    private JLabel titulo;
    private JComboBox<String> copr;
    private JLabel rpt;
    private JTextField crpt;
    private JButton ini;
    private int cntrpt = 0;
    private int trpt = 0;
    private double[] vetorKm;
    private double[] vetorHoras;

    public CalculadoraVelocidadeMedia() {
        super("Calculadora de Velocidade Média");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

        JPanel pp = new JPanel();
            pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
                getContentPane().add(pp);

        titulo = new JLabel("Escolha a operação e número de repetições:");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            pp.add(titulo);

        JPanel po = new JPanel();

            po.setLayout(new FlowLayout(FlowLayout.CENTER));
            copr = new JComboBox<>(new String[]{"Velocidade Média"});
            copr.setAlignmentX(Component.CENTER_ALIGNMENT);
            po.add(copr);
            pp.add(po);

        JPanel pr = new JPanel();

            pr.setLayout(new FlowLayout(FlowLayout.CENTER));
            rpt = new JLabel("Número de repetições:");
            rpt.setAlignmentX(Component.CENTER_ALIGNMENT);
            pr.add(rpt);
            crpt = new JTextField(10);
            crpt.setAlignmentX(Component.CENTER_ALIGNMENT);
            pr.add(crpt);
            pp.add(pr);

        ini = new JButton("Iniciar");
        ini.setAlignmentX(Component.CENTER_ALIGNMENT);
        ini.addActionListener(new ActionListener() {

    @Override
        public void actionPerformed(ActionEvent e) {
            iniciarOperacoes();
}
});
        pp.add(ini);
            setVisible(true);
    }

    private void iniciarOperacoes() {
        try {
            String os = (String) copr.getSelectedItem();
            trpt = Integer.parseInt(crpt.getText());

            if (trpt > 0) {
                cntrpt = 0;

                vetorKm = new double[trpt];
                vetorHoras = new double[trpt];
                realizarOperacao(os);
            } else {
                JOptionPane.showMessageDialog(this, "Número deve ser maior que zero.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "insira um número válido para repetições.");
        }
    }

    private void realizarOperacao(String operacao) {
        cntrpt++;

        JFrame jo = new JFrame("Operação " + cntrpt + "/" + trpt);
        jo.setSize(400, 300);
        jo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jo.setLocationRelativeTo(null);

        JPanel po = new JPanel();
        po.setLayout(new BoxLayout(po, BoxLayout.Y_AXIS));
        jo.getContentPane().add(po);

        JLabel op = new JLabel("Operação " + cntrpt + "/" + trpt + ": " + operacao);
        op.setFont(new Font("Arial", Font.BOLD, 16));
        op.setAlignmentX(Component.CENTER_ALIGNMENT);
        po.add(op);

        JPanel cmp = new JPanel();
        cmp.setLayout(new GridLayout(4, 2, 10, 10));
        cmp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        po.add(cmp);

        JLabel labelKm = new JLabel("Km percorridos:");
        JTextField ckm = new JTextField(10);
        ckm.setAlignmentX(Component.CENTER_ALIGNMENT);
        cmp.add(labelKm);
        cmp.add(ckm);

        JLabel hrs = new JLabel("Horas:");
        JTextField chrs = new JTextField(10);
        chrs.setAlignmentX(Component.CENTER_ALIGNMENT);
        cmp.add(hrs);
        cmp.add(chrs);

        JLabel vm = new JLabel("Velocidade Média:");
        JTextField cvm = new JTextField(10);
        cvm.setEditable(false); 
        cvm.setAlignmentX(Component.CENTER_ALIGNMENT);
        cmp.add(vm);
        cmp.add(cvm);

        JButton resultado = new JButton("Resultado");
        resultado.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularOperacao(operacao, ckm, chrs, cvm);

                try {
                    vetorKm[cntrpt - 1] = Double.parseDouble(ckm.getText());
                    vetorHoras[cntrpt - 1] = Double.parseDouble(chrs.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "insira valores válidos para Km e Horas.");
                    return;
                }

                cmp.revalidate();
                cmp.repaint();

                Timer timer = new Timer(2000, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jo.dispose(); 
                        if (cntrpt < trpt) {
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
        cmp.add(resultado);

        jo.setVisible(true);
    }

    private void mostrarResultadosAnteriores() {

        JFrame resultados = new JFrame("Resultados Anteriores e Velocidade Média Total");
        resultados.setSize(400, 300);
        resultados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultados.setLocationRelativeTo(null);

        JPanel pp = new JPanel();
        pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
        resultados.getContentPane().add(pp);

        JLabel titulo = new JLabel("Resultados Anteriores e Velocidade Média Total");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        pp.add(titulo);

        JPanel presultados = new JPanel();
        presultados.setLayout(new BoxLayout(presultados, BoxLayout.Y_AXIS));
        presultados.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pp.add(presultados);

        for (int i = 0; i < trpt; i++) {
            JLabel labelResultado = new JLabel("Operação " + (i + 1) + ": " +
                    "Velocidade Média = " + String.format("%.2f", vetorKm[i] / vetorHoras[i]));
            labelResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
            presultados.add(labelResultado);

            presultados.add(Box.createRigidArea(new Dimension(0, 10)));
            presultados.add(new JSeparator(SwingConstants.HORIZONTAL));
            presultados.add(Box.createRigidArea(new Dimension(0, 10)));
        }

double somaKm = 0;
double somaHoras = 0;

for (int i = 0; i < trpt; i++) {
    somaKm += vetorKm[i];
    somaHoras += vetorHoras[i];
}

double velocidadeMediaTotal = somaKm / somaHoras;
JLabel vmTotal = new JLabel("Velocidade Média Total: " + String.format("%.2f", velocidadeMediaTotal));
vmTotal.setFont(new Font("Arial", Font.BOLD, 14));
vmTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
pp.add(vmTotal);

JButton botaoReiniciar = new JButton("Reiniciar Programa");
botaoReiniciar.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
        reiniciarPrograma();
    }
});

JPanel bt = new JPanel();
bt.setLayout(new FlowLayout(FlowLayout.CENTER));
bt.add(botaoReiniciar);
pp.add(bt);

resultados.setVisible(true);
}

private void reiniciarPrograma() {

    vetorKm = null;
vetorHoras = null;
cntrpt = 0;
trpt = 0;
crpt.setText("");
copr.setSelectedIndex(0);

Window[] windows = Window.getWindows();

for (Window window : windows) {
    
if (window instanceof JFrame) {
JFrame frame = (JFrame) window;
if (frame.getTitle().equals("Resultados Anteriores/Velocidade Média Total")) {
    frame.dispose();
}   
    }
        }

dispose();

new CalculadoraVelocidadeMedia();
}

private void calcularOperacao(String operacao, JTextField ckm, JTextField chrs, JTextField cvm) {
double km = Double.parseDouble(ckm.getText());
double horas = Double.parseDouble(chrs.getText());

switch (operacao) {
case "Velocidade Média":

double velocidadeMedia = km / horas;
cvm.setText(String.format("%.2f", velocidadeMedia));
break;

default:
JOptionPane.showMessageDialog(null, "Operação inválida.");
}
}
public static void main(String[] args) {
new CalculadoraVelocidadeMedia();
}
}
