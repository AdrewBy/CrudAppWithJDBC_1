package com.ustsinau.chapter13.repository.impl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter13.models.Writer;
import com.ustsinau.chapter13.repository.WriterRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GsonWriterRepositoryImpl implements WriterRepository {

    private List<Writer> writerList = new ArrayList<>();

    private static String FILE = "src\\main\\java\\com\\ustsinau\\chapter13\\resources\\writers.json";


    @Override
    public void create(Writer value) {
//        if(writerList==null){
//          writerList = new ArrayList<>();  // что делать тут ? если json пуст то null
//        }
        writerList.add(value);
        Gson g = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter fileWriter = new FileWriter(FILE)) {

            g.toJson(writerList, fileWriter);

            System.out.println(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Writer value) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writerL ;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(FILE)) {

            Type listType = new TypeToken<List<Writer>>() {
            }.getType();
            writerL = gson.fromJson(reader, listType);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (writerL == null) {
            return new ArrayList<>();
        }
        if (writerL.isEmpty()) {
            return Collections.emptyList();
        }


        return writerL; // если файл пустой то сделать возврат коллекции
    }

    @Override
    public Writer getById(Long id) {
        return null;
    }


}
