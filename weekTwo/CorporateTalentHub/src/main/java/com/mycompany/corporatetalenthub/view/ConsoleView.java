
package com.mycompany.corporatetalenthub.view;
import com.mycompany.corporatetalenthub.model.Employee;

// Metodos antes en app: mostrarMenu() y mostrarCategoriasSalariales(). Trasladadas acá al separar la captura, logica del negocio y la presentacion
public class ConsoleView {
    
    
    
    
    public void mostrarMenu() {

        System.out.println("""
                
                    =====================================
                         CORPORATE TALENT HUB
                    =====================================
                    1. Registrar empleado y calificaciones
                    2. Mostrar reporte de desempeño
                    3. Consultar categorías salariales
                    0. Salir
                
                """);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    public void mostrarTituloReporte() {

        System.out.println(
                "\nREPORTE DE DESEMPEÑO"
        );
    }

    public void mostrarCategoriasSalariales() {

        System.out.println("""
                
                    Categorías:
                    - Menos de $2.000.000: JUNIOR
                    - Desde $2.000.000 y menos de $4.000.000: SEMISENIOR
                    - Desde $4.000.000 y menos de $7.000.000: SENIOR
                    - Desde $7.000.000: LÍDER
                
                """);
    }

    public void mostrarEmpleado( Employee empleado, double promedio, int puntajeSimplificado, String estado, String categoria) {

        System.out.printf(
                "ID: %d | Nombre: %s | Promedio: %.2f | "
                        + "Simplificado: %d | Estado: %s | Categoría: %s%n",

                empleado.getId(),
                empleado.getNombre(),
                promedio,
                puntajeSimplificado,
                estado,
                categoria
        );
    }
    
    
}
