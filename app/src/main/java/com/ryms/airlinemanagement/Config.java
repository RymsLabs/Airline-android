package com.ryms.airlinemanagement;

public class Config {
    public static final String BASE_URL = "https://airline-ryms.herokuapp.com/";

    public static final String USER_LOGIN = BASE_URL+"user/login";
    public static final String EMPLOYEE_LOGIN = BASE_URL+"employee/login/";
    public static final String ADMIN_LOGIN = BASE_URL+"admin/login/";

    public static final String TICKET_DETAILS = BASE_URL+"ticket/";
    public static final String TICKET_HISTORY = BASE_URL+"user/ticket/history/";

    public static final String CHECK_IN_USER = BASE_URL+"employee/checkin/";
}
