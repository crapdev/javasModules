
package com.mycompany.corporatetalenthub.service;
import com.mycompany.corporatetalenthub.model.Employee;

public class EmployeeService {
    
    private static final double PROMEDIO_PARA_PROMOCION = 80.0;
    
    public static String obtenerCategoriaSalarial(double salario) {
        var rango = 0;
        
        if (salario < 2_000_000.0) {
            rango = 1;
        } else if (salario < 4_000_000.0) {
            rango = 2;
        } else if (salario < 7_000_000.0) {
            rango = 3;
        } else {
            rango = 4;
        }
        
        
        /*
         * Switch Expression moderna. La flecha evita el fall-through y el switch
         * devuelve un valor, por lo que no se necesita asignar y usar break en cada case.
         */
        return switch (rango) {
            case 1 -> "JUNIOR";
            case 2 -> "SEMISENIOR";
            case 3 -> "SENIOR";
            case 4 -> "LÍDER";
            default -> throw new IllegalArgumentException(
                    "Rango salarial no reconocido: " + rango);
        };
    }
    
    public boolean idRepetido( Employee[] empleados, int cantidadEmpleados, int idBuscado) {
        
        for (var i = 0; i < cantidadEmpleados; i++) {
            if (empleados[i].getId() == idBuscado) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
    
}
