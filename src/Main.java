import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Main {
    private static final HashMap<String, String> users = new HashMap<>();
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel mainPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login and Registration System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);

            // Add panels to the CardLayout
            mainPanel.add(createTabbedPanel(frame), "LoginRegister");
            mainPanel.add(createMainPagePanel(), "MainPage");

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    private static JTabbedPane createTabbedPanel(JFrame frame) {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Login Tab
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(loginTitle, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (users.containsKey(username) && users.get(username).equals(password)) {
                JOptionPane.showMessageDialog(frame, "Login successful! Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "MainPage");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        loginPanel.add(loginButton, gbc);

        // Registration Tab
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());

        JLabel registerTitle = new JLabel("Register");
        registerTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registerPanel.add(registerTitle, gbc);

        JLabel regUsernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        registerPanel.add(regUsernameLabel, gbc);

        JTextField regUsernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        registerPanel.add(regUsernameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        registerPanel.add(emailField, gbc);

        JLabel regPasswordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(regPasswordLabel, gbc);

        JPasswordField regPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        registerPanel.add(regPasswordField, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        registerButton.addActionListener(e -> {
            String username = regUsernameField.getText();
            String email = emailField.getText();
            String password = new String(regPasswordField.getPassword());

            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(frame, "Username already exists. Try a different one.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Invalid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(frame, "Password must be at least 6 characters long, contain at least one uppercase letter, and one special character.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                users.put(username, password);
                JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                regUsernameField.setText("");
                emailField.setText("");
                regPasswordField.setText("");
            }
        });
        registerPanel.add(registerButton, gbc);

        // Add tabs to the tabbed pane
        tabbedPane.addTab("Login", loginPanel);
        tabbedPane.addTab("Register", registerPanel);

        return tabbedPane;
    }

    private static JPanel createMainPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to the Main Page!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(welcomeLabel, BorderLayout.CENTER);
        return panel;
    }

    private static boolean isValidPassword(String password) {
        if (password.length() < 6) {
            return false;
        }
        boolean hasUppercase = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        return hasUppercase && hasSpecialChar;
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}