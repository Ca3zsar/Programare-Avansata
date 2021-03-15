package catalog;

import catalogEntries.CatalogEntry;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Catalog implements Serializable {
    final List<CatalogEntry> entries; // accessible to the class and package

    public Catalog() {
        this.entries = new ArrayList<>();
    }

    /**
     * Create an HTML report containing the information from the catalog.
     */
    public void report() throws IOException, TemplateException {
        Configuration configuration = new Configuration();

        configuration.setDirectoryForTemplateLoading(new File("resources"));

        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.US);

        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<>();

        input.put("title", "Catalog report");

        List<String> entriesDescription = this.entries.stream().map(entry -> entry.toString()).collect(Collectors.toList());

        input.put("entries", entriesDescription);

        Template template = configuration.getTemplate("report.ftl");

        try (Writer fileWriter = new FileWriter("output.html")) {
            template.process(input, fileWriter);
        }
    }

}
