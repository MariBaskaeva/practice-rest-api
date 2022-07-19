package ru.baskaeva.practicerestapi.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CamundaCopyController {

    @GetMapping("/copy")
    public ResponseEntity copy(@RequestParam String from, @RequestParam String to) {
        Map<String, String> body = new HashMap<>();
        ArrayList<String> fileNames = new ArrayList<>();
        File folder = new File(from);

        //Takes all files from dir
        List<File> listOfFiles = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isFile())
                listOfFiles.add(file);
        }

        //Copies files to the destination dir
        Path destDir = Paths.get(to);
        if (!listOfFiles.isEmpty())
            for (File file : listOfFiles) {
                try {
                    Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
                    fileNames.add(file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                    ResponseEntity.internalServerError();
                }
            }
        body.put("files", fileNames.toString());

        return ResponseEntity.ok().body(body.toString());
    }
}
