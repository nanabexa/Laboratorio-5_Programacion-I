import java.io.BufferedReader;
import java.io.IOException;

public class Empleado {
    private String nombre;
    private String cedula;
    private String departamento;
    private double salarioBruto;
    private double salarioNeto;

    public Empleado (){}

    //Métodos Getters
    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getDepartamento() {
        return departamento;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public double getSalarioNeto() {
        return salarioNeto;
    }

    //Métodos setters
    public void setNombre(String nombre) throws NombreInvalidoException{
        System.out.println("Validando nombre: " + nombre);
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre no puede ser nulo o vacío.");
        }
        if (nombre.length() < 3 || nombre.length() > 50) {
            throw new NombreInvalidoException("El nombre debe tener entre 3 y 50 caracteres.");
        }
        if (nombre.matches(".*\\d.*")) {
            throw new NombreInvalidoException("El nombre no puede contener números.");
        }
        this.nombre = nombre;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
        calcularSalarioNeto();
    }

    //Métodos que hacen cosas muy métodicas

    public double calcularSeguroSocial(){
        return salarioBruto *0.0975;
    }

    public double calcularSeguroEducativo(){
        return salarioBruto *0.0125;
    }

    public void calcularSalarioNeto() {
        salarioNeto = salarioBruto - calcularSeguroSocial() - calcularSeguroEducativo();
    }

    public void obtenerDatos(BufferedReader reader) throws NombreInvalidoException, IOException  {
        System.out.println("Ingrese su nombre");
        setNombre(reader.readLine());
        System.out.println("Ingrese su cedula con guiones");
        setCedula(reader.readLine());
        System.out.println("Ingrese su departamento");
        setDepartamento(reader.readLine());
        System.out.println("Ingrese su salario Bruto");
        setSalarioBruto(Double.parseDouble(reader.readLine()));


    }
    public void imprimirDatos() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Cedula: " + cedula);
        System.out.println("Departamento: " + departamento);
        System.out.println("Salario Bruto: " + salarioBruto);
        System.out.println("Salario Neto: " + salarioNeto);


    }
}


