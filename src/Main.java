import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
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

            Product product = productRepository.get(sku);
            switch (key) {
                case "R" -> {
                    int receiveQuantity = getQuantityFromInput();
                    product.receiveProduct(receiveQuantity);
                    System.out.println(sku + " Received: " + receiveQuantity);
                }
                case "S" -> {
                    int shipQuantity = getQuantityFromInput();
                    if (product.shipProduct(shipQuantity)) {
                        System.out.println(sku + " Shipped: " + shipQuantity);
                    }
                }
                case "A" -> {
                    int adjustQuantity = getQuantityFromInput();
                    String reason = getAdjustReason();
                    if (product.adjustInventory(adjustQuantity, reason)) {
                        System.out.println(sku + " Adjusted: " + adjustQuantity + " Reason: " + reason);
                    }
                }
                case "Q" -> {
                    int quantityInStock = product.getQuantityInStock();
                    System.out.println(sku + " Quantity in stock: " + quantityInStock);
                }
                case "E" -> {
                    System.out.println(sku + " All events:");
                    List<EventInterface> eventList = productRepository.eventHistory(sku);
                    eventList.forEach((event) -> {
                        switch (event) {
                            case ProductShipped productShipped -> System.out.println(
                                    productShipped.dateTime().format(formatter)
                                            + " " + sku
                                            + " Shipped: "
                                            + productShipped.quantity());
                            case ProductReceived productReceived -> System.out.println(
                                    productReceived.dateTime().format(formatter)
                                            + " " + sku
                                            + " Received: "
                                            + productReceived.quantity());
                            case ProductAdjusted productAdjusted -> System.out.println(
                                    productAdjusted.dateTime().format(formatter)
                                            + " " + sku + " Adjusted: "
                                            + productAdjusted.quantity()
                                            + " Reason: " + productAdjusted.reason());
                            default -> throw new IllegalStateException("Unexpected value: " + event);
                        }

                    });
                }
            }
            switch (key) {
                case "R", "S", "A" -> productRepository.save(product);
            }
            System.out.println();
            System.out.println("-------------------------");
        }
    }

    private static String getAdjustReason() {
        System.out.print("Enter adjustment reason: ");
        return scan.nextLine();
    }

    private static int getQuantityFromInput() {
        System.out.print("Enter quantity: ");
        String input = scan.nextLine();
        while (!isInteger(input)) {
            System.out.print("Not a number! Enter quantity: ");
            input = scan.nextLine();
        }
        return Integer.parseInt(input);
    }

    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}