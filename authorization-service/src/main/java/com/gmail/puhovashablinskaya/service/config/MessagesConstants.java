package com.gmail.puhovashablinskaya.service.config;

public class MessagesConstants {
    private MessagesConstants() {
    }

    public static final String INVALID_LOGIN_DATA = "Login or password is not valid";

    public static final String BLANK_USERNAME_ERROR = "Username should be not blank";

    public static final String INVALID_LOGOUT_DATA = "Incorrect data";

    public static final String USERNAME_NULL_MESSAGE = "Username should be not null";

    public static final String USERNAME_IS_NOT_UNIQUE = "Пользователь существует. Укажите другой логин";

    public static final String USERNAME_LENGTH_ERROR = "Username length must be more than 6 and less than 100 characters";

    public static final String USERNAME_FORMAT_ERROR = "Username does not fit the given format";

    public static final String PASSWORD_LENGTH_ERROR = "Password length should be from 6 to 20 characters";

    public static final String PASSWORD_NULL_ERROR = "Password should be not null";

    public static final String EMAIL_EMPTY_ERROR = "Usermail should be not null";

    public static final String EMAIL_FORMAT_ERROR = "Usermail does not fit the given format";

    public static final String EMAIL_LENGTH_ERROR = "Usermail length must be more than 6 and less than 100 characters";

    public static final String EMAIL_NOT_UNIQUE_ERROR = "Usermail is not unique";

    public static final String USER_FIRSTNAME_NULL_ERROR = "Name should be not null";

    public static final String USER_FIRSTNAME_LANGUAGE_ERROR = "Name should be written in Russian";

    public static final String USER_FIRSTNAME_LENGTH_ERROR = "Name length must be less than 20 characters";

    public static final String USER_BLOCKED_MESSAGE = "Ваша учетная запись заблокирована. Обратитесь к Администратору";
}
