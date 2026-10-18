package ir.myket.billingclient.b4a;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import ir.myket.billingclient.util.IabResult;

/**
 * Represents the result of an in-app billing operation.
 */
@BA.ShortName("MyketIabResult")
public class MyketIabResult extends AbsObjectWrapper<IabResult> {

    /**
     * Initializes the wrapper with an IabResult instance.
     */
    public void Initialize(IabResult result) {
        setObject(result);
    }

    /**
     * Returns the numeric response code of the billing operation.
     * 0 = BILLING_RESPONSE_RESULT_OK.
     */
    public int getResponse() {
        return getObject() != null ? getObject().getResponse() : -1;
    }

    /**
     * Returns the human-readable message describing the billing result.
     */
    public String getMessage() {
        return getObject() != null ? getObject().getMessage() : "";
    }

    /**
     * Returns true if the operation succeeded (response code is OK).
     */
    public boolean getSuccess() {
        return getObject() != null && getObject().isSuccess();
    }

    /**
     * Returns true if the operation succeeded.
     */
    public boolean getIsSuccess() {
        return getSuccess();
    }

    /**
     * Returns true if the operation failed.
     */
    public boolean getFailure() {
        return getObject() != null && getObject().isFailure();
    }

    /**
     * Returns true if the operation failed.
     */
    public boolean getIsFailure() {
        return getFailure();
    }

    @Override
    public String toString() {
        return getObject() != null ? getObject().toString() : "MyketIabResult: null";
    }
}
