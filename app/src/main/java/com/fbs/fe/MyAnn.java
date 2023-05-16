package com.fbs.fe;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class MyAnn implements ShowInfo{
    
    @Override
        public void printInfo(String arg) {
            System.out.println(arg);
        }
        
    
    public void returnName(){
        StringBuilder sb = new StringBuilder("Список команд: \n");
        for (Method m : this.getClass().getDeclaredMethods())
        {
            if (m.isAnnotationPresent(SetFields.class))
            {
                SetFields fields = m.getAnnotation(SetFields.class);
                printInfo(fields.name());
            }
        }
    }
}

@SuppressWarnings("all")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface SetFields{
    String name();
    int age();
}

interface ShowInfo{
    public void printInfo(String arg);
}
