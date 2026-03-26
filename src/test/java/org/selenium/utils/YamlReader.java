package org.selenium.utils;

import org.openqa.selenium.By;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class YamlReader {

    private static Map<String, Object> locators;

    static {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = YamlReader.class
                .getClassLoader()
                .getResourceAsStream("locators/locators.yml")) {
            if (inputStream == null) {
                throw new RuntimeException("locators/locators.yml not found on classpath.");
            }
            locators = yaml.load(inputStream);
            if (locators == null) {
                throw new RuntimeException("Failed to load locators from locators/locators.yml. File might be empty or malformed.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading locators.yml: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> getLocatorMap(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = locators;

        for (int i = 0; i < keys.length; i++) {
            if (currentMap == null) {
                throw new IllegalArgumentException("Locator path '" + key + "' is invalid. Segment '" + keys[i-1] + "' did not lead to a map.");
            }
            Object next = currentMap.get(keys[i]);
            if (next == null) {
                throw new IllegalArgumentException("Locator '" + key + "' not found. Missing segment: '" + keys[i] + "'");
            }
            if (i < keys.length - 1) { // If not the last key, expect a map
                if (!(next instanceof Map)) {
                    throw new IllegalArgumentException("Locator path '" + key + "' is invalid. Segment '" + keys[i] + "' is not a map.");
                }
                currentMap = (Map<String, Object>) next;
            } else { // Last key, expect the final locator map
                if (!(next instanceof Map)) {
                    throw new IllegalArgumentException("Locator '" + key + "' is not defined as a map with 'type' and 'value'.");
                }
                return (Map<String, String>) next;
            }
        }
        return null; // Should not be reached
    }

    public static By getLocator(String key) {
        Map<String, String> locatorMap = getLocatorMap(key);
        Objects.requireNonNull(locatorMap, "Failed to retrieve locator map for key: " + key);

        String type = locatorMap.get("type");
        String value = locatorMap.get("value");

        if (type == null || value == null) {
            throw new IllegalArgumentException("Locator '" + key + "' is missing 'type' or 'value' in locators.yml");
        }

        switch (type.toLowerCase()) {
            case "id":
                return By.id(value);
            case "name":
                return By.name(value);
            case "classname":
                return By.className(value);
            case "tagname":
                return By.tagName(value);
            case "linktext":
                return By.linkText(value);
            case "partiallinktext":
                return By.partialLinkText(value);
            case "cssselector":
                return By.cssSelector(value);
            case "xpath":
                return By.xpath(value);
            default:
                throw new IllegalArgumentException("Unsupported locator type for '" + key + "': " + type);
        }
    }
}
