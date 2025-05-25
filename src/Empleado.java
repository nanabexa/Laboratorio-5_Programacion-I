import java.io.BufferedReader;
import java.io.IOException;

public class Empleado {
    private String nombre;
    private String cedula;
    private String departamento;
    private Double salarioBruto;
    private Double salarioNeto;
    private Double prestamo;
    private Double cuota;
    private Double pension;
    private BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));


    public Empleado (){
        this.nombre = "";
        this.cedula = "";
        this.departamento = "";
        this.salarioBruto = 0.0;
        this.salarioNeto = 0.0;
        this.prestamo = 0.0;
        this.cuota = 0.0;
        this.pension = 0.0;
    }//Profe si le ponía parámetros o hacía el constructor completo, las excepciones no se ejecutaban

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

    public double getPrestamo() {
        return prestamo;
    }

    public double getCuota() {
        return cuota;
    }

    public double getPension() {
        return pension;
    }

    //Métodos setters
    public void setNombre(String nombre) throws NombreInvalidoException{
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre no puede ser nulo o vacío.");
        }
        if (nombre.length() < 3 || nombre.length() > 50) {
            throw new NombreInvalidoException("El nombre debe tener entre 3 y 50 caracteres.");
        }
        if (nombre.matches(".*\\d.*")) {
            throw new NombreInvalidoException("El nombre no puede contener números.");
        }
        if (!nombre.matches("[a-zA-Z]+( [a-zA-Z]+)*")) {
            throw new NombreInvalidoException("El nombre no puede contener caracteres especiales.");
        }
        if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ]+(\\s+[A-Za-zÁÉÍÓÚáéíóúÑñ]+)+$")) {
            throw new NombreInvalidoException("Debe ingresar al menos un nombre y un apellido, usando solo letras.");
        }
        if (nombre.matches(".*(.)\\1{3,}.*")) {
            throw new NombreInvalidoException("El nombre no debe contener la misma letra muchas veces seguidas.");
            }
        if (nombre.matches(".*\\s+\\s+.*")) {
            throw new NombreInvalidoException("El nombre no debe contener espacios en blanco.");
        }
        String[] palabras = nombre.trim().split("\\s+");
        for (String palabra : palabras) {
            if (!palabra.matches("[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+")) {
                throw new NombreInvalidoException("Cada palabra debe iniciar con mayúscula seguida de minúsculas. Error en: " + palabra);
            }
        }

        this.nombre = nombre;
    }
    public void setCedula(String cedula) throws CedulaInvalidaException {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new CedulaInvalidaException("La cédula no puede estar vacía.");
        }
        // Nacional: formato típico 8-2345-6789
        // Extranjero: formato E-123-456
        if (!(cedula.matches("\\d+-\\d+-\\d+") || cedula.matches("E-\\d+-\\d+"))) {
            throw new CedulaInvalidaException("Formato de cédula inválido. Ejemplos válidos: 8-1234-5678 o E-123-456.");
        }
        if (cedula.matches("\\d+-.*") && cedula.startsWith("0")) {
            throw new CedulaInvalidaException("La cédula nacional no puede comenzar con 0.");
        }
        if (cedula.startsWith("E-")) {
            String[] partes = cedula.split("-");
            if (partes.length < 2 || partes[1].startsWith("0")) {
                throw new CedulaInvalidaException("La cédula extranjera no puede comenzar con 0 después de la 'E-'.");
            }
        }

        String noCeros = cedula.replaceAll("[^0-9]", ""); // quita letras y guiones
        if (noCeros.matches("0+")) {
            throw new CedulaInvalidaException("La cédula no puede contener solo ceros.");
        }

        this.cedula = cedula;
    }
    public void setDepartamento(String departamento) throws DepartamentoInvalidoException {
        if (departamento == null || departamento.trim().isEmpty()) {
            throw new DepartamentoInvalidoException("El departamento no puede estar vacío.");
        }
        // Validación sin importar mayúsculas/minúsculas
        if (departamento.equalsIgnoreCase("FINANZAS") ||
                departamento.equalsIgnoreCase("RECURSOS HUMANOS") ||
                departamento.equalsIgnoreCase("VENTAS") ||
                departamento.equalsIgnoreCase("TECNOLOGIA") ||
                departamento.equalsIgnoreCase("ADMINISTRACION")) {
            this.departamento = departamento;

        } else {
            throw new DepartamentoInvalidoException(
                    "Departamento inválido. Debe ser Finanzas, Recursos Humanos, Ventas, Tecnología o Administración."
            );
        }
    }

    public void setSalarioBruto(Double salarioBruto) throws SalarioBrutoInvalido {
        if (salarioBruto == null) {
            throw new SalarioBrutoInvalido("El salario bruto no puede estar vacío.");
        }
        if (salarioBruto <= 0) {
            throw new SalarioBrutoInvalido("El salario bruto debe ser mayor que 0.");
        }
        this.salarioBruto = salarioBruto;
        calcularSalarioNeto();
    }
    public void setPrestamo(Double prestamo){
        this.prestamo = prestamo;
        calcularSalarioNeto();
    }

    public void setCuota(Double cuota){
        this.cuota = cuota;
        calcularSalarioNeto();
    }

    public void setPension(Double pension){
        this.pension = pension;
        calcularSalarioNeto();
    }

    //Métodos que hacen cosas muy métodicas

    public double calcularSeguroSocial(){
        return salarioBruto *0.0975;
    }

    public double calcularSeguroEducativo(){
        return salarioBruto *0.0125;
    }
    public double descuentosAdicionales() throws IOException {
        Double totalDescuentosAdicionales = 0.0;
        System.out.println("¿Desea descontar un préstamo? s/n");
        if ("s".equals(reader.readLine().toLowerCase())) { //Puedes investigar cual es la diferencia entre toLowerCase y equalsIgnoreCase, intellij me marca que debes reemplazar
            System.out.println("Ingrese el valor del préstamo");
            setPrestamo(Double.parseDouble(reader.readLine()));
        }
        System.out.println("¿Desea descontar una cuota? s/n");
        if ("s".equals(reader.readLine().toLowerCase())) {
            System.out.println("Ingrese el valor de la cuota");
            setCuota(Double.parseDouble(reader.readLine()));
        }
        System.out.println("¿Desea descontar una pensión? s/n");
        if ("s".equals(reader.readLine().toLowerCase())) {
            System.out.println("Ingrese el valor de la pensión");
            setPension(Double.parseDouble(reader.readLine()));
        }return totalDescuentosAdicionales;
    }//Lo ideal es meterlo en un metodo y luego pasarlo a la impresion es mas facil poner las exepciones ahi, y no llenar demasiado de codigo el prinft


    public void calcularSalarioNeto() {
        salarioNeto = salarioBruto - calcularSeguroSocial() - calcularSeguroEducativo() - prestamo - cuota - pension;
    }

    public void obtenerDatos(BufferedReader reader) throws NombreInvalidoException,CedulaInvalidaException, IOException, DepartamentoInvalidoException, SalarioBrutoInvalido  {
        System.out.println("Ingrese su nombre");
        setNombre(reader.readLine());
        System.out.println("Ingrese su cedula con guiones");
        setCedula(reader.readLine());
        System.out.println("Departamentos:\nFinanzas, Recursos Humanos, Ventas, Tecnología o Administración");
        System.out.println("Ingrese su departamento");
        setDepartamento(reader.readLine());
        System.out.println("Ingrese su salario Bruto");
        setSalarioBruto(Double.parseDouble(reader.readLine()));
        descuentosAdicionales();//Esta arriba


    }
    public void imprimirDatos() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Cedula: " + cedula);
        System.out.println("Departamento: " + departamento);
        System.out.println("Salario Bruto: " + salarioBruto);
        System.out.println("Salario Neto: " +  String.format("%.2f", salarioNeto));

    }
}