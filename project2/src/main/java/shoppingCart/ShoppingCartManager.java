package shoppingCart;

import java.util.Scanner;

public class ShoppingCartManager {
   public static char printMenu(ShoppingCart theCart, Scanner scnr){
      char menuOp = ' ';
      String productName;
      String productDescription;
      int productPrice = 0;
      int productQuantity = 0;
      
      System.out.println("MENU");
      System.out.println("a - Add item to cart");
      System.out.println("d - Remove item from cart");
      System.out.println("c - Change item quantity");
      System.out.println("i - Output items' descriptions");
      System.out.println("o - Output shopping cart");
      System.out.println( "q - Quit");
      System.out.println("");

      while (menuOp != 'a' && menuOp != 'd' && menuOp != 'c' &&
                menuOp != 'i' && menuOp != 'o' && menuOp != 'q') {

         System.out.println("Choose an option:");
         menuOp = scnr.next().charAt(0);
      }
      
      
      switch (menuOp) {
         case 'a':
            scnr.nextLine(); 
            System.out.println("ADD ITEM TO CART");
            System.out.println("Enter the item name:");
            productName = scnr.nextLine();
         
            System.out.println("Enter the item description:");
            productDescription = scnr.nextLine();
      
            System.out.println("Enter the item price:");
            productPrice= scnr.nextInt();
      
            System.out.println("Enter the item quantity:");
            productQuantity = scnr.nextInt();
            
            ItemToPurchase newItem = new ItemToPurchase(productName, productDescription, productPrice, productQuantity);
            theCart.addItem(newItem);
            
            menuOp = ' ';
            System.out.println("");
            break;
         
         case 'd':
            scnr.nextLine(); 
         
            System.out.println("REMOVE ITEM FROM CART");
            System.out.println("Enter name of item to remove:");
            productName = scnr.nextLine();
            
            theCart.removeItem(productName);
            menuOp = ' ';
            System.out.println("");
            break;
      
      case 'c':
            scnr.nextLine();    
            
            System.out.println("CHANGE ITEM QUANTITY");
            System.out.println("Enter the item name:");
            productName = scnr.nextLine();
         
            System.out.println("Enter the new quantity:");
            productQuantity = scnr.nextInt();
            
            ItemToPurchase modItem = new ItemToPurchase();
            modItem.setName(productName);
            modItem.setQuantity(productQuantity);
            
            theCart.modifyItem(modItem);
            
            menuOp = ' ';
            System.out.println("");
            break;
      
      case 'i':
            System.out.println("OUTPUT ITEMS' DESCRIPTIONS");
            theCart.printDescriptions();
            
            menuOp = ' ';
            System.out.println("");
            break;
      
      case 'o':
            System.out.println("OUTPUT SHOPPING CART"); 
            theCart.printTotal();
            
            menuOp = ' ';
            System.out.println("");
            break;
      }
      
      return menuOp;
   }
   
   
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      String customerName;
      String todaysDate;
      char menuChoice = ' ';
      
      System.out.println("Enter Customer's Name:");
      customerName = scnr.nextLine();
      
      System.out.println("Enter Today's Date:");
      todaysDate = scnr.nextLine();
   
      System.out.println("\nCustomer Name: " + customerName);
      System.out.println("Today's Date: " + todaysDate);
      System.out.println("");
      
      ShoppingCart myCart = new ShoppingCart(customerName, todaysDate);
      
      while(menuChoice != 'q') {
         menuChoice = printMenu(myCart, scnr);
      }

      return;
   }   
}
