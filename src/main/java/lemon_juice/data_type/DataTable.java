package lemon_juice.data_type;

/**
 * Stores a table of values for resources that need it.
 */
public class DataTable {
    private boolean isAlloy; //Only effects metals
    private int isBurnable;
    private boolean isFireproof;
    private boolean isGlowing;
    private String modID;

    /**
     * Produces a <code>DataTable</code> that is marked false.
     */
    public DataTable(){
        this.isAlloy = false;
        this.isBurnable = 0;
        this.isFireproof = false;
        this.isGlowing = false;
        this.modID = "";
    }

    /**
     * Produces a <code>DataTable</code> that is marked false, other than its modID.
     *
     * @param modID The ID of the mod used in the tooltips, "" if none
     */
    public DataTable(String modID){
        this.isAlloy = false;
        this.isBurnable = 0;
        this.isFireproof = false;
        this.isGlowing = false;
        this.modID = modID;
    }

    /**
     * Produces a <code>DataTable</code> that is marked specified by parameter.
     *
     * @param isAlloy true if an <code>Item</code> is an alloy, false otherwise
     * @param isBurnable 0 if an <code>Item</code> is not burnable, otherwise it is
     * @param isFireproof true if an <code>Item</code> is Fireproof, false otherwise
     * @param isGlowing true if an <code>Item</code> is Glowing, false otherwise
     * @param modID The ID of the mod used in the tooltips, "" if none
     */
    public DataTable(boolean isAlloy, int isBurnable, boolean isFireproof, boolean isGlowing, String modID){
        this.isAlloy = isAlloy;
        this.isBurnable = isBurnable;
        this.isFireproof = isFireproof;
        this.isGlowing = isGlowing;
        this.modID = modID;
    }

    //Getters
    /**
     * @return true if this <code>Item</code> is an alloy, false otherwise
     */
    public boolean getIsAlloy() {
        return isAlloy;
    }

    /**
     * @return 0 if an <code>Item</code> is not burnable, otherwise the number of ticks it burns for
     */
    public int getIsBurnable() {
        return isBurnable;
    }

    /**
     * @return true if this <code>Item</code> is Fireproof, false otherwise
     */
    public boolean getIsFireproof() {
        return isFireproof;
    }

    /**
     * @return true if this <code>Item</code> is Glowing, false otherwise
     */
    public boolean getIsGlowing() {
        return isGlowing;
    }

    /**
     * @return the mod ID used int the tooltips
     */
    public String getModID() {
        return modID;
    }

    //Setters
    /**
     * @param isAlloy true if this <code>Item</code> is an alloy, false otherwise
     */
    public void setIsAlloy(boolean isAlloy) {
        this.isAlloy = isAlloy;
    }

    /**
     * @param isBurnable 0 if an <code>Item</code> is not burnable, otherwise the number of ticks it burns for
     */
    public void setIsBurnable(int isBurnable) {
        this.isBurnable = isBurnable;
    }

    /**
     * @param isFireproof true if this <code>Item</code> is Fireproof, false otherwise
     */
    public void setIsFireproof(boolean isFireproof) {
        this.isFireproof = isFireproof;
    }

    /**
     * @param isGlowing true if this <code>Item</code> is Glowing, false otherwise
     */
    public void setIsGlowing(boolean isGlowing) {
        this.isGlowing = isGlowing;
    }

    /**
     * @param modID the mod ID used int the tooltips
     */
    public void setModID(String modID) {
        this.modID = modID;
    }
}
