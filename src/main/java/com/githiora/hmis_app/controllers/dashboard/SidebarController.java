package com.githiora.hmis_app.controllers.dashboard;

import com.githiora.hmis_app.controllers.MainLayoutController;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class SidebarController {
@FXML
    private VBox sidebarRoot;
    
    @FXML
    private VBox menuContainer;
    
    @FXML
    private Label userNameLabel;
    
    @FXML
    private Label userRoleLabel;

    private Consumer<String> onMenuItemClick;
    
    private MainLayoutController mainLayoutController; 

    public void setMainController(MainLayoutController controller) {
        this.mainLayoutController = controller;
    }
    @FXML
    public void initialize() {
        buildMenu();
    }

    public void setOnMenuItemClick(Consumer<String> callback) {
        this.onMenuItemClick = callback;
    }

    public void setUserInfo(String name, String role) {
        userNameLabel.setText(name);
        userRoleLabel.setText(role);
    }

    private void buildMenu() {
        // MAIN NAVIGATION Section
        addSectionLabel("MAIN NAVIGATION");
        
        // Dashboard (no children)
        addMenuItem("📊", "Dashboard", "dashboard", null);
        
        // Patients Menu (with children)
        List<MenuItem> patientsChildren = new ArrayList<>();
        patientsChildren.add(new MenuItem("All Patients", "patients/all"));
        patientsChildren.add(new MenuItem("New Patient", "patients/new"));
        patientsChildren.add(new MenuItem("Patient Records", "patients/records"));
        addMenuItem("👥", "Patients", null, patientsChildren);
        
        // Appointments Menu (with children)
        List<MenuItem> appointmentsChildren = new ArrayList<>();
        appointmentsChildren.add(new MenuItem("All Appointments", "appointments/all"));
        appointmentsChildren.add(new MenuItem("Schedule New", "appointments/schedule"));
        appointmentsChildren.add(new MenuItem("Calendar View", "appointments/calendar"));
        addMenuItem("📅", "Appointments", null, appointmentsChildren);
        
        // Doctors Menu (with children)
        List<MenuItem> doctorsChildren = new ArrayList<>();
        doctorsChildren.add(new MenuItem("All Doctors", "doctors/all"));
        doctorsChildren.add(new MenuItem("Add Doctor", "doctors/add"));
        doctorsChildren.add(new MenuItem("Schedules", "doctors/schedules"));
        addMenuItem("⚕", "Doctors", null, doctorsChildren);
        
        addSectionLabel("ADMINISTRATION");
        
        // Users & Roles (with children)
        List<MenuItem> usersChildren = new ArrayList<>();
        usersChildren.add(new MenuItem("All Users", "users/all"));
        usersChildren.add(new MenuItem("Roles", "users/roles"));
        usersChildren.add(new MenuItem("Permissions", "users/permissions"));
        addMenuItem("👤", "Users & Roles", null, usersChildren);
        
        // Settings (with children)
        List<MenuItem> settingsChildren = new ArrayList<>();
        settingsChildren.add(new MenuItem("General", "settings/general"));
        settingsChildren.add(new MenuItem("Database", "settings/database"));
        settingsChildren.add(new MenuItem("Backup", "settings/backup"));
        addMenuItem("⚙", "Settings", null, settingsChildren);
        
        // Reports (no children)
        addMenuItem("📈", "Reports", "reports", null);
    }

    private void addSectionLabel(String text) {
        Label sectionLabel = new Label(text);
        sectionLabel.getStyleClass().add("menu-section-label");
        menuContainer.getChildren().add(sectionLabel);
    }

    private void addMenuItem(String icon, String text, String viewPath, List<MenuItem> children) {
        VBox menuItemContainer = new VBox();
        menuItemContainer.setSpacing(0);
        
        // Parent Button
        HBox parentButton = createParentButton(icon, text, viewPath, children, menuItemContainer);
        menuItemContainer.getChildren().add(parentButton);
        
        // Children Container
        if (children != null && !children.isEmpty()) {
            VBox childrenContainer = new VBox();
            childrenContainer.getStyleClass().add("menu-children-container");
            childrenContainer.setVisible(false);
            childrenContainer.setManaged(false);
            
            for (MenuItem child : children) {
                Button childButton = createChildButton(child.text, child.viewPath);
                childrenContainer.getChildren().add(childButton);
            }
            
            menuItemContainer.getChildren().add(childrenContainer);
        }
        
        menuContainer.getChildren().add(menuItemContainer);
    }

    private HBox createParentButton(String icon, String text, String viewPath, 
                                    List<MenuItem> children, VBox container) {
        HBox button = new HBox();
        button.getStyleClass().add("menu-parent-item");
        button.setSpacing(10);
        button.setPrefWidth(Double.MAX_VALUE);
        button.setPadding(new Insets(12, 15, 12, 15));
        
        // Icon
        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("menu-icon");
        iconLabel.setStyle("-fx-font-size: 16px;");
        
        // Text
        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("menu-text");
        HBox.setHgrow(textLabel, Priority.ALWAYS);
        
        button.getChildren().addAll(iconLabel, textLabel);
        
        // Arrow for expandable menus
        Label arrow = null;
        if (children != null && !children.isEmpty()) {
            arrow = new Label("▶");
            arrow.getStyleClass().add("menu-arrow");
            button.getChildren().add(arrow);
        }
        
        // Click handler
        Label finalArrow = arrow;
        button.setOnMouseClicked(e -> {
            if (children != null && !children.isEmpty()) {
                // Toggle children
                VBox childrenContainer = (VBox) container.getChildren().get(1);
                boolean isVisible = childrenContainer.isVisible();
                
                childrenContainer.setVisible(!isVisible);
                childrenContainer.setManaged(!isVisible);
                
                // Rotate arrow
                if (finalArrow != null) {
                    RotateTransition rt = new RotateTransition(Duration.millis(200), finalArrow);
                    rt.setToAngle(isVisible ? 0 : 90);
                    rt.play();
                }
            } else if (viewPath != null) {
                // Load view
                setActive(button);
                if (onMenuItemClick != null) {
                    onMenuItemClick.accept(viewPath);
                }
            }
        });
        
        return button;
    }

    private Button createChildButton(String text, String viewPath) {
        Button button = new Button("○  " + text);
        button.getStyleClass().add("menu-child-item");
        button.setPrefWidth(Double.MAX_VALUE);
        button.setMaxWidth(Double.MAX_VALUE);
        
        button.setOnAction(e -> {
            setActive(button);
            if (onMenuItemClick != null) {
                onMenuItemClick.accept(viewPath);
            }
        });
        
        return button;
    }

    private void setActive(javafx.scene.Node button) {
        // Remove active class from all buttons
        menuContainer.getChildren().forEach(node -> {
            if (node instanceof VBox) {
                VBox container = (VBox) node;
                container.getChildren().forEach(child -> {
                    child.getStyleClass().remove("active");
                    if (child instanceof VBox) {
                        VBox childContainer = (VBox) child;
                        childContainer.getChildren().forEach(c -> c.getStyleClass().remove("active"));
                    }
                });
            }
        });
        
        // Add active class to clicked button
        button.getStyleClass().add("active");
    }

    // Helper class for menu items
    private static class MenuItem {
        String text;
        String viewPath;
        
        MenuItem(String text, String viewPath) {
            this.text = text;
            this.viewPath = viewPath;
        }
    }
}
