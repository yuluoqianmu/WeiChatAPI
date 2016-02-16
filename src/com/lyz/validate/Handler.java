/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lyz.validate;

import java.lang.reflect.Field;


/**
 *This is the validate process interface
 * @author jiangtch
 */
public interface Handler {

    public void validate(AnnotationValidable validatedObj, Field field) throws ValidateException;
}
