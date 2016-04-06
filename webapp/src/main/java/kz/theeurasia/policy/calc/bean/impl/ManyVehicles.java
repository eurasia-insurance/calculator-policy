package kz.theeurasia.policy.calc.bean.impl;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Target({ METHOD, TYPE, FIELD })
@Retention(RUNTIME)
public @interface ManyVehicles {
}
