import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args)   {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Empleado empleado = new Empleado();
        try {
            empleado.obtenerDatos(br);
            empleado.imprimirDatos();

        }catch (NombreInvalidoException | CedulaInvalidaException | DepartamentoInvalidoException e) {
            System.out.println("Error en el nombre: " + e.getMessage());
        }catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un valor numérico válido. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        }



    }
}