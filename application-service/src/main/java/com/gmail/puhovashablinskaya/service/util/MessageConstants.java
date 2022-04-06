package com.gmail.puhovashablinskaya.service.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageConstants {
    public static final String ERROR_FILE_DUPLICATES = "Файл содержит дубль заявки";

    public static final String FILE_DATA_EXCEPTION = "Invalid data from file";

    public static final String LEGAL_NOT_FOUND_EXCEPTION = "Legal not found";

    public static final String SUCCESS_ADD_APPLICATIONS = "Application accepted";

    public static final String INVALID_APPLICATION_DATA_MESSAGE = "Invalid data from file";

    public static final String STATUS_EXCEPTION = "Invalid status application";

    public static final String INVALID_FILE_MESSAGE = "Invalid file";

    public static final String PAGINATION_ERROR_MESSAGE = "This type of pagination is not supported";

    public static final String APPLICATION_IS_NOT_FOUND = "Заявление на конверсию от сотрудника не существует";

    public static final String APPLICATION_BINDING = "Заявка на конверсию %s привязана к %s";

    public static final String APPLICATION_REBINDING = "Заявка на конверсию %s перепривязана к %s";

    public static final String APPLICATION_STATUS_NOT_CHANGED = "Статус не может быть изменен";

    public static final String APPLICATION_STATUS_CHANGED = "Статус изменен";

    public static final String APPLICATION_STATUS_NOT_FOUND = "Status not found";
}
