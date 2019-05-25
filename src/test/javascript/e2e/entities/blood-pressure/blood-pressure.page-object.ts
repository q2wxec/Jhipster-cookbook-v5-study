import { element, by, ElementFinder } from 'protractor';

export class BloodPressureComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-blood-pressure div table .btn-danger'));
    title = element.all(by.css('jhi-blood-pressure div h2#page-heading span')).first();

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

export class BloodPressureUpdatePage {
    pageTitle = element(by.id('jhi-blood-pressure-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    timestampInput = element(by.id('field_timestamp'));
    SystolicInput = element(by.id('field_Systolic'));
    DiastolicInput = element(by.id('field_Diastolic'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTimestampInput(timestamp) {
        await this.timestampInput.sendKeys(timestamp);
    }

    async getTimestampInput() {
        return this.timestampInput.getAttribute('value');
    }

    async setSystolicInput(Systolic) {
        await this.SystolicInput.sendKeys(Systolic);
    }

    async getSystolicInput() {
        return this.SystolicInput.getAttribute('value');
    }

    async setDiastolicInput(Diastolic) {
        await this.DiastolicInput.sendKeys(Diastolic);
    }

    async getDiastolicInput() {
        return this.DiastolicInput.getAttribute('value');
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

export class BloodPressureDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-bloodPressure-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-bloodPressure'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
