package com.mobiquityinc.builders;

/**
 * Main interface accountable for  building things within application.
 * <p>
 * Created by RLYBD20 on 26/10/2017.
 */
public interface Builder<I, O> {

    /**
     * Validates input item. Should be called before build operation.
     */
    public boolean validate(I inputItem);

    /**
     * Build something (O) based on input.
     */
    public O build(I inputItem);

}
