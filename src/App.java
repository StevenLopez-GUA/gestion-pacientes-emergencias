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

    public String atenderPaciente() {
        if (persona == null) {
            return "No hay pacientes para atender.";
        }
        Paciente atendido = persona;
        persona = persona.siguiente;
        return "Paciente atendido: " + atendido;
    }

    public void buscarPorId(int id) {
        Paciente actual = persona;
        while (actual != null) {
            if (actual.id == id) {
                System.out.println("Paciente encontrado: " + actual);
                return;
            }
            actual = actual.siguiente;
        }
        System.out.println("No se encontró un paciente con ID: " + id);
    }

    public void eliminarPorId(int id) {
        if (persona == null) {
            System.out.println("No hay pacientes para eliminar.");
            return;
        }

        if (persona.id == id) {
            System.out.println("Paciente eliminado: " + persona);
            persona = persona.siguiente;
            return;
        }

        Paciente actual = persona;
        while (actual.siguiente != null) {
            if (actual.siguiente.id == id) {
                System.out.println("Paciente eliminado: " + actual.siguiente);
                actual.siguiente = actual.siguiente.siguiente;
                return;
            }
            actual = actual.siguiente;
        }

        System.out.println("No se encontró un paciente con ID: " + id);
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

    public static void atenderPaciente(ColaPacientes cola) {
        String mensaje = cola.atenderPaciente();
        System.out.println(mensaje);
    }

    public static void buscarPacientePorId(Scanner scanner, ColaPacientes cola) {
        while (true) {
            System.out.print("Ingrese el ID del paciente: ");
            String entrada = scanner.nextLine().trim();
            try {
                int id = Integer.parseInt(entrada);
                cola.buscarPorId(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero. Inténtelo nuevamente.");
            }
        }
    }

    public static void eliminarPacientePorId(Scanner scanner, ColaPacientes cola) {
        while (true) {
            System.out.print("Ingrese el ID del paciente a eliminar: ");
            String entrada = scanner.nextLine().trim();
            try {
                int id = Integer.parseInt(entrada);
                cola.eliminarPorId(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero. Inténtelo nuevamente.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ColaPacientes cola = new ColaPacientes();

        while (true) {
            System.out.println("\n1. Agregar paciente");
            System.out.println("2. Mostrar pacientes");
            System.out.println("3. Atender paciente");
            System.out.println("4. Buscar paciente por ID");
            System.out.println("5. Eliminar paciente por ID");
            System.out.println("0. Salir");
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
                    atenderPaciente(cola);
                    break;
                case 4:
                    buscarPacientePorId(scanner, cola);
                    break;
                case 5:
                    eliminarPacientePorId(scanner, cola);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}