package com.serli.soap.scope;

import javax.inject.Scope;
import java.lang.annotation.Inherited;
import javax.enterprise.context.NormalScope;
import com.google.inject.ScopeAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;


@Target(TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Scope
@ScopeAnnotation
@NormalScope
@Inherited
public @interface SOAPScoped {

}
