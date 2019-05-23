import { element, by, ElementFinder } from 'protractor';

export class ContactHistoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-contact-history div table .btn-danger'));
    title = element.all(by.css('jhi-contact-history div h2#page-heading span')).first();

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

export class ContactHistoryUpdatePage {
    pageTitle = element(by.id('jhi-contact-history-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateInput = element(by.id('field_date'));
    notesInput = element(by.id('field_notes'));
    statusInput = element(by.id('field_status'));
    participantSelect = element(by.id('field_participant'));
    userSelect = element(by.id('field_user'));
    contactTypeSelect = element(by.id('field_contactType'));
    contactSubTypeSelect = element(by.id('field_contactSubType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setNotesInput(notes) {
        await this.notesInput.sendKeys(notes);
    }

    async getNotesInput() {
        return this.notesInput.getAttribute('value');
    }

    async setStatusInput(status) {
        await this.statusInput.sendKeys(status);
    }

    async getStatusInput() {
        return this.statusInput.getAttribute('value');
    }

    async participantSelectLastOption() {
        await this.participantSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async participantSelectOption(option) {
        await this.participantSelect.sendKeys(option);
    }

    getParticipantSelect(): ElementFinder {
        return this.participantSelect;
    }

    async getParticipantSelectedOption() {
        return this.participantSelect.element(by.css('option:checked')).getText();
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

    async contactTypeSelectLastOption() {
        await this.contactTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contactTypeSelectOption(option) {
        await this.contactTypeSelect.sendKeys(option);
    }

    getContactTypeSelect(): ElementFinder {
        return this.contactTypeSelect;
    }

    async getContactTypeSelectedOption() {
        return this.contactTypeSelect.element(by.css('option:checked')).getText();
    }

    async contactSubTypeSelectLastOption() {
        await this.contactSubTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contactSubTypeSelectOption(option) {
        await this.contactSubTypeSelect.sendKeys(option);
    }

    getContactSubTypeSelect(): ElementFinder {
        return this.contactSubTypeSelect;
    }

    async getContactSubTypeSelectedOption() {
        return this.contactSubTypeSelect.element(by.css('option:checked')).getText();
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

export class ContactHistoryDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contactHistory-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contactHistory'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
