import { element, by, ElementFinder } from 'protractor';

export class ActionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-action div table .btn-danger'));
    title = element.all(by.css('jhi-action div h2#page-heading span')).first();

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

export class ActionUpdatePage {
    pageTitle = element(by.id('jhi-action-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dueDateInput = element(by.id('field_dueDate'));
    startDateTimeInput = element(by.id('field_startDateTime'));
    endDateTimeInput = element(by.id('field_endDateTime'));
    notesInput = element(by.id('field_notes'));
    participantSelect = element(by.id('field_participant'));
    userSelect = element(by.id('field_user'));
    actionSubTypeSelect = element(by.id('field_actionSubType'));
    actionTypeSelect = element(by.id('field_actionType'));
    actionStatusSelect = element(by.id('field_actionStatus'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDueDateInput(dueDate) {
        await this.dueDateInput.sendKeys(dueDate);
    }

    async getDueDateInput() {
        return this.dueDateInput.getAttribute('value');
    }

    async setStartDateTimeInput(startDateTime) {
        await this.startDateTimeInput.sendKeys(startDateTime);
    }

    async getStartDateTimeInput() {
        return this.startDateTimeInput.getAttribute('value');
    }

    async setEndDateTimeInput(endDateTime) {
        await this.endDateTimeInput.sendKeys(endDateTime);
    }

    async getEndDateTimeInput() {
        return this.endDateTimeInput.getAttribute('value');
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

    async actionSubTypeSelectLastOption() {
        await this.actionSubTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async actionSubTypeSelectOption(option) {
        await this.actionSubTypeSelect.sendKeys(option);
    }

    getActionSubTypeSelect(): ElementFinder {
        return this.actionSubTypeSelect;
    }

    async getActionSubTypeSelectedOption() {
        return this.actionSubTypeSelect.element(by.css('option:checked')).getText();
    }

    async actionTypeSelectLastOption() {
        await this.actionTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async actionTypeSelectOption(option) {
        await this.actionTypeSelect.sendKeys(option);
    }

    getActionTypeSelect(): ElementFinder {
        return this.actionTypeSelect;
    }

    async getActionTypeSelectedOption() {
        return this.actionTypeSelect.element(by.css('option:checked')).getText();
    }

    async actionStatusSelectLastOption() {
        await this.actionStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async actionStatusSelectOption(option) {
        await this.actionStatusSelect.sendKeys(option);
    }

    getActionStatusSelect(): ElementFinder {
        return this.actionStatusSelect;
    }

    async getActionStatusSelectedOption() {
        return this.actionStatusSelect.element(by.css('option:checked')).getText();
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

export class ActionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-action-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-action'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
