import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class minhaJanela {

    private static DefaultListModel<String> modeloLista;
    private static JTextField novaTarefa;
    private static final String ARQUIVO_TAREFAS = "tasks.txt";

     public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CriarEEexibirGUI();
            }
        });
    }
         // Método para criar e exibir a GUI
    private static void CriarEEexibirGUI() {
        JFrame janela = new JFrame("Task App");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(800, 600);

        janela.setLayout(new BorderLayout());

        JPanel painel = new JPanel();

        novaTarefa = new JTextField(20);

        JButton adicionarButao = new JButton("Adicione uma Task");

        painel.add(novaTarefa);
        painel.add(adicionarButao);

        janela.add(painel, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();

        JList<String> listaTarefas = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaTarefas);
        janela.add(scrollPane, BorderLayout.CENTER);

        // Ação do botão para adicionar tarefa
        adicionarButao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tarefa = novaTarefa.getText(); 
                if (!tarefa.isEmpty()) {
                    modeloLista.addElement(tarefa);
                    novaTarefa.setText("");
                    salvarTarefas(); // Salva as tarefas após adicionar
                }
            }
        });

        // Carrega as tarefas ao iniciar o programa
        carregarTarefas();

        // Exibe a janela
        janela.setVisible(true);
        janela.setLocationRelativeTo(null);
    }

    // Método para salvar tarefas no arquivo
    private static void salvarTarefas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_TAREFAS))) {
            for (int i = 0; i < modeloLista.size(); i++) {
                writer.write(modeloLista.getElementAt(i));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    // Método para carregar tarefas do arquivo
    private static void carregarTarefas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_TAREFAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                modeloLista.addElement(linha);
            }
        } catch (FileNotFoundException e) {
            // Arquivo ainda não existe, não faz nada
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar tarefas: " + e.getMessage());
        }
    }
}
