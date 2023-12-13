package pages;
import elements.GroupTableRow;
import elements.StudentsTableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;



public class MainPage {

    private final WebDriverWait wait;

    @FindBy(css = "nav li.mdc-menu-surface--anchor a")
    private WebElement usernameLinkInNavBar;
    @FindBy(id = "create-btn")
    private WebElement createGroupButton;
    @FindBy(xpath = "//form//span[contains(text(), 'Group name')]/following-sibling::input")
    private WebElement groupNameField;
    @FindBy(css = "form div.submit button")
    private WebElement submitButtonOnModalWindow;
    @FindBy(xpath = "//span[text()='Creating Study Group']" +
            "//ancestor::div[contains(@class, 'form-modal-header')]//button")
    private WebElement closeCreateGroupIcon;
    @FindBy(xpath = "//table[@aria-label='Tutors list']/tbody/tr")
    private List<WebElement> rowsInGroupTable;
    @FindBy(css = "div#generateStudentsForm-content input")
    private WebElement createStudentsForm;
    @FindBy(css = "div#generateStudentsForm-content div.submit button")
    private WebElement saveCreateStudentsForm;
    @FindBy(xpath = "//h2[@id='generateStudentsForm-title']/../button")
    private WebElement closeCreateStudentsForm;
    @FindBy(xpath = "//table[@aria-label='User list']/tbody/tr")
    private List<WebElement> rowsInStudentsTable;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    //Students Table
    public void clickAddStudentIconOnGroupWithTitle(String title){
        getRowByTitle(title).clickAddStudentIcon();
    }

    public void clickZoomIconOnGroupWithTitle(String title){
        getRowByTitle(title).clickZoomIcon();
    }

    public void typeAmountOfStudentsInForm(int amount){
        wait.until(ExpectedConditions.visibilityOf(createStudentsForm))
                .sendKeys(String.valueOf(amount));
    }

    public void clickSaveBtnOnCreateStudentsForm(){
        wait.until(ExpectedConditions.visibilityOf(saveCreateStudentsForm)).click();
    }

    public void closeCreateStudentsModalWindow(){
        closeCreateStudentsForm.click();
        wait.until(ExpectedConditions.invisibilityOf(closeCreateStudentsForm));
    }

    public void clickTrashIconOnStudentWithName(String name) {
        getStudentRowName(name).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnStudentWithName(String name) {
        getStudentRowName(name).clickRestoreFromTrashIcon();
    }

    public String getStatusOfStudent(String name){
        return getStudentRowName(name).getStatus();
    }

    public String getNameByIndex(int index){
        wait.until(ExpectedConditions.visibilityOfAllElements(rowsInStudentsTable));
        return rowsInStudentsTable.stream()
                .map(StudentsTableRow::new)
                .toList().get(index).getName();
    }

    private StudentsTableRow getStudentRowName(String name) {
        wait.until(ExpectedConditions.visibilityOfAllElements(rowsInStudentsTable));
        return rowsInStudentsTable.stream()
                .map(StudentsTableRow::new)
                .filter(row -> row.getName().equals(name))
                .findFirst().orElseThrow();
    }



//group
    public void waitGroupTitleByText(String title) {
        String xpath = String.format("//table[@aria-label='Tutors list']/tbody//td[text()='%s']", title);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void createGroup(String groupName) {
        wait.until(ExpectedConditions.visibilityOf(createGroupButton)).click();
        wait.until(ExpectedConditions.visibilityOf(groupNameField)).sendKeys(groupName);
        wait.until(ExpectedConditions.textToBePresentInElementValue(groupNameField, groupName));
        submitButtonOnModalWindow.click();
        waitGroupTitleByText(groupName);
    }

    public void closeCreateGroupModalWindow() {
        closeCreateGroupIcon.click();
        wait.until(ExpectedConditions.invisibilityOf(closeCreateGroupIcon));
    }

    public String getUsernameLabelText() {
        return wait.until(ExpectedConditions.visibilityOf(usernameLinkInNavBar))
                .getText().replace("\n", " ");
    }

    public void clickTrashIconOnGroupWithTitle(String title) {
        getRowByTitle(title).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnGroupWithTitle(String title) {
        getRowByTitle(title).clickRestoreFromTrashIcon();
    }

    public String getStatusOfGroupWithTitle(String title) {
        return getRowByTitle(title).getStatus();
    }

    private GroupTableRow getRowByTitle(String title) {
        return rowsInGroupTable.stream()
                .map(GroupTableRow::new)
                .filter(row -> row.getTitle().equals(title))
                .findFirst().orElseThrow();
    }

    public void waitStudentCount(String groupTestName, int studentCount) {
        getRowByTitle(groupTestName).waitCountStudents(studentCount);
    }
}
