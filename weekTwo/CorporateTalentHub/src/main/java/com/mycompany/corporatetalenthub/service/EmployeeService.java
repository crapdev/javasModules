package com.mycompany.corporatetalenthub.service;
import com.mycompany.corporatetalenthub.model.Employee;

// Metodos antes en app: determinarRangoSalarial(...), obtenerCategoriaSalarial(...) y idRepetido(...).
//Trasladadas acá al separar la captura, logica del negocio y la presentacion
public class EmployeeService {
    
    private static final int CANTIDAD_TRIMESTRES = 3;
    private static final double PROMEDIO_PARA_PROMOCION = 80.0;
    
    
    public boolean idRepetido( Employee[] empleados, int cantidadEmpleados, int idBuscado) {

        for (var i = 0; i < cantidadEmpleados; i++) {

            if (empleados[i].getId() == idBuscado) {
                return true;
            }
        }

        return false;
    }
    
    
    public double[] calcularPromedios( double[][] calificaciones, int cantidadEmpleados) {

        var promedios = new double[cantidadEmpleados];

        /*
         * Bucles for anidados para recorrer
         * la matriz de calificaciones.
         */
        for (var fila = 0; fila < cantidadEmpleados; fila++) {

            var suma = 0.0;
            
            for (var columna = 0; columna < CANTIDAD_TRIMESTRES; columna++) {
                suma += calificaciones[fila][columna];
            }
            
            promedios[fila] = suma / CANTIDAD_TRIMESTRES;
            
        }
        return promedios;
    }
    
    public double calcularPromedio(
            double[][] calificaciones,
            int fila) {

        var suma = 0.0;

        for (var columna = 0;
             columna < CANTIDAD_TRIMESTRES;
             columna++) {

            suma += calificaciones[fila][columna];
        }

        return suma / CANTIDAD_TRIMESTRES;
    }

    public String obtenerEstadoPromocion(double promedio) {

        // Operador ternario solicitado por la tarea.
        return promedio >= PROMEDIO_PARA_PROMOCION ? "PROMOVIDO" : "NO PROMOVIDO";
    }

    public String obtenerCategoriaSalarial(double salario) {

        var rango = determinarRangoSalarial(salario);

        /*
         * Switch Expression moderna.
         * No necesita break y no tiene fall-through por defecto.
         */
        return switch (rango) {

            case 1 -> "JUNIOR";
            case 2 -> "SEMISENIOR";
            case 3 -> "SENIOR";
            case 4 -> "LÍDER";

            default -> throw new IllegalArgumentException(
                    "Rango salarial no reconocido: " + rango
            );
        };
    }

    private int determinarRangoSalarial(double salario) {

        if (salario < 2_000_000.0) {
            return 1;

        } else if (salario < 4_000_000.0) {
            return 2;

        } else if (salario < 7_000_000.0) {
            return 3;

        } else {
            return 4;
        }
    }
    
    
    
    
    
    
}
