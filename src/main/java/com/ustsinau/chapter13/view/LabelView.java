package com.ustsinau.chapter13.view;


import com.ustsinau.chapter13.controller.LabelController;
import com.ustsinau.chapter13.models.Label;


import java.io.IOException;
import java.util.Scanner;

public class LabelView {
    private HeadConsole headConsole = new HeadConsole();
    public static final String ACTIONS_LABEL = "Введите действие:\n" +
            "1.Создать новый лэйбл\n" +
            "2.Изменить лэйбл\n" +
            "3.Удалить лэйбл\n" +
            "4.Получить лэйбл по id\n" +
            "5.Список всех лэйблов\n";

    LabelController labelContrl = new LabelController();

    public void createLabel() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя нового лэйбла:");
        String name = scanner.next();
        labelContrl.createLabel(name);
        headConsole.run();
    }

    public void updateLabel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id лэйбла для его изменения:");
        long index = Long.parseLong(scanner.next());
        System.out.println("Введите новое название лэйбла(состоит из одного слова):");
        String name = scanner.next();
        Label updateLabel = new Label(index, name);
        labelContrl.updateLabel(updateLabel);
    }

    public void deleteLabel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id лэйбла для его удаления:");
        long indexForDelete = Long.parseLong(scanner.next());
        labelContrl.deleteLabel(indexForDelete);
    }

    public void getIdLabel() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id лэйбла для получения информации:");
        long id = Long.parseLong(scanner.next());

        System.out.println("\n"+labelContrl.getValueByIndex(id) + "\n");

        headConsole.run();
    }

    public void showAllLabels() throws IOException {
        System.out.println("Список всех категорий:");
        for (Label item : labelContrl.showAll()) {
            System.out.println(item);
        }
        headConsole.run();
    }
}