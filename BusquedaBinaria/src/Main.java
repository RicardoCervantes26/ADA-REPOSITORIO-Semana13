import java.util.*;
import java.io.*;

public class Main {

    // Metodo principal que ordena 
    public static void ordenarRapido(List<Integer> lista) {
        if (lista.size() <= 1) return;

        List<Integer> listaOrdenada = ordenarRecursivo(lista);
        lista.clear();
        lista.addAll(listaOrdenada);
    }

    // Metodo recursivo de QuickSort
    private static List<Integer> ordenarRecursivo(List<Integer> lista) {
        if (lista.size() <= 1) return lista;

        Random generadorAleatorio = new Random();
        int indicePivote = generadorAleatorio.nextInt(lista.size());
        int pivote = lista.get(indicePivote);

        List<Integer> menores = new ArrayList<>();
        List<Integer> iguales = new ArrayList<>();
        List<Integer> mayores = new ArrayList<>();

        for (int numero : lista) {
            if (numero < pivote) menores.add(numero);
            else if (numero == pivote) iguales.add(numero);
            else mayores.add(numero);
        }

        return unirListas(ordenarRecursivo(menores), iguales, ordenarRecursivo(mayores));
    }

    // Metodo para unir las listas ordenadas
    private static List<Integer> unirListas(List<Integer> lista1, List<Integer> lista2, List<Integer> lista3) {
        List<Integer> resultado = new ArrayList<>();
        resultado.addAll(lista1);
        resultado.addAll(lista2);
        resultado.addAll(lista3);
        return resultado;
    }

    // BÚSQUEDA BINARIA
    public static int busquedaBinaria(List<Integer> lista, int objetivo) {
        int izquierda = 0;
        int derecha = lista.size() - 1;

        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;

            if (lista.get(medio) == objetivo) {
                return medio; // Elemento encontrado
            } else if (lista.get(medio) < objetivo) {
                izquierda = medio + 1; // Buscar en mitad derecha
            } else {
                derecha = medio - 1; // Buscar en mitad izquierda
            }
        }
        return -1; // Elemento no encontrado
    }

    // Guardar lista en archivo TXT
    private static void guardarEnArchivo(List<Integer> lista, String nombreArchivo) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (int numero : lista) {
                escritor.println(numero);
            }
            System.out.println("Archivo guardado: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> numeros = new ArrayList<>();

        System.out.println("=== INGRESO DE NÚMEROS DE PEDIDO ===");
        System.out.println("Ingresa números (escribe 'fin' para terminar):");

        // Leer números del usuario
        while (true) {
            System.out.print("Número: ");
            String entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("fin")) break;

            try {
                numeros.add(Integer.parseInt(entrada));
            } catch (NumberFormatException e) {
                System.out.println("¡Número inválido! Intenta nuevamente.");
            }
        }

        // Guardar lista desordenada
        guardarEnArchivo(numeros, "pedidos_desordenados.txt");
        System.out.println("\nLista original: " + numeros);

        // ORDENAR con QuickSort
        ordenarRapido(numeros);
        System.out.println("Lista ordenada: " + numeros);

        // Guardar lista ordenada
        guardarEnArchivo(numeros, "pedidos_ordenados.txt");

        // BÚSQUEDA BINARIA CON "fin" PARA TERMINAR
        System.out.println("\n=== BÚSQUEDA BINARIA ===");
        System.out.println("Ingresa números a buscar (escribe 'fin' para terminar):");

        while (true) {
            System.out.print("Número a buscar: ");
            String entradaBusqueda = scanner.nextLine();

            if (entradaBusqueda.equalsIgnoreCase("fin")) {
                System.out.println("¡Búsqueda finalizada!");
                break;
            }

            try {
                int objetivo = Integer.parseInt(entradaBusqueda);
                int posicion = busquedaBinaria(numeros, objetivo);

                if (posicion != -1) {
                    System.out.println("Encontrado en posición: " + posicion);
                } else {
                    System.out.println("No encontrado");
                }
            } catch (NumberFormatException e) {
                System.out.println("¡Número inválido! Intenta nuevamente.");
            }
            System.out.println(); // Línea en blanco para separar
        }

        scanner.close();
    }
}
