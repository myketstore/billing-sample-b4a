package ir.myket.billingclient.b4a;

import anywheresoftware.b4a.BA;

/**
 * Convenience alias for MyketIabHelper.
 */
@BA.ShortName("MyketBilling")
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
public class MyketBilling extends MyketIabHelper {
}
