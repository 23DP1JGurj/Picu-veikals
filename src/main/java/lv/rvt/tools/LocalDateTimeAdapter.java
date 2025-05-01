package lv.rvt.tools;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value != null) {
            out.value(value.format(formatter)); // Сериализуем LocalDateTime в строку
        } else {
            out.nullValue(); // Если null, то записываем null
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        String date = in.nextString();
        if (date != null) {
            return LocalDateTime.parse(date, formatter); // Десериализуем строку обратно в LocalDateTime
        }
        return null; // Если строка равна null, то возвращаем null
    }
}
