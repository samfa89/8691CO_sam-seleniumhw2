

public class InvalidCredentialsTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set up WebDriver and open the application URL
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://magento.softwaretestingboard.com/");
    }

    @Test(dataProvider = "loginData")
    public void invalidCredentials(String email, String password) {
        // Click on "Sign In" link
        WebElement signInLink = driver.findElement(By.linkText("Sign In"));
        signInLink.click();

        // Fill in the login form with invalid credentials
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("pass"));

        emailField.sendKeys(email);
        passwordField.sendKeys(password);

        // Click on "Sign In" button
        WebElement signInButton = driver.findElement(By.id("send2"));
        signInButton.click();

        // Verify invalid credentials error message
        WebElement errorMessage = driver.findElement(By.cssSelector(".message-error"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Expected error message not displayed");
        Assert.assertEquals(errorMessage.getText(), "Invalid login or password.", "Incorrect error message");

        // Verify user is not logged in
        WebElement accountDashboard = driver.findElement(By.cssSelector(".dashboard"));
        Assert.assertFalse(accountDashboard.isDisplayed(), "User was logged in with invalid credentials");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][]{
                {"invalid@example.com", "invalidpassword"},
                // Add more test data here
        };
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();}
}
