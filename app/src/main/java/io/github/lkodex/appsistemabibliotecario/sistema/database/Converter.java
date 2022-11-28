package io.github.lkodex.appsistemabibliotecario.sistema.database;

import java.sql.Date;
import java.util.UUID;

import androidx.room.TypeConverter;

public class Converter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String uuidToString(UUID uuid) { return uuid == null ? null : uuid.toString(); }

    @TypeConverter
    public static UUID stringToUUID(String string) { return UUID.fromString(string); }
}
