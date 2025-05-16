package com.codeeditor.javacodeexecutor.controller;

import com.codeeditor.javacodeexecutor.model.CodeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.*;


@RestController
@RequestMapping("/api/code")
public class CodeExecutionController {

    @PostMapping("/run")
    public ResponseEntity<String> runJavaCode(@RequestBody CodeRequest request) {
        try {
            String code = request.getCode();
            String input = request.getInput();

            // Step 1: Save code to file
            String fileName = "Main.java";
            Files.write(Paths.get(fileName), code.getBytes());

            // Step 2: Compile code
            Process compile = Runtime.getRuntime().exec("javac " + fileName);
            compile.waitFor();

            if (compile.exitValue() != 0) {
                String error = new String(compile.getErrorStream().readAllBytes());
                return ResponseEntity.badRequest().body("Compilation error:\n" + error);
            }

            // Step 3: Run code
            Process run = Runtime.getRuntime().exec("java Main");

            // Send user input
            OutputStream stdin = run.getOutputStream();
            stdin.write(input.getBytes());
            stdin.flush();
            stdin.close();

            run.waitFor();

            // Read output and error
            String output = new String(run.getInputStream().readAllBytes());
            String errors = new String(run.getErrorStream().readAllBytes());

            return ResponseEntity.ok(output + (errors.isEmpty() ? "" : "\nErrors:\n" + errors));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error:\n" + e.getMessage());
        }
    }




}
