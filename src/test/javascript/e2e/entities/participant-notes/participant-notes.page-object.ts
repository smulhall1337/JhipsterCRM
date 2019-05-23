import { element, by, ElementFinder } from 'protractor';

export class ParticipantNotesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-participant-notes div table .btn-danger'));
    title = element.all(by.css('jhi-participant-notes div h2#page-heading span')).first();

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

export class ParticipantNotesUpdatePage {
    pageTitle = element(by.id('jhi-participant-notes-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    notesInput = element(by.id('field_notes'));
    userSelect = element(by.id('field_user'));
    participantSelect = element(by.id('field_participant'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNotesInput(notes) {
        await this.notesInput.sendKeys(notes);
    }

    async getNotesInput() {
        return this.notesInput.getAttribute('value');
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

export class ParticipantNotesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-participantNotes-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-participantNotes'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
