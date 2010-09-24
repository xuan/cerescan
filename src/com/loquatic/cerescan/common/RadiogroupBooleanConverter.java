package com.loquatic.cerescan.common;

import java.util.Iterator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

public class RadiogroupBooleanConverter implements TypeConverter, java.io.Serializable {
   public Object coerceToUi(Object val, Component comp) { //load
       if (val != null && val instanceof Boolean) {
           Boolean bval = (Boolean) val;
           //iterate to find the selected radio via the value
           for (Iterator it = comp.getChildren().iterator(); it.hasNext();) {
               final Component child = (Component)it.next();
               if (child instanceof Radio) {
                   if (bval.toString().equalsIgnoreCase(((Radio)child).getValue())) {
                       return child;
                   }
               } else if (!(child instanceof Radiogroup)) { //skip nested radiogroup
                   //bug 2464484
                   final Object value = coerceToUi(val, child); //recursive
                   if (value != null) {
                       return value;
                   }
               }
           }
       }
       return null;
   }

   public Object coerceToBean(Object val, Component comp) { //save
               return val != null ? Boolean.parseBoolean(((Radio)val).getValue()) : null;
   }
} 
