package com.ustsinau.chapter13.view;

import java.io.IOException;
import java.util.Scanner;

import static com.ustsinau.chapter13.view.LabelView.ACTIONS_LABEL;
import static com.ustsinau.chapter13.view.PostView.ACTIONS_POST;
import static com.ustsinau.chapter13.view.WriterView.ACTIONS_WRITER;

public class HeadConsole {
    private static final String WELCOME_MESSAGE = "Добро пожаловать!\n" +
            "Выберите раздел:\n" +
            "1.Авторы\n" +
            "2.Посты\n" +
            "3.Лэйблы\n";

    public void run() throws IOException {
        System.out.println(WELCOME_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        String line = scanner.next();
        switch (line) {
            case "1":
                WriterView writerView = new WriterView();
                System.out.println(ACTIONS_WRITER);
                String actionWriter = scanner.next();
                switch (actionWriter) {
                    case "1":
                        writerView.createWriter();
                        break;
                    case "2":
                        writerView.updateWriter();
                        break;
                    case "3":
                        writerView.deleteWriter();
                        break;
                    case "4":
                        writerView.getIdWriter();
                        break;
                    case "5":
                        writerView.showAllWriter();
                        break;
                }
                break;
            case "2":
                PostView postView = new PostView();
                System.out.println(ACTIONS_POST);
                String actionView = scanner.next();
                switch (actionView) {
                    case "1":
                        postView.createPost();
                        break;
                    case "2":
                        postView.updatePost();
                        break;
                    case "3":
                        postView.deletePost();
                        break;
                    case "4":
                        postView.getIdPost();
                        break;
                    case "5":
                        postView.showAllPosts();
                        break;
                }
                break;
            case "3":
                LabelView labelView = new LabelView();
                System.out.println(ACTIONS_LABEL);
                String actionLabel = scanner.next();
                switch (actionLabel) {
                    case "1":
                        labelView.createLabel();
                        break;
                    case "2":
                        labelView.updateLabel();
                        break;
                    case "3":
                        labelView.deleteLabel();
                        break;
                    case "4":
                        labelView.getIdLabel();
                        break;
                    case "5":
                        labelView.showAllLabels();
                        break;
                }
                break;
        }
    }
}
