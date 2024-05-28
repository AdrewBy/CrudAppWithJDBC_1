package com.ustsinau.chapter1_3;


import com.ustsinau.chapter1_3.repository.GeneratorIdRepository;
import com.ustsinau.chapter1_3.repository.impl.GeneratorIdRepositoryImpl;
import com.ustsinau.chapter1_3.view.HeadConsole;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {


        GeneratorIdRepository cacheService = new GeneratorIdRepositoryImpl();
        cacheService.initCache();

        HeadConsole headConsole = new HeadConsole();
        try {
            headConsole.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
