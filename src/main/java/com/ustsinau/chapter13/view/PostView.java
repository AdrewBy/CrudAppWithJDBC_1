package com.ustsinau.chapter13.view;


import com.ustsinau.chapter13.controller.PostController;
import com.ustsinau.chapter13.models.Post;

import java.io.IOException;
import java.util.Scanner;

public class PostView {
    HeadConsole headConsole = new HeadConsole();
    public static final String ACTIONS_POST = "Введите действие:\n" +
            "1.Создать новый пост\n" +
            "2.Изменить имя поста\n" +
            "3.Удалить пост\n" +
            "4.Получить имя поста по индексу\n" +
            "5.Список всех постов\n" ;

    PostController postCont = new PostController();

    public void createPost() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название нового поста:");
        String title = scanner.next();
        System.out.println("Введите содержание нового поста:");
        String content = scanner.next();

        postCont.createPostWithoutLabel(title, content);
        headConsole.run();

    }


    public void updatePost() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id поста для изменения его имени:");
        long index = Long.parseLong(scanner.next());
        System.out.println("Введите новое название поста:");
        String title = scanner.next();
        System.out.println("Введите новое содержание поста:");
        String content = scanner.next();
        scanner.nextLine();
        System.out.println("Введите id лэйблов для нового поста через ПРОБЕЛ:");
        String labelsUp = scanner.nextLine();
        postCont.updatePost(index,title,content,labelsUp);
    }

    public void deletePost() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id поста для его удаления:");
        long indexForDelete = Long.parseLong(scanner.next());
        postCont.deletePost(indexForDelete);
    }

    public void getIdPost() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id поста для получения его имени:");
        long id = Long.parseLong(scanner.next());
        System.out.println(postCont.getValueByIndex(id).toString());
    }

    public void showAllPosts() {
        System.out.println("Список всех постов:");
        for (Post item : postCont.showAll()) {
            System.out.println(item);
        }
    }
}