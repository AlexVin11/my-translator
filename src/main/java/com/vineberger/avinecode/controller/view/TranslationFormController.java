package com.vineberger.avinecode.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TranslationFormController {

    @GetMapping("/maintranslationforms")
    public String showMainTranslationForms() {
        return "maintranslationforms";
    }
}
