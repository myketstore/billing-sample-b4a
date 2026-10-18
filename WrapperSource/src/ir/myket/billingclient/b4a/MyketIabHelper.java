package ir.myket.billingclient.b4a;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import ir.myket.billingclient.IabHelper;
import ir.myket.billingclient.util.IabResult;
import ir.myket.billingclient.util.Inventory;
import ir.myket.billingclient.util.Purchase;

/**
 * Android In-App Billing SDK wrapper for Myket App Store.
 * Provides access to Myket In-App Billing version 3 via IabHelper.
 */
@BA.ShortName("MyketIabHelper")
@BA.Author("Myket")
@BA.Version(2.02f)
@BA.ActivityObject
@BA.Permissions(values = {
        "android.permission.INTERNET",
        "ir.mservices.market.BILLING"
})
@BA.DependsOn(values = {
        "MyketBilling-2.2.aar",
        "androidx.core:core",
        "androidx.browser:browser",
        "org.jetbrains.kotlin-kotlin-stdlib",
        "org.jetbrains.kotlinx-kotlinx-coroutines-core-jvm",
        "org.jetbrains.kotlinx-kotlinx-coroutines-android",
        "okhttp-4.9.0",
        "okio-2.8.0",
        "gson-2.8.5"
})
@BA.Events(values = {
        "SetupFinished (Result As MyketIabResult)",
        "PurchaseFinished (Result As MyketIabResult, Purchase As MyketPurchase)",
        "QueryInventoryFinished (Result As MyketIabResult, Inventory As MyketInventory)",
        "ConsumeFinished (Purchase As MyketPurchase, Result As MyketIabResult)"
})
public class MyketIabHelper extends AbsObjectWrapper<IabHelper> {

    // Standard billing response codes matching IabHelper
    public static final int BILLING_RESPONSE_RESULT_OK = IabHelper.BILLING_RESPONSE_RESULT_OK;
    public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED;
    public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = IabHelper.BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE;
    public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = IabHelper.BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE;
    public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = IabHelper.BILLING_RESPONSE_RESULT_DEVELOPER_ERROR;
    public static final int BILLING_RESPONSE_RESULT_ERROR = IabHelper.BILLING_RESPONSE_RESULT_ERROR;
    public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED;
    public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = IabHelper.BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED;
    public static final int BILLING_RESPONSE_RESULT_TIMEOUT = IabHelper.BILLING_RESPONSE_RESULT_TIMEOUT;

    // Item types
    public static final String ITEM_TYPE_INAPP = IabHelper.ITEM_TYPE_INAPP;
    public static final String ITEM_TYPE_SUBS = IabHelper.ITEM_TYPE_SUBS;

    private BA ba;
    private String eventName;

    /**
     * Initializes the Myket In-App Billing Helper.
     *
     * @param ba The B4A execution context.
     * @param EventName Prefix for raised events (e.g. "IabHelper").
     * @param Base64PublicKey The base64-encoded RSA public key from Myket Developer Console.
     */
    public void Initialize(final BA ba, final String EventName, final String Base64PublicKey) {
        this.ba = ba;
        this.eventName = EventName;

        Context context = ba.context;
        if (context == null && ba.activity != null) {
            context = ba.activity;
        }
        if (context == null) {
            context = BA.applicationContext;
        }

        IabHelper helper = new IabHelper(context, Base64PublicKey);
        setObject(helper);
    }

    /**
     * Enables or disables debug logging to Logcat.
     */
    public void EnableDebugLogging(boolean enable) {
        checkInitialized();
        getObject().enableDebugLogging(enable);
    }

    /**
     * Enables or disables debug logging to Logcat with a custom tag.
     */
    public void EnableDebugLogging2(boolean enable, String tag) {
        checkInitialized();
        getObject().enableDebugLogging(enable, tag);
    }

    /**
     * Starts the asynchronous billing setup process using the default EventName.
     * Event: EventName_SetupFinished (Result As MyketIabResult)
     */
    public void StartSetup() {
        StartSetup2(this.eventName);
    }

    /**
     * Starts the asynchronous billing setup process using a custom EventName.
     * Event: EventName_SetupFinished (Result As MyketIabResult)
     */
    public void StartSetup2(final String customEventName) {
        checkInitialized();
        getObject().startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                MyketIabResult res = new MyketIabResult();
                res.setObject(result);
                ba.raiseEventFromDifferentThread(MyketIabHelper.this, null, 0, (customEventName + "_setupfinished").toLowerCase(), false, new Object[] {res});
            }
        });
    }

    /**
     * Returns true if subscriptions are supported on the device.
     */
    public boolean getSubscriptionsSupported() {
        return getObject() != null && getObject().subscriptionsSupported();
    }

    /**
     * Launches the purchase UI flow for an in-app product.
     *
     * @param Sku Product SKU identifier.
     * @param DeveloperPayload Optional developer payload string.
     */
    public void LaunchPurchaseFlow(String Sku, String DeveloperPayload) {
        LaunchPurchaseFlowCustom(this.eventName, Sku, ITEM_TYPE_INAPP, DeveloperPayload);
    }

    /**
     * Launches the purchase UI flow for an in-app product without developer payload.
     *
     * @param Sku Product SKU identifier.
     */
    public void LaunchPurchaseFlow2(String Sku) {
        LaunchPurchaseFlowCustom(this.eventName, Sku, ITEM_TYPE_INAPP, "");
    }

    /**
     * Launches the purchase UI flow for a subscription item.
     *
     * @param Sku Subscription SKU identifier.
     * @param DeveloperPayload Optional developer payload string.
     */
    public void LaunchSubscriptionPurchaseFlow(String Sku, String DeveloperPayload) {
        LaunchPurchaseFlowCustom(this.eventName, Sku, ITEM_TYPE_SUBS, DeveloperPayload);
    }

    /**
     * Launches the purchase UI flow for a subscription item without developer payload.
     *
     * @param Sku Subscription SKU identifier.
     */
    public void LaunchSubscriptionPurchaseFlow2(String Sku) {
        LaunchPurchaseFlowCustom(this.eventName, Sku, ITEM_TYPE_SUBS, "");
    }

    /**
     * Launches the purchase UI flow with custom parameters.
     *
     * @param customEventName Event name prefix for the purchase callback.
     * @param Sku Product or subscription SKU identifier.
     * @param ItemType ITEM_TYPE_INAPP ("inapp") or ITEM_TYPE_SUBS ("subs").
     * @param DeveloperPayload Developer payload string.
     */
    public void LaunchPurchaseFlowCustom(final String customEventName, final String Sku, final String ItemType, final String DeveloperPayload) {
        checkInitialized();
        Activity act = ba.activity;
        if (act == null) {
            throw new IllegalStateException("LaunchPurchaseFlow requires an active Activity.");
        }

        getObject().launchPurchaseFlow(act, Sku, ItemType, new IabHelper.OnIabPurchaseFinishedListener() {
            @Override
            public void onIabPurchaseFinished(IabResult result, Purchase info) {
                MyketIabResult res = new MyketIabResult();
                res.setObject(result);
                MyketPurchase purchase = null;
                if (info != null) {
                    purchase = new MyketPurchase();
                    purchase.setObject(info);
                }
                ba.raiseEventFromDifferentThread(MyketIabHelper.this, null, 0, (customEventName + "_purchasefinished").toLowerCase(), false, new Object[] {res, purchase});
            }
        }, DeveloperPayload != null ? DeveloperPayload : "");
    }

    /**
     * Queries the user's inventory asynchronously (owned items and subscriptions).
     * Event: EventName_QueryInventoryFinished (Result As MyketIabResult, Inventory As MyketInventory)
     */
    public void QueryInventoryAsync() {
        QueryInventoryAsyncCustom(this.eventName, true, null);
    }

    /**
     * Queries the user's inventory asynchronously, specifying whether to query SKU details.
     *
     * @param QuerySkuDetails True to query details (price, title, description) for owned items.
     */
    public void QueryInventoryAsync2(boolean QuerySkuDetails) {
        QueryInventoryAsyncCustom(this.eventName, QuerySkuDetails, null);
    }

    /**
     * Queries the user's inventory and details for additional SKUs.
     *
     * @param QuerySkuDetails True to query details (price, title, description).
     * @param SkuList B4A List or Array As String of SKUs to query.
     */
    public void QueryInventoryAsync3(boolean QuerySkuDetails, anywheresoftware.b4a.objects.collections.List SkuList) {
        QueryInventoryAsyncCustom(this.eventName, QuerySkuDetails, SkuList);
    }

    /**
     * Queries the user's inventory and details for additional SKUs passed as a String array.
     *
     * @param QuerySkuDetails True to query details.
     * @param SkuIds Array of product SKU IDs.
     */
    public void QueryInventoryAsync4(boolean QuerySkuDetails, String[] SkuIds) {
        QueryInventoryAsyncCustom(this.eventName, QuerySkuDetails, SkuIds);
    }

    /**
     * Queries inventory with a custom event name and optional SKU list.
     *
     * @param customEventName Event prefix for the QueryInventoryFinished event.
     * @param QuerySkuDetails True to fetch SKU catalog details.
     * @param SkuIds Can be a B4A List, String[], Object[], or java.util.List.
     */
    public void QueryInventoryAsyncCustom(final String customEventName, boolean QuerySkuDetails, Object SkuIds) {
        checkInitialized();

        List<String> skuList = null;
        if (SkuIds instanceof anywheresoftware.b4a.objects.collections.List) {
            anywheresoftware.b4a.objects.collections.List b4aList = (anywheresoftware.b4a.objects.collections.List) SkuIds;
            skuList = new ArrayList<String>();
            for (int i = 0; i < b4aList.getSize(); i++) {
                Object item = b4aList.Get(i);
                if (item != null) skuList.add(String.valueOf(item));
            }
        } else if (SkuIds instanceof String[]) {
            skuList = Arrays.asList((String[]) SkuIds);
        } else if (SkuIds instanceof Object[]) {
            Object[] arr = (Object[]) SkuIds;
            skuList = new ArrayList<String>();
            for (Object item : arr) {
                if (item != null) skuList.add(String.valueOf(item));
            }
        } else if (SkuIds instanceof List) {
            skuList = (List<String>) SkuIds;
        }

        getObject().queryInventoryAsync(QuerySkuDetails, skuList, new IabHelper.QueryInventoryFinishedListener() {
            @Override
            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                MyketIabResult res = new MyketIabResult();
                res.setObject(result);
                MyketInventory inventory = null;
                if (inv != null) {
                    inventory = new MyketInventory();
                    inventory.setObject(inv);
                }
                ba.raiseEventFromDifferentThread(MyketIabHelper.this, null, 0, (customEventName + "_queryinventoryfinished").toLowerCase(), false, new Object[] {res, inventory});
            }
        });
    }

    /**
     * Consumes an owned in-app purchase asynchronously.
     * Event: EventName_ConsumeFinished (Purchase As MyketPurchase, Result As MyketIabResult)
     *
     * @param Purchase The purchase object to consume.
     */
    public void ConsumeAsync(final MyketPurchase Purchase) {
        ConsumeAsync2(this.eventName, Purchase);
    }

    /**
     * Consumes an owned in-app purchase asynchronously with a custom event name.
     * Event: EventName_ConsumeFinished (Purchase As MyketPurchase, Result As MyketIabResult)
     *
     * @param customEventName Event prefix for the ConsumeFinished event.
     * @param Purchase The purchase object to consume.
     */
    public void ConsumeAsync2(final String customEventName, final MyketPurchase Purchase) {
        checkInitialized();
        if (Purchase == null || Purchase.getObject() == null) {
            throw new IllegalArgumentException("Purchase parameter cannot be null.");
        }

        getObject().consumeAsync(Purchase.getObject(), new IabHelper.OnConsumeFinishedListener() {
            @Override
            public void onConsumeFinished(Purchase p, IabResult result) {
                MyketPurchase mp = new MyketPurchase();
                mp.setObject(p);
                MyketIabResult res = new MyketIabResult();
                res.setObject(result);
                ba.raiseEventFromDifferentThread(MyketIabHelper.this, null, 0, (customEventName + "_consumefinished").toLowerCase(), false, new Object[] {mp, res});
            }
        });
    }

    /**
     * Disposes of the helper and releases service connections.
     * Call this in Activity_Pause when UserClosed is True, or in Application_Error / Service_Destroy.
     */
    public void Dispose() {
        if (getObject() != null) {
            getObject().dispose();
            setObject(null);
        }
    }

    /**
     * Returns a human-readable description for a given billing response code.
     */
    public static String GetResponseDesc(int code) {
        return IabHelper.getResponseDesc(code);
    }

    private void checkInitialized() {
        if (getObject() == null) {
            throw new IllegalStateException("MyketIabHelper is not initialized or has been disposed. Call Initialize() first.");
        }
    }
}
