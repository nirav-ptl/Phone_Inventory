
/**
 Starter program
 The CellPhone class holds data about a cell phone.
*/

public class CellPhone {
   // Fields
   private String model;         // Model
   private String manufacturer;  // Manufacturer
   private double retailPrice;   // Retail price

   // Constructor with parameters
   public CellPhone(String m, String man, double price) throws InvalidModelException, InvalidManufacturerException,
           InvalidRetailPriceException {
      setModel(m);
      setManufacturer(man);
      setRetailPrice(price);
   }

   // Default constructor
   public CellPhone() throws InvalidModelException, InvalidRetailPriceException, InvalidManufacturerException {
      this("", "", 0.0);
   }

   // Setter for model with validation
   public void setModel(String m) throws InvalidModelException {
      if (m == null || m.trim().isEmpty()) {
         throw new InvalidModelException();
      }
      this.model = m;
   }

   // Setter for manufacturer with validation
   public void setManufacturer(String man) throws InvalidManufacturerException {
      if (man == null || man.trim().isEmpty()) {
         throw new InvalidManufacturerException();
      }
      this.manufacturer = man;
   }

   // Setter for retail price with validation
   public void setRetailPrice(double price) throws InvalidRetailPriceException {
      if (price <= 0 || price >= 1500) {
         throw new InvalidRetailPriceException();
      }
      this.retailPrice = price;
   }

   // Getters
   public String getModel() {
      return model;
   }

   public String getManufacturer() {
      return manufacturer;
   }

   public double getRetailPrice() {
      return retailPrice;
   }

   // toString method for formatted output
   @Override
   public String toString() {
      return String.format("Model: %-20s Manufacturer: %-20s Retail Price: $%.2f%n",
              getModel(), getManufacturer(), getRetailPrice());
   }
}
