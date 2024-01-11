import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        ProductRepository repository = new ProductRepository();
        String key = "";

        while (!key.equals("X")) {
            System.out.println("S: Ship inventory");
            System.out.println("R: Receive inventory");
            System.out.println("A: Adjust inventory");
            System.out.println("Q: Quantity in stock");
            System.out.println("E: Events");
            System.out.print("--> ");
            key = scan.nextLine().toUpperCase();
            System.out.print("sku: ");
            String sku = scan.nextLine();
            Product product = repository.get(sku);
            switch (key) {
                case "R":
                    int receiveQuantity = getQuantityFromInput();
                    product.receiveProduct(receiveQuantity);
                    System.out.println(sku + " Received: " + receiveQuantity);
                    break;
                case "S":
                    int shipQuantity = getQuantityFromInput();
                    if (product.shipProduct(shipQuantity)) {
                        System.out.println(sku + " Shipped: " + shipQuantity);
                    }
                    break;
                case "A":
                    int adjustQuantity = getQuantityFromInput();
                    String reason = getAdjustReason();
                    if (product.adjustInventory(adjustQuantity, reason)) {
                        System.out.println(sku + " Adjusted: " + adjustQuantity + " Reason: " + reason);
                    }
                    break;
                case "Q":
                    int quantityInStock = product.getQuantityInStock();
                    System.out.println(sku + " Quantity in stock: " + quantityInStock);
                    break;
                case "E":
                    System.out.println(sku + " All events:");
                    product.getEvents().forEach((event) -> {
                        switch (event) {
                            case ProductShipped productShipped:
                                System.out.println(
                                    productShipped.dateTime().format(formatter)
                                    + " " + sku
                                    + " Shipped: "
                                    + productShipped.quantity());
                                break;
                            case ProductReceived productReceived:
                                System.out.println(
                                    productReceived.dateTime().format(formatter)
                                    + " " + sku
                                    + " Received: "
                                    + productReceived.quantity());
                                break;
                            case ProductAdjusted productAdjusted:
                                System.out.println(
                                    productAdjusted.dateTime().format(formatter)
                                    + " " + sku + " Adjusted: "
                                    + productAdjusted.quantity()
                                    + " Reason: " + productAdjusted.reason());
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + event);
                        }

                    });
            }

            repository.save(product);
            System.out.println();
            System.out.println();
        }
    }

    private static String getAdjustReason() {
        System.out.print("Enter adjustment reason: ");
        return scan.nextLine();
    }

    private static int getQuantityFromInput() throws IOException {
        System.out.print("Enter quantity: ");
        return Integer.parseInt(scan.nextLine());
    }
}