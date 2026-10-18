package ir.myket.billingclient.b4a;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import ir.myket.billingclient.util.SkuDetails;

/**
 * Represents an in-app product's catalog details.
 */
@BA.ShortName("MyketSkuDetails")
public class MyketSkuDetails extends AbsObjectWrapper<SkuDetails> {

    /**
     * Initializes the wrapper with a SkuDetails instance.
     */
    public void Initialize(SkuDetails skuDetails) {
        setObject(skuDetails);
    }

    /**
     * Returns the product SKU.
     */
    public String getSku() {
        return getObject() != null ? getObject().getSku() : "";
    }

    /**
     * Returns the product type ("inapp" or "subs").
     */
    public String getType() {
        return getObject() != null ? getObject().getType() : "";
    }

    /**
     * Returns formatted price of the item, including currency sign.
     */
    public String getPrice() {
        return getObject() != null ? getObject().getPrice() : "";
    }

    /**
     * Returns the title of the product.
     */
    public String getTitle() {
        return getObject() != null ? getObject().getTitle() : "";
    }

    /**
     * Returns the description of the product.
     */
    public String getDescription() {
        return getObject() != null ? getObject().getDescription() : "";
    }

    @Override
    public String toString() {
        return getObject() != null ? getObject().toString() : "MyketSkuDetails: null";
    }
}
