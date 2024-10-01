/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package epsproyecto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

class Patient {
    private String id;
    private String category;
    private String service;
    private String arrivalTime;

    public Patient(String id, String category, String service, String arrivalTime) {
        this.id = id;
        this.category = category;
        this.service = service;
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getService() {
        return service;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Categoría: " + category + ", Servicio: " + service + ", Llegada: " + arrivalTime;
    }
}

public class EPSServiceSimulator extends JFrame {

    // Componentes de la interfaz
    private JTextField idField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> serviceBox;
    private JButton registerButton;
    private JLabel timeLabel;
    private DefaultListModel<Patient> listModel;
    private JList<Patient> patientList;
    private JSlider speedSlider;
    private Timer timer;

    // Datos
    private Queue<Patient> queue;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Timer intervals in milliseconds
    private int interval = 15000; // 15 segundos por defecto

    public EPSServiceSimulator() {
        setTitle("Simulador de Servicio de Atención EPS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        queue = new LinkedList<>();

        // Panel Izquierdo - Entrada de Datos
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Número de Cédula
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Número de Cédula:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(15);
        leftPanel.add(idField, gbc);

        // Categoría
        gbc.gridx = 0;
        gbc.gridy = 1;
        leftPanel.add(new JLabel("Categoría:"), gbc);

        gbc.gridx = 1;
        String[] categories = {"Menor de 60 años", "Adulto Mayor", "Persona con Discapacidad"};
        categoryBox = new JComboBox<>(categories);
        leftPanel.add(categoryBox, gbc);

        // Servicio Solicitado
        gbc.gridx = 0;
        gbc.gridy = 2;
        leftPanel.add(new JLabel("Servicio Solicitado:"), gbc);

        gbc.gridx = 1;
        String[] services = {"Consulta con Médico General", "Consulta con Especialista", "Prueba de Laboratorio", "Imágenes Diagnósticas"};
        serviceBox = new JComboBox<>(services);
        leftPanel.add(serviceBox, gbc);

        // Botón Registrar y Hora
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerButton = new JButton("Registrar");
        leftPanel.add(registerButton, gbc);

        gbc.gridx = 1;
        timeLabel = new JLabel(getCurrentTime());
        leftPanel.add(timeLabel, gbc);

        add(leftPanel, BorderLayout.WEST);

        // Panel Derecho - Lista de Pacientes y Control de Velocidad
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Lista de Pacientes
        listModel = new DefaultListModel<>();
        patientList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(patientList);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Control de Velocidad
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BorderLayout());
        speedPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        speedPanel.add(new JLabel("Control de Velocidad:"), BorderLayout.NORTH);

        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 2, 1);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        // Establecer orientación de derecha a izquierda
        speedSlider.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // Crear etiquetas personalizadas para el slider
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("Rápido"));
        labelTable.put(1, new JLabel("Medio"));
        labelTable.put(2, new JLabel("Lento"));
        speedSlider.setLabelTable(labelTable);

        speedSlider.setSnapToTicks(true);
        speedSlider.setToolTipText("Ajusta la velocidad de atención");

        speedPanel.add(speedSlider, BorderLayout.CENTER);
        rightPanel.add(speedPanel, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.CENTER);

        // Acción del Botón Registrar
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerPatient();
            }
        });

        // Timer para actualizar la hora actual cada segundo
        Timer clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText(getCurrentTime());
            }
        });
        clockTimer.start();

        // Timer para simular la atención de pacientes
        timer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attendNextPatient();
            }
        });
        timer.start();

        // Cambio en el control deslizante para ajustar la velocidad
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                adjustTimerSpeed();
            }
        });

        setVisible(true);
    }

    private String getCurrentTime() {
        return LocalTime.now().format(timeFormatter);
    }

    private void registerPatient() {
        String id = idField.getText().trim();
        String category = (String) categoryBox.getSelectedItem();
        String service = (String) serviceBox.getSelectedItem();

        // Validación de número de cédula
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El número de cédula debe contener solo números.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (queue.size() >= 10) {
            JOptionPane.showMessageDialog(this, "La fila está llena. No se pueden registrar más pacientes.", "Fila Completa", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String arrivalTime = getCurrentTime();
        Patient newPatient = new Patient(id, category, service, arrivalTime);
        queue.add(newPatient);
        listModel.addElement(newPatient);

        // Limpiar campos después del registro
        idField.setText("");
        categoryBox.setSelectedIndex(0);
        serviceBox.setSelectedIndex(0);
    }

    private void attendNextPatient() {
        if (!queue.isEmpty()) {
            Patient attended = queue.poll();
            listModel.removeElement(attended);
            // Puedes agregar lógica adicional aquí si es necesario
        }
    }

    private void adjustTimerSpeed() {
        int sliderValue = speedSlider.getValue();
        switch (sliderValue) {
            case 0: // Rápido
                interval = 5000; // 5 segundos (1s=0.5s)
                break;
            case 1: // Medio
                interval = 15000; // 15 segundos (1s=1s)
                break;
            case 2: // Lento
                interval = 30000; // 30 segundos (1s=2s)
                break;
            default:
                interval = 15000;
        }
        timer.setDelay(interval);
        timer.setInitialDelay(interval);
    }

    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EPSServiceSimulator();
            }
        });
    }
}
