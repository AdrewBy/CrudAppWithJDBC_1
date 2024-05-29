package com.ustsinau.chapter1_3.view;


import com.ustsinau.chapter1_3.controller.WriterController;
import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.models.Writer;
import com.ustsinau.chapter1_3.repository.PostRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonPostRepositoryImpl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final PostRepository posts = new GsonPostRepositoryImpl();
    private final Scanner scanner = new Scanner(System.in);
    private final HeadConsole headConsole = new HeadConsole();
    public static final String ACTIONS_WRITER = "Введите действие:\n" +
            "1.Создать нового автора\n" +
            "2.Изменить автора\n" +
            "3.Удалить автора\n" +
            "4.Получить информацию об авторе по id\n" +
            "5.Список всех авторов\n";

    WriterController writerControllerContr = new WriterController();

    public void createWriter() throws IOException {

        System.out.println("Введите имя нового автора:");
        String firstName = scanner.next();
        System.out.println("Введите фамилию нового автора:");
        String lastName = scanner.next();

        writerControllerContr.createWriterWithoutPost(firstName, lastName);
        headConsole.run();
    }

    public void updateWriter() throws IOException {

        System.out.println("Введите индекс автора для его изменения:");
        long indexUp = Long.parseLong(scanner.next());
        System.out.println("Введите имя автора:");
        String nameWrUp = scanner.next();
        System.out.println("Введите фамилию нового автора:");
        String lastNameWrUP = scanner.next();
        scanner.nextLine();
        System.out.println("Введите id постов для автора через ПРОБЕЛ:");
        String postsUp = scanner.nextLine();
        List<Post> postsWriter = new ArrayList<>();

        long[] numArr = Arrays.stream(postsUp.split(" ")).mapToLong(Long::parseLong).toArray();
        if (numArr.length == 0) {
            System.out.println("Укажите пост");
        }
        for (long l : numArr) {                               // подумать над этим

            postsWriter.add(posts.getById(l));

        }
        writerControllerContr.updateWriter(indexUp, nameWrUp, lastNameWrUP, postsWriter);
        headConsole.run();
    }

    public void deleteWriter() throws IOException {

        System.out.println("Введите id автора для его удаления:");
        long indexForDelete = Long.parseLong(scanner.next());
        writerControllerContr.deleteWriter(indexForDelete);
        headConsole.run();
    }

    public void getIdWriter() throws IOException {

        System.out.println("Введите id автора для получения всей информации:");
        long id = Long.parseLong(scanner.next());
        System.out.println("\n" + writerControllerContr.getValueByIndex(id) + "\n");
        headConsole.run();
    }


    public void showAllWriter() throws IOException {

        System.out.println("Список всех авторов:");
        for (Writer item : writerControllerContr.showAll()) {
            System.out.println(item);
        }
        headConsole.run();
    }
}
