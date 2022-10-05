package controller;

/**
 * @author Esteban
 * @author Levi Horst
 * Reads a wood order file and outputs a pdf file with all the order
 * information plus a pin and estimated delivery time and a total order price
 */
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.Random;
import model.WoodItem;

public class HardwoodSeller {
    // Create WoodItems
    static WoodItem cherry = new WoodItem("Cherry", 2.5, 5.95);
    static WoodItem curlyMaple = new WoodItem("Curly Maple", 1.5, 6.00);
    static WoodItem genuineMahogany = new WoodItem("Genuine Mahogany", 3.0, 9.60);
    static WoodItem wenge = new WoodItem("Wenge", 5.0, 22.35);
    static WoodItem whiteOak = new WoodItem("White Oak", 2.3, 6.70);
    static WoodItem sawdust = new WoodItem("Sawdust", 1.0, 1.50);
    // Globals
    static Double deliveryETA;
    static String[] orders;
    static Random random = new Random();
    static int pin;
    static String name;
    static String address;
    static String date;
    static double totalPrice;
    static boolean makeOutput;
    public static void main(String[] args) {
        System.out.println("Type 'q' to quit.\nType a file name: ");
        Scanner input = new Scanner(System.in);
        boolean output = false;
        while(true) {
            String file = input.nextLine();
            makeOutput = true;
            if(file.equalsIgnoreCase("q")){
                return;
            }else{
                try{
                String filename = file; // name of the file that contains the user's order and info
                pin = random.nextInt(9999999);
                if(pin < 1000000){
                    pin += 1000000;
                }
                String outputFileName = "woodInvoice_" + pin + ".txt";
                    readInputFile(filename);
                    if(makeOutput){
                        makeOutputFile(outputFileName);
                        System.out.println("Invoice Processed.\nEnter another file or enter 'q' to quit.");
                    }
                } catch (Exception e){
                    System.out.println("An Error has occurred. Please try another file name.");
                }
            }
        }
    }

    public static void makeOutputFile(String filename){
        try{
            File outputFile = new File(filename);
            FileWriter writer = new FileWriter(filename);
            writer.write("Invoice ID: " + pin + "\nName:       " + name + "\nAddress:    " + address + "\n");
            for(int i = 0; i < orders.length; i++){
                String[] order = orders[i].split(":");
                String wood = order[0];
                String quantity = order[1];
                double price = 0;
                if(wood.equalsIgnoreCase(cherry.getType())){ // Sets the baseTime for the computation
                    price = cherry.getPrice();
                } else if (wood.equalsIgnoreCase(curlyMaple.getType())) {
                    price = curlyMaple.getPrice();
                }else if (wood.equalsIgnoreCase(genuineMahogany.getType())) {
                    price = genuineMahogany.getPrice();
                }else if (wood.equalsIgnoreCase(wenge.getType())) {
                    price = wenge.getPrice();
                }else if (wood.equalsIgnoreCase(whiteOak.getType())) {
                    price = whiteOak.getPrice();
                }else if (wood.equalsIgnoreCase(sawdust.getType())) {
                    price = sawdust.getPrice();
                }
                writer.write(wood + ": " + " $" + price + " /BF\nQuantity:   " + quantity + "BF\n");
            }
            writer.write("--------------------------------------------------\n");
            if(deliveryETA <= 0){
                writer.write("VOID SALE: INVALID WOOD OR QUANTITY\n--------------------------------------------------");
            }else {
                writer.write("TOTAL SALE PRICE:        $" + totalPrice + "\nESTIMATED DELIVERY TIME: " + deliveryETA + " hours\n--------------------------------------------------");
            }
            writer.close();
        }catch (IOException e){
            System.out.println("An error has occurred.");
        }
    }

    /**
     * Method for reading the input file to be processed by the Hardwood Seller
     **/
    public static void readInputFile(String inputFilePath) {
        try {
            File file = new File(inputFilePath);
            Scanner scanner = new Scanner(file);
            String infoline = scanner.nextLine(); // takes the first line of the file and save the info
            String[] info = infoline.split(";");
            name = info[0];
            address = info[1];
            date = info[2];
            // System.out.println("Name: " + name + "\nAddress: " + address + "\nDate: " + date); // For testing purposes
            orders = scanner.nextLine().split(";"); // split up the orders in an array
            deliveryETA = deliveryTime();
            // System.out.println("Delivery Time: " + deliveryTime()); // For testing purposes
        } catch (FileNotFoundException e) {
            makeOutput = false;
            System.out.println("Error: File could not be found.");
        }
    }

    /**
     * Method that computes the delivery ETA
     * returns -1 if invalid wood type is entered
     * returns -1 if invalid quantity is entered (< 1 or > 1000)
     **/
    public static Double deliveryTime() {
        deliveryETA = 0.0;
        totalPrice = 0;
        for (int i = 0; i < orders.length; i++) { // Runs the loop for every order
            String[] order = orders[i].split(":");
            String wood = order[0];
            String quantity = order[1];
            int quant = Integer.parseInt(quantity); // make the quantity an int so it can be compared
            double baseTime;
            double price;
            if(wood.equalsIgnoreCase(cherry.getType())){ // Sets the baseTime for the computation
                baseTime = cherry.getBaseDeliveryTime();
                price = cherry.getPrice();
            } else if (wood.equalsIgnoreCase(curlyMaple.getType())) {
                baseTime = curlyMaple.getBaseDeliveryTime();
                price = curlyMaple.getPrice();
            }else if (wood.equalsIgnoreCase(genuineMahogany.getType())) {
                baseTime = genuineMahogany.getBaseDeliveryTime();
                price = genuineMahogany.getPrice();
            }else if (wood.equalsIgnoreCase(wenge.getType())) {
                baseTime = wenge.getBaseDeliveryTime();
                price = wenge.getPrice();
            }else if (wood.equalsIgnoreCase(whiteOak.getType())) {
                baseTime = whiteOak.getBaseDeliveryTime();
                price = whiteOak.getPrice();
            }else if (wood.equalsIgnoreCase(sawdust.getType())) {
                baseTime = sawdust.getBaseDeliveryTime();
                price = sawdust.getPrice();
            }else{ // invalid wood type
                return -1.0;
            }
            double multiplier;
            if(quant >= 1 && quant <= 100){ // sets the multiplier based on the quantity for the computation
                multiplier = 1;
            } else if (quant >= 101 && quant <= 200) {
                multiplier = 2;
            }else if (quant >= 201 && quant <= 300) {
                multiplier = 3;
            }else if (quant >= 301 && quant <= 400) {
                multiplier = 4;
            }else if (quant >= 401 && quant <= 500) {
                multiplier = 5;
            }else if (quant >= 501 && quant <= 1000) {
                multiplier = 5.5;
            }else{ // invalid quantity
                return -1.0;
            }
            totalPrice = totalPrice + quant * price;
            deliveryETA += (multiplier * baseTime); // add to total time for the ETA
        }
        return deliveryETA;
    }
}
