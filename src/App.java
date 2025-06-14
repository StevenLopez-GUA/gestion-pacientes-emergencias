import java.util.Scanner;

class Paciente {
    private static int contadorID = 1;
    int id;
    String nombre;
    int urgencia;
    String horaLlegada;
    Paciente siguiente;

    public Paciente(String nombre, int urgencia, String horaLlegada) {
        this.id = contadorID++;
        this.nombre = nombre;
        this.urgencia = urgencia;
        this.horaLlegada = horaLlegada;
        this.siguiente = null;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Urgencia: " + urgencia + ", Hora: " + horaLlegada;
    }
}

class ColaPacientes {
    private Paciente persona;

    public void mostrarPacientes() {
        Paciente actual = persona;
        if (actual == null) {
            System.out.println("No hay pacientes en la cola.");
            return;
        }
        System.out.println("Lista de pacientes:");
        while (actual != null) {
            System.out.println(actual);
            actual = actual.siguiente;
        }
    }

    public void agregarPaciente(String nombre, int urgencia, String horaLlegada) {
        Paciente nuevo = new Paciente(nombre, urgencia, horaLlegada);
        if (persona == null || debeInsertarseAntes(nuevo, persona)) {
            nuevo.siguiente = persona;
            persona = nuevo;
        } else {
            Paciente actual = persona;
            while (actual.siguiente != null && !debeInsertarseAntes(nuevo, actual.siguiente)) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
        System.out.println("Paciente agregado: " + nuevo);
    }

    private boolean debeInsertarseAntes(Paciente nuevo, Paciente existente) {
        if (nuevo.urgencia > existente.urgencia)
            return true;
        if (nuevo.urgencia == existente.urgencia)
            return nuevo.horaLlegada.compareTo(existente.horaLlegada) < 0;
        return false;
    }
}

public class App {

    public static void crearPaciente(Scanner scanner, ColaPacientes cola) {
        String nombre;
        while (true) {
            System.out.print("Nombre: ");
            nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty())
                break;
            System.out.println("El nombre no puede estar vacío. Inténtelo nuevamente.");
        }

        int urgencia;
        while (true) {
            System.out.print("Nivel de urgencia (1-5): ");
            try {
                urgencia = Integer.parseInt(scanner.nextLine());
                if (urgencia >= 1 && urgencia <= 5)
                    break;
                System.out.println("El nivel de urgencia debe estar entre 1 y 5.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entre 1 y 5.");
            }
        }

        String hora;
        while (true) {
            System.out.print("Hora de llegada (HH:mm): ");
            hora = scanner.nextLine().trim();
            if (hora.matches("^([01]\\d|2[0-3]):([0-5]\\d)$"))
                break;
            System.out.println("Formato inválido. Debe usar el formato HH:mm (ejemplo: 14:30).");
        }

        cola.agregarPaciente(nombre, urgencia, hora);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ColaPacientes cola = new ColaPacientes();

        while (true) {
            System.out.println("\n1. Agregar paciente");
            System.out.println("2. Mostrar pacientes");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    crearPaciente(scanner, cola);
                    break;
                case 2:
                    cola.mostrarPacientes();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}