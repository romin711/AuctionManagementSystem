// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
class Item {
    String name;
    String description;
    double highestBid;
    String highestBidder = "None";
    boolean isSold = false;

    Item(String var1, String var2, double var3) {
        this.name = var1;
        this.description = var2;
        this.highestBid = var3;
    }

    Item(String var1) {
        switch (var1.toLowerCase()) {
            case "electronics":
                this.name = "Laptop";
                this.description = "16GB RAM Gaming Laptop";
                this.highestBid = 25000.0;
                break;
            case "art":
                this.name = "Painting";
                this.description = "Abstract Art by John Doe";
                this.highestBid = 15000.0;
                break;
            case "furniture":
                this.name = "Table";
                this.description = "Wooden Dining Table";
                this.highestBid = 8000.0;
                break;
            default:
                System.out.println("Invalid item type.");
        }

    }

    void displayDetails(int var1) {
        System.out.println("" + var1 + ". Item: " + this.name);
        System.out.println("  Description: " + this.description);
        System.out.println("  Current Highest Bid: " + this.highestBid + " by " + this.highestBidder);
        System.out.println("------------------------------");
    }
}