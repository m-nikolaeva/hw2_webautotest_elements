package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

public class GroupTableRow {

    private final WebElement root;

    public GroupTableRow(WebElement root) {
        this.root = root;
    }

    public String getTitle() {
        return root.findElement(By.xpath("./td[2]")).getText();
    }

    public String getStatus() {
        return root.findElement(By.xpath("./td[3]")).getText();
    }

    public void clickTrashIcon() {
        root.findElement(By.xpath("./td/button[text()='delete']")).click();
        waitUntil(root -> root.findElement(By.xpath("./td/button[text()='restore_from_trash']")));
    }

    public void clickRestoreFromTrashIcon() {
        root.findElement(By.xpath("./td/button[text()='restore_from_trash']")).click();
        waitUntil(root -> root.findElement(By.xpath("./td/button[text()='delete']")));
    }

    private void waitUntil(Function<WebElement, WebElement> condition) {
         new FluentWait<>(root)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(condition);
    }

    public void clickAddStudentIcon() {
        root.findElement(By.cssSelector("td button i.material-icons")).click();
    }

    public void clickZoomIcon() {
        root.findElement(By.xpath(".//td/button[contains(., 'zoom_in')]")).click();
    }

    public void waitCountStudents(int expectedCount) {
        waitUntil(root ->
                root.findElement(By.xpath("./td[4]//span[text()='%s']".formatted(expectedCount))));
    }
}
