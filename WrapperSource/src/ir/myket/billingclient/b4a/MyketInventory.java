package ir.myket.billingclient.b4a;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import ir.myket.billingclient.util.Inventory;
import ir.myket.billingclient.util.Purchase;
import ir.myket.billingclient.util.SkuDetails;

/**
 * Represents the inventory of owned items and product details.
 */
@BA.ShortName("MyketInventory")
public class MyketInventory extends AbsObjectWrapper<Inventory> {

    /**
     * Initializes the wrapper with an Inventory instance.
     */
    public void Initialize(Inventory inventory) {
        setObject(inventory);
    }

    /**
     * Returns the listing details for a product, or null if details were not queried.
     */
    public MyketSkuDetails GetSkuDetails(String sku) {
        if (getObject() == null) return null;
        SkuDetails d = getObject().getSkuDetails(sku);
        if (d == null) return null;
        MyketSkuDetails res = new MyketSkuDetails();
        res.setObject(d);
        return res;
    }

    /**
     * Returns purchase information for a given product, or null if the user does not own it.
     */
    public MyketPurchase GetPurchase(String sku) {
        if (getObject() == null) return null;
        Purchase p = getObject().getPurchase(sku);
        if (p == null) return null;
        MyketPurchase res = new MyketPurchase();
        res.setObject(p);
        return res;
    }

    /**
     * Returns true if there exists an owned purchase of the given product.
     */
    public boolean HasPurchase(String sku) {
        return getObject() != null && getObject().hasPurchase(sku);
    }

    /**
     * Returns true if details about the given product are available.
     */
    public boolean HasDetails(String sku) {
        return getObject() != null && getObject().hasDetails(sku);
    }

    /**
     * Erases a purchase locally from the inventory.
     * Note: This only modifies the local Inventory object and does not affect the server.
     */
    public void ErasePurchase(String sku) {
        if (getObject() != null) {
            getObject().erasePurchase(sku);
        }
    }

    /**
     * Returns a list of all owned product SKUs.
     */
    public anywheresoftware.b4a.objects.collections.List GetAllOwnedSkus() {
        anywheresoftware.b4a.objects.collections.List list = new anywheresoftware.b4a.objects.collections.List();
        list.Initialize();
        if (getObject() != null) {
            for (Purchase p : getObject().getAllPurchases()) {
                list.Add(p.getSku());
            }
        }
        return list;
    }

    /**
     * Returns a list of all owned product SKUs of a given item type ("inapp" or "subs").
     */
    public anywheresoftware.b4a.objects.collections.List GetAllOwnedSkus2(String itemType) {
        anywheresoftware.b4a.objects.collections.List list = new anywheresoftware.b4a.objects.collections.List();
        list.Initialize();
        if (getObject() != null) {
            for (String sku : getObject().getAllOwnedSkus(itemType)) {
                list.Add(sku);
            }
        }
        return list;
    }

    /**
     * Returns a list of all owned purchases (MyketPurchase objects).
     */
    public anywheresoftware.b4a.objects.collections.List GetAllPurchases() {
        anywheresoftware.b4a.objects.collections.List list = new anywheresoftware.b4a.objects.collections.List();
        list.Initialize();
        if (getObject() != null) {
            for (Purchase p : getObject().getAllPurchases()) {
                MyketPurchase mp = new MyketPurchase();
                mp.setObject(p);
                list.Add(mp);
            }
        }
        return list;
    }

    /**
     * Returns a list of all available product details (MyketSkuDetails objects).
     */
    public anywheresoftware.b4a.objects.collections.List GetAllProducts() {
        anywheresoftware.b4a.objects.collections.List list = new anywheresoftware.b4a.objects.collections.List();
        list.Initialize();
        if (getObject() != null) {
            for (SkuDetails d : getObject().getAllProducts()) {
                MyketSkuDetails md = new MyketSkuDetails();
                md.setObject(d);
                list.Add(md);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return getObject() != null ? "MyketInventory(owned=" + GetAllOwnedSkus().getSize() + ")" : "MyketInventory: null";
    }
}
