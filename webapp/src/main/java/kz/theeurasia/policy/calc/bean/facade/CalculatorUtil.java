package kz.theeurasia.policy.calc.bean.facade;

import java.util.Calendar;
import java.util.Date;

public final class CalculatorUtil {
    public static int calculateAgeByDOB(Calendar dob) {
	Calendar now = Calendar.getInstance();
	int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
	if (now.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
	    age--;
	} else if (now.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		&& now.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
	    age--;
	}
	return age;
    }

    public static int calculateAgeByDOB(Date dob) {
	Calendar c = Calendar.getInstance();
	c.setTime(dob);
	return calculateAgeByDOB(c);
    }
}
