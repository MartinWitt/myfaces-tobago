package org.apache.myfaces.tobago.renderkit;

/*
 * Copyright 2002-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.ComponentUtil;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SelectManyRendererBase extends RendererBase {

  private static final Log LOG = LogFactory.getLog(SelectManyRendererBase.class);

  public void decode(FacesContext facesContext, UIComponent component) {
    if (ComponentUtil.isOutputOnly(component)) {
      return;
    }

    UISelectMany uiSelectMany = (UISelectMany) component;

    String[] newValues = (String[])
        facesContext.getExternalContext().getRequestParameterValuesMap().get(uiSelectMany.getClientId(facesContext));
    if (LOG.isDebugEnabled()) {
      LOG.debug("decode: key='" + component.getClientId(facesContext)
          + "' value='" + newValues + "'");
      LOG.debug("size ... '" + (newValues != null ? newValues.length : -1) + "'");
      if (newValues != null) {
        for (String newValue : newValues) {
          LOG.debug("newValues[i] = '" + newValue + "'");
        }
      }
    }

    if (newValues == null) {
      newValues = new String[0]; // because no selection will not submitted by browsers
    }
    uiSelectMany.setSubmittedValue(newValues);

  }

  // the following is copied from myfaces shared RendererUtils
  public Object getConvertedValue(FacesContext facesContext, UIComponent component, Object submittedValue) throws ConverterException {

    if (submittedValue == null)
    {
      return null;
    }
    else
    {
      if (!(submittedValue instanceof String[]))
      {
        throw new ConverterException("Submitted value of type String[] for component : "
            + component.getClientId(facesContext) + "expected");
      }
    }
    return getConvertedUISelectManyValue(facesContext, (UISelectMany) component, (String[]) submittedValue);
  }

  private Object getConvertedUISelectManyValue(FacesContext facesContext,
                                               UISelectMany component,
                                               String[] submittedValue)
            throws ConverterException
    {
        // Attention!
        // This code is duplicated in jsfapi component package.
        // If you change something here please do the same in the other class!

        if (submittedValue == null) throw new NullPointerException("submittedValue");

        ValueBinding vb = component.getValueBinding("value");
        Class valueType = null;
        Class arrayComponentType = null;
        if (vb != null)
        {
            valueType = vb.getType(facesContext);
            if (valueType != null && valueType.isArray())
            {
                arrayComponentType = valueType.getComponentType();
            }
        }

        Converter converter = component.getConverter();
        if (converter == null)
        {
            if (valueType == null)
            {
                // No converter, and no idea of expected type
                // --> return the submitted String array
                return submittedValue;
            }

            if (List.class.isAssignableFrom(valueType))
            {
                // expected type is a List
                // --> according to javadoc of UISelectMany we assume that the element type
                //     is java.lang.String, and copy the String array to a new List
                int len = submittedValue.length;
                List lst = new ArrayList(len);
                for (int i = 0; i < len; i++)
                {
                    lst.add(submittedValue[i]);
                }
                return lst;
            }

            if (arrayComponentType == null)
            {
                throw new IllegalArgumentException("ValueBinding for UISelectMany must be of type List or Array");
            }

            if (String.class.equals(arrayComponentType)) return submittedValue; //No conversion needed for String type
            if (Object.class.equals(arrayComponentType)) return submittedValue; //No conversion for Object class

            try
            {
                converter = facesContext.getApplication().createConverter(arrayComponentType);
            }
            catch (FacesException e)
            {
                LOG.error("No Converter for type " + arrayComponentType.getName() + " found", e);
                return submittedValue;
            }
        }

        // Now, we have a converter...
        // We determine the type of the component array after converting one of it's elements
        if (vb != null)
        {
            valueType = vb.getType(facesContext);
            if (valueType != null && valueType.isArray())
            {
                if (submittedValue.length > 0)
                {
                    arrayComponentType = converter.getAsObject(facesContext, component, submittedValue[0]).getClass();
                }
            }
        }

        if (valueType == null)
        {
            // ...but have no idea of expected type
            // --> so let's convert it to an Object array
            int len = submittedValue.length;
            Object [] convertedValues = (Object []) Array.newInstance(
                    arrayComponentType==null?Object.class:arrayComponentType,len);
            for (int i = 0; i < len; i++)
            {
                convertedValues[i]
                    = converter.getAsObject(facesContext, component, submittedValue[i]);
            }
            return convertedValues;
        }

        if (List.class.isAssignableFrom(valueType))
        {
            // Curious case: According to specs we should assume, that the element type
            // of this List is java.lang.String. But there is a Converter set for this
            // component. Because the user must know what he is doing, we will convert the values.
            int len = submittedValue.length;
            List lst = new ArrayList(len);
            for (int i = 0; i < len; i++)
            {
                lst.add(converter.getAsObject(facesContext, component, submittedValue[i]));
            }
            return lst;
        }

        if (arrayComponentType == null)
        {
            throw new IllegalArgumentException("ValueBinding for UISelectMany must be of type List or Array");
        }

        if (arrayComponentType.isPrimitive())
        {
            //primitive array
            int len = submittedValue.length;
            Object convertedValues = Array.newInstance(arrayComponentType, len);
            for (int i = 0; i < len; i++)
            {
                Array.set(convertedValues, i,
                          converter.getAsObject(facesContext, component, submittedValue[i]));
            }
            return convertedValues;
        }
        else
        {
            //Object array
            int len = submittedValue.length;
            ArrayList convertedValues = new ArrayList(len);
            for (int i = 0; i < len; i++)
            {
                convertedValues.add(i, converter.getAsObject(facesContext, component, submittedValue[i]));
            }
            return convertedValues.toArray((Object[]) Array.newInstance(arrayComponentType, len));
        }
    }

}


