package kz.theeurasia.policy.calc.bean.dataBuilder;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.faces.application.ProjectStage;
import javax.inject.Qualifier;

@Qualifier
@Target({ METHOD, TYPE, FIELD })
@Retention(RUNTIME)
public @interface ProjectStageDepend {
    ProjectStage stage() default ProjectStage.Production;
}
