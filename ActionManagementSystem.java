import java.util.Scanner;

class AuctionManagementSystem {

    static Item[] items = new Item[10];
    static Bidder[] bidders = new Bidder[10];
    static Item selectedItem = null;

    static double maxBidLimit = 100000.0;

    static int bidderCount = 0;
    static int itemCount = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        items[0] = new Item("Electronics");
        items[1] = new Item("Art");
        items[2] = new Item("Furniture");

        itemCount = 3;

        while (true) {

            System.out.println("\nSelect an action:");
            System.out.println("1. Show Available Items");
            System.out.println("2. Add Item");
            System.out.println("3. Delete Item");
            System.out.println("5. Add Bidder");
            System.out.println("6. Delete Bidder");
            System.out.println("7. Start Auction");
            System.out.println("8. End Auction");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    showAvailableItems();
                    break;

                case 2:
                    addItem(sc);
                    break;

                case 3:
                    deleteItem(sc);
                    break;

                case 5:
                    addBidder(sc);
                    break;

                case 6:
                    deleteBidder(sc);
                    break;

                case 7:
                    startAuction(sc);
                    break;

                case 8:
                    System.out.println("\nAuction Ended.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void showAvailableItems() {

        System.out.println("\nAvailable Auction Items:");

        if (itemCount == 0) {
            System.out.println("No items available.");
            return;
        }

        for (int i = 0; i < itemCount; i++) {

            if (!items[i].isSold) {
                items[i].displayDetails(i + 1);
            }
        }
    }

    static void addItem(Scanner sc) {

        if (itemCount >= items.length) {
            System.out.println("Item storage full.");
            return;
        }

        System.out.println("Enter the item name:");
        String name = sc.nextLine();

        System.out.println("Enter item description:");
        String desc = sc.nextLine();

        System.out.println("Enter the starting bid price:");
        double price = sc.nextDouble();
        sc.nextLine();

        if (price < 0) {
            System.out.println("Enter valid price.");
            return;
        }

        items[itemCount] = new Item(name, desc, price);
        itemCount++;

        System.out.println("New item added successfully!");
    }

    static void deleteItem(Scanner sc) {

        System.out.println("Select an item to delete by entering the item number:");

        showAvailableItems();

        int index = sc.nextInt() - 1;

        if (index >= 0 && index < itemCount && !items[index].isSold) {

            items[index] = items[itemCount - 1];
            items[itemCount - 1] = null;

            itemCount--;

            System.out.println("Item deleted successfully.");
            showAvailableItems();

        } else {
            System.out.println("Invalid item number or item already sold.");
        }
    }

    static void addBidder(Scanner sc) {

        if (bidderCount >= bidders.length) {
            System.out.println("Bidder storage full.");
            return;
        }

        boolean again = true;

        while (again) {

            System.out.print("Enter Full Name of Bidder: ");
            String name = sc.nextLine();

            System.out.print("Enter Mobile Number (10 digits starting with 6-9): ");
            String mobile = sc.nextLine();

            if (mobile.length() != 10 || mobile.charAt(0) < '6' || mobile.charAt(0) > '9') {

                System.out.println("Invalid mobile number.");
                continue;
            }

            System.out.print("Enter Your Balance: ");
            String balInput = sc.nextLine();

            double balance = getValidBalance(balInput);

            if (balance <= 0) {

                System.out.println("Invalid balance.");
                continue;
            }

            bidders[bidderCount] = new Bidder(name, mobile, balance);
            bidderCount++;

            System.out.println("\nBidder Registered Successfully!");

            System.out.print("Add another bidder? (yes/no): ");
            again = sc.nextLine().equalsIgnoreCase("yes");
        }
    }

    static double getValidBalance(String input) {

        if (input == null || input.isEmpty()) return 0;

        for (char c : input.toCharArray()) {

            if (!Character.isDigit(c) && c != '.') {
                return 0;
            }
        }

        return Double.parseDouble(input);
    }

    static void deleteBidder(Scanner sc) {

        System.out.println("Enter the bidder ID to delete:");
        String id = sc.nextLine();

        for (int i = 0; i < bidderCount; i++) {

            if (bidders[i].bidderId.equals(id)) {

                bidders[i] = bidders[bidderCount - 1];
                bidders[bidderCount - 1] = null;

                bidderCount--;

                System.out.println("Bidder deleted successfully.");
                return;
            }
        }

        System.out.println("Bidder not found.");
    }

    static void startAuction(Scanner sc) {

        if (bidderCount < 2) {
            System.out.println("\nYou need at least two bidders to start the auction.");
            return;
        }

        showAvailableItems();

        System.out.println("\nSelect an item number:");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index < 0 || index >= itemCount) {

            System.out.println("Invalid item.");
            return;
        }

        selectedItem = items[index];

        selectedItem.displayDetails(index + 1);

        System.out.println("\nAuction Started for: " + selectedItem.name);
        System.out.println("Starting Bid: " + selectedItem.highestBid);

        startBidding("");
    }

    static double getValidBid(String input) {

        if (input == null || input.isEmpty()) return 0;

        for (char c : input.toCharArray()) {

            if (!Character.isDigit(c) && c != '.') {
                return 0;
            }
        }

        return Double.parseDouble(input);
    }

    static void startBidding(String lastBidderId) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\nCurrent Highest Bid: "
                    + selectedItem.highestBid + " by "
                    + selectedItem.highestBidder);

            System.out.print("Enter bidder ID (or 'end'): ");

            String id = sc.nextLine();

            if (id.equalsIgnoreCase("end")) {

                System.out.println("\nAuction Ended.");
                selectedItem.isSold = true;

                deductAmountFromWinner();
                return;
            }

            Bidder bidder = null;

            for (int i = 0; i < bidderCount; i++) {

                if (bidders[i].bidderId.equals(id)) {

                    bidder = bidders[i];
                    break;
                }
            }

            if (bidder == null) {

                System.out.println("Invalid Bidder ID.");
                continue;
            }

            if (id.equals(lastBidderId)) {

                System.out.println("You cannot bid consecutively.");
                continue;
            }

            System.out.print("Enter your bid: ");
            String bidInput = sc.nextLine();

            double bid = getValidBid(bidInput);

            if (bid <= selectedItem.highestBid) {

                System.out.println("Bid must be higher than current bid.");
                continue;
            }

            if (bid > bidder.balance) {

                System.out.println("Insufficient balance.");
                continue;
            }

            if (bid > maxBidLimit) {

                System.out.println("Bid exceeds maximum limit.");
                continue;
            }

            selectedItem.highestBid = bid;
            selectedItem.highestBidder = bidder.name;

            lastBidderId = bidder.bidderId;

            System.out.println("Bid accepted! New highest bid: " + bid);
        }
    }

    static void deductAmountFromWinner() {

        for (int i = 0; i < bidderCount; i++) {

            if (bidders[i].name.equals(selectedItem.highestBidder)) {

                bidders[i].balance -= selectedItem.highestBid;

                System.out.println("Amount of " + selectedItem.highestBid +
                        " deducted from " + bidders[i].name);
                break;
            }
        }

        System.out.println("\nAuction Results:");
        System.out.println("Item: " + selectedItem.name
                + " sold to " + selectedItem.highestBidder
                + " for " + selectedItem.highestBid);

        System.out.println("** CONGRATULATIONS "
                + selectedItem.highestBidder.toUpperCase() + " **");
    }
}