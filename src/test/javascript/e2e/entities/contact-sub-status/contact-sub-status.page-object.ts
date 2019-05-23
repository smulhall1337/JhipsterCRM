import { element, by, ElementFinder } from 'protractor';

export class ContactSubStatusComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-contact-sub-status div table .btn-danger'));
    title = element.all(by.css('jhi-contact-sub-status div h2#page-heading span')).first();

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

export class ContactSubStatusUpdatePage {
    pageTitle = element(by.id('jhi-contact-sub-status-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    subTypeOfSelect = element(by.id('field_subTypeOf'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async subTypeOfSelectLastOption() {
        await this.subTypeOfSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async subTypeOfSelectOption(option) {
        await this.subTypeOfSelect.sendKeys(option);
    }

    getSubTypeOfSelect(): ElementFinder {
        return this.subTypeOfSelect;
    }

    async getSubTypeOfSelectedOption() {
        return this.subTypeOfSelect.element(by.css('option:checked')).getText();
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

export class ContactSubStatusDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contactSubStatus-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contactSubStatus'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
