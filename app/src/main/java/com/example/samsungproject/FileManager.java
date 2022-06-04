package com.example.samsungproject;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.Scanner;

public class FileManager {
    public static void readData(Context context, String path) {
        try {
            System.out.println(path);
            String[] ll = path.split(":");
            File file = new File(Environment.getExternalStorageDirectory(), ll[1]);
            System.out.println(file.getAbsolutePath());
            if (!file.canRead()) {
                throw new Exception();
            }
            Scanner scanner = new Scanner(file);
            DBWords DB = new DBWords(context);
            while (scanner.hasNextLine()) {
                try {
                    String[] data = scanner.nextLine().split(";");
                    String name = data[0];
                    String value = data[1];
                    DB.insert(name, value);
                } catch (Exception e) {
                    Toast.makeText(context, "Что-то загрузить не получилось", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            Toast.makeText(context, "Успешно!", Toast.LENGTH_SHORT).show();
            scanner.close();
        } catch (Exception e) {
            Toast.makeText(context, "Что-то загрузить не получилось", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
