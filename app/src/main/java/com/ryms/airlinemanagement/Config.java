package com.ryms.airlinemanagement;

public class Config {
    public static final String BASE_URL = "https://airline-ryms.herokuapp.com/";

    public static final String USER_LOGIN = BASE_URL+"user/login";
    public static final String EMPLOYEE_LOGIN = BASE_URL+"employee/login/";
    public static final String ADMIN_LOGIN = BASE_URL+"admin/login/";

    public static final String SIGN_UP = BASE_URL+"user/signup";

    public static final String TICKET_DETAILS = BASE_URL+"ticket/";
    public static final String TICKET_HISTORY = BASE_URL+"user/ticket/history/";

    public static final String CHECK_IN_USER = BASE_URL+"employee/checkin/";

    public static final String FETCH_ALL_USERS = BASE_URL+"admin/users";
    public static final String FETCH_ALL_FLIGHTS = BASE_URL+"admin/flight";
    public static final String FETCH_ALL_PILOTS = BASE_URL+"admin/pilot";
    public static final String RESCHEDULE = BASE_URL+"admin/reschedule/";

    public static final String FLIGHT_FILTER = BASE_URL+"flight/filter";
    public static final String BOOK_FLIGHT = BASE_URL+"user/flight";
}
