/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class EPSAtencionSimulacion extends JFrame {
    // Componentes de la interfaz
    private JTextField txtCedula;
    private JComboBox<String> cbCategoria, cbServicio;
    private JLabel lblHoraActual, lblProximoPaciente;
    private DefaultListModel<String> modeloListaPacientes;
    private JList<String> listaPacientes;
    private Timer timerAtencion;
    private JSlider sliderVelocidad;

    // Variables de control
    private ArrayList<Paciente> pacientes;
    private int indexPaciente = 0; // Índice del paciente actual
    private int velocidad = 1000; // Velocidad inicial de rotación (1s)
    private boolean simulacionIniciada = false; // Controla si la simulación ya ha iniciado

    public EPSAtencionSimulacion() {
        // Inicializar lista de pacientes
        pacientes = new ArrayList<>();

        // Panel izquierdo (Toma de datos)
        JPanel panelIzquierdo = new JPanel(new GridLayout(6, 2));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Toma de datos"));

        panelIzquierdo.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelIzquierdo.add(txtCedula);

        panelIzquierdo.add(new JLabel("Categoría:"));
        cbCategoria = new JComboBox<>(new String[] {"Menor de 60 años", "Adulto mayor", "Persona con discapacidad"});
        panelIzquierdo.add(cbCategoria);

        panelIzquierdo.add(new JLabel("Servicio:"));
        cbServicio = new JComboBox<>(new String[] {"Consulta con médico general", "Consulta con especialista", 
                                                   "Prueba de laboratorio", "Imágenes diagnósticas"});
        panelIzquierdo.add(cbServicio);

        // Botón registrar y hora actual
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarPaciente();
            }
        });
        panelIzquierdo.add(btnRegistrar);

        lblHoraActual = new JLabel();
        actualizarHoraActual();
        panelIzquierdo.add(lblHoraActual);
        
        // Timer para actualizar la hora actual
        Timer timerHora = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarHoraActual();
            }
        });
        timerHora.start();

        // Panel derecho (Simulación de fila)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Fila de atención"));

        lblProximoPaciente = new JLabel("Próximo paciente: ");
        panelDerecho.add(lblProximoPaciente, BorderLayout.NORTH);

        modeloListaPacientes = new DefaultListModel<>();
        listaPacientes = new JList<>(modeloListaPacientes);
        JScrollPane scrollLista = new JScrollPane(listaPacientes);
        panelDerecho.add(scrollLista, BorderLayout.CENTER);

        // Slider para controlar la velocidad (invertido)
        sliderVelocidad = new JSlider(JSlider.HORIZONTAL, 500, 2000, 1000); // 0.5s a 2s
        sliderVelocidad.setInverted(true); // Invertir el slider
        sliderVelocidad.setMajorTickSpacing(500);
        sliderVelocidad.setPaintTicks(true);
        sliderVelocidad.setPaintLabels(true);
        sliderVelocidad.addChangeListener(e -> {
            velocidad = sliderVelocidad.getValue();
            if (timerAtencion != null) {
                timerAtencion.setDelay(velocidad);
            }
        });
        panelDerecho.add(sliderVelocidad, BorderLayout.SOUTH);

        // Layout general
        setLayout(new GridLayout(1, 2));
        add(panelIzquierdo);
        add(panelDerecho);

        // Configuraciones de la ventana
        setTitle("Simulación de atención en EPS");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Método para registrar un paciente
    private void registrarPaciente() {
        String cedula = txtCedula.getText().trim();
        
        // Validación de cédula
        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "La cédula debe contener solo números.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cedula.length() < 6 || cedula.length() > 12) {
            JOptionPane.showMessageDialog(this, "La cédula debe tener entre 6 y 12 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Paciente p : pacientes) {
            if (p.getCedula().equals(cedula)) {
                JOptionPane.showMessageDialog(this, "El paciente ya está en la fila.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (pacientes.size() >= 10) {
            JOptionPane.showMessageDialog(this, "No se pueden registrar más de 10 pacientes.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Almacenar datos
        String categoria = cbCategoria.getSelectedItem().toString();
        String servicio = cbServicio.getSelectedItem().toString();
        String horaLlegada = lblHoraActual.getText();

        Paciente nuevoPaciente = new Paciente(cedula, categoria, servicio, horaLlegada);
        pacientes.add(nuevoPaciente);
        modeloListaPacientes.addElement("Cédula: " + cedula + " - Servicio: " + servicio + " - Hora: " + horaLlegada);

        if (pacientes.size() == 10 && !simulacionIniciada) {
            iniciarSimulacion(); // Iniciar solo cuando se hayan registrado 10 pacientes
        }
    }

    // Método para iniciar la simulación de atención
    private void iniciarSimulacion() {
        simulacionIniciada = true; // Evitar que la simulación se inicie más de una vez
        timerAtencion = new Timer(velocidad, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atenderPaciente();
            }
        });
        timerAtencion.start();
    }

    // Método para atender un paciente
    private void atenderPaciente() {
        if (indexPaciente < pacientes.size()) {
            lblProximoPaciente.setText("Próximo paciente: " + pacientes.get(indexPaciente).getCedula());
            modeloListaPacientes.remove(0); // Eliminar paciente de la lista visual
            indexPaciente++;
        } else {
            timerAtencion.stop(); // Detener cuando se hayan atendido todos
        }
    }

    // Método para actualizar la hora actual
    private void actualizarHoraActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        lblHoraActual.setText(sdf.format(new Date()));
    }

    // Clase Paciente
    class Paciente {
        private String cedula, categoria, servicio, horaLlegada;

        public Paciente(String cedula, String categoria, String servicio, String horaLlegada) {
            this.cedula = cedula;
            this.categoria = categoria;
            this.servicio = servicio;
            this.horaLlegada = horaLlegada;
        }

        public String getCedula() { return cedula; }
        public String getCategoria() { return categoria; }
        public String getServicio() { return servicio; }
        public String getHoraLlegada() { return horaLlegada; }
    }

    public static void main(String[] args) {
        new EPSAtencionSimulacion();
    }
}
