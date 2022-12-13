package workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.tools.javac.Main;
import input.*;

import java.io.File;
import java.io.IOException;

public class Actions{

    public static void action(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]), Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);

        for (input.ActionInput actions: inputData.getActions()) {
            switch (actions.getType()) {
                case "on page"-> {
                    switch (actions.getFeature()) {
                        case "register"-> {

                        }
                    }
                }
                case "chage page"-> {

                }
            }
        }
    }
}
