import hash.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        HashTable hashTable1 = new HashTable(1000);
        HashTable hashTable2 = new HashTable(1000);

        String line;
        String splitBy = ",";
        int id = 1;

        long totalInsertionTime1 = 0;
        long totalInsertionTime2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("bussines.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] business = line.split(splitBy);
                if (business.length >= 5) {
                    String key = business[1];
                    String value = "ID=" + business[0] + ", Address=" + business[2] +
                            ", City=" + business[3] + ", State=" + business[4];

                    long time1 = hashTable1.measurePutTime(key, value, 1);
                    long time2 = hashTable2.measurePutTime(key, value, 2);

                    totalInsertionTime1 += time1;
                    totalInsertionTime2 += time2;

                    System.out.println("[" + id + "] Business [ID=" + business[0] + ", Name=" +
                            business[1] + ", Address=" + business[2] + ", City=" +
                            business[3] + ", State=" + business[4] + "]");
                    System.out.println("Tiempo para HashFunction1: " + time1 + " ns");
                    System.out.println("Tiempo para HashFunction2: " + time2 + " ns");
                    id++;
                } else {
                    System.err.println("Saltando línea inválida: " + line);
                }
            }

            System.out.println("\nTiempo total de inserción usando HashFunction1: " + totalInsertionTime1 + " ns");
            System.out.println("Tiempo total de inserción usando HashFunction2: " + totalInsertionTime2 + " ns");
            if (totalInsertionTime1 < totalInsertionTime2) {
                System.out.println("HashFunction1 fue más eficiente en la inserción.");
            } else {
                System.out.println("HashFunction2 fue más eficiente en la inserción.");
            }

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println("\nMenu:");
                System.out.println("1. Buscar por nombre (clave)");
                System.out.println("2. Mostrar datos por índice");
                System.out.println("3. Salir");
                System.out.print("Selecciona una opción: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Introduce el nombre a buscar: ");
                        String searchKey = scanner.nextLine().trim();

                        try {
                            long startTime1 = System.nanoTime();
                            String foundValue1 = hashTable1.searchValueByKey(searchKey, 1);
                            long endTime1 = System.nanoTime();
                            long searchTime1 = endTime1 - startTime1;

                            long startTime2 = System.nanoTime();
                            String foundValue2 = hashTable2.searchValueByKey(searchKey, 2);
                            long endTime2 = System.nanoTime();
                            long searchTime2 = endTime2 - startTime2;

                            System.out.println("Tiempo de búsqueda usando HashFunction1: " + searchTime1 + " ns");
                            System.out.println("Tiempo de búsqueda usando HashFunction2: " + searchTime2 + " ns");

                            if (foundValue1 != null || foundValue2 != null) {
                                if (foundValue1 != null) {
                                    System.out.println("Clave '" + searchKey + "' encontrada en Tabla Hash 1. " + foundValue1);
                                }
                                if (foundValue2 != null) {
                                    System.out.println("Clave '" + searchKey + "' encontrada en Tabla Hash 2. " + foundValue2);
                                }
                            } else {
                                System.out.println("Clave '" + searchKey + "' no encontrada.");
                            }

                            if (searchTime1 < searchTime2) {
                                System.out.println("HashFunction1 fue más eficiente en la búsqueda.");
                            } else {
                                System.out.println("HashFunction2 fue más eficiente en la búsqueda.");
                            }
                        } catch (Exception e) {
                            System.err.println("Error al buscar el nombre: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.print("Introduce el índice para mostrar datos: ");
                        int searchIndex = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            if (searchIndex < 0 || searchIndex >= hashTable1.getCapacity()) {
                                System.out.println("Índice fuera de rango. El rango válido es 0 a " + (hashTable1.getCapacity() - 1));
                            } else {
                                System.out.println("Datos en el índice " + searchIndex + " de la Tabla Hash 1:");
                                List<String> data1 = hashTable1.getDataAtIndex(searchIndex);
                                for (String data : data1) {
                                    System.out.println(data);
                                }

                                System.out.println("Datos en el índice " + searchIndex + " de la Tabla Hash 2:");
                                List<String> data2 = hashTable2.getDataAtIndex(searchIndex);
                                for (String data : data2) {
                                    System.out.println(data);
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("Error al obtener los datos del índice: " + e.getMessage());
                        }
                        break;

                    case 3:
                        exit = true;
                        break;

                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                        break;
                }
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}