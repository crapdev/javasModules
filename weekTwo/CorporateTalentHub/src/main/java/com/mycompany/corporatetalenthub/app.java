package com.mycompany.corporatetalenthub;

import com.mycompany.corporatetalenthub.model.Employee;
import com.mycompany.corporatetalenthub.service.EmployeeService;
import com.mycompany.corporatetalenthub.view.ConsoleView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class app {

    private static final int MAXIMO_EMPLEADOS = 50;
    private static final int CANTIDAD_TRIMESTRES = 3;

    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 100.0;

    public static void main(String[] args) {

        var service = new EmployeeService();
        var view = new ConsoleView();

        try (var scanner = new Scanner(System.in)) {

            var empleados = new Employee[MAXIMO_EMPLEADOS];

            var calificaciones = new double[MAXIMO_EMPLEADOS][CANTIDAD_TRIMESTRES];

            var cantidadEmpleados = 0;
            var sistemaActivo = true;

            do {

                view.mostrarMenu();

                try {

                    System.out.print("Seleccione una opción: ");

                    var opcion = scanner.nextInt();
                    scanner.nextLine();

                    /*
                     * Switch tradicional compatible con Java 8.
                     *
                     * Si olvidamos el break puede ocurrir fall-through,
                     * haciendo que se ejecute también el siguiente case.
                     *
                     * En la Switch Expression moderna con -> este
                     * comportamiento no ocurre por defecto.
                     */
                    switch (opcion) {

                        case 0:
                            sistemaActivo = false;
                            view.mostrarMensaje("Sesión finalizada.");
                            break;
                            
                        case 1:
                            
                            if (cantidadEmpleados >= MAXIMO_EMPLEADOS) {
                                view.mostrarMensaje( "No hay espacio para más empleados.");
                                
                            } else {
                                var registrado = registrarEmpleado( scanner, empleados, calificaciones, cantidadEmpleados, service, view );
                                if (registrado) {
                                    cantidadEmpleados++;
                                }
                            }
                            break;

                        case 2:
                            mostrarReporte( empleados, calificaciones, cantidadEmpleados, service, view );
                            break;

                        case 3:
                            view.mostrarCategoriasSalariales();
                            break;


                        default:
                            view.mostrarMensaje("Opción fuera del menú.");
                            break;
                    }

                } catch (InputMismatchException excepcion) {

                    view.mostrarMensaje(
                            "Entrada inválida. Debe escribir un valor numérico."
                    );

                    scanner.nextLine();

                    /*
                     * Java moderno mejoró algunos diagnósticos de excepciones,
                     * especialmente Helpful NullPointerExceptions desde Java 14.
                     *
                     * Esto no significa que todas las excepciones tengan
                     * automáticamente mensajes más detallados.
                     */
                }

            } while (sistemaActivo);
        }
    }


    // ==============================
    // CAPTURA DE DATOS
    // ==============================

    private static boolean registrarEmpleado( Scanner scanner, Employee[] empleados, double[][] calificaciones, int posicion, EmployeeService service, ConsoleView view) {

        System.out.print("ID positivo: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        if (id <= 0) {
            view.mostrarMensaje("El ID debe ser mayor que cero.");
            return false;

        } else if (service.idRepetido(empleados, posicion, id)) {
            view.mostrarMensaje("Ya existe un empleado con ese ID.");
            return false;
        }


        System.out.print("Nombre: ");
        var nombre = scanner.nextLine().trim();

        if (nombre.isBlank()) {
            view.mostrarMensaje("El nombre no puede estar vacío.");
            return false;
        }


        System.out.print("Edad entre 18 y 100: ");
        var edadIngresada = scanner.nextInt();

        if (edadIngresada < 18 || edadIngresada > 100) {
            view.mostrarMensaje("La edad está fuera del rango permitido.");

            scanner.nextLine();
            return false;
        }

        /*
         * Scanner entrega un int.
         * Después de validar el rango se convierte a byte.
         */
        var edad = (byte) edadIngresada;


        System.out.print("Salario mayor que cero: ");
        var salario = scanner.nextDouble();

        if (salario <= 0) {

            view.mostrarMensaje("El salario debe ser mayor que cero.");

            scanner.nextLine();
            return false;
        }


        for (var trimestre = 0;
             trimestre < CANTIDAD_TRIMESTRES;
             trimestre++) {

            System.out.printf("Calificación del trimestre %d (0 a 100): ",trimestre + 1);

            var calificacion = scanner.nextDouble();

            if (calificacion < NOTA_MINIMA || calificacion > NOTA_MAXIMA) {

                view.mostrarMensaje("La calificación está fuera del rango permitido.");

                scanner.nextLine();
                return false;
            }

            calificaciones[posicion][trimestre] = calificacion;
        }

        scanner.nextLine();

        empleados[posicion] = new Employee(id, nombre, edad, salario);

        view.mostrarMensaje("Empleado registrado correctamente.");

        return true;
    }


    // ==============================
    // COORDINACIÓN DEL REPORTE
    // ==============================

    private static void mostrarReporte(Employee[] empleados, double[][] calificaciones, int cantidadEmpleados, EmployeeService service, ConsoleView view) {

        if (cantidadEmpleados == 0) {
            view.mostrarMensaje(
                    "Todavía no hay empleados registrados."
            );
            return;
        }

        view.mostrarTituloReporte();

        var promedios = service.calcularPromedios( calificaciones, cantidadEmpleados );

        for (var fila = 0; fila < cantidadEmpleados; fila++) {

            var promedio = promedios[fila];

            empleados[fila].setPromedioDesempeno(promedio);

            /*
             * Casting explícito de double a int.
             *
             * Se elimina la parte decimal:
             * 89.99 -> 89
             *
             * Esto implica pérdida de precisión.
             */
            var puntajeSimplificado = (int) promedio;

            var estadoPromocion = service.obtenerEstadoPromocion( promedio );

            var categoria = service.obtenerCategoriaSalarial( empleados[fila].getSalario() );

            view.mostrarEmpleado( empleados[fila], promedio, puntajeSimplificado, estadoPromocion, categoria );
        }
    }
}