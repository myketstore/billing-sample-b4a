package ir.myket.billingclient.b4a;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import ir.myket.billingclient.util.Purchase;

/**
 * Represents an in-app product purchase.
 */
@BA.ShortName("MyketPurchase")
public class MyketPurchase extends AbsObjectWrapper<Purchase> {

    /**
     * Initializes the wrapper with a Purchase instance.
     */
    public void Initialize(Purchase purchase) {
        setObject(purchase);
    }

    /**
     * Returns the item type ("inapp" or "subs").
     */
    public String getItemType() {
        return getObject() != null ? getObject().getItemType() : "";
    }

    /**
     * Returns the unique order ID for the transaction.
     */
    public String getOrderId() {
        return getObject() != null ? getObject().getOrderId() : "";
    }

    /**
     * Returns the package name from which the purchase was made.
     */
    public String getPackageName() {
        return getObject() != null ? getObject().getPackageName() : "";
    }

    /**
     * Returns the product SKU.
     */
    public String getSku() {
        return getObject() != null ? getObject().getSku() : "";
    }

    /**
     * Returns the time the product was purchased in milliseconds since the epoch (Jan 1, 1970).
     */
    public long getPurchaseTime() {
        return getObject() != null ? getObject().getPurchaseTime() : 0L;
    }

    /**
     * Returns the purchase state (0 = purchased, 1 = canceled, 2 = refunded).
     */
    public int getPurchaseState() {
        return getObject() != null ? getObject().getPurchaseState() : 0;
    }

    /**
     * Returns additional developer-specified information for the purchase.
     */
    public String getDeveloperPayload() {
        return getObject() != null ? getObject().getDeveloperPayload() : "";
    }

    /**
     * Returns a token that uniquely identifies a purchase for a given item and user pair.
     */
    public String getToken() {
        return getObject() != null ? getObject().getToken() : "";
    }

    /**
     * Returns the original JSON purchase data received from the billing service.
     */
    public String getOriginalJson() {
        return getObject() != null ? getObject().getOriginalJson() : "";
    }

    /**
     * Returns the digital signature of the purchase data.
     */
    public String getSignature() {
        return getObject() != null ? getObject().getSignature() : "";
    }

    @Override
    public String toString() {
        return getObject() != null ? getObject().toString() : "MyketPurchase: null";
    }
}
