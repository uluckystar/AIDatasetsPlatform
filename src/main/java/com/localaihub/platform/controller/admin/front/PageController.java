package com.localaihub.platform.controller.admin.front;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/3/28 22:44
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * PageController is responsible for handling page navigation and rendering
 * for the front-end part of the AIDatasetsPlatform.
 *
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/3/28 22:44
 */
@Controller
public class PageController {

    /**
     * Display the home page.
     *
     * @param model The model object to pass attributes to the view.
     * @return The name of the home page template.
     */
    @GetMapping("/")
    public String showHomePage(Model model) {
        // Add any necessary attributes to the model
        model.addAttribute("message", "Welcome to AIDatasetsPlatform!");
        return "home"; // Assuming 'home.html' is your home page template
    }

    /**
     * Display the login page.
     *
     * @return The name of the login page template.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Assuming 'login.html' is your login page template
    }

    /**
     * Display the registration page.
     *
     * @return The name of the registration page template.
     */
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // Assuming 'register.html' is your registration page template
    }

    /**
     * Display a specific dataset detail page.
     *
     * @param datasetId The ID of the dataset to display details for.
     * @param model The model object to pass attributes to the view.
     * @return The name of the dataset detail page template.
     */
    @GetMapping("/datasets/{datasetId}")
    public String showDatasetDetailPage(@PathVariable String datasetId, Model model) {
        // Fetch dataset details using the datasetId
        // Add dataset details to the model
        model.addAttribute("datasetId", datasetId);
        // Assuming you have a service method to fetch dataset details
        // Dataset dataset = datasetService.findById(datasetId);
        // model.addAttribute("dataset", dataset);

        return "dataset-detail"; // Assuming 'dataset-detail.html' is your dataset detail page template
    }

    /**
     * Display the error page.
     *
     * @return The name of the error page template.
     */
    @GetMapping("/error")
    public String showErrorPage() {
        return "error"; // Assuming 'error.html' is your error page template
    }

    // You can add more page navigation methods here as needed.
}
