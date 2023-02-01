package ru.yandex.practicum.filmorate.message;

public enum ExceptionMessage {
    ;
    public static final String EMPTY_NAME = "Название фильма не может быть пустым";
    public static final String MAX_DESCRIPTION_SIZE = "Максимальная длина описания — 200 символов.";
    public static final String POSITIVE_DURATION = "Продолжительность фильма должна быть положительной";
    public static final String INCORRECT_EMAIL = "Некорректный e-mail адрес";
    public static final String EMPTY_LOGIN = "Логин пользователя не может быть пустой";
    public static final String INCORRECT_BIRTHDAY = "Дата рождения не может быть в будущем";
    public static final String LOGIN_WITHOUT_SPACE = "login не должен содержать пробелы";
}

