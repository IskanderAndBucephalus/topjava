package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int ID = ADMIN_ID + 1;

    public static final Meal MEAL_01 = new Meal(ID, of(1988,
            8, 8, 7, 0), "Desayuno01", 500);
    public static final Meal MEAL_02 = new Meal(ID +1, of(1988,
            8, 8, 13, 0), "Almuerzo01", 1000);
    public static final Meal MEAL_03 = new Meal(ID +2, of(1988,
            8, 8, 20, 0), "Cena01", 500);

    public static final Meal MEAL_04 = new Meal(ID +3, of(1988,
            9, 9, 7, 0), "Desayuno02", 500);
    public static final Meal MEAL_05 = new Meal(ID +4, of(1988,
            9, 9, 13, 0), "Almuerzo02", 1000);
    public static final Meal MEAL_06 = new Meal(ID +5, of(1988,
            9, 9, 20, 0), "Cena02", 500);

    public static final Meal MEAL_11 = new Meal(ID, of(1988,
            8, 8, 7, 0), "Desayuno11", 500);
    public static final Meal MEAL_12 = new Meal(ID +1, of(1988,
            8, 8, 13, 0), "Almuerzo11", 1000);
    public static final Meal MEAL_13 = new Meal(ID +2, of(1988,
            8, 8, 20, 0), "Cena11", 500);

    public static final Meal MEAL_14 = new Meal(ID +3, of(1988,
            9, 9, 7, 0), "Desayuno12", 500);
    public static final Meal MEAL_15 = new Meal(ID +4, of(1988,
            9, 9, 13, 0), "Almuerzo12", 1000);
    public static final Meal MEAL_16 = new Meal(ID +5, of(1988,
            9, 9, 20, 0), "Cena12", 500);
}
