package com.findy.category.domain.model;

import com.findy.category.domain.exception.CreateCategoryException;
import lombok.Getter;

@Getter
public class CategoryInfo {
    private String name;
    private String description;
    private String iconUrl;

    public CategoryInfo(String name, String description, String iconUrl) {
        Validator.name(name);
        Validator.description(description);

        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
    }

    private static class Validator {
        private final static int NAME_SIZE_LIMIT = 32;
        private final static int DESCRIPTION_SIZE_LIMIT = 255;

        private static void name(String name) {
            if (name == null || name.isEmpty()) {
                throw new CreateCategoryException("Name cannot be null or empty");
            }

            if (name.length() > NAME_SIZE_LIMIT) {
                throw new CreateCategoryException("Name cannot exceed " + NAME_SIZE_LIMIT + " characters");
            }
        }

        private static void description(String description) {
            if (description.length() > DESCRIPTION_SIZE_LIMIT) {
                throw new CreateCategoryException("Description cannot exceed" + DESCRIPTION_SIZE_LIMIT +  "characters");
            }
        }
    }
}
