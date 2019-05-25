import { element, by, ElementFinder } from 'protractor';

export class GoalSettingsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-goal-settings div table .btn-danger'));
    title = element.all(by.css('jhi-goal-settings div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GoalSettingsUpdatePage {
    pageTitle = element(by.id('jhi-goal-settings-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    weeklyGoalInput = element(by.id('field_weeklyGoal'));
    weightUnitsSelect = element(by.id('field_weightUnits'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setWeeklyGoalInput(weeklyGoal) {
        await this.weeklyGoalInput.sendKeys(weeklyGoal);
    }

    async getWeeklyGoalInput() {
        return this.weeklyGoalInput.getAttribute('value');
    }

    async setWeightUnitsSelect(weightUnits) {
        await this.weightUnitsSelect.sendKeys(weightUnits);
    }

    async getWeightUnitsSelect() {
        return this.weightUnitsSelect.element(by.css('option:checked')).getText();
    }

    async weightUnitsSelectLastOption() {
        await this.weightUnitsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class GoalSettingsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-goalSettings-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-goalSettings'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
