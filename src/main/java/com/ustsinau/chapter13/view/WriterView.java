package com.ustsinau.chapter13.view;


import com.ustsinau.chapter13.controller.PostController;
import com.ustsinau.chapter13.models.Status;
import com.ustsinau.chapter13.controller.WriterController;
import com.ustsinau.chapter13.models.Writer;



import java.io.IOException;
import java.util.Scanner;

public class WriterView {
    HeadConsole headConsole = new HeadConsole();
    public static final String ACTIONS_WRITER = "Введите действие:\n" +
            "1.Создать нового автора\n" +
            "2.Изменить автора\n" +
            "3.Удалить автора\n" +
            "4.Получить имя автора по индексу\n" +
            "5.Список всех авторов\n";

    WriterController writerControllerContr = new WriterController();
    PostController postController =new PostController();


    public void createWriter() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя нового автора:");
        String firstName = scanner.next();
        System.out.println("Введите фамилию нового автора:");
        String lastName = scanner.next();
        System.out.println("Хотите создать пост автору? Выберите 1 или 2:\n" +
                "1.Да\n" +
                "2.Нет");
        String postUp = scanner.next();

        switch (postUp) {
            case "1":
                System.out.println("Введите название нового поста:");
                String title = scanner.next();
                System.out.println("Введите содержание нового поста:");
                String content = scanner.next();
//
//                postController.createPostWithoutLabel(title, content);
//                List<Post> posts;
//
//
//                ist<Post> posts = (List<Post>) postController.showAll().g;
//
//                writerControllerContr.createWriterWithPost(firstName, lastName, posts);
                break;
            case "2":
                writerControllerContr.createWriterWithoutPost(firstName, lastName);
                break;
        }

        headConsole.run();
        scanner.close();  // нужно ли закрывать? если перевызываю run

    }

    public void updateWriter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите индекс автора для его изменения:");
        long indexUp = Long.parseLong(scanner.next());
        System.out.println("Введите имя автора:");
        String nameWrUp = scanner.next();
        System.out.println("Введите фамилию нового автора:");
        String lastNameWrUP = scanner.next();


        System.out.println("Введите статус для автора:\n" +
                "1.Активен\n" +
                "2.Удалить");
        String statUp = scanner.next();
        Status stUp = null;
        switch (statUp) {
            case "1":
                stUp = Status.ACTIVE;
                break;
            case "2":
                stUp = Status.DELETED;
                break;
        }

        writerControllerContr.updateWriter(indexUp, nameWrUp, lastNameWrUP, stUp);
    }

    public void deleteWriter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите индекс проекта для его удаления:");
        long indexForDelete = Long.parseLong(scanner.next());
        writerControllerContr.deleteWriter(indexForDelete);
    }

    public void getIdWriter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите индекс автора для получения всей информации:");
        long id = Long.parseLong(scanner.next());
        System.out.println(writerControllerContr.getValueByIndex(id).toString());
    }


    public void showAllWriter() throws IOException {

        System.out.println("Список всех авторов:");
        for (Writer item : writerControllerContr.showAll()) {
            System.out.println(item);
        }
        headConsole.run();
    }
}
