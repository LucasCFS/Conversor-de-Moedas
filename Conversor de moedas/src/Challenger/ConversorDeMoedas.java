package Challenger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class ConversorDeMoedas {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    /**
     * @param args
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {    
            char choice = 'S'; // Inicializa choice com 'S' para garantir que o loop seja executado pelo menos uma vez

            do {
                System.out.println("*********************************");
                System.out.println("Bem-vindo ao Conversor de Moedas!");
                System.out.println("*********************************");

                System.out.println("\nEscolha uma opção de conversão:");
                System.out.println("1. USD para EUR");
                System.out.println("2. EUR para USD");
                System.out.println("3. USD para JPY");
                System.out.println("4. JPY para USD");
                System.out.println("5. EUR para JPY");
                System.out.println("6. JPY para EUR");
                System.out.println("\n*********************************");

                System.out.print("Digite o número da opção desejada: ");
                int option = scanner.nextInt();

                String baseCurrency = "";
                String targetCurrency = "";
                switch (option) {
                    case 1:
                        baseCurrency = "USD";
                        targetCurrency = "EUR";
                        break;
                    case 2:
                        baseCurrency = "EUR";
                        targetCurrency = "USD";
                        break;
                    case 3:
                        baseCurrency = "USD";
                        targetCurrency = "JPY";
                        break;
                    case 4:
                        baseCurrency = "JPY";
                        targetCurrency = "USD";
                        break;
                    case 5:
                        baseCurrency = "EUR";
                        targetCurrency = "JPY";
                        break;
                    case 6:
                        baseCurrency = "JPY";
                        targetCurrency = "EUR";
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        continue; // Volta ao início do loop
                }

                if (!baseCurrency.isEmpty() && !targetCurrency.isEmpty()) {
                    System.out.print("Digite o valor a ser convertido: ");
                    double amount = scanner.nextDouble();

                    double exchangeRate = obterTaxaDeCambio(baseCurrency, targetCurrency);
                    if (exchangeRate != -1) {
                        double convertedAmount = amount * exchangeRate;
                        System.out.println(amount + " " + baseCurrency + " equivale a " + convertedAmount + " " + targetCurrency);
                    }
                }

                System.out.print("Deseja fazer outra conversão? (S/N): ");
                choice = scanner.next().toUpperCase().charAt(0);
                
            
            } while (choice == 'S');
            

            System.out.println("Obrigado por usar o Conversor de Moedas!");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }

    private static double obterTaxaDeCambio(String baseCurrency, String targetCurrency) {
        String completeURL = API_URL + baseCurrency;

        try {
            URL url = new URL(completeURL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Procurar pela taxa de câmbio da moeda alvo na resposta da API
                if (line.contains("\"" + targetCurrency + "\"")) {
                    int startIndex = line.indexOf(targetCurrency) + targetCurrency.length() + 3;
                    int endIndex = line.indexOf(",", startIndex);
                    String rateStr = line.substring(startIndex, endIndex);
                    return Double.parseDouble(rateStr);
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao obter a taxa de câmbio: " + e.getMessage());
        }

        return -1; // Retorna valor inválido em caso de erro
    }
}








