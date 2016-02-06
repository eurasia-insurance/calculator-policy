package kz.theeurasia.esbdproxy.domain.infos.general;

import java.util.Calendar;

import kz.theeurasia.esbdproxy.domain.dict.general.SexDict;

/**
 * Класс для предсталвения основных персональных данных клиента - физического
 * лица
 * 
 * @author vadim.isaev
 *
 */
public class PersonalInfo {

    // First_Name s:string Имя (для физ. лица)
    private String name;

    // Last_Name s:string Фамилия (для физ. лица)
    private String surename;

    // Middle_Name s:string Отчество (для физ. лица)
    private String patronymic;

    // Born s:string Дата рождения
    private Calendar dayOfBirth;

    // Sex_ID s:int Пол (справочник SEX)
    private SexDict sex = SexDict.UNSPECIFIED;

    // GENERATED

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSurename() {
	return surename;
    }

    public void setSurename(String surename) {
	this.surename = surename;
    }

    public String getPatronymic() {
	return patronymic;
    }

    public void setPatronymic(String patronymic) {
	this.patronymic = patronymic;
    }

    public Calendar getDayOfBirth() {
	return dayOfBirth;
    }

    public void setDayOfBirth(Calendar dayOfBirth) {
	this.dayOfBirth = dayOfBirth;
    }

    public SexDict getSex() {
	return sex;
    }

    public void setSex(SexDict sex) {
	this.sex = sex;
    }

}
