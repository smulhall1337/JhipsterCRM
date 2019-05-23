import { element, by, ElementFinder } from 'protractor';

export class AltContactComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-alt-contact div table .btn-danger'));
    title = element.all(by.css('jhi-alt-contact div h2#page-heading span')).first();

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

export class AltContactUpdatePage {
    pageTitle = element(by.id('jhi-alt-contact-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    relationshipInput = element(by.id('field_relationship'));
    phoneInput = element(by.id('field_phone'));
    notesInput = element(by.id('field_notes'));
    participantSelect = element(by.id('field_participant'));
    createdBySelect = element(by.id('field_createdBy'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setRelationshipInput(relationship) {
        await this.relationshipInput.sendKeys(relationship);
    }

    async getRelationshipInput() {
        return this.relationshipInput.getAttribute('value');
    }

    async setPhoneInput(phone) {
        await this.phoneInput.sendKeys(phone);
    }

    async getPhoneInput() {
        return this.phoneInput.getAttribute('value');
    }

    async setNotesInput(notes) {
        await this.notesInput.sendKeys(notes);
    }

    async getNotesInput() {
        return this.notesInput.getAttribute('value');
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

    async createdBySelectLastOption() {
        await this.createdBySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async createdBySelectOption(option) {
        await this.createdBySelect.sendKeys(option);
    }

    getCreatedBySelect(): ElementFinder {
        return this.createdBySelect;
    }

    async getCreatedBySelectedOption() {
        return this.createdBySelect.element(by.css('option:checked')).getText();
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

export class AltContactDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-altContact-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-altContact'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
