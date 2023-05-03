public class Main {
	
    private static int numeroAlgoritmo;
    
    private static int numeroThreads;

    private static String contrasenaInicial;
   
    private static String salt;
    
   
    public static void menu(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("Inicio programa");
        System.out.println("Escriba la contraseña a descifrar:");
        while(contrasenaInicial == null){
            contrasenaInicial = teclado.nextLine();
            if (contrasenaInicial.length()> 7 || contrasenaInicial.length()< 1 || !contrasenaInicial.matches("^[a-z]+$")){
                contrasenaInicial = null;
                System.out.println("La contraseña no es válida ");
            }
        }
        System.out.println("Elija el algoritmo a usar:");
        System.out.println("1. SHA-256");
        System.out.println("2. SHA-512");
        numeroAlgoritmo = 0;
        while(numeroAlgoritmo == 0){
            try{
                numeroAlgoritmo = Integer.parseInt(teclado.nextLine());
                if (numeroAlgoritmo != 1 && numeroAlgoritmo != 2 ){
                    numeroAlgoritmo = 0;
                    System.out.println("Numero invalido");
                }
            }
            catch (Exception e){
                System.out.println("Debe ingresar un numero");
            }
           
        }
        System.out.println("PDigite un cadena de 2 caracteres:");
        while(salt == null){
            salt = teclado.next();
            if (salt.length()> 2 || salt.length()< 1 || !salt.matches("^[a-z]+$")){
                salt = null;
                System.out.println("La sal ingresada no es válida");
            }
        }
        System.out.println("Eliga el numero de threads que desea poner en ejecución. 1 o 2");
        numeroThreads = 0;
        while(numeroThreads == 0){
            try{
                numeroThreads = Integer.parseInt(teclado.next());
                if (numeroThreads != 1 && numeroThreads != 2 ){
                    numeroThreads = 0;
                    System.out.println("Numero invalido");
                }
            }
            catch (Exception e){
                System.out.println("Debe ingresar un número ");
                e.printStackTrace();
            }
           
        }
        teclado.close();
    }
    
    
    public static void main(String[] args) throws Exception {
        menu();
        String concat = contrasenaInicial+salt;
        int tamConcat = concat.length();
        Descifrador hashing = new Descifrador();
        Random fuerzaBruta = new Random();
        String hash =" ";
        if(numeroAlgoritmo == 1){
            hash = hashing.SHA256(concat);
            System.out.println(hash);
            
        }
        else{
            hash = hashing.SHA512(concat);
            System.out.println(hash);
        }

        if(numeroThreads == 1){
            Descifrador thread = new Descifrador(hash, tamConcat,numeroAlgoritmo,0,fuerzaBruta);
            thread.start();
        }
        else{
            Descifrador thread1 = new Descifrador(hash, tamConcat,numeroAlgoritmo,0,fuerzaBruta);
            Descifrador thread2 = new Descifrador(hash, tamConcat,numeroAlgoritmo,1, fuerzaBruta);
            thread1.start();
            thread2.start();
        }

    }
}
}