# Myket In-App Billing SDK for B4A (Basic4Android)

Android In-App Billing library and sample project for [Myket App Store](https://myket.ir), targeting **B4A
v14**.

This SDK wraps Myket's native billing client (`ir.myket.billingclient.IabHelper`), exposing its methods and
event callbacks in a clean, idiomatic B4A interface.

---

## 📁 Directory Structure

```text
./B4A/
├── MyketBillingLibrary/      # Compiled B4A library files (copy to Additional Libraries)
│   ├── MyketBilling.jar       # B4A Java wrapper binary with embedded manifest macro
│   ├── MyketBilling.xml       # B4A IDE autocomplete & type descriptor
│   └── MyketBilling-2.2.aar # Underlying Myket Billing SDK AAR
│
├── MyketBillingSample/       # Complete B4A sample application (TrivialDrive)
│   ├── MyketBillingSample.b4a
│   ├── MyketBillingSample.b4a.meta
│   ├── Starter.bas
│   ├── Files/                 # Game graphics (car, gas gauges, buttons, wait screen)
│   └── Objects/               # Compiled APK & build artifacts
│
├── WrapperSource/            # Complete Java wrapper source code
│   ├── src/ir/myket/billingclient/b4a/
│   │   ├── MyketIabHelper.java
│   │   ├── MyketBilling.java
│   │   ├── MyketIabResult.java
│   │   ├── MyketPurchase.java
│   │   ├── MyketInventory.java
│   │   └── MyketSkuDetails.java
│   └── MyketBillingReceiver.b4x_excluded
│
└── README.md
```

---

## 🚀 Getting Started

### 1. Install the Library in B4A

Copy all three files from `./B4A/MyketBillingLibrary/` to your B4A **Additional Libraries** folder:

- `MyketBilling.jar`
- `MyketBilling.xml`
- `MyketBilling-2.2.aar`

In the B4A IDE, refresh the **Libraries Manager** tab and check **MyketBilling** (or `myketbilling`).

### 2. Configure the Android Manifest

Add the manifest macro in the B4A **Manifest Editor** (`Project -> Manifest Editor`):

```b4a
CreateResourceFromFile(Macro, MyketBilling.MyketBillingReceiver)
```

The macro automatically configures:

- Required permissions (`INTERNET`, `ir.mservices.market.BILLING`)
- Android 11+ package visibility queries for Myket and Chrome
- Required activities (`ProxyBillingActivity`, `PaymentCallbackActivity`, `CustomTabLauncherActivity`)
- Metadata (`market_id`, `market_bind_address`, `sdk_version`)
- Broadcast receiver (`IABReceiver`)

### 3. Required Project Dependencies

Add the following `#AdditionalJar` directives to your main module's `#Region Project Attributes`:

```b4a
#AdditionalJar: androidx.core:core
#AdditionalJar: androidx.browser:browser
#AdditionalJar: org.jetbrains.kotlin-kotlin-stdlib
#AdditionalJar: org.jetbrains.kotlinx-kotlinx-coroutines-core-jvm
#AdditionalJar: org.jetbrains.kotlinx-kotlinx-coroutines-android
#AdditionalJar: okhttp-4.9.0
#AdditionalJar: okio-2.8.0
#AdditionalJar: gson-2.8.5
```

*(All of these JARs / AARs are included by default in B4A v14 / Android SDK).*

---

## 📖 API Reference

The SDK is designed to be as close to `ir.myket.billingclient.IabHelper` as possible.

### Declaration and Initialization

```b4a
Sub Globals
    Private mHelper As MyketIabHelper ' or MyketBilling
End Sub

Sub Activity_Create(FirstTime As Boolean)
    ' Initialize with EventName prefix and your Base64 Public Key from Myket Console
    mHelper.Initialize("IabHelper", "YOUR_BASE64_PUBLIC_KEY")
    mHelper.EnableDebugLogging(True)
    mHelper.StartSetup
End Sub
```

### Methods

| Method                                                        | Description                                                 |
|---------------------------------------------------------------|-------------------------------------------------------------|
| `Initialize(EventName, Base64PublicKey)`                      | Initializes the helper with context and RSA public key      |
| `EnableDebugLogging(Enable)`                                  | Enables/disables Logcat debug logs                          |
| `EnableDebugLogging2(Enable, Tag)`                            | Enables/disables debug logs with custom tag                 |
| `StartSetup()`                                                | Starts connection to Myket billing service asynchronously   |
| `StartSetup2(EventName)`                                      | Starts connection with a custom callback event prefix       |
| `SubscriptionsSupported`                                      | Returns `True` if subscriptions are supported on the device |
| `LaunchPurchaseFlow(Sku, DeveloperPayload)`                   | Initiates in-app purchase flow                              |
| `LaunchPurchaseFlow2(Sku)`                                    | Initiates in-app purchase flow without payload              |
| `LaunchSubscriptionPurchaseFlow(Sku, DeveloperPayload)`       | Initiates subscription purchase flow                        |
| `LaunchSubscriptionPurchaseFlow2(Sku)`                        | Initiates subscription purchase flow without payload        |
| `LaunchPurchaseFlowCustom(EventName, Sku, ItemType, Payload)` | Initiates flow with custom event and item type              |
| `QueryInventoryAsync()`                                       | Queries owned items and SKU details asynchronously          |
| `QueryInventoryAsync2(QuerySkuDetails)`                       | Queries owned items, specifying whether to fetch details    |
| `QueryInventoryAsync3(QuerySkuDetails, SkuList)`              | Queries owned items + specific SKUs from a B4A `List`       |
| `QueryInventoryAsync4(QuerySkuDetails, SkuIds)`               | Queries owned items + specific SKUs from a `String[]`       |
| `ConsumeAsync(Purchase)`                                      | Consumes a purchased item asynchronously                    |
| `ConsumeAsync2(EventName, Purchase)`                          | Consumes a purchased item with custom callback event        |
| `Dispose()`                                                   | Disposes helper and releases service connections            |
| `GetResponseDesc(Code)`                                       | Returns human-readable string for numeric response code     |

### Events

| Event Signature                                                                                | Raised When                                |
|------------------------------------------------------------------------------------------------|--------------------------------------------|
| `Sub EventName_SetupFinished (Result As MyketIabResult)`                                       | Setup completes successfully or with error |
| `Sub EventName_QueryInventoryFinished (Result As MyketIabResult, Inventory As MyketInventory)` | Inventory query completes                  |
| `Sub EventName_PurchaseFinished (Result As MyketIabResult, Purchase As MyketPurchase)`         | Purchase flow finishes or is cancelled     |
| `Sub EventName_ConsumeFinished (Purchase As MyketPurchase, Result As MyketIabResult)`          | Item consumption finishes                  |

---

## 🎮 Sample Application (TrivialDrive)

The sample project in `./B4A/MyketBillingSample/` implements the TrivialDrive scenario matching
`MainActivity.java` from `billing-sample-main`:

- **Consumable Gas (`"gas"`)**: Purchasing gas adds 1/4 tank after consumption.
- **Non-Consumable Premium Upgrade (`"premium"`)**: Upgrades the car to the red model permanently.
- **Subscription Infinite Gas (`"infinite_gas"`)**: Provides unlimited driving without consuming gas.
- **Lifecycle Management**: Safely disposes the billing helper in `Activity_Pause` when `UserClosed = True`.
- **Pure Responsive UI**: Implemented directly in B4A using high-resolution assets (`title.png`, `free.png`,
  `premium.png`, `gas0..4.png`, `gas_inf.png`, `buy_gas.png`, `drive.png`, `upgrade_app.png`,
  `infinite_gas.png`, `wait.png`).
