package model;

/**
 * @author Esteban
 * Class defining the elements of a wood type
 */
public class WoodItem {

    private String type;
    private Double baseDeliveryTime;
    private Double price;

    /**
     * Default constructor
     */
    public WoodItem(){
        this.type = "Empty";
        this.baseDeliveryTime = 0.0;
        this.price = 0.0;
    }

    /**
     * Constructor of a WoodItem element with parameters
     * @param type Name of the wood type
     * @param daseDeliveryTime base delivery time
     * @param price price per Bare Foot
     */
    public WoodItem(String type, Double daseDeliveryTime, Double price) {
        this.type = type;
        this.baseDeliveryTime = daseDeliveryTime;
        this.price = price;
    }

    /**
     * Get the type of Wood
     *
     * @return Name of the WoodItem
     */
    public String getType() {
        return type;
    }

    /**
     * Get the base delivery time
     *
     * @return the baseDeliverytime
     */
    public Double getBaseDeliveryTime() {
        return baseDeliveryTime;
    }

    /**
     *
     * Get the price per Bare Foot
     *
     * @return the price for 1 Bare Foot of this WoodItem
     */
    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "";
    }

}
