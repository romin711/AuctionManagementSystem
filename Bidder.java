// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
class Bidder {
    String name;
    String mobile;
    double balance;
    String bidderId;

    Bidder(String var1, String var2, double var3) {
        this.name = var1;
        this.mobile = var2;
        this.balance = var3;
        String var10001 = var1.substring(0, 2).toUpperCase();
        this.bidderId = var10001 + var2.substring(0, 4);
    }

    void displayBidderInfo(int var1) {
        System.out.println("Data of Member " + var1 + ":");
        System.out.println("Bidder ID: " + this.bidderId);
        System.out.println("Name: " + this.name);
        System.out.println("Mobile: " + this.mobile);
        System.out.println("Balance: " + this.balance);
        System.out.println("------------------------------");
    }
}