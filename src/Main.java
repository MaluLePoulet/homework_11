import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final int DOC_NUMBER_LENGTH = 15;
    private static final String DOCNUM_PREFIX = "docnum";
    private static final String KONTRACT_PREFIX = "kontract";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<String> documentNumbers = new HashSet<>();
        List<String> filePaths = new ArrayList<>();
        Map<String, String> validationResults = new HashMap<>();

        System.out.println("Enter document numbers or file paths (type 'exit' to finish):");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            if (input.contains("/") || input.contains("\\")) {
                filePaths.add(input);
            } else {
                documentNumbers.add(input);
            }
        }

        for (String filePath : filePaths) {
            readDocumentNumbersFromFile(filePath, documentNumbers);
        }

        for (String docNumber : documentNumbers) {
            String validationResult = validateDocumentNumber(docNumber);
            validationResults.put(docNumber, validationResult);
        }

        writeReportToFile("report.txt", validationResults);
        scanner.close();
    }

    private static void readDocumentNumbersFromFile(String filePath, Set<String> documentNumbers) {
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                documentNumbers.add(fileScanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static String validateDocumentNumber(String docNumber) {
        if (docNumber.length() != DOC_NUMBER_LENGTH) {
            return "Invalid length";
        }
        if (!(docNumber.startsWith(DOCNUM_PREFIX) || docNumber.startsWith(KONTRACT_PREFIX))) {
            return "Invalid prefix";
        }
        return "Valid";
    }

    private static void writeReportToFile(String fileName, Map<String, String> validationResults) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Map.Entry<String, String> entry : validationResults.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + fileName);
        }
    }
}
