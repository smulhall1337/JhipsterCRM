import { element, by, ElementFinder } from 'protractor';

export class ParticipantComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-participant div table .btn-danger'));
    title = element.all(by.css('jhi-participant div h2#page-heading span')).first();

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

export class ParticipantUpdatePage {
    pageTitle = element(by.id('jhi-participant-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    firstNameInput = element(by.id('field_firstName'));
    middleInitialInput = element(by.id('field_middleInitial'));
    lastNameInput = element(by.id('field_lastName'));
    titleInput = element(by.id('field_title'));
    registrationDateInput = element(by.id('field_registrationDate'));
    addressInput = element(by.id('field_address'));
    cityInput = element(by.id('field_city'));
    stateInput = element(by.id('field_state'));
    dobInput = element(by.id('field_dob'));
    primaryPhoneInput = element(by.id('field_primaryPhone'));
    primaryPhoneTypeInput = element(by.id('field_primaryPhoneType'));
    secondaryPhoneInput = element(by.id('field_secondaryPhone'));
    secondaryPhoneTypeInput = element(by.id('field_secondaryPhoneType'));
    emailInput = element(by.id('field_email'));
    zipInput = element(by.id('field_zip'));
    medicareIdNumberInput = element(by.id('field_medicareIdNumber'));
    medicaidIdNumberInput = element(by.id('field_medicaidIdNumber'));
    genderInput = element(by.id('field_gender'));
    participantStatusInput = element(by.id('field_participantStatus'));
    countyInput = element(by.id('field_county'));
    dateAuthorizedInput = element(by.id('field_dateAuthorized'));
    authorizationNumberInput = element(by.id('field_authorizationNumber'));
    contactStatusSelect = element(by.id('field_contactStatus'));
    contactSubStatusSelect = element(by.id('field_contactSubStatus'));
    mcoSelect = element(by.id('field_mco'));
    referralTypeSelect = element(by.id('field_referralType'));
    referralSourceSelect = element(by.id('field_referralSource'));
    assignedToSelect = element(by.id('field_assignedTo'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setFirstNameInput(firstName) {
        await this.firstNameInput.sendKeys(firstName);
    }

    async getFirstNameInput() {
        return this.firstNameInput.getAttribute('value');
    }

    async setMiddleInitialInput(middleInitial) {
        await this.middleInitialInput.sendKeys(middleInitial);
    }

    async getMiddleInitialInput() {
        return this.middleInitialInput.getAttribute('value');
    }

    async setLastNameInput(lastName) {
        await this.lastNameInput.sendKeys(lastName);
    }

    async getLastNameInput() {
        return this.lastNameInput.getAttribute('value');
    }

    async setTitleInput(title) {
        await this.titleInput.sendKeys(title);
    }

    async getTitleInput() {
        return this.titleInput.getAttribute('value');
    }

    async setRegistrationDateInput(registrationDate) {
        await this.registrationDateInput.sendKeys(registrationDate);
    }

    async getRegistrationDateInput() {
        return this.registrationDateInput.getAttribute('value');
    }

    async setAddressInput(address) {
        await this.addressInput.sendKeys(address);
    }

    async getAddressInput() {
        return this.addressInput.getAttribute('value');
    }

    async setCityInput(city) {
        await this.cityInput.sendKeys(city);
    }

    async getCityInput() {
        return this.cityInput.getAttribute('value');
    }

    async setStateInput(state) {
        await this.stateInput.sendKeys(state);
    }

    async getStateInput() {
        return this.stateInput.getAttribute('value');
    }

    async setDobInput(dob) {
        await this.dobInput.sendKeys(dob);
    }

    async getDobInput() {
        return this.dobInput.getAttribute('value');
    }

    async setPrimaryPhoneInput(primaryPhone) {
        await this.primaryPhoneInput.sendKeys(primaryPhone);
    }

    async getPrimaryPhoneInput() {
        return this.primaryPhoneInput.getAttribute('value');
    }

    async setPrimaryPhoneTypeInput(primaryPhoneType) {
        await this.primaryPhoneTypeInput.sendKeys(primaryPhoneType);
    }

    async getPrimaryPhoneTypeInput() {
        return this.primaryPhoneTypeInput.getAttribute('value');
    }

    async setSecondaryPhoneInput(secondaryPhone) {
        await this.secondaryPhoneInput.sendKeys(secondaryPhone);
    }

    async getSecondaryPhoneInput() {
        return this.secondaryPhoneInput.getAttribute('value');
    }

    async setSecondaryPhoneTypeInput(secondaryPhoneType) {
        await this.secondaryPhoneTypeInput.sendKeys(secondaryPhoneType);
    }

    async getSecondaryPhoneTypeInput() {
        return this.secondaryPhoneTypeInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setZipInput(zip) {
        await this.zipInput.sendKeys(zip);
    }

    async getZipInput() {
        return this.zipInput.getAttribute('value');
    }

    async setMedicareIdNumberInput(medicareIdNumber) {
        await this.medicareIdNumberInput.sendKeys(medicareIdNumber);
    }

    async getMedicareIdNumberInput() {
        return this.medicareIdNumberInput.getAttribute('value');
    }

    async setMedicaidIdNumberInput(medicaidIdNumber) {
        await this.medicaidIdNumberInput.sendKeys(medicaidIdNumber);
    }

    async getMedicaidIdNumberInput() {
        return this.medicaidIdNumberInput.getAttribute('value');
    }

    async setGenderInput(gender) {
        await this.genderInput.sendKeys(gender);
    }

    async getGenderInput() {
        return this.genderInput.getAttribute('value');
    }

    async setParticipantStatusInput(participantStatus) {
        await this.participantStatusInput.sendKeys(participantStatus);
    }

    async getParticipantStatusInput() {
        return this.participantStatusInput.getAttribute('value');
    }

    async setCountyInput(county) {
        await this.countyInput.sendKeys(county);
    }

    async getCountyInput() {
        return this.countyInput.getAttribute('value');
    }

    async setDateAuthorizedInput(dateAuthorized) {
        await this.dateAuthorizedInput.sendKeys(dateAuthorized);
    }

    async getDateAuthorizedInput() {
        return this.dateAuthorizedInput.getAttribute('value');
    }

    async setAuthorizationNumberInput(authorizationNumber) {
        await this.authorizationNumberInput.sendKeys(authorizationNumber);
    }

    async getAuthorizationNumberInput() {
        return this.authorizationNumberInput.getAttribute('value');
    }

    async contactStatusSelectLastOption() {
        await this.contactStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contactStatusSelectOption(option) {
        await this.contactStatusSelect.sendKeys(option);
    }

    getContactStatusSelect(): ElementFinder {
        return this.contactStatusSelect;
    }

    async getContactStatusSelectedOption() {
        return this.contactStatusSelect.element(by.css('option:checked')).getText();
    }

    async contactSubStatusSelectLastOption() {
        await this.contactSubStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contactSubStatusSelectOption(option) {
        await this.contactSubStatusSelect.sendKeys(option);
    }

    getContactSubStatusSelect(): ElementFinder {
        return this.contactSubStatusSelect;
    }

    async getContactSubStatusSelectedOption() {
        return this.contactSubStatusSelect.element(by.css('option:checked')).getText();
    }

    async mcoSelectLastOption() {
        await this.mcoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async mcoSelectOption(option) {
        await this.mcoSelect.sendKeys(option);
    }

    getMcoSelect(): ElementFinder {
        return this.mcoSelect;
    }

    async getMcoSelectedOption() {
        return this.mcoSelect.element(by.css('option:checked')).getText();
    }

    async referralTypeSelectLastOption() {
        await this.referralTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async referralTypeSelectOption(option) {
        await this.referralTypeSelect.sendKeys(option);
    }

    getReferralTypeSelect(): ElementFinder {
        return this.referralTypeSelect;
    }

    async getReferralTypeSelectedOption() {
        return this.referralTypeSelect.element(by.css('option:checked')).getText();
    }

    async referralSourceSelectLastOption() {
        await this.referralSourceSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async referralSourceSelectOption(option) {
        await this.referralSourceSelect.sendKeys(option);
    }

    getReferralSourceSelect(): ElementFinder {
        return this.referralSourceSelect;
    }

    async getReferralSourceSelectedOption() {
        return this.referralSourceSelect.element(by.css('option:checked')).getText();
    }

    async assignedToSelectLastOption() {
        await this.assignedToSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async assignedToSelectOption(option) {
        await this.assignedToSelect.sendKeys(option);
    }

    getAssignedToSelect(): ElementFinder {
        return this.assignedToSelect;
    }

    async getAssignedToSelectedOption() {
        return this.assignedToSelect.element(by.css('option:checked')).getText();
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

export class ParticipantDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-participant-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-participant'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
