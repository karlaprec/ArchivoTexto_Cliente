package archivocliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArchivoCliente {

    static String direccionip, ruta, nombre;
    static int nsocket;
    static Socket socket;

    /* SIRVE PARA VALIDAR UNA DIRECCION IP VERSION 4 */
    public static final String IPv4_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final Pattern IPv4_PATTERN = Pattern.compile(IPv4_REGEX);
    public static String desc = "C:Documents\\";

    public static void main(String[] args) throws UnknownHostException, IOException {
        try {
            direccionip = args[0];
        } catch (Exception e) {
            System.out.println("INGRESE UNA IP CORRECTA");
            System.exit(0);
        }
        try {
            nsocket = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("INGRESE UN SOCKET CORRECTO");
            System.exit(1);
        }
        try {
            ruta = args[2];
        } catch (Exception e) {
            System.out.println("INGRESE RUTA");
            System.exit(0);
        }

        try {
            socket = new Socket(direccionip, nsocket);
        } catch (Exception e) {
            System.out.println("ERROR AL CREAR SOCKET, INTENTE DE NUEVO");
            System.exit(3);
        }
        PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String datosEntrada;
        escritor.println(ruta);
        StringTokenizer tokens = new StringTokenizer(ruta, "\\");
        while (tokens.hasMoreTokens()) {
            nombre = tokens.nextToken();
        }
        File file = new File(desc + nombre);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        while (true) {
            datosEntrada = lector.readLine();
            if (datosEntrada == null) {
                socket.close();
                System.exit(0);
            } else {
                ENVIO(datosEntrada);
                System.out.println(datosEntrada);
            }
        }
    }

    public static void ENVIO(String texto) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File file = new File(desc + nombre);

            if (!file.exists()) {
                /* CREA EL ARCHIVO */
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(texto + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean isValidInet4Address(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = IPv4_PATTERN.matcher(ip);
        return matcher.matches();
    }

}
