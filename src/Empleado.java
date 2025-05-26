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
                departamento.equalsIgnoreCase("TECNOLOGÍA") ||
                departamento.equalsIgnoreCase("ADMINISTRACIÓN")) {
            this.departamento = departamento;

        } else {
            throw new DepartamentoInvalidoException(
                    "Departamento inválido. Debe ser Finanzas, Recursos Humanos, Ventas, Tecnología o Administración.\nRecuerde utilizar tildes"
            );
        }
    }

    public void setSalarioBruto(Double salarioBruto) throws SalarioBrutoInvalidoException {
        if (salarioBruto == null) {
            throw new SalarioBrutoInvalidoException("El salario bruto no puede estar vacío.");
        }
        if (salarioBruto <= 0) {
            throw new SalarioBrutoInvalidoException("El salario bruto debe ser mayor que 0.");
        }
        this.salarioBruto = salarioBruto;
        calcularSalarioNeto();
    }
    public void setPrestamo(Double prestamo){
        if(prestamo == null){
            throw new PrestamoInvalidoException("El préstamo no puede ser nulo.");
        }
        if (prestamo <= 0) {
            throw new PrestamoInvalidoException("El préstamo debe ser mayor que 0.");
        }
        this.prestamo = prestamo;
        calcularSalarioNeto();
    }

    public void setCuota(Double cuota){
        if(cuota == null){
            throw new CuotaInvalidaException("La cuota no puede ser nulo.");
        }
        if (cuota <= 0) {
            throw new CuotaInvalidaException("La cuota debe ser mayor que 0.");
        }
        this.cuota = cuota;
        calcularSalarioNeto();
    }

    public void setPension(Double pension){
        if(pension == null){
            throw new PensionInvalidaException("La pension no puede ser nulo.");
        }
        if (pension <= 0) {
            throw new PensionInvalidaException("La pension debe ser mayor que 0.");
        }
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

    public void checkPrestamo(String sino) throws PrestamoInvalidoException, IOException {
        if(sino == null){
            throw new PrestamoInvalidoException("La selección de alternativa no puede ser nula.");
        } else if ("s".equalsIgnoreCase(sino)) {
            System.out.println("Ingrese el valor del préstamo");
            setPrestamo(Double.parseDouble(reader.readLine()));
            return;
        } else if ("n".equalsIgnoreCase(sino)) {
            return;
        } else {
            throw new PrestamoInvalidoException("el caracter introducido no es válido. Debe ser un \"s\" (sí) o \"n\" (no).");
            }
    }

    public void checkCuota(String sino) throws CuotaInvalidaException, IOException {
        if(sino == null){
            throw new CuotaInvalidaException("La selección de alternativa no puede ser nula.");
        } else if ("s".equalsIgnoreCase(sino)) {
            System.out.println("Ingrese el valor de la cuota");
            setCuota(Double.parseDouble(reader.readLine()));
            return;
        } else if ("n".equalsIgnoreCase(sino)) {
            return;
        } else {
            throw new CuotaInvalidaException("el caracter introducido no es válido. Debe ser un \"s\" (sí) o \"n\" (no).");
        }
    }

    public void checkPension(String sino) throws PensionInvalidaException, IOException {
        if(sino == null){
            throw new PensionInvalidaException("La selección de alternativa no puede ser nula.");
        } else if ("s".equalsIgnoreCase(sino)) {
            System.out.println("Ingrese el valor de la pensión");
            setPension(Double.parseDouble(reader.readLine()));
            return;
        } else if ("n".equalsIgnoreCase(sino)) {
            return;
        } else {
            throw new PensionInvalidaException("el caracter introducido no es válido. Debe ser un \"s\" (sí) o \"n\" (no).");
        }
    }

    public void descuentosAdicionales() throws IOException {
        System.out.println("¿Desea descontar un préstamo? s/n");
        checkPrestamo(reader.readLine());
        System.out.println("¿Desea descontar una cuota? s/n");
        checkCuota(reader.readLine());
        System.out.println("¿Desea descontar una pensión? s/n");
        checkPension(reader.readLine());
        return;
    }

    public void calcularSalarioNeto() {
        salarioNeto = salarioBruto - calcularSeguroSocial() - calcularSeguroEducativo() - prestamo - cuota - pension;
    }

    public void obtenerDatos(BufferedReader reader) throws NombreInvalidoException,CedulaInvalidaException, IOException, DepartamentoInvalidoException, SalarioBrutoInvalidoException {
        System.out.println("Ingrese su nombre");
        setNombre(reader.readLine());
        System.out.println("Ingrese su cedula con guiones");
        setCedula(reader.readLine());
        System.out.println("Departamentos:\nFinanzas, Recursos Humanos, Ventas, Tecnología o Administración");
        System.out.println("Ingrese su departamento");
        setDepartamento(reader.readLine());
        System.out.println("Ingrese su salario bruto");
        setSalarioBruto(Double.parseDouble(reader.readLine()));
        descuentosAdicionales();//Esta arriba


    }
    public void imprimirDatos() {
        String[] encabezados = {
                "Nombre", "Cédula", "Departamento", "Salario Bruto", "Préstamo",
                "Cuota", "Pensión", "Seguro Social", "Seguro Educativo", "Salario Neto"
        };

        String[] datos = {
                nombre,
                cedula,
                departamento,
                String.format("%.2f", salarioBruto),
                String.format("%.2f", prestamo),
                String.format("%.2f", cuota),
                String.format("%.2f", pension),
                String.format("%.2f", calcularSeguroSocial()),
                String.format("%.2f", calcularSeguroEducativo()),
                String.format("%.2f", salarioNeto)
        };

        // Se calcula longitud máxima de cada columna (dependiendo del tamaño del dato ingresado por el usuario)
        int[] anchos = new int[encabezados.length];
        for (int i = 0; i < encabezados.length; i++) {
            anchos[i] = Math.max(encabezados[i].length(), datos[i].length()) + 2; // +2 para padding
        }

        // Se imprimen encabezados con padding dinámico (panding el relleno que le daria a cada encabezado para que se ajuste al tamaño del dato ingresado por el usuario)
        for (int i = 0; i < encabezados.length; i++) {
            System.out.printf("%-" + anchos[i] + "s", encabezados[i]);
        }
        System.out.println();

        // 4. Imprimir valores
        for (int i = 0; i < datos.length; i++) {
            System.out.printf("%-" + anchos[i] + "s", datos[i]);
        }
        System.out.println();
    }
    //Ese seria nuestro programa espero que todo salga bien, lo ultimo no sabia muy bien como se realizaba por eso lo comente tanto
    }
